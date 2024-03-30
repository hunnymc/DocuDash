<script setup>
import {onBeforeMount, onMounted, onUpdated, ref, watch, watchEffect} from "vue";
import {initFlowbite} from 'flowbite'
import {useRoute, useRouter} from "vue-router";
import {useDocumentListStore} from '/src/stores/listOfDocumentStore.js';
import Cookies from "js-cookie";
import Stomp from 'stompjs';
import axios from 'axios';

const documentListStore = useDocumentListStore();

const router = useRouter();
const route = useRoute();

let mainURL = import.meta.env.VITE_API_URL;
let wsURL = import.meta.env.VITE_WS_URL;

// let mainURL = "http://localhost:5002";
// let mainURL = "http://cp23kw2.sit.kmutt.ac.th:10003";
// let mainURL = "https://capstone23.sit.kmutt.ac.th/kw2";

// let wsURL = "https://capstone23.sit.kmutt.ac.th/kw2-socket";
// let wsURL = "http://cp23kw2.sit.kmutt.ac.th:10003";
// let wsURL = "http://localhost:5002";
// let wsURL = "https://capstone23.sit.kmutt.ac.th/kw2";

function callFunctionInComponentB() {
  documentListStore.setCallFunctionInComponentB(true);
}

let showMenu = ref(false);

let notificationMessage = ref(null);

// {
//   "id": 2,
//   "message": "Document shared with you",
//   "dateSent": "2024-02-17T15:40:52.684324400Z",
//   "readStatus": "UNREAD",
//   "sourceUsername": "นภา ฟ้าสวย",
//   "docTitle": "test2"
// },
// {
//   "id": 1,
//   "message": "Document shared with you",
//   "dateSent": "2024-02-17T15:40:52.684324400Z",
//   "readStatus": "UNREAD",
//   "sourceUsername": "นภา ฟ้าสวย",
//   "docTitle": "test1"
// }

const toggleNav = () => (showMenu.value = !showMenu.value);

let userID = ref(Cookies.get("userId"));

const user = ref({
  // username: "ธนพนธ์ สุขเกษม",
  // fullName: "ธนพนธ์ สุขเกษม",
  // role: "ADMIN",
  // email: "thnanaphon.mc34@gmail.com",
  // branch: "CEO",
  // phone: "098-765-4321",
});

const getUserInfo = async () => {
  await axios.post(
      mainURL + '/api/auth/user-info'
      // 'http://cp23kw2.sit.kmutt.ac.th:35000/api/auth/user-info'
      , {email: Cookies.get("email")}
      , {headers: {"Authorization": "Bearer " + Cookies.get("accessToken"),}})
      .then((response) => {
        if (response.status === 200) {
          user.value = {...response.data};
          Cookies.set("userId", response.data.id);
          localStorage.setItem("fullName", response.data.fullName);
        }
      });
};

const getNewNotification = async () => {
  await axios.get(
      mainURL + '/api/n/user/' + Cookies.get("userId")
      , {headers: {"Authorization": "Bearer " + Cookies.get("accessToken"),}})
      .then((response) => {
        if (response.status === 200) {
          notificationMessage.value = response.data;

          // change dateSent from integer to string
          for (let i = 0; i < notificationMessage.value.length; i++) {
            let dateInMilliseconds = notificationMessage.value[i].dateSent * 1000;
            notificationMessage.value[i].dateSent = new Date(dateInMilliseconds).toString();
          }
        }
      });
};

function timeSince(timestamp) {

  let date = new Date(timestamp);
  let seconds = Math.floor((new Date() - date) / 1000);

  var interval = seconds / 31536000;

  if (interval > 1) {
    return Math.floor(interval) + " ปีที่แล้ว";
  }
  interval = seconds / 2592000;
  if (interval > 1) {
    return Math.floor(interval) + " เดือนที่แล้ว";
  }
  interval = seconds / 86400;
  if (interval > 1) {
    return Math.floor(interval) + " วันที่แล้ว";
  }
  interval = seconds / 3600;
  if (interval > 1) {
    return Math.floor(interval) + " ชั่วโมงที่แล้ว";
  }
  interval = seconds / 60;
  if (interval > 1) {
    return Math.floor(interval) + " นาทีที่แล้ว";
  }
  return "เมื่อไม่นานมานี้";
}

// ------------------ Notification --------------------------

let stompClient = null;
let notificationCount = ref(0);

