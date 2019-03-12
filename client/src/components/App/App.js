import React, { Component } from 'react';
import './App.css';
import Header from '../Header/Header';
import Footer from '../Footer/Footer';
import BtnMessage from '../Utils/BtnMessage';
import RouterURL from '../RouterURL/RouterURL';
import {BrowserRouter as Router} from 'react-router-dom';
class App extends Component {
    render() {
        return (
            <Router>
                <>
                    <Header/> 
                    <RouterURL/>  
                    <Footer/>
                    <BtnMessage/>
                </>
            </Router>
        );
  }
}

export default App;
