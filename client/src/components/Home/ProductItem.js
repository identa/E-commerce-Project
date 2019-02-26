import React, { Component } from 'react';

class ProductItem extends Component {
    render() {
        return (
            <div className="col-md-3 col-sm-3 col-xs-12 product-item-box">
                <div className="product-item">
                    <div className="product-image">
                        <a href="javascript:void(0)">
                        <img
                            src="./assets/images/products/product.jpg"
                            className="img-responsive"
                            alt=""
                        />
                        </a>
                    </div>
                    <div className="product-info">
                        <h4 className="product-name">Product</h4>
                        <div className="product-price">99999$</div>
                        <div className="action">
                        <a href="javascript:void(0)" className="add-to-cart btn">
                            <i className="fa fa-shopping-cart" /> Add to cart
                        </a>
                        </div>
                    </div>
                </div>
          </div>
        );
    }
}

export default ProductItem;