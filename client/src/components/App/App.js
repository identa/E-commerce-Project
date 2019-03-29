import React, { Component } from 'react';
import './App.css';
import Header from '../Header/Header';
import Footer from '../Footer/Footer';
import BtnMessage from '../Utils/BtnMessage';
import RouterURL from '../RouterURL/RouterURL';
import { BrowserRouter as Router } from 'react-router-dom';

class App extends Component {
    constructor(props) {
        super(props);

        this.state = {
            imageURL: localStorage.imageURL,
            shoppingCart: []
        }
    }
    componentDidMount() {
        this.getShoppingCart();
    }

    changeImageAvatar = (imageURL) => {
        this.setState({ imageURL: imageURL });
    }

    getImageAvatar = () => {
        return localStorage.imageURL;
    }

    addItemToCart = (pid, pname, quantity, price, imageURL) => {
        let shoppingCart = this.state.shoppingCart;
        for (let i in shoppingCart) {
            if (shoppingCart[i].pid === Number(pid)) {
                shoppingCart[i].quantity += Number(quantity);
                this.setState({shoppingCart : shoppingCart});
                this.saveCart();
                return;
            }
        }
        let item = {
            pid : Number(pid),
            pname : pname,
            quantity : Number(quantity),
            price : Number(price),
            imageURL : imageURL
        }
        shoppingCart.push(item);
        this.setState({shoppingCart : shoppingCart});
        this.saveCart();
    }

    removeCartItem = (pid) =>{
        let shoppingCart = this.state.shoppingCart;
        for(let i in shoppingCart) {
            if(shoppingCart[i].pid === Number(pid)){
                shoppingCart.splice(shoppingCart[i], 1);
            }
        }
        this.setState({shoppingCart : shoppingCart});
        this.saveCart();
    }

    saveCart = () => {
        localStorage.setItem("shoppingCart", JSON.stringify(this.state.shoppingCart)); 
    }

    getShoppingCart = () => {
        if(!localStorage.shoppingCart) {
            localStorage["shoppingCart"] = JSON.stringify([]);
        }
        else{
            this.setState({shoppingCart : JSON.parse(localStorage.shoppingCart) });
        }  
    }

    render() {        
        return (
            <Router>
                <>
                    <Header changeImageAvatar={this.changeImageAvatar}
                            getImageAvatar={this.getImageAvatar}
                            getShoppingCart={this.state.shoppingCart} 
                            removeCartItem={this.removeCartItem}/>
                    <RouterURL changeImageAvatar={this.changeImageAvatar}
                               addItemToCart={this.addItemToCart} />
                    <Footer />
                    <BtnMessage />
                </>
            </Router>
        );
    }
}

export default App;
