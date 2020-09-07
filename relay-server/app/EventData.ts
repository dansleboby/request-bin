export interface EventData {
    binId: string,
    createdAt: Date,
    encodedRequestBody: string,
    queryParameters: any,
    httpHeaders: any,
    ipAddress: string,
    httpVerb: string
}