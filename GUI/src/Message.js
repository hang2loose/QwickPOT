import React from 'react';
import classNames from 'classnames';
import { withStyles } from '@material-ui/core/styles';
import Card from "@material-ui/core/Card";
import CardHeader from "@material-ui/core/CardHeader";
import Avatar from "@material-ui/core/Avatar";

const styles = theme => ({
    card: {
        maxWidth: 400,
    },
    media: {
        height: 0,
        paddingTop: '56.25%', // 16:9
    },
    expand: {
        transform: 'rotate(0deg)',
        marginLeft: 'auto',
        transition: theme.transitions.create('transform', {
            duration: theme.transitions.duration.shortest,
        }),
    },
    expandOpen: {
        transform: 'rotate(180deg)',
    },
    avatar: {
        backgroundColor: 'red',
    },
});

class Message extends React.Component {
    render() {

        const { classes } = this.props;

        return (
            <Card>
                <CardHeader
                    avatar={
                        <Avatar aria-label={this.props.username} className={classes.avatar}>
                            {this.props.username[0]}
                        </Avatar>
                    }
                    subheader={this.props.username}
                />
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

export default withStyles(styles)(Message);