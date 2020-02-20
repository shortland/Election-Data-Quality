import React, {Component} from 'react';

import Dropdown from 'react-bootstrap/Dropdown';

class StateSelector extends Component{

    render(){
        return(
            <Dropdown>
                <Dropdown.Toggle variant="success" id="dropdown-basic">
                    State
                </Dropdown.Toggle>

                <Dropdown.Menu>
                    <Dropdown.Item href="#/action-1">New York</Dropdown.Item>
                    <Dropdown.Item href="#/action-2">Ohio</Dropdown.Item>
                    <Dropdown.Item href="#/action-3">Wisconsin</Dropdown.Item>
                </Dropdown.Menu>
            </Dropdown>
        );
    }
}

export default StateSelector;