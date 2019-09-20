import React from 'react';
import NextButton from "./NextButton";
import PreviousButton from "./PreviousButton";
import PlayButton from "./PlayButton";
import Root from "../../containers/Root/Root";
import Status from "./Status";

const Controls = (props) => (
    <Root>
        <div className="buttons are-medium">
            <PlayButton/>
            <PreviousButton/>
            <NextButton/>
        </div>
        <Status latestUpdate={props.latestUpdate}/>
    </Root>
);

export default Controls;