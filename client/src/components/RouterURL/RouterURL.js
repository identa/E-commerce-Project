import React, { Component } from 'react';
import {Route,Switch} from 'react-router-dom';
import Home from '../Home/Home';
import Profile from '../User/Profile';
import CustomerDashboard from '../User/CustomerDashboard';
import CustomerCreate from '../User/CustomerCreate';
import CustomerEdit from '../User/CustomerEdit';

class RouteURL extends Component {
    render() {
        return (
                <Switch>
                    <Route exact path="/" component={Home}></Route>
                    <Route path="/customer/profile" component={Profile}></Route>
                    <Route path="/manage/customer/dashboard" component={CustomerDashboard}></Route>
                    <Route path="/manage/customer/create" component={CustomerCreate}></Route>
                    <Route path="/manage/customer/edit" component={CustomerEdit}></Route>
                </Switch>
        );
    }
}

export default RouteURL;