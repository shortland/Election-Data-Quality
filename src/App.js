import React, {Component} from 'react';
import './App.css';

//import Bootstrap css
import 'bootstrap/dist/css/bootstrap.min.css';

//import Mapbox css
import 'mapbox-gl/dist/mapbox-gl.css';

//import our React components
import StateSelector from './components/StateSelector';

//import React-Mapbox  documanetation here: https://uber.github.io/react-map-gl/docs
import ReactMapGL from 'react-map-gl';
import MapGL, { LinearInterpolator, WebMercatorViewport } from 'react-map-gl';

//import Mapbox GL JS (a little different from React-Mapbox), actually not sure we need this
import Mapbox from 'mapbox-gl';

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

class App extends Component {
  constructor(props) {
    super(props);
    this.state = {
      viewport: {
        width: "100%",
        height: "95vh",
        latitude: 37.0902,
        longitude: -95.7129,
        zoom: 3.5,
      },
    }
  }

  stateSelect(name){
    let latitude = 0;
    let longitude = 0;
    let zoom;

    switch(name){
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
  
  render(){
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
              <ReactMapGL
                {...this.state.viewport}
                onViewportChange={(viewport => this.setState({viewport: viewport} ))}
                mapStyle= "mapbox://styles/reedm121/ck6modif70t8r1ilazl4xfw4c"
                mapboxApiAccessToken="pk.eyJ1IjoicmVlZG0xMjEiLCJhIjoiY2s2bW81dWlnMHJ2djNra2dmbGJvaDByNCJ9.T6GQYdURKxuqQHUTczcZ-g"
              />
            </Col>
            <Col></Col>
          </Row>
        </div>
      </div>
    );
  }
}

export default App;
