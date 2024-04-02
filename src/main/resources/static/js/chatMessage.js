"use strict";

let usernamePage = document.querySelector("#username-page");
let chatPage = document.querySelector("#chat-page");
let usernameForm = document.querySelector("#usernameForm");
let messageForm = document.querySelector("#messageForm");
let messageInput = document.querySelector("#message");
let messageArea = document.querySelector("#messageArea");
let connectingElement = document.querySelector(".connecting");
let actionHeader = document.querySelector(".action-header");

let stompClient = null;
let username = null;

function loadChatMessagePage() {
    fetch('/api/auth/log-in', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(data)
    })
        .then(response => {
            if (response.ok) {
                return response.json();
            } else {
            }
        })
        .catch(error => {
            console.error('Đã xảy ra lỗi:', error);
        });
}

function connect(event) {
    username = document.querySelector("#name").value.trim();
    console.log(username);
    if (username) {
        usernamePage?.classList.add("hidden");
        chatPage?.classList.remove("hidden");

        let socket = new SockJS("/ws"); // khởi tạo đối tượng SockJS với endpoint là "/ws"
        stompClient = Stomp.over(socket); //  thiết lập một kết nối Stomp trên một kết nối WebSocket đã có sẵn
    }
    event.preventDefault();
}

function onConnected() {
    stompClient.subscribe("/topic/public", onMessageReceived);

    stompClient.send(
        "/app/chat.addUser",
        {},
        JSON.stringify({sender: username, type: "JOIN"})
    );

    connectingElement.classList.add("hidden");
}

function onError(error) {
    connectingElement.textContent =
        "Could not connect to WebSocket server. Please refresh this page to try again!";
    connectingElement.style.color = "red";
}

function sendMessage(event) {
    let messageContent = messageInput.value.trim();

    if (messageContent && stompClient) {
        let chatMessage = {
            sender: username,
            connect: u,
        };
    }
    event.preventDefault();
}



