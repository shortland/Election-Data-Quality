import React, { Component } from "react";

import Dropdown from "react-bootstrap/Dropdown";
import { toast } from "react-toastify";

/**
 * @prop selectedFeature : selectedFeature
 * @prop userModeSelect : call back for selected mode
 */
class UserModeSelector extends Component {
    constructor(props) {
        super(props);
    }

    // NOTE: Change to "toggle mode" which would be a button that shows a toast everytime you toggle mode
    render() {
        let selectedFeatureType = this.props.selectedFeature ? this.props.selectedFeature.properties.type : undefined;
        return (
            <Dropdown>
                <Dropdown.Toggle variant="info" id="userModeSelect">
                    Select Mode
        </Dropdown.Toggle>
                <Dropdown.Menu>
                    <Dropdown.Item onClick={() => this.props.userModeSelect("View")}>
                        View
          </Dropdown.Item>
                    <Dropdown.Item onClick={() => {
                        this.props.userModeSelect("Edit");
                        console.log(selectedFeatureType)
                        if (selectedFeatureType != "Precinct" && selectedFeatureType != undefined) {
                            toast.info("Note: Only can edit precinct", {
                                position: toast.POSITION.BOTTOM_RIGHT,
                            });
                        }
                    }}>
                        Edit
          </Dropdown.Item>
                </Dropdown.Menu>
            </Dropdown>
        );
    }
}

export default UserModeSelector;
