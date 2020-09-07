import React from 'react';
import Root from "../../containers/Root/Root";

const RelayServerCommand = (props) => {
    const helpUrl = 'https://github.com/MrGraversen/request-bin/tree/develop/relay-server';
    const command = (binId) => `requestbin --stream ${binId} --target $TARGET`;
    const copyToClipboard = (binId) => navigator.clipboard.writeText(command(binId));

    return (
        <Root>
            <a
                onClick={() => window.open(helpUrl, "_blank")}
                className="level-item button is-outlined tooltip is-tooltip-left"
                data-tooltip="What is this? 🤔"
            >
                <i className="fas fa-question-circle"/>
            </a>
            <a
                onClick={() => copyToClipboard(props.binId)}
                className="level-item button is-outlined tooltip is-tooltip-left"
                data-tooltip="Copy Request Bin Relay Command"
            >
                <i className="fas fa-copy"/>
            </a>
            <code className="has-text-link">{command(props.binId)}</code>
        </Root>
    );
};

export default RelayServerCommand;