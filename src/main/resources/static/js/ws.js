$( document ).ready(function() {
    var wsAddress = "ws://" + window.location.hostname + ":18001";
    var ws = new WebSocket(wsAddress);
    console.log(ws);

    ws.onopen = function (openEvent) {
        var subscribe = { binIdentifier: binIdentifier }
        ws.send(JSON.stringify(subscribe));
    };

    ws.onmessage = function (messageEvent) {
        showOrQueue(messageEvent.data);
    };
});
