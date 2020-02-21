import React, {Component} from 'react';
import './App.css';

//import Bootstrap css
import 'bootstrap/dist/css/bootstrap.min.css';

//import Mapbox css
import 'mapbox-gl/dist/mapbox-gl.css'

//import our React components
import StateSelector from './components/StateSelector';

//import React-Mapbox  documanetation here: https://uber.github.io/react-map-gl/docs
import ReactMapGL from 'react-map-gl';
import MapGL, { LinearInterpolator, WebMercatorViewport } from 'react-map-gl';

//import Mapbox GL JS (a little different from React-Mapbox), actually not sure we need this
import Mapbox from 'mapbox-gl';

class App extends Component {
  constructor(props) {
    super(props);
    this.state = {
      //currentState: "NY",
      viewport: {
        width: "95vw",
        height: "95vh",
        latitude: 43.2994,
        longitude: -74.2179,
        zoom: 8
      }
    }
  }

  stateSelect(name){
    var latitude = 0;
    var longitude = 0;
    var zoom = 8;
    switch(name){
      case "NY":
        latitude = 43.2994;
        longitude = -74.2179;
        break;
      case "WI":
        latitude = 43.7844;
        longitude = -88.7879;
        break;
      case "OH":
        latitude = 40.4173;
        longitude = -82.9071;
        break;
    }
    this.setState({
      viewport: {
        ...this.state.viewport,
        longitude,
        latitude,
        zoom
      }
    });
  }
  
  render(){
    const { viewport } = this.state;

    return (
      <div className="App">
        <StateSelector 
          selectNY={() => this.stateSelect.bind(this, "NY")} 
          selectWI={() => this.stateSelect.bind(this, "WI")}
          selectOH={() => this.stateSelect.bind(this, "OH")}
          />
        <ReactMapGL
          {...viewport}
          onViewportChange={(viewport => this.setState({viewport}))}
          mapStyle= "mapbox://styles/reedm121/ck6modif70t8r1ilazl4xfw4c"
          mapboxApiAccessToken="pk.eyJ1IjoicmVlZG0xMjEiLCJhIjoiY2s2bW81dWlnMHJ2djNra2dmbGJvaDByNCJ9.T6GQYdURKxuqQHUTczcZ-g">
        </ReactMapGL>
      
      </div>
    );
  }
}

export default App;
