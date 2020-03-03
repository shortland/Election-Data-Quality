import React, { Component } from 'react';

// import InputGroup from 'react-bootstrap/InputGroup';
// import FormControl from 'react-bootstrap/FormControl';
// import Table from 'react-bootstrap/Table';
// import Jumbotron from 'react-bootstrap/Jumbotron';
// import Tabs from 'react-bootstrap/Tabs';
// import Tab from 'react-bootstrap/Tab';
// import Button from 'react-bootstrap/Tab';
import Collapsible from 'react-collapsible';

import ElectionDisplayBar from '../components/ElectionDisplayBar';
import DemographicsTable from '../components/DemographicsTable';
import DataCorrectionPage from '../components/DataCorrectionPage';
import Comments from '../components/Comments';
import CommentModal from '../components/CommentModal';

class LeftSidebar extends Component {
    constructor(props) {
        super(props);
        this.state = {
            mode: "data_display",
            comment_data: undefined
        }
    }

    createList = () => {
        let list = [];

        const feature = this.props.selected; //the selected feature

        if (feature) {
            if (feature.properties) {
                const properties = feature.properties;
                for (const p in properties) {
                    list.push(<div>{`${p}: ${properties[p]}`}</div>);
                }
                // list.push(<div>State: {properties['name']}</div>);
            }
        }
        return list;
    }

    //For data correction page
    // data_correct_selected() {
    //     this.setState({ mode: "data_correction" });
    // }

    //For data correction page to return page_status
    get_data_correction_page_status = (page_status) => {
        if (page_status === "done") {
            this.setState({ mode: "data_display" });
        }
    }

    get_comments_modal_data = (comment_modal_data) => {
        if (comment_modal_data) {
            this.setState({ comment_data: comment_modal_data });
        }
        console.log(this.state);
    }

    render() {
        const { mode } = this.state;
        const { comment_data } = this.state;
        const list = this.createList();
        // const feature = this.props.selected;

        if (list.length > 0) {
            if (mode === "data_display") {
                return (
                    <div >
                        <Collapsible trigger="View General Info" open={true}>
                            {list}
                        </Collapsible>
                        <Collapsible trigger="View Elections">
                            <ElectionDisplayBar />
                        </Collapsible>
                        <Collapsible trigger="View Demographics">
                            <DemographicsTable />
                        </Collapsible>
                        <Collapsible trigger="Modify Data">
                            <DataCorrectionPage data_correction_page_status={this.get_data_correction_page_status} />
                        </Collapsible>
                        <Collapsible trigger="View Comments">
                            <Comments savedCommentData={comment_data} />
                            <br />
                            <CommentModal savedCommentData={this.get_comments_modal_data} />
                            <br />
                        </Collapsible>
                    </div >
                );
            }
        }
        return (
            <div>
                Click on a highlighted state to view details
            </div>
        );
    }
}

export default LeftSidebar;