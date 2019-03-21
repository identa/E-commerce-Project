import React, { Component } from 'react';
import {Link,Redirect} from 'react-router-dom';

const clientId = '78bc0dc37ea9d00';
const urlImgur = 'https://api.imgur.com/3/image';
const urlEditCampaignByAdmin = 'https://dac-project.herokuapp.com/api/admin/updateCampaign';
const urlEditCampaignByShop = 'https://dac-project.herokuapp.com/api/shop/updateCampaign';
class CampaignEdit extends Component {

    constructor(props) {
        super(props);
        
        this.state = {
            campaignId : this.props.location.state.data.campaignId,
            name: this.props.location.state.data.campaignName,
            status: this.props.location.state.data.status,
            startDate: this.props.location.state.data.startDate,
            endDate: this.props.location.state.data.endDate,
            budget: this.props.location.state.data.budget,
            bid: this.props.location.state.data.bidAmount,
            title: this.props.location.state.data.title,
            description: this.props.location.state.data.description,
            imageURL: this.props.location.state.data.creativePreview,
            finalURL: this.props.location.state.data.productURL,
            shopID : this.props.location.state.data.shopID,
            messageShowingStyle: 'message',
            showLoading : 'collapse',
            isRedirect : false,
            isReadOnlyStartDate : false,
            isButtonEnable : true,
            dropdownArrowStyle: {
                detail: 'fa fa-angle-up',
                schedule: 'fa fa-angle-up',
                budget: 'fa fa-angle-up',
                bidding: 'fa fa-angle-up',
                creative: 'fa fa-angle-up'
            },
            error : {
                name: '',
                title: '',
                startDate: '',
                endDate: '',
                budget : '',
                finalURL: '',
                message: '',
            }
        }
    }
    
    componentDidMount() {
        const startDate = this.formatDate(new Date(this.state.startDate));
        const currentDate = this.formatDate(new Date());

        if(startDate < currentDate){
            this.setState({isReadOnlyStartDate : true});
        }
    }
    
    formatDate = (date) =>{
        let dateReturn = new Date(date);
        let month = '' + (dateReturn.getMonth() + 1);
        let day = '' + dateReturn.getDate();
        let year = '' + dateReturn.getFullYear();
        if(month.length < 2) 
            month = '0' + month;
        if (day.length < 2) 
            day = '0' + day;
        return [year,month,day].join('-');
    }

    onChange = (event) => {
        this.setState({ [event.target.name]: event.target.value });
        this.setState({isButtonEnable : false});
        this.setState(prevState => ({
            error :{
                ...prevState.error,
                message : ''
            }
        }));
    }

    onFocus = (event) => {
        let name = event.target.name;
        this.setState({ messageShowingStyle: 'message-hidden' });
        switch (name) {
            case 'name':
                this.setState(prevState => ({
                    error: {
                        ...prevState.error,
                        name: ''
                    }
                }));
                break;
            case 'title':
                this.setState(prevState => ({
                    error: {
                        ...prevState.error,
                        title: ''
                    }
                }));
                break;
            case 'imageFile':
                this.setState(prevState => ({
                    error: {
                        ...prevState.error,
                        imageFile: ''
                    }
                }));
                break;
            case 'finalURL':
                this.setState(prevState => ({
                    error: {
                        ...prevState.error,
                        finalURL: ''
                    }
                }));
                break;
            case 'startDate':
                this.setState(prevState => ({
                    error: {
                        ...prevState.error,
                        startDate: ''
                    }
                }));
                break;
            case 'endDate':
                this.setState(prevState => ({
                    error: {
                        ...prevState.error,
                        endDate: ''
                    }
                }));
                break;
            case 'budget':
                this.setState(prevState => ({
                    error: {
                        ...prevState.error,
                        budget: ''
                    }
                }));
                break;
            default : 
                break;
        }
    }

