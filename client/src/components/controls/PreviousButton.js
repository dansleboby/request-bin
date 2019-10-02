import React from 'react';

const PreviousButton = (props) => (
    <a onClick={props.goBackClicked} className="level-item button is-outlined">
        <i className="fas fa-chevron-left"></i>
    </a>
);

export default PreviousButton;