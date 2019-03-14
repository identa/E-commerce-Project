import React, { Component } from 'react';
import { Link } from 'react-router-dom';

const clientId = '78bc0dc37ea9d00';
const urlImgur = 'https://api.imgur.com/3/image';
class CampaignCreate extends Component {
    constructor(props) {
        super(props);
        
        this.state = {
            name :'',
            status: '',
            startDate : '',
            endDate : '',
            budget : '',
            bid :'',
            title : '',
            description : '',
            imageURL : '',
            finalURL : '',
            dropdownArrowStyle : {
                detail : 'fa fa-angle-down',
                schedule : 'fa fa-angle-down',
                budget: 'fa fa-angle-down',
                bidding : 'fa fa-angle-down',
                creative : 'fa fa-angle-down'
            }
        }
    }
    
    onOpenComponent = (event) =>{
        let ariaControls = event.target.getAttribute("aria-controls").replace("collapse","");
        let coppyState = JSON.parse(JSON.stringify(this.state.dropdownArrowStyle));
        switch(ariaControls) {
            case 'Detail' :
                if(coppyState.detail === 'fa fa-angle-down'){
                    coppyState.detail = 'fa fa-angle-up';
                }
                else {
                    coppyState.detail = 'fa fa-angle-down';
                }
                this.setState({dropdownArrowStyle : coppyState});
                break;
            case 'Schedule' :
                if(coppyState.schedule === 'fa fa-angle-down'){
                    coppyState.schedule = 'fa fa-angle-up';
                }
                else {
                    coppyState.schedule = 'fa fa-angle-down';
                }
                this.setState({dropdownArrowStyle : coppyState});
                break;
            case 'Budget' :
                if(coppyState.budget === 'fa fa-angle-down'){
                    coppyState.budget = 'fa fa-angle-up';
                }
                else {
                    coppyState.budget = 'fa fa-angle-down';
                }
                this.setState({dropdownArrowStyle : coppyState});
                break;
            case 'Bidding' :
                if(coppyState.bidding === 'fa fa-angle-down'){
                    coppyState.bidding = 'fa fa-angle-up';
                }
                else {
                    coppyState.bidding = 'fa fa-angle-down';
                }
                this.setState({dropdownArrowStyle : coppyState});
                break;
            case 'Creative' :
                if(coppyState.creative === 'fa fa-angle-down'){
                    coppyState.creative = 'fa fa-angle-up';
                }
                else {
                    coppyState.creative = 'fa fa-angle-down';
                }
                this.setState({dropdownArrowStyle : coppyState});
                break;
        }
    }

    loadFile = (event) =>{
        let reader = new FileReader();  
        let file = event.target.files[0];
        
        reader.onloadend = () =>{
            var image = document.getElementById('preview');
            image.src = reader.result;
        };
        reader.readAsDataURL(file);
    }

    getImageUrl = async () =>{
        let file = document.querySelector("input[type='file']").files[0];
        let link = '';
        if(file !== undefined){
                
            const formData = new FormData();
            formData.append('image', file);

            const respone = await fetch(urlImgur, { 
                                                    method : 'POST',
                                                    headers :{'Authorization' : `Client-ID ${clientId}` },
                                                    body : formData
                                                });
            const json = await respone.json();
            link = json.data.link;
        }
        return link;
    }

