import React, {Component} from 'react';
import Paper from '@material-ui/core/Paper';
import Table from '@material-ui/core/Table';
import TableContainer from '@material-ui/core/TableContainer';
import TableHeader from "./TableHeader";
import {TablePageController} from "./TablePageController";
import Content from "./Content"

export default class Paging extends Component {

    constructor(props) {
        super(props);
        this.state = {
            page: 0,
            rowsPerPage: 10,
            rows: []
        };
    }

    columns = [
        {id: "name", label: "Name", minWidth: 170},
        {id: "surname", label: "Surname", minWidth: 170},
    ];

    handleChangePage = (event, newPage) => {
        this.setState({page: newPage});
    };

    handleChangeRowsPerPage = (event) => {
        this.setState({
            rowsPerPage: event.target.value,
            page: 0
        });
    };


    render() {
        return (
            <Paper>
                <TableContainer>
                    <Table stickyHeader aria-label="sticky table">
                        <TableHeader columns={this.columns}/>
                        <Content rows={this.props.rows} page={this.state.page} rowsPerPage={this.state.rowsPerPage}
                                                     columns={this.columns} />
                    </Table>
                </TableContainer>
                <TablePageController count={this.state.rows.length}
                                     rowsPerPage={this.state.rowsPerPage}
                                     page={this.state.page} handleChangePage={this.handleChangePage}
                                     handleChangeRowsPerPage={this.handleChangeRowsPerPage}/>
            </Paper>
        );
    }


}