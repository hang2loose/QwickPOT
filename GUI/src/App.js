import React from 'react';
import SignIn from './SignIn';
import Dashboard from './Dashboard'
import InputBar from "./InputBar";
import './App.css';

class App extends React.Component{

    constructor(props) {
        super(props);
    }


    render() {
        if (this.props.submitted) {
            // Form was submitted, now show the main App
            return (
                <Dashboard username={this.props.username} />
            );
        }

        // Initial page load, show a simple login form
        return (
            <SignIn />
        );
    }


    //return (
    //<div className="App">
    //    <Dashboard />
    //    <SignIn />
    //</div>
  //);
}

App.defaultProps = {
    username: ''
};

export default App;

