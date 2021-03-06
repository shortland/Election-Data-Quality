import React, { Component } from 'react';

import ButtonGroup from 'react-bootstrap/ButtonGroup';
import Button from 'react-bootstrap/Button';


class ElectionDisplayBar extends Component {
    constructor(props) {
        super(props);
        this.state = {
            votingData: props.votingData,
            election: 2016,
            presidental: undefined,
            congressional: undefined
        }
    }

    static getDerivedStateFromProps(nextProps, prevState) {
        if (nextProps.votingData !== prevState.votingData) {
            return {
                votingData: nextProps.votingData,
                presidental: undefined,
                congressional: undefined
            };
        }
        else {
            return null;
        }
    }

    getAllElection() {
        let elections = this.state.votingData;
        if (elections === undefined) {
            return undefined;
        }
        else {
            let allElections = Object.keys(elections.electionData);
            if (allElections.length === 0) {
                return undefined;
            }
            else {
                return allElections;
            }
        }
    }

    getResults(year) {
        let elections = this.state.votingData;
        if (elections === undefined) {

        }
        else {
            let allElections = Object.keys(elections.electionData);
            let electionInYear = [];
            for (let i in allElections) {
                if (allElections[i].includes(year.toString())) {
                    electionInYear.push(allElections[i]);
                }
            }

            let ans = {};
            let result = elections.electionData;
            for (let i in electionInYear) {
                ans[electionInYear[i]] = result[electionInYear[i]].resultsByParty;
            }
            return ans;
        }
    }

    changeElection(year) {
        let resultInYear = this.getResults(year);
        let resultInYearKey = Object.keys(resultInYear);
        this.setState({
            election: year,
            presidental: undefined,
            congressional: undefined
        });
        for (let i in resultInYearKey) {
            if (resultInYearKey[i].includes("PRES")) {
                this.setState({
                    presidental: resultInYear[resultInYearKey[i]]
                });
            }
            else if (resultInYearKey[i].includes("CONG"))
                this.setState({
                    congressional: resultInYear[resultInYearKey[i]]
                });
        }
    }

    yearButtons(allElection) {
        if (allElection) {
            let years = [];
            for (let i in allElection) {
                let year = allElection[i].substring(4, 9);
                if (!years.includes(year)) {
                    years.push(year);
                }
            }

            let buttonList = [];
            for (let i in years) {
                buttonList.push(<Button variant="outline-primary" id={"btn" + i.toString()} className="button" onClick={this.changeElection.bind(this, parseInt(years[i]))}>{years[i]}</Button>);
            }
            return buttonList;
        }
        else {
            return undefined;
        }
    }

    render() {
        let allElection = this.getAllElection();
        if (allElection !== undefined && (this.state.presidental === undefined && this.state.congressional === undefined)) {
            this.changeElection(allElection[0].substring(4, 9));
        }

        const { presidental } = this.state;
        const { congressional } = this.state;

        if (presidental !== undefined && congressional !== undefined) {
            return (
                <div>
                    <ButtonGroup size="sm" className="mt-3">
                        {this.yearButtons(allElection)}
                    </ButtonGroup>
                    <br />
                    <hr />
                    Presidential:
                    <div className="BarDisplay">
                        <div className="BlueBar Demographic" style={{ width: presidental["DEMOCRAT"] + '%' }}>
                            {'Democrat\n' + presidental["DEMOCRAT"]}
                        </div>
                        <div className="RedBar Replublican" style={{ width: presidental["REPUBLICAN"] + '%' }}>
                            {'Republican\n' + presidental["REPUBLICAN"]}
                        </div>
                        <div className="GreenBar Libratarian" style={{ width: presidental["LIBRATARIAN"] + '%' }}>
                            {'Libratarian\n' + presidental["LIBRATARIAN"]}
                        </div>
                        <div className="YellowBar Other" style={{ width: presidental["OTHER"] + '%' }}>
                            {'Other\n' + presidental["OTHER"]}
                        </div>
                    </div>
                    <br />
                    <hr />
                    Congressional:
                    <div className="BarDisplay">
                        <div className="BlueBar Demographic" style={{ width: congressional["DEMOCRAT"] + '%' }}>
                            {'Democrat\n' + congressional["DEMOCRAT"]}
                        </div>
                        <div className="RedBar Replublican" style={{ width: congressional["REPUBLICAN"] + '%' }}>
                            {'Republican\n' + congressional["REPUBLICAN"]}
                        </div>
                        <div className="GreenBar Libratarian" style={{ width: congressional["LIBRATARIAN"] + '%' }}>
                            {'Libratarian\n' + congressional["LIBRATARIAN"]}
                        </div>
                        <div className="YellowBar Other" style={{ width: congressional["OTHER"] + '%' }}>
                            {'Other\n' + congressional["OTHER"]}
                        </div>
                    </div>
                    <a className="dataSource" href="https://electionlab.mit.edu/data">source</a>
                </div>
            );
        }
        else if (presidental !== undefined && congressional === undefined) {
            return (
                <div>
                    <ButtonGroup size="sm" className="mt-3">
                        {this.yearButtons(allElection)}
                    </ButtonGroup>
                    <br />
                    <hr />
                    Presidential:
                    <div className="BarDisplay">
                        <div className="BlueBar Demographic" style={{ width: presidental["DEMOCRAT"] + '%' }}>
                            {'Democrat\n' + presidental["DEMOCRAT"]}
                        </div>
                        <div className="RedBar Replublican" style={{ width: presidental["REPUBLICAN"] + '%' }}>
                            {'Republican\n' + presidental["REPUBLICAN"]}
                        </div>
                        <div className="GreenBar Libratarian" style={{ width: presidental["LIBRATARIAN"] + '%' }}>
                            {'Libratarian\n' + presidental["LIBRATARIAN"]}
                        </div>
                        <div className="YellowBar Other" style={{ width: presidental["OTHER"] + '%' }}>
                            {'Other\n' + presidental["OTHER"]}
                        </div>
                    </div>
                    <a className="dataSource" href="https://electionlab.mit.edu/data">source</a>
                </div>
            );
        }
        else if (presidental === undefined && congressional !== undefined) {
            return (
                <div>
                    <ButtonGroup size="sm" className="mt-3">
                        {this.yearButtons(allElection)}
                    </ButtonGroup>
                    <br />
                    <hr />
                    Congressional:
                    <div className="BarDisplay">
                        <div className="BlueBar Demographic" style={{ width: congressional["DEMOCRAT"] + '%' }}>
                            {'Democrat\n' + congressional["DEMOCRAT"]}
                        </div>
                        <div className="RedBar Replublican" style={{ width: congressional["REPUBLICAN"] + '%' }}>
                            {'Republican\n' + congressional["REPUBLICAN"]}
                        </div>
                        <div className="GreenBar Libratarian" style={{ width: congressional["LIBRATARIAN"] + '%' }}>
                            {'Libratarian\n' + congressional["LIBRATARIAN"]}
                        </div>
                        <div className="YellowBar Other" style={{ width: congressional["OTHER"] + '%' }}>
                            {'Other\n' + congressional["OTHER"]}
                        </div>
                    </div>
                    <a className="dataSource" href="https://electionlab.mit.edu/data">source</a>
                </div>
            );
        }
        else {
            return (
                <div>
                    No ElectionData
                </div>
            );
        }
    }
}

export default ElectionDisplayBar;