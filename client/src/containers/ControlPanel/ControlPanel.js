import React from 'react';
import Root from "../Root/Root";
import Controls from "../../components/controls/Controls";
import HttpRequest from "../HttpRequest/HttpRequest";

const controlPanel = () => (
    <Root>
        <Controls/>
        <hr/>
        <HttpRequest/>
    </Root>
);

export default controlPanel;