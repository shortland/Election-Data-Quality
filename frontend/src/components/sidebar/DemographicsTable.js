import React, { PureComponent } from 'react';

import Table from 'react-bootstrap/Table';

/**
 * @prop demogrpahicData: demographicData
 * 
 */
class DemographicsTable extends PureComponent {
    constructor(props) {
        super(props);
        this.state = {
            "total": 0
        }
    }

    calculateTotal() {
        if (this.props.demographicData) {
            let total = 0;
            let value = this.props.demographicData.demographic;
            for (let i in value) {
                total += value[i];
            }
            this.setState({
                "total": total
            });
        }
    }

    getPopulation(race) {
        if (this.props.demographicData) {
            let demographic = this.props.demographicData.demographic;
            return demographic[race];
        }
        else {
            return 0;
        }

    }

    getPercentage(race) {
        if (this.props.demographicData) {
            let demographic = this.props.demographicData.demographic;
            return demographic[race] / this.state.total;
        }
        else {
            return 0;
        }

    }


    render() {
        this.calculateTotal();
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
                            <td>{this.getPopulation("WHITE")}</td>
                            <td>{this.getPercentage("WHITE")}</td>
                        </tr>
                        <tr>
                            <th>Black</th>
                            <td>{this.getPopulation("BLACK")}</td>
                            <td>{this.getPercentage("BLACK")}</td>
                        </tr>
                        <tr>
                            <th>Native_American</th>
                            <td>{this.getPopulation("NATIVE_AMERICAN")}</td>
                            <td>{this.getPercentage("NATIVE_AMERICAN")}</td>
                        </tr>
                        <tr>
                            <th>Asian</th>
                            <td>{this.getPopulation("ASIAN")}</td>
                            <td>{this.getPercentage("ASIAN")}</td>
                        </tr>
                        <tr>
                            <th>Native_Hawaiian</th>
                            <td>{this.getPopulation("NATIVE_HAWAIIAN")}</td>
                            <td>{this.getPercentage("NATIVE_HAWAIIAN")}</td>
                        </tr>
                        <tr>
                            <th>Other</th>
                            <td>{this.getPopulation("OTHER")}</td>
                            <td>{this.getPercentage("OTHER")}</td>
                        </tr>
                    </tbody>
                    <tfoot>
                        <tr>
                            <th colSpan="2">Total Population</th>
                            <td>{this.state.total}</td>
                        </tr>
                    </tfoot>
                </Table>
                <a className="dataSource" href="https://electionlab.mit.edu/data">source</a>
            </div>
        );
    }
}

export default DemographicsTable;