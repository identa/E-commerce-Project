import React, { Component } from 'react';
import UserLink from './UserLink';
import ModalComponent from './Modal/ModalComponent';

class AuthenticatedBar extends Component {

    constructor(props) {
        super(props);
        
        this.state = {
            isAuthenticated : false
        }
    }
    
    render() {
        const authenticate = localStorage.getItem("name");
        return (
            <div>
                {
                    authenticate ? <UserLink/> : <ModalComponent isAuthenticated={this.state.isAuthenticated}/>
                }
            </div>
        );
    }
}

export default AuthenticatedBar;