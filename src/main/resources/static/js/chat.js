let stomClient = null;
let username = document.getElementById("username").textContent;
const inboxChat = document.querySelector(".inbox_chat")
const messageHistory = document.querySelector('.msg_history')
let usernameReceive = null;
const sendMessage = document.querySelector('.input_msg_write')
let sendConversation = null;
const message_input = document.querySelector('.write_msg')
function onConnect() {
    stomClient.subscribe(
        `/topic/public/listUserMessage/${username}`, receivedListChat)
    stomClient.subscribe(
        `/topic/public/conversation/2`, receivedMessage)
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
    const response = await fetch(`http://localhost:8080/message/user/${username}/conversation/${conversationId}`);
    const messages = await response.json();
    console.log(messages);
    let  html = ''
    for(let i = 0; i < messages.length; i++) {
        if(messages[i].messageType === 'SEND') {
            html += `<div class="outgoing_msg">
                        <div class="sent_msg">
                            <p>${messages[i].content}</p>
                            <span class="time_date"> 11:01 AM    |    June 9</span> </div>
                    </div>`
        } else {
            html += `<div class="incoming_msg">
                        <div class="incoming_msg_img"> <img src="https://ptetutorials.com/images/user-profile.png" alt="sunil"> </div>
                        <div class="received_msg">
                            <div class="received_withd_msg">
                                <p>${messages[i].content}</p>
                                <span class="time_date"> 11:01 AM    |    June 9</span></div>
                        </div>
                    </div>`
        }
    }
    messageHistory.innerHTML = html;
}

function onSendMessage() {
    stomClient.send(
        `/app/chat.sendMessage/${username}/{conversationId}`,
        {},
        JSON.stringify({
            'content':message_input.value,
            'userSenderId': username,
            'senderToConversationId': sendConversation
        })
    )
}

function receivedListChat(payload) {
    console.log(payload)
    const userResponse = JSON.parse(payload.body);
    console.log(userResponse)
    const listChat = userResponse.conversationResponses;
    let html = ``;
    for (let i = 0; i < listChat.length; i++) {
        let sizeMessage = listChat[i].chatMessageResponses.length;
        let message =  listChat[i].chatMessageResponses[sizeMessage - 1] ;
        html += `<div class="chat_list" id="${listChat[i].id}" style="cursor: pointer" onclick="getListMessageOfConverstation(${listChat[i].id})">
                        <div class="chat_people">
                            <div class="chat_img"> <img src="https://ptetutorials.com/images/user-profile.png" alt="sunil"> </div>
                            <div class="chat_ib">
                                <h5>${listChat[i].conversationName}<span class="chat_date">Dec 25</span></h5>
                                <p>${sizeMessage > 0 ? message.messageType === 'SEND' ? 'Bạn: ' + message.content : message.userSenderFullName + ': ' + message.content : ''}</p>
                            </div>
                        </div>
                    </div>`
    }
    console.log(html)
    inboxChat.innerHTML = html;
}

function receivedMessage(payload) {
    const message = JSON.parse(payload.body)
    let html = ''
    if(message.messageType === 'SEND') {
        html = `<div class="outgoing_msg">
                        <div class="sent_msg">
                            <p>${message.content}</p>
                            <span class="time_date"> 11:01 AM    |    June 9</span> </div>
                    </div>`
    } else {
        html= `<div class="incoming_msg">
                        <div class="incoming_msg_img"> <img src="https://ptetutorials.com/images/user-profile.png" alt="sunil"> </div>
                        <div class="received_msg">
                            <div class="received_withd_msg">
                                <p>${message.content}</p>
                                <span class="time_date"> 11:01 AM    |    June 9</span></div>
                        </div>
                    </div>`
    }
    inboxChat.appendChild(html)
}