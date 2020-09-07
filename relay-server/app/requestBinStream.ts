import {relay} from "./relay";

import {sseDataFilter} from "./sseDataFilter";
import {fatal} from "./fatal";

const EventSource = require('eventsource');

export const requestBinStream = (host: string, stream: string, target: string) => {
    const requestBinUrl: string = [host, 'api', stream, 'stream'].join('/');
    const eventSource: EventSource = new EventSource(requestBinUrl);

    eventSource.onerror = () => {
        fatal(`Whoops. The request bin '${stream}' does not seem to exist on '${host}'!`);
    };
    eventSource.onmessage = (event: MessageEvent) => {
        sseDataFilter(event, (event) => relay(target, event));
    };
};