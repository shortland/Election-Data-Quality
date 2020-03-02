import React, { Component } from 'react';

import InputGroup from 'react-bootstrap/InputGroup';
import FormControl from 'react-bootstrap/FormControl';
import Table from 'react-bootstrap/Table';
import Jumbotron from 'react-bootstrap/Jumbotron';

import ElectionDisplayBar from '../components/ElectionDisplayBar';
import DemographicsTable from '../components/DemographicsTable';

class LeftSidebar extends Component {
    constructor(props) {
        super(props);
    }

    createList = () => {
        let list = [];

        const feature = this.props.selected; //the selected feature

        if (feature) {
            if (feature.properties) {
                const properties = feature.properties;
                for (const p in properties) {
                    list.push(<div>{`${p}: ${properties[p]}`}</div>)
                }
            }
        }
        return list;
    }

    render() {
        const list = this.createList();
        const feature = this.props.selected;

        if (list.length > 0) {
            return (
                <div>
                    {list}
                    <ElectionDisplayBar />
                    <DemographicsTable />
                </div>
            );
        }
        return (
            <div>

            </div>
        );
    }
}

export default LeftSidebar;