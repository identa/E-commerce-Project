import React, { Component } from "react";

const REGEX_EMAIL = /^(([^<>()[\]\\.,;:\s@\"]+(\.[^<>()[\]\\.,;:\s@\"]+)*)|(\".+\"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;

class Signup extends Component {
    constructor(props) {
        super(props);
        this.state = {
            firstName: "",
            lastName: "",
            email: "",
            password: "",
            message: "",
            firstNameError : '',
            lastNameError : '',
            emailError : '',
            passwordError : ''
        };
    }
    onChange = e => {
        this.setState({ [e.target.name]: e.target.value });
    };

    formSubmit = event => {
        event.preventDefault();

        const { firstName, lastName, email, password } = this.state;
        fetch("http://192.168.1.53:8080/api/signup", {
            method: "POST",
            body: JSON.stringify({
                firstName: firstName,
                lastName: lastName,
                email: email,
                password: password
            }),
            headers: {
                "Content-Type": "application/json"
            }
        })
        .then(res => res.json())
        .then(data => {
            if (data.status === "SUCCESS") {
                localStorage.setItem("name", data.data.firstName +  " " + data.data.lastName);         
                window.location.reload();    
            } else if (data.status === "FAILED") {
                this.setState({ message: data.message });
            }
        })
        .catch(error => {
            this.setState({ message: error });
        });
    };

    validateFirstName = () => {
        const firstName = this.state.firstName;

        if (firstName.length === 0) {
            this.setState({ firstNameError: "First name can not be empty!" });
        } else {
            this.setState({ firstNameError: "" });
        }
    };

    validateLastName = () => {
        const lastName = this.state.lastName;

        if (lastName.length === 0) {
            this.setState({ lastNameError: "Last name can not be empty!" });
        } 
        else {
            this.setState({ lastNameError: "" });
        }
    };

    validateEmail = () => {
        const email = this.state.email;
        if(email.length === 0){
            console.log("not pass");
            console.log(REGEX_EMAIL.test(email));
            this.setState({emailError : "Please enter valid email!"});
        }
        if(!REGEX_EMAIL.test(email)){
            
            this.setState({emailError : "Please enter valid email!"});
        }
        else{
            this.setState({emailError : ''});
        }
    };

    validatePassword = () =>{
        const password = this.state.password;

        if(password.length === 0){
            this.setState({ passwordError: "Password can not be empty!" });
        }
        else if(password.length < 6){
            console.log(password.length);
            this.setState({ passwordError: "Password at least 6 characters!" });
        }
        else {
            this.setState({ passwordError: "" });
        }
    };
    render() {
        return (
        <div className="sign-up-content">
            <div className="header-text">
            <p>Sign up for free</p>
            </div>
            <div className="form">
            <form method="POST" onSubmit={this.formSubmit}>
                <div className="contain">

                    <div className="form-row">
                        <div className="form-col-left">
                            <input
                                type="text"
                                placeholder="First Name*"
                                name="firstName"
                                required
                                autoFocus
                                onChange={this.onChange}
                                onBlur={this.validateFirstName}
                            />
                            <div className="message">
                                {this.state.firstNameError}
                            </div>
                        </div>
                        
                        <div className="form-col-right">
                            <input
                                type="text"
                                placeholder="Last Name*"
                                name="lastName"
                                required
                                onChange={this.onChange}
                                onBlur={this.validateLastName}
                            />
                            <div className="message">
                                {this.state.lastNameError}
                            </div>
                        </div>
                    </div>

                    <div className="form-row">
                        <input
                        type="email"
                        placeholder="Email Address*"
                        name="email"
                        required
                        onChange={this.onChange}
                        onBlur={this.validateEmail}
                        />                       
                    </div>
                    
                    <div className="form-row">
                        <div className="message">
                            {this.state.emailError}
                        </div>
                    </div>

                    <div className="form-row">
                        <input
                        type="password"
                        placeholder="Set a Password*"
                        name="password"
                        required
                        onChange={this.onChange}
                        onBlur={this.validatePassword}
                        />
                        
                    </div>

                    <div className="form-row">
                        <div className="message">
                            {this.state.passwordError}
                        </div>
                    </div>

                    <div className="form-row">
                        <button type="submit">GET STARTED</button>
                    </div>

                    <div className="form-row">
                        <div className="message">
                            {this.state.message}
                        </div>
                    </div>
                
                </div>
            </form>
            </div>
        </div>
        );
    }
}

export default Signup;
