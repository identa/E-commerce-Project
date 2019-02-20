import React, { Component } from 'react';
import './App.css';
import Header from '../Header/Header';
import Banner from '../Banner/Banner';
import ProductList from '../ProductList/ProductList';
import Footer from '../Footer/Footer';
import BtnMessage from '../Utils/BtnMessage';
import Modal from '../Modal/Modal';

class App extends Component {
  render() {
    return (
      <div className="wrapper">
        <Header/>
        <Banner/>
        <ProductList/>
        <Footer/>
        <BtnMessage/>
        <Modal/>
      </div>
    );
  }
}

export default App;
