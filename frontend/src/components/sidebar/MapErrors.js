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
            selectedErrorType: "All"
        }
    }

    // createErrorList() {
    //     let allError = this.props.allErrors;
    //     let errorList = [];
    //     for (let i in allError) {
    //         errorList.push(<ListGroup.Item onClick={() => this.props.errorListOnClick(allError[i])}> {allError[i].errorType + " " + allError[i].precinctId}</ListGroup.Item >);
    //     }
    //     return errorList;
    // }

    changeErrorType(type) {
        this.setState({
            selectedErrorType: type
        });
    }

    createErrorListByType() {
        let allError = this.props.allErrors;
        let errorList = [];
        let selectedErrorType = this.state.selectedErrorType;
        if (selectedErrorType === "All") {
            for (let i in allError) {
                errorList.push(<ListGroup.Item onClick={() => this.props.errorListOnClick(allError[i])}> {allError[i].errorType + " " + allError[i].precinctId}</ListGroup.Item >);
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
                Errors
                <hr />
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