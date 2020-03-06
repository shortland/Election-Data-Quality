import React, { Component } from 'react';
import { render } from 'react-dom';
import MapGL, { Popup, NavigationControl, FullscreenControl, ScaleControl, Source, Layer, LinearInterpolator, WebMercatorViewport } from 'react-map-gl';
import { Nav, Navbar } from 'react-bootstrap';
import { json } from 'd3-request';
import bbox from '@turf/bbox';
import { ToastContainer, toast } from 'react-toastify';
import { GeoJsonLayer } from '@deck.gl/layers';
import Geocoder from 'react-map-gl-geocoder';
import { Editor, EditorModes } from 'react-map-gl-draw';

/**
 * CSS Styling
 */
import './App.css';
import './styles/Collapsible.css';
import './styles/PinPopup.css';
import './static/app.css';
import 'react-map-gl-geocoder/dist/mapbox-gl-geocoder.css'
import 'bootstrap/dist/css/bootstrap.min.css';
import 'mapbox-gl/dist/mapbox-gl.css';
import 'react-toastify/dist/ReactToastify.css';

/**
 * Map layers
 */
import { countyDataLayerFillable, countyDataLayerFillableHighlight, countyDataLayerOutline } from './layers/CountyLayer';
import { stateLayerFill, stateLayerFillHighlight } from './layers/StateLayer';
import { precinctLayerFill, precinctLayerFillHighlight, precinctLayerOutline } from './layers/PrecinctLayer';

/**
 * Our components
 */
import Pins from './components/map-components/ErrorPins';
import ErrorInfo from './components/map-components/ErrorModal';
import StateSelector from './components/StateSelector';
import LeftSidebar from './components/LeftSidebar';
import Toolbar from './toolbar';
import UserModeSelector from './components/UserModeSelector';

/**
 * Static data files
 */
import ERRORS from './data/errors.json';
import STATES_TOOLTIP_DATA from './data/states_tooltip_data.geojson';
import NY_COUNTY_SHORELINE_DATA from './data/ny_county_shoreline.geojson';
import NY_PRECINCT_DATA from './data/ny_precincts.geojson';
// import TESTING_PRECINCT_DATA from './data/GeoJSON_example.geojson';

/**
 * Mapbox Style & API Key
 */
const MAPBOX_STYLE = 'mapbox://styles/shortland/ck7fn4gmu014c1ip60jlumnm2';
const MAPBOX_API = 'pk.eyJ1Ijoic2hvcnRsYW5kIiwiYSI6ImNqeXVzOWhsbjBpYzczY29hNGZycTlqdXAifQ.B6l-uEqGG-Pw6-quz4eflQ';