    onOpenComponent = (event) => {
        let ariaControls = event.target.getAttribute("aria-controls").replace("collapse", "");
        let coppyState = JSON.parse(JSON.stringify(this.state.dropdownArrowStyle));
        switch (ariaControls) {
            case 'Detail':
                if (coppyState.detail === 'fa fa-angle-up') {
                    coppyState.detail = 'fa fa-angle-down';
                }
                else {
                    coppyState.detail = 'fa fa-angle-up';
                }
                this.setState({ dropdownArrowStyle: coppyState });
                break;
            case 'Schedule':
                if (coppyState.schedule === 'fa fa-angle-up') {
                    coppyState.schedule = 'fa fa-angle-down';
                }
                else {
                    coppyState.schedule = 'fa fa-angle-up';
                }
                this.setState({ dropdownArrowStyle: coppyState });
                break;
            case 'Budget':
                if (coppyState.budget === 'fa fa-angle-up') {
                    coppyState.budget = 'fa fa-angle-down';
                }
                else {
                    coppyState.budget = 'fa fa-angle-up';
                }
                this.setState({ dropdownArrowStyle: coppyState });
                break;
            case 'Bidding':
                if (coppyState.bidding === 'fa fa-angle-up') {
                    coppyState.bidding = 'fa fa-angle-down';
                }
                else {
                    coppyState.bidding = 'fa fa-angle-up';
                }
                this.setState({ dropdownArrowStyle: coppyState });
                break;
            case 'Creative':
                if (coppyState.creative === 'fa fa-angle-up') {
                    coppyState.creative = 'fa fa-angle-down';
                }
                else {
                    coppyState.creative = 'fa fa-angle-up';
                }
                this.setState({ dropdownArrowStyle: coppyState });
                break;
            default : 
                break;
        }
    }

