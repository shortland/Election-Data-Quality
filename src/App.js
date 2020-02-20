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
  
  render(){
    return (
      <div className="App">
        <StateSelector />
        <ReactMapGL
          {...this.state.viewport}
          onViewportChange={(viewport => this.setState({viewport}))}
          mapStyle= "mapbox://styles/reedm121/ck6modif70t8r1ilazl4xfw4c"
          mapboxApiAccessToken="pk.eyJ1IjoicmVlZG0xMjEiLCJhIjoiY2s2bW81dWlnMHJ2djNra2dmbGJvaDByNCJ9.T6GQYdURKxuqQHUTczcZ-g">
        </ReactMapGL>
      
      </div>
    );
  }
}

export default App;
