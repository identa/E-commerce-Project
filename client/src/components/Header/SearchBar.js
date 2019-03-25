import React, { Component } from 'react';

class SearchBar extends Component {
    render() {
        return (
            <div className="search-box">
                <form>
                    <div className="form-content">
                        <input type="text" placeholder="Search here...." />
                        <button type="submit">
                            <i className="fa fa-search" />
                        </button>
                    </div>
                </form>
            </div>
        );
    }
}

export default SearchBar;