function connect() {

  let socket = new SockJS(wsURL + '/api/kw2-websocket?userId=' + userID.value);
  // let socket = new SockJS(wsURL + '/api/kw2-websocket');

  stompClient = Stomp.over(socket);

  stompClient.connect({userId: userID}, function (frame) {

    console.log('Connected: ' + frame);

    stompClient.subscribe('/topic/messages', function (message) {
      callFunctionInComponentB();
      // reciveMessage.value.push(JSON.parse(message.body).content);
      // console.log(JSON.parse(message.body).content);
      // showMessage(JSON.parse(message.body).content);
    });

    stompClient.subscribe('/user/topic/private-messages', function (message) {
      callFunctionInComponentB();
      // reciveMessage.value.push(JSON.parse(message.body).content);
      // console.log(JSON.parse(message.body).content);
      // showMessage(JSON.parse(message.body).content);
    });

    stompClient.subscribe('/topic/global-notifications', function () {
      notificationCount.value = notificationCount.value + 1;
      callFunctionInComponentB();
    });

    stompClient.subscribe('/user/topic/private-notifications', function (message) {
      notificationMessage.value = JSON.parse(message.body);
      callFunctionInComponentB();
      notificationCount.value = notificationCount.value + 1;
    });
  });
}

async function clickNotification(documentId) {
  notificationCount.value = 0;
  await documentListStore.getdocumentFilenameAndUserIdFromAxios(documentId);
  await getNewNotification();
  await router.push("/kw2/approval/detail/user/" + documentId);
}

function clickToAllDoc() {
  router.push("/list");
}

// ---------------------------------------------------------------------------------

function logout() {
  Cookies.remove("accessToken");
  Cookies.remove("refreshToken");
  Cookies.remove("role");
  Cookies.remove("email");
  Cookies.remove("userId");
  localStorage.removeItem("fullName");
  router.push("/kw2/login");
}

// ---------------------------------------------------------------------------------

onBeforeMount(() => {
  initFlowbite();
});

onMounted(async () => {
  initFlowbite();
  console.log("Index page is ready");
  await getUserInfo();
  // connect();
  await getNewNotification();
});

onUpdated(() => {
  // getNewNotification();
});

// watch(() => route.value, getNewNotification(), {immediate: true});
watchEffect(() => {
  route.value;
  getNewNotification();
});


</script>

