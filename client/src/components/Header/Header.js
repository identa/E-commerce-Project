import React, { Component } from "react";
import SearchBar from "./SearchBar";
import ShoppingCart from "./ShoppingCart";
import LogoBar from "./LogoBar";
import MobileCategoryList from "./MobileCategoryList";
import UserLink from "./UserLink";
import ModalComponent from "./Modal/ModalComponent";

class Header extends Component {

    constructor(props) {
        super(props);
        
        this.state = {
            isAuthenticated : false
        }
    }
    
    changeAuthenticated = () =>{
        this.setState({isAuthenticated : !this.state.isAuthenticated});   
    }
      
    render() {

        const authenticate = localStorage.getItem("name"); 
        return (
            <div>
                <div className="header">                    

                <>
                    {
                        authenticate ? <UserLink changeAuthenticated={this.changeAuthenticated}/> : <ModalComponent changeAuthenticated={this.changeAuthenticated}/>
                    }
                </>
                    
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
