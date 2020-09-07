import {RequestBinEvent} from "./RequestBinEvent";

const dataLiteral: string = 'DATA';

export const sseDataFilter = (event: MessageEvent, onData: (event: RequestBinEvent) => void) => {
    const requestBinEvent = <RequestBinEvent>JSON.parse(event.data);

    if (requestBinEvent.eventType === dataLiteral) {
        onData(requestBinEvent);
    }
};