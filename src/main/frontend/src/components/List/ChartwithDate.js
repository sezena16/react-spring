import React, { Component } from 'react'
import axios from 'axios';
import { Bar } from 'react-chartjs-2';

export default class ChartwithDate extends Component {
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
                let counter=[];
                let main=[];
                ipl.forEach(record => {
                    let value=record.startDate.toString();
                    let date=value.split("T");
                    let zet=date[0];
                    let holder=0;
                    main.push(date[0]);
                    console.log(main);
                    counter.push(record.registered);
                    playername.push(zet);
                    for(let i=0;i<main.length-1;i++){
                        for(let j=i+1;j<main.length;j++){
                            if(main[i]===main[j]){
                                console.log(counter[i]);
                                console.log(counter[j]);
                                counter[i]=counter[i]+counter[j];
                                holder=j;
                                console.log(counter[i]);
                                counter.splice(j,1);
                                playername.splice(j,1);
                                console.log(counter);
                            }
                        }
                    }
                    if(holder!==0)
                        main.splice(holder,1);
                    runscore.push(record.registered);
                });
                this.setState({
                    Data: {
                        labels: playername,
                        datasets: [
                            {
                                label: 'Dates',
                                data: counter,
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