export default class App extends Component {
    constructor(props) {
        super(props);
        this.state = {
            countyData: null,
            countyDataOutline: null,
            stateData: null,
            hoveredFeature: null,
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
            shouldShowPins: false,
            features: {},
            selectedFeatureIndex: null,
            userMode: "View",
            countyFilter: ['==', 'NAME', ''],
            precinctFilter: ['==', 'GEOID10', ''],
            stateFilter: ['==', 'name', ''],
        };

        this._editorRef = null;
        this.showErrorPins = this.showErrorPins.bind(this);
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

    componentDidMount() {
        /**
         * State data
         */
        json(
            STATES_TOOLTIP_DATA,
            (error, response) => {
                if (!error) {
                    this.setState({
                        stateData: response,
                    });
                }
            }
        );

        /**
         * County data outline
         */
        json(
            NY_COUNTY_SHORELINE_DATA,
            (error, response) => {
                if (!error) {
                    this.setState({
                        countyDataOutline: response,
                    });
                }
            }
        );

        /**
         * County data
         */
        json(
            NY_COUNTY_SHORELINE_DATA,
            (error, response) => {
                if (!error) {
                    this.setState({
                        countyData: response,
                    });
                }
            }
        );

        /**
         * Precinct Data
         */
        json(
            NY_PRECINCT_DATA,
            (error, response) => {
                if (!error) {
                    this.setState({
                        precinctData: response,
                    });
                }
            }
        );
    }

    _onClick = event => {
        // sets the selected feature onclcik, to have properties displayed by LeftSidebar
        const {
            features,
        } = event;

        const stateFeature = features && features.find(f => f.layer.id === 'stateFill');
        if (stateFeature) {
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
            return;
        }

        const countyFeature = features && features.find(f => f.layer.id === 'countyFill');
        if (countyFeature) {
            // if a clicks on a county that was already selected/clicked on
            if (this.state.selectedFeature) {
                if (countyFeature.properties.NAME === this.state.selectedFeature.properties.NAME) {
                    this._zoomToFeature(event);
                    return;
                }
            }

            this.setState({ selectedFeature: countyFeature });
            return;
        }

        const precinctFeature = features && features.find(f => f.layer.id === 'precinctFill');
        if (precinctFeature) {
            this.setState({ selectedFeature: precinctFeature });
            return;
        }
    };

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

    _onHover = event => {
        const {
            features,
            srcEvent: { x, y },
        } = event;

        if (!event.type || !event.features || event.features.length === 0 || event.features[0].source === "composite") {
            this.setState({
                stateHovered: null,
                countyHovered: null,
                precinctHovered: null,
                stateFilter: ['==', 'name', ''],
                countyFilter: ['==', 'NAME', ''],
                precinctFilter: ['==', 'GEOID10', ''],
            });
            return;
        }

        // prevent hovering on pin making the tooltip show at top left of map
        if (x !== 0 && y !== 0) {
            this.setState({ x: x, y: y });
        }

        const stateHovered = features && features.find(f => f.layer.id === 'stateFill');
        this.setState({ stateHovered: stateHovered });
        if (stateHovered) {
            this.setState({ stateFilter: ['==', 'name', stateHovered.properties.name] });
            return;
        }

        const countyHovered = features && features.find(f => f.layer.id === 'countyFill');
        this.setState({ countyHovered: countyHovered });
        if (countyHovered) {
            this.setState({ countyFilter: ['==', 'NAME', countyHovered.properties.NAME] });
            return;
        }

        const precinctHovered = features && features.find(f => f.layer.id === 'precinctFill');
        this.setState({ precinctHovered: precinctHovered });
        if (precinctHovered) {
            this.setState({ precinctFilter: ['==', 'GEOID10', precinctHovered.properties.GEOID10] });
            return;
        }
    }

    _renderTooltip() {
        const { stateHovered, countyHovered, precinctHovered, x, y } = this.state;

        if (stateHovered) {
            return (
                stateHovered && (
                    <div className="state-tooltip" style={{ left: x, top: y }}>
                        <h5>{stateHovered.properties.name}</h5>
                        <div>Counties: {stateHovered.properties.amount_counties}</div>
                        {/* <br /> */}
                        {/* <div style={{ "fontStyle": "italic" }}>(click again to enlarge)</div> */}
                    </div>
                )
            );
        }

        if (countyHovered) {
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

        if (precinctHovered) {
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

    render() {
        const { viewport, stateData, countyDataOutline, countyData, precinctData, selectedMode, shouldShowPins, stateFilter, countyFilter, precinctFilter } = this.state;

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

                        {/* STATES DATA */}
                        <Source type="geojson" data={stateData}>
                            <Layer
                                {...stateLayerFillHighlight}
                                filter={stateFilter}
                                maxzoom={5}
                            />
                            <Layer
                                {...stateLayerFill}
                                maxzoom={5}
                            />
                        </Source>

                        {/* NY COUNTY DATA */}
                        <Source type="geojson" data={countyData}>
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
                        </Source>

                        {/* NY COUNTY DATA (OUTLINE) */}
                        <Source type="geojson" data={countyDataOutline}>
                            <Layer
                                {...countyDataLayerOutline}
                                minzoom={5}
                            // maxzoom={8}
                            />
                        </Source>

                        {/* NY PRECINCT DATA */}
                        <Source type="geojson" data={precinctData}>
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
                        </Source>

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
}

export function renderToDom(container) {
    render(<App />, container);
}
