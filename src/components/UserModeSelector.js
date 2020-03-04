import React, { Component } from 'react';

import Dropdown from 'react-bootstrap/Dropdown';

class UserModeSelector extends Component {
    constructor(props) {
        super(props);
    }

    render() {
        return (
            <Dropdown>
                <Dropdown.Toggle variant="success" id="userModeSelect">
                    Switch User Mode
            </Dropdown.Toggle>
                <Dropdown.Menu>
                    <Dropdown.Item onClick={() => this.props.userModeSelect("View")}>View</Dropdown.Item>
                    <Dropdown.Item onClick={() => this.props.userModeSelect("Edit")}>Edit</Dropdown.Item>
                </Dropdown.Menu>
            </Dropdown>
        );
    }
}

export default UserModeSelector;