import React, { Component } from "react";
class RowItem extends Component {
    render() {
        return (
            <tr>
                <td>{this.props.uid}</td>
                <td>{this.props.firstName}</td>
                <td>{this.props.lastName}</td>
                <td>{this.props.email}</td>
                <td>{this.props.role}</td>
                <td>{this.props.status}</td>
                <td>
                    <button className="btn btn-info btn-action">
                        <i className="fa fa-edit" />
                        Edit
                    </button>
                    <button className="btn btn-danger btn-action">
                        <i className="fa fa-remove" />
                        Delete
                    </button>
                </td>
            </tr>
        );
    }
}

export default RowItem;
