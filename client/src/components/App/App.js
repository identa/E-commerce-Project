import React, { Component } from 'react';
import './App.css';
import Header from '../Header/Header';
import Banner from '../Banner/Banner';
import ProductList from '../ProductList/ProductList';
import Footer from '../Footer/Footer';
import BtnMessage from '../Utils/BtnMessage';

class App extends Component {
  render() {
    return (
      <div className="wrapper">
        <Header/>
        <Banner/>
        <ProductList/>
        <Footer/>
        <BtnMessage/>
      </div>
    );
  }
}

export default App;
