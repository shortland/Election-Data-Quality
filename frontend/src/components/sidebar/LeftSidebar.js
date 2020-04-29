import React, { Component } from 'react';

import Collapsible from 'react-collapsible';

import ElectionDisplayBar from './ElectionDisplayBar';
import DemographicsTable from './DemographicsTable';
import DataCorrectionPage from './DataCorrectionPage';
import Comments from './Comments';
import CommentModal from './CommentModal';

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
    }

    _handleClick = event => {
        this.props.showErrorPins();
    }

    makeHeader(){
        const feature = this.props.selected;

        if(feature && feature.properties && feature.properties.type){
            //let name = feature.properties.name || feature.properties.NAME || feature.properties.GEOID10 || feature.properties.NAMELSAD
            let type = feature.properties.type;
        }
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
                        <h5></h5>
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
                        <Collapsible trigger="View Map Errors">
                            <button className="Extra-Large-Button" onClick={this._handleClick}>View All</button>
                            <button className="Extra-Large-Button" onClick={this._handleClick}>View Self Intersecting Boundaries</button>
                            <button className="Extra-Large-Button" onClick={this._handleClick}>View Open Borders</button>
                            <button className="Extra-Large-Button" onClick={this._handleClick}>View Gaps in Precinct Coverage</button>
                            <button className="Extra-Large-Button" onClick={this._handleClick}>View Precinct Multipolygon</button>
                            <button className="Extra-Large-Button" onClick={this._handleClick}>View Overlapping Precincts</button>
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