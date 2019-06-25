import React from 'react';
import Config from '../config/config.json'

const API = Config["data-service"].api;

class Departments extends React.Component {

    componentDidMount() {
        fetch(API)
        .then(response => response.json())
        .then(data => this.props.departmentsGET(data));
    };

    render() {
        return(
            null
        );
    }
}

export default Departments;