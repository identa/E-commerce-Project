import React, { Component } from 'react';

class ShoppingCart extends Component {
    render() {
        return (
            <div className="nav-cart">
                <div className="nav-cart-content">
                    <a href="#">
                        <i className="fa fa-shopping-cart" />
                        <span className="cart-quantity">(0)</span>
                    </a>
                </div>
            </div>
        );
    }
}

export default ShoppingCart;