import React, {useState, useEffect} from 'react';
import requestBinClient from "../../http/RequestBinClient";
import {Redirect} from "react-router-dom";

const LoadingLandingPage = () => {
    const [requestBin, setRequestBin] = useState(false);

    if (!requestBin) {
        requestBinClient.post('/').then(value => setRequestBin(value.data.binId));
    }

    if (requestBin) {
        const redirectObject = {
            pathname: `/${requestBin}`,
            state: {
                binId: requestBin
            }
        };

        return <Redirect to={redirectObject}/>
    }

    return (
        <section className="hero is-fullheight is-primary is-bold">
            <div className="hero-body">
                <div className="container">
                    <h1 className="title is-1 has-text-centered has-text-weight-light">
                        Creating a new Request Bin
                    </h1>
                </div>
            </div>
        </section>
    );
};

export default LoadingLandingPage;