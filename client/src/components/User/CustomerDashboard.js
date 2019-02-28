import React, { Component } from 'react';
import RowItem from './RowItem';

const url = 'https://dac-java.herokuapp.com/api/admin/getByPageAndSize';

class CustomerDashboard extends Component {
    constructor(props) {
        super(props);
        
        this.state = {
            itemPerPage : 5,
            totalPages : 0,
            userResponseList : [],
            activePage: 1,
            activeClassName : 'page-item'
        }
    }
    

    componentDidMount() {
        const dataGet = {
            page : "1",
            size : this.state.itemPerPage
        };
        this.fetchData(dataGet);
    }

    createPageIndex = () =>{
        const totalPages = this.state.totalPages;
        let ul = [];
        for (let i = 0; i < totalPages; i++){
            ul.push(<li className={this.state.activeClassName} key={i}>
                <a className="page-link" id={'page-'+`${i + 1}`} href="javascript:void(0)" onClick={this.handlePaging}>{`${i + 1}`}</a>
            </li>);
        }
        return ul;
    }

    handlePaging = (event) => {
        const pageId = Number(event.target.id.replace('page-',''));
        const dataSend = {
            page : pageId,
            size : this.state.itemPerPage
        };
        this.fetchData(dataSend);
    }

    fetchData = (data) => {
        const token = localStorage.getItem("token");

        fetch(url ,{
            method : 'POST',
            body: JSON.stringify(data),
            headers : {
                "Content-Type" : "application/json",
                "Authorization" : token
            }    
        })
        .then(res => res.json())
        .then(data =>{
            if(data.status === 'SUCCESS'){
                this.setState({totalPages : data.data.totalPages});               
                this.setState({userResponseList : data.data.userResponseList}); 
            }
            else if(data.status === 'FAILED'){

            }
        })
        .catch(err => {

        });
    }

    render() {
        const userResponseList= this.state.userResponseList;
        return (
            <div className="main">
                <div className="container">
                    <h2>Dashboard</h2>
                    <div className="row btn-create">
                        <button className="btn btn-success btn-action">
                            <i className="fa fa-plus"></i>Create
                        </button>

                        <div className="form-search">
                            <form>
                                <label>Search : </label>
                                <input type="text" name="keyword"/>
                            </form>
                        </div>
                    </div>
                    <div className="row">
                        <table className="table table-bordered">
                            <tbody>
                                <tr>
                                    <th>Id</th>
                                    <th>First Name</th>
                                    <th>Last Name</th>
                                    <th>Email</th>
                                    <th>Role</th>
                                    <th>Status</th>
                                    <th>&nbsp;</th>
                                </tr>
                                {
                                    userResponseList.map((value,key) => {
                                        return <RowItem key={key} 
                                                        uid={value.id} 
                                                        firstName={value.firstName}
                                                        lastName={value.lastName}
                                                        email={value.email}
                                                        status={value.status}
                                                        role={value.role}/>
                                    })
                                }
                            </tbody>
                        </table>
                                                
                        <ul className="pagination">
                            {this.createPageIndex()} 
                        </ul>
                    </div>
                </div>
            </div>
        );
    }
}

export default CustomerDashboard;