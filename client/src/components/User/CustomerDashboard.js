import React, { Component } from 'react';
import RowItem from './RowItem';
import {Link,Redirect} from 'react-router-dom';
import {Modal,Button} from 'react-bootstrap';

const urlGetListCustomer = 'https://dac-java.herokuapp.com/api/admin/getByPageAndSize';
const urlDeleteCustomer = 'https://dac-java.herokuapp.com/api/admin/delete';
class CustomerDashboard extends Component {
    constructor(props) {
        super(props);
        
        this.state = {
            itemPerPage : 5,
            totalPages : 0,
            userResponseList : [],
            activePage: 1,
            activeClassName : 'page-item',
            isModalShowing : false,
            customerId : 0,
        }

        this.handleShowModal = this.handleShowModal.bind(this);
    }
    
    handleShowModal = () => {
        this.setState({isModalShowing : true});
    }

    handleCloseModal = () => {
        this.setState({isModalShowing: false });
    }

    getCustomerId = (customerId) =>{
        this.setState({customerId : customerId});
    }


    componentDidMount() {
        const dataGet = {
            page : "1",
            size : this.state.itemPerPage
        };
        this.fetchData(dataGet);
    }

    createPageIndex = () =>{
        const totalPages = this.state.totalPages;
        let ul = [];
        for (let i = 0; i < totalPages; i++){
            ul.push(<li className={this.state.activeClassName} key={i}>
                <a className="page-link" id={'page-'+`${i + 1}`} href="javascript:void(0)" onClick={this.handlePaging}>{`${i + 1}`}</a>
            </li>);
        }
        return ul;
    }

    handlePaging = (event) => {
        const pageId = Number(event.target.id.replace('page-',''));
        const dataSend = {
            page : pageId,
            size : this.state.itemPerPage
        };
        this.fetchData(dataSend);
    }

    fetchData = (data) => {
        const token = localStorage.getItem("token");

        fetch(urlGetListCustomer ,{
            method : 'POST',
            body: JSON.stringify(data),
            headers : {
                "Content-Type" : "application/json",
                "Authorization" : token
            }    
        })
        .then(res => res.json())
        .then(data =>{
            if(data.status === 'SUCCESS'){
                this.setState({totalPages : data.data.totalPages});               
                this.setState({userResponseList : data.data.userResponseList}); 
            }
            else if(data.status === 'FAILED'){

            }
        })
        .catch(err => {

        });
    }

    onDeleteCustomer = () =>{
        fetch(urlDeleteCustomer ,{
            method : 'PUT',
            headers : {
                'Content-Type' : 'application/json',
                'Authorization' : localStorage.token
            },
            body : JSON.stringify({
                id : this.state.customerId
            })
        })
        .then(res=>res.json())
        .then(data =>{
            if(data.status === 'SUCCESS'){
                const list = this.state.userResponseList.filter(element => element.id !== this.state.customerId);
                this.setState({
                    isModalShowing : false,
                    userResponseList: list
                });
            }
        })
        .catch(err =>{
            console.log(err);  
        })
    }

    render() {
        const userResponseList= this.state.userResponseList;
        return (
            <div className="main">
                <div className="container">
                    <h2>Dashboard</h2>
                    <div className="row btn-create">
                        <Link to="/manage/customer/create/">
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
                    <div className="row">
                        <table className="table table-bordered">
                            <tbody>
                                <tr>
                                    <th>Id</th>
                                    <th>First Name</th>
                                    <th>Last Name</th>
                                    <th>Email</th>
                                    <th>Role</th>
                                    <th>Status</th>
                                    <th>&nbsp;</th>
                                </tr>
                                {
                                    userResponseList.map((value,key) => {
                                        return <RowItem key={key} 
                                                        uid={value.id} 
                                                        firstName={value.firstName}
                                                        lastName={value.lastName}
                                                        email={value.email}
                                                        status={value.status}
                                                        role={value.role}
                                                        handleShowModal={this.handleShowModal}
                                                        getCustomerId={this.getCustomerId}/>
                                    })
                                }
                            </tbody>
                        </table>
                                                
                        <ul className="pagination">
                            {this.createPageIndex()} 
                        </ul>
                    </div>
                </div>
                <Modal show={this.state.isModalShowing} onHide={this.handleCloseModal}>
                    <Modal.Header closeButton/>

                    <Modal.Body>
                        <p style={{color : 'white'}}>Do you want to delete this id :{this.state.customerId} </p>
                        <Button onClick={this.onDeleteCustomer}>Yes</Button>
                        <Button onClick={this.handleCloseModal}>No</Button>
                    </Modal.Body>
                </Modal>
            </div>
        );
    }
}

export default CustomerDashboard;