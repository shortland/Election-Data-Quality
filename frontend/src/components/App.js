import React, { Component } from "react";
import { render } from "react-dom";
import MapGL, {
    Popup,
    NavigationControl,
    FullscreenControl,
    ScaleControl,
    Source,
    Layer,
    LinearInterpolator,
    WebMercatorViewport,
} from "react-map-gl";
import { Nav, Navbar, Form } from "react-bootstrap";
//import { json } from "d3-request";
import bbox from "@turf/bbox";
import { ToastContainer, toast } from "react-toastify";
import { GeoJsonLayer } from "@deck.gl/layers";
import Geocoder from "react-map-gl-geocoder";
import { Editor, EditorModes } from "react-map-gl-draw";

/**
 * JS classes
 */
import AppData from "../data/AppData";

/**
 * CSS Styling
 */
import "../App.css";
import "../styles/Collapsible.css";
import "../styles/PinPopup.css";
import "../static/app.css";
import "react-map-gl-geocoder/dist/mapbox-gl-geocoder.css";
import "bootstrap/dist/css/bootstrap.min.css";
import "mapbox-gl/dist/mapbox-gl.css";
import "react-toastify/dist/ReactToastify.css";

import Card from "react-bootstrap/Card";

/**
 * Map layers
 */
import {
    countyDataLayerFillable,
    //countyDataLayerFillableHighlight,
    countyDataLayerOutline,
} from "../layers/CountyLayer";
import {
    stateLayerFill, stateLayerOutline,
    //stateLayerFillHighlight
} from "../layers/StateLayer";
import {
    precinctLayerFill,
    //precinctLayerFillHighlight,
    precinctLayerOutline,
} from "../layers/PrecinctLayer";
import {
    congressionalLayerFill,
    //congressionalLayerFillHighlight,
    congressionalLayerOutline,
} from "../layers/CongressionalLayer";

/**
 * Our components
 */
import Pins from "./map/ErrorPins";
import ErrorInfo from "./map/ErrorModal";
import StateSelector from "./navbar/StateSelector";
import LeftSidebar from "./sidebar/LeftSidebar";
import Toolbar from "./map/toolbar";
import UserModeSelector from "./navbar/UserModeSelector";

/**
 * Static data files
 */
import ERRORS from "../data/errors.json";
//import STATES_TOOLTIP_DATA from '../data/states_tooltip_data.geojson';
//import NY_COUNTY_SHORELINE_DATA from '../data/ny_county_shoreline.geojson';
//import NY_PRECINCT_DATA from '../data/ny_precincts.geojson';
//import NY_CONGRESSIONAL_DATA from "../data//New_York_Congressional_Districts.GeoJSON";
// import TESTING_PRECINCT_DATA from './data/GeoJSON_example.geojson';

/**
 * Mapbox Style & API Key
 */
const MAPBOX_STYLE = "mapbox://styles/shortland/ck7fn4gmu014c1ip60jlumnm2";
const MAPBOX_API =
    "pk.eyJ1Ijoic2hvcnRsYW5kIiwiYSI6ImNqeXVzOWhsbjBpYzczY29hNGZycTlqdXAifQ.B6l-uEqGG-Pw6-quz4eflQ";

