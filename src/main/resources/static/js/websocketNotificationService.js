function connectNotification() {
    if (getAuthId()) {
        let socket = new SockJS('/ws');
        stompNotification = Stomp.over(socket);

        notificationSubscription = stompNotification.connect({}, () => enterRoomNotification(getAuthId()), {});
    }
}

function enterRoomNotification(newRoomId) {
    topic = `/app/notification/${newRoomId}`;

    stompNotification.subscribe(`/channel/${newRoomId}`, onMessageReceivedNotification);

    stompNotification.send(`${topic}/addUser`,
        {},
        JSON.stringify({sender: getAuthName(), type: 'JOIN'})
    );
}

function onMessageReceivedNotification(payload) {
    let message = JSON.parse(payload.body);
    console.log(message);
}

document.addEventListener("DOMContentLoaded", function () {
    connectNotification();
});

