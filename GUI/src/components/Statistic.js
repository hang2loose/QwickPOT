import React from 'react';
import classNames from 'classnames';
import PropTypes from 'prop-types';
import { withStyles } from '@material-ui/core/styles';
import Table from '@material-ui/core/Table';
import TableBody from '@material-ui/core/TableBody';
import TableCell from '@material-ui/core/TableCell';
import TableHead from '@material-ui/core/TableHead';
import TablePagination from '@material-ui/core/TablePagination';
import TableRow from '@material-ui/core/TableRow';
import TableSortLabel from '@material-ui/core/TableSortLabel';
import Toolbar from '@material-ui/core/Toolbar';
import Typography from '@material-ui/core/Typography';
import Paper from '@material-ui/core/Paper';
import IconButton from '@material-ui/core/IconButton';
import Tooltip from '@material-ui/core/Tooltip';
import FilterListIcon from '@material-ui/icons/FilterList';
import Config from "../config/config";
import Menu from "@material-ui/core/Menu";
import MenuItem from "@material-ui/core/MenuItem";

let counter = 0;
function createData(name, count) {
    counter++;
    return { id: counter, name, count };
}

function desc(a, b, orderBy) {
    if (b[orderBy] < a[orderBy]) {
        return -1;
    }
    if (b[orderBy] > a[orderBy]) {
        return 1;
    }
    return 0;
}

function stableSort(array, cmp) {
    const stabilizedThis = array.map((el, index) => [el, index]);
    stabilizedThis.sort((a, b) => {
        const order = cmp(a[0], b[0]);
        if (order !== 0) return order;
        return a[1] - b[1];
    });
    return stabilizedThis.map(el => el[0]);
}

function getSorting(order, orderBy) {
    return order === 'desc' ? (a, b) => desc(a, b, orderBy) : (a, b) => -desc(a, b, orderBy);
}

const rows = [
    { id: 'name', numeric: false, label: 'Thema' },
    { id: 'count', numeric: true, label: 'Anzahl' },
];

class EnhancedTableHead extends React.Component {
    createSortHandler = property => event => {
        this.props.onRequestSort(event, property);
    };

    render() {
        const { order, orderBy } = this.props;

        return (
            <TableHead>
                <TableRow>
                    {rows.map(
                        row => (
                            <TableCell
                                key={row.id}
                                align={row.numeric ? 'right' : 'left'}
                                sortDirection={orderBy === row.id ? order : false}
                            >
                                <Tooltip
                                    title="Sortieren"
                                    placement={row.numeric ? 'bottom-end' : 'bottom-start'}
                                    enterDelay={300}
                                >
                                    <TableSortLabel
                                        active={orderBy === row.id}
                                        direction={order}
                                        onClick={this.createSortHandler(row.id)}
                                    >
                                        {row.label}
                                    </TableSortLabel>
                                </Tooltip>
                            </TableCell>
                        ),
                        this,
                    )}
                </TableRow>
            </TableHead>
        );
    }
}

EnhancedTableHead.propTypes = {
    onRequestSort: PropTypes.func.isRequired,
    order: PropTypes.string.isRequired,
    orderBy: PropTypes.string.isRequired,
};

const toolbarStyles = theme => ({
    root: {
        paddingRight: theme.spacing.unit,
    },
    spacer: {
        flex: '1 1 100%',
    },
    actions: {
        color: theme.palette.text.secondary,
    },
    title: {
        paddingTop: theme.spacing.unit * 2,
        flex: '0 0 auto',
    },
});

let EnhancedTableToolbar = props => {
    const { classes } = props;

    return (
        <Toolbar
            className={classNames(classes.root)}
        >
            <div className={classes.title}>
                    <Typography variant="title" id="tableTitle">
                        {props.department.name}
                    </Typography>
                    <Typography variant="caption">
                        {'Erfasst seit 2019'}
                    </Typography>
            </div>
            <div className={classes.spacer} />
            <div className={classes.actions}>
                <Tooltip title="Abteilungen" disableFocusListener disableTouchListener>
                    <IconButton aria-label="Abteilungen"
                                aria-owns={props.anchorEl ? 'department-menu' : undefined}
                                aria-haspopup="true"
                                onClick={props.openDepartmentMenu}
                    >
                        <FilterListIcon />
                    </IconButton>
                </Tooltip>
                <Menu id="department-menu"
                      anchorEl={props.anchorEl}
                      open={Boolean(props.anchorEl)}
                      onClose={props.closeDepartmentMenu}
                >
                    {props.departments.map(option => (
                        <MenuItem key={option.department_id}
                                  value={option.department_id}
                                  onClick={props.departmentChangeHandler}>
                            {option.department_name}
                        </MenuItem>
                    ))}
                </Menu>
            </div>
        </Toolbar>
    );
};

EnhancedTableToolbar.propTypes = {
    classes: PropTypes.object.isRequired,
};

