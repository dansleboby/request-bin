import React from 'react';
import NextButton from "./NextButton";
import PreviousButton from "./PreviousButton";
import PlayButton from "./PlayButton";

function controls() {
    return (
        <div className="buttons are-medium">
            <PlayButton/>
            <PreviousButton/>
            <NextButton/>
        </div>
    );
}

export default controls;