import React from 'react';

const API = 'http://localhost:9090/getAllDepartmentNames';

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