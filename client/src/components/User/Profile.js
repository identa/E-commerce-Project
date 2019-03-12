import React, { Component } from 'react';
import {Link,Redirect} from 'react-router-dom';

const clientId = '78bc0dc37ea9d00';
const urlImgur = 'https://api.imgur.com/3/image';
const urlGetProfile = 'https://dac-java.herokuapp.com/api/customer/getById';
const urlGetShopProfile = 'https://dac-java.herokuapp.com/api/shop/getInfoById';
const urlEditProfile = 'https://dac-java.herokuapp.com/api/customer/update';
const urlEditShopProfile = 'https://dac-java.herokuapp.com/api/shop/update';
class Profile extends Component {

    constructor(props) {
        super(props);
        
        this.state = {
            uid : '',
            firstName : '',
            lastName : '',
            email : '',
            password : '',
            imageURL : '',
            messageShowingStyle : 'message',
            isRedirect : false,
            error : {
                firstName : '',
                lastName : '',
                password : '',
                message : ''       
            }
        }
    }

    componentDidMount() {
        let url = '';
        if(localStorage.role === 'ROLE_SHOP'){
            url = urlGetShopProfile;
        }
        else if(localStorage.role === 'ROLE_CUSTOMER'){
            url = urlGetProfile;
        }
        fetch(url ,{
            method : 'GET',
            headers : {
                'Content-Type' : 'application/json',
                'Authorization' : localStorage.token
            }
        })
        .then(res => res.json())
        .then(data =>{
            this.setState({
                uid : data.data.id,
                firstName : data.data.firstName,
                lastName : data.data.lastName,
                email : data.data.email,
                imageURL : data.data.imageURL
            });
        })
        .catch(err =>{
            console.log(err);
        })
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

    validatePassword = () =>{
        const password = this.state.password;
        this.setState({messageShowingStyle : 'message'});
        if(password === null){
            return true;
        }
        else if(password.length > 0 && password.length < 6){
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

        if(this.validateFirstName() && this.validateLastName() && this.validatePassword() && this.validateMessage()){
            const imgURL = await this.getImageUrl();
            if(imgURL !== ''){
                this.setState({imageURL : imgURL});
            }
            const data ={
                id: this.state.uid,
                firstName : this.state.firstName,
                lastName : this.state.lastName,
                password : this.state.password,
                imageURL : this.state.imageURL
            }

            let url = '';
            if(localStorage.role === 'ROLE_SHOP'){
                url = urlEditShopProfile;
            }
            else if(localStorage.role === 'ROLE_CUSTOMER'){
                url = urlEditProfile;
            }
            const response = await fetch(url ,{
                method : 'PUT',
                headers :{ 
                    'Content-Type' : 'application/json',
                    'Authorization' : localStorage.token
                },
                body : JSON.stringify(data)
            })
            const result = await response.json();
            if(result.status === 'SUCCESS'){
                this.setState({isRedirect : true});
            }
            else if (result.status === 'FAILED'){
                this.setState(prevState =>({
                    error :{
                        ...prevState.error,
                        message : data.message
                    }
                }));
            }
        }

    }
    render() {
        const isRedirect = this.state.isRedirect;

        if(isRedirect){
            return(
                <Redirect to="/"/>
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
                                    <h4>User profile</h4>
                                </div>
                                <hr />                                    
                                </div>
                                    <div className="row">
                                        <div className="col-md-12">
                                            <form onSubmit={this.formSubmit}>
                                                <div className="form-group row">
                                                    <label htmlFor="username" className="col-4 col-form-label">First Name *</label> 
                                                    <div className="col-8">
                                                        <input id="firstname" name="firstName" value={this.state.firstName} placeholder="First name" className="form-control" required="required" type="text" onChange={this.onChange} onFocus={this.onFocus}/>
                                                        <div className="message">
                                                            {this.state.error.firstName}
                                                        </div>
                                                    </div>
                                                </div>
                                                <div className="form-group row">
                                                    <label htmlFor="username" className="col-4 col-form-label">Last Name*</label> 
                                                    <div className="col-8">
                                                        <input id="lastname" name="lastName" value={this.state.lastName} placeholder="Last name" className="form-control" required="required" type="text" onChange={this.onChange} onFocus={this.onFocus}/>
                                                        <div className="message">
                                                            {this.state.error.lastName}
                                                        </div>
                                                    </div>
                                                </div>
                                                
                                                <div className="form-group row">
                                                    <label htmlFor="username" className="col-4 col-form-label">Email Name*</label> 
                                                    <div className="col-8">
                                                        <input id="email" value={this.state.email} className="form-control" readOnly />                             
                                                    </div>
                                                </div>
                                                <div className="form-group row">
                                                    <label htmlFor="username" className="col-4 col-form-label">Password*</label> 
                                                    <div className="col-8">
                                                        <input id="password" name="password" placeholder="Password" className="form-control" type="password" onChange={this.onChange} onFocus={this.onFocus}/>
                                                        <div className="message">
                                                            {this.state.error.password}
                                                        </div>
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
                                                        <img src={this.state.imageURL} id='preview' alt=''/>
                                                    </div>
                                                </div>
                                                <div className="form-group row">
                                                    <div className="offset-4 col-8">
                                                        <Link className="btn" to="/">Back to Home</Link>
                                                        <button name="submit" type="submit" className="btn btn-primary">Update</button>
                                                        <div className="message">
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

export default Profile;