import React, { Component } from "react";

const REGEX_PASSWORD = '^.*(?=.{8,})(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&+=*])(?=\\S+).*';

class Signup extends Component {
  constructor(props) {
    super(props);
    this.state = {
      firstName: '',
      lastName: '',
      email: '',
      password: '',
      message : ''
    };
  }
  onChange = e => {
    this.setState({ [e.target.name]: e.target.value });
  };
  formSubmit = event => {
    event.preventDefault();

    const { firstName, lastName, email, password } = this.state;
    fetch("http://192.168.1.53:8080/api/signup", {
      method: "POST",
      body: JSON.stringify({
        firstName: firstName,
        lastName: lastName,
        email: email,
        password: password
      }),
      headers: {
        "Content-Type": "application/json"
      }
    })
    .then(res => res.json())
    .then(data =>{
        if(data.status === 'SUCCESS'){
            
            this.setState({message : data.message});
        }
        else if( data.status === 'FAILED'){
            this.setState({message : data.message});
        }
        })
    .catch(error => {
        this.setState({message : error});
    });
  };
  render() {
    return (
      <div className="sign-up-content">
        <div className="header-text">
          <p>Sign up for free</p>
        </div>
        <div className="form">
          <form method="POST" onSubmit={this.formSubmit}>
            <div className="contain">
              <div className="form-row">
                <div className="form-col-left">
                  <input
                    type="text"
                    placeholder="First Name*"
                    name="firstName"
                    required autoFocus
                    onChange={this.onChange}
                  />
                </div>
                <div className="form-col-right">
                  <input
                    type="text"
                    placeholder="Last Name*"
                    name="lastName"
                    required
                    onChange={this.onChange}
                  />
                </div>
              </div>
              <div className="form-row">
                <input
                  type="email"
                  placeholder="Email Address*"
                  name="email"
                  required
                  onChange={this.onChange}
                />
              </div>
              <div className="form-row">
                <input
                  type="password"
                  placeholder="Set a Password*"
                  name="password"
                  required
                  onChange={this.onChange}
                />
              </div>
              <div className="form-row">
                <button type="submit">GET STARTED</button>
              </div>
              <div className="form-row">
                <div className="message">
                    {this.state.message}
                </div>               
              </div>
            </div>
          </form>
        </div>
      </div>
    );
  }
}

export default Signup;
