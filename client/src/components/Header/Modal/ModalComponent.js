import React, { Component } from 'react';
import {Modal,Button} from 'react-bootstrap';
import SignUpComponent from './SignUpComponent';
import SignInComponent from './SignInComponent';
class ModalComponent extends Component {

    constructor(props) {
        super(props);
        
        this.state ={
            isModalShowing : false,
            isSignUpShowing : true
        }
    }
    
    hanleShow = () => {
        this.setState({isModalShowing : true});
    }

    handleClose = () => {
        this.setState({isModalShowing: false });
    }

    showSignUp = () =>{
        this.setState({isSignUpShowing : true});
    }

    showSignIn = () =>{
        this.setState({isSignUpShowing : false});
    }

    render() {
        const isSignUpShowing = this.state.isSignUpShowing;

        return (
            <div className="links-bar">
                <Button variant="link" onClick={this.hanleShow}>Sign up/ Sign in</Button>

                <Modal show={this.state.isModalShowing} onHide={this.handleClose} > 
                    <Modal.Header closeButton/>

                    <Modal.Body>
                        <div>
                            {
                                isSignUpShowing ? <SignUpComponent onHideModal={this.handleClose} showSignIn={this.showSignIn}/> : <SignInComponent onHideModal={this.handleClose} showSignUp={this.showSignUp}/>
                            }
                        </div>
                    </Modal.Body>
                </Modal>
            </div>
        );
    }
}

export default ModalComponent;