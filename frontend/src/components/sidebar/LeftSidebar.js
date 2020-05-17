import React, { Component } from 'react';

import Collapsible from 'react-collapsible';

import ElectionDisplayBar from './ElectionDisplayBar';
import DemographicsTable from './DemographicsTable';
import DataCorrectionPage from './DataCorrectionPage';
import Comments from './Comments';
import CommentModal from './CommentModal';
/**
 * Our sidebar component
 * @prop selected: the currently selected map feature
 * @prop showErrorPins: function to show or hide error pins
 * @prop userMode :  current user mode
 */
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
        console.log(this.props.selected);
        const feature = this.props.selected; //the selected feature
        if (feature) {
            const properties = feature.properties;
            for (const p in properties) {
                if (p !== "demographicData" && p !== "precinctError" && p !== "votingData") {
                    list.push(<div>{`${p}: ${properties[p]}`}</div>);
                }
            }
            // list.push(<div>State: {properties['name']}</div>);
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

    // makeHeader() {
    //     const feature = this.props.selected;

    //     if (feature && feature.properties && feature.properties.type) {
    //         //let name = feature.properties.name || feature.properties.NAME || feature.properties.GEOID10 || feature.properties.NAMELSAD
    //         let type = feature.properties.type;
    //     }
    // }

    render() {
        // const { mode } = this.state;
        const { userMode } = this.props;
        const { comment_data } = this.state;
        const list = this.createList();
        let selectedFeatureType = this.props.selected ? this.props.selected.properties.type : undefined;
        if (list.length > 0) {
            if (userMode === "View") {
                return (
                    <div >
                        <h5></h5>
                        <Collapsible trigger="General Info" open={true}>
                            {list}
                        </Collapsible>
                        <Collapsible trigger="Elections">
                            <ElectionDisplayBar
                                electionData={this.props.selected.properties.votingData}
                            />
                        </Collapsible>
                        <Collapsible trigger="Demographics">
                            <DemographicsTable
                                demographicData={this.props.selected.properties.demographicData}
                            />
                        </Collapsible>
                        <Collapsible trigger="Comments">
                            <Comments savedCommentData={comment_data} />
                            <br />
                            <CommentModal savedCommentData={this.get_comments_modal_data} />
                            <br />
                        </Collapsible>
                        <Collapsible trigger="Map Errors">
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
            else if (userMode === "Edit") {
                if (selectedFeatureType === "Precinct") {
                    return (
                        <div >
                            <h5></h5>
                            <Collapsible trigger="General Info" open={true}>
                                {list}
                            </Collapsible>
                            <Collapsible trigger="Modify Data" open={true}>
                                <DataCorrectionPage
                                    data_correction_page_status={this.get_data_correction_page_status}
                                />
                            </Collapsible>
                        </div >
                    );
                }
                else {
                    return (
                        <div >
                            <h5></h5>
                            <Collapsible trigger="General Info" open={true}>
                                {list}
                            </Collapsible>
                        </div >
                    );
                }

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