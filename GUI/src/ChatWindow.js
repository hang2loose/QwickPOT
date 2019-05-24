import React from 'react';
import PropTypes from 'prop-types';
import { withStyles } from '@material-ui/core/styles';
import Paper from '@material-ui/core/Paper';
import Typography from '@material-ui/core/Typography';

const styles = theme => ({
    root: {
        ...theme.mixins.gutters(),
        paddingTop: theme.spacing.unit * 2,
        paddingBottom: theme.spacing.unit * 2,
        marginTop: theme.spacing.unit + 50,
        margin: theme.spacing.unit,
    },
});

class ChatWindow extends React.Component{

    //socket = {};
    constructor(props) {
        super(props);
        this.state = { messages: [] };
        this.sendHandler = this.sendHandler.bind(this);

        // Connect to the server
        //this.socket = io(config.api, { query: `username=${props.username}` }).connect();

        // Listen for messages from the server
        //this.socket.on('server:message', message => {
        //    this.addMessage(message);
        //});
    }

    sendHandler(message) {
        const messageObject = {
            username: this.props.username,
            message
        };

        // Emit the message to the server
        //this.socket.emit('client:message', messageObject);

        messageObject.fromMe = true;
        this.addMessage(messageObject);
    }

    addMessage(message) {
        // Append the message to the component state
        const messages = this.state.messages;
        messages.push(message);
        this.setState({ messages });
    }

    render() {

        const {classes} = this.props;

        return (
            <div>
                <Paper className={classes.root} elevation={1}>
                    <Typography variant="h5" component="h3">
                        This is a sheet of paper.
                    </Typography>
                    <Typography component="p">
                        Paper can be used to build surface or other elements for your application.
                    </Typography>
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