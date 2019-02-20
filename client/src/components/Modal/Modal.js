import React, { Component } from "react";
import Signin from "./Signin/Signin";
import Signup from "./Signup/Signup";

class Modal extends Component {
    constructor(props) {
        super(props);
        this.state = {
            isShowing : true
        }
    }
    
    showComponent = () => {
        if(this.state.isShowing){
            return (
                <Signup/>
            )
        }
        else{
            return(
                <Signin/>
            )
        }
    }

    showButton = () =>{
        if(this.state.isShowing){
            return(
                <div className="header-button-contain row">
                    <div className="header-button active">
                        <button onClick={this.showSignup}>Sign up</button>
                    </div>
                    <div className="header-button">
                        <button onClick={this.showSignin}>Sign in</button>
                    </div>
                </div>
            )
        }
        else{
            return(
                <div className="header-button-contain row">
                    <div className="header-button">
                        <button onClick={this.showSignup}>Sign up</button>
                    </div>
                    <div className="header-button active">
                        <button onClick={this.showSignin}>Sign in</button>
                    </div>
                </div>
            )
        }
    }
    showSignup = () =>{
        this.setState({isShowing : true});
    }

    showSignin = () =>{
        this.setState({isShowing : false});
    }
    
    render() {
        return (
        <div id="myModal" className="modal fade" role="dialog">
            <div className="modal-dialog">
            {/* Modal content*/}
            <div className="modal-content">
                <div className="modal-header">
                <button type="button" className="close" data-dismiss="modal">
                    ×
                </button>
                </div>
                <div className="modal-body">

                {this.showButton()}

                {this.showComponent()}
                </div>
            </div>
            </div>
        </div>
        );
    }
}

export default Modal;
