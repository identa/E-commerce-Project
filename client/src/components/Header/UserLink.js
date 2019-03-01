import React, { Component } from 'react';
import {Button} from 'react-bootstrap';
import {Link} from 'react-router-dom';
class UserLink extends Component {
    constructor(props) {
        super(props);
        
        this.state = {
            name : localStorage.getItem("name"), 
            role : ''
        }
    }
    
    logout = () =>{
        if(localStorage.getItem("token")){
            localStorage.removeItem("token");
            localStorage.removeItem("name");
            this.props.changeAuthenticated();
        }
    }

    render() {
        const role = this.props.getRole();
        return (
            <div className="links-bar">
                <Button variant="link" className="menu-action-link">
                    
                        <img src={process.env.PUBLIC_URL + '/assets/images/user.png'} className="img-avatar" alt=""/> 
  
                    Hello {this.state.name}
                </Button>
                <div className="menu-action">
                    <ul>
                        <li>
                            <Link to="/customer/profile">User Information</Link>
                        </li>                    
                        {                            
                            role === 'ROLE_ADMIN' || role === 'ROLE_SHOP' ? (
                                <li><Link to="/manage/customer/dashboard">Management User</Link></li>
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