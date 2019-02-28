import React, { Component } from 'react';
import {Link} from 'react-router-dom';

const REGEX_EMAIL = /^(([^<>()[\]\\.,;:\s@\"]+(\.[^<>()[\]\\.,;:\s@\"]+)*)|(\".+\"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
class CustomerCreate extends Component {

    constructor(props) {
        super(props);
        
        this.state = {
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

    render() {
        return (
            <div className="main">
                <div className="container">
                    <div className="profile-content">
                        <div className="col-md-10">
                            <div className="card">
                                <div className="card-body">
                                    <div className="row">
                                <div className="col-md-12">
                                    <h4>Create new user</h4>
                                </div>
                                <hr />                                    
                                </div>
                                    <div className="row">
                                        <div className="col-md-12">
                                            <form>
                                                <div className="form-group row">
                                                    <label htmlFor="username" className="col-4 col-form-label">First Name *</label> 
                                                    <div className="col-8">
                                                        <input type="text" name="firstname" placeholder="First name" className="form-control here" required="required" onChange={this.onChange} onBlur={this.validateFirstName}/>
                                                        <div className="message">
                                                            {this.state.error.firstName}
                                                        </div>
                                                    </div>
                                                    
                                                </div>
                                                <div className="form-group row">
                                                    <label htmlFor="username" className="col-4 col-form-label">Last Name*</label> 
                                                    <div className="col-8">
                                                        <input type="text" name="lastname" placeholder="Last name" className="form-control here" required="required" onChange={this.onChange} onBlur={this.validateLastName}/>
                                                        <div className="message">
                                                            {this.state.error.lastName}
                                                        </div>
                                                    </div>
                                                    
                                                </div>
                                                <div className="form-group row">
                                                    <label htmlFor="select" className="col-4 col-form-label">Role</label> 
                                                    <div className="col-8">
                                                        <select name="select" className="custom-select">
                                                            <option value="ROLE_ADMIN">Admin</option>
                                                            <option value="ROLE_SHOP">Shop</option>
                                                            <option value="ROLE_CUSTOMER">Customer</option>
                                                        </select>
                                                    </div>
                                                </div>
                                                
                                                <div className="form-group row">
                                                    <label htmlFor="username" className="col-4 col-form-label">Email *</label> 
                                                    <div className="col-8">
                                                        <input type="email" name="email" placeholder="Email" className="form-control here" onChange={this.onChange} onBlur={this.validateEmail}/>
                                                        <div className="message">
                                                            {this.state.error.email}
                                                        </div>
                                                    </div>
                                                </div>
                                                <div className="form-group row">
                                                    <label htmlFor="username" className="col-4 col-form-label">Password*</label> 
                                                    <div className="col-8">
                                                        <input type="password" name="password" placeholder="Password" className="form-control here" required="required" onChange={this.onChange} onBlur={this.validatePassword}/>
                                                        <div className="message">
                                                            {this.state.error.password}
                                                        </div>
                                                    </div>
                                                </div>
                                                <div className="form-group row">
                                                    <div className="offset-4 col-8">
                                                        <button className="btn btn-link">
                                                            <i className="fa fa-angle-double-left"></i>
                                                            <Link to="/manage/customer/dashboard">
                                                                Back to list
                                                            </Link>
                                                        </button>
                                                        <button name="submit" type="submit" className="btn btn-success">Create</button>
                                                    </div>                                               
                                                </div>
                                            </form>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        );
    }
}

export default CustomerCreate;