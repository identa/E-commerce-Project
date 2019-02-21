import React, { Component } from "react";
import LinkText from "./LinkText";
import UserLink from "./UserLink";

class Header extends Component {
    constructor(props) {
        super(props);
        
        this.state = {
            sessionEmail : sessionStorage.getItem("email")
        }
    }
      
    render() {
        
        return (
            <div>
                <div className="header">                    

                    <LinkText/>
                    
                    <div className="logo-bar">
                    <div className="logo-bar-content">
                    <div className="logo-content">
                        <a href="#">
                        <img src="./assets/images/logo.png" alt="" />
                        </a>
                    </div>
                    <div className="search-box">
                        <form>
                        <div className="form-content">
                            <input type="text" placeholder="Search here...." />
                            <button type="submit">
                            <i className="fa fa-search" />
                            </button>
                        </div>
                        </form>
                    </div>
                    <div className="nav-cart">
                        <div className="nav-cart-content">
                        <a href="#">
                            <i className="fa fa-shopping-cart" />
                            <span className="cart-quantity">(0)</span>
                        </a>
                        </div>
                    </div>
                    </div>
                </div>

                    <div className="mobile-category-list">
                    <div className="mobile-category-text">
                    <span>Category list</span>
                    <span>
                        <i className="fa fa-angle-down" />
                    </span>
                    </div>
                    <div className="mobile-category-content">
                    <ul>
                        <li>
                        <a href="#">Electronic Devices</a>
                        </li>
                        <li>
                        <a href="#">Electronic Accessories</a>
                        </li>
                        <li>
                        <a href="#">TV &amp; Home Appliances</a>
                        </li>
                        <li>
                        <a href="#">Health &amp; Beauty</a>
                        </li>
                        <li>
                        <a href="#">Babies &amp; Toys</a>
                        </li>
                        <li>
                        <a href="#">Groceries &amp; Pets</a>
                        </li>
                        <li>
                        <a href="#">Home &amp; Lifestyle</a>
                        </li>
                        <li>
                        <a href="#">Women's Fashion</a>
                        </li>
                        <li>
                        <a href="#">Men's Fashion</a>
                        </li>
                        <li>
                        <a href="#">Fashion Accessories</a>
                        </li>
                        <li>
                        <a href="#">Sports &amp; Travel</a>
                        </li>
                        <li>
                        <a href="#">Automotive &amp; Motocycle</a>
                        </li>
                    </ul>
                    </div>
                </div>
                </div>
            </div>
        );
    }
}

export default Header;
