import React, { Component } from 'react';
import {Route} from 'react-router-dom';
import Home from '../Home/Home';
import Profile from '../User/Profile';
import CustomerDashboard from '../User/CustomerDashboard';

class RouteURL extends Component {
    render() {
        return (
                <>
                    <Route exact path="/" component={Home}></Route>
                    <Route exact path="/customer/profile" component={Profile}></Route>
                    <Route exact path="/customer/dashboard" component={CustomerDashboard}></Route>
                </>
        );
    }
}

export default RouteURL;