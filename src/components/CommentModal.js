import React, { Component } from 'react';
import Modal from 'react-bootstrap/Modal';
import Button from 'react-bootstrap/Button';

class CommentModal extends Component {
    constructor(props) {
        super(props);
        this.state = {
            show: false
        };
    }

    //display is boolean
    handle_modal_display(display) {
        this.setState({ show: display });
    }

    saveCommentData(user, text) {
        let comment_data = {
            commenter: user,
            content: text
        };
        this.props.savedCommentData(comment_data);
    }

    render() {
        const { show } = this.state;
        return (
            <div>
                <Button variant="primary" onClick={() => { this.handle_modal_display(true) }}>Create Comment</Button>
                <Modal show={show} onHide={() => { this.handle_modal_display(false) }}>
                    <Modal.Header closeButton>
                        <Modal.Title>Enter comment</Modal.Title>
                    </Modal.Header>
                    <Modal.Body>
                        <input id="commentModalInput" placeholder=" Please enter your comments here"></input>
                    </Modal.Body>
                    <Modal.Footer>
                        <Button variant="secondary" onClick={() => { this.handle_modal_display(false) }}>Cancel</Button>
                        <Button variant="primary" onClick={() => {
                            this.handle_modal_display(false);
                            this.saveCommentData("User", document.getElementById("commentModalInput").value);
                        }}>Save Comment</Button>
                    </Modal.Footer>
                </Modal>
            </div>
        );
    }
}

export default CommentModal;