import React, {Component} from 'react';
import Login from "./components/login_component/Login";
import Home from "./components/Home/Home"
import {BrowserRouter as Router,Route,Link,Switch} from "react-router-dom";
import OutsideUserHome from "./components/Home/OutsideUserHome";
import Button from "@material-ui/core/Button";
import Charts from "./components/List/Charts";
import ShowOnMap from "./components/map_components/ShowOnMap";
import Alert from "@material-ui/lab/Alert";
import Snackbar from "@material-ui/core/Snackbar";
import SignUp from "./components/SignUp/SignUp";

class App extends Component{

    constructor(props) {
        super(props)
        this.state={
            username: [],
            password: [],
            loggedIn: false,
            snackbarProperties: {
                isOpen: false,
                message: "",
                severity: ""
            }
        };
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



    onChangeLoggedIn=(username,password,token) =>{
        if(token===""){
            console.log("Bad Credentials!");
            this.snackbarOpen("Bad Credentials!" , "error");
        }
        else{
            console.log(this.state);
            this.snackbarOpen("Successfully Logged In!" , "success");
            this.setState({ username: username, password: password, loggedIn: true });
            console.log(this.state);
        }
    }

    onChangeSignup=(username,password) =>{
        this.snackbarOpen("Successfully Signed Up!" , "success");
        this.setState({ username: username, password: password, loggedIn: true });
    }

    onChangeLoggedOut=() =>{
        console.log(this.state);
        this.setState({ username: "", password: "", loggedIn: false });
        console.log(this.state);
    }

    render() {
      if(this.state.loggedIn){
          console.log(this.state)
          return (
              <div className="App">
                  <Snackbar open={this.state.snackbarProperties.isOpen} autoHideDuration={5000} onClose={this.snackbarClose}
                            anchorOrigin={{vertical: 'top', horizontal: 'right'}}>
                      <Alert onClose={this.snackbarClose} severity={this.state.snackbarProperties.severity}>
                          {this.state.snackbarProperties.message}
                      </Alert>
                  </Snackbar>
                  <Router>
                      <div>
                          <nav>
                              <ul>
                                  <li>
                                      <Link to="/">Home</Link>
                                  </li>
                                  <li>
                                      <Link to="/charts">Charts</Link>
                                  </li>
                                  <li>
                                      <Link to="/map">Map</Link>
                                  </li>
                              </ul>
                          </nav>
                      </div>
                      <Switch>
                          <Route exact path="/">
                              <div>
                                  <Button variant="contained"
                                          color="primary"
                                          style={{float: "left"}}
                                          onClick={this.onChangeLoggedOut}>
                                      Logout
                                  </Button>
                                  <Home username={this.state.username}/>

                              </div>
                          </Route>
                          <Route exact path="/charts">
                              <Charts/>
                          </Route>
                          <Route exact path="/map">
                              <ShowOnMap/>
                          </Route>
                      </Switch>
                  </Router>

                  <Home/>
              </div>
          );
      }
      else {
          return (
              <div className="App">
                  <Snackbar open={this.state.snackbarProperties.isOpen} autoHideDuration={5000} onClose={this.snackbarClose}
                            anchorOrigin={{vertical: 'top', horizontal: 'right'}}>
                      <Alert onClose={this.snackbarClose} severity={this.state.snackbarProperties.severity}>
                          {this.state.snackbarProperties.message}
                      </Alert>
                  </Snackbar>
                  <Router>
                      <div>
                          <nav>
                              <ul>
                                  <li>
                                      <Link to="/signup">SignUp</Link>
                                  </li>
                                  <li>
                                      <Link to="/login">Login</Link>
                                  </li>
                                  <li>
                                      <Link to="/">Home</Link>
                                  </li>
                              </ul>
                          </nav>
                      </div>
                      <Switch>
                          <Route exact path="/signup">
                              <SignUp onChangeSignUp={this.onChangeSignup}/>
                          </Route>
                          <Route exact path="/login">
                              <Login onChangeLoggedIn={this.onChangeLoggedIn}/>
                          </Route>
                          <Route exact path="/">
                              <OutsideUserHome/>
                          </Route>
                      </Switch>
                  </Router>
              </div>
          );
      }
  }
}

export default App;
