import React, { Component } from 'react'
import axios from 'axios';
import { Bar } from 'react-chartjs-2';
export default class Chart extends Component {
    constructor(props) {
        super(props);
        this.state = { Data: {} };
    }
    componentDidMount=()=> {
        axios.get("/users/activities/user")
            .then(res => {
                console.log(res);
                const ipl = res.data;
                let playername = [];
                let runscore = [];
                ipl.forEach(record => {
                    playername.push(record.activityName);
                    runscore.push(record.registered);
                });
                this.setState({
                    Data: {
                        labels: playername,
                        datasets: [
                            {
                                label: 'Registered',
                                data: runscore,
                                backgroundColor: [
                                    "#3cb371",
                                    "#0000FF",
                                    "#9966FF",
                                    "#4C4CFF",
                                    "#00FFFF",
                                    "#f990a7",
                                    "#aad2ed",
                                    "#FF00FF",
                                    "Blue",
                                    "Red"
                                ]
                            }
                        ]
                    }
                });
            })
    }
    render() {
        return (
            <div>
                <Bar data={this.state.Data} options={{ maintainAspectRatio: false,
                    scales: { yAxes: [{ ticks: {beginAtZero: true}}]}
                }} />
            </div>
        );
    }
}