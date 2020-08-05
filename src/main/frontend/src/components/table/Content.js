import React from 'react';
import TableBody from "@material-ui/core/TableBody";
import TableRow from "@material-ui/core/TableRow";
import TableCell from "@material-ui/core/TableCell";

export default function Content(props) {

    return (
        <TableBody>
            {getRowSlice().map(row => createTableRow(row))}
        </TableBody>

    );

    function getRowSlice() {
        return props
            .rows
            .slice(calculatePageBeginning(), calculatePageEnd());
    }

    function calculatePageBeginning() {
        return props.page * props.rowsPerPage;
    }

    function calculatePageEnd() {
        return props.page * props.rowsPerPage + props.rowsPerPage;
    }


    function createTableRow(row) {
        return (
            <TableRow hover role="checkbox" key={row.activityName}>
                {props.columns.map(column => createTableCell(column, row))}
            </TableRow>
        );
    }

    function createTableCell(column, row) {

        let cellValue = row[column.id];

        return (
            <TableCell key={column.id} align={column.align}>
                {cellValue}
            </TableCell>
        );
    }

}