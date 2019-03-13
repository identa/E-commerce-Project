import React, { Component } from 'react';
import RowItem from './RowItem';
import {Modal,Button} from 'react-bootstrap';
import {Link} from 'react-router-dom';
class CampaignDashboard extends Component {
    render() {
        return (
            <div className="main">
                <div className="dashboard-container">
                    <h2>Campaign Management Dashboard</h2>
                    <div className="row btn-create">
                        <Link to="/manage/campaign/create">
                            <button className="btn btn-success btn-action">
                                <i className="fa fa-plus"></i>Create
                            </button>
                        </Link>
                        <div className="form-search">
                            <form>
                                <label>Search : </label>
                                <input type="text" name="keyword"/>
                            </form>
                        </div>
                    </div>
                    <div className="row table-responsive">
                        <table className="table table-bordered table-hover">
                            <tbody>
                                <tr>
                                    <th>Id</th>
                                    <th>First Name</th>
                                    <th>Last Name</th>
                                    <th>Email</th>
                                    <th>Avatar</th>
                                    <th>Role</th>
                                    <th>Status</th>
                                    <th>&nbsp;</th>
                                </tr>
                                {/* {
                                    userResponseList.map((value,key) => {
                                        return <RowItem key={key} 
                                                        uid={value.id} 
                                                        firstName={value.firstName}
                                                        lastName={value.lastName}
                                                        email={value.email}
                                                        status={value.status}
                                                        role={value.role}
                                                        imageURL={value.imageURL}
                                                        handleShowModal={this.handleShowModal}
                                                        getCustomerId={this.getCustomerId}/>
                                    })
                                } */}
                            </tbody>
                        </table>
                                                
                        <ul className="pagination">
                            {/* {this.createPageIndex()}  */}
                        </ul>
                    </div>
                </div>
                {/* <Modal show={this.state.isModalShowing} onHide={this.handleCloseModal}>
                    <div className='modal-confirm'>
                        <Modal.Header closeButton />
                        <Modal.Body>
                            <p>Do you want to delete this campaign ?</p>
                        </Modal.Body>
                        <Modal.Footer>
                            <Button variant='primary' onClick={this.onDeleteCustomer}>Yes</Button> 
                            <Button variant='secondary' onClick={this.handleCloseModal}>No</Button>
                        </Modal.Footer>
                    </div>
                </Modal> */}
            </div>
        );
    }
}

export default CampaignDashboard;