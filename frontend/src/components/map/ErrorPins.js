import React, { PureComponent } from 'react';
import { Marker } from 'react-map-gl';
import { precinctLayerFill } from '../../layers/PrecinctLayer';

const ICON = `M20.2,15.7L20.2,15.7c1.1-1.6,1.8-3.6,1.8-5.7c0-5.6-4.5-10-10-10S2,4.5,2,10c0,2,0.6,3.9,1.6,5.4c0,0.1,0.1,0.2,0.2,0.3
  c0,0,0.1,0.1,0.1,0.2c0.2,0.3,0.4,0.6,0.7,0.9c2.6,3.1,7.4,7.6,7.4,7.6s4.8-4.5,7.4-7.5c0.2-0.3,0.5-0.6,0.7-0.9
  C20.1,15.8,20.2,15.8,20.2,15.7z`;

const SIZE = 20;

// Important for perf: the markers never change, avoid rerender when the map viewport changes
/**
 * @props appData : API
 * @props shouldShowPins
 * @props mapRef : map
 * @props allErrors : callback for apps to handle the errors
 */
export default class Pins extends PureComponent {

    // getAllErrors() {
    //     this.props.appData.fetchAllPrecinctError().then((data) => {
    //         console.log(data);
    //         let precinctId = [];
    //         for (let i in data) {
    //             if (data[i].precinctId) {
    //                 precinctId.push(data[i].precinctId);
    //             }
    //         }
    //     });
    // }

    render() {
        //this.getAllErrors();
        const { data, onClick, shouldShowPins } = this.props;
        console.log(shouldShowPins);
        if (shouldShowPins) {
            console.log("should show pins");
            return data.map((city, index) => (
                <Marker key={`marker-${index}`} longitude={city.longitude} latitude={city.latitude}>
                    <svg
                        height={SIZE}
                        viewBox="0 0 24 24"
                        style={{
                            cursor: 'pointer',
                            fill: '#d00',
                            stroke: 'none',
                            transform: `translate(${-SIZE / 2}px,${-SIZE}px)`
                        }}
                        onClick={() => onClick(city)}
                    >
                        <path d={ICON} />
                    </svg>
                </Marker>
            ));
        } else {
            return (<span></span>);
        }

    }
}