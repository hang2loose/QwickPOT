import React from 'react';
import PropTypes from 'prop-types';
import { withStyles } from '@material-ui/core/styles';
import Paper from '@material-ui/core/Paper';
import Typography from '@material-ui/core/Typography';
import List from '@material-ui/core/List';
import ListItem from '@material-ui/core/ListItem';
import ListItemIcon from '@material-ui/core/ListItemIcon';
import ListItemText from '@material-ui/core/ListItemText';
import LockOutlinedIcon from "@material-ui/core/SvgIcon/SvgIcon";
import Message from './Message';

const styles = theme => ({
    root: {
        ...theme.mixins.gutters(),
        paddingTop: theme.spacing.unit * 2,
        paddingBottom: theme.spacing.unit * 2,
        marginTop: theme.spacing.unit + 50,
        margin: theme.spacing.unit,
    },
    container: {
        display: 'flex',
        flexWrap: 'wrap',
    },
});

class ChatWindow extends React.Component{

    //socket = {};
    constructor(props) {
        super(props);

        this.sendHandler = this.sendHandler.bind(this);

        // Connect to the server
        //this.socket = io(config.api, { query: `username=${props.username}` }).connect();

        // Listen for messages from the server
        //this.socket.on('server:message', message => {
        //    this.addMessage(message);
        //});
    }

    componentDidUpdate() {
        // There is a new message in the state, scroll to bottom of list
        const objDiv = document.getElementById('messageList');
        objDiv.scrollTop = objDiv.scrollHeight;
    }

    sendHandler(message) {
        const messageObject = {
            username: this.props.username,
            message
        };

        // Emit the message to the server
        //this.socket.emit('client:message', messageObject);

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

        const messages = this.props.messages.map((message, i) => {
            return (
                <Message
                    key={i}
                    username={message.username}
                    message={message.message}
                />
            );
        });

        return (
            <div>
                <Paper className={classes.root} elevation={1} id='messageList'>
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