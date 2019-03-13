import React, { Component } from 'react';
import {Link} from 'react-router-dom';
import {Modal,Button} from 'react-bootstrap';
import RowItem from './RowItem';

const urlGetListProductByRoleAdmin = 'https://dac-project.herokuapp.com/api/admin/paginateProduct';
const urlDeleteProduct = 'https://dac-project.herokuapp.com/api/admin/deleteProduct';
const urlGetListProductByRoleShop = 'https://dac-project.herokuapp.com/api/shop/paginateProduct';
class ProductDashboard extends Component {
    constructor(props) {
        super(props);
        
        this.state = {
            itemPerPage : 5,
            totalPages : 0,
            productList : [],
            isModalShowing : false,
            productId : 0
        }
    }

    handleShowModal = () => {
        this.setState({isModalShowing : true});
    }

    handleCloseModal = () => {
        this.setState({isModalShowing: false });
    }

    getProductId = (productId) =>{
        this.setState({productId : productId});
    }

    componentDidMount() {
        const dataGet = {
            id : localStorage.id,
            page : '1',
            size : this.state.itemPerPage,
        }

        this.fetchData(dataGet);
    }
    
    fetchData = (data) =>{
        const token = localStorage.token;
        let url = '';
        if(localStorage.role === 'ROLE_ADMIN'){
            url = urlGetListProductByRoleAdmin;
        }
        else if (localStorage.role === 'ROLE_SHOP'){
            url = urlGetListProductByRoleShop;
        }
        fetch(url, {
            method : 'POST',
            body : JSON.stringify(data),
            headers : {
                "Content-Type" : "application/json",
                "Authorization" : token
            }
        })
        .then(res=> res.json())
        .then(data =>{
            if(data.status === 'SUCCESS'){
                this.setState({
                    totalPages : data.data.totalPages,
                    productList : data.data.productList
                });
            }
            else if (data.status === 403){
            }
        })
    }

    createPageIndex = () =>{
        const totalPages = this.state.totalPages;
        let ul = [];
        for (let i = 0; i < totalPages; i++){
            ul.push(<li className={this.state.activeClassName} key={i}>
                <a className="page-link" id={'page-'+`${i + 1}`} href="javascript:void(0)" onClick={(e) => this.handlePaging(e)}>{`${i + 1}`}</a>
            </li>);
        }
        return ul;
    }

    handlePaging = (event) => {
        const pageId = Number(event.target.id.replace('page-',''));
        const dataSend = {
            id: localStorage.id,
            page : pageId,
            size : this.state.itemPerPage
        };
        this.fetchData(dataSend);
    }
    
    onDeleteProduct = () =>{
        fetch(urlDeleteProduct, {
            method : 'DELETE',
            headers : {
                'Content-Type' : 'application/json',
                'Authorization' : localStorage.token
            },
            body : JSON.stringify({
                id : this.state.productId
            })
        })
        .then(res=>res.json())
        .then(data =>{
            if(data.status === 'SUCCESS'){
                const list = this.state.productList.filter(element => element.id !== this.state.productId);
                this.setState({
                    isModalShowing : false,
                    productList: list
                });
            }
        })
        .catch(err =>{
            console.log(err);  
        })
    }

    render() {
        const productList= this.state.productList;
        const role = localStorage.role;
        return (
            <div className="main">
                <div className="dashboard-container">
                    <h2>Product Management Dashboard</h2>
                    <div className="row btn-create">
                        <Link to="/manage/product/create">
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
                                    <th>Name</th>
                                    <th>Quantity</th>
                                    <th>Price</th>
                                    <th>Discount</th>
                                    <th>View</th>
                                    <th>Image</th>
                                    <th>Status</th>
                                    <th>Description</th>
                                    <th>Category</th>
                                    {
                                        role !== 'ROLE_SHOP' ? (<th>Shop</th>) : null
                                    }
                                    <th>&nbsp;</th>
                                </tr>
                                {
                                    productList.map((value,key) => {
                                        return <RowItem key={key} 
                                                        pid={value.id} 
                                                        name={value.name}
                                                        description={value.description}
                                                        quantity={value.quantity}
                                                        originalPrice={value.originalPrice}
                                                        discount={value.discount}
                                                        view={value.view}
                                                        productImageURL={value.productImageURL}
                                                        status={value.status}
                                                        categoryID={value.categoryID}
                                                        categoryName={value.categoryName}
                                                        shopID={value.shopID}
                                                        shopName={value.shopName}
                                                        handleShowModal={this.handleShowModal}
                                                        getProductId={this.getProductId}/>
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
                    <div className='modal-confirm'>
                        <Modal.Header closeButton />
                        <Modal.Body>
                            <p>Do you want to delete this product ?</p>
                        </Modal.Body>
                        <Modal.Footer>
                            <Button variant='primary' onClick={this.onDeleteProduct}>Yes</Button>
                            <Button variant='secondary' onClick={this.handleCloseModal}>No</Button>
                        </Modal.Footer>
                    </div>
                </Modal>
            </div>
        );
    }
}

export default ProductDashboard;