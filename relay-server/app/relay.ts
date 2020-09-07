import {RequestBinEvent} from "./RequestBinEvent";
import {EventData} from "./EventData";

const httpClient = require('axios');

const queryParameters = (eventData: EventData) => {
    if (Object.keys(eventData.queryParameters).length !== 0) {
        const parameters = new URLSearchParams();

        for (const property in eventData.queryParameters) {
            if (eventData.queryParameters.hasOwnProperty(property)) {
                parameters.set(property, eventData.queryParameters[property]);
            }
        }

        return '?' + parameters.toString();
    } else {
        return '';
    }
};

export const relay = (target: string, event: RequestBinEvent) => {
    const eventData: EventData = event.data;
    const compiledUrl = target + queryParameters(eventData);
    const requestBody = Buffer.from(eventData.encodedRequestBody, 'base64').toString('ascii');

    httpClient({
        url: compiledUrl,
        method: eventData.httpVerb,
        headers: eventData.httpHeaders,
        data: requestBody
    });
};