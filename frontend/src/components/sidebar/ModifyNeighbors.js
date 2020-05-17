import React, { Component } from 'react';
import Button from 'react-bootstrap/Button'


/**
 * @props selectedFeature = currentSelectedFeature from Apps
 * @props modify_neighbors_page_status = callback for sending PrecinctSelectionMode for Apps
 * @props precinctSelectedForEdit = a precinct that user selected for editions 
 * @props appData = API
 */
class ModifyNeighbors extends Component {
    constructor(props) {
        super(props);
        this.state = {
            mode: "default"
        }
    }

    getAllNeighbors() {
        let currentFeature = this.props.selectedFeature;
        let neighbors = currentFeature.properties.neighborsId;
        let r = [];
        for (let i in neighbors) {
            r.push(<p>{neighbors[i].trim()}</p>);
        }
        return r;
    }

    switchMode(mode) {
        this.setState({
            mode: mode
        });
        this.props.modify_neighbors_page_status(mode);
    }


    saveAction() {
        let mode = this.state.mode;
        let targetPrecinct = this.props.selectedFeature.id;
        let precinctsForEdit = this.props.precinctSelectedForEdit;
        if (mode === "add") {
            this.setState({
                mode: "default"
            });
            if (precinctsForEdit) {
                this.props.appData.addPrecinctNeighbor(targetPrecinct, precinctsForEdit).then((respond) => {
                    console.log(respond);
                });

            }
            this.props.modify_neighbors_page_status("default");
        }
        else {
            this.setState({
                mode: "default"
            });
            if (precinctsForEdit) {
                this.props.appData.deletePrecinctNeighbor(targetPrecinct, precinctsForEdit).then((respond) => {
                    console.log(respond);
                });
            }
            this.props.modify_neighbors_page_status("default");
        }
    }

    render() {
        this.getAllNeighbors();
        const { mode } = this.state;
        if (mode === "default") {
            return (
                <div>
                    <br />
                    <Button id="addButton" variant="primary" onClick={(evt) => { this.switchMode("add") }}>Add Neighbor</Button>
                    <br />
                    <Button id="deleteButton" variant="danger" onClick={(evt) => { this.switchMode("delete") }}>Delete Neighbor</Button>
                    <br />
                    <hr />
                    <h5>Current Neighbors</h5>
                    <div>{this.getAllNeighbors()}</div>
                    <br />
                </div>
            );
        }
        else if (mode === "add") {
            return (
                <div>
                    <br />
                    <h5>Add Nieghbors</h5>
                    <hr />
                    <p>Please select precincts from the map to add as neighbor</p>
                    <hr />
                    <span>Current Selected Precincts</span>
                    <ul>
                        <li>{this.props.precinctSelectedForEdit}</li>
                    </ul>
                    <hr />
                    <Button id="saveButton_add" variant="success" onClick={(evt) => { this.saveAction() }}>Save Changes</Button>
                    <br />
                </div >
            );
        }
        else if (mode === "delete") {
            return (
                <div>
                    <br />
                    <h5>Delete Nieghbors</h5>
                    <hr />
                    <p>Please select neighbors from the map to delete</p>
                    <hr />
                    <span>Current Selected Precincts</span>
                    <ul>
                        <li>{this.props.precinctSelectedForEdit}</li>
                    </ul>
                    <hr />
                    <Button id="saveButton_del" variant="success" onClick={(evt) => { this.saveAction() }}>Save Changes</Button>
                    <br />
                </div>
            );
        }
    }

}

export default ModifyNeighbors;