"use strict";

let usernamePage = document.querySelector("#username-page");
let chatPage = document.querySelector("#chat-page");
let messageForm = document.querySelector("#messageForm");
let messageInput = document.querySelector("#message");
let messageArea = document.querySelector("#messageArea");
let connectingElement = document.querySelector(".connecting");
let search = document.querySelector("#search");
let searchResults = document.querySelector("#searchResults");
let searchResultsContainer = document.querySelector(".search-results");
let itemUserContainer = document.querySelector(".item-user-container");
let actionHeader = document.querySelector(".item-user-container .action-header");

let stompClient = null;
let username = null;
let searchResultsItem = [];
const colors = [
    '#2196F3', '#32c787', '#00BCD4', '#ff5652',
    '#ffc107', '#ff85af', '#FF9800', '#39bbb0'
];


function connect(event) {
    username = "hello";

    if(username) {
        usernamePage.classList.add('hidden');
        chatPage.classList.remove('hidden');

        var socket = new SockJS('/ws');
        stompClient = Stomp.over(socket);

        stompClient.connect({}, onConnected, onError);
    }
    event.preventDefault();
}

function onConnected() {
    // Subscribe to the Public Topic
    stompClient.subscribe('/topic/public', onMessageReceived);

    // Tell your username to the server
    stompClient.send("/app/chat.addUser",
        {},
        JSON.stringify({sender: username, type: 'JOIN'})
    )

    connectingElement.classList.add('hidden');
}


function onError(error) {
    connectingElement.textContent = 'Could not connect to WebSocket server. Please refresh this page to try again!';
    connectingElement.style.color = 'red';
}


function sendMessage(event) {
    let messageContent = messageInput.value.trim();

    if(messageContent && stompClient) {
        let chatMessage = {
            sender: username,
            content: messageInput.value,
            type: 'CHAT'
        };

        stompClient.send("/app/chat.sendMessage", {}, JSON.stringify(chatMessage));
        messageInput.value = '';
    }
    event.preventDefault();
}


function onMessageReceived(payload) {
    let message = JSON.parse(payload.body);

    let messageElement = document.createElement('li');

    if(message.type === 'JOIN') {
        messageElement.classList.add('event-message');
        message.content = message.sender + ' joined!';
    } else if (message.type === 'LEAVE') {
        messageElement.classList.add('event-message');
        message.content = message.sender + ' left!';
    } else {
        messageElement.classList.add('chat-message');

        let avatarElement = document.createElement('i');
        let avatarText = document.createTextNode(message.sender[0]);
        avatarElement.appendChild(avatarText);
        avatarElement.style['background-color'] = getAvatarColor(message.sender);

        messageElement.appendChild(avatarElement);

        let usernameElement = document.createElement('span');
        let usernameText = document.createTextNode(message.sender);
        usernameElement.appendChild(usernameText);
        messageElement.appendChild(usernameElement);
    }

    let textElement = document.createElement('p');
    let messageText = document.createTextNode(message.content);
    textElement.appendChild(messageText);

    messageElement.appendChild(textElement);

    messageArea.appendChild(messageElement);
    messageArea.scrollTop = messageArea.scrollHeight;
}
function getAvatarColor(messageSender) {
    let hash = 0;
    for (let i = 0; i < messageSender.length; i++) {
        hash = 31 * hash + messageSender.charCodeAt(i);
    }
    let index = Math.abs(hash % colors.length);
    return colors[index];
}

const debounce = (func, delay) => {
    let timeoutId;
    return function (...args) {
        clearTimeout(timeoutId);
        timeoutId = setTimeout(() => {
            func.apply(this, args);
        }, delay);
    };
}

function getAccessToken() {
    const authDataString = localStorage.getItem("auth");
    const authData = JSON.parse(authDataString);
    const accessToken = authData.token;
    if (accessToken) {
        return accessToken;
    } else {
        console.error('access token does not exist');
        return null;
    }
}

