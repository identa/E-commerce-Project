import React, { Component } from 'react';
import RowItem from './RowItem';
import {Modal,Button} from 'react-bootstrap';
import {Link,Redirect} from 'react-router-dom';

const urlGetListCampaignByAdmin = "https://dac-project.herokuapp.com/api/admin/paginateCampaign";
const urlGetListCampaignByShop = "https://dac-project.herokuapp.com/api/shop/paginateCampaign";
const urlDeleteCampaignByAdmin= 'https://dac-project.herokuapp.com/api/admin/deleteCampaign';
const urlDeleteCampaignByShop= 'https://dac-project.herokuapp.com/api/shop/deleteCampaign';
class CampaignDashboard extends Component {
    constructor(props) {
        super(props);
        
        this.state = {
            itemPerPage : 5,
            totalPages : 0,
            campaignResponseList : [],
            isModalShowing : false,
            campaignId : 0,
            isRedirect : false
        }
    }

    componentDidMount() {
        const dataGet = {
            id : localStorage.id,
            page : '1',
            size : this.state.itemPerPage
        }
        this.fetchData(dataGet);
    }
    
    fetchData = (data) =>{
        const token = localStorage.token;
        let url = '';
        if(localStorage.role === 'ROLE_ADMIN'){
            url = urlGetListCampaignByAdmin;
        }
        else if (localStorage.role ==='ROLE_SHOP'){
            url = urlGetListCampaignByShop;
        }

        fetch(url, {
            method : 'POST',
            headers : {
                'Content-Type' : 'application/json',
                'Authorization' : token
            }, 
            body : JSON.stringify(data)
        })
        .then(res => res.json())
        .then(result =>{
            if(result.status === 'SUCCESS'){
                this.setState({
                    totalPages : result.data.totalPages,
                    campaignResponseList : result.data.campaignResponseList
                });
            }
        })
    }

    handleShowModal = () => {
        this.setState({isModalShowing : true});
    }

    handleCloseModal = () => {
        this.setState({isModalShowing: false });
    }

    getCampaignId = (campaignId) =>{
        this.setState({campaignId : campaignId});
    }
    
    createPageIndex = () =>{
        const totalPages = this.state.totalPages;
        let ul = [];
        for (let i = 0; i < totalPages; i++){
            ul.push(<li key={i}>
                <a className="page-link" id={'page-'+ (i + 1)} href="javascript:void(0)" onClick={(e) => this.handlePaging(e)}>{(i + 1)}</a>
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

    onDeleteCampaign = () =>{
        let url = '';
        if(localStorage.role === 'ROLE_ADMIN'){
            url = urlDeleteCampaignByAdmin;
        }
        else if(localStorage.role === 'ROLE_SHOP'){
            url = urlDeleteCampaignByShop
        }
        fetch(url, {
            method : 'DELETE',
            headers :{
                'Content-Type' : 'application/json',
                'Authorization' : localStorage.token
            },
            body : JSON.stringify({
                id : this.state.campaignId
            })
        })
        .then(res => res.json())
        .then(data => {
            if(data.status === 'SUCCESS') {
                const list = this.state.campaignResponseList.filter(element => element.id !== this.state.campaignId);
                this.setState({
                    isModalShowing : false,
                    campaignResponseList : list
                });
            }
        })
    }
    render() {
        const campaignResponseList = this.state.campaignResponseList;
        const isRedirect = this.state.isRedirect;
        const role = localStorage.role;

        if(isRedirect) {
            return <Redirect to='/'/>
        }
        return (
            <div className="main">
                <div className="dashboard-container">
                    <h2>Campaign Management Dashboard</h2>

                    <div className="row btn-create">
                    {
                        role === 'ROLE_SHOP' ? (<Link to="/manage/campaign/create">
                                                    <button className="btn btn-success btn-action">
                                                        <i className="fa fa-plus"></i>Create
                                                    </button>
                                                </Link>) : null
                    }
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
                                    <th>Campaign Name</th>
                                    <th>Status</th>
                                    <th>Start date</th>
                                    <th>End date</th>
                                    <th>Budget</th>
                                    <th>Bid Amount</th>
                                    <th>Spend</th>
                                    <th>Creative preview</th>
                                    <th>Title</th>
                                    <th>Description</th>
                                    <th>&nbsp;</th>
                                </tr>
                                {
                                    campaignResponseList.map((value,key) => {
                                        return <RowItem key={key} 
                                                        campaignId={value.id} 
                                                        campaignName={value.name}
                                                        status={value.status}
                                                        startDate={value.startDate}
                                                        endDate={value.endDate}
                                                        budget={value.budget}
                                                        bidAmount={value.bid}
                                                        spend={value.spend}
                                                        creativePreview={value.imageURL}
                                                        title={value.title}
                                                        description={value.description}
                                                        shopID={value.shopID}
                                                        productURL={value.productURL}
                                                        handleShowModal={this.handleShowModal}
                                                        getCampaignId={this.getCampaignId}/>
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
                            <p>Do you want to delete this campaign ?</p>
                        </Modal.Body>
                        <Modal.Footer>
                            <Button variant='primary' onClick={this.onDeleteCampaign}>Yes</Button> 
                            <Button variant='secondary' onClick={this.handleCloseModal}>No</Button>
                        </Modal.Footer>
                    </div>
                </Modal>
            </div>
        );
    }
}

export default CampaignDashboard;