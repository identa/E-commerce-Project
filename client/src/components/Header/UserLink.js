import React, { Component } from 'react';

class UserLink extends Component {
    constructor(props) {
        super(props);
        
        this.state = {
            email : sessionStorage.getItem("email")
        }
    }
    
    render() {
        return (
            <div className="links-bar">
                <a href="#" type="button" data-toggle="modal" data-target="#myModal">
                    Hello {this.state.email} 
                </a>
            </div>
        );
    }
}

export default UserLink;