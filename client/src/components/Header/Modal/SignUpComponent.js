import React, { Component } from "react";
import REGEX_EMAIL from './../../Utils/Constant';
import passwordLength from './../../Utils/Constant';

class SignUpComponent extends Component {

    constructor(props) {
        super(props);
        
        this.state = {
            isModalShow : true,
            isSignUpShowing : true,
            firstName : '',
            lastName : '',
            email : '',
            password : '',
            error : {
                firstName : '',
                lastName : '',
                email : '',
                password : '',
                message : ''               
            }
        }
    }
    
    onChange = (event) =>{
        this.setState({ [event.target.name]: event.target.value });
    }

    showSignIn = ()=>{
        this.setState({isSignUpShowing : false});
        this.props.showSignIn(this.state.isSignUpShowing);
    }

    validateEmail = () => {
        const email = this.state.email;
        if(email.length === 0){
            this.setState(prevState =>({
                error :{
                    ...prevState.error,
                    email : 'Email can not be empty!'
                }
            }));
            return false;
        }
        if(!REGEX_EMAIL.test(email)){
            this.setState(prevState =>({
                error :{
                    ...prevState.error,
                    email : 'Please enter valid email!'
                }
            }));
            return false;
        }
        else{
            this.setState(prevState =>({
                error :{
                    ...prevState.error,
                    email : ''
                }
            }));
            return true;
        }
    }

    validatePassword = () =>{
        const password = this.state.password;

        if(password.length === 0){
            this.setState(prevState =>({
                error :{
                    ...prevState.error,
                    password : 'Password can not be empty!'
                }
            }));
            return false;
        }
        else if(password.length < passwordLength){
            this.setState(prevState =>({
                error :{
                    ...prevState.error,
                    password : 'Password at least 6 characters!'
                }
            }));
            return false;
        }
        else {
            this.setState(prevState =>({
                error :{
                    ...prevState.error,
                    password : ''
                }
            }));
            return true;
        }
    }

    validateFirstName = ()=> {
        const firstName = this.state.firstName;

        if(firstName.length === 0){
            this.setState(prevState =>({
                error :{
                    ...prevState.error,
                    firstName : 'First name can not be empty!'
                }
            }));
            return false;
        }
        else{
            this.setState(prevState =>({
                error :{
                    ...prevState.error,
                    firstName : ''
                }
            }));
            return true;
        }
    }

    validateLastName = ()=> {
        const lastName = this.state.lastName;

        if(lastName.length === 0){
            this.setState(prevState =>({
                error :{
                    ...prevState.error,
                    lastName : 'Last name can not be empty!'
                }
            }));
            return false;
        }
        else{
            this.setState(prevState =>({
                error :{
                    ...prevState.error,
                    lastName : ''
                }
            }));
            return true;
        }
    }

    formSubmit =(event) =>{
        event.preventDefault();

        if(this.validateFirstName() && this.validateLastName() && this.validateEmail() && this.validatePassword()){
            const { firstName, lastName, email, password } = this.state;
            fetch("http://192.168.1.53:8080/api/customer/signup", {
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
            .then(data =>{
                if(data.status === 'SUCCESS'){
                    localStorage.setItem("token", "Bearer "+ data.data.token);   
                    localStorage.setItem("name", data.data.firstName +  " " + data.data.lastName);   
                    this.setState({isModalShow : false});      
                    this.props.onHideModal(this.state.isModalShow);
                    this.props.changeAuthenticated();
                }
                else if (data.status === 'FAILED') {
                    this.setState(prevState =>({
                        error :{
                            ...prevState.error,
                            message : data.message
                        }
                    }));
                }
            })
            .catch(err => this.setState(prevState =>({
                error :{
                    ...prevState.error,
                    message : err
                }
            })));
        }
    }
    
    render() {
        return (
            <>
                <div className="header-button-contain row">
                    <div className="header-button active">
                        <button>Sign up</button>
                    </div>
                    <div className="header-button">
                        <button onClick={this.showSignIn}>Sign in</button>
                    </div>
                </div>
                <div className="sign-up-content">

                    <div className="header-text">
                        <p>Sign up for free</p>
                    </div>

                    <div className="form">
                        <form>
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
                                            {this.state.error.firstName}
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
                                            {this.state.error.lastName}
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
                                        {this.state.error.email}
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
                                        {this.state.error.password}
                                    </div>
                                </div>

                                <div className="form-row">
                                    <button type="submit" onClick={this.formSubmit}>GET STARTED</button>
                                </div>

                                <div className="form-row">
                                    <div className="message">
                                        {this.state.error.message}
                                    </div>
                                </div>
                            
                            </div>
                        </form>
                    </div>
                </div>
            </>
        );
  }
}

export default SignUpComponent;
