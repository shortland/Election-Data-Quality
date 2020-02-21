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
                    <Dropdown.Item href="#/NewYork" onClick={this.props.selectNY()}>New York</Dropdown.Item>
                    <Dropdown.Item href="#/Ohio" onClick={this.props.selectOH()}>Ohio</Dropdown.Item>
                    <Dropdown.Item href="#/Wisconsin" onClick={this.props.selectWI()}>Wisconsin</Dropdown.Item>
                </Dropdown.Menu>
            </Dropdown>
        );
    }
}

export default StateSelector;