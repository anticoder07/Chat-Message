function connectNotification() {
    if (getAuthId()) {
        let socket = new SockJS(endPointWs);
        stompNotification = Stomp.over(socket);

        stompNotification.connect({}, () => {
            enterRoomNotification(getAuthId());
        }, onError);
    }
}

function enterRoomNotification(newRoomId) {
    topicNotification = `/app/notification/${newRoomId}`;

    stompNotification.subscribe(`/current-user/${newRoomId}`, onMessageReceivedNotification);
    stompNotification.send(`${topicNotification}/addUsers`,
        {},
        JSON.stringify({sender: username, type: 'JOIN'})
    );
}

function onError(error) {
    console.error('Error during WebSocket connection:', error);
}

function onMessageReceivedNotification(payload) {
    let message = JSON.parse(payload.body);
    console.log(message)
    let userNotification = document.querySelector(`#user-${message.senderId}`);
    userNotification.classList.add("red-notification");
}

connectNotification();

