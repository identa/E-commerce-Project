import React, { Component } from 'react';
import { Link } from 'react-router-dom';

const formatter = new Intl.NumberFormat('en-US', {
    style: 'currency',
    currency: 'USD',
    minimumFractionDigits: 2
});
class ProductItem extends Component {
    constructor(props) {
        super(props);

        this.state = {
            pid: this.props.pid,
            pname: this.props.pname,
            price: this.props.price,
            discount: this.props.discount,
            imageURL: this.props.imageURL,
            limit : this.props.limit,
            description : '',
            categoryId : '',
            categoryName : ''
        }
    }
    
    to_slug = (str) => {
        // convert to lowercase
        str = str.toLowerCase();

        // remove 
        str = str.replace(/(à|á|ạ|ả|ã|â|ầ|ấ|ậ|ẩ|ẫ|ă|ằ|ắ|ặ|ẳ|ẵ)/g, 'a');
        str = str.replace(/(è|é|ẹ|ẻ|ẽ|ê|ề|ế|ệ|ể|ễ)/g, 'e');
        str = str.replace(/(ì|í|ị|ỉ|ĩ)/g, 'i');
        str = str.replace(/(ò|ó|ọ|ỏ|õ|ô|ồ|ố|ộ|ổ|ỗ|ơ|ờ|ớ|ợ|ở|ỡ)/g, 'o');
        str = str.replace(/(ù|ú|ụ|ủ|ũ|ư|ừ|ứ|ự|ử|ữ)/g, 'u');
        str = str.replace(/(ỳ|ý|ỵ|ỷ|ỹ)/g, 'y');
        str = str.replace(/(đ)/g, 'd');

        // remove special character
        str = str.replace(/([^0-9a-z-\s])/g, '');

        // replace space by -
        str = str.replace(/(\s+)/g, '-');

        // replace first -
        str = str.replace(/^-+/g, '');

        // replace last -
        str = str.replace(/-+$/g, '');

        // return
        return str;
    }

    render() {
        const data = {
            pid: this.state.pid,
            pname: this.state.pname,
            price: this.state.price,
            discount: this.state.discount,
            imageURL: this.state.imageURL,
            limit : this.state.limit
        }
        return (
            <div className="col-md-2 col-sm-3 col-xs-12 product-item-box">
                <div className="product-item">
                    <div className="product-image">
                        <Link to={{pathname : "/product/detail/" + this.state.pid + "/" + this.to_slug(this.state.pname),state : {data : data} }}>
                            {
                                this.state.imageURL === '' ? (<img src={process.env.PUBLIC_URL + '/assets/images/products/default-product.jpg'}
                                                            className="img-product"
                                                            alt="" />)
                                                            : (<img src={this.state.imageURL}
                                                                className="img-product"
                                                                alt="" />)
                            }

                        </Link>
                    </div>
                    <div className="product-info">
                        <h4 className="product-name">{this.state.pname}</h4>
                        <span className="product-price">{formatter.format(this.state.price)}</span>
                        <div className="action">
                            <Link to={{pathname : "/product/detail/" + this.state.pid + "/" + this.to_slug(this.state.pname),state : {data : data} }} className="btn btn-detail">
                                <i className="fa fa-eye" />
                            </Link>
                            <a href="javascript:void(0)" className="btn btn-add-to-cart add-to-cart">
                                <i className="fa fa-shopping-cart" />
                            </a>
                        </div>
                    </div>
                </div>
            </div>
        );
    }
}

export default ProductItem;