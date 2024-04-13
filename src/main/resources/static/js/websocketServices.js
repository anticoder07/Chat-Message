function connect() {
    if (id) {
        let socket = new SockJS(endPointWs);
        stompClient = Stomp.over(socket);

        stompClient.connect({}, onConnected, onError);
    }
}

function enterRoom(newRoomId) {
    roomId = newRoomId;
    topic = `/app/chat/${newRoomId}`;

    if (currentSubscription) {
        currentSubscription.unsubscribe();
    }
    currentSubscription = stompClient.subscribe(`/channel/${roomId}`, onMessageReceived);

    stompClient.send(`${topic}/addUser`,
        {},
        JSON.stringify({sender: username, type: 'JOIN'})
    );
}

function onConnected() {
    enterRoom(id);
    connectingElement.classList.add('hidden');
}

function onError(error) {
    connectingElement.textContent = 'Could not connect to WebSocket server. Please refresh this page to try again!';
    connectingElement.style.color = 'red';
}

function sendMessage(event) {
    let messageContent = messageInput.value.trim();
    if (messageContent.startsWith('/join ')) {
        let newRoomId = messageContent.substring('/join '.length);
        enterRoom(newRoomId);
        while (messageArea.firstChild) {
            messageArea.removeChild(messageArea.firstChild);
        }
    } else if (messageContent && stompClient) {
        let chatMessage = {
            sender: getAuthName(),
            senderId: getAuthId(),
            content: messageInput.value,
            type: 'CHAT'
        };

        let parts = roomId.split("_");
        let idSenderNotification = Number(parts[1]) === Number(getAuthId()) ? parts[0] : parts[1];

        stompNotification.send(`/app/notification/${idSenderNotification}/sendMessages`, {}, JSON.stringify(chatMessage));

        stompClient.send(`${topic}/sendMessage`, {}, JSON.stringify(chatMessage));
    }
    messageInput.value = '';
    event.preventDefault();
}

function onMessageReceived(payload) {
    let message = JSON.parse(payload.body);

    let messageElement = document.createElement('li');

    if (message.type === 'JOIN') {
        // messageElement.classList.add('event-message');
        // message.content = message.sender + ' joined!';
    } else if (message.type === 'LEAVE') {
        // messageElement.classList.add('event-message');
        // message.content = message.sender + ' left!';
    } else {
        messageElement.classList.add('chat-message');

        // avatar
        let avatarElement = document.createElement('i');

        // avatar text
        let avatarText = document.createTextNode(message.sender[0]);
        avatarElement.appendChild(avatarText);
        avatarElement.style['background-color'] = getAvatarColor(message.sender);

        // content
        let usernameElement = document.createElement('span');
        let usernameText = document.createTextNode(message.sender);
        usernameElement.appendChild(usernameText);

        let avatarUsernameDiv = document.createElement('div');
        avatarUsernameDiv.className = 'avatar-username-container';

        if (getAuthName() === message.sender) {
            avatarUsernameDiv.appendChild(usernameElement);
            avatarUsernameDiv.appendChild(avatarElement);
            messageElement.classList.remove('chat-message');
            messageElement.classList.add('chat-message-left');
        } else {
            avatarUsernameDiv.appendChild(avatarElement);
            avatarUsernameDiv.appendChild(usernameElement);
        }


        messageElement.appendChild(avatarUsernameDiv);

    }

    let textElement = document.createElement('p');
    let messageText = document.createTextNode(message.content);
    textElement.appendChild(messageText);

    messageElement.appendChild(textElement);

    messageArea.appendChild(messageElement);
    messageArea.scrollTop = messageArea.scrollHeight;
    handleSideBarService();
}

document.addEventListener("DOMContentLoaded", function () {
    connect();
});
messageForm.addEventListener('submit', sendMessage, true)

