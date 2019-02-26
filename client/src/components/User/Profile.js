import React, { Component } from 'react';

class Profile extends Component {
    render() {
        return (
            <div className="main">
                <div className="container">
                    <div className="profile-content">
                        <div className="col-md-10">
                            <div className="card">
                                <div className="card-body">
                                    <div className="row">
                                <div className="col-md-12">
                                    <h4>User profile</h4>
                                </div>
                                <hr />                                    
                                </div>
                                    <div className="row">
                                        <div className="col-md-12">
                                            <form>
                                                <div className="form-group row">
                                                    <label htmlFor="username" className="col-4 col-form-label">First Name *</label> 
                                                    <div className="col-8">
                                                    <input id="firstname" name="firstname" placeholder="First name" className="form-control here" required="required" type="text" />
                                                    </div>
                                                </div>
                                                <div className="form-group row">
                                                    <label htmlFor="username" className="col-4 col-form-label">Last Name*</label> 
                                                    <div className="col-8">
                                                    <input id="lastname" name="lastname" placeholder="Last name" className="form-control here" required="required" type="text" />
                                                    </div>
                                                </div>
                                                <div className="form-group row">
                                                    <label htmlFor="select" className="col-4 col-form-label">Role</label> 
                                                    <div className="col-8">
                                                    <select name="select" className="custom-select">
                                                        <option value="ROLE_ADMIN">Admin</option>
                                                        <option value="ROLE_SHOP">Shop</option>
                                                        <option value="ROLE_CUSTOMER">Customer</option>
                                                    </select>
                                                    </div>
                                                </div>
                                                <div className="form-group row">
                                                    <label htmlFor="select" className="col-4 col-form-label">Status</label> 
                                                    <div className="col-8">
                                                    <select name="select" className="custom-select">
                                                        <option value="admin">Active</option>
                                                        <option value="shop">Pause</option>
                                                    </select>
                                                    </div>
                                                </div>
                                                <div className="form-group row">
                                                    <label htmlFor="username" className="col-4 col-form-label">Email Name*</label> 
                                                    <div className="col-8">
                                                    <input id="email" name="email" placeholder="Email" className="form-control here" readOnly defaultValue="abc@gmail.com" />
                                                    </div>
                                                </div>
                                                <div className="form-group row">
                                                    <label htmlFor="username" className="col-4 col-form-label">Password*</label> 
                                                    <div className="col-8">
                                                    <input id="password" name="password" placeholder="Password" className="form-control here" required="required" type="password" />
                                                    </div>
                                                </div>
                                                <div className="form-group row">
                                                    <div className="offset-4 col-8">
                                                    <button name="submit" type="submit" className="btn btn-primary">Update My Profile</button>
                                                    </div>
                                                </div>
                                            </form>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        );
    }
}

export default Profile;