import React, { Component } from "react";

class Signin extends Component {
  render() {
    return (
      <div className="sign-in-content">
        <div className="header-text">
          <p>Sign in here</p>
        </div>
        <div className="form">
          <form method="POST">
            <div className="contain">
              <div className="form-row">
                <input type="email" placeholder="Email Address*" />
              </div>
              <div className="form-row">
                <input type="password" placeholder="Enter Password*" />
              </div>
              <div className="form-row">
                <button type="submit">SIGN IN</button>
              </div>
            </div>
          </form>
        </div>
      </div>
    );
  }
}

export default Signin;
