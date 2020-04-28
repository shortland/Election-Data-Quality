import React, {Component} from 'react';

import Dropdown from 'react-bootstrap/Dropdown';

class StateSelector extends Component{

    render(){
        return(
            <Dropdown>
                <Dropdown.Toggle variant="success" id="dropdown-basic">
                    Select State
                </Dropdown.Toggle>

                <Dropdown.Menu>
                    <Dropdown.Item href="#/NewYork" onClick={this.props.select_state("NY")}>New York</Dropdown.Item>
                    <Dropdown.Item href="#/Utah" onClick={this.props.select_state("UT")}>Utah</Dropdown.Item>
                    <Dropdown.Item href="#/Wisconsin" onClick={this.props.select_state("WI")}>Wisconsin</Dropdown.Item>
                </Dropdown.Menu>
            </Dropdown>
        );
    }
}

export default StateSelector;