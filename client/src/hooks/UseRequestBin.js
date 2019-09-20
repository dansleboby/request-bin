import React, {useState, useEffect} from 'react';

const sseParse: { eventType: string, data: any } = event => JSON.parse(event.data);

const eventDispatcher = (callback: function, event) => {
    const parsedEvent = sseParse(event);

    if (parsedEvent.eventType === 'DATA') {
        callback(parsedEvent.data);
    }
};

function useRequestBin(binId: string, callback: function): void {
    useEffect(() => {
        const eventSource = new EventSource(`http://localhost:8080/${binId}/stream`);
        eventSource.onmessage = event => eventDispatcher(callback, event);
    });
}

export default useRequestBin;