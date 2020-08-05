import React from 'react';
import TableBody from "@material-ui/core/TableBody";
import TableRow from "@material-ui/core/TableRow";
import TableCell from "@material-ui/core/TableCell";
import CreateIcon from '@material-ui/icons/Create';
import DeleteIcon from "@material-ui/icons/Delete"
import IconButton from "@material-ui/core/IconButton";
import ArrowDropDownIcon from '@material-ui/icons/ArrowDropDown';

export default function TableContent(props) {

    let iconMap = {
        "update": <CreateIcon/>,
        "delete": <DeleteIcon/>,
        "list": <ArrowDropDownIcon/>
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
        if (column.id === "delete") {
            cellValue = createIcondel(column.id, column.onClick, row.activityName);
        }
        if (column.id === "update") {
            cellValue = createIconupdate(column.id, column.onClick, row.activityName);
        }
        if (column.id === "list") {
            cellValue = createIconlist(column.id, column.onClick, row.activityName);
        }

        return (
            <TableCell key={column.id} align={column.align}>
                {cellValue}
            </TableCell>
        );
    }

    function createIcondel(key, onClick, activityName) {
        return (
            <IconButton aria-label={key} color="primary" onClick={() => onClick(activityName)}>
                {iconMap[key]}
            </IconButton>
        )
    }

    function createIconupdate(key, onClick, activityName) {
        return (
            <IconButton aria-label={key} color="primary" onClick={() => onClick(activityName)}>
                {iconMap[key]}
            </IconButton>
        )
    }

    function createIconlist(key, onClick, activityName) {
        return (
            <IconButton aria-label={key} color="primary" onClick={() => onClick(activityName)}>
                {iconMap[key]}
            </IconButton>
        )
    }

}