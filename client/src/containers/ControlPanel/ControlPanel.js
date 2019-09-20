import React, { useState, useEffect } from 'react';
import Root from "../Root/Root";
import Controls from "../../components/controls/Controls";
import HttpRequest from "../HttpRequest/HttpRequest";

const ControlPanel = () => {
    const [latestHttpRequest, setLatestHttpRequest] = useState(null);

    return (
        <Root>
            <Controls/>
            <hr/>
            <HttpRequest/>
        </Root>
    );
};

export default ControlPanel;