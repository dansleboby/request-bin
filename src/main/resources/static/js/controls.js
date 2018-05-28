$( document ).ready(function() {
    $("#play-pause-btn").click(function() {
        isPaused = !isPaused;
        adjustIcon();
        adjustButtonVisibility();
        handleHttpRequests();
    });

    $("#next-btn").click(function() {
        showNext();
    });
});

var httpRequests = [];
var isPaused = false;

function adjustIcon() {
    if(isPaused) {
        $("#play-pause-icon").removeClass("fa-pause").addClass("fa-play");
    } else {
        $("#play-pause-icon").removeClass("fa-play").addClass("fa-pause");
    }
}

function adjustButtonVisibility() {
    if(isPaused) {
        $("#next-btn").show();
        $("#request-queue-count").text("0")
    } else {
        $("#next-btn").hide();
    }
}

function handleHttpRequests() {
    if (!isPaused) {
        showAll();
    }
}

function showOrQueue(httpRequest) {
    if(isPaused) {
        httpRequests.push(httpRequest);
        $("#request-queue-count").text(httpRequests.length)
    } else {
        showOne(httpRequest);
    }
}

function showOne(httpRequest) {
    console.log(JSON.parse(httpRequest));
}

function showNext() {
    if(httpRequests.length > 0) {
        showOne(httpRequests.pop());
        $("#request-queue-count").text(httpRequests.length)
    }
}

function showAll() {
    var currentCount = httpRequests.length;
    for (var i = 0; i < currentCount; i++) {
        showNext();
    }
}