import React, { Component } from 'react';
import Card from 'react-bootstrap/Card';

class Comments extends Component {
    constructor(props) {
        super(props);
        this.state = {
            comments: []
        };
    }

    componentDidMount() {
        this.get_comments();
    }

    componentDidUpdate(prevProps) {
        if (prevProps.savedCommentData !== this.props.savedCommentData) {
            this.get_saved_comments();
        }
    }

    //Dynamically shows saved comments from comment modal
    get_saved_comments() {
        console.log("________________", this.props.savedCommentData)
        if (this.props.savedCommentData) {
            this.setState((state) => {
                let new_comments = state.comments;
                new_comments.push(this.props.savedCommentData);
                return { comments: new_comments }
            });
        }
    }

    // this is just for the presentation
    get_comments() {
        let numComments = Math.ceil(Math.random() * 3);
        let commenter = ["David", "Ilan", "Reed", "Sam"];
        let content = ["Some comment about changing the election data b/c old source was invalid.", "Some comment about undoing the yesterdays changes done to the boundaries of the Albany county of NY.", "Some test comment.", "Another test comment."];
        let comments = [];
        for (let i = 0; i < numComments; i++) {
            let comment = {
                commenter: commenter[Math.round(Math.random() * 3)],
                content: content[Math.round(Math.random() * 3)]
            }
            comments.push(comment);
        }
        this.setState({ comments: comments });
    }

    render() {
        const { comments } = this.state;
        const displayed_comments = [];
        for (let i in comments) {
            displayed_comments.push(
                <Card>
                    <Card.Body>
                        {comments[i].commenter}{": "}{comments[i].content}
                    </Card.Body>
                </Card>
            );
        }

        return (
            <div >
                {displayed_comments}
            </div >
        );
    }
}

export default Comments;