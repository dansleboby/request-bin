import React from 'react';
import Root from "../Root/Root";
import HttpHeaders from "../../components/http/HttpHeaders/HttpHeaders";
import HttpQueryString from "../../components/http/QueryString/HttpQueryString";
import HttpBody from "../../components/http/Body/HttpBody";

const httpRequest = () => (
    <Root>
        <HttpHeaders/>
        <br/>
        <HttpQueryString/>
        <br/>
        <HttpBody/>
    </Root>
);

export default httpRequest;