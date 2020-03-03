import React, { PureComponent } from 'react';

import Table from 'react-bootstrap/Table';

class DemographicsTable extends PureComponent {
    render() {
        return (
            <div>
                <Table striped bordered hover>
                    <thead>
                        <tr>
                            <th>Race</th>
                            <th>Num</th>
                            <th>Percent</th>
                        </tr>
                    </thead>
                    <tbody>
                        <tr>
                            <th>White</th>
                            <td>112</td>
                            <td>0%</td>
                        </tr>
                        <tr>
                            <th>Black</th>
                            <td>55</td>
                            <td>0</td>
                        </tr>
                        <tr>
                            <th>Hispanic</th>
                            <td>86</td>
                            <td>0</td>
                        </tr>
                        <tr>
                            <th>Asian</th>
                            <td>0</td>
                            <td>0</td>
                        </tr>
                        <tr>
                            <th>Other</th>
                            <td>0</td>
                            <td>0</td>
                        </tr>
                    </tbody>
                </Table>
            </div>
        );
    }
}

export default DemographicsTable;