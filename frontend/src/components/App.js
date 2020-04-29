import React, { Component } from 'react';
import { render } from 'react-dom';
import MapGL, { Popup, NavigationControl, FullscreenControl, ScaleControl, Source, Layer, LinearInterpolator, WebMercatorViewport } from 'react-map-gl';
import { Nav, Navbar, Form } from 'react-bootstrap';
import { json } from 'd3-request';
import bbox from '@turf/bbox';
import { ToastContainer, toast } from 'react-toastify';
import { GeoJsonLayer } from '@deck.gl/layers';
import Geocoder from 'react-map-gl-geocoder';
import { Editor, EditorModes } from 'react-map-gl-draw';

/**
 * JS classes
 */
import AppData from '../data/AppData';

/**
 * CSS Styling
 */
import '../App.css';
import '../styles/Collapsible.css';
import '../styles/PinPopup.css';
import '../static/app.css';
import 'react-map-gl-geocoder/dist/mapbox-gl-geocoder.css'
import 'bootstrap/dist/css/bootstrap.min.css';
import 'mapbox-gl/dist/mapbox-gl.css';
import 'react-toastify/dist/ReactToastify.css';

import Card from 'react-bootstrap/Card';

/**
 * Map layers
 */
import { countyDataLayerFillable, countyDataLayerFillableHighlight, countyDataLayerOutline } from '../layers/CountyLayer';
import { stateLayerFill, stateLayerFillHighlight } from '../layers/StateLayer';
import { precinctLayerFill, precinctLayerFillHighlight, precinctLayerOutline } from '../layers/PrecinctLayer';
import { congressionalLayerFill, congressionalLayerFillHighlight, congressionalLayerOutline } from '../layers/CongressionalLayer';

/**
 * Our components
 */
import Pins from './map/ErrorPins';
import ErrorInfo from './map/ErrorModal';
import StateSelector from './navbar/StateSelector';
import LeftSidebar from './sidebar/LeftSidebar';
import Toolbar from './map/toolbar';
import UserModeSelector from './navbar/UserModeSelector';

/**
 * Static data files
 */
import ERRORS from '../data/errors.json';
//import STATES_TOOLTIP_DATA from '../data/states_tooltip_data.geojson';
//import NY_COUNTY_SHORELINE_DATA from '../data/ny_county_shoreline.geojson';
//import NY_PRECINCT_DATA from '../data/ny_precincts.geojson';
import NY_CONGRESSIONAL_DATA from '../data//New_York_Congressional_Districts.GeoJSON';
// import TESTING_PRECINCT_DATA from './data/GeoJSON_example.geojson';

/**
 * Mapbox Style & API Key
 */
const MAPBOX_STYLE = 'mapbox://styles/shortland/ck7fn4gmu014c1ip60jlumnm2';
const MAPBOX_API = 'pk.eyJ1Ijoic2hvcnRsYW5kIiwiYSI6ImNqeXVzOWhsbjBpYzczY29hNGZycTlqdXAifQ.B6l-uEqGG-Pw6-quz4eflQ';

/**
 * @state viewport: the mpas viewport (lat, long, etc)
 * 
 */
export default class App extends Component {
    constructor(props) {
        super(props);
        this.state = {
            stateData: null,
            countyData: null,
            countyDataOutline: null,
            congressionalDistrictData: null,
            precinctData: null,
            viewport: {
                width: "100%",
                height: window.innerHeight - 56,
                latitude: 37.0902,
                longitude: -105.7129,
                zoom: 3.3,
            },
            popupInfo: null,
            selectedFeature: null,
            selectedMode: EditorModes.READ_ONLY,
            selectedFeatureId: null,
            hoveredFeature: null,
            filter: null,
            shouldShowPins: false,
            features: {},
            selectedFeatureIndex: null,
            userMode: "View",
            layers: { states: true, counties: true, congressional: true, precincts: true, parks: true }
        };

        this._editorRef = null;
        this.showErrorPins = this.showErrorPins.bind(this);
        this.appData = new AppData();

        this.filters = {
            countyFilter: ['==', 'NAME', ''],
            precinctFilter: ['==', 'GEOID10', ''],
            stateFilter: ['==', 'name', ''],
            congressionalFilter: ['==', 'NAMELSAD', '']
        }

        this.checkboxes = {
            States: React.createRef(), Counties: React.createRef(), CongressionalDistricts: React.createRef(), Precincts: React.createRef(), NationalParks: React.createRef()
        }
    }

