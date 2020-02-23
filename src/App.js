import React, { Component } from 'react';
import './App.css';

//import Bootstrap css
import 'bootstrap/dist/css/bootstrap.min.css';

//import Mapbox css
import 'mapbox-gl/dist/mapbox-gl.css';

//import our React components
import StateSelector from './components/StateSelector';

//import React-Mapbox  documanetation here: https://uber.github.io/react-map-gl/docs
// import ReactMapGL from 'react-map-gl';
import MapGL, { Popup, NavigationControl, FullscreenControl, ScaleControl } from 'react-map-gl';

import Pins from './components/map-components/pins';
import CityInfo from './components/map-components/city-info';

import CITIES from './data/cities.json';

//import Mapbox GL JS (a little different from React-Mapbox), actually not sure we need this
// import Mapbox from 'mapbox-gl';

/**
 * bootstrap stuff
 */
import Row from 'react-bootstrap/Row';
import Col from 'react-bootstrap/Col';
import Navbar from 'react-bootstrap/Navbar';
import Nav from 'react-bootstrap/Nav';
import NavDropdown from 'react-bootstrap/NavDropdown';
import Form from 'react-bootstrap/Form';
import FormControl from 'react-bootstrap/FormControl';
import Button from 'react-bootstrap/Button';

const fullscreenControlStyle = {
  position: 'absolute',
  top: 0,
  left: 0,
  padding: '10px'
};

const navStyle = {

};

const scaleControlStyle = {
  position: 'absolute',
  bottom: 36,
  left: 0,
  padding: '10px'
};


class App extends Component {
  constructor(props) {
    super(props);
    this.state = {
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

  stateSelect(name) {
    let latitude = 0;
    let longitude = 0;
    let zoom;

    switch (name) {
      case "NY":
        latitude = 43.2994;
        longitude = -74.2179;
        zoom = 6;
        break;
      case "WI":
        latitude = 43.7844;
        longitude = -88.7879;
        zoom = 6;
        break;
      case "UT":
        latitude = 39.3210;
        longitude = -111.0937;
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
                {...this.state.viewport}
                onViewportChange={(viewport => this.setState({ viewport: viewport }))}
                mapStyle="mapbox://styles/shortland/ck6bf8xag0zv81io8o68otfdr/draft"
                mapboxApiAccessToken="pk.eyJ1Ijoic2hvcnRsYW5kIiwiYSI6ImNqeXVzOWhsbjBpYzczY29hNGZycTlqdXAifQ.B6l-uEqGG-Pw6-quz4eflQ">

                <Pins data={CITIES} onClick={this._onClickMarker} />

                {this._renderPopup()}

                <div style={fullscreenControlStyle}>
                  <FullscreenControl />
                </div>

                <div style={navStyle}>
                  <NavigationControl />
                </div>

                <div style={scaleControlStyle}>
                  <ScaleControl />
                </div>

              </MapGL>
            </Col>
            <Col></Col>
          </Row>
        </div>

      </div>
    );
  }
}

export default App;
