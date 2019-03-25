import React, { Component } from 'react';
import {Carousel} from 'react-bootstrap';

const urlGetCampaign = 'https://dac-project.herokuapp.com/api/public/getCampaign';
class SlideShow extends Component {

    constructor(props) {
        super(props);
        
        this.state = {
            campaignList : []
        }
    }
    
    componentDidMount() {
        fetch(urlGetCampaign, {
            method : 'GET',
            headers :{
                'Content-Type' : 'application/json',
                'Authorization' : localStorage.token
            }
        })
        .then(res => res.json())
        .then(data =>{
            this.setState({campaignList : data.data});
        })
    }
    
    render() {
        const campaignList = this.state.campaignList;
        return (
            <div className="slide-show">
                <Carousel>
                    {
                        campaignList.map((value,key) =>{
                            return (<Carousel.Item key={key}>
                                        <a href={value.productURL} target='_blank' rel="noopener noreferrer">
                                            <img src={value.imageURL} alt={value.title}/>
                                        </a>
                                    </Carousel.Item>)
                        })
                    }
                </Carousel>
            </div>
        );
    }
}

export default SlideShow;