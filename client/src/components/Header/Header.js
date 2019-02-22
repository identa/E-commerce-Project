import React, { Component } from "react";
import AuthenticatedBar from "./AuthenticatedBar";
import SearchBar from "./SearchBar";
import ShoppingCart from "./ShoppingCart";
import LogoBar from "./LogoBar";
import MobileCategoryList from "./MobileCategoryList";

class Header extends Component {
      
    render() {
        return (
            <div>
                <div className="header">                    

                    <AuthenticatedBar/>
                    
                    <div className="logo-bar">
                        <div className="logo-bar-content">
                            <LogoBar/>

                            <SearchBar/>

                            <ShoppingCart/>
                        </div>
                    </div>

                  <MobileCategoryList/>
                </div>
            </div>
        );
    }
}

export default Header;
