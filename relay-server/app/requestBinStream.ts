const EventSource = require('eventsource');

import {sseDataFilter} from "./sseDataFilter";

export const requestBinStream = (host: string, stream: string, target: string) => {
    const requestBinUrl: string = [host, 'api', stream, 'stream'].join('/');

    const eventSource: EventSource = new EventSource(requestBinUrl);

    eventSource.onmessage = (event: MessageEvent) => {
        sseDataFilter(event, (event) => console.log(event));
    };
};