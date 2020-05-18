import React, { Component } from 'react';

import ListGroup from 'react-bootstrap/ListGroup';
import DropdownButton from 'react-bootstrap/DropdownButton';
import Dropdown from 'react-bootstrap/Dropdown';


/**
 * @props appData : api
 */
class MapErrors extends Component {
    constructor(props) {
        super(props);
        this.state = {
            selectedErrorType: "All",
            selectedFeature: null
        }
    }

    static getDerivedStateFromProps(nextProps, prevState) {
        if (nextProps.selectedFeature !== prevState.selectedFeature) {
            return ({
                selectedFeature: nextProps.selectedFeature
            });
        }
    }

    changeErrorType(type) {
        this.setState({
            selectedErrorType: type
        });
    }

    getErrorInState(stateId) {
        let ny = 36;
        let wn = 55;
        let utah = 49;
        let allError = this.props.allErrors;
        let filteredErrors = [];
        for (let i in allError) {
            if (allError[i].precinctId.slice(0, 2) === stateId) {
                filteredErrors.push(allError[i]);
            }
        }
        return filteredErrors;
    }

    createErrorListByType() {
        let selectedFeature = this.props.selectedFeature;
        let allError = [];
        if (selectedFeature) {
            if (selectedFeature.properties.type === "Precinct") {
                //console.log(selectedFeature);
                if (selectedFeature.properties.precinctError) {
                    for (let i in selectedFeature.properties.precinctError) {
                        console.log(i);
                        allError.push(selectedFeature.properties.precinctError[i]);
                    }
                }
                console.log(allError)
            }
            else {
                let stateId = selectedFeature.id.toString().slice(0, 2);
                allError = this.getErrorInState(stateId);
            }
        }
        let errorList = [];
        let selectedErrorType = this.state.selectedErrorType;
        if (selectedErrorType === "All") {
            for (let i in allError) {
                errorList.push(<ListGroup.Item onClick={() => this.props.errorListOnClick(allError[i])}> {allError[i].precinctId ? allError[i].errorType + " " + allError[i].precinctId : allError[i].errorType + " \nResolve:" + allError[i].resolved}</ListGroup.Item >);
            }
        }
        else if (selectedErrorType === "NO_VOTER") {
            for (let i in allError) {
                if (allError[i].errorType === "NO_VOTER") {
                    errorList.push(<ListGroup.Item onClick={() => this.props.errorListOnClick(allError[i])}> {allError[i].errorType + " " + allError[i].precinctId}</ListGroup.Item >);
                }
            }
        }
        else if (selectedErrorType === "NO_DEMOGRAPIHIC") {
            for (let i in allError) {
                if (allError[i].errorType === "NO_DEMOGRAPIHIC") {
                    errorList.push(<ListGroup.Item onClick={() => this.props.errorListOnClick(allError[i])}> {allError[i].errorType + " " + allError[i].precinctId}</ListGroup.Item >);

                }
            }
        }
        else if (selectedErrorType === "GHOST") {
            for (let i in allError) {
                if (allError[i].errorType === "GHOST") {
                    errorList.push(<ListGroup.Item onClick={() => this.props.errorListOnClick(allError[i])}> {allError[i].errorType + " " + allError[i].precinctId}</ListGroup.Item >);
                }
            }
        }
        else if (selectedErrorType === "OVERLAP") {
            for (let i in allError) {
                if (allError[i].errorType === "OVERLAP") {
                    errorList.push(<ListGroup.Item onClick={() => this.props.errorListOnClick(allError[i])}> {allError[i].errorType + " " + allError[i].precinctId}</ListGroup.Item >);
                }
            }
        }
        else if (selectedErrorType === "INTERSECTING") {
            for (let i in allError) {
                if (allError[i].errorType === "INTERSECTING") {
                    errorList.push(<ListGroup.Item onClick={() => this.props.errorListOnClick(allError[i])}> {allError[i].errorType + " " + allError[i].precinctId}</ListGroup.Item >);
                }
            }
        }
        return errorList;
    }

    render() {
        return (
            <div>
                <br />
                <DropdownButton title={this.state.selectedErrorType}>
                    <Dropdown.Item as="button" onClick={() => this.changeErrorType("All")}>All</Dropdown.Item>
                    <Dropdown.Item as="button" onClick={() => this.changeErrorType("NO_VOTER")} > No voters</Dropdown.Item>
                    <Dropdown.Item as="button" onClick={() => this.changeErrorType("NO_DEMOGRAPIHIC")} > No demographic</Dropdown.Item>
                    <Dropdown.Item as="button" onClick={() => this.changeErrorType("GHOST")}> Ghost</Dropdown.Item>
                    <Dropdown.Item as="button" onClick={() => this.changeErrorType("OVERLAP")} > Overlap</Dropdown.Item>
                    <Dropdown.Item as="button" onClick={() => this.changeErrorType("INTERSECTING")}> Intersecting</Dropdown.Item>
                </DropdownButton>
                <ListGroup>
                    {this.createErrorListByType()}
                </ListGroup>
                <hr />
            </div >

        );
    }
}
export default MapErrors;