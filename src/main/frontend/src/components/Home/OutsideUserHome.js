import React, {Component} from "react";
import axios from "axios";
import Snackbar from "@material-ui/core/Snackbar";
import Alert from "@material-ui/lab/Alert";
import PaginationTableforOutsideUsers from "../table/PaginationTableforOutsideUsers";
import ReactDialog from "../common/ReactDialog";
import QRCode from "qrcode.react"
import QrReader from "react-qr-reader"

class OutsideUserHome extends Component{

    userDialogFields = [
        {id: "name", label: "Name", type: "text"},
        {id: "surname", label: "Surname", type: "text"},
        {id: "trIdNo", label: "TR Id Number", type: "text"},
    ]

    constructor(props) {
        super(props);
        this.state = {
            rows: [],
            registerActivityModalOpen: false,
            activityName:"",
            registered:false,
            result:"No Result",
            name:[],
            snackbarProperties: {
                isOpen: false,
                message: "",
                severity: ""
            }
        }
    }

    componentDidMount=()=> {
        console.log(this.props)
            axios.get("/users/activities")
                .then(response => {
                    this.setState({rows: response.data})
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

    toggleRegisterActivityModal = () => {
        this.setState({registerActivityModalOpen: !this.state.registerActivityModalOpen})
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

    onActivityRegister = (activityName) => {
        this.toggleRegisterActivityModal();
        this.setState({activityName:activityName});
    }

    submitActivityRegister = (inputData) => {
        this.toggleRegisterActivityModal();
        axios.post("/users/outsideuser",inputData)
            .then(response => {
                this.snackbarOpen("You have been registered successfully: " + inputData.name , "success")
                this.setState({name:inputData});
        }).catch(error => {
            if (error.response.status === 400) {
                this.snackbarOpen(error.response.data.errors[0].defaultMessage, "error")
            }
            else if (error.response.status === 500) {
                this.snackbarOpen(error.response.data.message, "error")
            }
            console.log(error.response);
        })
        setTimeout(() => {
            axios.post("/users/activities/" + inputData.trIdNo +"/"+ this.state.activityName, inputData)
                .then(response => {
                    this.setState( {
                        rows: this.state.rows.filter((activity) => activity.activityName !== this.state.activityName),
                        registered:true
                    })
                    this.setState(prevState => (
                        {rows: [...prevState.rows, response.data]}
                    ));
                    this.snackbarOpen("You have been registered successfully to activity: " + this.state.activityName , "success")
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
        }, 5000);
    }

    handleScan = data => {
        if (data) {
            this.setState({
                result: data
            })
        }
    }
    handleError = err => {
        console.error(err)
    }

    render(){
        if(this.state.registered)
        {
            return (<div>
                <Snackbar open={this.state.snackbarProperties.isOpen} autoHideDuration={5000} onClose={this.snackbarClose}
                          anchorOrigin={{vertical: 'top', horizontal: 'right'}}>
                    <Alert onClose={this.snackbarClose} severity={this.state.snackbarProperties.severity}>
                        {this.state.snackbarProperties.message}
                    </Alert>
                </Snackbar>

                <ReactDialog fields={this.userDialogFields} title="Register to Activity" isOpen={this.state.registerActivityModalOpen} onClose={this.toggleRegisterActivityModal} onSubmit={this.submitActivityRegister}/>

                <PaginationTableforOutsideUsers rows={this.state.rows} onUpdate={this.onActivityRegister}/>

                <div>
                    <QRCode id={this.state.name} value={this.state.name} size={290} level={"H"} includeMargin={true}/>
                </div>

                <div>
                    <QrReader
                        delay={300}
                        onError={this.handleError}
                        onScan={this.handleScan}
                        style={{ width: '100%' }}
                    />
                    <p>{this.state.result}</p>
                </div>
            </div>);
        }
        else{
            return (<div>
                <Snackbar open={this.state.snackbarProperties.isOpen} autoHideDuration={5000} onClose={this.snackbarClose}
                          anchorOrigin={{vertical: 'top', horizontal: 'right'}}>
                    <Alert onClose={this.snackbarClose} severity={this.state.snackbarProperties.severity}>
                        {this.state.snackbarProperties.message}
                    </Alert>
                </Snackbar>

                <ReactDialog fields={this.userDialogFields} title="Register to Activity" isOpen={this.state.registerActivityModalOpen} onClose={this.toggleRegisterActivityModal} onSubmit={this.submitActivityRegister}/>

                <PaginationTableforOutsideUsers rows={this.state.rows} onUpdate={this.onActivityRegister}/>
            </div>);
        }
    }
}

export default OutsideUserHome;