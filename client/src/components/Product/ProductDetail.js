import React, { Component } from 'react';
import {Link} from 'react-router-dom';

const formatter = new Intl.NumberFormat('en-US', {
    style: 'currency',
    currency: 'USD',
    minimumFractionDigits: 2
});
const urlGetProductById = 'https://dac-project.herokuapp.com/api/public/getProduct';
class ProductDetail extends Component {
    constructor(props) {
        super(props);
        
        this.state={
            style :{
                backgroundImage: `url(${this.props.location.state.data.imageURL})`,
                backgroundPosition : '0% 0%'
            },
            pid : this.props.location.state.data.pid,
            pname : '',
            price : '',
            discount : '',
            imageURL : '',
            limit : this.props.location.state.data.limit,
            description : '',
            categoryId : '',
            categoryName : '',
            quantity : 1
        }
    }
    
    componentDidMount() {
        fetch(urlGetProductById, {
            method : 'POST',
            headers :{
                'Content-Type' : 'application/json'
            }, 
            body :JSON.stringify({
                "id" : this.state.pid
            })
        })
        .then(res=>res.json())
        .then(data =>{
            if(data.status === 'SUCCESS'){
                this.setState({
                    pname : data.data.name,
                    price : data.data.originalPrice,
                    discount : data.data.discount,
                    imageURL : data.data.productImageURL,
                    categoryId : data.data.categoryId,
                    categoryName : data.data.category,
                    description : data.data.description,
                });
            }
        })
    }
    
    handleMouseMove = event => {
        const { left, top, width, height } = event.target.getBoundingClientRect()
        const x = (event.pageX - left) / width * 100
        const y = (event.pageY - top) / height * 100
        this.setState(prevState => ({
            style: {
                ...prevState.style,
                backgroundPosition: `${x}% ${y}%`
            }
        }));
    }

    increaseQuantity = () =>{
        let quantity = this.state.quantity;
        if(quantity < this.state.limit) {
            this.setState({quantity : quantity + 1});
        }
        else {
            this.setState({quantity : this.state.limit});
        }
    }

    decreaseQuantity = () =>{
        let quantity = this.state.quantity;
        if(quantity > 1) {
            this.setState({quantity : quantity - 1});
        }
        else {
            this.setState({quantity : 1});
        }
    }
    
    render() {
        return (
            <div className="main">
                <div className="container">
                    <div className="page-container">
                        <div className="row">
                            <div className="col-12 detail-nav">
                                <Link to="/">Home</Link>
                                <i className="fa fa-angle-double-right" />
                                <a href="#">{this.state.categoryName}</a>
                                <i className="fa fa-angle-double-right" />
                                <a className="last-item" href="javascript:void(0)">{this.state.pname}</a>
                            </div>
                        </div>
                        <div className="row">
                            <div className="product-detail col-12">
                                <div className="row">
                                    <div className="col-md-3 col-sm-3 product-detail-image">
                                        <figure onMouseMove={this.handleMouseMove} style={this.state.style}>
                                            <img src={this.state.imageURL} alt=''/>
                                        </figure>
                                    </div>
                                    <div className="col-md-9 col-sm-9 product-detail-info">
                                        <div className="product-title">
                                            <h1>{this.state.pname}</h1>
                                        </div>
                                        <div className="product-price">
                                            <span>{formatter.format(this.state.price)}</span>
                                        </div>
                                        <div className="product-quantity">
                                            <span>Quantity :</span>
                                            <button className="btn btn-minus" onClick={this.decreaseQuantity}>-</button>
                                            <input type="number" value={this.state.quantity} min={1} max={this.state.limit} readOnly />
                                            <button className="btn btn-plus" onClick={this.increaseQuantity}>+</button>
                                        </div>
                                        <div className="product-btn-add-cart">
                                            <button className="btn btn-dark">
                                                <i className="fa fa-shopping-cart" />Add to cart
                                            </button>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div className="row">
                            <div className="product-description col-12">
                                <ul className="nav nav-tabs">
                                    <li className="active">
                                        <span>Description</span>
                                    </li>
                                </ul>
                                <div className="product-detail-description">
                                    {this.state.description}
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        );
    }
}

export default ProductDetail;