    loadFile = (event) => {
        this.setState({isButtonEnable : false});
        let reader = new FileReader();
        let file = event.target.files[0];

        reader.onloadend = () => {
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

    validateName = () => {
        const name = this.state.name;
        this.setState({ messageShowingStyle: 'message' });
        if (name.length === 0) {
            this.setState(prevState => ({
                error: {
                    ...prevState.error,
                    name: 'Campaign name can not be empty!'
                }
            }));
            return false;
        }
        else {
            this.setState(prevState => ({
                error: {
                    ...prevState.error,
                    name: ''
                }
            }));
            return true;
        }
    }

    validateTitle = () => {
        const title = this.state.title;
        this.setState({ messageShowingStyle: 'message' });
        if (title.length === 0) {
            this.setState(prevState => ({
                error: {
                    ...prevState.error,
                    title: 'Campaign title can not be empty!'
                }
            }));
            return false;
        }
        else {
            this.setState(prevState => ({
                error: {
                    ...prevState.error,
                    title: ''
                }
            }));
            return true;
        }
    }

    validateMessage = () => {
        const message = this.state.error.message;
        return message.length === 0;
    }

    validateStartDate = () => {
        if(!this.state.isReadOnlyStartDate){
            this.setState({ messageShowingStyle: 'message' });
            if(this.state.startDate === ''){
                this.setState(prevState => ({
                    error: {
                        ...prevState.error,
                        startDate: 'Please choose start date!'
                    }
                }));
                return false;
            }
            const startDate = this.formatDate(new Date(this.state.startDate));
            const currentDate = this.formatDate(new Date());
            if(startDate < currentDate){
                this.setState(prevState => ({
                    error: {
                        ...prevState.error,
                        startDate: 'Campaign start date can not less than current date!'
                    }
                }));
                return false;
            }
            else {
                this.setState(prevState => ({
                    error: {
                        ...prevState.error,
                        startDate: ''
                    }
                }));
                return true;
            }
        }
        return true; 
    }

    validateEndDate = () => {
        this.setState({ messageShowingStyle: 'message' });
        if(this.state.endDate === ''){
            this.setState(prevState => ({
                error: {
                    ...prevState.error,
                    endDate: 'Please choose end date!'
                }
            }));
            return false;
        }
        const endDate = new Date(this.state.endDate);
        let startDate = new Date(this.state.startDate);
        if (endDate < startDate){
            this.setState(prevState => ({
                error: {
                    ...prevState.error,
                    endDate: 'Campaign end date can not less than start date!'
                }
            }));
            return false;
        }
        else {
            this.setState(prevState => ({
                error: {
                    ...prevState.error,
                    endDate: ''
                }
            }));
            return true;
        }
    }

    validateFinalUrl = () => {
        const finalURL = this.state.finalURL;
        this.setState({ messageShowingStyle: 'message' });
        if (finalURL.length === 0) {
            this.setState(prevState => ({
                error: {
                    ...prevState.error,
                    finalURL: 'Campaign name can not be empty!'
                }
            }));
            return false;
        }
        else {
            this.setState(prevState => ({
                error: {
                    ...prevState.error,
                    finalURL: ''
                }
            }));
            return true;
        }
    }

    validateBudget = () =>{
        const bid = this.state.bid;
        const budget = this.state.budget;
        if(budget < bid){
            this.setState(prevState => ({
                error: {
                    ...prevState.error,
                    budget: 'Budget must great than bid!'
                }
            }));
            return false;
        }
        else {
            this.setState(prevState => ({
                error: {
                    ...prevState.error,
                    budget: ''
                }
            }));
            return true;
        }
    }

    formSubmit = async (event) =>{
        event.preventDefault();      
        if(this.validateName() && this.validateTitle() && this.validateStartDate() && this.validateEndDate() && this.validateBudget() && this.validateFinalUrl() && this.validateMessage()){
            this.setState({
                showLoading : 'show',
                isButtonEnable : true
            });
            const imageURL = await this.getImageUrl();
            if(imageURL !== ''){
                this.setState({imageURL : imageURL});
            }
            
            const data ={
                id : this.state.campaignId, 
                name: this.state.name,
                status: this.state.status,
                startDate: this.state.startDate,
                endDate: this.state.endDate,
                budget: this.state.budget,
                bid: this.state.bid,
                title: this.state.title,
                description: this.state.description,
                imageURL: this.state.imageURL,
                productURL: this.state.finalURL
            }

            let url = '';
            if(localStorage.role === 'ROLE_ADMIN'){
                url = urlEditCampaignByAdmin;
            }
            else if(localStorage.role === 'ROLE_SHOP'){
                url = urlEditCampaignByShop;
            }
            const response = await fetch(url, {
                method: 'PUT',
                headers: {
                    'Content-Type': 'application/json',
                    'Authorization': localStorage.token
                },
                body: JSON.stringify(data)
            });
            let result = await response.json();
            if(result.status === 'SUCCESS'){
                this.setState({showLoading : 'collapse'});
                this.setState({isRedirect : true});
            }
            else if(result.status === 'FAILED'){
                this.setState(prevState => ({
                    showLoading : 'collapse',
                    isButtonEnable : false,
                    error: {
                        ...prevState.error,
                        message: result.message
                    }
                }));
            }
        }
    }

    render() {
        const isRedirect = this.state.isRedirect;
        if(isRedirect){
            return <Redirect to='/manage/campaign/dashboard'/>
        }
        return (
             <div className="main">
                <div className="container">
                    <div className="campaign-container">
                        <div className="col-md-12">
                            <h4>Edit Campaign</h4>
                            <hr />
                            <div className="campaign-content">
                                <form onSubmit={this.formSubmit}>
                                    {/*************************************Detail*****************************/}
                                    <div className="card">
                                        <div className="card-header custom-card-header" onClick={this.onOpenComponent} data-toggle="collapse" data-target="#collapseDetail" aria-expanded="true" aria-controls="collapseDetail">
                                            <h5 aria-controls="collapseDetail">Detail</h5>
                                            <div className="pull-right">
                                                <button type="button" aria-controls="collapseDetail">
                                                    <i className={this.state.dropdownArrowStyle.detail} aria-controls="collapseDetail" />
                                                </button>
                                            </div>
                                        </div>
                                        <div className="collapse show" id="collapseDetail">
                                            <div className="card-body">
                                                <div className="form-group row custom-form-row">
                                                    <label className="col-4 col-form-label">Name :</label>
                                                    <div className="col-8 custom-style">
                                                        <input type="text"
                                                            name="name"
                                                            placeholder="Name"
                                                            className="form-control"
                                                            required
                                                            value={this.state.name}
                                                            onChange={this.onChange}
                                                            onBlur={this.validateName}
                                                            onFocus={this.onFocus} />
                                                    </div>
                                                </div>
                                                <div className="form-group row">
                                                    <div className="offset-4 col-8 message">
                                                        {this.state.error.name}
                                                    </div>
                                                </div>
                                                <div className="form-group row custom-form-row">
                                                    <label className="col-4 col-form-label">Campaign Status :</label>
                                                    <div className="col-8 custom-style">
                                                        <select name="status" className="form-control" value={this.state.status} onChange={this.onChange}>
                                                            <option value="ACTIVE">ACTIVE</option>
                                                            <option value="PAUSE">PAUSE</option>
                                                        </select>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>

                                    {/*************************************Schedule*****************************/}
                                    <div className="card">
                                        <div className="card-header custom-card-header" onClick={this.onOpenComponent} data-toggle="collapse" data-target="#collapseSchedule" aria-expanded="true" aria-controls="collapseSchedule">
                                            <h5 aria-controls="collapseSchedule">Schedule</h5>
                                            <div className="pull-right">
                                                <button type="button" aria-controls="collapseSchedule">
                                                    <i className={this.state.dropdownArrowStyle.schedule} aria-controls="collapseSchedule" />
                                                </button>
                                            </div>
                                        </div>
                                        <div className="collapse show" id="collapseSchedule">
                                            <div className="card-body">
                                                <div className="form-group row custom-form-row">
                                                    <label className="col-4 col-form-label">Start date :</label>
                                                    <div className="col-8 custom-style">
                                                        <div className="input-left">
                                                            <input type="date"
                                                                name="startDate"
                                                                id="startDate"
                                                                className="form-control"
                                                                readOnly={this.state.isReadOnlyStartDate}
                                                                value={this.state.startDate}
                                                                onChange={this.onChange}
                                                                onBlur={this.validateStartDate}
                                                                onFocus={this.onFocus}/>
                                                        </div>
                                                    </div>
                                                </div>
                                                <div className="form-group row">
                                                    <div className="offset-4 col-8 message">
                                                        {this.state.error.startDate}
                                                    </div>
                                                </div>
                                                <div className="form-group row custom-form-row">
                                                    <label className="col-4 col-form-label">End date :</label>
                                                    <div className="col-8 custom-style">
                                                        <div className="input-left">
                                                            <input type="date"
                                                                name="endDate"
                                                                id="endDate"
                                                                className="form-control"                                                               
                                                                value={this.state.endDate}
                                                                onChange={this.onChange}
                                                                onBlur={this.validateEndDate}
                                                                onFocus={this.onFocus}/>
                                                        </div>
                                                    </div>
                                                </div>
                                                <div className="form-group row">
                                                    <div className="offset-4 col-8 message">
                                                        {this.state.error.endDate}
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>

                                    {/*************************************Budget*****************************/}
                                    <div className="card">
                                        <div className="card-header custom-card-header" onClick={this.onOpenComponent} data-toggle="collapse" data-target="#collapseBudget" aria-expanded="true" aria-controls="collapseBudget">
                                            <h5 aria-controls="collapseBudget">Budget</h5>
                                            <div className="pull-right">
                                                <button type="button" aria-controls="collapseBudget">
                                                    <i className={this.state.dropdownArrowStyle.budget} aria-controls="collapseBudget" />
                                                </button>
                                            </div>
                                        </div>
                                        <div className="collapse show" id="collapseBudget">
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
                                                            <input type="number"
                                                                name="budget"
                                                                placeholder="Budget"
                                                                min="1"
                                                                className="form-control"
                                                                required
                                                                value={this.state.budget}
                                                                onChange={this.onChange}
                                                                onFocus={this.onFocus} 
                                                                onBlur={this.validateBudget}/>
                                                        </div>
                                                    </div>
                                                </div>
                                                <div className="form-group row">
                                                    <div className="offset-4 col-8 message">
                                                        {this.state.error.budget}
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>

                                    {/*************************************Bid Amount*****************************/}
                                    <div className="card">
                                        <div className="card-header custom-card-header" onClick={this.onOpenComponent} data-toggle="collapse" data-target="#collapseBidding" aria-expanded="true" aria-controls="collapseBidding">
                                            <h5 aria-controls="collapseBidding">Bidding</h5>
                                            <div className="pull-right">
                                                <button type="button" aria-controls="collapseBidding">
                                                    <i className={this.state.dropdownArrowStyle.bidding} aria-controls="collapseBidding" />
                                                </button>
                                            </div>
                                        </div>
                                        <div className="collapse show" id="collapseBidding">
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
                                                            <input type="number" 
                                                                   name="bid" 
                                                                   value={this.state.bid} 
                                                                   readOnly
                                                                   className="form-control" />
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>

                                    {/*************************************Creative*****************************/}
                                    <div className="card">
                                        <div className="card-header custom-card-header" onClick={this.onOpenComponent} data-toggle="collapse" data-target="#collapseCreative" aria-expanded="true" aria-controls="collapseCreative">
                                            <h5 aria-controls="collapseCreative">Creative</h5>
                                            <div className="pull-right">
                                                <button type="button" aria-controls="collapseCreative">
                                                    <i className={this.state.dropdownArrowStyle.creative} aria-controls="collapseCreative" />
                                                </button>
                                            </div>
                                        </div>
                                        <div className="collapse show" id="collapseCreative">
                                            <div className="card-body">
                                                <div className="form-group row custom-form-row">
                                                    <label className="col-4 col-form-label">Title :</label>
                                                    <div className="col-8">
                                                        <input type="text"
                                                            name="title"
                                                            placeholder="Title"
                                                            className="form-control"
                                                            required
                                                            value={this.state.title}
                                                            onChange={this.onChange}
                                                            onBlur={this.validateTitle}
                                                            onFocus={this.onFocus} />
                                                    </div>
                                                </div>
                                                <div className="form-group row">
                                                    <div className="offset-4 col-8 message">
                                                        {this.state.error.title}
                                                    </div>
                                                </div>
                                                <div className="form-group row custom-form-row">
                                                    <label className="col-4 col-form-label">Description :</label>
                                                    <div className="col-8">
                                                        <textarea name="description"
                                                            placeholder="Description"
                                                            className="form-control"
                                                            rows="4"
                                                            value={this.state.description}
                                                            onChange={this.onChange} />
                                                    </div>
                                                </div>
                                                <div className="form-group row custom-form-row">
                                                    <label className="col-4 col-form-label">Create preview :</label>
                                                    <div className="col-8 custom-style">
                                                        <input type="file"
                                                            accept="images/*"
                                                            name="imageFile"
                                                            onChange={(e) => this.loadFile(e)}/>
                                                    </div>
                                                    <div className="offset-4 col-8 img-preview">
                                                        <img src={this.state.imageURL} id="preview" alt="" />
                                                    </div>
                                                </div>
                                                <div className="form-group row custom-form-row">
                                                    <label className="col-4 col-form-label">Final URL :</label>
                                                    <div className="col-8">
                                                        <input type="text"
                                                            name="finalURL"
                                                            placeholder="Final URL"
                                                            className="form-control"
                                                            required
                                                            value={this.state.finalURL}
                                                            onChange={this.onChange}
                                                            onFocus={this.onFocus}
                                                            onBlur={this.validateFinalUrl} />
                                                    </div>
                                                </div>
                                                <div className="form-group row">
                                                    <div className="offset-4 col-8 message">
                                                        {this.state.error.finalURL}
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>

                                    <div className="form-group form-contain-button">
                                        <button className="btn"><Link to="/manage/campaign/dashboard">Back to list</Link></button>
                                        <button type="submit" className="btn btn-info" disabled={this.state.isButtonEnable}>Save</button>
                                    </div>

                                    <div className="form-group row">
                                        <div className="offset-4 col-8">
                                            <div className={this.state.messageShowingStyle}>
                                                {this.state.error.message}
                                            </div>
                                        </div>
                                    </div>

                                    <div className="form-group row">
                                        <div className="offset-4 col-8">
                                            <div className={this.state.showLoading}>
                                                <i className="fa fa-spinner fa-spin" />Loading....
                                            </div>
                                        </div>
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

export default CampaignEdit;