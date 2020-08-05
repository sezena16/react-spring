import React from 'react';
import TableBody from "@material-ui/core/TableBody";
import TableRow from "@material-ui/core/TableRow";
import TableCell from "@material-ui/core/TableCell";
import AddCircleIcon from '@material-ui/icons/AddCircle';
import IconButton from "@material-ui/core/IconButton";

export default function TableContentforOutsideUsers(props) {

    let iconMap = {
        "update": <AddCircleIcon/>,
    }

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

        if (column.id === "update") {
            cellValue = createIconaddCircle(column.id, column.onClick, row.activityName);
        }

        return (
            <TableCell key={column.id} align={column.align}>
                {cellValue}
            </TableCell>
        );
    }

    function createIconaddCircle(key, onClick, activityName) {
        return (
            <IconButton aria-label={key} color="primary" onClick={() => onClick(activityName)}>
                {iconMap[key]}
            </IconButton>
        )
    }

}