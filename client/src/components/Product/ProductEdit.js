import React, { Component } from 'react';
import {Link,Redirect} from 'react-router-dom';

const clientId = '78bc0dc37ea9d00';
const urlImgur = 'https://api.imgur.com/3/image';
const urlEditProduct = 'https://dac-java.herokuapp.com/api/admin/updateProduct';
class ProductEdit extends Component {

    constructor(props) {
        super(props);
        
        this.state = {
            isRedirect : false,
        }
    }
    
    onChange = (event)=>{

    }

    onFocus = ()=>{

    }
    
    validateMessage = () =>{

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

    formSubmit = (event) =>{
        event.preventDefault();
    }

    render() {
        const isRedirect = this.state.isRedirect;

        if(isRedirect){
            return (
                <Redirect to='/manage/product/dashboard'/>
            )
        }
        return (
            <div>
                
            </div>
        );
    }
}

export default ProductEdit;