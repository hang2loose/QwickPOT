import React from 'react';
import ListItem from '@material-ui/core/ListItem';
import ListItemIcon from '@material-ui/core/ListItemIcon';
import ListItemText from '@material-ui/core/ListItemText';
import Avatar from '@material-ui/core/Avatar';
import TimelineIcon from '@material-ui/icons/Timeline';
import {withStyles} from '@material-ui/core/styles';
import PropTypes from "prop-types";

const styles = theme => ({
    iconTap: {
        height: 42,
        width: 42,
    },
});

class ListElements extends React.Component {

    render() {
        const {classes} = this.props;

        return (
            <div>
                <ListItem button onClick={() => this.props.changeViewComponent('chat')}>
                    <ListItemIcon>
                        <Avatar aria-label={this.props.username} className={classes.avatar}>
                            {this.props.username[0]}
                        </Avatar>
                    </ListItemIcon>
                    <ListItemText primary={this.props.username}
                                  secondary={this.props.department}/>
                </ListItem>
                <ListItem button onClick={() => this.props.changeViewComponent('statistic')}>
                    <ListItemIcon>
                        <TimelineIcon className={classes.iconTap}/>
                    </ListItemIcon>
                    <ListItemText primary="Statistik" />
                </ListItem>
            </div>
        );
    }
}

ListElements.defaultProps = {
    username: '',
    department: '',
};

ListElements.propTypes = {
    classes: PropTypes.object.isRequired,
};

export default withStyles(styles)(ListElements)
