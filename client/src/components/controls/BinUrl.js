import React from 'react';
import Root from "../../containers/Root/Root";

const BinUrl = (props) => {
    const copyToClipboard = () => navigator.clipboard.writeText(props.binUrl);

    return (
        <Root>
            <a
                onClick={copyToClipboard}
                className="level-item button is-outlined tooltip is-tooltip-left"
                data-tooltip="Copy Request Bin URL"
            >
                <i className="fas fa-copy"/>
            </a>
            <code className="has-text-link">{props.binUrl}</code>
        </Root>
    );
};

export default BinUrl;