'use strict';

const usernamePage = document.querySelector('#username-page')
const chatPage = document.querySelector('#chat-page')
const usernameForm = document.querySelector('#usernameForm')
const messageForm = document.querySelector('#messageForm')
const messageInput = document.querySelector('#message')
const messageArea = document.querySelector('#messageArea')
const connectingElement = document.querySelector('.connecting')

var stomClient = null;
var username = null;
var colors = [
    '#2196F3', '#32c787', '#00BCD4', '#ff5652',
    '#ffc107', '#ff85af', '#FF9800', '#39bbb0'
];

function onError() {
    connectingElement.textContent = 'Could not connect to WebSocket server. Please refresh this page to try again!';
    connectingElement.style.color = 'red';
}

const connect = (event) => {
    event.preventDefault();
    username = document.querySelector("#name").value.trim();
    if(username) {
        usernamePage.classList.add("hidden")
        chatPage.classList.remove('hidden')
        let socket = new SockJS('/ws');
        stomClient = Stomp.over(socket);
        stomClient.connect({}, onConnect, onError)
    }
}



function onConnect(){
    stomClient.subscribe('/topic/public', onMessageReceived);
    stomClient.send(
        "/app/chat.addUser",
        {},
        JSON.stringify({"sender": username, "messageType": "JOIN"})
    )
    connectingElement.classList.add('hidden')

}
function onMessageReceived(payload) {
    const message = JSON.parse(payload.body)
    const showMessage = document.createElement('li')

    if(message.messageType === 'JOIN') {
        showMessage.innerHTML = `<p style="text-align: center">User: ${message.sender} JOINED</p>`
    } else if(message.messageType === 'CHAT') {
        showMessage.innerHTML = `<p><span style='color: ${getAvatar(message.sender)}; border-radius: 50%'>${message.sender}</span>: ${message.content}</p>`
    } else {
        showMessage.innerHTML = `<p style="text-align: center">User: ${message.sender} LEAVED</p>`
    }
    messageArea.appendChild(showMessage)
}
function sendMessage(event) {
    event.preventDefault();
    stomClient.send(
        '/app/chat.sendMessage/1',
        {},
        JSON.stringify({
            "sender": username,
            "content": messageInput.value,
            "messageType": 'CHAT'
        })
    )
    messageInput.value = ""
}
function getAvatar(sender) {
    let hash = 0;
    for(let i = 0; i < sender.length; i++) {
        hash += sender.charCodeAt(i);
    }
    return colors[Math.abs(hash % sender.length)]
}
usernameForm.addEventListener('submit', connect, true)
messageForm.addEventListener('submit', sendMessage, true)

