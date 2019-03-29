import React, { Component } from 'react';

const formatter = new Intl.NumberFormat('en-US', {
    style: 'currency',
    currency: 'USD',
    minimumFractionDigits: 2
});
class ShoppingCart extends Component {
    constructor(props) {
        super(props);
        this.state = {
            style :{ 
                display : 'none'
            },
            shoppingCart : this.props.getShoppingCart 
        }
    }
    
    onHoverCart = () =>{
        this.setState(prevState => ({
            style: {
                ...prevState.style,
                display : 'block'
            }
        }));
    }

    onLeaveCart = () =>{
        this.setState(prevState => ({
            style: {
                ...prevState.style,
                display : 'none'
            }
        }));
    }
    
    removeCartItem = (event) =>{
        let pid = event.target.getAttribute('pid');
        this.props.removeCartItem(pid);      
    }
    showCart = () => {
        let shoppingCart = this.props.getShoppingCart;
        if(shoppingCart.length === 0) {
            return (<span>Your cart is empty!</span>)
        }
        else {  
            let total = 0;        
            return (<div className="cart-list">
                        {
                            shoppingCart.map((value, key) =>{
                                total += value.price * value.quantity;
                                return (<div className="cart-item clearfix" key={key}>
                                            <figure className="image-cart">
                                                <img src={value.imageURL} alt=''/>
                                            </figure>
                                            <div className="text-cart">
                                                <h4>{value.pname}</h4>
                                                <div className="price-line">
                                                    <span className="new-price">{formatter.format(value.price) + ' x ' + value.quantity}</span>
                                                </div>
                                            </div>
                                            <span className="remove-link">
                                                <a href="javascript:void(0);" >
                                                    <i className="fa fa-trash" pid={value.pid} onClick={(e) => this.removeCartItem(e)}/>
                                                </a>
                                            </span>
                                        </div>)
                            })
                        }
                        <div className="text-mini-cart">
                            <span className="cart-block-text">Total : </span>
                            <span className="cart-block-total">{formatter.format(total)}</span>
                        </div>
                        <div className="check-cart-mini">
                            <a className="btn btn-view-cart">View cart</a>
                            <a className="btn btn-checkout">Checkout</a>
                        </div>
                    </div>)
        }
    }
    render() {
        return (
            <div className="nav-cart" onMouseEnter={this.onHoverCart} onMouseLeave={this.onLeaveCart}>
                <div className="nav-cart-content">
                    <a href="#">
                        <i className="fa fa-shopping-cart" />
                        <span className="cart-quantity">(0)</span>
                    </a>
                </div>
                <div className="cart-detail" style={this.state.style}>
                {this.showCart()}
                </div>
            </div>
        );
    }
}

export default ShoppingCart;