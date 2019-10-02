import React from 'react';

const NextButton = (props) => (
    <a onClick={props.goForwardClicked} className="level-item button is-outlined">
        <i className="fas fa-chevron-right"></i>
    </a>
);

export default NextButton;