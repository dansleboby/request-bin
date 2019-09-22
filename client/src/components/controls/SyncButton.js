import React from 'react';

const SyncButton = (props) => (
    <a
        onClick={props.syncClicked}
        className="button is-outlined tooltip is-tooltip-right"
        data-tooltip="Retrieve the latest HTTP requests for this bin"
    >
        <i className="fas fa-sync"></i>
    </a>
);

export default SyncButton;