import React from 'react';
import PropTypes from 'prop-types';
import Avatar from '@material-ui/core/Avatar';
import Button from '@material-ui/core/Button';
import CssBaseline from '@material-ui/core/CssBaseline';
import FormControl from '@material-ui/core/FormControl';
import TextField from '@material-ui/core/TextField';
import MenuItem from "@material-ui/core/MenuItem";
import Input from '@material-ui/core/Input';
import InputLabel from '@material-ui/core/InputLabel';
import Logo from '../images/logo.png'
import Paper from '@material-ui/core/Paper';
import Typography from '@material-ui/core/Typography';
import withStyles from '@material-ui/core/styles/withStyles';
import InputAdornment from "@material-ui/core/InputAdornment";

const styles = theme => ({
    main: {
        width: 'auto',
        display: 'block', // Fix IE 11 issue.
        marginLeft: theme.spacing.unit * 3,
        marginRight: theme.spacing.unit * 3,
        [theme.breakpoints.up(400 + theme.spacing.unit * 3 * 2)]: {
            width: 400,
            marginLeft: 'auto',
            marginRight: 'auto',
        },
    },
    paper: {
        marginTop: theme.spacing.unit * 8,
        display: 'flex',
        flexDirection: 'column',
        alignItems: 'center',
        padding: `${theme.spacing.unit * 2}px ${theme.spacing.unit * 3}px ${theme.spacing.unit * 3}px`,
    },
    avatar: {
        margin: theme.spacing.unit,
    },
    form: {
        width: '100%', // Fix IE 11 issue.
        marginTop: theme.spacing.unit,
    },
    submit: {
        marginTop: theme.spacing.unit * 3,
    },
});

const departments = [
    'Projektmanagement',
    'Operations/Planung',
    'Strategie',
    'Projektleiter-Pool'
];

class SignIn extends React.Component {

    constructor(props) {
        super(props);

        this.state = {
            department: ''
        };

        this.usernameChangeHandler = this.usernameChangeHandler.bind(this);
        this.usernameSubmitHandler = this.usernameSubmitHandler.bind(this);
        this.departmentChangeHandler = this.departmentChangeHandler.bind(this);
    }

    usernameChangeHandler = (event) => {
        this.props.onUsernameChange(event.target.value);
    };

    departmentChangeHandler = (event) => {
        this.props.onDepartmentChange(event.target.value);
        this.setState({ department: event.target.value });
    };

    usernameSubmitHandler = (event) => {
        event.preventDefault();
        this.props.submittedCheck();
    };


    render() {

        const {classes} = this.props;

        return (
            <main className={classes.main}>
                <CssBaseline/>
                <Paper className={classes.paper}>
                    <Avatar src={Logo} className={classes.avatar} />
                    <Typography component="h1" variant="h5">
                        Sign in
                    </Typography>
                    <form className={classes.form}
                          onSubmit={this.usernameSubmitHandler}>
                        <TextField
                            required
                            fullWidth
                            margin="normal"
                            label="Username"
                            autoComplete="username"
                            onChange={this.usernameChangeHandler}
                            autoFocus
                        />
                        <TextField
                            select
                            required
                            fullWidth
                            margin="normal"
                            variant="outlined"
                            label="Abteilung"
                            value={this.state.department}
                            onChange={this.departmentChangeHandler}
                            SelectProps={{
                                native: true
                            }}
                        >
                            <option />
                            {departments.map(option => (
                                <option key={option} value={option}>
                                    {option}
                                </option>
                            ))}
                        </TextField>
                        <Button
                            type="submit"
                            fullWidth
                            variant="contained"
                            color="primary"
                            className={classes.submit}>
                            Sign in
                        </Button>
                    </form>
                </Paper>
            </main>
        );
    }
}

SignIn.propTypes = {
    classes: PropTypes.object.isRequired,
};

export default withStyles(styles)(SignIn);