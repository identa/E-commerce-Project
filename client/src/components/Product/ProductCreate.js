import React, { Component } from 'react';
import {Link,Redirect} from 'react-router-dom';

const clientId = '78bc0dc37ea9d00';
const urlImgur = 'https://api.imgur.com/3/image';
const urlCreateProduct = 'https://dac-java.herokuapp.com/api/admin/createProduct';
const urlGetCategories = 'https://dac-java.herokuapp.com/api/customer/getCategoryTree';
const urlGetShop = 'https://dac-java.herokuapp.com/api/admin/getShop';

class ProductCreate extends Component {
    constructor(props) {
        super(props);
        
        this.state = {
            name : '',
            quantity : 1,
            originalPrice : 0.0,
            discount : 0,
            status : 'PAUSE',
            description : '',
            category : '1',
            shop : '0',
            categoryList : [],
            shopList : [],
            productImageUrl : 'https://i.imgur.com/YYJJRJh.jpg',
            messageShowingStyle : 'message',
            isRedirect : false,
            error : {
                name : '',
                price : '',
                message : ''               
            }
        }
    }
    
    componentDidMount() {
        fetch(urlGetCategories, {
            method : 'GET',
            headers : {
                'Content-Type' : 'application/json'
            }
        })
        .then(res=>res.json())
        .then(data =>{
            if(data.status === 'SUCCESS'){
                this.setState({categoryList : data.data});                
            }
            else if(data.status === 'FAILED'){
                console.log(data.message);
            }
        })

        fetch(urlGetShop, {
            method : 'GET',
            headers : {
                'Content-Type' : 'application/json',
                'Authorization' : localStorage.token
            }
        })
        .then(res=>res.json())
        .then(data =>{
            if(data.status === 'SUCCESS'){
                this.setState({shopList : data.data});                
            }
            else if(data.status === 'FAILED'){
                console.log(data.message);
            }
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

    onFocus = (event) =>{
        let name = event.target.name;
        this.setState({messageShowingStyle : 'message-hidden'});
        if(name ==='name'){
            this.setState(prevState => ({
                error: {
                    ...prevState.error,
                    name: ''
                }
            }));
        }
        if(name ==='originalPrice'){
            this.setState(prevState => ({
                error: {
                    ...prevState.error,
                    price: ''
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
        if(this.validateName() && this.validatePrice() && this.validateMessage()){
            const imgURL = await this.getImageUrl();
            this.setState({productImageUrl : imgURL});

            const data = {
                name : this.state.name,
                status : 'PAUSE',
                description : this.state.description,
                quantity : this.state.quantity,
                originalPrice : this.state.originalPrice,
                discount : this.state.discount,
                productImageURL : this.state.productImageUrl,
                categoryID : this.state.category,
                shopID : this.state.shop
            }

            const response = await fetch(urlCreateProduct,{
                method : 'POST',
                headers :{
                    'Content-Type' : 'application/json',
                    'Authorization' : localStorage.token
                },
                body : JSON.stringify(data)
            })
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

    validateMessage = () =>{
        const message = this.state.error.message;
        return message.length === 0;
    }

    validateName = () =>{
        const name = this.state.name;
        this.setState({messageShowingStyle : 'message'});
        if(name.length === 0){
            this.setState(prevState =>({
                error :{
                    ...prevState.error,
                    name : 'Product name can not be empty!'
                }
            }));
            return false;
        }
        else{
            this.setState(prevState =>({
                error :{
                    ...prevState.error,
                    name : ''
                }
            }));
            return true;
        }
    }

    validatePrice = () =>{
        const originalPrice = this.state.originalPrice;
        this.setState({messageShowingStyle : 'message'});
        if(originalPrice === 0){
            this.setState(prevState =>({
                error :{
                    ...prevState.error,
                    price : 'Price must be greater than 0'
                }
            }));
            return false;
        }
        else{
            this.setState(prevState =>({
                error :{
                    ...prevState.error,
                    price : ''
                }
            }));
            return true;
        }   
    }

    render() {
        const isRedirect = this.state.isRedirect;

        if(isRedirect){
            return(
                <Redirect push to="/manage/product/dashboard"/>
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
                                    <h4>Create new product</h4>
                                </div>
                                <hr />                                    
                                </div>
                                    <div className="row">
                                        <div className="col-md-12">
                                            <form onSubmit={(event)=>this.formSubmit(event)}>

                                                <div className="form-group row">
                                                    <label className="col-4 col-form-label">Name *</label> 
                                                    <div className="col-8">
                                                        <input type="text" 
                                                               name="name" 
                                                               placeholder="Name" 
                                                               className="form-control" 
                                                               required="required" 
                                                               onChange={this.onChange} 
                                                               onFocus={this.onFocus} 
                                                               onBlur={this.validateName}/>
                                                        <div className="message">
                                                            {this.state.error.name}
                                                        </div>
                                                    </div> 
                                                </div>
                                                
                                                <div className="form-group row">
                                                    <label className="col-4 col-form-label">Quantity *</label> 
                                                    <div className="col-8">
                                                        <input type="number" 
                                                               name="quantity" 
                                                               placeholder="Quantity" 
                                                               className="form-control" 
                                                               value={this.state.quantity} 
                                                               min="1" 
                                                               required="required" 
                                                               onChange={this.onChange} 
                                                               onFocus={this.onFocus}/>
                                                       
                                                    </div>
                                                    
                                                </div>                                           
                                                
                                                <div className="form-group row">
                                                    <label className="col-4 col-form-label">Price *</label> 
                                                    <div className="col-8">
                                                        <input type="number" 
                                                               name="originalPrice" 
                                                               placeholder="Price" 
                                                               value={this.state.originalPrice}
                                                               min="0" 
                                                               className="form-control" 
                                                               step="0.01" 
                                                               required="required" 
                                                               onChange={this.onChange} 
                                                               onFocus={this.onFocus} 
                                                               onBlur={this.validatePrice}/>
                                                        <div className="message">
                                                            {this.state.error.price}
                                                        </div>
                                                    </div>
                                                </div>

                                                <div className="form-group row">
                                                    <label className="col-4 col-form-label">Discount</label> 
                                                    <div className="col-8">
                                                        <input type="number" 
                                                               name="discount" 
                                                               placeholder="Discount" 
                                                               value={this.state.discount} 
                                                               min="0" 
                                                               max="100" 
                                                               className="form-control" 
                                                               step="0.01" 
                                                               required="required" 
                                                               onChange={this.onChange} 
                                                               onFocus={this.onFocus}/>                                                       
                                                    </div>
                                                </div>                                                                                           

                                                <div className="form-group row">
                                                    <label className="col-4 col-form-label">Status *</label> 
                                                    <div className="col-8">
                                                        <select name="status" className="custom-select" value={this.state.status} onChange={this.onChange}>
                                                            <option value="PAUSE">PAUSE</option>
                                                            <option value="ACTIVE">ACTIVE</option>
                                                        </select>
                                                    </div>
                                                </div>

                                                <div className="form-group row">
                                                    <label className="col-4 col-form-label">Description </label> 
                                                    <div className="col-8">
                                                        <textarea name="description" 
                                                                  placeholder="Description" 
                                                                  rows="4" 
                                                                  className="form-control" 
                                                                  onChange={this.onChange}>
                                                        </textarea>
                                                    </div>
                                                </div>

                                                <div className="form-group row">
                                                    <label className="col-4 col-form-label">Category *</label> 
                                                    <div className="col-8">
                                                        <select name="category" className="custom-select" onChange={this.onChange}>
                                                        {
                                                            this.state.categoryList.map((value,key)=>{
                                                                return (<option key={key} value={value.id}>{value.name}</option>)
                                                            })
                                                        } 
                                                        </select>
                                                    </div>
                                                </div>

                                                <div className="form-group row">
                                                    <label className="col-4 col-form-label">Shop *</label> 
                                                    <div className="col-8">
                                                        <select name="shop" className="custom-select" onChange={this.onChange}>
                                                        {
                                                            this.state.shopList.map((value,key)=>{
                                                                return (<option key={key} value={value.id}>{value.name}</option>)
                                                            })
                                                        } 
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
                                                            <Link to="/manage/product/dashboard">
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

export default ProductCreate;