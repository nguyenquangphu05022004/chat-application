let stomClient = null;
let username = document.getElementById("username").textContent;
const inboxChat = document.querySelector(".inbox_chat")
const messageHistory = document.querySelector('.msg_history')
const form_sendMessage = document.querySelector('#form-message')
let sendConversationId = null;
const message_input = document.querySelector('.write_msg')
const search_username_button = document.getElementById('search-username')
const input_username = document.getElementById("search-input-username")

function onConnect() {
    stomClient.subscribe(
        `/topic/public/listUserMessage/${username}`, receivedListChat)
    stomClient.send(
        `/app/chat.getListUserConversation/${username}`,
        {},
        ''
    )
}

function onError() {

}

function connectWebSocket() {
    let socket = new SockJS('/ws');
    stomClient = Stomp.over(socket);
    stomClient.connect({}, onConnect, onError)
}

async function getListMessageOfConverstation(conversationId) {
    alert(conversationId)
    sendConversationId = conversationId;
    console.log(sendConversationId)
    if (sendConversationId.includes('null_')) {
        fetch(`http://localhost:8080/conversations?username=${username}&username=${sendConversationId.split('_')[1]}`)
            .then(res => res.json())
            .then((conversation) => {
                stomClient.send(
                    `/app/chat.getListUserConversation/${username}`,
                    {},
                    ''
                )
                stomClient.send(
                    `/app/chat.getListUserConversation/${sendConversationId.split('_')[1]}`,
                    {},
                    ''
                )
                sendConversationId = conversation.id;
            })
            .catch(err => {
                console.log(err)
            })

    }
    const response = await fetch(`http://localhost:8080/message/user/${username}/conversation/${sendConversationId}`);
    const messages = await response.json();
    console.log(messages);
    let html = ''
    for (let i = 0; i < messages.length; i++) {
        if (messages[i].messageType === 'SEND') {
            html += `<div class="outgoing_msg">
                        <div class="sent_msg">
                             <p>${messages[i].userSenderFullName}</p>
                            <p>${messages[i].content}</p>
                    </div>`
        } else {
            html += `<div class="incoming_msg">
                        <div class="incoming_msg_img"> <img src="https://ptetutorials.com/images/user-profile.png" alt="sunil"> </div>
                        <div class="received_msg">
                            <div class="received_withd_msg">
                                <p>${messages[i].content}</p>
                        </div>
                    </div>`
        }
    }
    console.log(html)
    messageHistory.innerHTML = `${html}`;

}

function onSendMessage(event) {
    event.preventDefault();
    stomClient.send(
        `/app/chat.sendMessage/${username}/${sendConversationId}`,
        {},
        JSON.stringify({
            'content': message_input.value
        })
    )
}

function receivedListChat(payload) {
    const userResponse = JSON.parse(payload.body);
    const listChat = userResponse.conversationResponses;
    let html = ``;
    for (let i = 0; i < listChat.length; i++) {
        stomClient.subscribe(`/topic/public/conversation/${listChat[i].id}`, receivedMessage)
        let sizeMessage = listChat[i].chatMessageResponses.length;
        let message = listChat[i].chatMessageResponses[sizeMessage - 1];
        console.log(listChat[i].id)
        console.log(message)
        html += `<div class="chat_list" id='people_chat_${listChat[i].id}' style="cursor: pointer" onclick="getListMessageOfConverstation('${listChat[i].id}')">
                        <div class="chat_people">
                            <div class="chat_img"> <img src="https://ptetutorials.com/images/user-profile.png" alt="sunil"> </div>
                            <div class="chat_ib">
                                <h5>${listChat[i].conversationName}<span class="chat_date">Dec 25</span></h5>
                                <p id="inbox_chat_${listChat[i].id}">${sizeMessage > 0 ? message.messageType === 'SEND' ? 'Bạn: ' + message.content : message.userSenderFullName + ': ' + message.content : ''}</p>
                            </div>
                        </div>
                    </div>`
    }
    console.log(html)
    inboxChat.innerHTML = `${html}`;
}

function receivedMessage(payload) {
    const message = JSON.parse(payload.body)
    console.log("compare ", message.conversationId, sendConversationId)
    console.log(message.conversationId === sendConversationId)
    const getChatInbox = document.getElementById(`inbox_chat_${message.conversationId}`)

    if (message.conversationId === (sendConversationId + '')) {
        let html = ''
        if (message.usernameSender === username) {
            html = `<div class="outgoing_msg">
                        <div class="sent_msg">
                            <p>${message.content}</p>
                    </div>`
        } else {
            html = `<div class="incoming_msg">
                        <div class="incoming_msg_img"> <img src="https://ptetutorials.com/images/user-profile.png" alt="sunil"> </div>
                        <div class="received_msg">
                            <div class="received_withd_msg">
                                <p>${message.userSenderFullName}</p>
                                <p>${message.content}</p>
                        </div>
                    </div>`
        }
        messageHistory.insertAdjacentHTML('beforeend', html)
        messageHistory.scrollTop = messageHistory.scrollHeight;
        getChatInbox.innerHTML = `<p>${message.usernameSender === username ? 'Bạn: ' + message.content : message.userSenderFullName + ': ' + message.content}</p>`
    } else {
        getChatInbox.innerHTML = `<p style="font-weight: bold">${message.usernameSender === username ? 'Bạn: ' + message.content : message.userSenderFullName + ': ' + message.content}</p>`
    }
}

function searchUsername(e) {
    e.preventDefault();
    const usernameSearch = input_username.value;
    const response = fetch(`http://localhost:8080/users?username=${usernameSearch}`);
    response.then(res => res.json())
        .then(userResponse => {
            inboxChat.innerHTML = `<div class="chat_list" id="${'null_' + userResponse.username}" style="cursor: pointer" onclick="getListMessageOfConverstation('${'null_' + userResponse.username}')">
                        <div class="chat_people">
                            <div class="chat_img"> <img src="https://ptetutorials.com/images/user-profile.png" alt="sunil"> </div>
                            <div class="chat_ib">
                                <h5>${userResponse.fullName}<span class="chat_date">Dec 25</span></h5>
                            </div>
                        </div>
                    </div>`
        })


}

form_sendMessage.addEventListener('submit', onSendMessage, true)
search_username_button.addEventListener('submit', searchUsername, true)