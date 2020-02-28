import React, { Component } from 'react';

import InputGroup from 'react-bootstrap/InputGroup';
import FormControl from 'react-bootstrap/FormControl';
import Table from 'react-bootstrap/Table';

class LeftSidebar extends Component{

    render(){
        return(
            <div>
                <InputGroup className="mb-3">
                    <InputGroup.Prepend>
                        <InputGroup.Text id="basic-addon1">Name</InputGroup.Text>
                    </InputGroup.Prepend>
                    <FormControl
                        placeholder="Precinct Name"
                        defaultValue="heelloooo"
                        aria-label="Username"
                        aria-describedby="basic-addon1"
                    />
                </InputGroup>

                <Table striped bordered hover>
                    <thead>
                        <tr>
                            <th>Race</th>
                            <th>Population</th>
                            <th>Percentage</th>
                        </tr>
                    </thead>
                    <tbody>
                        <tr>
                            <td>White</td>
                            <td>num</td>
                            <td>%</td>
                            
                        </tr>
                        <tr>
                            <td>Hispanic</td>
                            <td>num</td>
                            <td>%</td>
                            
                        </tr>
                        <tr>
                            <td>Black</td>
                            <td>num</td>
                            <td>%</td>
                        </tr>
                    </tbody>
                </Table>
            </div>
        )
    }
}

export default LeftSidebar;