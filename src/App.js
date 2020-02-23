import React, { Component } from 'react';
import { render } from 'react-dom';
import MapGL, { Popup, NavigationControl, FullscreenControl, ScaleControl, Source, Layer, LinearInterpolator, WebMercatorViewport } from 'react-map-gl';
import { Row, Col, Nav, Navbar, Form, FormControl, Button } from 'react-bootstrap';
import { json } from 'd3-request';
import bbox from '@turf/bbox';

/**
 * CSS Styling
 */
import './App.css';
import 'bootstrap/dist/css/bootstrap.min.css';
import 'mapbox-gl/dist/mapbox-gl.css';

/**
 * Our components
 */
import Pins from './components/map-components/pins';
import CityInfo from './components/map-components/city-info';
import StateSelector from './components/StateSelector';
import { updateStateColors } from './utils';

/**
 * Static data files
 */
import CITIES from './data/cities.json';
import STATES_TOOLTIP_DATA from './data/states_tooltip_data.geojson';

/**
 * Mapbox Style & API Key
 */
const MAPBOX_STYLE = 'mapbox://styles/shortland/ck6bf8xag0zv81io8o68otfdr/draft';
const MAPBOX_API = 'pk.eyJ1Ijoic2hvcnRsYW5kIiwiYSI6ImNqeXVzOWhsbjBpYzczY29hNGZycTlqdXAifQ.B6l-uEqGG-Pw6-quz4eflQ';

export default class App extends Component {
  constructor(props) {
    super(props);
    this.state = {
      data: null,
      hoveredFeature: null,
      viewport: {
        width: "100%",
        height: "90vh",
        latitude: 37.0902,
        longitude: -95.7129,
        zoom: 3.3,
      },
      popupInfo: null,
    };
  }

  componentDidMount() {
    json(
      STATES_TOOLTIP_DATA,
      (error, response) => {
        if (!error) {
          this._loadData(response);
        }
      }
    );
  }

  _loadData = data => {
    this.setState({
      data: updateStateColors(data, f => f.properties.amount_counties)
    });
  };

  _onClick = event => {
    const feature = event.features[0];
   
    if (feature.source === "jsx-source-0") {
      // calculate the bounding box of the feature
      const [minLng, minLat, maxLng, maxLat] = bbox(feature);
      // construct a viewport instance from the current state
      const viewport = new WebMercatorViewport(this.state.viewport);
      const {longitude, latitude, zoom} = viewport.fitBounds([[minLng, minLat], [maxLng, maxLat]], {
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
    }
  };

  _onHover = event => {
    const {
      features,
      srcEvent: {offsetX, offsetY},
    } = event;

    const hoveredFeature = features && features.find(f => f.layer.id === 'data');

    this.setState({hoveredFeature, x: offsetX, y: offsetY});
  };

  _renderTooltip() {
    const {hoveredFeature, x, y} = this.state;

    return (
      hoveredFeature && (
        <div className="state-tooltip" style={{left: x, top: y}}>
          <h5>{hoveredFeature.properties.name}</h5>
          <div>Counties: {hoveredFeature.properties.amount_counties}</div>
          <br/>
          <div style={{"fontStyle": "italic"}}>(click to enlarge)</div>
        </div>
      )
    );
  }

  stateSelect(name) {
    let latitude = 0;
    let longitude = 0;
    let zoom;

    switch (name) {
      case "New York":
      case "NY":
        latitude = 43.2994;
        longitude = -74.2179;
        zoom = 6;
        break;
      case "Wisconsin":
      case "WI":
        latitude = 43.7844;
        longitude = -88.7879;
        zoom = 6;
        break;
      case "Utah":
      case "UT":
        latitude = 39.3210;
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
    const {viewport, data} = this.state;

    return (
      <div className="App">
        <Navbar bg="dark" expand="lg" variant="dark">
          <Navbar.Brand href="#home">Map Data Viewer</Navbar.Brand>
          <Navbar.Toggle aria-controls="basic-navbar-nav" />
          <Navbar.Collapse id="basic-navbar-nav">
            <Nav className="mr-auto">
              <Nav.Link href="#tmp">Something</Nav.Link>
              <StateSelector
                select_state={(state_abv) => this.stateSelect.bind(this, state_abv)}
              />
            </Nav>
            <Form inline>
              <FormControl type="text" placeholder="Search" className="mr-sm-2" />
              <Button variant="outline-success">Search</Button>
            </Form>
          </Navbar.Collapse>
        </Navbar>

        <div>
          <Row>
            <Col></Col>
            <Col xs={8}>
              <MapGL
                {...viewport}
                onViewportChange={(viewport => this.setState({ viewport: viewport }))}
                mapStyle={MAPBOX_STYLE}
                mapboxApiAccessToken={MAPBOX_API}
                onHover={this._onHover}
                onClick={this._onClick}
              >
                
                {/* For rendering pins over our map */}
                {/* <Pins data={CITIES} onClick={this._onClickMarker} />
                {this._renderPopup()} */}

                {/* For rendering the controls at top left of the map */}
                <div className="FullScreenController">
                  <FullscreenControl />
                </div>
                <div className="NavigationController">
                  <NavigationControl />
                </div>
                <div className="ScaleController">
                  <ScaleControl />
                </div>
                
                {/* For rendering colors & tooltips over our states */}
                <Source type="geojson" data={data}>
                  <Layer 
                    {...dataLayer} 
                    maxzoom={5} 
                  />
                </Source>
                {this._renderTooltip()}
              </MapGL>
            </Col>
            <Col></Col>
          </Row>
        </div>
      </div>
    );
  }
}

const dataLayer = {
  id: 'data',
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
