import React, { Component } from 'react';

class ElectionDisplayBar extends Component {
    constructor(props) {
        super(props);
        this.electionResults = { "Clinton": 60, "Trump": 40, "Other": 5 };
    }

    render() {
        const electionResults = this.electionResults;

        let keys = Object.keys(electionResults);
        //let values = Object.values(electionResults);

        if (electionResults) {
            return (
                <div>
                    <h3>2016 Election Results:</h3>
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