/**
 * Main React component which renders everything
 * @state viewport: the maps viewport (lat, long, etc)
 * @state stateData, countyData, congresionalDistrictData, precinctData: hold data loaded from API
 * @state layers: keeps track of which layers should be on (ex: precincts should be displayed when layers.precincts is true)
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
                height: window.innerHeight,
                latitude: 37.0902,
                longitude: -105.7129,
                zoom: 3.3,
            },
            popupInfo: null,
            selectedFeature: null,
            selectedMode: EditorModes.READ_ONLY,
            // selectedFeatureId: null,
            hoveredFeature: null,
            filter: null,
            shouldShowPins: false,
            features: {},
            selectedFeatureIndex: null,
            userMode: "View",
            layers: {
                states: true,
                counties: true,
                congressional: false,
                precincts: true,
                parks: false,
            },
            precinct_selection_to_edit: false, // this is for determine if user is selecting precincts to edit
            precinct_selected_for_edit: null, // this is the precinct user selected while (precinct_selection_to_edit == true)
        };

        this._editorRef = null;
        this.showErrorPins = this.showErrorPins.bind(this);
        this.appData = new AppData();

        this.filters = {
            countyFilter: ["==", "NAME", ""],
            precinctFilter: ["==", "GEOID10", ""],
            stateFilter: ["==", "name", ""],
            congressionalFilter: ["==", "NAMELSAD", ""],
        };

        this.lastHovered = null; //keeps track of which feature to unhighlight
        this.mapRef = React.createRef();
    }

    /**
     * gets the underlying Mapbox GL JS map
     * be careful calling methods on this map as it may break the React wrapper
     */
    getMap() {
        return this.mapRef.current.getMap()
    }

    handleViewportChange = (viewport) => {
        this.setState({
            viewport: { ...this.state.viewport, ...viewport },
        });
    };

    handleGeocoderViewportChange = (viewport) => {
        const geocoderDefaultOverrides = { transitionDuration: 1000 };

        return this.handleViewportChange({
            ...viewport,
            ...geocoderDefaultOverrides,
        });
    };

    handleOnResult = (event) => {
        console.log(event.result);
        this.setState({
            searchResultLayer: new GeoJsonLayer({
                id: "search-result",
                data: event.result.geometry,
                getFillColor: [255, 0, 0, 128],
                getRadius: 1000,
                pointRadiusMinPixels: 10,
                pointRadiusMaxPixels: 10,
            }),
        });
    };
    //----------------------- React Map GL Draw--------------------//
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
        } else {
            toast.info("Selected precinct is deleted", {
                position: toast.POSITION.BOTTOM_RIGHT,
            });
            let features = this.state.features;
            if (selectedFeatureIndex > -1) {
                features.data.splice(selectedFeatureIndex, 1);
            }
            this.setState({
                selectedFeatureIndex: null,
                selectedMode: EditorModes.READ_ONLY,
                features: features,
            });
        }
        this._editorRef.deleteFeatures(selectedFeatureIndex);
    };

    _switchMode = (evt) => {
        let selectedMode = evt.target.id;
        if (selectedMode === this.state.selectedMode) {
            selectedMode = null;
        }

        this.setState({ selectedMode });
    };

    //Map GL Draw select a feature (a created shape by the MapGLDraw)
    _onSelect = (selected) => {
        this.setState({
            selectedFeatureIndex: selected && selected.selectedFeatureIndex,
        });
    };
    //Map GL Draw update (user draw a new shape)
    _onUpdate = (features) => {
        this.setState({
            features: features,
            selectedMode: EditorModes.READ_ONLY,
            selectedFeatureIndex: null,
        });
    };

    //toobar save button click event
    _onSaveRequest = (toolBarRequest) => {
        if (toolBarRequest) {
            let selected_saved_feature = this.state.features.data[
                this.state.selectedFeatureIndex
            ];
            console.log(selected_saved_feature);
            this.setState({
                selectedMode: EditorModes.READ_ONLY,
                selectedFeatureIndex: null,
            });
            if (selected_saved_feature) {
                toast.info("New precinct is saved", {
                    position: toast.POSITION.BOTTOM_RIGHT,
                });
            } else {
                toast.info("Please select a new created precint to save", {
                    position: toast.POSITION.BOTTOM_RIGHT,
                });
            }
        }
    };

    _renderToolbar = () => {
        const { userMode } = this.state;
        if (userMode === "Edit") {
            // let selectedFeature = this.state.selectedFeature;
            // if (selectedFeature && selectedFeature.properties.type === "Precinct") {
            //     let oldFeatureForEdit = this.state.featureForEditing;
            //     let selectedFeatureId = selectedFeature.id;
            //     let isUpdateStateNeeded = false;
            //     //check if featureForEdit is the same
            //     if (oldFeatureForEdit) {
            //         if (oldFeatureForEdit.id !== selectedFeatureId) {
            //             isUpdateStateNeeded = true;
            //         }
            //     }
            //     else {
            //         isUpdateStateNeeded = true;
            //     }

            //     if (isUpdateStateNeeded) {
            //         this.appData.fetchPrecinctShape(selectedFeatureId).then((data) => {
            //             data.features[0].geometry = {
            //                 type: "Polygon",
            //                 coordinates: data.features[0].geometry.coordinates[0]
            //             }
            //             this.setState({
            //                 featureForEditing: data.features
            //             });
            //         });
            //     }
            // }
            // let featureForEditing = this.state.featureForEditing;
            // let editor = this._editorRef;
            // //we only use one feature
            // if (featureForEditing) {
            //     //check if editor's featureCollection exist or not
            //     if (editor.state.featureCollection) {
            //         if (editor.state.featureCollection.featureCollection.length !== 1) {
            //             editor.state.featureCollection.featureCollection.length = 0;
            //             //editor.addFeatures(featureForEditing);
            //         }
            //     }
            //     else {
            //         editor.addFeatures(featureForEditing);
            //     }
            // }
            // else {
            //     //console.log(editor);
            // }


            return (
                <Toolbar
                    selectedMode={this.state.selectedMode}
                    onSwitchMode={this._switchMode}
                    onDelete={this._onDelete}
                    onSelect={this._onSelect}
                    features={this.state.features}
                    selectedFeature={this.state.selectedFeature}
                    toolBarRequest={this._onSaveRequest}
                    appData={this.appData}
                />
            );
        } else {
            return;
        }
    };

    /**
     * Here can load anything based on the state feature selected
     * such as congressional districts or national parks by state
     * 
     * state.selectedFeature is set within here
     */
    onStateSelected(stateFeature) {
        // const { layers } = this.state;
        console.log(stateFeature.properties)
        let stateID = stateFeature.properties.id;

        this.appData.fetchCongressionalDistrictByState(stateID).then((data) => {
            //console.log(data)
            this.setState({
                congressionalDistrictData: data
            });
        });

        this.appData.fetchCountiesByState(stateID).then((data) => {
            //console.log(data)
            this.setState({
                countyData: data
            });
        });

        this.setState({ selectedFeature: stateFeature })
    }

    /**
     * any operations when a county is selected can go here
     * 
     * sets state.selectedFeature to be the county feature passed
     */
    onCountySelected(countyFeature) {
        //console.log(countyFeature.properties)
        let countyId = countyFeature.properties.id;

        this.appData.fetchShapeOfPrecinctsByCounty(countyId).then((data) => {
            console.log(data)
            this.setState({
                precinctData: data
            });
        });

        this.setState({ selectedFeature: countyFeature });
    }

    /**
     * when detected a precinct is being hovered on
     */
    onPrecinctHover(precinctFeature) {
    }

    /**
     * any operations for when a precinct is selected can go here
     * 
     * sets state.selectedFeature as the precinct passed
     */
    onPrecinctSelected(precinctFeature) {
        let precinctId = precinctFeature.properties.id;
        this.appData.fetchPrecinctInfo(precinctId).then((data) => {
            let oldData = precinctFeature;
            let neighborsStrId = [];
            for (let i in data.neighborsId) {
                neighborsStrId.push(data.neighborsId[i].toString().trim());
            }
            let info = {
                "demographicData": data.demographicData,
                "fullName": data.fullName,
                "id": data.id,
                "isGhost": data.isGhost,
                "neighborsId": neighborsStrId,
                "parentId": data.parentDistrictId,
                "precinctError": data.precinctErrors,
                "votingData": data.votingData
            }
            let newProp = Object.assign(oldData.properties, info);
            oldData.properties = newProp;
            return oldData;
        }).then((updatedPrecinct) => {
            const prevSelected = this.state.selectedFeature;
            this.removeHighlightPrecinctNeighbors(prevSelected);
            this.highlightPrecinctNeighbors(updatedPrecinct);
            this.setState({
                selectedFeature: updatedPrecinct
            });
        });
    }

    _onClick = (event) => {
        //map object
        //const map = this.mapRef.current.getMap();

        // sets the selected feature onclcik, to have properties displayed by LeftSidebar
        const { features } = event;

        if (features) {
            const stateFeature = features.find((f) => f.layer.id === "stateFill");
            const countyFeature = features.find((f) => f.layer.id === "countyFill");
            const congressionalFeature = features.find((f) => f.layer.id === "congressionalFill");
            const precinctFeature = features.find((f) => f.layer.id === "precinctFill");

            const selectedFeature = stateFeature || countyFeature || congressionalFeature || precinctFeature;
            const previouslySelected = this.state.selectedFeature;
            this.removeSelectedHighlight(previouslySelected);
            this.addSelectedHighlight(selectedFeature);

            if (stateFeature) {
                stateFeature.properties.type = "State";
                console.log(stateFeature)
                // if a clicks on a state that was already selected/clicked on
                if (this.state.selectedFeature) {
                    if (stateFeature.properties.name === this.state.selectedFeature.properties.name) {
                        this._zoomToFeature(event);
                        return;
                    }
                } else {
                    toast.info(
                        "Click the same feature (state/county) again to zoom in.",
                        {
                            position: toast.POSITION.BOTTOM_RIGHT,
                        }
                    );
                }

                this.onStateSelected(stateFeature);

            }
            else if (countyFeature) {
                countyFeature.properties.type = "County";
                // if a clicks on a county that was already selected/clicked on
                if (this.state.selectedFeature) {
                    if (countyFeature.properties.name === this.state.selectedFeature.properties.name) {
                        this._zoomToFeature(event);
                        return;
                    }
                }
                this.onCountySelected(countyFeature);
            }
            else if (congressionalFeature) {
                congressionalFeature.properties.type = "Congressional District";
                this.setState({ selectedFeature: congressionalFeature });
            }
            else if (precinctFeature) {
                //check if precinct_selection_to_edit is true
                if (this.state.precinct_selection_to_edit) {
                    //add the selected precinct to precinct_selected_for_edit
                    this.setState({
                        precinct_selected_for_edit: precinctFeature.id
                    });
                    //TODO: enable user to select multiple precicnts so this should also highlight all the selected precincts
                }
                else {
                    precinctFeature.properties.type = "Precinct";
                    this.onPrecinctSelected(precinctFeature);
                }
            }
            else {
                if (previouslySelected.properties.type === "Precinct") {
                    this.removeHighlightPrecinctNeighbors(previouslySelected);
                }
                //this.setState({ selectedFeature: null });
            }
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
            const { longitude, latitude, zoom } = viewport.fitBounds(
                [
                    [minLng, minLat],
                    [maxLng, maxLat],
                ],
                {
                    padding: 40,
                }
            );

            this.setState({
                viewport: {
                    ...this.state.viewport,
                    longitude,
                    latitude,
                    zoom,
                    transitionInterpolator: new LinearInterpolator({
                        around: [event.offsetCenter.x, event.offsetCenter.y],
                    }),
                    transitionDuration: 1000,
                },
            });
            return;
        }
    }

    getFeatureFilter = (feature) => {
        //console.log(feature)
        if (!feature) {
            return null;
        } else if (feature.isState) {
            return ["==", "name", feature.properties.name];
        } else if (feature.isCounty) {
            return ["==", "name", feature.properties.name];
        } else if (feature.isCongressional) {
            return ["==", "NAMELSAD", feature.properties.NAMELSAD];
        } else if (feature.isPrecinct) {
            return ["==", "GEOID10", feature.properties.GEOID10];
        } else {
            return null;
        }
    };

    _onHover = (event) => {
        const map = this.mapRef.current.getMap();

        const {
            features,
            srcEvent: { x, y },
        } = event;

        if (!event.type || !event.features || event.features.length === 0 || event.features[0].source === "composite") {
            const lastHovered = this.state.hoveredFeature;
            if (lastHovered && lastHovered.id) {
                map.setFeatureState(
                    { source: lastHovered.source, id: lastHovered.id },
                    { hover: false }
                );
            }
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
            const stateHovered = features.find((f) => f.layer.id === "stateFill");
            const countyHovered = features.find((f) => f.layer.id === "countyFill");
            const congressionalHovered = features.find((f) => f.layer.id === "congressionalFill");
            const precinctHovered = features.find((f) => f.layer.id === "precinctFill");

            const hovered = stateHovered || countyHovered || congressionalHovered || precinctHovered;
            const lastHovered = this.state.hoveredFeature;

            //console.log(hovered);

            //set highlight on hover
            if (hovered) {
                map.setFeatureState(
                    { source: hovered.source, id: hovered.id },
                    { hover: true }
                );
            }

            if (stateHovered) {
                hovered.isState = true;
            } else if (countyHovered) {
                hovered.isCounty = true;
            } else if (congressionalHovered) {
                hovered.isCongressional = true;
            } else if (precinctHovered) {
                hovered.isPrecinct = true;
                this.onPrecinctHover(precinctHovered)
                //this.highlightPrecinctNeighbors(precinctHovered);
            }

            //if changed hovered features, remove highlight from previous
            if (lastHovered && hovered && hovered.id && lastHovered.id != hovered.id) {
                map.setFeatureState(
                    { source: lastHovered.source, id: lastHovered.id },
                    { hover: false }
                );
            }

            //const filter = this.getFeatureFilter(hovered);

            this.setState({
                hoveredFeature: hovered,
                //filter: filter,
            });
        }
    };

    addHoveredHighlight(feature) {
        const map = this.mapRef.current.getMap();
        if (feature && feature.id && feature.source) {
            map.setFeatureState(
                { source: feature.source, id: feature.id },
                { hover: true }
            );
        }
    }

    addSelectedHighlight(feature) {
        const map = this.mapRef.current.getMap();
        if (feature && feature.id && feature.source) {
            map.setFeatureState(
                { source: feature.source, id: feature.id },
                { selected: true }
            );
        }
    }

    removeSelectedHighlight(feature) {
        const map = this.mapRef.current.getMap();
        if (feature && feature.id && feature.source) {
            map.setFeatureState(
                { source: feature.source, id: feature.id },
                { selected: false }
            );
        }
    }

    /**
     * will set precicnt's neighbors' styling
     * @param {*} precinctFeature the precinct whose neighbors to highlight
     */
    highlightPrecinctNeighbors(precinctFeature) {
        //console.log(precinctFeature);
        const map = this.mapRef.current.getMap()
        const neighbors = precinctFeature.properties.neighborsId || false;
        const source = precinctFeature.source;

        if (neighbors && source) {
            for (let index in neighbors) {
                let Nid = neighbors[index].trim();
                //console.log(Nid);
                map.setFeatureState(
                    { source: source, id: Nid },
                    { neighbor: true }
                );
            }
        }
    }

    /**
     * removes neighbors styling, for when new precinct selected
     * @param {*} precinctFeature the precicnt to remove neighbors' highlighting
     */
    removeHighlightPrecinctNeighbors(precinctFeature) {
        console.log(precinctFeature);
        const map = this.mapRef.current.getMap()
        const neighbors = precinctFeature.properties.neighborsId || false;
        const source = precinctFeature.source;

        if (neighbors && source) {
            for (let index in neighbors) {
                let Nid = neighbors[index].trim();
                //console.log(Nid);
                map.setFeatureState(
                    { source: source, id: Nid },
                    { neighbor: false }
                );
            }
        }
    }

    /**
     * renders the on hover tooltip
     */
    _renderTooltip() {
        const { hoveredFeature, x, y } = this.state;

        if (!hoveredFeature) {
            return;
        } else if (hoveredFeature.isState) {
            const stateHovered = hoveredFeature;
            return (
                hoveredFeature && (
                    <div className="state-tooltip" style={{ left: x, top: y }}>
                        <h5>{stateHovered.properties.name}</h5>
                        <div>
                            Congressional Districts:{" "}
                            {stateHovered.properties.congressional_districts.length}
                        </div>
                        {/* <br /> */}
                        {/* <div style={{ "fontStyle": "italic" }}>(click again to enlarge)</div> */}
                    </div>
                )
            );
        } else if (hoveredFeature.isCounty) {
            const countyHovered = hoveredFeature;
            return (
                countyHovered && (
                    <div className="state-tooltip" style={{ left: x, top: y }}>
                        <h5>{countyHovered.properties.name}</h5>
                        {/* <div>FIPS Code: {countyHovered.properties.FIPS_CODE}</div> */}
                        {/* <br /> */}
                        {/* <div style={{ "fontStyle": "italic" }}>(click again to enlarge)</div> */}
                    </div>
                )
            );
        } else if (hoveredFeature.isCongressional) {
            const congressionalHovered = hoveredFeature;
            return (
                congressionalHovered && (
                    <div className="state-tooltip" style={{ left: x, top: y }}>
                        <h5>{congressionalHovered.properties.name}</h5>
                        <div></div>
                        {/* <br /> */}
                        {/* <div style={{ "fontStyle": "italic" }}>(click again to enlarge)</div> */}
                    </div>
                )
            );
        } else if (hoveredFeature.isPrecinct) {
            const precinctHovered = hoveredFeature;
            return (
                precinctHovered && (
                    <div className="state-tooltip" style={{ left: x, top: y }}>
                        <h6>Precinct id: {precinctHovered.properties.id}</h6>
                        {/* <div>id: {precinctHovered.properties.id}</div> */}
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

        if (userMode !== "Edit") {
            this.setState({
                precinct_selection_to_edit: false,
                precinct_selected_for_edit: null
            });
        }
        else {
            this.setState({
                precinct_selection_to_edit: true
            });
        }
    };

    stateSelect(name) {
        let latitude = 0;
        let longitude = 0;
        let zoom = 6;
        switch (name) {
            case "New York":
            case "NY":
                latitude = 43.2994;
                longitude = -76.2179;
                break;
            case "Wisconsin":
            case "WI":
                latitude = 44.7844;
                longitude = -89.7879;
                break;
            case "Utah":
            case "UT":
                latitude = 39.321;
                longitude = -112.0937;
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
    _onClickError = (error_data) => {
        this.setState({ popupInfo: error_data });
        console.log(error_data);
        this.setState({
            viewport: {
                ...this.state.viewport,
                longitude: error_data.longitude,
                latitude: error_data.latitude,
                transitionDuration: 1000,
                zoom: 11,
            },
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
            this.setState({
                shouldShowPins: false,
            });
        } else {
            this.setState({
                shouldShowPins: true,
            });
        }
    }

    /**
     * This is called after everything is rendered to DOM
     * Good place for API calls
     */
    componentDidMount() {
        /**
         * Get all the states
         */
        this.appData.fetchAllStates().then((data) => {
            this.setState({
                stateData: data.featureCollection,
            });
        });

        console.log(this.mapRef.current.getMap())

        const map = this.mapRef.current.getMap()
        //this.map = this.mapRef.current.getMap()
    }

    /**
     * Render state data
     */
    renderStateLayers() {
        const { layers, stateData, stateFilter } = this.state;

        return (
            <>
                {layers.states && (
                    <Source type="geojson" data={stateData}>
                        {/* <Layer
                            {...stateLayerFillHighlight}
                            filter={stateFilter}
                            maxzoom={5}
                        /> */}
                        <Layer {...stateLayerFill} maxzoom={5.5} />
                        <Layer {...stateLayerOutline} maxzoom={5.5} />
                    </Source>
                )}
            </>
        );
    }

    /**
     * Render congressional data
     */
    renderCongressionalLayers() {
        const { layers, congressionalDistrictData } = this.state;
        // console.log(congressionalDistrictData)
        return (
            <>
                {layers.congressional && (
                    <Source type="geojson" data={congressionalDistrictData}>
                        {/* <Layer
                           {...congressionalLayerFillHighlight}
                            //filter={congressionalFilter}
                            minzoom={5}
                        /> */}
                        <Layer {...congressionalLayerOutline} minzoom={5} />
                        <Layer {...congressionalLayerFill} minzoom={5} />
                    </Source>
                )}
            </>
        );
    }

    renderCountyLayers() {
        const { layers, countyData } = this.state;
        return (
            <>
                {layers.counties && (
                    <Source type="geojson" data={countyData}>
                        {/* <Layer
                            {...countyDataLayerFillableHighlight}
                            filter={this.countyFilter}
                            minzoom={5.5}
                        /> */}
                        <Layer {...countyDataLayerOutline} minzoom={5.5} />
                        <Layer {...countyDataLayerFillable} minzoom={5.5} maxzoom={9} />
                    </Source>
                )}
            </>
        );
    }

    get_leftSideBar_status = (status) => {
        if (status === "precinct_selection_to_edit") {
            this.setState({
                precinct_selection_to_edit: true
            });
        }
        else {
            this.setState({
                precinct_selected_for_edit: null,
                precinct_selection_to_edit: false
            })
        }
        console.log("precinct_selection_to_edit : ", this.state.precinct_selection_to_edit);
    }


    /**
     * Render precinct data
     */
    renderPrecinctLayers() {
        const { layers, precinctData } = this.state;
        //console.log(layers.precincts, precinctData)

        return (
            <>
                {layers.precincts && (
                    <Source type="geojson" data={precinctData}>
                        {/* <Layer
                            {...precinctLayerFillHighlight}
                            // filter={precinctFilter}
                            minzoom={8}
                        /> */}
                        <Layer {...precinctLayerOutline} minzoom={8} />
                        <Layer {...precinctLayerFill} minzoom={8} />
                    </Source>
                )}
            </>
        );
    }

    render() {
        const { viewport, selectedMode, shouldShowPins } = this.state;
        //console.log("testing for leftSideBar\n", this.state.selectedFeature)
        return (
            <div className="App">
                <div className="selectStateItem inlineDivs">
                    <StateSelector
                        select_state={(state_abv) => this.stateSelect.bind(this, state_abv)}
                    />
                </div>
                <div className="toggleModeItem inlineDivs">
                    <UserModeSelector
                        userModeSelect={(selectedMode) => this.userModeSelect(selectedMode)}
                        selectedFeature={this.state.selectedFeature}
                    />
                </div>

                <div>
                    <div id="leftCol">
                        <LeftSidebar
                            selected={this.state.selectedFeature}
                            showErrorPins={this.showErrorPins.bind(this)}
                            userMode={this.state.userMode}
                            leftSideBarStatus={this.get_leftSideBar_status}
                            precinctSelectedForEdit={this.state.precinct_selected_for_edit}
                            appData={this.appData}
                        />
                    </div>

                    <MapGL
                        {...viewport}
                        onViewportChange={(viewport) =>
                            this.setState({ viewport: viewport })
                        }
                        mapStyle={MAPBOX_STYLE}
                        mapboxApiAccessToken={MAPBOX_API}
                        onHover={this._onHover}
                        onClick={this._onClick}
                        doubleClickZoom={false}
                        ref={this.mapRef}
                    >
                        <div
                            id="map-checkboxes"
                            style={{
                                position: "absolute",
                                textAlign: "left",
                                fontSize: "10pt",
                                top: "10%",
                                right: "10px",
                            }}
                        >
                            {this._renderCheckboxes()}
                        </div>

                        <Editor
                            ref={(_) => (this._editorRef = _)}
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

                        {/* {this.renderLayers()} */}

                        {this.renderStateLayers()}

                        {this.renderCongressionalLayers()}

                        {this.renderCountyLayers()}

                        {this.renderPrecinctLayers()}

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
            </div>
        );
    }

    /**
     * toggles whether a layer is displayed
     */
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
        this.setState({ layers: layers });
    };

    /**
     * renders layer selection checkboxes
     */
    _renderCheckboxes = () => {
        return (
            <div>
                <Card
                    border="secondary"
                    style={{ width: "10rem", backgroundColor: "rgba(255,255,255,0.9)" }}
                >
                    <Card.Header>Layers</Card.Header>
                    <Card.Body>
                        <Form>
                            {[
                                "States",
                                "Congressional Districts",
                                "Counties",
                                "Precincts",
                                "National Parks",
                            ].map((name) => (
                                <div key={"checkbox-".concat(name)}>
                                    <Form.Check
                                        label={name}
                                        type={"checkbox"}
                                        defaultChecked={name != "Congressional Districts"
                                            && name != "National Parks" ? true : false}
                                        onChange={this.toggleLayer.bind(this, name)}
                                    />
                                </div>
                            ))}
                        </Form>
                    </Card.Body>
                    <Card.Footer style={{ lineHeight: "1.0" }}>
                        <small className="text-muted">
                            Note: some layers will only show at certain zoom levels
                        </small>
                    </Card.Footer>
                </Card>
            </div>
        );
    };
}

export function renderToDom(container) {
    render(<App />, container);
}
