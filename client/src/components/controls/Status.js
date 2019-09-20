import React from 'react';

function Status(props) {
    const dateFormatted = (date: Date) => date.toISOString().slice(11, 19);
    const latestUpdateElement = props.latestUpdate === null ? 'Never! 🙀' : dateFormatted(props.latestUpdate);

    return (
        <p className="has-text-grey">
            Viewing request <strong>{props.current}</strong> of <strong>{props.total}</strong>.
            Latest ❤️ from server: {latestUpdateElement}
        </p>
    );
}

export default Status;