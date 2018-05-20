$( document ).ready(function() {
    var wsAddress = "ws://" + window.location.hostname + ":18001";
    var ws = new WebSocket(wsAddress);
    console.log(ws);

    ws.onopen = function (data) {
        var subscribe = { binIdentifier: binId }
        ws.send(JSON.stringify(subscribe));
    };

    ws.onmessage = function (data) {
        console.log(data);
    };
});
