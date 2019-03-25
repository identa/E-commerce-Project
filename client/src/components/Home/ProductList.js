import React, { Component } from 'react';
import ProductItem from './ProductItem';

const urlGetProductList = 'https://dac-project.herokuapp.com/api/public/paginateProduct';
class ProductList extends Component {
    constructor(props) {
        super(props);

        this.state = {
            productList: []
        }
    }

    componentDidMount() {
        fetch(urlGetProductList, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({
                'page': 1,
                'size': 12
            })
        })
            .then(res => res.json())
            .then(data => {
                if (data.status === 'SUCCESS') {
                    this.setState({ productList: data.data.responses });
                }
                else if (data.status === 'FAILED') {
                    console.log(data.message);
                }
            })
    }

    render() {
        return (
            <div className="product-container">
                <div className="container">
                    <h2>
                        <u>Collections</u>
                    </h2>
                    <div className="product-content row">
                        {
                            this.state.productList.map((value,key) =>{
                                return <ProductItem key={key} 
                                                    pid={value.id}
                                                    pname={value.name}
                                                    price={value.originalPrice}
                                                    discount={value.discount}
                                                    limit={value.limit}
                                                    imageURL={value.productImageURL}/>
                            })
                        }
                    </div>

                    <div className="card-load-more">
                        <button className="btn-load-more">Load more</button>
                    </div>
                </div>
            </div>
        );
    }
}

export default ProductList;