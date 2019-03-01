import React, { Component } from 'react';
import {Link} from 'react-router-dom';

class LogoBar extends Component {
    render() {
        return (
            <div className="logo-content">
                <Link to="/">
                    <img src={process.env.PUBLIC_URL + '/assets/images/logo.png'} alt="" />
                </Link>
            </div>
        );
    }
}

export default LogoBar;