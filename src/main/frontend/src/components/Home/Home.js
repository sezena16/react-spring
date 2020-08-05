import React, {Component} from "react";
import axios from "axios";
import Button from "@material-ui/core/Button";
import PlusIcon from "@material-ui/icons/Add";
import Snackbar from "@material-ui/core/Snackbar";
import Alert from "@material-ui/lab/Alert";
import ReactDialog from "../common/ReactDialog";
import PaginationTable from "../table/PaginationTable";
import ListItem from "../List/ListItem";

class Home extends Component{
    activityDialogFields = [
        {id: "activityName", label: "Activity Name", type: "text"},
        {id: "startDate", label: "Start Date", type: "datetime-local"},
        {id: "endDate", label: "End Date", type: "datetime-local"},
        {id: "capacity", label: "Capacity", type: "number"},
        {id: "latitude", label: "Latitude", type: "float"},
        {id: "longitude", label: "Longitude", type: "float"}
    ]


    constructor(props) {
        super(props);
        this.state = {
            rows: [],
            addActivityModalOpen: false,
            updateActivityModalOpen: false,
            listItems:[],
            counter:0,
            data:[],
            snackbarProperties: {
                isOpen: false,
                message: "",
                severity: ""
            }
        }
    }

    componentDidMount() {
        console.log(this.props)
        console.log(this.props.username)
        if(this.props.username!==undefined){
            axios.get("/users/activities/user")
                .then(response => {
                    this.setState({rows: response.data})
                })
        }
    }

    getActivities=()=>{
        axios.get("/users/activities/user")
            .then(response => {
                this.setState({data: response.data})
            })
    }

    toggleAddActivityModal = () => {
        this.setState({addActivityModalOpen: !this.state.addActivityModalOpen})
    }

    toggleUpdateActivityModal = () => {
        this.setState({updateActivityModalOpen: !this.state.updateActivityModalOpen})
    }

    toggleUpdated = () => {
        this.setState({updated: !this.state.updated})
    }

    toggleAdded = () => {
        this.setState({added: !this.state.added})
    }

    toggleDeleted = () => {
        this.setState({deleted: !this.state.deleted})
    }

    submitActivityAdd = (inputData) => {
        this.toggleAddActivityModal();
        console.log(inputData);
        axios.post("/users/"+this.props.username+"/activities", inputData)
            .then(response => {
                this.setState(prevState => (
                    {rows: [...prevState.rows, response.data]}
                ));
                this.snackbarOpen("Activity has been added successfully!", "success");
            })
            .catch(error => {
                console.log(error.response);
                if (error.response.status === 400) {
                    this.snackbarOpen(error.response.data.errors[0].defaultMessage, "error");
                }
                else if (error.response.status === 500) {
                    this.snackbarOpen(error.response.data.message, "error");
                }
            })
    }

    snackbarOpen = (message, severity) => {
        console.log(message, severity);
        this.setState(prevState => {
            let snackbarProperties = {...prevState.snackbarProperties}
            snackbarProperties.isOpen = true;
            snackbarProperties.message = message;
            snackbarProperties.severity = severity;
            return {snackbarProperties};
        })
    }

    snackbarClose = () => {
        this.setState(prevState => {
            let snackbarProperties = {...prevState.snackbarProperties}
            snackbarProperties.isOpen = false;
            snackbarProperties.message = "";
            snackbarProperties.severity = "";
            return {snackbarProperties};
        })
    }

    onActivityUp = (activityName) => {
        this.toggleUpdateActivityModal();
    }

    submitActivityUpdate = (inputData) => {
        this.toggleUpdateActivityModal();
        axios.put("/users/"+this.props.username+"/activities/" + inputData.activityName, inputData)
            .then(response => {
                this.setState( {
                    rows: this.state.rows.filter((activity) => activity.activityName !== inputData.activityName)
                })
                this.setState(prevState => (
                    {rows: [...prevState.rows, response.data]}
                ));
                this.snackbarOpen("Activity: " + inputData.activityName + " has been updated!", "success")
            })
            .catch(error => {
                if (error.response.status === 400) {
                    this.snackbarOpen(error.response.data.errors[0].defaultMessage, "error")
                }
                else if (error.response.status === 500) {
                    this.snackbarOpen(error.response.data.message, "error")
                }
                console.log(error.response);
            })
    }

    onActivityDelete = (activityName) => {
        axios.delete("/users/"+this.props.username+"/activities/" + activityName)
            .then(response => {
                this.setState( {
                    rows: this.state.rows.filter((activity) => activity.activityName !== activityName)
                })
                this.snackbarOpen("Activity: " + activityName + " has been deleted!", "success")
            })
    }

    onList = (activityName) => {
        axios.get("/users/outsideuser/" + activityName)
            .then(response => {
                this.setState({listItems:response.data,counter:this.state.counter+1});
                this.snackbarOpen("User List for Activity: " + activityName , "success")
            })
        if(this.state.counter%2===0){
            this.setState({listItems:[]});
        }
    }

    render(){
        if(this.props.username!==undefined){
            return (<div>
                <Button variant="contained"
                        color="primary"
                        style={{float: "right"}}
                        onClick={this.toggleAddActivityModal}
                        startIcon={<PlusIcon/>}>
                    Add Activity
                </Button>
                <Snackbar open={this.state.snackbarProperties.isOpen} autoHideDuration={5000} onClose={this.snackbarClose}
                          anchorOrigin={{vertical: 'top', horizontal: 'right'}}>
                    <Alert onClose={this.snackbarClose} severity={this.state.snackbarProperties.severity}>
                        {this.state.snackbarProperties.message}
                    </Alert>
                </Snackbar>

                <ReactDialog fields={this.activityDialogFields} title="Add Activity" isOpen={this.state.addActivityModalOpen} onClose={this.toggleAddActivityModal} onSubmit={this.submitActivityAdd}/>

                <ReactDialog fields={this.activityDialogFields} title="Update Activity" isOpen={this.state.updateActivityModalOpen} onClose={this.toggleUpdateActivityModal} onSubmit={this.submitActivityUpdate}/>

                <PaginationTable rows={this.state.rows} onUpdate={this.onActivityUp} onDelete={this.onActivityDelete} onList={this.onList}/>

                <div>
                    <ListItem listItem={this.state.listItems}/>
                </div>

            </div>);
        }
        else{
            return(
                <br/>
            );
        }
    }
}

export default Home;