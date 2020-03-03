import React, { Component } from 'react';
import { render } from 'react-dom';
import MapGL, { Popup, NavigationControl, FullscreenControl, ScaleControl, Source, Layer, LinearInterpolator, WebMercatorViewport } from 'react-map-gl';
import { Nav, Navbar, Form, FormControl, Button } from 'react-bootstrap';
import { json } from 'd3-request';
import bbox from '@turf/bbox';
import { ToastContainer, toast } from 'react-toastify';
import DeckGL from '@deck.gl/react';
import { GeoJsonLayer } from '@deck.gl/layers';
import Geocoder from 'react-map-gl-geocoder';
import { Editor, EditorModes } from 'react-map-gl-draw';
/**
 * CSS Styling
 */
import './App.css';
import './styles/Collapsible.css';
import './static/app.css';
// import './styles/GeoCodeSearch.css';
import 'react-map-gl-geocoder/dist/mapbox-gl-geocoder.css'
import 'bootstrap/dist/css/bootstrap.min.css';
import 'mapbox-gl/dist/mapbox-gl.css';
import 'react-toastify/dist/ReactToastify.css';

/**
 * Our components
 */
// import Pins from './components/map-components/pins';
import CityInfo from './components/map-components/city-info';
import StateSelector from './components/StateSelector';
import LeftSidebar from './components/LeftSidebar';
import { updateStateColors } from './utils';
import Toolbar from './toolbar';

/**
 * Static data files
 */
// import CITIES from './data/cities.json';
import STATES_TOOLTIP_DATA from './data/states_tooltip_data.geojson';
import NY_COUNTY_SHORELINE_DATA from './data/ny_county_shoreline.geojson';
import TESTING_PRECINCT_DATA from './data/GeoJSON_example.geojson';

/**
 * Mapbox Style & API Key
 */
