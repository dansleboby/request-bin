import React, {useState, useEffect} from 'react';
import ControlPanel from "../ControlPanel/ControlPanel";
import requestBinClient from "../../http/RequestBinClient";
import Root from "../Root/Root";
import {Redirect} from "react-router-dom";

const RequestBinLandingPage = (props) => {
    const [isFound, setIsFound] = useState(true);
    const binId = props.match.params.binId;

    if (!isFound) {
        return <Redirect to='/404'/>;
    }

    requestBinClient.get(`/${binId}/status`).catch(error => setIsFound(false));

    return (
        <section className="section">
            <div className="container">
                <h1 className="title is-2 has-text-weight-light">
                    Request Bin
                </h1>
                <ControlPanel binId={binId}/>
            </div>
        </section>
    );
};

export default RequestBinLandingPage;