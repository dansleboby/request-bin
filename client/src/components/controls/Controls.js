import React from 'react';
import NextButton from "./NextButton";
import PreviousButton from "./PreviousButton";
import PlayButton from "./PlayButton";
import Root from "../../containers/Root/Root";
import Status from "./Status";
import SyncButton from "./SyncButton";
import BinUrl from "./BinUrl";

const Controls = (props) => (
    <Root>
        <nav className="level">
            <div className="level-left">
                <PlayButton isPaused={props.isPaused} playClickHandler={props.playClickHandler}/>
                <PreviousButton goBackClicked={props.goBackHandler}/>
                <NextButton goForwardClicked={props.goForwardHandler}/>
                <SyncButton isSynced={props.isSynced} syncClicked={props.syncHandler}/>
            </div>
            <div className="level-right">
                <BinUrl binUrl={props.binUrl}/>
            </div>
        </nav>
        <Status latestUpdate={props.latestUpdate} current={props.current} total={props.total}/>
    </Root>
);

export default Controls;