    /**
     * For Mapbox geocoding
     */
    mapRef = React.createRef();

    handleViewportChange = viewport => {
        this.setState({
            viewport: { ...this.state.viewport, ...viewport }
        });
    };

    handleGeocoderViewportChange = viewport => {
        const geocoderDefaultOverrides = { transitionDuration: 1000 };

        return this.handleViewportChange({
            ...viewport,
            ...geocoderDefaultOverrides,
        });
    };

    handleOnResult = event => {
        console.log(event.result);
        this.setState({
            searchResultLayer: new GeoJsonLayer({
                id: "search-result",
                data: event.result.geometry,
                getFillColor: [255, 0, 0, 128],
                getRadius: 1000,
                pointRadiusMinPixels: 10,
                pointRadiusMaxPixels: 10,
            })
        });
    };

    /**
     * For React Map Gl Draw
     */
    _onDelete = () => {
        const { selectedFeatureIndex } = this.state;
        if (selectedFeatureIndex === null || selectedFeatureIndex === undefined) {
            toast.info("Please select a new created precint before deleting action", {
                position: toast.POSITION.BOTTOM_RIGHT,
            });
            return;
        }
        else {
            toast.info("Selected precinct is deleted", {
                position: toast.POSITION.BOTTOM_RIGHT,
            });
            let features = this.state.features;
            if (selectedFeatureIndex > -1) {
                features.data.splice(selectedFeatureIndex, 1);
            }
            this.setState({ selectedFeatureIndex: null, selectedMode: EditorModes.READ_ONLY, features: features });
        }
        this._editorRef.deleteFeatures(selectedFeatureIndex);

    };

    _switchMode = evt => {
        let selectedMode = evt.target.id;
        if (selectedMode === this.state.selectedMode) {
            selectedMode = null;
        }

        this.setState({ selectedMode });
    };

    //Map GL Draw select a feature (a created shape by the MapGLDraw)
    _onSelect = selected => {
        this.setState({ selectedFeatureIndex: (selected && selected.selectedFeatureIndex) });
    };
    //Map GL Draw update (user draw a new shape)
    _onUpdate = (features) => {
        this.setState({ features: features, selectedMode: EditorModes.READ_ONLY, selectedFeatureIndex: null });
    }
    // _updateViewport = viewport => {
    //     this.setState({ viewport });
    // };

    //toobar save button click event
    _onSaveRequest = (toolBarRequest) => {
        if (toolBarRequest) {
            let selected_saved_feature = this.state.features.data[this.state.selectedFeatureIndex];
            console.log(selected_saved_feature);
            this.setState({ selectedMode: EditorModes.READ_ONLY, selectedFeatureIndex: null })
            if (selected_saved_feature) {
                toast.info("New precinct is saved", {
                    position: toast.POSITION.BOTTOM_RIGHT,
                });
            }
            else {
                toast.info("Please select a new created precint to save", {
                    position: toast.POSITION.BOTTOM_RIGHT,
                });
            }
        }
    }

    _renderToolbar = () => {
        const { userMode } = this.state;
        if (userMode === "Edit") {
            return (
                <Toolbar
                    selectedMode={this.state.selectedMode}
                    onSwitchMode={this._switchMode}
                    onDelete={this._onDelete}
                    onSelect={this._onSelect}
                    features={this.state.features}
                    selectedFeatureId={this.state.selectedFeatureId}
                    toolBarRequest={this._onSaveRequest}
                />
            );
        }
        else {
            return;
        }

    };

