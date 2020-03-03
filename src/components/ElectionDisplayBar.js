import React, { Component } from 'react';

import ButtonGroup from 'react-bootstrap/ButtonGroup';
import Button from 'react-bootstrap/Button';


class ElectionDisplayBar extends Component {
    constructor(props) {
        super(props);
        //this.electionResults = { "Clinton": 60, "Trump": 40, "Other": 5 };
        this.state = {
            election: 2016,
            electionResults: { "Dem": 60, "Rep": 40, "Other": 5 }
        }
    }

    changeElection(year) {
        let clinton = Math.random() * 100;
        clinton = Math.round(clinton);
        let trump = 100 - clinton;
        this.setState({ election: year, electionResults: { "Dem": clinton, "Rep": trump } });
    }

    render() {
        const { electionResults } = this.state;

        let keys = Object.keys(electionResults);
        //let values = Object.values(electionResults);

        if (electionResults) {

            return (
                <div>
                    <ButtonGroup size="sm" className="mt-3">
                        <Button variant="outline-primary" id="btn1" className="button" onClick={this.changeElection.bind(this, 2016)}>2016</Button>
                        <Button variant="outline-primary" id="btn2" className="button" onClick={this.changeElection.bind(this, 2012)}>2012</Button>
                        <Button variant="outline-primary" id="btn3" className="button" onClick={this.changeElection.bind(this, 2008)}>2008</Button>
                    </ButtonGroup>
                    <br />
                    <hr />
                    Presidential:
                    <div className="BarDisplay">
                        <div className="BlueBar" style={{ width: electionResults[keys[0]] + '%' }}>
                            {keys[0] + ' ' + electionResults[keys[0]] + '%'}
                        </div>
                        <div className="RedBar" style={{ width: electionResults[keys[1]] + '%' }}>
                            {keys[1] + ' ' + electionResults[keys[1]] + '%'}
                        </div>
                    </div>
                    <br />
                    <hr />
                    Congressional:
                    <div className="BarDisplay">
                        <div className="BlueBar" style={{ width: electionResults[keys[0]] + '%' }}>
                            {keys[0] + ' ' + electionResults[keys[0]] + '%'}
                        </div>
                        <div className="RedBar" style={{ width: electionResults[keys[1]] + '%' }}>
                            {keys[1] + ' ' + electionResults[keys[1]] + '%'}
                        </div>
                    </div>
                </div>
            )
        }
    }
}

export default ElectionDisplayBar;