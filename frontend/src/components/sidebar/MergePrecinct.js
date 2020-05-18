import React, { Component } from 'react';

import Button from 'react-bootstrap/Button'


/**
 * @props selectedFeature = currentSelectedFeature from Apps
 * @props precinctSelectedForEdit = a precinct that user selected for editions 
 * @props appData = API
 */
class MergePrecinct extends Component {
    constructor(props) {
        super(props);
        this.state = {

        }
    }

    mergeAction() {
        let precinctsForEdit = this.props.precinctSelectedForEdit;
        if (precinctsForEdit) {
            alert("Merge" + this.props.selectedFeature + " and " + precinctsForEdit);
            this.props.appData.mergePrecinct(11790, 11791);
        }
    }



    render() {
        return (
            <div>
                <br />
                <p>Please select a precinct on map to merge</p>
                <hr />
                <ul>
                    <li>{this.props.precinctSelectedForEdit}</li>
                </ul>
                <hr />
                <Button id="mergeBtn" variant="success" onClick={(evt) => { this.mergeAction() }}>MergePrecinct</Button>
            </div >
        );
    }
}

export default MergePrecinct;