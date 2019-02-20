import React, { Component } from "react";

class Banner extends Component {
  render() {
    return (
      <div className="banner">
        <div className="category-list">
          <div className="category-content">
            <ul>
              <li>
                <a href="#">Electronic Devices</a>
              </li>
              <li>
                <a href="#">Electronic Accessories</a>
              </li>
              <li>
                <a href="#">TV &amp; Home Appliances</a>
              </li>
              <li>
                <a href="#">Health &amp; Beauty</a>
              </li>
              <li>
                <a href="#">Babies &amp; Toys</a>
              </li>
              <li>
                <a href="#">Groceries &amp; Pets</a>
              </li>
              <li>
                <a href="#">Home &amp; Lifestyle</a>
              </li>
              <li>
                <a href="#">Women's Fashion</a>
              </li>
              <li>
                <a href="#">Men's Fashion</a>
              </li>
              <li>
                <a href="#">Fashion Accessories</a>
              </li>
              <li>
                <a href="#">Sports &amp; Travel</a>
              </li>
              <li>
                <a href="#">Automotive &amp; Motocycle</a>
              </li>
            </ul>
          </div>
        </div>
        <div className="slide-show">
          <div id="myCarousel" className="carousel slide" data-ride="carousel">
            {/* Indicators */}
            <ol className="carousel-indicators">
              <li
                data-target="#myCarousel"
                data-slide-to={0}
                className="active"
              />
              <li data-target="#myCarousel" data-slide-to={1} />
              <li data-target="#myCarousel" data-slide-to={2} />
              <li data-target="#myCarousel" data-slide-to={3} />
            </ol>
            {/* Wrapper for slides */}
            <div className="carousel-inner">
              <div className="item active">
                <img src="./assets/images/slider/1.webp" alt="" />
              </div>
              <div className="item">
                <img src="./assets/images/slider/2.webp" alt="" />
              </div>
              <div className="item">
                <img src="./assets/images/slider/3.webp" alt="" />
              </div>
              <div className="item">
                <img src="./assets/images/slider/4.webp" alt="" />
              </div>
            </div>
            {/* Left and right controls */}
            <a
              className="left carousel-control"
              href="#myCarousel"
              data-slide="prev"
            >
              <span className="glyphicon glyphicon-chevron-left" />
              <span className="sr-only">Previous</span>
            </a>
            <a
              className="right carousel-control"
              href="#myCarousel"
              data-slide="next"
            >
              <span className="glyphicon glyphicon-chevron-right" />
              <span className="sr-only">Next</span>
            </a>
          </div>
        </div>
      </div>
    );
  }
}

export default Banner;
