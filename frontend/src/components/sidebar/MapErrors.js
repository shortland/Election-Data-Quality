import React, { Component } from 'react';

import ListGroup from 'react-bootstrap/ListGroup';


/**
 * @props appData : api
 */
class MapErrors extends Component {
    constructor(props) {
        super(props);
        this.state = {
        }
    }

    createErrorList() {
        let allError = this.props.allErrors;
        let errorList = [];
        for (let i in allError) {
            errorList.push(<ListGroup.Item onClick={() => this.props.errorListOnClick(allError[i])}> {allError[i].errorType + " " + allError[i].precinctId}</ListGroup.Item >);
        }
        return errorList;
    }

    render() {
        return (
            <div>
                Errors
                <hr />
                <ListGroup>
                    {this.createErrorList()}
                </ListGroup>
                <hr />
            </div>

        );
    }
}
export default MapErrors;