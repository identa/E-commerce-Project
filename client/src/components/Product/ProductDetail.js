import React, { Component } from 'react';

const src = 'https://images.unsplash.com/photo-1444065381814-865dc9da92c0'
class ProductDetail extends Component {
    constructor(props) {
        super(props);
        
        this.state={
            style :{
                backgroundImage: `url(${src})`,
                backgroundPosition : '0% 0%'
            },
        }
    }
    
    handleMouseMove = event => {
        const { left, top, width, height } = event.target.getBoundingClientRect()
        const x = (event.pageX - left) / width * 100
        const y = (event.pageY - top) / height * 100
        this.setState(prevState => ({
            style: {
                ...prevState.style,
                backgroundPosition: `${x}% ${y}%`
            }
        }));
    }
    render() {
        return (
            <div className="main">
                <div className="container">
                    <div className="page-container">
                        <div className="row">
                            <div className="col-12 detail-nav">
                                <a href="/">Home</a>
                                <i className="fa fa-angle-double-right" />
                                <a href="#">Category name</a>
                                <i className="fa fa-angle-double-right" />
                                <a className="last-item" href="javascript:void(0)">Product name</a>
                            </div>
                        </div>
                        <div className="row">
                            <div className="product-detail col-12">
                                <div className="row">
                                    <div className="col-md-3 col-sm-3 product-detail-image">
                                        <figure onMouseMove={this.handleMouseMove} style={this.state.style}>
                                            <img src={src} alt=''/>
                                        </figure>
                                    </div>
                                    <div className="col-md-9 col-sm-9 product-detail-info">
                                        <div className="product-title">
                                            <h1>product name</h1>
                                        </div>
                                        <div className="product-price">
                                            <span>9999$</span>
                                        </div>
                                        <div className="product-quantity">
                                            <span>Quantity :</span>
                                            <button className="btn btn-minus">-</button>
                                            <input type="number" defaultValue={1} min={1} />
                                            <button className="btn btn-plus">+</button>
                                        </div>
                                        <div className="product-btn-add-cart">
                                            <button className="btn btn-dark">
                                                <i className="fa fa-shopping-cart" />Add to cart
                          </button>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div className="row">
                            <div className="product-description col-12">
                                <ul className="nav nav-tabs">
                                    <li className="active">
                                        <span>Description</span>
                                    </li>
                                </ul>
                                <div className="product-detail-description">
                                    RUNTIME.VN là một thương hiệu Việt, được xây dựng từ một tập thể nhiều kinh nghiệm và đầy
                                    nhiệt
                                    huyết trong lĩnh vực cung cấp giải pháp công nghệ số. Với tiêu chí hỗ trợ xây dựng và phát
                                    triển
                                    thị trường thương mại diện tử Việt Nam, Runtime.vn luôn tập trung phát triển những sản phẩm
                                    chủ
                                    lực cho việc quản trị doanh nghiệp, hỗ trợ bán hàng dễ dàng &amp; hiệu quả.
                                    RUNTIME.VN là một môi trường website. Trên môi trường website này, người sử dụng có thể tự
                                    khởi
                                    tạo cho riêng mình một website mang phong cách của chính mình. Với các tính năng hỗ trợ được
                                    xây
                                    dựng từ các nhà cung cấp giải pháp có uy tín như Thanh toán online; Theo dõi lịch trình giao
                                    nhận đơn hàng trực tuyến; Chat với khách hàng..v.v... người sử dụng web từ hệ thống
                                    RUNTIME.VN
                                    để bán hàng sẽ luôn dễ dàng chăm sóc và quản lý việc kinh doanh của mình một cách tốt nhất.
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        );
    }
}

export default ProductDetail;