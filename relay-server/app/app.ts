import express = require('express');
import {sseDataFilter} from "./sseDataFilter";

const EventSource = require('eventsource');


const app: express.Application = express();

const eventSource: EventSource = new EventSource("https://bin.graversen.io/api/demo/stream");
eventSource.onmessage = (event: MessageEvent) => {
    sseDataFilter(event, (event) => console.log(event));
};

app.get('/', function (req, res) {
    res.send('Hello World, Part 2!');
});

app.listen(3000, function () {
    console.log('Example app listening on port 3000!');
});