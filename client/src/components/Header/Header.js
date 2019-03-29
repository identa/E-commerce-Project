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
            isAuthenticated : false,
            roleName : ''
        }
    }
    
    changeAuthenticated = () =>{
        this.setState({isAuthenticated : !this.state.isAuthenticated});   
    }
      
    setRole = (roleName) =>{
        this.setState({roleName : roleName});
    }

    getRole = () =>{
        return this.state.roleName;
    }
    
    render() {

        const authenticate = localStorage.getItem("token"); 
        return (
            <>
                <div className="header">                    

                <>
                    {
                        authenticate ? <UserLink        changeAuthenticated={this.changeAuthenticated}
                                                        getRole={this.getRole}
                                                        {...this.props}/> 
                                     : <ModalComponent 
                                                        changeAuthenticated={this.changeAuthenticated}
                                                        setRole={this.setRole}/>
                    }
                </>
                    
                <div className="logo-bar">
                    <div className="logo-bar-content">
                        <LogoBar/>

                        <SearchBar/>

                        <ShoppingCart {...this.props}/>
                    </div>
                </div>

                  <MobileCategoryList/>
                </div>
            </>
        );
    }
}

export default Header;
