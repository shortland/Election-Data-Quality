import React, { Component } from "react";

import Dropdown from "react-bootstrap/Dropdown";

class UserModeSelector extends Component {
  constructor(props) {
    super(props);
  }

  // NOTE: Change to "toggle mode" which would be a button that shows a toast everytime you toggle mode
  render() {
    return (
      <Dropdown>
        <Dropdown.Toggle variant="info" id="userModeSelect">
          Select Mode
        </Dropdown.Toggle>
        <Dropdown.Menu>
          <Dropdown.Item onClick={() => this.props.userModeSelect("View")}>
            View
          </Dropdown.Item>
          <Dropdown.Item onClick={() => this.props.userModeSelect("Edit")}>
            Edit
          </Dropdown.Item>
        </Dropdown.Menu>
      </Dropdown>
    );
  }
}

export default UserModeSelector;
