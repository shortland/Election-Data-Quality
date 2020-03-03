import React, { Component } from 'react';

import Button from 'react-bootstrap/Button';
import Form from 'react-bootstrap/Form'



class DataCorrectionPage extends Component {
    constructor(props) {
        super(props);
    }

    save_changes() {
        this.dismiss();
    }

    dismiss() {
        this.props.data_correction_page_status("done");
    }


    render() {
        return (
            <div className="data_correction_page">
                <h4>Data Correction Form</h4>
                <Form>
                    <Form.Group controlId="data_correction.target_data">
                        <Form.Label>Data for correction</Form.Label>
                        <Form.Control as="select">
                            <option>Demographic</option>
                            <option>Election</option>
                        </Form.Control>
                    </Form.Group>
                    <Form.Group controlId="data_correction.value">
                        <Form.Label>Changed Value</Form.Label>
                        <Form.Control type="value" placeholder="Please enter corrected data" />
                    </Form.Group>
                </Form>
                <div className="data_correction_Btns">
                    <Button variant="outline-primary" id="data_change" onClick={() => { this.save_changes() }}>Change</Button>
                    <Button variant="outline-primary" id="data_change_cancel" onClick={() => { this.dismiss() }}>Cancel</Button>
                </div>
                <br />
            </div>
        );
    }
}

export default DataCorrectionPage;