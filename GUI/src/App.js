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
            return (
                <Dashboard username={this.state.username} />
            );
        }

        return (
            <SignIn submittedCheck={this.submittedCheck}
                    onUsernameChange={this.onUsernameChange}/>
        );
    }
}

export default App;

