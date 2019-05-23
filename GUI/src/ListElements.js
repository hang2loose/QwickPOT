import React from 'react';
import ListItem from '@material-ui/core/ListItem';
import ListItemIcon from '@material-ui/core/ListItemIcon';
import ListItemText from '@material-ui/core/ListItemText';
import Avatar from '@material-ui/core/Avatar';
import SettingsIcon from '@material-ui/icons/Settings';
import LockOutlinedIcon from "@material-ui/core/SvgIcon/SvgIcon";



export const mainListItems = (
    <div>
        <ListItem button>
            <ListItemIcon>
                <Avatar>
                    <LockOutlinedIcon />
                </Avatar>
            </ListItemIcon>
            <ListItemText primary="Username" />
        </ListItem>
        <ListItem button>
            <ListItemIcon>
                <SettingsIcon />
            </ListItemIcon>
            <ListItemText primary="Settings" />
        </ListItem>
    </div>
);
