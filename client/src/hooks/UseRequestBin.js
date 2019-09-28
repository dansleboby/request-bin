import React, {useState, useEffect} from 'react';

const sseParse: { eventType: string, data: any } = event => JSON.parse(event.data);

const eventDispatcher = (dataCallback: function, updateCallback: function, event) => {
    const parsedEvent = sseParse(event);

    if (parsedEvent.eventType === 'DATA') {
        dataCallback(parsedEvent.data);
    } else if (parsedEvent.eventType === 'HEARTBEAT') {
        updateCallback(new Date());
    }
};

function useRequestBin(binId: string, dataCallback: function, updateCallback: function): void {
    useEffect(() => {
        const eventSourceUrl = process.env.NODE_ENV === 'production'
            ? 'https://bin.graversen.io'
            : 'http://localhost:8080';
        const eventSource = new EventSource(`${eventSourceUrl}/${binId}/stream`);
        eventSource.onmessage = event => eventDispatcher(dataCallback, updateCallback, event);
    }, [binId]);
}

export default useRequestBin;