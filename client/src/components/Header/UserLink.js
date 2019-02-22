import React, { Component } from 'react';

class UserLink extends Component {
    constructor(props) {
        super(props);
        
        this.state = {
            name : localStorage.getItem("name")
        }
    }
    
    render() {
        return (
            <div className="links-bar">
                <a href="#" type="button" className="menu-action-link">
                    Hello {this.state.name} 
                </a>
                    <div className="menu-action">
                        <ul>
                            <li>
                                <a href="#">User Information</a>
                            </li>
                            <li>
                                <a href="#">Management Campaign</a>
                            </li>
                            <li>
                                <a href="#">Logout</a>
                            </li>
                        </ul>
                    </div>
            </div>
        );
    }
}

export default UserLink;