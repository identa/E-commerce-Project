import React, { Component } from 'react';
import {Route,Switch} from 'react-router-dom';
import Home from '../Home/Home';
import Profile from '../User/Profile';
import CustomerDashboard from '../User/CustomerDashboard';
import CustomerCreate from '../User/CustomerCreate';
import CustomerEdit from '../User/CustomerEdit';
import ProductDashboard from '../Product/ProductDashboard';
import ProductCreate from '../Product/ProductCreate';
import ProductEdit from '../Product/ProductEdit';
import CampaignDashboard from '../Campaign/CampaignDashboard';
import CampaignCreate from '../Campaign/CampaignCreate';
import CampaignEdit from '../Campaign/CampaignEdit';
import ProductDetail from '../Product/ProductDetail';
class RouteURL extends Component {
    render() {
        return (
                <Switch>
                    <Route exact path="/" {...this.props} render={(props) => <Home {...props} addItemToCart={this.props.addItemToCart}/>}/>
                    <Route path="/customer/profile" {...this.props} render={(props) => <Profile {...props} changeImageAvatar={this.props.changeImageAvatar}/>} />
                    <Route path="/manage/customer/dashboard" component={CustomerDashboard}/>
                    <Route path="/manage/customer/create" component={CustomerCreate}/>
                    <Route path="/manage/customer/edit" component={CustomerEdit}/>
                    <Route path="/manage/product/dashboard" component={ProductDashboard}/>
                    <Route path="/manage/product/create" component={ProductCreate}/>
                    <Route path="/manage/product/edit" component={ProductEdit}/>
                    <Route path="/manage/campaign/dashboard" component={CampaignDashboard}/>
                    <Route path="/manage/campaign/create" component={CampaignCreate}/>
                    <Route path="/manage/campaign/edit" component={CampaignEdit}/>
                    <Route path={"/product/detail/:id/:slug"} component={ProductDetail}/>
                </Switch>  
        );
    }
}

export default RouteURL;