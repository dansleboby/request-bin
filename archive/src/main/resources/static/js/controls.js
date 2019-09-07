$( document ).ready(function() {
    $("time.timeago").timeago();

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
    var httpRequestObj = JSON.parse(httpRequest);
    console.log(httpRequestObj);

    // $("#")
    $("#http-request-body-content").text(window.atob(httpRequestObj.requestBody));
    $("#http-request-response-time").text(httpRequestObj.requestDuration);

    $("#timeago-time").attr("datetime", httpRequestObj.createdAt).attr("title", httpRequestObj.createdAt).text(getTimestampFormatted(httpRequestObj.createdAt));

    getTimestampFormatted(httpRequestObj.createdAt);

    $("time.timeago").timeago();
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

function getTimestampFormatted(iso8601Date) {
    return moment(iso8601Date).format("YYYY-MM-DD HH:mm:ss.SSS");
}