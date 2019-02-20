import React, { Component } from "react";

class Signup extends Component {
  render() {
    return (
      <div className="sign-up-content">
        <div className="header-text">
          <p>Sign up for free</p>
        </div>
        <div className="form">
          <form>
            <div className="contain">
              <div className="form-row">
                <div className="form-col-left">
                  <input type="text" placeholder="First Name*" />
                </div>
                <div className="form-col-right">
                  <input type="text" placeholder="Last Name*" />
                </div>
              </div>
              <div className="form-row">
                <input type="email" placeholder="Email Address*" />
              </div>
              <div className="form-row">
                <input type="password" placeholder="Set a Password*" />
              </div>
              <div className="form-row">
                <button type="submit">GET STARTED</button>
              </div>
            </div>
          </form>
        </div>
      </div>
    );
  }
}

export default Signup;
