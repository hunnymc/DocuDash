<script setup>
import { ref, onMounted } from 'vue';

import Stomp from 'stompjs';

// let url = "http://capstone23.sit.kmutt.ac.th/kw2";
// let url = 'http://cp23kw2.sit.kmutt.ac.th:10003';
let url = 'http://localhost:5002';

let stompClient = null;
let notificationCount = ref(0);

const message = ref('');

const reciveMessage = ref([]);

const privateMessage = ref('');

onMounted(() => {
    connect();
});

function connect() {
    var socket = new SockJS(url + '/our-websocket');
    stompClient = Stomp.over(socket);
    stompClient.connect({userId: '2'}, function (frame) {

        console.log('Connected: ' + frame);

        stompClient.subscribe('/topic/messages', function (message) {
            reciveMessage.value.push(JSON.parse(message.body).content);
            console.log(JSON.parse(message.body).content);
            // showMessage(JSON.parse(message.body).content);
        });

        stompClient.subscribe('/user/topic/private-messages', function (message) {
            reciveMessage.value.push(JSON.parse(message.body).content);
            console.log(JSON.parse(message.body).content);
            // showMessage(JSON.parse(message.body).content);
        });

        stompClient.subscribe('/topic/global-notifications', function () {
            notificationCount.value = notificationCount.value + 1;
        });

        stompClient.subscribe('/user/topic/private-notifications', function () {
            notificationCount.value = notificationCount.value + 1;
        });
    });
}

function sendMessage() {
    console.log("sending message");
    stompClient.send("/ws/message", {}, JSON.stringify({ 'messageContent': message.value }));
}

function sendPrivateMessage() {
    console.log("sending private message");
    stompClient.send("/ws/private-message", {}, JSON.stringify({ 'messageContent': privateMessage.value }));
}

function resetNotificationCount() {
    notificationCount.value = 0;
}

</script>

<template>
    <div style="margin-top: 50px">
        <div>
            <div>
                <form>
                    <div class="m-4">
                        <label>Message</label>
                        <input v-model="message" type="text" id="message" class="m-4"
                            placeholder="Enter your message here...">
                        <button @click="sendMessage()"
                            class="text-white bg-green-700 hover:bg-green-800 focus:outline-none focus:ring-4 focus:ring-green-300 font-medium rounded-full text-sm px-5 py-2.5 text-center me-2 mb-2 dark:bg-green-600 dark:hover:bg-green-700 dark:focus:ring-green-800"
                            type="button">Send</button>
                    </div>

                </form>
            </div>
        </div>
        <div class="m-4">
            <div class="col-md-12">
                <form class="form-inline">
                    <div class="form-group">
                        <label class="m-4" for="private-message">Private Message</label>
                        <input v-model="privateMessage" type="text" id="private-message" class="m-4"
                            placeholder="Enter your message here...">
                        <button @click="sendPrivateMessage()" id="send-private"
                            class="text-white bg-purple-700 hover:bg-purple-800 focus:outline-none focus:ring-4 focus:ring-purple-300 font-medium rounded-full text-sm px-5 py-2.5 text-center mb-2 dark:bg-purple-600 dark:hover:bg-purple-700 dark:focus:ring-purple-900"
                            type="button">Send
                            Private
                            Message</button>
                    </div>
                </form>
            </div>
        </div>
        <div class="row">
            <div class="col-md-12 m-4">
                <table id="message-history" class="table table-striped m-4">
                    <thead>
                        <tr>
                            <th>Messages
                                <span v-if="notificationCount == 0">
                                </span>
                                <span v-else @click="resetNotificationCount()" id="notifications" style="
                                    color: white;
                                    background-color: red;
                                    padding-left: 15px;
                                    padding-right: 15px;">
                                    {{ notificationCount }}
                                </span>
                            </th>
                        </tr>
                    </thead>
                    <tbody id="messages">
                        <ul>
                            <li v-for="msg in reciveMessage">{{ msg }}</li>
                        </ul>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</template>

<style scoped></style>