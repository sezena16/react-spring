import React, {Component} from "react";
import { Button, FormGroup, FormControl} from "react-bootstrap";
import axios from "axios";
import {Redirect} from "react-router-dom"

class SignUp extends Component{

    constructor(props) {
        super(props);
        this.state = {
            username: [],
            password: [],
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
        axios.post("/users", inputData)
            .then(response => {
                if(response.status===200){
                    this.setState({username:response.data.username,password:response.data.password,redirect:true,from:"/"});
                    console.log("Successfully Signed Up!");
                    this.props.onChangeSignUp(this.state.username,this.state.password);
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
                            Signup
                        </Button>
                    </form>
                </div>
            );
        }
    }
}

export default SignUp;