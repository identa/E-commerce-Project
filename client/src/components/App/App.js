import React, { Component } from 'react';
import './App.css';
import Header from '../Header/Header';
import Footer from '../Footer/Footer';
import BtnMessage from '../Utils/BtnMessage';
import RouterURL from '../RouterURL/RouterURL';
import {BrowserRouter as Router} from 'react-router-dom';
class App extends Component {
    constructor(props) {
        super(props);
        
        this.state = {
            imageURL : localStorage.imageURL
        }
    }
    
    changeImageAvatar = (imageURL) => {
        this.setState({imageURL : imageURL});
    }

    getImageAvatar = () =>{
        return localStorage.imageURL;
    }
    render() {
        return (
            <Router>
                <>
                    <Header changeImageAvatar={this.changeImageAvatar} getImageAvatar={this.getImageAvatar}/> 
                    <RouterURL changeImageAvatar={this.changeImageAvatar}/>  
                    <Footer/>
                    <BtnMessage/>
                </>
            </Router>
        );
  }
}

export default App;
