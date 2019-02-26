import React, { Component } from 'react';
import {Button} from 'react-bootstrap';
class UserLink extends Component {
    constructor(props) {
        super(props);
        
        this.state = {
            name : sessionStorage.getItem("name")
        }
    }
    
    logout = () =>{
        if(localStorage.getItem("token")){
            localStorage.removeItem("token");
            sessionStorage.removeItem("name");
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
                            <a href="javascript:void(0)">User Information</a>
                        </li>
                        <li>
                            <a href="javascript:void(0)">Management Campaign</a>
                        </li>
                        <li>
                            <a href="javascript:void(0)" onClick={this.logout}>Logout</a>
                        </li>
                    </ul>
                </div>
            </div>
        );
    }
}

export default UserLink;