<template>
  <div class="bg-blue-900">
    <nav class="sticky-top md:flex md:justify-between md:items-center">

      <div class="flex items-center justify-between">
        <a class="text-xl font-bold text-gray-100 md:text-2xl hover:text-indigo-400 cursor-pointer" href="/kw2/">
          <img alt="image description"
               class="h-32 md:h-24 max-w-xs object-contain object-left contrast-125 brightness-150" src="../../../assets/DD1.png"/>
        </a>
        <div class="flex md:hidden" @click="toggleNav">
          <button class="text-gray-100 hover:text-gray-400 focus:outline-none focus:text-gray-400" type="button">
            <svg class="w-6 h-6 fill-current" viewBox="0 0 24 24">
              <path d="M4 5h16a1 1 0 0 1 0 2H4a1 1 0 1 1 0-2zm0 6h16a1 1 0 0 1 0 2H4a1 1 0 0 1 0-2zm0 6h16a1 1 0 0 1 0 2H4a1 1 0 0 1 0-2z"
                    fill-rule="evenodd">
              </path>
            </svg>
          </button>
        </div>
      </div>

      <div class="text-gray-100  text-sm font-extrabold font-sans hidden md:block mr-auto">
        <h2 class="text-gray-100 text-base md:text-sm">
          <span
              class="text-transparent bg-clip-text bg-gradient-to-r to-emerald-600 from-sky-400 uppercase">E-Approval</span>
          <a class="text-transparent bg-clip-text font-bold bg-gradient-to-r from-indigo-200 via-red-200 to-yellow-100 hover:underline"
             href="#">ระบบอนุมัติเอกสารออนไลน์</a>
        </h2>
      </div>

      <!-- Mobile Menu open แฮม-->
      <ul :class="showMenu ? 'flex justify-start' : 'hidden'"
          class="flex-col mt-8 space-y-4 md:flex md:space-y-0 md:flex-row md:items-center md:space-x-10 md:mt-0 mx-8 ">

        <!-- **** หน้าแรก **** -->


        <!-- <li>
          <a href="/list" class="text-gray-100 hover:text-indigo-400 cursor-pointer">
            จำนวนแจ้งเตือน: {{ notificationCount }}
          </a>
        </li> -->


        <li>
          <a class="text-gray-100 hover:text-indigo-400 cursor-pointer" href="/kw2/approval">
            หน้าแรก
          </a>
        </li>
        <li class="text-gray-100 hover:text-indigo-400 cursor-pointer" data-tooltip-trigger="click">

          <a v-if="!$route.path.startsWith('/view/')">
            <button id="dropdownNotificationButton" class="relative inline-flex items-center text-sm font-medium text-center text-white hover:text-gray-500 focus:outline-none dark:hover:text-white dark:text-gray-400"
                    data-dropdown-toggle="dropdownNotification"
                    type="button">
              <svg aria-hidden="true" class="w-5 h-5" fill="currentColor" viewBox="0 0 14 20"
                   xmlns="http://www.w3.org/2000/svg">
                <path
                    d="M12.133 10.632v-1.8A5.406 5.406 0 0 0 7.979 3.57.946.946 0 0 0 8 3.464V1.1a1 1 0 0 0-2 0v2.364a.946.946 0 0 0 .021.106 5.406 5.406 0 0 0-4.154 5.262v1.8C1.867 13.018 0 13.614 0 14.807 0 15.4 0 16 .538 16h12.924C14 16 14 15.4 14 14.807c0-1.193-1.867-1.789-1.867-4.175ZM3.823 17a3.453 3.453 0 0 0 6.354 0H3.823Z"/>
              </svg>

              <!-- จุดแดง -->
              <div v-if="notificationCount > 0"
                   class="absolute block w-3 h-3 bg-red-500 border-2 border-white rounded-full -top-0.5 start-2.5 dark:border-gray-900">
              </div>

            </button>
          </a>

          <!-- Dropdown menu -->
          <div id="dropdownNotification"
               aria-labelledby="dropdownNotificationButton"
               class="z-20 hidden w-full max-w-sm bg-white divide-y divide-gray-100 rounded-lg shadow dark:bg-gray-800 dark:divide-gray-700">
            <div
                class="block px-4 py-2 font-medium text-center text-gray-700 rounded-t-lg bg-gray-50 dark:bg-gray-800 dark:text-white">
              การแจ้งเตือน
            </div>

            <div class="divide-y divide-gray-100 dark:divide-gray-700">

              <a v-if="!notificationMessage || notificationMessage.length === 0">
                <div class="flex px-4 py-3 hover:bg-gray-100 dark:hover:bg-gray-700">
                  <div class="w-full ps-3">
                    <div class="text-gray-500 text-sm mb-1.5 dark:text-gray-400">ไม่มีการแจ้งเตือนใหม่</div>
                  </div>
                </div>
              </a>

              <a v-for="noti in notificationMessage" v-else
                 class="flex px-4 py-3 hover:bg-gray-100 dark:hover:bg-gray-700">
                <a @click="clickNotification(noti.documentId)">
                  <!-- <div class="flex-shrink-0">
                  <img class="rounded-full w-11 h-11" src="/docs/images/people/profile-picture-5.jpg" alt="Robert image">
                  <div
                    class="absolute flex items-center justify-center w-5 h-5 ms-6 -mt-5 bg-purple-500 border border-white rounded-full dark:border-gray-800">
                    <svg class="w-2 h-2 text-white" aria-hidden="true" xmlns="http://www.w3.org/2000/svg"
                      fill="currentColor" viewBox="0 0 20 14">
                      <path
                        d="M11 0H2a2 2 0 0 0-2 2v10a2 2 0 0 0 2 2h9a2 2 0 0 0 2-2V2a2 2 0 0 0-2-2Zm8.585 1.189a.994.994 0 0 0-.9-.138l-2.965.983a1 1 0 0 0-.685.949v8a1 1 0 0 0 .675.946l2.965 1.02a1.013 1.013 0 0 0 1.032-.242A1 1 0 0 0 20 12V2a1 1 0 0 0-.415-.811Z" />
                    </svg>
                  </div>
                </div> -->
                  <div class="w-full ps-3">
                    <div class="text-gray-500 text-sm mb-1.5 dark:text-gray-400"><span
                        class="font-semibold text-gray-900 dark:text-white">{{ noti.sourceUsername }}</span>
                      ได้ส่งเอกสารถึงท่าน
                      : <span class="font-semibold text-gray-900">{{ noti.docTitle }}</span></div>
                    <!-- <div class="text-xs text-blue-600 dark:text-blue-500">{{ timeSince(noti.dateSent) }}</div> -->
                    <div class="text-xs text-blue-600 dark:text-blue-500">{{ timeSince(noti.dateSent) }}</div>

                  </div>

                </a>
              </a>

            </div>

            <!-- ---------------------------------------------------------------------------------------------------------------- -->

            <a class="block py-2 text-sm font-medium text-center text-gray-900 rounded-b-lg bg-gray-50 hover:bg-gray-100 dark:bg-gray-800 dark:hover:bg-gray-700 dark:text-white"
               @click="clickToAllDoc()">
              <div class="inline-flex items-center ">
                <svg aria-hidden="true" class="w-4 h-4 me-2 text-gray-500 dark:text-gray-400"
                     fill="currentColor" viewBox="0 0 20 14" xmlns="http://www.w3.org/2000/svg">
                  <path
                      d="M10 0C4.612 0 0 5.336 0 7c0 1.742 3.546 7 10 7 6.454 0 10-5.258 10-7 0-1.664-4.612-7-10-7Zm0 10a3 3 0 1 1 0-6 3 3 0 0 1 0 6Z"/>
                </svg>
                ดูเอกสารใหม่ทั้งหมด
              </div>
            </a>
          </div>
        </li>

        <!-- ---------------------------------------------------------------------------------------------------------------- -->

        <!--    data-dropdown-toggle="dropdownAvatarName"    -->
        <li>
          <button id="dropdownAvatarNameButton" class="flex items-center text-sm md:text-base font-medium text-gray-900 rounded-full hover:text-blue-600 md:me-0"
                  data-dropdown-toggle="dropdownAvatarName">
            <span class="sr-only">Open user menu</span>
            <div class="relative w-10 h-10 overflow-hidden bg-gray-100 rounded-full">
              <svg class="absolute w-12 h-12 text-gray-400 -left-1" fill="currentColor" viewBox="0 0 20 20"
                   xmlns="http://www.w3.org/2000/svg">
                <path clip-rule="evenodd" d="M10 9a3 3 0 100-6 3 3 0 000 6zm-7 9a7 7 0 1114 0H3z" fill-rule="evenodd">
                </path>
              </svg>
            </div>
            <!-- ชื่อเต็ม -->
            <a class="ml-4 font-medium text-sm text-slate-300 hover:text-white">{{ user.fullName }}</a>
            <svg aria-hidden="true" class="w-2.5 h-2.5 ms-3 text-slate-300" fill="none"
                 viewBox="0 0 10 6"
                 xmlns="http://www.w3.org/2000/svg">
              <path d="m1 1 4 4 4-4" stroke="currentColor" stroke-linecap="round" stroke-linejoin="round"
                    stroke-width="2"/>
            </svg>
          </button>

          <!-- <div class="relative w-10 h-10 overflow-hidden bg-gray-100 rounded-full dark:bg-gray-600">
    <svg class="absolute w-12 h-12 text-gray-400 -left-1" fill="currentColor" viewBox="0 0 20 20" xmlns="http://www.w3.org/2000/svg"><path fill-rule="evenodd" d="M10 9a3 3 0 100-6 3 3 0 000 6zm-7 9a7 7 0 1114 0H3z" clip-rule="evenodd"></path></svg>
