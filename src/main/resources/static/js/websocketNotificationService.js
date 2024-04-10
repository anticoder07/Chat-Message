function connectNotification() {
    if (getAuthId()) {
        let socket = new SockJS('/ws');
        stompNotification = Stomp.over(socket);

        stompNotification.connect({}, () => {
            console.log('Connected to Notification');
            enterRoomNotification(getAuthId());
        }, onError);
    }
}
function enterRoomNotification(newRoomId) {
    topicNotification = `/notification/${newRoomId}`;
    stompNotification.subscribe(topicNotification, onMessageReceivedNotification);
    stompNotification.send(`${topicNotification}/addUser`, {}, JSON.stringify({sender: getAuthName(), type: 'JOIN'}));
}

function onError(error) {
    console.error('Error during WebSocket connection:', error);
}

function onMessageReceivedNotification(payload) {
    let message = JSON.parse(payload.body);
    console.log(message);
}

document.addEventListener("DOMContentLoaded", function () {
    connectNotification();
});

