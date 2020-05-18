import React, { Component } from "react";

import Button from "react-bootstrap/Button";

/**
 * @prop mergePrecicncts: function to merge the precincts
 * @prop enabled: boolean as to whether merge button enabled
 */
class MergeBtn extends Component {

    render() {
        const { enabled } = this.props;
        if (enabled) {
            return (
                <Button onClick={this.props.mergePrecincts}>
                    Merge
                </Button>
            )
        }
        else {
            return (
                <Button disabled>Merge</Button>
            )
        }
    }
}

export default MergeBtn;