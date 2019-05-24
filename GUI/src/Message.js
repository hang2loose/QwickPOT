import React from 'react';
import Card from "@material-ui/core/Card";

class Message extends React.Component {
    render() {
        return (
            <Card>
                <div>
                    { this.props.username }
                </div>
                <div>
                    { this.props.message }
                </div>
            </Card>
        );
    }
}

Message.defaultProps = {
    message: '',
    username: '',
};

export default Message;