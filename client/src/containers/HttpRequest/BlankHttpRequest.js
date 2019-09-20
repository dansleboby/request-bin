import React from 'react';
import Root from "../Root/Root";
import HttpHeaders from "../../components/http/HttpHeaders/HttpHeaders";
import HttpQueryString from "../../components/http/QueryString/HttpQueryString";
import HttpBody from "../../components/http/Body/HttpBody";

const  BlankHttpRequest = (props) => {
    return (
        <Root>
            <h3 className="has-text-centered subtitle is-4">To start, send your first request to this bin! 🚀</h3>
        </Root>
    );
};

export default BlankHttpRequest;