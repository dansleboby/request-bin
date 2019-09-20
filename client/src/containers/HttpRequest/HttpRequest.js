import React from 'react';
import Root from "../Root/Root";
import HttpHeaders from "../../components/http/HttpHeaders/HttpHeaders";
import HttpQueryString from "../../components/http/QueryString/HttpQueryString";
import HttpBody from "../../components/http/Body/HttpBody";

const  HttpRequest = (props) => {
    const request = props.httpRequest;
    console.log(request);

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
            <HttpHeaders/>
            <br/>
            <HttpQueryString/>
            <br/>
            <HttpBody/>
        </Root>
    );
};

export default HttpRequest;