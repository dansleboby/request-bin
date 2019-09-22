import React, {useState, useEffect} from 'react';
import Root from "../Root/Root";
import Controls from "../../components/controls/Controls";
import HttpRequest from "../HttpRequest/HttpRequest";
import useRequestBin from "../../hooks/UseRequestBin";
import BlankHttpRequest from "../HttpRequest/BlankHttpRequest";
import requestBinClient from "../../http/RequestBinClient";

const ControlPanel = (props) => {
    const [httpRequestHistory, setHttpRequestHistory] = useState([]);
    const [latestUpdate, setLatestUpdate] = useState(null);
    const [index, setIndex] = useState(0);
    const [isPaused, setIsPaused] = useState(false);
    const [isSynced, setIsSynced] = useState(false);

    if (index === 0 && httpRequestHistory.length > 0) {
        setIndex(1);
    }

    const selectIndex = isPaused ? index : httpRequestHistory.length;
    const selectedHttpRequest = httpRequestHistory[selectIndex - 1];

    const playClicked = () => {
        setIsPaused(isPaused => !isPaused);
        setIndex(httpRequestHistory.length);
    };

    const goBackClicked = () => {
        setIsPaused(true);

        if (selectIndex > 1) {
            setIndex(selectIndex - 1);
        }
    };

    const goForwardClicked = () => {
        if (index !== httpRequestHistory.length) {
            setIndex(index => index + 1);
        }
    };

    const syncClicked = () => {
        if (!isSynced) {
            setIsSynced(true);
            setHttpRequestHistory([]);
            requestBinClient.get(`/${props.binId}/stream/100`)
        }
    };

    const updateHttpRequest = (httpRequest) => {
        setHttpRequestHistory(previousHistory => [...previousHistory, httpRequest]);
    };

    useRequestBin(props.binId, updateHttpRequest, setLatestUpdate);

    const httpRequest = selectedHttpRequest === undefined
        ? <BlankHttpRequest/>
        : <HttpRequest httpRequest={selectedHttpRequest}/>;


    return (
        <Root>
            <Controls
                latestUpdate={latestUpdate}
                current={selectIndex}
                total={httpRequestHistory.length}
                playClickHandler={playClicked}
                goBackHandler={goBackClicked}
                goForwardHandler={goForwardClicked}
                syncHandler={syncClicked}
                isSynced={isSynced}
                isPaused={isPaused}
            />
            <hr/>
            {httpRequest}
        </Root>
    );
};

export default ControlPanel;