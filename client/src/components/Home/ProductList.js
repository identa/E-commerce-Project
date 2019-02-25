import React, { Component } from 'react';
import ProductItem from './ProductItem';

class ProductList extends Component {
    render() {
        return (
            <div className="product-container">
        <h2>
          <u>Collections</u>
        </h2>
        <div className="product-content row">
          <ProductItem/>

          <ProductItem/>

          <ProductItem/>
          
          <ProductItem/>

          <ProductItem/>

          <ProductItem/>

          <ProductItem/>
          
          <ProductItem/>
        </div>
      </div>
        );
    }
}

export default ProductList;