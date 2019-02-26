import React, { Component } from 'react';
import {BrowserRouter as Router,Route} from 'react-router-dom';
import Home from '../Home/Home';
import User from '../Contact/Contact';

class RouteURL extends Component {
    render() {
        return (
            <Router>
                <>
                    <Route path="/" component={Home}></Route>
                    <Route path="/user" component={User}></Route>
                </>
            </Router>
        );
    }
}

export default RouteURL;