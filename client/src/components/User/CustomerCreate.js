import React, { Component } from 'react';
import {Link,Redirect} from 'react-router-dom';

const clientId = '78bc0dc37ea9d00';
const urlImgur = 'https://api.imgur.com/3/image';
const url = 'https://dac-java.herokuapp.com/api/admin/create';
const REGEX_EMAIL = /^(([^<>()[\]\\.,;:\s@\"]+(\.[^<>()[\]\\.,;:\s@\"]+)*)|(\".+\"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
class CustomerCreate extends Component {

    constructor(props) {
        super(props);
        
        this.state = {
            firstName : '',
            lastName : '',
            email : '',
            password : '',
            role : 'ROLE_CUSTOMER',
            imageURL : 'https://i.imgur.com/2G9UXB2.png',
            messageShowingStyle : 'message',
            isRedirect : false,
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

    validateMessage = () =>{
        const message = this.state.error.message;
        return message.length === 0;
    }

    validateEmail = () => {
        const email = this.state.email;
        this.setState({messageShowingStyle : 'message'});
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
        this.setState({messageShowingStyle : 'message'});
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
        this.setState({messageShowingStyle : 'message'});
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
        this.setState({messageShowingStyle : 'message'});
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
 
    onFocus = (event) =>{
        let name = event.target.name;
        this.setState({messageShowingStyle : 'message-hidden'});
        if(name ==='firstName'){
            this.setState(prevState => ({
                error: {
                    ...prevState.error,
                    firstName: ''
                }
            }));
        }
        if(name ==='lastName'){
            this.setState(prevState => ({
                error: {
                    ...prevState.error,
                    lastName: ''
                }
            }));
        }
        if(name ==='email'){
            this.setState(prevState => ({
                error: {
                    ...prevState.error,
                    email: ''
                }
            }));
        }
        if(name ==='password'){
            this.setState(prevState => ({
                error: {
                    ...prevState.error,
                    password: ''
                }
            }));
        }
    }

    loadFile = (event) =>{
        let reader = new FileReader();  
        let file = event.target.files[0];
        
        reader.onloadend = () =>{
            var image = document.getElementById('preview');
            image.src = reader.result;
        };
        reader.readAsDataURL(file);
    }

    getImageUrl = async () =>{
        let file = document.querySelector("input[type='file']").files[0];
        let link = '';
        if(file !== undefined){
                
            const formData = new FormData();
            formData.append('image', file);

            const respone = await fetch(urlImgur, { 
                                                    method : 'POST',
                                                    headers :{'Authorization' : `Client-ID ${clientId}` },
                                                    body : formData
                                                });
            const json = await respone.json();
            link = json.data.link;
        }
        return link;
    }

    formSubmit = async (event) =>{
        event.preventDefault();
        if(this.validateFirstName() && this.validateLastName() && this.validateEmail() && this.validatePassword() && this.validateMessage()){

            const imgURL = await this.getImageUrl();
            this.setState({imageURL : imgURL});
            const data = {
                firstName : this.state.firstName,
                lastName : this.state.lastName,
                email : this.state.email,
                password : this.state.password,
                imageURL : this.state.imageURL,
                status : 'ACTIVE',
                role : this.state.role
            }
            
            const response = await fetch(url, {
                                    method : 'POST',
                                    headers : {
                                        'Content-Type' : 'application/json',
                                        'Authorization' : localStorage.token
                                    },
                                    body : JSON.stringify(data)
                                });
            let result = await response.json();
            if(result.status === 'SUCCESS'){
                this.setState({isRedirect : true});
            }
            else if(result.status === 'FAILED'){
                this.setState(prevState =>({
                    error :{
                        ...prevState.error,
                        message : result.message
                    }
                }));
            }
        }
    }

    render() {
        const isRedirect = this.state.isRedirect;

        if(isRedirect){
            return(
                <Redirect push to="/manage/customer/dashboard"/>
            )
        }
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
                                            <form onSubmit={(event)=>this.formSubmit(event)}>
                                                <div className="form-group row">
                                                    <label className="col-4 col-form-label">First Name *</label> 
                                                    <div className="col-8">
                                                        <input type="text" name="firstName" placeholder="First name" className="form-control" required="required" onChange={this.onChange} onFocus={this.onFocus} onBlur={this.validateFirstName}/>
                                                        <div className="message">
                                                            {this.state.error.firstName}
                                                        </div>
                                                    </div>
                                                    
                                                </div>
                                                
                                                <div className="form-group row">
                                                    <label className="col-4 col-form-label">Last Name *</label> 
                                                    <div className="col-8">
                                                        <input type="text" name="lastName" placeholder="Last name" className="form-control" required="required" onChange={this.onChange} onFocus={this.onFocus} onBlur={this.validateLastName}/>
                                                        <div className="message">
                                                            {this.state.error.lastName}
                                                        </div>
                                                    </div>
                                                    
                                                </div>                                           
                                                
                                                <div className="form-group row">
                                                    <label className="col-4 col-form-label">Email *</label> 
                                                    <div className="col-8">
                                                        <input type="email" name="email" placeholder="Email" className="form-control" required="required" onChange={this.onChange} onFocus={this.onFocus} onBlur={this.validateEmail}/>
                                                        <div className="message">
                                                            {this.state.error.email}
                                                        </div>
                                                    </div>
                                                </div>
                                                
                                                <div className="form-group row">
                                                    <label className="col-4 col-form-label">Password *</label> 
                                                    <div className="col-8">
                                                        <input type="password" name="password" placeholder="Password" className="form-control" required="required" onChange={this.onChange} onFocus={this.onFocus} onBlur={this.validatePassword}/>
                                                        <div className="message">
                                                            {this.state.error.password}
                                                        </div>
                                                    </div>
                                                </div>

                                                <div className="form-group row">
                                                    <label htmlFor="select" className="col-4 col-form-label">Role *</label> 
                                                    <div className="col-8">
                                                        <select name="role" className="custom-select" value={this.state.role} onChange={this.onChange}>
                                                            <option value="ROLE_CUSTOMER">Customer</option>
                                                            <option value="ROLE_SHOP">Shop</option>
                                                        </select>
                                                    </div>
                                                </div>

                                                <div className="form-group row">
                                                    <label className="col-4 col-form-label">Avatar</label> 
                                                    <div className="col-4">
                                                        <input type="file" accept="image/*" name="imgURL" onChange={(e)=>this.loadFile(e)}/>
                                                    </div>
                                                    
                                                </div>
                                                <div className="form-group row">
                                                    <div className="offset-4 col-4 img-preview">
                                                        <img src='' id='preview' alt=''/>
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

                                                <div className="form-group row">
                                                    <div className="offset-4 col-8">
                                                        <div className={this.state.messageShowingStyle}>
                                                            {this.state.error.message}
                                                        </div>
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