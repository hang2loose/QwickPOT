import React from 'react';
import PropTypes from 'prop-types';
import classNames from 'classnames';
import { withStyles } from '@material-ui/core/styles';
import MenuItem from '@material-ui/core/MenuItem';
import TextField from '@material-ui/core/TextField';

const styles = theme => ({
    container: {
        display: 'flex',
        flexWrap: 'wrap',
    },
    textField: {
        marginLeft: theme.spacing.unit,
        marginRight: theme.spacing.unit,
    },
    dense: {
        marginTop: 16,
    },

});


class InputBar extends React.Component {

    constructor(props) {
        super(props);
        this.state = { chatInput: '' };

        // React ES6 does not bind 'this' to event handlers by default
        this.submitHandler = this.submitHandler.bind(this);
        this.textChangeHandler = this.textChangeHandler.bind(this);
    }
    submitHandler(event) {
        // Stop the form from refreshing the page on submit
        event.preventDefault();

        // Clear the input box
        this.setState({ chatInput: '' });

        // Call the onSend callback with the chatInput message
        this.props.onSend(this.state.chatInput);
    }

    textChangeHandler(event)  {
        this.setState({ chatInput: event.target.value });
    }

    render() {
        const {classes} = this.props;

        return (
            <form className={classes.container} noValidate autoComplete="off" onSubmit={this.submitHandler}>
                <TextField
                    id="outlined-full-width"
                    label="Chat"
                    style={{margin: 8}}
                    helperText="Press Enter to Send!"
                    onChange={this.textChangeHandler}
                    fullWidth
                    margin="normal"
                    variant="outlined"
                    InputLabelProps={{
                        shrink: true,
                    }}
                />
            </form>
        );
    }
}

InputBar.propTypes = {
    classes: PropTypes.object.isRequired,
};

export default withStyles(styles)(InputBar);