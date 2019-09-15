import React from 'react';
import Root from "../Root/Root";
import HttpHeaders from "../../components/http/HttpHeaders/HttpHeaders";
import HttpQueryString from "../../components/http/QueryString/HttpQueryString";
import HttpBody from "../../components/http/Body/HttpBody";

const httpRequest = () => (
    <Root>
        <div className="container">
            <div className="notification">
                <div className="columns">
                    <div className="column">
                        <strong>HTTP Request</strong>
                    </div>
                    <div className="column">
                        <p>
                            <strong>Received at:</strong>
                        </p>
                    </div>
                    <div className="column">
                        <p>
                            <strong>Response time:</strong>
                        </p>
                    </div>
                    <div className="column">
                        <p>
                            <strong>Request ID:</strong>
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

export default httpRequest;