import React, { Component } from "react";

import Button from "react-bootstrap/Button";
import ButtonGroup from "react-bootstrap/ButtonGroup";

/**
 * @prop addPrecinctNeighbor: function to add precinct neighbor, passed from App.js
 * @prop removePrecinctNeighbor: function to remove precinct neighbor, passed from App.js
 * @prop enabled: boolean as to whether button is enabled
 */
class AddRemNeighborsBtn extends Component {
    render() {
        const { enabled } = this.props;
        const btnSize = "md";
        if (enabled) {
            return (
                <ButtonGroup toggle>
                    <Button variant="warning" size={btnSize}
                        onClick={this.props.addPrecinctNeighbor}>Add Neighborship
                    </Button>
                    <Button variant="danger" size={btnSize}
                        onClick={this.props.removePrecinctNeighbor}>Remove Neighborship
                    </Button>
                </ButtonGroup>
            )
        }
        else {
            return (<></>)
        }
    }
}

export default AddRemNeighborsBtn;