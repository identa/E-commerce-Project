import React, { Component } from "react";

class MobileCategoryList extends Component {
    render() {
        return (
        <div className="mobile-category-list">
            <div className="mobile-category-text">
                <span>Category list</span>
                <span>
                    <i className="fa fa-angle-down" />
                </span>
            </div>
            <div className="mobile-category-content">
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
        );
    }
}

export default MobileCategoryList;
