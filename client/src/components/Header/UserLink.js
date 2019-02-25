import React, { Component } from 'react';
import {Button} from 'react-bootstrap';
class UserLink extends Component {
    constructor(props) {
        super(props);
        
        this.state = {
            name : localStorage.getItem("name")
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
        return (
            <div className="links-bar">
                <Button variant="link" className="menu-action-link">Hello {this.state.name}</Button>
                <div className="menu-action">
                    <ul>
                        <li>
                            <a href="#">User Information</a>
                        </li>
                        <li>
                            <a href="#">Management Campaign</a>
                        </li>
                        <li>
                            <a href="#" onClick={this.logout}>Logout</a>
                        </li>
                    </ul>
                </div>
            </div>
        );
    }
}

export default UserLink;