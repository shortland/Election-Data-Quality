import React, { Component } from 'react';

import InputGroup from 'react-bootstrap/InputGroup';
import FormControl from 'react-bootstrap/FormControl';
import Table from 'react-bootstrap/Table';
import Jumbotron from 'react-bootstrap/Jumbotron';
import Tabs from 'react-bootstrap/Tabs';
import Tab from 'react-bootstrap/Tab';

import ElectionDisplayBar from '../components/ElectionDisplayBar';
import DemographicsTable from '../components/DemographicsTable';
import DataCorrectionPage from '../components/DataCorrectionPage';

class LeftSidebar extends Component {
    constructor(props) {
        super(props);
        this.state = {
            mode: "data_display"
        }
    }

    createList = () => {
        let list = [];

        const feature = this.props.selected; //the selected feature

        if (feature) {
            if (feature.properties) {
                const properties = feature.properties;
                for (const p in properties) {
                    list.push(<div>{`${p}: ${properties[p]}`}</div>)
                }
            }
        }
        return list;
    }

    //new for data correction page
    data_correct_selected() {
        this.setState({ mode: "data_correction" });
    }

    get_data_correction_page_status = (page_status) => {
        if (page_status === "done") {
            this.setState({ mode: "data_display" });
        }
    }

    render() {
        const { mode } = this.state;
        const list = this.createList();
        const feature = this.props.selected;

        if (list.length > 0) {
            if (mode === "data_display") {
                return (
                    <div>
                        <Tabs defaultActiveKey="general_display" id="left_side_bar_data_tabs">
                            <Tab eventKey="general_display" title="General Data">
                                {list}
                            </Tab>
                            <Tab eventKey="demographic_display" title="DemographicsTable">
                                <DemographicsTable />
                            </Tab>
                            <Tab eventKey="election_display" title="ElectionBar">
                                <ElectionDisplayBar />
                            </Tab>
                        </Tabs>
                        <div className="data_correction">
                            <button id="data_correction_btn" onClick={() => this.data_correct_selected()}>Correct Data Request</button>
                        </div>
                    </div >
                );
            }
            else if (mode === "data_correction") {
                return (
                    <div>
                        <div>
                            <DataCorrectionPage callBackLeftSideBar={this.get_data_correction_page_status} />
                        </div >
                    </div>
                );
            }
        }
        return (
            <div>

            </div>
        );
    }
}

export default LeftSidebar;