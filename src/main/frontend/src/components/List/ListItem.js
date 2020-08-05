import React, {Component} from "react";
import Paging from "../table/Paging";

class ListItem extends Component{

    columns = [
        {id: "name", label: "Name", minWidth: 170},
        {id: "surname", label: "Surname", minWidth: 170,align: 'right'},
        {id: "trIdNo", label: "TR Id Number", minWidth: 170,align: 'right'},
    ];

    render(){
        if(this.props.listItem===[])
        {
            return(
                <br/>
            );
        }
        else{
            return (
                <Paging rows={this.props.listItem}/>
            );
        }
    }
}

export default ListItem;