    _onClick = event => {
        // sets the selected feature onclcik, to have properties displayed by LeftSidebar
        const {
            features,
        } = event;

        if (features) {
            const stateFeature = features.find(f => f.layer.id === 'stateFill');
            const countyFeature = features.find(f => f.layer.id === 'countyFill');
            const congressionalFeature = features.find(f => f.layer.id === 'congressionalFill')
            const precinctFeature = features.find(f => f.layer.id === 'precinctFill');

            if (stateFeature) {
                stateFeature.properties.type = "State";
                // if a clicks on a state that was already selected/clicked on
                if (this.state.selectedFeature) {
                    if (stateFeature.properties.name === this.state.selectedFeature.properties.name) {
                        this._zoomToFeature(event);
                        return;
                    }
                } else {
                    toast.info("Click the same feature (state/county) again to zoom in.", {
                        position: toast.POSITION.BOTTOM_RIGHT,
                    });
                }
                this.setState({ selectedFeature: stateFeature });
            }
            else if (countyFeature) {
                countyFeature.properties.type = "County";
                // if a clicks on a county that was already selected/clicked on
                if (this.state.selectedFeature) {
                    if (countyFeature.properties.NAME === this.state.selectedFeature.properties.NAME) {
                        this._zoomToFeature(event);
                        return;
                    }
                }
                this.setState({ selectedFeature: countyFeature });
            }
            else if (congressionalFeature) {
                congressionalFeature.properties.type = "Congressional District";
                this.setState({ selectedFeature: congressionalFeature });
            }
            else if (precinctFeature) {
                precinctFeature.properties.type = "Precinct";
                this.setState({ selectedFeature: precinctFeature });
            }
            else {
                this.setState({ selectedFeature: null });
            }
        }
    }

    _zoomToFeature(event) {
        const feature = event.features[0];

        if (!feature) {
            return;
        }

        if (feature.layer.id === "stateFill" || feature.layer.id === "countyFill") {
            const [minLng, minLat, maxLng, maxLat] = bbox(feature);
            const viewport = new WebMercatorViewport(this.state.viewport);
            const { longitude, latitude, zoom } = viewport.fitBounds([[minLng, minLat], [maxLng, maxLat]], {
                padding: 40
            });

            this.setState({
                viewport: {
                    ...this.state.viewport,
                    longitude,
                    latitude,
                    zoom,
                    transitionInterpolator: new LinearInterpolator({
                        around: [event.offsetCenter.x, event.offsetCenter.y]
                    }),
                    transitionDuration: 1000,
                }
            });
            return;
        }
    }

    getFeatureFilter = (feature) => {
        if (!feature) {
            return null;
        }
        else if (feature.isState) {
            return ['==', 'name', feature.properties.name];
        }
        else if (feature.isCounty) {
            return ['==', 'NAME', feature.properties.NAME];
        }
        else if (feature.isCongressional) {
            return ['==', 'NAMELSAD', feature.properties.NAMELSAD];
        }
        else if (feature.isPrecinct) {
            return ['==', 'GEOID10', feature.properties.GEOID10];
        }
        else {
            return null;
        }
    }

    _onHover = event => {
        const {
            features,
            srcEvent: { x, y },
        } = event;

        if (!event.type || !event.features || event.features.length === 0 || event.features[0].source === "composite") {
            this.setState({
                hoveredFeature: null,
                filter: null,
            });
            return;
        }

        // prevent hovering on pin making the tooltip show at top left of map
        if (x !== 0 && y !== 0) {
            this.setState({ x: x, y: y });
        }

        if (features) {
            const stateHovered = features.find(f => f.layer.id === 'stateFill');
            const countyHovered = features.find(f => f.layer.id === 'countyFill');
            const congressionalHovered = features.find(f => f.layer.id === 'congressionalFill');
            const precinctHovered = features.find(f => f.layer.id === 'precinctFill');

            const hovered = stateHovered || countyHovered || congressionalHovered || precinctHovered;

            if (stateHovered) { hovered.isState = true; }
            else if (countyHovered) { hovered.isCounty = true; }
            else if (congressionalHovered) { hovered.isCongressional = true; }
            else if (precinctHovered) { hovered.isPrecinct = true; }
            const filter = this.getFeatureFilter(hovered);

            this.setState({
                hoveredFeature: hovered,
                filter: filter
            });
        }
    }

