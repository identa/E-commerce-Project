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

    render() {
        const role = localStorage.role;
        const name = localStorage.firstName + ' ' +  localStorage.lastName;

        return (
            <div className="links-bar">
                <Button variant="link" className="menu-action-link">
                    
                        <img src={process.env.PUBLIC_URL + '/assets/images/user.png'} className="img-avatar" alt=""/> 
  
                    Hello {name}
                </Button>
                <div className="menu-action">
                    <ul>
                        <li>
                            <Link to="/customer/profile">User Information</Link>
                        </li>                    
                        {                          
                            role === 'ROLE_ADMIN' ? (
                                <>
                                    <li><Link to="/manage/customer/dashboard">Management User</Link></li>
                                    <li><Link to="/manage/product/dashboard">Management Product</Link></li>
                                </>
                            ) : null
                        }
                        <li>
                            <Link to="/" onClick={this.logout}>Logout</Link>
                        </li>
                    </ul>
                </div>
            </div>
        );
    }
}

export default UserLink;