import React, { Component } from "react";

class LinkText extends Component {
    render() {
        return (
        <div className="links-bar">
            <a href="#" type="button" data-toggle="modal" data-target="#myModal">
            Sign in/ Sign up
            </a>
        </div>
        );
    }
}

export default LinkText;
