import React from 'react';
import 'typeface-roboto';
import SignIn from './components/SignIn';
import Dashboard from './components/Dashboard'
import './App.css';

class App extends React.Component{
    constructor(props) {
        super(props);
        this.state = {
            department: {},
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

    onDepartmentChange = (id, value) => {
        this.setState({department: {
                id: id,
                name: value
            }
        })
    };

    render() {
        if (this.state.submitted) {
            return (
                <Dashboard username={this.state.username}
                           department={this.state.department}/>
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

