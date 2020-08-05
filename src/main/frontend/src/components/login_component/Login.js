import React, {Component} from "react";
import { Button, FormGroup, FormControl} from "react-bootstrap";
import axios from "axios";
import {Redirect} from "react-router-dom"

class Login extends Component{

    constructor(props) {
        super(props);
        this.state = {
            username: [],
            password: [],
            token: "",
            redirect:false,
            from:""
        }
    }

    validateForm = () => {
        return this.state.username.length > 0 && this.state.password.length > 0;
    }

    handleSubmit = (event) => {
        event.preventDefault();
        let inputData={"username":this.state.username,"password":this.state.password}
        axios.post("/login", inputData)
            .then(response => {
                if(response.status===200){
                    this.setState({token: response.data,redirect:true,from:"/"});
                    console.log(this.state.token)
                    console.log("Successfully Logged In!");
                    this.props.onChangeLoggedIn(this.state.username,this.state.password,this.state.token);
                }
                console.log(response)
            })
    }

    render() {
        if(this.state.redirect){
            return (<Redirect to={this.state.from}/>)
        }
        else{
            return (
                <div className="Login">
                    <form onSubmit={this.handleSubmit}>
                        <FormGroup controlId="username" bsSize="large">
                            <FormControl
                                autoFocus
                                type="username"
                                value={this.state.username}
                                onChange={e=> this.setState({username:e.target.value})}
                            />
                        </FormGroup>
                        <FormGroup controlId="password" bsSize="large">
                            <FormControl
                                value={this.state.password}
                                onChange={e=> this.setState({password:e.target.value})}
                                type="password"
                            />
                        </FormGroup>
                        <Button block bsSize="large" disabled={!this.validateForm()} type="submit">
                            Login
                        </Button>
                    </form>
                </div>
            );
        }
    }
}

export default Login;