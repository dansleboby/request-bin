import React from 'react';

const playButton = () => {
    let isPlaying = true;
    const iconPlay = "fas fa-play";
    const iconPause = "fas fa-pause";

    return (
        <a className="button is-outlined">
            <i className={iconPause}/>
        </a>
    );
};

export default playButton;