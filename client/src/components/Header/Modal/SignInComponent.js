import React, { Component } from 'react';
import passwordLength from './../../Utils/Constant';

const REGEX_EMAIL = /^(([^<>()[\]\\.,;:\s@\"]+(\.[^<>()[\]\\.,;:\s@\"]+)*)|(\".+\"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
class SignInComponent extends Component {

    constructor(props) {
        super(props);
        
        this.state ={
            isModalShow : true,
            isSignUpShowing : false,
            email : '',
            password: '',
            error : {
                email : '',
                password : '',
                message : ''               
            }
        }
    }
    
    showSignUp = ()=>{
        this.setState({isSignUpShowing : true});
        this.props.showSignUp(this.state.isSignUpShowing);
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

    formSubmit = (event) =>{
        event.preventDefault();
        if(this.validateEmail() && this.validatePassword()){
            const data = {
                email : this.state.email,
                password : this.state.password
            };

            fetch("http://192.168.1.53:8080/api/customer/signin", {
                method: "POST",
                body: JSON.stringify(data),
                headers: {
                    "Content-Type": "application/json"
                }
            })
            .then(res => res.json())
            .then(data =>{
                if(data.status === 'SUCCESS'){  
                    localStorage.setItem("token", "Bearer "+ data.data.token);   
                    sessionStorage.setItem("name", data.data.firstName +  " " + data.data.lastName);   
                    this.setState({isModalShow : false});      
                    this.props.onHideModal(this.state.isModalShow);
                    this.props.changeAuthenticated();
                }
                else if (data.status === 'FAILED'){
                    this.setState(prevState =>({
                        error :{
                            ...prevState.error,
                            message : data.message
                        }
                    }))
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

    onChange = (event) =>{
        this.setState({ [event.target.name]: event.target.value });
    }

    render() {
        return (
            <div>
                <div className="header-button-contain row">
                    <div className="header-button">
                        <button onClick={this.showSignUp}>Sign up</button>
                    </div>
                    <div className="header-button active">
                        <button>Sign in</button>
                    </div>
                </div>

                <div className="sign-in-content">
                    <div className="header-text">
                        <p>Sign in here</p>
                    </div>
                    <div className="form">
                        <form>
                            <div className="contain">
                                <div className="form-row">
                                    <input type="email" placeholder="Email Address*" name="email" autoFocus required onChange={this.onChange} onBlur={this.validateEmail}/>
                                </div>
                                
                                <div className="form-row">
                                    <div className="message">{this.state.error.email}</div>
                                </div>

                                <div className="form-row">
                                    <input type="password" placeholder="Enter Password*" name="password" required onChange={this.onChange} onBlur={this.validatePassword}/>
                                </div>

                                <div className="form-row">
                                    <div className="message">{this.state.error.password}</div>
                                </div>

                                <div className="form-row">
                                    <button type="submit" onClick={this.formSubmit}>SIGN IN</button>
                                </div>   

                                <div className="form-row">
                                    <div className="message">{this.state.error.message}</div>
                                </div>                         
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        );
    }
}

export default SignInComponent;