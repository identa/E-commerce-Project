import React, { Component } from "react";
class Signin extends Component {
    constructor(props) {
        super(props);
        
        this.state = {
            email : '',
            password :'',
            message : ''
        }
    }
    
    onChange = e => {
        this.setState({ [e.target.name]: e.target.value });
    };

    formSubmit = event => {
        event.preventDefault();
        const data = {
            email : this.state.email,
            password : this.state.password
        };
        fetch("http://192.168.1.53:8080/api/signin", {
          method: "POST",
          body: JSON.stringify(data),
          headers: {
            "Content-Type": "application/json"
          }
        })
        .then(res => res.json())
        .then(data =>{
            if(data.status === 'SUCCESS'){  
                sessionStorage.setItem("email",this.state.email);
                this.setState({message : data.message});                
            }
            else if (data.status === 'FAILED'){
                this.setState({message : data.message});
            }
        })
        .catch(error => this.setState({message : error}));
    };

    render() {
        return (
        <div className="sign-in-content">
            <div className="header-text">
                <p>Sign in here</p>
            </div>
            <div className="form">
                <form method="POST" onSubmit={this.formSubmit}>
                    <div className="contain">
                        <div className="form-row">
                            <input type="email" placeholder="Email Address*" name="email" autoFocus required onChange={this.onChange}/>
                        </div>
                        <div className="form-row">
                            <input type="password" placeholder="Enter Password*" name="password" required onChange={this.onChange}/>
                        </div>
                        <div className="form-row">
                            <button type="submit">SIGN IN</button>
                        </div>

                        <div className="message">
                            {this.state.message}
                        </div>
                    </div>
                </form>
            </div>
        </div>
        );
    }
}

export default Signin;
