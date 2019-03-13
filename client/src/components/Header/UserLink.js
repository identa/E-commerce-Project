import React, { Component } from 'react';
import {Button} from 'react-bootstrap';
import {Link,Redirect} from 'react-router-dom';
const url = 'https://dac-java.herokuapp.com/api/customer/signout';
class UserLink extends Component {
    constructor(props) {
        super(props);
        
        this.state = {
            name : '',
            role : '',
        }
    }
    
    logout = () =>{
        if(localStorage.getItem("token")){
            fetch(url, {
                method : 'DELETE',
                headers : {
                    'Content-Type' : 'application/json',
                    'Authorization' : localStorage.token
                },
            })
            .then(res=>res.json())
            .then(data => {
                if(data.status === 'SUCCESS'){
                    localStorage.clear();
                    this.props.changeAuthenticated();
                }
            });
        }
    }

    renderLi = () =>{
        const role = localStorage.role;
        switch(role){
            case 'ROLE_ADMIN':
                return (<>
                            <li><Link to="/customer/profile">User Information</Link></li>
                            <li><Link to="/manage/customer/dashboard">Management User</Link></li>
                            <li><Link to="/manage/campaign/dashboard">Management Campaign</Link></li>
                            <li><Link to="/manage/product/dashboard">Management Product</Link></li>
                            <li><Link to="/" onClick={this.logout}>Logout</Link></li>
                        </>)
            case 'ROLE_SHOP':
                return (<>
                    <li><Link to="/customer/profile">User Information</Link></li>
                    <li><Link to="/manage/campaign/dashboard">Management Campaign</Link></li>
                    <li><Link to="/manage/product/dashboard">Management Product</Link></li>
                    <li><Link to="/" onClick={this.logout}>Logout</Link></li>
                </>)
            default :
                return (<>
                    <li><Link to="/customer/profile">User Information</Link></li>                    
                    <li><Link to="/" onClick={this.logout}>Logout</Link></li>
                </>)

        }
    }

    render() {
        const name = localStorage.firstName + ' ' +  localStorage.lastName;
        const imageURL = localStorage.imageURL;
        return (
            <div className="links-bar">
                <Button variant="link" className="menu-action-link">
                        <img src={imageURL} className="img-avatar" alt=""/> 
                    Hello {name}
                </Button>
                <div className="menu-action">
                    <ul>
                        {this.renderLi()}
                    </ul>
                </div>
            </div>
        );
    }
}

export default UserLink;