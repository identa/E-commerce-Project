import React, { Component } from 'react';
import CategoryList from './CategoryList';
import SlideShow from './SlideShow';

class BannerComponent extends Component {
    render() {
        return (
            <div className="banner">
                <CategoryList/>

                <SlideShow/>
            </div>
        );
    }
}

export default BannerComponent;