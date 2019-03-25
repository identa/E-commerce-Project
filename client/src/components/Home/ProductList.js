import React, { Component } from 'react';
import ProductItem from './ProductItem';

class ProductList extends Component {
    render() {
        return (
            <div className="product-container">
                <div className="container">
                    <h2>
                        <u>Collections</u>
                    </h2>
                    <div className="product-content row">
                        <ProductItem />

                        <ProductItem />

                        <ProductItem />

                        <ProductItem />

                        <ProductItem />

                        <ProductItem />

                        <ProductItem />

                        <ProductItem />

                        <ProductItem />

                        <ProductItem />

                        <ProductItem />

                        <ProductItem />
                    </div>

                    <div className="card-load-more">
                        <button className="btn-load-more">Load more</button>
                    </div>
                </div>
            </div>
        );
    }
}

export default ProductList;