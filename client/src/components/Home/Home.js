import React, { Component } from 'react';
import ProductList from './ProductList';
import BannerComponent from '../Banner/BannerComponent';

class Home extends Component {
    render() {
        return (
            <>
                <BannerComponent/>
                <ProductList {...this.props}/>
            </>
            
        );
    }
}

export default Home;