const MAPBOX_STYLE = 'mapbox://styles/shortland/ck6bf8xag0zv81io8o68otfdr';
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
            features: [],
            selectedFeatureId: null,
        };

        this._editorRef = null;
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
            return;
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

    // _updateViewport = viewport => {
    //     this.setState({ viewport });
    // };

    _renderToolbar = () => {
        return (
            <Toolbar
                selectedMode={this.state.selectedMode}
                onSwitchMode={this._switchMode}
                onDelete={this._onDelete}
            />
        );
    };
    // _updateViewport = (viewport) => {
    //     this.setState({ viewport });
    // }

    // _onSelect = ({ selectedFeatureId }) => {
    //     this.setState({ selectedFeatureId });
    // };

    // _onUpdate = features => {
    //     this.setState({
    //         features
    //     });
    // };

    // _switchMode = evt => {
    //     const selectedMode = evt.target.id === this.state.selectedMode ? EditorModes.READ_ONLY : evt.target.id;
    //     this.setState({
    //         selectedMode,
    //         selectedFeatureId: null
    //     });
    // };

    // _renderControlPanel = () => {
    //     return (
    //         <div style={{ position: 'absolute', top: 0, right: 0, maxWidth: '320px' }}>
    //             <select onChange={this._switchMode}>
    //                 <option value="">--Please choose a mode--</option>
    //                 {MODES.map(mode => <option value={mode.id}>{mode.text}</option>)}
    //             </select>
    //         </div>
    //     );
    // }

    // _getEditHandleStyle = ({ feature, featureState, vertexIndex, vertexState }) => {
    //     return {
    //         fill: vertexState === `SELECTED` ? '#000' : '#aaa',
    //         stroke: vertexState === `SELECTED` ? '#000' : 'none'
    //     }
    // }

    // _getFeatureStyle = ({ feature, featureState }) => {
    //     return {
    //         stroke: featureState === `SELECTED` ? '#000' : 'none',
    //         fill: featureState === `SELECTED` ? '#080' : 'none',
    //         fillOpacity: 0.8
    //     }
    // }

    componentDidMount() {
        /**
         * State data
         */
        json(
            STATES_TOOLTIP_DATA,
            (error, response) => {
                if (!error) {
                    this.setState({
                        stateData: updateStateColors(response, f => f.properties.amount_counties),
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
         * example/testing precinct data
         */
        json(
            TESTING_PRECINCT_DATA,
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

        const stateFeature = features && features.find(f => f.layer.id === 'stateData');
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

        const countyFeature = features && features.find(f => f.layer.id === 'countyData');
        if (countyFeature) {
            // if a clicks on a county that was already selected/clicked on
            if (countyFeature.properties.NAME === this.state.selectedFeature.properties.NAME) {
                this._zoomToFeature(event);
                return;
            }

            this.setState({ selectedFeature: countyFeature });
            return;
        }

        const precinctFeature = features && features.find(f => f.layer.id === 'precinctData');
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

        if (feature.layer.id === "stateData" || feature.layer.id === "countyData") {
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
            srcEvent: { offsetX, offsetY },
        } = event;

        const hoveredFeature = features && features.find(f => f.layer.id === 'stateData');
        this.setState({ hoveredFeature, x: offsetX, y: offsetY });

        if (!hoveredFeature) {
            const countyHoveredFeature = features && features.find(f => f.layer.id === 'countyData');
            this.setState({ countyHoveredFeature, x: offsetX, y: offsetY });
        }
    };

    _renderTooltip() {
        const { hoveredFeature, countyHoveredFeature, x, y } = this.state;

        if (hoveredFeature) {
            return (
                hoveredFeature && (
                    <div className="state-tooltip" style={{ left: x, top: y }}>
                        <h5>{hoveredFeature.properties.name}</h5>
                        <div>Counties: {hoveredFeature.properties.amount_counties}</div>
                        {/* <br /> */}
                        {/* <div style={{ "fontStyle": "italic" }}>(click again to enlarge)</div> */}
                    </div>
                )
            );
        }

        if (countyHoveredFeature) {
            return (
                countyHoveredFeature && (
                    <div className="state-tooltip" style={{ left: x, top: y }}>
                        <h5>{countyHoveredFeature.properties.NAME} County</h5>
                        <div>FIPS Code: {countyHoveredFeature.properties.FIPS_CODE}</div>
                        {/* <br /> */}
                        {/* <div style={{ "fontStyle": "italic" }}>(click again to enlarge)</div> */}
                    </div>
                )
            );
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

    _onClickMarker = city => {
        this.setState({ popupInfo: city });
    };

    _renderPopup() {
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
                    <CityInfo info={popupInfo} />
                </Popup>
            )
        );
    }

    render() {
        const { viewport, stateData, countyDataOutline, countyData, precinctData, searchResultLayer, selectedMode } = this.state;

        return (
            <div className="App">
                <Navbar bg="dark" expand="lg" variant="dark">
                    <Navbar.Brand href="#home">Election Data Quality</Navbar.Brand>
                    <Navbar.Toggle aria-controls="basic-navbar-nav" />
                    <Navbar.Collapse id="basic-navbar-nav">
                        <Nav className="mr-auto">
                            <Nav.Link href="#tmp">Something</Nav.Link>
                            <StateSelector
                                select_state={(state_abv) => this.stateSelect.bind(this, state_abv)}
                            />
                        </Nav>
                    </Navbar.Collapse>
                </Navbar>

                <div>
                    <div id="leftCol">
                        <LeftSidebar
                            selected={this.state.selectedFeature}
                        />
                    </div>

                    <MapGL
                        {...viewport}
                        onViewportChange={(viewport => this.setState({ viewport: viewport }))}
                        mapStyle={MAPBOX_STYLE}
                        mapboxApiAccessToken={MAPBOX_API}
                        onHover={this._onHover}
                        onClick={this._onClick}
                        // onDblClick={this._onDblClick}
                        doubleClickZoom={false}
                        ref={this.mapRef}
                    >
                        <Editor
                            ref={_ => (this._editorRef = _)}
                            clickRadius={12}
                            onSelect={selected => {
                                this.setState({ selectedFeatureIndex: selected && selected.selectedFeatureIndex });
                            }}
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

                        {/* For rendering pins over our map */}
                        {/* <Pins data={CITIES} onClick={this._onClickMarker} />*/}
                        {/* {this._renderPopup()} */}

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

                        {/* For rendering (NYS) county colors & tooltips over counties */}
                        <Source type="geojson" data={countyData}>
                            <Layer
                                {...countyDataLayerFillable}
                                minzoom={5}
                                maxzoom={8}
                            />
                        </Source>

                        {/* For rendering (NYS) county data outline */}
                        <Source type="geojson" data={countyDataOutline}>
                            <Layer
                                {...countyDataLayerOutline}
                                minzoom={5}
                            // maxzoom={8}
                            />
                        </Source>

                        {/* For rendering state colors & tooltips over states */}
                        <Source type="geojson" data={stateData}>
                            <Layer
                                {...dataLayer}
                                maxzoom={5}
                            />
                        </Source>

                        {/* PRECINCT EXAMPLE DATA */}
                        <Source type="geojson" data={precinctData}>
                            <Layer
                                {...precinctDataLayerFillable}
                                minzoom={8}
                            />
                        </Source>
                        {this._renderTooltip()}

                        {this._renderToolbar()}
                    </MapGL>
                </div>

                <ToastContainer />
            </div >
        );
    }
}

const precinctDataLayerFillable = {
    id: 'precinctData',
    type: 'fill',
    paint: {
        'fill-color': {
            property: 'percentile',
            stops: [
                [0, '#3288bd'],
                [1, '#66c2a5'],
                [2, '#abdda4'],
                [3, '#e6f598'],
                [4, '#ffffbf'],
                [5, '#fee08b'],
                [6, '#fdae61'],
                [7, '#f46d43'],
                [8, '#d53e4f']
            ]
        },
        'fill-opacity': 0.5,
    },
};

const countyDataLayerFillable = {
    id: 'countyData',
    type: 'fill',
    paint: {
        'fill-color': {
            property: 'percentile',
            stops: [
                [0, '#3288bd'],
                [1, '#66c2a5'],
                [2, '#abdda4'],
                [3, '#e6f598'],
                [4, '#ffffbf'],
                [5, '#fee08b'],
                [6, '#fdae61'],
                [7, '#f46d43'],
                [8, '#d53e4f']
            ]
        },
        'fill-opacity': 0.0,
    },
};

const countyDataLayerOutline = {
    id: 'countyDataOutline',
    type: 'line',
    paint: {
        'line-color': '#000000',
        'line-width': 2,
    },
};

const dataLayer = {
    id: 'stateData',
    type: 'fill',
    paint: {
        'fill-color': {
            property: 'percentile',
            stops: [
                [0, '#3288bd'],
                [1, '#66c2a5'],
                [2, '#abdda4'],
                [3, '#e6f598'],
                [4, '#ffffbf'],
                [5, '#fee08b'],
                [6, '#fdae61'],
                [7, '#f46d43'],
                [8, '#d53e4f']
            ]
        },
        'fill-opacity': 0.8,
    },
};

export function renderToDom(container) {
    render(<App />, container);
}
