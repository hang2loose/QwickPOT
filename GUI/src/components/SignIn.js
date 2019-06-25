import React from 'react';
import PropTypes from 'prop-types';
import Avatar from '@material-ui/core/Avatar';
import Button from '@material-ui/core/Button';
import CssBaseline from '@material-ui/core/CssBaseline';
import TextField from '@material-ui/core/TextField';
import Logo from '../images/logo.png'
import Paper from '@material-ui/core/Paper';
import Typography from '@material-ui/core/Typography';
import Config from '../config/config'
import withStyles from '@material-ui/core/styles/withStyles';

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

class SignIn extends React.Component {

    constructor(props) {
        super(props);

        this.state = {
            error: null,
            department: {},
            departments: []
        };

        fetch(Config["data-service"].api + "getAllDepartmentNames")
        .then(response => response.json())
        .then(data => this.setState({departments: data}),
                error => this.setState({
                    error: error,
                    departments: [{
                        department_id: null,
                        department_name: 'buildAll'
                    }]
                }));

        this.usernameChangeHandler = this.usernameChangeHandler.bind(this);
        this.usernameSubmitHandler = this.usernameSubmitHandler.bind(this);
        this.departmentChangeHandler = this.departmentChangeHandler.bind(this);
    }

    usernameChangeHandler = (event) => {
        this.props.onUsernameChange(event.target.value);
    };

    departmentChangeHandler = (event) => {
        const departmentObject = this.state.departments.find(
            entry => entry.department_id.toString() === event.target.value
        );

        console.log(departmentObject);

        this.props.onDepartmentChange(
            departmentObject.department_id,
            departmentObject.department_name
        );

        this.setState({ department: {
                id: departmentObject.department_id,
                name: departmentObject.department_name
            }
        });

        console.log(this.state.department);

    };

    usernameSubmitHandler = (event) => {
        event.preventDefault();
        this.props.submittedCheck();
    };

    render() {

        const {classes} = this.props;

        if (this.state.error) console.log(this.state.error);

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
                            value={this.state.department.name}
                            onChange={this.departmentChangeHandler}
                            SelectProps={{
                                native: true
                            }}
                        >
                            <option />
                            {this.state.departments.map(option => (
                                <option key={option.department_id} value={option.department_id}>
                                    {option.department_name}
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