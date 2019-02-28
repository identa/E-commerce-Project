import React, { Component } from 'react';
import {Route} from 'react-router-dom';
import Home from '../Home/Home';
import Profile from '../User/Profile';
import CustomerDashboard from '../User/CustomerDashboard';
import CustomerCreate from '../User/CustomerCreate';

class RouteURL extends Component {
    render() {
        return (
                <>
                    <Route exact path="/" component={Home}></Route>
                    <Route path="/customer/profile" component={Profile}></Route>
                    <Route path="/manage/customer/dashboard" component={CustomerDashboard}></Route>
                    <Route path="/manage/customer/create" component={CustomerCreate}></Route>
                </>
        );
    }
}

export default RouteURL;