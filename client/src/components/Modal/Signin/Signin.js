import React, { Component } from "react";

const REGEX_EMAIL = /^(([^<>()[\]\\.,;:\s@\"]+(\.[^<>()[\]\\.,;:\s@\"]+)*)|(\".+\"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
class Signin extends Component {
    constructor(props) {
        super(props);
        
        this.state = {
            email : '',
            password :'',
            message : '',
            emailError : '',
            passwordError : ''
        }
    }
    
    onChange = e => {
        this.setState({ [e.target.name]: e.target.value });
    };

    formSubmit = event => {
        event.preventDefault();
        if (this.validateEmail() && this.validatePassword()) {       
            const data = {
                email : this.state.email,
                password : this.state.password
            };
            // document.getElementById("myModal").style.display = "none";
            // var elem = document.getElementsByClassName("modal-backdrop");
            // elem[0].parentNode.removeChild(elem[0]);
            fetch("http://192.168.1.53:8080/api/signin", {
            method: "POST",
            body: JSON.stringify(data),
            headers: {
                "Content-Type": "application/json"
            }
            })
            .then(res => res.json())
            .then(data =>{
                if(data.status === 'SUCCESS'){  
                    localStorage.setItem("name", data.data.firstName +  " " + data.data.lastName);         
                    window.location.reload();      
                }
                else if (data.status === 'FAILED'){
                    this.setState({message : data.message});
                }
            })
            .catch(error => this.setState({message : error}));
        }
    };

    validateEmail = () => {
        const email = this.state.email;
        if(email.length === 0){
            this.setState({emailError : "Please enter valid email!"});
            return false;
        }
        if(!REGEX_EMAIL.test(email)){
            this.setState({emailError : "Please enter valid email!"});
            return false;
        }
        else{
            this.setState({emailError : ''});
            return true;
        }
    };

    validatePassword = () =>{
        const password = this.state.password;

        if(password.length === 0){
            this.setState({ passwordError: "Password can not be empty!" });
            return false;
        }
        else if(password.length < 6){
            this.setState({ passwordError: "Password at least 6 characters!" });
            return false;
        }
        else {
            this.setState({ passwordError: "" });
            return true;
        }
    };

    render() {
        return (
        <div className="sign-in-content">
            <div className="header-text">
                <p>Sign in here</p>
            </div>
            <div className="form">
                <form onSubmit={this.formSubmit}>
                    <div className="contain">
                        <div className="form-row">
                            <input type="email" placeholder="Email Address*" name="email" autoFocus required onChange={this.onChange} onBlur={this.validateEmail}/>
                        </div>
                        <div className="form-row">
                            <div className="message">
                                {this.state.emailError}
                            </div>
                        </div>

                        <div className="form-row">
                            <input type="password" placeholder="Enter Password*" name="password" required onChange={this.onChange} onBlur={this.validatePassword}/>
                        </div>

                        <div className="form-row">
                            <div className="message">
                                {this.state.passwordError}
                            </div>
                        </div>

                        <div className="form-row">
                            <button type="submit">SIGN IN</button>
                        </div>

                        <div className="message">
                            {this.state.message}
                        </div>
                    </div>
                </form>
            </div>
        </div>
        );
    }
}

export default Signin;
