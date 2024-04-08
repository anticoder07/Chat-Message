function handleChatMessageAreaService() {
    let url = new URL(window.location.href);
    let room = url.searchParams.get('id');
    fetch(`/api/get-chat?i=${room.toString()}`, {
        method: 'GET',
        headers: {
            'Authorization': `bearer ${getAuthAccessToken()}`
        }
    })
        .then(response => {
            if (!response.ok) {
                throw new Error("Lỗi kết nối server");
            }
            return response.json();
        })
        .then(data => {
            let messageArea = document.getElementById('messageArea'); // Giả sử có một phần tử có id là 'messageArea'

            data.forEach(message => {
                let messageElement = document.createElement('li');
                messageElement.classList.add('chat-message');

                let avatarElement = document.createElement('i');
                let avatarText = document.createTextNode(message.sendName[0]);
                avatarElement.appendChild(avatarText);
                avatarElement.style['background-color'] = getAvatarColor(message.sendName);

                let usernameElement = document.createElement('span');
                let usernameText = document.createTextNode(message.sendName);
                usernameElement.appendChild(usernameText);

                let avatarUsernameDiv = document.createElement('div');
                avatarUsernameDiv.className = 'avatar-username-container';

                if (getAuthId() === message.sendId) {
                    avatarUsernameDiv.appendChild(usernameElement);
                    avatarUsernameDiv.appendChild(avatarElement);
                    messageElement.classList.remove('chat-message');
                    messageElement.classList.add('chat-message-left');
                } else {
                    avatarUsernameDiv.appendChild(avatarElement);
                    avatarUsernameDiv.appendChild(usernameElement);
                }

                messageElement.appendChild(avatarUsernameDiv);

                let textElement = document.createElement('p');
                let messageText = document.createTextNode(message.message);
                textElement.appendChild(messageText);

                messageElement.appendChild(textElement);

                messageArea.appendChild(messageElement);
                messageArea.scrollTop = messageArea.scrollHeight;
            });
        })
        .catch(error => {
            console.error('Đã xảy ra lỗi: ', error);
        });

}

handleChatMessageAreaService();
