import React from 'react';
import Root from "../Root/Root";
import HttpHeaders from "../../components/http/HttpHeaders/HttpHeaders";
import HttpQueryString from "../../components/http/QueryString/HttpQueryString";
import HttpBody from "../../components/http/Body/HttpBody";

const HttpRequest = (props) => {
    const request = props.httpRequest;

    const httpHeaders = Object.entries(request.httpHeaders).map(([key, value]) => ({key,value}));
    const queryParameters = Object.entries(request.queryParameters).map(([key, value]) => ({key,value}));
    const httpBody = atob(request.encodedRequestBody);

    return (
        <Root>
            <div className="container">
                <div className="notification is-warning">
                    <div className="columns">
                        <div className="column">
                            <strong>HTTP Request</strong>
                        </div>
                        <div className="column">
                            <p>
                                <strong>Received at:</strong> {request.createdAt}
                            </p>
                        </div>
                        <div className="column">
                            <p>
                                <strong>Response time:</strong> {request.requestDuration}
                            </p>
                        </div>
                        <div className="column">
                            <p>
                                <strong>HTTP Verb:</strong> {request.httpVerb}
                            </p>
                        </div>
                    </div>
                </div>
            </div>
            <br/>
            <HttpHeaders httpHeaders={httpHeaders}/>
            <br/>
            <HttpQueryString queryParameters={queryParameters}/>
            <br/>
            <HttpBody httpBody={httpBody}/>
        </Root>
    );
};

export default HttpRequest;