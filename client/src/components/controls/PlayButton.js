import React, {useState, useEffect} from 'react';

const PlayButton = (props) => {
    const iconPlay = "fas fa-play";
    const iconPause = "fas fa-pause";

    return (
        <a onClick={props.playClickHandler} className="level-item button is-outlined">
            <i className={props.isPaused ? iconPlay : iconPause}/>
        </a>
    );
};

export default PlayButton;