import React, {useState, useEffect} from 'react';
import Root from "../Root/Root";
import Controls from "../../components/controls/Controls";
import HttpRequest from "../HttpRequest/HttpRequest";
import useRequestBin from "../../hooks/UseRequestBin";

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

    return (
        <Root>
            <Controls
                latestUpdate={latestUpdate}
                current={httpRequestHistory.length + 1}
                total={httpRequestHistory.length + 1}
            />
            <hr/>
            <HttpRequest httpRequest={selectedHttpRequest}/>
        </Root>
    );
};

export default ControlPanel;