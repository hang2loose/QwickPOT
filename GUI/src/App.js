import React from 'react';
import SignIn from './SignIn';
import Dashboard from './Dashboard'
import InputBar from "./InputBar";
import './App.css';

function App() {
  return (
    <div className="App">
        <Dashboard />
        <SignIn />
    </div>
  );
}

export default App;