</div> -->

          <!-- Dropdown menu -->
          <div id="dropdownAvatarName"
               class="z-10 hidden bg-white divide-y divide-gray-100 rounded-lg shadow w-44 md:w-auto dark:bg-gray-700 dark:divide-gray-600">
            <div class="px-4 py-3 text-sm text-gray-900 dark:text-white">
              <!-- ตำแหน่ง -->
              <div class="font-medium">ตำแหน่ง: {{ user.branch }}</div>
              <!-- อีเมล -->
              <div class="truncate">{{ user.email }}</div>
            </div>

            <ul aria-labelledby="dropdownAvatarNameButton" class="py-2 text-sm text-gray-700 dark:text-gray-200">
              <li>
                <a class="block px-4 py-2 hover:bg-gray-100 dark:hover:bg-gray-600 dark:hover:text-white" href="#"
                   @click="router.push('/kw2/document/user')">
                  ดูโปรไฟล์
                </a>
              </li>
            </ul>

            <ul aria-labelledby="dropdownAvatarNameButton"
                class="py-2 text-sm text-gray-700 dark:text-gray-200 hover:bg-blue-400 bg-blue-200">
              <li>
                <a class="block px-4 py-2 hover:bg-blue-400 " href="#"
                   @click="router.push('/user')">
                  กลับสู่เมนูหลัก
                </a>
              </li>
            </ul>

            <div class="py-2">
              <a class="block px-4 py-2 text-sm text-gray-700 hover:bg-gray-100 dark:hover:bg-gray-600 dark:text-gray-200 dark:hover:text-white" href="#"
                 @click="logout()">
                ออกจากระบบ
              </a>
            </div>
          </div>
        </li>


      </ul>
    </nav>
  </div>

</template>