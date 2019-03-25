import React, { Component } from 'react';
import {Link} from 'react-router-dom';
class ProductItem extends Component {
    render() {
        return (
            <div className="col-md-2 col-sm-3 col-xs-12 product-item-box">
                <div className="product-item">
                    <div className="product-image">
                        <a href="javascript:void(0)">
                        <img
                            src="./assets/images/products/product.jpg"
                            className="img-product"
                            alt=""
                        />
                        </a>
                    </div>
                    <div className="product-info">
                        <h4 className="product-name">Product</h4>
                        <span className="product-price">99999$</span>
                        <div className="action">
                            <Link to="/product/detail" className="btn btn-detail">
                                <i className="fa fa-eye"/>
                            </Link>
                            <a href="javascript:void(0)" className="btn btn-add-to-cart add-to-cart">
                                <i className="fa fa-shopping-cart" />
                            </a>
                        </div>
                    </div>
                </div>
          </div>
        );
    }
}

export default ProductItem;