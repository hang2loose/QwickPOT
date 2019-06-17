import React from 'react';
import PropTypes from 'prop-types';
import {withStyles} from '@material-ui/core/styles';
import TextField from '@material-ui/core/TextField';

const styles = theme => ({
    container: {
        display: 'flex',
        flexWrap: 'wrap',
    },
});


class InputBar extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            chatInput: '',
        };

        this.submitHandler = this.submitHandler.bind(this);
        this.chatInputHandler = this.chatInputHandler.bind(this);
    }
    
    submitHandler = (event) => {
        event.preventDefault();

        this.setState({ chatInput: '' });

        this.props.onSend(this.state.chatInput);
    };

    chatInputHandler = (event) => {
        this.setState({ chatInput: event.target.value});
    };

    render() {
        const {classes} = this.props;

        return (
            <form className={classes.container}
                  noValidate autoComplete="off"
                  onSubmit={this.submitHandler}
                  ref={this.props.toBottom}>
                <TextField
                    id="outlined-full-width"
                    label="Chat"
                    style={{margin: 8}}
                    helperText="Press Enter to Send!"
                    onChange={this.chatInputHandler}
                    value={this.state.chatInput}
                    fullWidth
                    margin="normal"
                    variant="outlined"
                    autoFocus={true}
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