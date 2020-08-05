import React, {Component} from "react";
import Chart from "./Chart";
import ChartwithDate from "./ChartwithDate";

class Charts extends Component{

    render(){
        return (
            <div>
                <Chart />
                <ChartwithDate/>
            </div>
            );
    }
}

export default Charts;