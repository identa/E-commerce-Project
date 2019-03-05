import React, { Component } from 'react';
import {Link} from 'react-router-dom';
class CustomerEdit extends Component {
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
                                    <h4>Create new user</h4>
                                </div>
                                <hr />                                    
                                </div>
                                    <div className="row">
                                        <div className="col-md-12">
                                            <form>
                                                <div className="form-group row">
                                                    <label className="col-4 col-form-label">First Name *</label> 
                                                    <div className="col-8">
                                                        <input type="text" name="firstName" placeholder="First name" className="form-control" required="required" onChange={this.onChange} onFocus={this.onFocus} onBlur={this.validateFirstName}/>
                                                        <div className="message">
                                                            {this.state.error.firstName}
                                                        </div>
                                                    </div>
                                                    
                                                </div>
                                                
                                                <div className="form-group row">
                                                    <label className="col-4 col-form-label">Last Name*</label> 
                                                    <div className="col-8">
                                                        <input type="text" name="lastName" placeholder="Last name" className="form-control" required="required" onChange={this.onChange} onFocus={this.onFocus} onBlur={this.validateLastName}/>
                                                        <div className="message">
                                                            {this.state.error.lastName}
                                                        </div>
                                                    </div>
                                                    
                                                </div>                                           
                                                
                                                <div className="form-group row">
                                                    <label className="col-4 col-form-label">Email *</label> 
                                                    <div className="col-8">
                                                        <input type="email" name="email" placeholder="Email" className="form-control" required="required" onChange={this.onChange} onFocus={this.onFocus} onBlur={this.validateEmail}/>
                                                        <div className="message">
                                                            {this.state.error.email}
                                                        </div>
                                                    </div>
                                                </div>
                                                
                                                <div className="form-group row">
                                                    <label className="col-4 col-form-label">Password*</label> 
                                                    <div className="col-8">
                                                        <input type="password" name="password" placeholder="Password" className="form-control" required="required" onChange={this.onChange} onFocus={this.onFocus} onBlur={this.validatePassword}/>
                                                        <div className="message">
                                                            {this.state.error.password}
                                                        </div>
                                                    </div>
                                                </div>

                                                <div className="form-group row">
                                                    <label htmlFor="select" className="col-4 col-form-label">Role*</label> 
                                                    <div className="col-8">
                                                        <select name="role" className="custom-select" value={this.state.role} onChange={this.onChange}>
                                                            <option value="ROLE_CUSTOMER">Customer</option>
                                                            <option value="ROLE_ADMIN">Admin</option>
                                                            <option value="ROLE_SHOP">Shop</option>
                                                        </select>
                                                    </div>
                                                </div>

                                                <div className="form-group row">
                                                    <label className="col-4 col-form-label">Avatar</label> 
                                                    <div className="col-4">
                                                        <input type="file" accept="image/*" name="imgURL" onChange={(e)=>this.loadFile(e)}/>
                                                    </div>
                                                    
                                                </div>
                                                <div className="form-group row">
                                                    <div className="offset-4 col-4 img-preview">
                                                        <img src='' id='preview' alt=''/>
                                                        {this.state.imageLink}
                                                    </div>
                                                </div>
                                                <div className="form-group row">
                                                    <div className="offset-4 col-8">
                                                        <button className="btn btn-link">
                                                            <i className="fa fa-angle-double-left"></i>
                                                            <Link to="/manage/customer/dashboard">
                                                                Back to list
                                                            </Link>
                                                        </button>

                                                        <button name="submit" type="submit" className="btn btn-info">Edit</button>
                                                    </div>                                               
                                                </div>

                                                <div className="form-group row">
                                                    <div className="offset-4 col-8">
                                                        <div className="message">
                                                            {this.state.error.message}
                                                        </div>
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

export default CustomerEdit;