EnhancedTableToolbar = withStyles(toolbarStyles)(EnhancedTableToolbar);

const styles = theme => ({
    root: {
        width: '100%',
        marginTop: theme.spacing.unit + 50,
    },
    table: {
        minWidth: 1020,
    },
    tableWrapper: {
        overflowX: 'auto',
    },
});

class EnhancedTable extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            order: 'asc',
            orderBy: 'name',
            data: [
                createData('Cupcake', 305),
                createData('Donut', 452),
                createData('Eclair', 262),
                createData('Frozen yoghurt', 159),
                createData('Gingerbread', 356),
                createData('Honeycomb', 408),
                createData('Ice cream sandwich', 237),
                createData('Jelly Bean', 375),
                createData('KitKat', 518),
                createData('Lollipop', 392),
                createData('Marshmallow', 318),
                createData('Nougat', 360),
                createData('Oreo', 437),
            ],
            page: 0,
            rowsPerPage: 5,
            department: this.props.department,
            departments: [],
            error: '',
            anchorEl: null
        };

        fetch(Config["data-service"].api + "DepartmentController/getAllDepartmentNames")
        .then(response => response.json())
        .then(data => this.setState({departments: data}),
            error => this.setState({
                error: error,
            }));

        this.openDepartmentMenu = this.openDepartmentMenu.bind(this);
        this.closeDepartmentMenu = this.closeDepartmentMenu.bind(this);
        this.departmentChangeHandler = this.departmentChangeHandler.bind(this);
        this.handleRequestSort = this.handleRequestSort.bind(this);
        this.handleChangePage = this.handleChangePage.bind(this);
        this.handleChangeRowsPerPage = this.handleChangeRowsPerPage.bind(this);
    };

    openDepartmentMenu = (event) => {
        this.state.error ?
            this.setState({anchorEl: null}) :
            this.setState({ anchorEl: event.currentTarget })
    };

    closeDepartmentMenu = (event) => {
        this.setState({ anchorEl: null });
    };

    departmentChangeHandler = (event) => {
        const departmentObject = this.state.departments.find(
            entry => entry.department_id === event.target.value
        );

        this.setState({
            department: {
                    name: departmentObject.department_name,
                    id: departmentObject.department_id
                },
            anchorEl: null
        });
    };

    handleRequestSort = (event, property) => {
        const orderBy = property;
        let order = 'desc';

        if (this.state.orderBy === property && this.state.order === 'desc') {
            order = 'asc';
        }

        this.setState({ order, orderBy });
    };

    handleChangePage = (event, page) => {
        this.setState({ page });
    };

    handleChangeRowsPerPage = event => {
        this.setState({ rowsPerPage: event.target.value });
    };

    render() {
        const { classes } = this.props;
        const { data, order, orderBy, rowsPerPage, page } = this.state;
        const emptyRows = rowsPerPage - Math.min(rowsPerPage, data.length - page * rowsPerPage);

        return (
            <Paper className={classes.root}>
                <EnhancedTableToolbar department={this.state.department}
                                      departments={this.state.departments}
                                      anchorEl={this.state.anchorEl}
                                      openDepartmentMenu={this.openDepartmentMenu}
                                      closeDepartmentMenu={this.closeDepartmentMenu}
                                      departmentChangeHandler={this.departmentChangeHandler}
                />
                <div className={classes.tableWrapper}>
                    <Table className={classes.table} aria-labelledby="tableTitle">
                        <EnhancedTableHead
                            order={order}
                            orderBy={orderBy}
                            onRequestSort={this.handleRequestSort}
                        />
                        <TableBody>
                            {stableSort(data, getSorting(order, orderBy))
                            .slice(page * rowsPerPage, page * rowsPerPage + rowsPerPage)
                            .map(value => {
                                return (
                                    <TableRow hover key={value.id}>
                                        <TableCell>
                                            {value.name}
                                        </TableCell>
                                        <TableCell align="right">
                                            {value.count}
                                        </TableCell>
                                    </TableRow>
                                );
                            })}
                            {emptyRows > 0 && (
                                <TableRow style={{ height: 49 * emptyRows }}>
                                    <TableCell colSpan={2} />
                                </TableRow>
                            )}
                        </TableBody>
                    </Table>
                </div>
                <TablePagination
                    rowsPerPageOptions={[5, 10, 25]}
                    component="div"
                    count={data.length}
                    rowsPerPage={rowsPerPage}
                    page={page}
                    backIconButtonProps={{
                        'aria-label': 'Previous Page',
                    }}
                    nextIconButtonProps={{
                        'aria-label': 'Next Page',
                    }}
                    onChangePage={this.handleChangePage}
                    onChangeRowsPerPage={this.handleChangeRowsPerPage}
                />
            </Paper>
        );
    }
}

EnhancedTable.propTypes = {
    classes: PropTypes.object.isRequired,
};

export default withStyles(styles)(EnhancedTable);