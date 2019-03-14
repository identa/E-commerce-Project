import React, { Component } from 'react';

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
    render() {
        return (
            <tr>
                <td>{this.props.campaignId}</td>
                <td>{this.props.campaignName}</td>
                <td>{this.props.status}</td>
                <td>{this.props.startDate}</td>
                <td>{this.props.endDate}</td>
                <td>{this.props.budget}</td>
                <td>{this.props.bidAmount}</td>
                <td className='td-creative'>
                    <img src={this.props.creativePreview} alt='' className='img-creative'/>
                </td>
                <td>{this.props.title}</td>
                <td>{this.props.description}</td>
                <td>
                    <button className='btn btn-info btn-action'>
                        <i className='fa fa-edit'/>Edit
                        </button>
                    <button className='btn btn-danger btn-action' onClick={this.handleShowModal}>
                        <i className='fa fa-remove'/>Delete
                        </button>
                </td>
            </tr>
        );
    }
}

export default RowItem;