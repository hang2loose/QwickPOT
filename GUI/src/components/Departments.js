import React from 'react';
import Config from '../config/config'

const API = Config["data-service"].api + "getAllDepartmentNames";

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