    _renderTooltip() {
        const { hoveredFeature, x, y } = this.state;

        if (!hoveredFeature) { return; }
        else if (hoveredFeature.isState) {
            const stateHovered = hoveredFeature;
            return (
                hoveredFeature && (
                    <div className="state-tooltip" style={{ left: x, top: y }}>
                        <h5>{stateHovered.properties.name}</h5>
                        <div>Counties: {stateHovered.properties.amount_counties}</div>
                        {/* <br /> */}
                        {/* <div style={{ "fontStyle": "italic" }}>(click again to enlarge)</div> */}
                    </div>
                )
            );
        }
        else if (hoveredFeature.isCounty) {
            const countyHovered = hoveredFeature;
            return (
                countyHovered && (
                    <div className="state-tooltip" style={{ left: x, top: y }}>
                        <h5>{countyHovered.properties.NAME} County</h5>
                        <div>FIPS Code: {countyHovered.properties.FIPS_CODE}</div>
                        {/* <br /> */}
                        {/* <div style={{ "fontStyle": "italic" }}>(click again to enlarge)</div> */}
                    </div>
                )
            );
        }
        else if (hoveredFeature.isCongressional) {
            const congressionalHovered = hoveredFeature;
            return (
                congressionalHovered && (
                    <div className="state-tooltip" style={{ left: x, top: y }}>
                        <h5>{congressionalHovered.properties.NAMELSAD}</h5>
                        <div></div>
                        {/* <br /> */}
                        {/* <div style={{ "fontStyle": "italic" }}>(click again to enlarge)</div> */}
                    </div>
                )
            );
        }
        else if (hoveredFeature.isPrecinct) {
            const precinctHovered = hoveredFeature;
            return (
                precinctHovered && (
                    <div className="state-tooltip" style={{ left: x, top: y }}>
                        <h5>Precinct GEOID: {precinctHovered.properties.GEOID10}</h5>
                        {/* <div>FIPS Code: {precinctHovered.properties}</div> */}
                        {/* <br /> */}
                        {/* <div style={{ "fontStyle": "italic" }}>(click again to enlarge)</div> */}
                    </div>
                )
            );
        }
    }

    userModeSelect = (userMode) => {
        if (userMode) {
            this.setState({ userMode: userMode });
        }
    }

    stateSelect(name) {
        let latitude = 0;
        let longitude = 0;
        let zoom;

        switch (name) {
            case "New York":
            case "NY":
                latitude = 43.2994;
                longitude = -76.2179;
                zoom = 6;
                break;
            case "Wisconsin":
            case "WI":
                latitude = 44.7844;
                longitude = -89.7879;
                zoom = 6;
                break;
            case "Utah":
            case "UT":
                latitude = 39.3210;
                longitude = -112.0937;
                zoom = 6;
                break;
            default:
        }

        this.setState({
            viewport: {
                ...this.state.viewport,
                latitude: latitude,
                longitude: longitude,
                zoom: zoom,
            },
        });
    }

    /**
     * Rendering error pins on the map & its popup
     */
    _onClickError = error_data => {
        this.setState({ popupInfo: error_data });
        console.log(error_data);
        this.setState({
            viewport: {
                ...this.state.viewport,
                longitude: error_data.longitude,
                latitude: error_data.latitude,
                transitionDuration: 1000,
                zoom: 11,
            }
        });
    };

    _renderErrorPopup() {
        const { popupInfo } = this.state;

        return (
            popupInfo && (
                <Popup
                    tipSize={5}
                    anchor="top"
                    longitude={popupInfo.longitude}
                    latitude={popupInfo.latitude}
                    closeOnClick={false}
                    onClose={() => this.setState({ popupInfo: null })}
                >
                    <ErrorInfo info={popupInfo} />
                </Popup>
            )
        );
    }

    showErrorPins() {
        const { shouldShowPins } = this.state;
        if (shouldShowPins) {
            this.setState({ shouldShowPins: false });
        } else {
            this.setState({ shouldShowPins: true });
        }
    }

