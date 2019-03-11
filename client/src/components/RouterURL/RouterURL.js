import React, { Component } from 'react';
import {Route,Switch} from 'react-router-dom';
import Home from '../Home/Home';
import Profile from '../User/Profile';
import CustomerDashboard from '../User/CustomerDashboard';
import CustomerCreate from '../User/CustomerCreate';
import CustomerEdit from '../User/CustomerEdit';
import ProductDashboard from '../Product/ProductDashboard';
import ProductCreate from '../Product/ProductCreate';
class RouteURL extends Component {
    render() {
        return (
                <Switch>
                    <Route exact path="/" component={Home}></Route>
                    <Route path="/customer/profile" component={Profile}></Route>
                    <Route path="/manage/customer/dashboard" component={CustomerDashboard}></Route>
                    <Route path="/manage/customer/create" component={CustomerCreate}></Route>
                    <Route path="/manage/customer/edit" component={CustomerEdit}></Route>
                    <Route path="/manage/product/dashboard" component={ProductDashboard}></Route>
                    <Route path="/manage/product/create" component={ProductCreate}></Route>
                </Switch>
        );
    }
}

export default RouteURL;