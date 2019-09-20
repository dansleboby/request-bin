import React, {useState, useEffect} from 'react';
import Root from "../Root/Root";
import Controls from "../../components/controls/Controls";
import HttpRequest from "../HttpRequest/HttpRequest";
import useRequestBin from "../../hooks/UseRequestBin";
import BlankHttpRequest from "../HttpRequest/BlankHttpRequest";

const httpRequestHistory = [];

const ControlPanel = (props) => {
    const updateHistory = (httpRequest) => {
        if (httpRequest !== null) {
            httpRequestHistory.push(httpRequest);
            setLatestHttpRequest(httpRequest);
        }
    };

    const [latestHttpRequest, setLatestHttpRequest] = useState(null);
    const [latestUpdate, setLatestUpdate] = useState(null);
    const selectedHttpRequest = latestHttpRequest;

    // useEffect(() => updateHistory(latestHttpRequest));
    useRequestBin(props.binId, updateHistory, setLatestUpdate);

    const httpRequest = selectedHttpRequest === null
        ? <BlankHttpRequest/>
        : <HttpRequest httpRequest={selectedHttpRequest}/>;


    return (
        <Root>
            <Controls
                latestUpdate={latestUpdate}
                current={httpRequestHistory.length}
                total={httpRequestHistory.length}
            />
            <hr/>
            {httpRequest}
        </Root>
    );
};

export default ControlPanel;