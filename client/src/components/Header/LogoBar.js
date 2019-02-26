import React, { Component } from 'react';
import {Link} from 'react-router-dom';

class LogoBar extends Component {
    render() {
        return (
            <div className="logo-content">
                <Link to="/">
                    <img src="./assets/images/logo.png" alt="" />
                </Link>
            </div>
        );
    }
}

export default LogoBar;