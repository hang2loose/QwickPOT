import React from 'react';
import SignIn from './components/SignIn';
import Dashboard from './components/Dashboard'
import './App.css';

class App extends React.Component{
    constructor(props) {
        super(props);
        this.state = {
            department: '',
            username: '',
            submitted: false,
        };

        this.submittedCheck = this.submittedCheck.bind(this);
        this.onUsernameChange = this.onUsernameChange.bind(this);
        this.onDepartmentChange = this.onDepartmentChange.bind(this);
    }

    submittedCheck = () => {
        this.setState({submitted: true})
    };

    onUsernameChange = (input) => {
        this.setState({username: input})
    };

    onDepartmentChange = (input) => {
        this.setState({department: input})
        console.log(input)
    };

    render() {
        if (this.state.submitted) {
            return (
                <Dashboard username={this.state.username} />
            );
        }

        return (
            <SignIn submittedCheck={this.submittedCheck}
                    onUsernameChange={this.onUsernameChange}
                    onDepartmentChange={this.onDepartmentChange}/>
        );
    }
}

export default App;

