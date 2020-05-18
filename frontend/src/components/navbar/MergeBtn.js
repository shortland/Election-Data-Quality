import React, { Component } from "react";

import Button from "react-bootstrap/Button";

/**
 * @prop mergePrecicncts: function to merge the precincts
 * @prop enabled: boolean as to whether merge button enabled
 */
class MergeBtn extends Component {
    render() {
        const { enabled } = this.props;
        const btnSize = "md";
        if (enabled) {
            return (
                <Button variant="success" size={btnSize} onClick={this.props.mergePrecincts}>
                    Merge Precincts
                </Button>
            )
        }
        else {
            return (
                <Button variant="success" size={btnSize} disabled>Merge Precincts</Button>
            )
        }
    }
}

export default MergeBtn;