    componentDidMount() {
        this.appData.fetchAllStates().then(result => {
            console.log(result);
            this.setState({ stateData: result });
        });

        this.appData.fetchCongressionalDistrictByCID(36005).then(result => {
            console.log(result);
            this.setState({ congressionalDistrictData: result });
        })
        /**
         * State data
         */
        /* json(
            STATES_TOOLTIP_DATA,
            (error, response) => {
                if (!error) {
                    console.log(response)
                    // this.setState({ stateData: response});
                }
            }
        ); */
        /**
         * County data outline
         */
        /* json(
            NY_COUNTY_SHORELINE_DATA,
            (error, response) => {
                if (!error) {
                    this.setState({countyDataOutline: response});
                }
            }
        ); */
        /**
         * County data
         */
        /* json(
            NY_COUNTY_SHORELINE_DATA,
            (error, response) => {
                if (!error) {
                    this.setState({countyData: response});
                }
            }
        );
        */
        /* json(
            NY_CONGRESSIONAL_DATA,
            (error, response) => {
                if (!error) {
                    console.log(response);
                    this.setState({ congressionalDistrictData: response });
                }
            }
        ); */

        /* json(
            NY_PRECINCT_DATA,
            (error, response) => {
                if (!error) {
                    console.log(response);
                    this.setState({precinctData: response});
                }
                else {
                    console.log(error);
                }
            }
        ); */
    }

    /**
     * called when rendering our data layers to the map
     */
    renderLayers() {
        const { stateData, countyData, countyDataOutline, congressionalDistrictData, precinctData, stateFilter, countyFilter, precinctFilter, congressionalFilter } = this.state;
        const { layers } = this.state;

        return (
            <>
                {layers.states &&
                    < Source type="geojson" data={stateData} >
                        <Layer
                            {...stateLayerFillHighlight}
                            filter={stateFilter}
                            maxzoom={5}
                        />
                        <Layer
                            {...stateLayerFill}
                            maxzoom={5}
                        />
                    </Source >
                }

                {/* NY COUNTY DATA */}
                {/* < Source type="geojson" data={countyData} >
                    <Layer
                        {...countyDataLayerFillableHighlight}
                        filter={countyFilter}
                        minzoom={5}
                        maxzoom={8}
                    />
                    <Layer
                        {...countyDataLayerFillable}
                        minzoom={5}
                        maxzoom={8}
                    />
                </Source > */}

                {/* NY COUNTY DATA (OUTLINE) */}
                {/* < Source type="geojson" data={countyDataOutline} >
                    <Layer
                        {...countyDataLayerOutline}
                        minzoom={5}
                    // maxzoom={8}
                    />
                </Source > */}

                {/* CONGRESSIONAL DATA */}
                < Source type="geojson" data={congressionalDistrictData} >
                    <Layer
                        {...congressionalLayerFillHighlight}
                        //filter={congressionalFilter}
                        minzoom={6}
                    />
                    <Layer
                        {...congressionalLayerOutline}
                        minzoom={6}
                    />
                    <Layer
                        {...congressionalLayerFill}
                        minzoom={6}
                    />
                </Source >

                {/* NY PRECINCT DATA */}
                < Source type="geojson" data={precinctData} >
                    <Layer
                        {...precinctLayerFillHighlight}
                        filter={precinctFilter}
                        minzoom={8}
                    />
                    <Layer
                        {...precinctLayerOutline}
                        minzoom={8}
                    />
                    <Layer
                        {...precinctLayerFill}
                        minzoom={8}
                    />
                </Source >
            </>
        );
    }

