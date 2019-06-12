import React from 'react';
import ListItem from '@material-ui/core/ListItem';
import ListItemIcon from '@material-ui/core/ListItemIcon';
import ListItemText from '@material-ui/core/ListItemText';
import Avatar from '@material-ui/core/Avatar';
import SettingsIcon from '@material-ui/icons/Settings';
import { withStyles } from '@material-ui/core/styles';
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
                <ListItem button>
                    <ListItemIcon>
                        <Avatar aria-label={this.props.username} className={classes.avatar}>
                            {this.props.username[0]}
                        </Avatar>
                    </ListItemIcon>
                    <ListItemText primary={this.props.username} />
                </ListItem>
                <ListItem button>
                    <ListItemIcon>
                        <SettingsIcon className={classes.iconTap}/>
                    </ListItemIcon>
                    <ListItemText primary="Settings" />
                </ListItem>
            </div>
        );
    }
}

ListElements.defaultProps = {
    username: '',
};

ListElements.propTypes = {
    classes: PropTypes.object.isRequired,
};

export default withStyles(styles)(ListElements)
