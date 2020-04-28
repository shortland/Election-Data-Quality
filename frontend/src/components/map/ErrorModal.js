import React, { PureComponent } from 'react';

export default class ErrorInfo extends PureComponent {
    render() {
        const { info } = this.props;

        return (
            <div>
                <h3>Map Error</h3>
                <br />
                <p style={{ textAlign: 'left' }}>
                    Type: {info.type}
                    <br />
                    Source: {info.from}
                </p>
            </div >
        );
    }
}