import React from 'react';
import PropTypes from 'prop-types';
import {withStyles} from '@material-ui/core/styles';
import Paper from '@material-ui/core/Paper';
import Message from './Message';

const styles = theme => ({
    root: {
        ...theme.mixins.gutters(),
        paddingTop: theme.spacing.unit * 2,
        paddingBottom: theme.spacing.unit * 2,
        marginTop: theme.spacing.unit + 50,
        margin: theme.spacing.unit,
    }
});

class ChatWindow extends React.Component{

    render() {

        const {classes} = this.props;

        const messages = this.props.messages.map((message, index) => {
            return (
                <Message
                    key={index}
                    username={message.load.username}
                    message={message.load.question}
                />
            );
        });

        return (
            <div>
                <Paper className={classes.root} elevation={1}>
                    { messages }
                </Paper>
            </div>
        );
    }
}


ChatWindow.defaultProps = {
    messages: []
};

ChatWindow.propTypes = {
    classes: PropTypes.object.isRequired,
};

export default withStyles(styles)(ChatWindow);