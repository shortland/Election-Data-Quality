import React, { Component } from 'react';

import InputGroup from 'react-bootstrap/InputGroup';
import FormControl from 'react-bootstrap/FormControl';
import Table from 'react-bootstrap/Table';
import Jumbotron from 'react-bootstrap/Jumbotron';

class LeftSidebar extends Component{
    constructor(props){
        super(props);

    }

    createList = () => {
        let list = [];

        const feature = this.props.selected; //the selected feature

        if (feature) {
            if (feature.properties) {
                const properties = feature.properties;
                for (const p in properties){
                    list.push(<div>{`${p}: ${properties[p]}`}</div>)
                }
            }
        }
        return list;
    }

    render(){
       return(
           <div>
           {this.createList()}
           </div>
       );
    }
}

export default LeftSidebar;