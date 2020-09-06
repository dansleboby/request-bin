import {RequestBinEvent} from "./RequestBinEvent";

export const sseDataFilter = (event: MessageEvent, onData: (event: RequestBinEvent) => void) => {
    const requestBinEvent = <RequestBinEvent>JSON.parse(event.data);

    if (requestBinEvent.eventType === 'DATA') {
        onData(requestBinEvent);
    }
};