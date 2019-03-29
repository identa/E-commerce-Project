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
        this.props.getCampaignId(this.props.campaignId);
    }
    formatDate = (date) =>{
        let dateReturn = date.replace(/\//g,'-');
        return dateReturn;
    }

    render() {
        const data = {
            campaignId : this.props.campaignId,
            campaignName : this.props.campaignName,
            startDate : this.formatDate(this.props.startDate),
            endDate : this.formatDate(this.props.endDate),
            status : this.props.status,
            budget : this.props.budget,
            bidAmount : this.props.bidAmount,
            creativePreview : this.props.creativePreview,
            title : this.props.title,
            description : this.props.description,
            productURL: this.props.productURL,
            shopID : this.props.shopID
        }
        
        return (
            <tr>
                <td>{this.props.campaignId}</td>
                <td>{this.props.campaignName}</td>
                <td>{this.props.status}</td>
                <td>{this.props.startDate}</td>
                <td>{this.props.endDate}</td>
                <td>{this.props.budget}</td>
                <td>{this.props.bidAmount}</td>
                <td>{this.props.spend}</td>
                <td className='td-creative'>
                    <img src={this.props.creativePreview} alt='' className='img-creative'/>
                </td>
                <td>{this.props.title}</td>
                <td className="td-description">{this.props.description}</td>
                <td>
                    <Link to={{pathname : "/manage/campaign/edit", state : {data : data } }}>
                        <button className='btn btn-info btn-action'>
                            <i className='fa fa-edit'/>Edit
                        </button>
                    </Link>       
                    <button className='btn btn-danger btn-action' onClick={this.handleShowModal}>
                        <i className='fa fa-remove'/>Delete
                    </button>
                </td>
            </tr>
        );
    }
}

export default RowItem;