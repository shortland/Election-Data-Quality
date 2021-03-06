import React, { Component } from "react";

import Collapsible from "react-collapsible";

import ElectionDisplayBar from "./ElectionDisplayBar";
import DemographicsTable from "./DemographicsTable";
import DataCorrectionPage from "./DataCorrectionPage";
import ModifyNeighbors from "./ModifyNeighbors";
import Comments from "./Comments";
import CommentModal from "./CommentModal";
import MergePrecinct from "./MergePrecinct";
import MapErrors from "./MapErrors";
/**
 * Our sidebar component
 * @props selected: the currently selected map feature
 * @props showErrorPins: function to show or hide error pins
 * @props userMode :  current user mode
 * @props leftSideBarStatus : for returning status of the leftSideBar
 * @props precinctSelectedForEdit : a precinct that user selected for editions
 * @props appData : API
 */
class LeftSidebar extends Component {
  constructor(props) {
    super(props);
    this.state = {
      mode: "data_display",
      comment_data: undefined,
    };
  }

  createList = () => {
    let list = [];
    //console.log(this.props.selected);
    const feature = this.props.selected; //the selected feature
    if (feature) {
      const properties = feature.properties;
      for (const p in properties) {
        if (
          p !== "demographicData" &&
          p !== "precinctError" &&
          p !== "votingData"
        ) {
          list.push(
            <div className="text-left">{`${p}: ${properties[p]}`}</div>
          );
        }
      }
      // list.push(<div>State: {properties['name']}</div>);
    }
    return list;
  };

  //For data correction page to return page_status
  get_data_correction_page_status = (page_status) => {
    if (page_status === "done") {
      this.setState({ mode: "data_display" });
    }
  };

  get_modify_neighbors_page_status = (page_status) => {
    if (page_status !== "default") {
      // send the precinct Selection Mode back to the Apps
      this.props.leftSideBarStatus("precinct_selection_to_edit");
    } else if (page_status === "default") {
      this.props.leftSideBarStatus("saveRequested");
    } else {
      this.props.leftSideBarStatus();
    }
  };

  get_mapErrorList_onClick = (errorData) => {
    this.props.errorListOnClick(errorData);
  };

  get_comments_modal_data = (comment_modal_data) => {
    if (comment_modal_data) {
      this.setState({ comment_data: comment_modal_data });
    }
  };

  _handleClick = (event) => {
    this.props.showErrorPins();
  };

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
    let selectedFeatureType = this.props.selected
      ? this.props.selected.properties.type
      : undefined;
    const selectedFeature = this.props.selected;

    let keys = 0;

    if (list.length > 0) {
      if (userMode === "View") {
        return (
          <div style={{ textAlign: "left" }}>
            <Collapsible trigger="General Info" open={true}>
              <div style={{ marginLeft: "5px" }}>{list}</div>
            </Collapsible>
            <Collapsible trigger="Elections">
              {(selectedFeature.properties.type === "Precinct" && (
                <ElectionDisplayBar
                  votingData={selectedFeature.properties.votingData}
                />
              )) || <div>select a precinct to view elections</div>}
            </Collapsible>
            <Collapsible trigger="Demographics">
              {(selectedFeature.properties.type === "Precinct" && (
                <DemographicsTable
                  demographicData={selectedFeature.properties.demographicData}
                />
              )) || <div>select a precinct to view demographics</div>}
            </Collapsible>
            {/* <Collapsible trigger="Comments">
                            <Comments savedCommentData={comment_data} />
                            <br />
                            <CommentModal savedCommentData={this.get_comments_modal_data} />
                            <br />
                        </Collapsible> */}
            <Collapsible trigger="Map Errors" class="mapError">
              <MapErrors
                selectedFeature={this.props.selected}
                allErrors={this.props.allErrors}
                errorListOnClick={this.get_mapErrorList_onClick}
              />
              <br />
              <br />
              <br />
              <br />
              <br />
              <br />
              <br />
              <br />
              {/* <button className="Extra-Large-Button" onClick={this._handleClick}>View All</button>
                            <button className="Extra-Large-Button" onClick={this._handleClick}>View Self Intersecting Boundaries</button>
                            <button className="Extra-Large-Button" onClick={this._handleClick}>View Open Borders</button>
                            <button className="Extra-Large-Button" onClick={this._handleClick}>View Gaps in Precinct Coverage</button>
                            <button className="Extra-Large-Button" onClick={this._handleClick}>View Precinct Multipolygon</button>
                            <button className="Extra-Large-Button" onClick={this._handleClick}>View Overlapping Precincts</button> */}
            </Collapsible>
          </div>
        );
      } else if (userMode === "Edit") {
        if (selectedFeatureType === "Precinct") {
          return (
            <div>
              <Collapsible trigger="General Info" open={true}>
                {list}
              </Collapsible>
              <Collapsible trigger="Modify Data">
                <DataCorrectionPage
                  data_correction_page_status={
                    this.get_data_correction_page_status
                  }
                />
              </Collapsible>
              <Collapsible trigger="Modify Neighbors">
                <ModifyNeighbors
                  selectedFeature={selectedFeature}
                  modify_neighbors_page_status={
                    this.get_modify_neighbors_page_status
                  }
                  precinctSelectedForEdit={this.props.precinctSelectedForEdit}
                  appData={this.props.appData}
                />
              </Collapsible>
              {/* <Collapsible trigger="Merge Precinct" >
                                <MergePrecinct
                                    selectedFeature={selectedFeature}
                                    precinctSelectedForEdit={this.props.precinctSelectedForEdit}
                                    appData={this.props.appData} />
                            </Collapsible> */}
            </div>
          );
        } else {
          return (
            <div>
              <h5></h5>
              <Collapsible trigger="General Info" open={true}>
                {list}
              </Collapsible>
            </div>
          );
        }
      }
    }
    return <div>Click on a highlighted state to view details</div>;
  }
}

export default LeftSidebar;
