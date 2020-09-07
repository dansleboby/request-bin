import {EventData} from "./EventData";

export interface RequestBinEvent {
    eventType: string;
    data: EventData;
}