    render() {
        const { viewport, selectedMode, shouldShowPins } = this.state;

        return (
            <div className="App">
                <Navbar bg="dark" expand="lg" variant="dark">
                    <Navbar.Brand href="#home">Election Data Quality</Navbar.Brand>
                    <Navbar.Toggle aria-controls="basic-navbar-nav" />
                    <Navbar.Collapse id="basic-navbar-nav">
                        <Nav className="mr-auto">
                            {/* <Nav.Link href="#tmp">Something</Nav.Link> */}
                            <StateSelector
                                select_state={(state_abv) => this.stateSelect.bind(this, state_abv)}
                            />
                            <UserModeSelector userModeSelect={(selectedMode) => this.userModeSelect(selectedMode)} />
                        </Nav>
                    </Navbar.Collapse>
                </Navbar>

                <div>
                    <div id="leftCol">
                        <LeftSidebar
                            selected={this.state.selectedFeature}
                            showErrorPins={this.showErrorPins.bind(this)}
                        />
                    </div>

                    <MapGL
                        {...viewport}
                        onViewportChange={(viewport => this.setState({ viewport: viewport }))}
                        mapStyle={MAPBOX_STYLE}
                        mapboxApiAccessToken={MAPBOX_API}
                        onHover={this._onHover}
                        onClick={this._onClick}
                        doubleClickZoom={false}
                        ref={this.mapRef}
                    >
                        <div id="map-checkboxes" style={{
                            position: 'absolute',
                            textAlign: 'left',
                            fontSize: '10pt',
                            bottom: '10%',
                            right: '10px'
                        }}>
                            {this._renderCheckboxes()}
                        </div>

                        <Editor
                            ref={_ => (this._editorRef = _)}
                            clickRadius={12}
                            onSelect={(selected) => this._onSelect(selected)}
                            onUpdate={(features) => this._onUpdate(features)}
                            mode={selectedMode}
                        />

                        {/* Geocoder - enables searching on the map */}
                        <Geocoder
                            mapRef={this.mapRef}
                            onResult={this.handleOnResult}
                            onViewportChange={this.handleGeocoderViewportChange}
                            mapboxApiAccessToken={MAPBOX_API}
                            position="top-right"
                        />
                        {/* This is used alongside the Geocoder... 
                            But apparently it doesn't need to be here for the geocoder to work?
                            I commented it out - because it mysteriously breaks the Editor...
                         */}
                        {/* <DeckGL {...viewport} layers={[searchResultLayer]} /> */}

                        {/* For rendering the controls at top left of the map */}
                        <div className="FullScreenController MapControllers">
                            <FullscreenControl />
                        </div>
                        <div className="NavigationController MapControllers">
                            <NavigationControl />
                        </div>
                        <div className="ScaleController MapControllers">
                            <ScaleControl />
                        </div>

                        {this.renderLayers()}

                        {this._renderTooltip()}

                        {this._renderToolbar()}

                        {/* For rendering pins over our map */}
                        <Pins
                            data={ERRORS}
                            onClick={this._onClickError}
                            shouldShowPins={shouldShowPins}
                        />
                        {this._renderErrorPopup()}
                    </MapGL>
                </div>

                <ToastContainer />
            </div >
        );
    }

    toggleLayer = (layer) => {
        const { layers } = this.state;
        switch (layer) {
            case "States":
                layers.states = !layers.states;
                break;
            case "Counties":
                layers.counties = !layers.counties;
                break;
            case "Congressional Districts":
                layers.congressional = !layers.congressional;
                break;
            case "Precincts":
                layers.precincts = !layers.precincts;
                break;
            case "National Parks":
                layers.parks = !layers.parks;
                break;
            default:
        }
        this.setState({ layers: layers })
    }

    _renderCheckboxes = () => {
        return (
            <div>
                <Card border="secondary" style={{ width: '12rem', backgroundColor: 'rgba(255,255,255,0.9)' }} >
                    <Card.Header>Layers</Card.Header>
                    <Card.Body>
                        <Form>
                            {['States', 'Counties', 'Congressional Districts', 'Precincts', 'National Parks'].map((name) => (
                                <div key={"checkbox-".concat(name)}>
                                    <Form.Check inline label={name} type={'checkbox'} defaultChecked={true} onChange={this.toggleLayer.bind(this, name)} />
                                </div>
                            ))}
                        </Form>
                    </Card.Body>
                    <Card.Footer style={{ lineHeight: '1.0' }}>
                        <small className="text-muted">Note: some layers will only show at certain zoom levels</small>
                    </Card.Footer>
                </Card>
            </div>
        );
    }
}

export function renderToDom(container) {
    render(<App />, container);
}
