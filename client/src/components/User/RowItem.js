import React, { Component } from "react";
import {Link} from 'react-router-dom'
class RowItem extends Component {

    constructor(props) {
        super(props);
        
        this.state = {
            isModalShowing :  false
        }

        this.handleShowModal = this.handleShowModal.bind(this);
    }

    handleShowModal = () =>{     
        this.setState({isModalShowing : true});
        this.props.handleShowModal(this.state.isModalShowing);
        this.props.getCustomerId(this.props.uid);
    }


    render() {
        const data = {
            uid : this.props.uid,
            firstName : this.props.firstName,
            lastName : this.props.lastName,
            email : this.props.email,
            role : this.props.role,
            status : this.props.status,
            imageURL : this.props.imageURL
        }
    
        return (
            <tr>
                <td>{this.props.uid}</td>
                <td>{this.props.firstName}</td>
                <td>{this.props.lastName}</td>
                <td>{this.props.email}</td>
                <td>
                    <img src={this.props.imageURL} alt=''/>
                </td>
                <td>{this.props.role}</td>
                <td>{this.props.status}</td>
                <td>
                    <Link to={{pathname : "/manage/customer/edit", state : {data : data} }}>
                        <button className="btn btn-info btn-action">
                            <i className="fa fa-edit" />
                            Edit
                        </button>
                    </Link>
                    <button className="btn btn-danger btn-action" onClick={this.handleShowModal}>
                        <i className="fa fa-remove" />
                        Delete
                    </button>
                </td>
            </tr>
        );
    }
}

export default RowItem;