const handleSearch = debounce((event) => {
    let params = event.target.value;
    fetch(`/api/search?s=${params.toString()}`, {
        method: 'POST',
        headers: {
            'Authorization': `bearer ${getAccessToken()}`
        }
    })
        .then(response => {
            if (response.ok) {
                return response.json();
            } else {
                throw new Error("Lỗi kết nối server");
            }
        })
        .then(data => {
            searchResults.innerHTML = '';
            if (data.length > 0) {
                searchResultsItem = data;
                searchResults.innerHTML = '';
                searchResultsContainer?.classList.remove("hidden");
                data.forEach(item => {
                    let avatarLetter = item.userName.charAt(0).toUpperCase();

                    // Tạo avatar
                    let avatarElement = document.createElement('div');
                    avatarElement.classList.add('avatar'); // Thêm class 'avatar' cho element

                    let avatarText = document.createTextNode(avatarLetter);
                    avatarElement.appendChild(avatarText);
                    avatarElement.style['background-color'] = getAvatarColor(avatarLetter);

                    // Tạo tên người dùng
                    let usernameElement = document.createElement('span');
                    let usernameText = document.createTextNode(truncateString(item.userName, 23));
                    usernameElement.appendChild(usernameText);

                    function truncateString(str, maxLength) {
                        if (str.length > maxLength) {
                            return str.slice(0, maxLength) + '...';
                        } else {
                            return str;
                        }
                    }


                    // Tạo một li và thêm avatar và tên người dùng vào đó
                    let listItem = document.createElement('li');
                    listItem.setAttribute('id', item.id);
                    listItem.appendChild(avatarElement);
                    listItem.appendChild(usernameElement);
                    listItem.addEventListener('mousedown', function(event) {
                        event.stopPropagation();  // Ngăn chặn sự kiện click lan truyền
                        console.log(`You clicked on item with ID: ${item.id}`);
                    });


                    searchResults.appendChild(listItem);
                });

            } else {
                let listItem = document.createElement('li');
                listItem.textContent = "Không tìm thấy kết quả";
                searchResults.appendChild(listItem);
            }
        })
        .catch(error => {
            console.error('Đã xảy ra lỗi: ', error);
        });
}, 800);

search.addEventListener("blur", function () {
    searchResults.classList.add("hidden");
});
search.addEventListener("focus", function () {
    searchResults.classList.remove("hidden");
});

fetch(`/api/get-user`, {
    method: 'GET',
    headers: {
        'Authorization': `bearer ${getAccessToken()}`
    }
})
    .then(response => {
        if (response.ok) {
            return response.json();
        } else {
            throw new Error("Lỗi kết nối server");
        }
    })
    .then(data => {
        if (data.length > 0) {
            actionHeader.innerHTML = '';
            data.forEach(item => {
                let avatarElement = document.createElement('div');
                avatarElement.classList.add('avatar');

                let avatarText = document.createTextNode(item.userName.charAt(0).toUpperCase());
                avatarElement.appendChild(avatarText);
                avatarElement.style['background-color'] = getAvatarColor(item.userName);

                let usernameElement = document.createElement('span');
                let usernameText = document.createTextNode(truncateString(item.userName, 23));
                usernameElement.appendChild(usernameText);

                function truncateString(str, maxLength) {
                    if (str.length > maxLength) {
                        return str.slice(0, maxLength) + '...';
                    } else {
                        return str;
                    }
                }

                let listItem = document.createElement('li');
                listItem.setAttribute('id', item.id);
                listItem.appendChild(avatarElement);
                listItem.appendChild(usernameElement);
                listItem.addEventListener('click', function() {
                    console.log(`You clicked on item with ID: ${item.id}`);
                });

                actionHeader.appendChild(listItem);
            });

        } else {
        }
    })
    .catch(error => {
        console.error('Đã xảy ra lỗi: ', error);
    });


function handleClickItemUser(id){
    fetch(`/api/get-user`, {
        method: 'GET',
        headers: {
            'Authorization': `bearer ${getAccessToken()}`
        }
    })
        .then(response => {
            if (response.ok) {
                return response.json();
            } else {
                throw new Error("Lỗi kết nối server");
            }
        })
        .then(data => {

        })
        .catch(error => {
            console.error('Đã xảy ra lỗi: ', error);
        });
}

document.addEventListener("DOMContentLoaded", function() {
    connect(event);
});
messageForm.addEventListener('submit', sendMessage, true)