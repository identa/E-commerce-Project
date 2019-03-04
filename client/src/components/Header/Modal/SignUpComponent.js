import React, { Component } from "react";

const REGEX_EMAIL = /^(([^<>()[\]\\.,;:\s@\"]+(\.[^<>()[\]\\.,;:\s@\"]+)*)|(\".+\"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
const url = 'https://dac-java.herokuapp.com/api/customer/signup';
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
            messageShowingStyle : 'form-row',
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
        this.setState(prevState =>({
            error :{
                ...prevState.error,
                message : ''
            }
        }));
    }

    showSignIn = ()=>{
        this.setState({isSignUpShowing : false});
        this.props.showSignIn(this.state.isSignUpShowing);
    }

    validateMessage = () => {
        const message = this.state.error.message;
        return message.length === 0;
    }

    validateEmail = () => {
        const email = this.state.email;
        this.setState({messageShowingStyle : 'form-row'});
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
        this.setState({messageShowingStyle : 'form-row'});
        if(password.length === 0){
            this.setState(prevState =>({
                error :{
                    ...prevState.error,
                    password : 'Password can not be empty!'
                }
            }));
            return false;
        }
        else if(password.length < 6){
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
        this.setState({messageShowingStyle : 'form-row'});
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
        this.setState({messageShowingStyle : 'form-row'});
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

        if(this.validateFirstName() && this.validateLastName() && this.validateEmail() && this.validatePassword() && this.validateMessage()){
            const { firstName, lastName, email, password } = this.state;
            fetch(url, {
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
                    this.props.setRole(data.data.role);
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

    onFocus = (event) =>{
        let name = event.target.name;
        this.setState({messageShowingStyle : 'message-hidden'});
        if(name==='email'){
            this.setState(prevState => ({
                error: {
                    ...prevState.error,
                    email: '',
                }
            }));
        }
        if(name==='password'){
            this.setState(prevState => ({
                error: {
                    ...prevState.error,
                    password: ''
                }
            }));
        }
        if(name==='firstName'){
            this.setState(prevState => ({
                error: {
                    ...prevState.error,
                    firstName: ''
                }
            }));
        }
        if(name==='lastName'){
            this.setState(prevState => ({
                error: {
                    ...prevState.error,
                    lastName: ''
                }
            }));
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
                                            onFocus={this.onFocus}/>
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
                                            onFocus={this.onFocus}/>
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
                                    onFocus={this.onFocus}/>
                                </div>

                                <div className="form-row">
                                    <div className="message">
                                        {this.state.error.email}
                                    </div>
                                </div>

                                <div className="form-row">
                                    <input  type="password"
                                            placeholder="Set a Password*"
                                            name="password"
                                            required
                                            onChange={this.onChange}
                                            onBlur={this.validatePassword}
                                            onFocus={this.onFocus}/>
                                </div>

                                <div className="form-row">
                                    <div className="message">
                                        {this.state.error.password}
                                    </div>
                                </div>

                                <div className="form-row">
                                    <button type="submit" onClick={this.formSubmit}>GET STARTED</button>
                                </div>

                                <div className={this.state.messageShowingStyle}>
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
