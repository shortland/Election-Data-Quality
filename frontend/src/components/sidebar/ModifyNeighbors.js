import React, { Component } from 'react';
import Dropdown from 'react-bootstrap/Dropdown';
class ModifyNeighbors extends Component {
    constructor(props) {
        super(props);
        this.state = {

        }
    }

    getAllNeighbors() {
        let currentFeature = this.props.selectedFeature;
        let neighbors = currentFeature.properties.neighborsId;
        let r = [];
        for (let i in neighbors) {
            r.push(<span>{neighbors[i].trim()}</span>);
        }
        console.log(r);
        return r;
    }

    render() {
        this.getAllNeighbors();
        return (
            <div>
                <br />
                <Dropdown>
                    <Dropdown.Toggle variant="success" id="dropdown-basic">
                        Select Action
                    </Dropdown.Toggle>
                    <Dropdown.Menu>
                        <Dropdown.Item>Add</Dropdown.Item>
                        <Dropdown.Item>Delete</Dropdown.Item>
                    </Dropdown.Menu>
                </Dropdown>
                <br />
                <hr />
                <h5>Current Neighbors</h5>
                <div>{this.getAllNeighbors}</div>
                <br />
                <hr />
            </div>
        );
    }

}

export default ModifyNeighbors;