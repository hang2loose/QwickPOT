import React from 'react';
import PropTypes from 'prop-types';
import classNames from 'classnames';
import {withStyles} from '@material-ui/core/styles';
import CssBaseline from '@material-ui/core/CssBaseline';
import Drawer from '@material-ui/core/Drawer';
import AppBar from '@material-ui/core/AppBar';
import Toolbar from '@material-ui/core/Toolbar';
import List from '@material-ui/core/List';
import Typography from '@material-ui/core/Typography';
import Divider from '@material-ui/core/Divider';
import IconButton from '@material-ui/core/IconButton';
import MenuIcon from '@material-ui/icons/Menu';
import ChevronLeftIcon from '@material-ui/icons/ChevronLeft';
import ListElements from './ListElements';
import Content from './Content';
import Config from '../config/config';
import io from 'socket.io-client';

const drawerWidth = 240;

const styles = theme => ({
    root: {
        display: 'flex',
    },
    toolbar: {
        paddingRight: 24,
    },
    toolbarIcon: {
        display: 'flex',
        alignItems: 'center',
        justifyContent: 'flex-end',
        padding: '0 8px',
        ...theme.mixins.toolbar,
    },
    appBar: {
        zIndex: theme.zIndex.drawer + 1,
        transition: theme.transitions.create(['width', 'margin'], {
            easing: theme.transitions.easing.sharp,
            duration: theme.transitions.duration.leavingScreen,
        }),
    },
    appBarShift: {
        marginLeft: drawerWidth,
        width: `calc(100% - ${drawerWidth}px)`,
        transition: theme.transitions.create(['width', 'margin'], {
            easing: theme.transitions.easing.sharp,
            duration: theme.transitions.duration.enteringScreen,
        }),
    },
    menuButton: {
        marginLeft: 12,
        marginRight: 36,
    },
    menuButtonHidden: {
        display: 'none',
    },
    title: {
        flexGrow: 1,
    },
    drawerPaper: {
        position: 'relative',
        whiteSpace: 'nowrap',
        width: drawerWidth,
        transition: theme.transitions.create('width', {
            easing: theme.transitions.easing.sharp,
            duration: theme.transitions.duration.enteringScreen,
        }),
    },
    drawerPaperClose: {
        overflowX: 'hidden',
        transition: theme.transitions.create('width', {
            easing: theme.transitions.easing.sharp,
            duration: theme.transitions.duration.leavingScreen,
        }),
        width: theme.spacing.unit * 7,
        [theme.breakpoints.up('sm')]: {
            width: theme.spacing.unit * 9,
        },
    },
    appBarSpacer: theme.mixins.toolbar,

});

class Dashboard extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            open: false,
            viewComponent: 'chat',
            messages: [],
        };

        this.socket = io(Config.chatserver.address + ':' + Config.chatserver.port).connect();
        this.socket.emit('user_on_connect', {
            event_type: 'new_user',
            load: {
                username: this.props.username
            }
        });

        this.forceToBottom = React.createRef();

        this.sendHandler = this.sendHandler.bind(this);
        this.addMessage = this.addMessage.bind(this);
        this.handleDrawerOpen = this.handleDrawerOpen.bind(this);
        this.handleDrawerClose = this.handleDrawerClose.bind(this);
        this.changeViewComponent = this.changeViewComponent.bind(this);

        this.socket.on('user_receive', message => {
            this.addMessage(message);
        });
    }

    changeViewComponent = (value) => {
        this.setState({viewComponent: value})
    };

    sendHandler = (message) => {
        const messageObject = {
            username: this.props.username,
            department: this.props.department.id,
            message,
        };

        if(messageObject.message !== '') {
            this.socket.emit('user_send', {
                event_type: 'question',
                load: {
                    username: messageObject.username,
                    department: messageObject.department,
                    question: messageObject.message
                }
            });
        }
    };

    addMessage = (message) => {
        const messages = this.state.messages;
        messages.push(message);
        this.setState({ messages });
    };

    handleDrawerOpen = () => {
        this.setState({ open: true });
    };

    handleDrawerClose = () => {
        this.setState({ open: false });
    };

    componentDidUpdate() {
        if(this.state.viewComponent === 'chat') {
            this.forceToBottom.current.scrollIntoView(false);
        }
    }

    render() {
        const { classes } = this.props;

        // Debug Console Log
        // console.log(this.state.messages);

        return (
            <div className={classes.root}>
                <CssBaseline />
                <AppBar
                    position="absolute"
                    className={classNames(classes.appBar,
                        this.state.open && classes.appBarShift)}
                >
                    <Toolbar disableGutters={!this.state.open}
                             className={classes.toolbar}>
                        <IconButton
                            color="inherit"
                            aria-label="Open drawer"
                            onClick={this.handleDrawerOpen}
                            className={classNames(
                                classes.menuButton,
                                this.state.open && classes.menuButtonHidden,
                            )}
                        >
                            <MenuIcon />
                        </IconButton>
                        <Typography
                            component="h1"
                            variant="h6"
                            color="inherit"
                            noWrap
                            className={classes.title}
                        >
                            QwickPOT+-
                        </Typography>
                    </Toolbar>
                </AppBar>
                <Drawer
                    variant="permanent"
                    classes={{
                        paper: classNames(classes.drawerPaper,
                            !this.state.open && classes.drawerPaperClose),
                    }}
                    open={this.state.open}
                >
                    <div className={classes.toolbarIcon}>
                        <IconButton onClick={this.handleDrawerClose}>
                            <ChevronLeftIcon />
                        </IconButton>
                    </div>
                    <Divider />
                    <List>
                        <ListElements username={this.props.username}
                                      department={this.props.department}
                                      changeViewComponent={this.changeViewComponent}
                        />
                    </List>
                </Drawer>
                <Content viewComponent={this.state.viewComponent}
                         messages={this.state.messages}
                         sendHandler={this.sendHandler}
                         forceToBottom={this.forceToBottom}
                         department={this.props.department}
                />
            </div>
        );
    }
}

Dashboard.defaultProps = {
    username: '',
    department: {}
};

Dashboard.propTypes = {
    classes: PropTypes.object.isRequired,
};

export default withStyles(styles)(Dashboard);
