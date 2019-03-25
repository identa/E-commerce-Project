import React, { Component } from 'react';
import {Link} from 'react-router-dom';

class RowItem extends Component {
    constructor(props) {
        super(props);
        
        this.state = {
            isModalShowing : false
        }

        this.handleShowModal = this.handleShowModal.bind(this);
    }

    handleShowModal = () =>{     
        this.setState({isModalShowing : true});
        this.props.handleShowModal(this.state.isModalShowing);
        this.props.getProductId(this.props.pid);
    }
    
    render() {
        const role = localStorage.role;
        const data = {
            pid : this.props.pid,
            name : this.props.name,
            description : this.props.description,
            quantity : this.props.quantity,
            originalPrice : this.props.originalPrice,
            discount : this.props.discount,
            view : this.props.view,
            status : this.props.status,
            productImageURL : this.props.productImageURL,
            categoryID : this.props.categoryID,
            shopID : this.props.shopID
        }

        return (
            <tr>
                <td>{this.props.pid}</td>
                <td>{this.props.name}</td>
                <td>{this.props.quantity}</td>
                <td>{this.props.originalPrice}</td>
                <td>{this.props.discount}</td>
                <td>{this.props.view}</td>
                <td>
                    <img src={this.props.productImageURL} alt=''/>
                </td>
                <td>{this.props.status}</td>
                <td className="td-description">{this.props.description}</td>
                <td>{this.props.categoryName}</td>
                {
                    role !== 'ROLE_SHOP' ? (<td>{this.props.shopName}</td>) : null
                }
                <td>
                    <Link to={{pathname : "/manage/product/edit", state : {data : data} }}>
                        <button className="btn btn-info btn-action">
                            <i className="fa fa-edit" />
                            Edit
                        </button>
                    </Link>
                    {
                        role === 'ROLE_ADMIN' ? (<button className="btn btn-danger btn-action" onClick={this.handleShowModal}>
                                                    <i className="fa fa-remove" />
                                                    Delete
                                                </button>) 
                                               : null
                    }
                </td>
            </tr>
        );
    }
}

export default RowItem;