    formSubmit = async (event) => {
        event.preventDefault();
    }
    render() {
        return (
            <div className="main">
                <div className="container">
                    <div className="campaign-container">
                        <div className="col-md-12">
                            <h4>Create Campaign</h4>
                            <hr />
                            <div className="campaign-content">
                                <form onSubmit={this.formSubmit}>
                                    <div className="card">
                                        <div className="card-header custom-card-header" onClick={this.onOpenComponent} data-toggle="collapse" data-target="#collapseDetail" aria-expanded="false" aria-controls="collapseDetail">
                                            <h5 aria-controls="collapseDetail">Detail</h5>
                                            <div className="pull-right">
                                                <button type="button" aria-controls="collapseDetail">
                                                    <i className={this.state.dropdownArrowStyle.detail} aria-controls="collapseDetail"/>
                                                </button>
                                            </div>
                                        </div>
                                        <div className="collapse" id="collapseDetail">
                                            <div className="card-body">
                                                <div className="form-group row custom-form-row">
                                                    <label className="col-4 col-form-label">Name :</label>
                                                    <div className="col-8 custom-style">
                                                        <input type="text" name="name" placeholder="Name" className="form-control" required />
                                                    </div>
                                                </div>
                                                <div className="form-group row custom-form-row">
                                                    <label className="col-4 col-form-label">Campaign Status :</label>
                                                    <div className="col-8 custom-style">
                                                        <select name="status" className="form-control">
                                                            <option value="ACTIVE">ACTIVE</option>
                                                            <option value="PAUSE">PAUSE</option>
                                                        </select>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    
                                    <div className="card">
                                        <div className="card-header custom-card-header" onClick={this.onOpenComponent} data-toggle="collapse" data-target="#collapseSchedule" aria-expanded="false" aria-controls="collapseSchedule">
                                            <h5 aria-controls="collapseSchedule">Schedule</h5>
                                            <div className="pull-right">
                                                <button type="button" aria-controls="collapseSchedule">
                                                    <i className={this.state.dropdownArrowStyle.schedule} aria-controls="collapseSchedule"/>
                                                </button>
                                            </div>
                                        </div>
                                        <div className="collapse" id="collapseSchedule">
                                            <div className="card-body">
                                                <div className="form-group row custom-form-row">
                                                    <label className="col-4 col-form-label">Start date :</label>
                                                    <div className="col-8 custom-style">
                                                        <div className="input-left">
                                                            <input type="date" name="startDate" className="form-control" required />
                                                        </div>
                                                    </div>
                                                </div>
                                                <div className="form-group row custom-form-row">
                                                    <label className="col-4 col-form-label">End date :</label>
                                                    <div className="col-8 custom-style">
                                                        <div className="input-left">
                                                            <input type="date" name="endDate" className="form-control" required />
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                   
                                    <div className="card">
                                        <div className="card-header custom-card-header" onClick={this.onOpenComponent} data-toggle="collapse" data-target="#collapseBudget" aria-expanded="false" aria-controls="collapseBudget">
                                            <h5 aria-controls="collapseBudget">Budget</h5>
                                            <div className="pull-right">
                                                <button type="button" aria-controls="collapseBudget">
                                                    <i className={this.state.dropdownArrowStyle.budget} aria-controls="collapseBudget"/>
                                                </button>
                                            </div>
                                        </div>
                                        <div className="collapse" id="collapseBudget">
                                            <div className="card-body">
                                                <div className="form-group row custom-form-row">
                                                    <label className="col-4 col-form-label">Budget :</label>
                                                    <div className="col-8 custom-style">
                                                        <div className="input-group">
                                                            <div className="input-group-prepend">
                                                                <span className="input-group-text">
                                                                    <i className="fa fa-dollar" />
                                                                </span>
                                                            </div>
                                                            <input type="number" name="budget" placeholder="Budget" min="1" className="form-control" required />
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    
                                    <div className="card">
                                        <div className="card-header custom-card-header" onClick={this.onOpenComponent} data-toggle="collapse" data-target="#collapseBidding" aria-expanded="false" aria-controls="collapseBidding">
                                            <h5 aria-controls="collapseBidding">Bidding</h5>
                                            <div className="pull-right">
                                                <button type="button" aria-controls="collapseBidding">
                                                    <i className={this.state.dropdownArrowStyle.bidding} aria-controls="collapseBidding" />
                                                </button>
                                            </div>
                                        </div>
                                        <div className="collapse" id="collapseBidding">
                                            <div className="card-body">
                                                <div className="form-group row custom-form-row">
                                                    <label className="col-4 col-form-label">Bid Amount :</label>
                                                    <div className="col-8 custom-style">
                                                        <div className="input-group">
                                                            <div className="input-group-prepend">
                                                                <span className="input-group-text">
                                                                    <i className="fa fa-dollar" />
                                                                </span>
                                                            </div>
                                                            <input type="number" name="bid" placeholder="Bidding Amount" min="1" className="form-control" required />
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    
                                    <div className="card">
                                        <div className="card-header custom-card-header" onClick={this.onOpenComponent} data-toggle="collapse" data-target="#collapseCreative" aria-expanded="false" aria-controls="collapseCreative">
                                            <h5 aria-controls="collapseCreative">Creative</h5>
                                            <div className="pull-right">
                                                <button type="button" aria-controls="collapseCreative">
                                                    <i className={this.state.dropdownArrowStyle.creative} aria-controls="collapseCreative"/>
                                                </button>
                                            </div>
                                        </div>
                                        <div className="collapse" id="collapseCreative">
                                            <div className="card-body">
                                                <div className="form-group row custom-form-row">
                                                    <label className="col-4 col-form-label">Title :</label>
                                                    <div className="col-8">
                                                        <input type="text" name="title" placeholder="Title" className="form-control" required />
                                                    </div>
                                                </div>
                                                <div className="form-group row custom-form-row">
                                                    <label className="col-4 col-form-label">Description :</label>
                                                    <div className="col-8">
                                                        <textarea name="description" placeholder="Description" className="form-control" rows={4} defaultValue={""} />
                                                    </div>
                                                </div>
                                                <div className="form-group row custom-form-row">
                                                    <label className="col-4 col-form-label">Create preview :</label>
                                                    <div className="col-8 custom-style">
                                                        <input type="file" accept="images/*" name="imgURL" onChange={(e)=>this.loadFile(e)}/>
                                                    </div>
                                                    <div className="offset-4 col-8 img-preview">
                                                        <img src="https://cdn.wallpapersafari.com/53/47/4YNVas.jpg" id="preview" alt="" />
                                                    </div>
                                                </div>
                                                <div className="form-group row custom-form-row">
                                                    <label className="col-4 col-form-label">Final URL :</label>
                                                    <div className="col-8">
                                                        <input type="text" name="finalURL" placeholder="Final URL" className="form-control" required />
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    
                                    <div className="form-group form-contain-button">
                                        <button className="btn"><Link to="/manage/campaign/dashboard">Back to list</Link></button>
                                        <button type="submit" className="btn btn-info">Save</button>
                                    </div>
                                </form>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        );
    }
}

export default CampaignCreate;