import React from 'react';
import SignIn from './SignIn';
import Dashboard from './Dashboard'
import './App.css';

class App extends React.Component{
    constructor(props) {
        super(props);
        this.state = {
            username: '',
            submitted: false,
        };

        // Bind 'this' to event handlers. React ES6 does not do this by default
        this.submittedCheck = this.submittedCheck.bind(this);
        this.onUsernameChange = this.onUsernameChange.bind(this);
    }

    submittedCheck = () => {
        this.setState({submitted: true})
    };

    onUsernameChange = (input) => {
        this.setState({username: input})
    };

    render() {
        if (this.state.submitted) {
            // Form was submitted, now show the main App
            return (
                <Dashboard username={this.state.username} />
            );
        }

        // Initial page load, show a simple login form
        return (
            <SignIn submittedCheck={this.submittedCheck} onUsernameChange={this.onUsernameChange}/>
        );
    }
}

export default App;

