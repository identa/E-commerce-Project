import React, { Component } from 'react';
import {Carousel} from 'react-bootstrap';

class SlideShow extends Component {
    render() {
        return (
            <div className="slide-show">
                <Carousel>
                    <Carousel.Item>
                        <img src="./assets/images/slider/1.jpg" alt="First slide"/>
                    </Carousel.Item>

                    <Carousel.Item>
                        <img src="./assets/images/slider/2.jpg" alt="Second slide"/>
                    </Carousel.Item>

                    <Carousel.Item>
                        <img src="./assets/images/slider/3.jpg" alt="Third slide"/>
                    </Carousel.Item>

                    <Carousel.Item>
                        <img src="./assets/images/slider/4.jpg" alt="Fourth slide"/>
                    </Carousel.Item>
                </Carousel>
            </div>
        );
    }
}

export default SlideShow;