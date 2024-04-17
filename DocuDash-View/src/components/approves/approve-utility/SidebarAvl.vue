<script setup>
import {RouterLink} from 'vue-router';
import {onBeforeMount, onMounted, ref} from 'vue';
import Cookies from 'js-cookie';
import axios from "axios";

let userRoleNumber = ref(0);
let mainURL = import.meta.env.VITE_API_URL;


function checkUserRole(role) {
  if (role === "USER") {
    userRoleNumber.value = 1;        // 1 = USER
  } else if (role === "MANAGER") {
    userRoleNumber.value = 2;        // 2 = MANAGER
  } else if (role === "ADMIN") {
    userRoleNumber.value = 3;        // 3 = ADMIN
  }
}

const sidebarAlert = ref({
  // "manager_incoming": -1,
  // "user_rejected_4": 2,
  // "admin_all": 6,
  // "user_all_1": 4,
  // "user_done_3": 0,
  // "user_waiting_2": 2
});

async function isSideAlert() {
  await axios.get(mainURL + '/api/approve/sidebar/alert/' + Cookies.get('userId'), {
    headers: {'Authorization': 'Bearer ' + Cookies.get('accessToken')}
  })
      .then((response) => {
        sidebarAlert.value = response.data;
      })
      .catch((error) => {
        console.log(error);
      });
}

onBeforeMount(() => {
  checkUserRole(Cookies.get('role'));
  isSideAlert();
});

</script>

<template>
  <div class="flex min-h-screen ">
    <div class="w-64  bg-blue-950 ">

      <a v-if="userRoleNumber !== 2" href="/kw2/approval/create">
        <div class="px-6 pt-8 ">
          <button
              class=" text-white bg-blue-500 hover:bg-blue-600   rounded-lg text-sm px-8 py-2.5 text-center inline-flex items-center "
              type="button">
            ขออนุมัติเอกสาร
            <svg class="ml-2 w-6 h-6" fill="none" stroke="#ffffff" viewBox="0 0 24.00 24.00"
                 xmlns="http://www.w3.org/2000/svg">
              <g id="SVGRepo_bgCarrier" stroke-width="0"></g>
              <g id="SVGRepo_tracerCarrier" stroke="#CCCCCC" stroke-linecap="round" stroke-linejoin="round"
                 stroke-width="0.144"></g>
              <g id="SVGRepo_iconCarrier">
                <g stroke="#ffffff" stroke-linejoin="round" stroke-width="1.344">
                  <path d="M14.5 15.5h-5" stroke-linecap="round"></path>
                  <path d="M12 18v-5" stroke-linecap="round"></path>
                  <path
                      d="M5 20V4a1 1 0 011-1h6.172a2 2 0 011.414.586l4.828 4.828A2 2 0 0119 9.828V20a1 1 0 01-1 1H6a1 1 0 01-1-1z">
                  </path>
                  <path d="M12 3v6a1 1 0 001 1h6"></path>
                </g>
              </g>
            </svg>
          </button>
        </div>
      </a>


      <div v-if="userRoleNumber === 1 || userRoleNumber === 3" class="px-6 pt-4">
        <ul class="flex flex-col space-y-2">

          <br>
          <hr class="border-slate-400"/>
          <a class="px-1    font-bold text-xs text-neutral-400">การดำเนินงาน</a>
          <li class="relative text-white hover:text-white focus-within:text-white">
            <div class="absolute inset-y-0 left-0 flex items-center pl-2 pointer-events-none">
              <svg class="w-5 h-4" fill="none" viewBox="0 0 24 24" xmlns="http://www.w3.org/2000/svg">
                <g id="SVGRepo_bgCarrier" stroke-width="0"></g>
                <g id="SVGRepo_tracerCarrier" stroke-linecap="round" stroke-linejoin="round"></g>
                <g id="SVGRepo_iconCarrier">
                  <path
                      d="M8.5 21H4C4 17.134 7.13401 14 11 14C11.1681 14 11.3348 14.0059 11.5 14.0176M15 7C15 9.20914 13.2091 11 11 11C8.79086 11 7 9.20914 7 7C7 4.79086 8.79086 3 11 3C13.2091 3 15 4.79086 15 7ZM12.5898 21L14.6148 20.595C14.7914 20.5597 14.8797 20.542 14.962 20.5097C15.0351 20.4811 15.1045 20.4439 15.1689 20.399C15.2414 20.3484 15.3051 20.2848 15.4324 20.1574L19.5898 16C20.1421 15.4477 20.1421 14.5523 19.5898 14C19.0376 13.4477 18.1421 13.4477 17.5898 14L13.4324 18.1574C13.3051 18.2848 13.2414 18.3484 13.1908 18.421C13.1459 18.4853 13.1088 18.5548 13.0801 18.6279C13.0478 18.7102 13.0302 18.7985 12.9948 18.975L12.5898 21Z"
                      stroke="#ffffff" stroke-linecap="round" stroke-linejoin="round" stroke-width="2">
                  </path>
                </g>
              </svg>
            </div>
            <RouterLink to="/kw2/approval/list"><a
                class="inline-block w-full py-2 pl-8 pr-4 text-xs rounded hover:bg-neutral-900 focus:outline-none focus:ring-1 focus:ring-gray-500 focus:bg-gray-800">
              เอกสารรอดำเนินการ</a>
            </RouterLink>
            <!-- จุดแดง -->
            <div v-if="sidebarAlert.user_all_1 > 0"
                 class="absolute top-3 right-4 block w-2 h-2 bg-red-500 border border-white rounded-full  dark:border-gray-900">
            </div>
            <!--            <a href="/kw2/approval/list"-->
            <!--               class="inline-block w-full py-2 pl-8 pr-4 text-xs rounded hover:bg-neutral-900 focus:outline-none focus:ring-1 focus:ring-gray-500 focus:bg-gray-800">-->
            <!--              เอกสารรอผจก</a>-->
          </li>
          <li class="relative text-white hover:text-white focus-within:text-white">
            <div class="absolute inset-y-0 left-0 flex items-center pl-2 pointer-events-none">
              <svg class="w-5 h-4" fill="none" viewBox="0 0 24 24" xmlns="http://www.w3.org/2000/svg">
                <g id="SVGRepo_bgCarrier" stroke-width="0"></g>
                <g id="SVGRepo_tracerCarrier" stroke-linecap="round" stroke-linejoin="round"></g>
                <g id="SVGRepo_iconCarrier">
                  <path
                      d="M15 6H9M20 21H19M19 21H5M19 21C19 18.4898 17.7877 16.1341 15.7451 14.675L12 12M5 21H4M5 21C5 18.4898 6.21228 16.1341 8.25493 14.675L12 12M20 3H19M19 3H5M19 3C19 5.51022 17.7877 7.86592 15.7451 9.32495L12 12M5 3H4M5 3C5 5.51022 6.21228 7.86592 8.25493 9.32495L12 12"
                      stroke="#ffffff" stroke-linecap="round" stroke-linejoin="round" stroke-width="2">
                  </path>
                </g>
              </svg>
            </div>
            <RouterLink to="/kw2/approve/list/pending-request"><a
                class="inline-block w-full py-2 pl-8 pr-4 text-xs rounded hover:bg-neutral-900 focus:outline-none focus:ring-1 focus:ring-gray-500 focus:bg-gray-800">
              เอกสารรอการอนุมัติ </a>
            </RouterLink>

            <!-- จุดแดง -->
            <div v-if="sidebarAlert.user_waiting_2 > 0"
                class=" absolute top-3 right-4 block w-2 h-2 bg-red-500 border border-white rounded-full  dark:border-gray-900">
            </div>
            <!--            <a href="#"-->
            <!--               class="inline-block w-full py-2 pl-8 pr-4 text-xs rounded hover:bg-neutral-900 focus:outline-none focus:ring-1 focus:ring-gray-500 focus:bg-gray-800">-->
            <!--              เอกสารรอดำเนินการ</a>-->
          </li>

          <li class="relative text-white hover:text-white focus-within:text-white">
            <div class="absolute inset-y-0 left-0 flex items-center pl-2 pointer-events-none">
              <svg class="w-5 h-4" enable-background="new 0 0 32 32" fill="#ffffff" stroke="#ffffff" version="1.1"
                   viewBox="0 0 32 32" xml:space="preserve" xmlns="http://www.w3.org/2000/svg"
                   xmlns:xlink="http://www.w3.org/1999/xlink">
                <g id="SVGRepo_bgCarrier" stroke-width="0"></g>
                <g id="SVGRepo_tracerCarrier" stroke-linecap="round" stroke-linejoin="round"></g>
                <g id="SVGRepo_iconCarrier">
                  <g id="Approved"></g>
                  <g id="Approved_1_"></g>
                  <g id="File_Approve">
                    <g>
                      <path
                          d="M26,24c-0.553,0-1,0.448-1,1v4H7V3h10v7c0,0.552,0.447,1,1,1h7v4c0,0.552,0.447,1,1,1s1-0.448,1-1v-4.903 c0.003-0.033,0.02-0.063,0.02-0.097c0-0.337-0.166-0.635-0.421-0.816l-7.892-7.891c-0.086-0.085-0.187-0.147-0.292-0.195 c-0.031-0.015-0.063-0.023-0.097-0.034c-0.082-0.028-0.166-0.045-0.253-0.05C18.043,1.012,18.022,1,18,1H6C5.447,1,5,1.448,5,2v28 c0,0.552,0.447,1,1,1h20c0.553,0,1-0.448,1-1v-5C27,24.448,26.553,24,26,24z M19,9V4.414L23.586,9H19z">
                      </path>
                      <path
                          d="M30.73,15.317c-0.379-0.404-1.01-0.424-1.414-0.047l-10.004,9.36l-4.629-4.332c-0.404-0.378-1.036-0.357-1.414,0.047 c-0.377,0.403-0.356,1.036,0.047,1.413l5.313,4.971c0.192,0.18,0.438,0.27,0.684,0.27s0.491-0.09,0.684-0.27l10.688-10 C31.087,16.353,31.107,15.72,30.73,15.317z">
                      </path>
                    </g>
                  </g>
                  <g id="Folder_Approved"></g>
                  <g id="Security_Approved"></g>
                  <g id="Certificate_Approved"></g>
                  <g id="User_Approved"></g>
                  <g id="ID_Card_Approved"></g>
                  <g id="Android_Approved"></g>
                  <g id="Privacy_Approved"></g>
                  <g id="Approved_2_"></g>
                  <g id="Message_Approved"></g>
                  <g id="Upload_Approved"></g>
                  <g id="Download_Approved"></g>
                  <g id="Email_Approved"></g>
                  <g id="Data_Approved"></g>
                </g>
              </svg>
            </div>
            <RouterLink to="/kw2/approval/list/done-request"><a
                class="inline-block w-full py-2 pl-8 pr-4 text-xs rounded hover:bg-neutral-900 focus:outline-none focus:ring-1 focus:ring-gray-500 focus:bg-gray-800">
              คำร้องที่ผ่านอนุมัติ</a>
            </RouterLink>
            <!-- จุดแดง -->
            <div v-if="sidebarAlert.user_done_3 > 0"
                class="absolute top-3 right-4 block w-2 h-2 bg-red-500 border border-white rounded-full  dark:border-gray-900">
            </div>
          </li>


          <!-- เอกสารที่ไ่ผ่านการอนุมัติ -->
          <li class="relative text-white hover:text-white focus-within:text-white">
            <div class="absolute inset-y-0 left-0 flex items-center pl-2 pointer-events-none">
              <svg class="w-5 h-5 " viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg"><g id="SVGRepo_bgCarrier" stroke-width="0"></g><g id="SVGRepo_tracerCarrier" stroke-linecap="round" stroke-linejoin="round"></g><g id="SVGRepo_iconCarrier"> <path d="M6.5 12C9.53757 12 12 14.4624 12 17.5C12 20.5376 9.53757 23 6.5 23C3.46243 23 1 20.5376 1 17.5C1 14.4624 3.46243 12 6.5 12ZM12.1289 2C12.7254 2 13.2976 2.23689 13.7195 2.65859L16.5314 5.46899L19.339 8.28087C19.7602 8.70273 19.9968 9.27451 19.9968 9.87066V19.75C19.9968 20.9926 18.9895 22 17.7468 22L11.1896 22.0008C11.616 21.5566 11.98 21.052 12.2672 20.5011L17.7468 20.5C18.161 20.5 18.4968 20.1642 18.4968 19.75L18.496 10.003L14.25 10.0039C13.0591 10.0039 12.0844 9.07873 12.0052 7.90794L12 7.75389L11.999 3.5H6.25C5.83579 3.5 5.5 3.83579 5.5 4.25L5.49909 11.0766C4.97586 11.1575 4.47324 11.3007 3.99907 11.4986L4 4.25C4 3.00736 5.00736 2 6.25 2H12.1289ZM4.09305 14.9663L4.0238 15.0241L3.96594 15.0934C3.84782 15.2639 3.84782 15.4915 3.96594 15.662L4.0238 15.7312L5.79335 17.5007L4.02639 19.2675L3.96853 19.3367C3.85041 19.5072 3.85041 19.7348 3.96853 19.9053L4.02639 19.9746L4.09564 20.0325C4.26615 20.1506 4.49374 20.1506 4.66425 20.0325L4.7335 19.9746L6.50035 18.2077L8.26939 19.9768L8.33864 20.0347C8.50915 20.1528 8.73674 20.1528 8.90725 20.0347L8.9765 19.9768L9.03436 19.9076C9.15248 19.7371 9.15248 19.5095 9.03436 19.339L8.9765 19.2697L7.20735 17.5007L8.97927 15.7313L9.03712 15.662C9.15525 15.4915 9.15525 15.2639 9.03712 15.0934L8.97927 15.0242L8.91002 14.9663C8.73951 14.8482 8.51192 14.8482 8.34141 14.9663L8.27216 15.0242L6.50035 16.7937L4.73091 15.0241L4.66166 14.9663C4.49115 14.8482 4.26356 14.8482 4.09305 14.9663ZM13.499 4.559L13.5 7.75389C13.5 8.13359 13.7822 8.44738 14.1482 8.49704L14.25 8.50389L17.441 8.503L13.499 4.559Z" fill="#ffffff"></path> </g></svg>
            </div>
            <RouterLink to="/kw2/approve/list/reject-request"><a
                class="inline-block w-full py-2 pl-8 pr-4 text-xs rounded hover:bg-neutral-900 focus:outline-none focus:ring-1 focus:ring-gray-500 focus:bg-gray-800">
              คำร้องที่ไม่ผ่านการอนุมัติ </a>
            </RouterLink>

            <!-- จุดแดง -->
            <div v-if="sidebarAlert.user_rejected_4 > 0"
                class=" absolute top-3 right-4 block w-2 h-2 bg-red-500 border border-white rounded-full  dark:border-gray-900">
            </div>
            <!--            <a href="#"-->
            <!--               class="inline-block w-full py-2 pl-8 pr-4 text-xs rounded hover:bg-neutral-900 focus:outline-none focus:ring-1 focus:ring-gray-500 focus:bg-gray-800">-->
            <!--              เอกสารรอดำเนินการ</a>-->
          </li>
        </ul>
      </div>


      <div v-if="userRoleNumber === 2" class="px-6 pt-8">
        <hr v-if="userRoleNumber !== 2" class="border-slate-400"/>
        <a class="px-1 font-bold text-xs text-neutral-400">การอนุมัติ</a>
        <ul class="flex flex-col space-y-2">

          <li class="relative text-white hover:text-white focus-within:text-white">
            <div class="absolute inset-y-0 left-0 flex items-center pl-2 pointer-events-none">
              <svg class="w-5 h-5 stroke-current" fill="none" viewBox="0 0 24 24">
                <path d="M7.75 19.25H16.25C17.3546 19.25 18.25 18.3546 18.25 17.25V9L14 4.75H7.75C6.64543 4.75 5.75 5.64543 5.75 6.75V17.25C5.75 18.3546 6.64543 19.25 7.75 19.25Z" stroke="currentColor" stroke-linecap="round" stroke-linejoin="round"
                      stroke-width="1.5">
                </path>
                <path d="M18 9.25H13.75V5" stroke="currentColor" stroke-linecap="round" stroke-linejoin="round"
                      stroke-width="1.5"></path>
                <path d="M9.75 15.25H14.25" stroke="currentColor" stroke-linecap="round" stroke-linejoin="round"
                      stroke-width="1.5"></path>
                <path d="M9.75 12.25H14.25" stroke="currentColor" stroke-linecap="round" stroke-linejoin="round"
                      stroke-width="1.5"></path>
              </svg>
            </div>

            <div>
              <RouterLink to="/kw2/approval/list/manager-accept"><a
                  class="inline-block w-full py-2 pl-8 pr-4 text-xs rounded hover:bg-neutral-900 focus:outline-none focus:ring-1 focus:ring-gray-500 focus:bg-gray-800">
                เอกสารรอการอนุมัติ </a>
              </RouterLink>
            </div>
            <!-- จุดแดง -->
            <div v-if="sidebarAlert.manager_incoming > 0"
                class=" absolute top-3 right-4 block w-2 h-2 bg-red-500 border border-white rounded-full  dark:border-gray-900">
            </div>
          </li>

          <li class="relative text-white hover:text-white focus-within:text-white">
            <div class="absolute inset-y-0 left-0 flex items-center pl-2 pointer-events-none">
              <svg class="w-5 h-5 stroke-current" fill="none" viewBox="0 0 24 24">
                <path d="M12 4.75L19.25 9L12 13.25L4.75 9L12 4.75Z" stroke="currentColor" stroke-linecap="round"
                      stroke-linejoin="round" stroke-width="1.5"></path>
                <path d="M9.25 12L4.75 15L12 19.25L19.25 15L14.6722 12" stroke="currentColor" stroke-linecap="round"
                      stroke-linejoin="round" stroke-width="1.5"></path>
              </svg>
            </div>
            <RouterLink to="/kw2/approval/status"><a
                class="inline-block w-full py-2 pl-8 pr-4 text-xs rounded hover:bg-neutral-900 focus:outline-none focus:ring-1 focus:ring-gray-500 focus:bg-gray-800">
              สถานะการอนุมัติ</a>
            </RouterLink>
            <div
                class=" absolute top-3 right-4 block w-2 h-2 bg-red-500 border border-white rounded-full  dark:border-gray-900">
            </div>
          </li>


          <li class="relative text-white hover:text-white focus-within:text-white">
            <div class="absolute inset-y-0 left-0 flex items-center pl-2 pointer-events-none">
              <svg class="w-5 h-5 stroke-current" fill="none" viewBox="0 0 24 24">
                <circle cx="15" cy="9" fill="currentColor" r="1"></circle>
                <path d="M12 4.75H19.25V12L12.5535 18.6708C11.7544 19.4668 10.4556 19.445 9.68369 18.6226L5.28993 13.941C4.54041 13.1424 4.57265 11.8895 5.36226 11.1305L12 4.75Z" stroke="currentColor" stroke-linecap="round" stroke-linejoin="round"
                      stroke-width="1.5">
                </path>
              </svg>
            </div>

            <RouterLink to="/kw2/approval/list/manager-accept"><a
                class="inline-block w-full py-2 pl-8 pr-4 text-xs rounded hover:bg-neutral-900 focus:outline-none focus:ring-1 focus:ring-gray-500 focus:bg-gray-800">
              สรุปสถิติการอนุมัติเอกสาร</a>
            </RouterLink>

          </li>
        </ul>
      </div>


      <div v-if="userRoleNumber === 3" class="px-6 pt-8">
        <hr class="border-slate-400"/>
        <a class="px-1 font-bold text-xs text-neutral-400">เมนูผู้ดูแลระบบ</a>
        <ul class="flex flex-col space-y-2">

          <li class="relative text-white hover:text-white focus-within:text-white">
            <div class="absolute inset-y-0 left-0 flex items-center pl-2 pointer-events-none">
              <svg class="w-5 h-5 stroke-current" fill="none" viewBox="0 0 24 24">
                <path d="M7.75 19.25H16.25C17.3546 19.25 18.25 18.3546 18.25 17.25V9L14 4.75H7.75C6.64543 4.75 5.75 5.64543 5.75 6.75V17.25C5.75 18.3546 6.64543 19.25 7.75 19.25Z" stroke="currentColor" stroke-linecap="round" stroke-linejoin="round"
                      stroke-width="1.5">
                </path>
                <path d="M18 9.25H13.75V5" stroke="currentColor" stroke-linecap="round" stroke-linejoin="round"
                      stroke-width="1.5"></path>
                <path d="M9.75 15.25H14.25" stroke="currentColor" stroke-linecap="round" stroke-linejoin="round"
                      stroke-width="1.5"></path>
                <path d="M9.75 12.25H14.25" stroke="currentColor" stroke-linecap="round" stroke-linejoin="round"
                      stroke-width="1.5"></path>
              </svg>
            </div>

            <RouterLink to="/kw2/approval/admin/dashboard"><a
                class="inline-block w-full py-2 pl-8 pr-4 text-xs rounded hover:bg-neutral-900 focus:outline-none focus:ring-1 focus:ring-gray-500 focus:bg-gray-800">
              รายการเอกสารทั้งหมด</a>

            </RouterLink>
            <!-- จุดแดง -->
            <div v-if="sidebarAlert.admin_all > 0"
                 class=" absolute top-3 right-4 block w-2 h-2 bg-red-500 border border-white rounded-full  dark:border-gray-900">
            </div>
          </li>

          <li class="relative text-white hover:text-white focus-within:text-white">
            <div class="absolute inset-y-0 left-0 flex items-center pl-2 pointer-events-none">
              <svg class="w-5 h-5 stroke-current" fill="none" viewBox="0 0 24 24">
                <path d="M12 4.75L19.25 9L12 13.25L4.75 9L12 4.75Z" stroke="currentColor" stroke-linecap="round"
                      stroke-linejoin="round" stroke-width="1.5"></path>
                <path d="M9.25 12L4.75 15L12 19.25L19.25 15L14.6722 12" stroke="currentColor" stroke-linecap="round"
                      stroke-linejoin="round" stroke-width="1.5"></path>
              </svg>
            </div>
            <RouterLink to="/kw2/approval/status"><a
                class="inline-block w-full py-2 pl-8 pr-4 text-xs rounded hover:bg-neutral-900 focus:outline-none focus:ring-1 focus:ring-gray-500 focus:bg-gray-800">
              สรุปสถิติการอนุมัติเอกสาร </a>
            </RouterLink>
            <!--                      <a href="/kw2/approval/status" data-tooltip-trigger="click"-->
            <!--                         class="inline-block w-full py-2 pl-8 pr-4 text-xs rounded hover:bg-neutral-900 focus:outline-none focus:ring-1 focus:ring-gray-500 focus:bg-gray-800">-->
            <!--                        สถานะการอนุมัติ</a>-->
            <!-- จุดแดง -->
<!--            <div v-if="sidebarAlert.admin_all > 0"-->
<!--                 class=" absolute top-3 right-4 block w-2 h-2 bg-red-500 border border-white rounded-full  dark:border-gray-900">-->
<!--            </div>-->
          </li>


          <li class="relative text-white hover:text-white focus-within:text-white">
            <div class="absolute inset-y-0 left-0 flex items-center pl-2 pointer-events-none">
              <svg class="w-5 h-5 stroke-current" fill="none" viewBox="0 0 24 24">
                <circle cx="15" cy="9" fill="currentColor" r="1"></circle>
                <path d="M12 4.75H19.25V12L12.5535 18.6708C11.7544 19.4668 10.4556 19.445 9.68369 18.6226L5.28993 13.941C4.54041 13.1424 4.57265 11.8895 5.36226 11.1305L12 4.75Z" stroke="currentColor" stroke-linecap="round" stroke-linejoin="round"
                      stroke-width="1.5">
                </path>
              </svg>
            </div>
            <RouterLink to="/kw2/approval/admin/dashboard"><a
                class="inline-block w-full py-2 pl-8 pr-4 text-xs rounded hover:bg-neutral-900 focus:outline-none focus:ring-1 focus:ring-gray-500 focus:bg-gray-800">
              รายงานและสถิติทั้งหมด </a>
            </RouterLink>
            <!--            <a href="/kw2/approval/admin/dashboard"-->
            <!--               class="inline-block w-full py-2 pl-8 pr-4 text-xs rounded hover:bg-neutral-900 focus:outline-none focus:ring-1 focus:ring-gray-500 focus:bg-gray-800">-->
            <!--              รายงานและสถิติทั้งหมด </a>-->
          </li>
        </ul>
      </div>
      <!-- <a class="px-6 pt-4 mt-4   font-bold text-xs text-neutral-400">หมวดหมู่ของผู้ใช้</a> -->
      <br>
      <div class="px-6 pt-4 pb-8">
        <hr class="border-slate-400"/>
        <a class="px-1 font-bold text-xs text-neutral-400">อื่น ๆ</a>
        <ul>
          <!-- <li class="relative text-white hover:text-white focus-within:text-white">
              <div class="absolute inset-y-0 left-0 flex items-center pl-2 pointer-events-none">
                  <svg class="w-5 h-5 stroke-current" fill="none" viewBox="0 0 24 24">
                      <path stroke="currentColor" stroke-linecap="round" stroke-linejoin="round"
                          stroke-width="1.5"
                          d="M4.75 5.75C4.75 5.19772 5.19772 4.75 5.75 4.75H9.25C9.80228 4.75 10.25 5.19772 10.25 5.75V9.25C10.25 9.80228 9.80228 10.25 9.25 10.25H5.75C5.19772 10.25 4.75 9.80228 4.75 9.25V5.75Z">
                      </path>
                      <path stroke="currentColor" stroke-linecap="round" stroke-linejoin="round"
                          stroke-width="1.5"
                          d="M4.75 14.75C4.75 14.1977 5.19772 13.75 5.75 13.75H9.25C9.80228 13.75 10.25 14.1977 10.25 14.75V18.25C10.25 18.8023 9.80228 19.25 9.25 19.25H5.75C5.19772 19.25 4.75 18.8023 4.75 18.25V14.75Z">
                      </path>
                      <path stroke="currentColor" stroke-linecap="round" stroke-linejoin="round"
                          stroke-width="1.5"
                          d="M13.75 5.75C13.75 5.19772 14.1977 4.75 14.75 4.75H18.25C18.8023 4.75 19.25 5.19772 19.25 5.75V9.25C19.25 9.80228 18.8023 10.25 18.25 10.25H14.75C14.1977 10.25 13.75 9.80228 13.75 9.25V5.75Z">
                      </path>
                      <path stroke="currentColor" stroke-linecap="round" stroke-linejoin="round"
                          stroke-width="1.5"
                          d="M13.75 14.75C13.75 14.1977 14.1977 13.75 14.75 13.75H18.25C18.8023 13.75 19.25 14.1977 19.25 14.75V18.25C19.25 18.8023 18.8023 19.25 18.25 19.25H14.75C14.1977 19.25 13.75 18.8023 13.75 18.25V14.75Z">
                      </path>
                  </svg>
              </div>
              <a href="#"
                  class="inline-block w-full py-2 pl-8 pr-4 text-xs rounded hover:bg-neutral-900 focus:outline-none focus:ring-1 focus:ring-gray-500 focus:bg-gray-800">
                  พัสดุและคลัง</a>
          </li> -->
          <li class="relative text-white hover:text-white focus-within:text-white">
            <div class="absolute inset-y-0 left-0 flex items-center pl-2 pointer-events-none">
              <svg class="w-5 h-5 stroke-current" fill="none" viewBox="0 0 24 24">
                <path d="M13.1191 5.61336C13.0508 5.11856 12.6279 4.75 12.1285 4.75H11.8715C11.3721 4.75 10.9492 5.11856 10.8809 5.61336L10.7938 6.24511C10.7382 6.64815 10.4403 6.96897 10.0622 7.11922C10.006 7.14156 9.95021 7.16484 9.89497 7.18905C9.52217 7.3524 9.08438 7.3384 8.75876 7.09419L8.45119 6.86351C8.05307 6.56492 7.49597 6.60451 7.14408 6.9564L6.95641
                                    7.14408C6.60452 7.49597 6.56492 8.05306 6.86351 8.45118L7.09419 8.75876C7.33841 9.08437 7.3524 9.52216 7.18905 9.89497C7.16484 9.95021 7.14156 10.006 7.11922 10.0622C6.96897 10.4403 6.64815 10.7382 6.24511 10.7938L5.61336 10.8809C5.11856 10.9492 4.75 11.372 4.75 11.8715V12.1285C4.75 12.6279 5.11856 13.0508 5.61336 13.1191L6.24511 13.2062C6.64815
                                    13.2618 6.96897 13.5597 7.11922 13.9378C7.14156 13.994 7.16484 14.0498 7.18905 14.105C7.3524 14.4778 7.3384 14.9156 7.09419 15.2412L6.86351 15.5488C6.56492 15.9469 6.60451 16.504 6.9564 16.8559L7.14408 17.0436C7.49597 17.3955 8.05306 17.4351 8.45118 17.1365L8.75876 16.9058C9.08437 16.6616 9.52216 16.6476 9.89496 16.811C9.95021 16.8352 10.006
                                    16.8584 10.0622 16.8808C10.4403 17.031 10.7382 17.3519 10.7938 17.7549L10.8809 18.3866C10.9492 18.8814 11.3721 19.25 11.8715 19.25H12.1285C12.6279 19.25 13.0508 18.8814 13.1191 18.3866L13.2062 17.7549C13.2618 17.3519 13.5597 17.031 13.9378 16.8808C13.994
                                    16.8584 14.0498 16.8352 14.105 16.8109C14.4778 16.6476 14.9156 16.6616 15.2412 16.9058L15.5488 17.1365C15.9469 17.4351 16.504 17.3955 16.8559 17.0436L17.0436 16.8559C17.3955 16.504 17.4351 15.9469 17.1365 15.5488L16.9058 15.2412C16.6616 14.9156 16.6476 14.4778 16.811 14.105C16.8352 14.0498 16.8584 13.994 16.8808 13.9378C17.031
                                    13.5597 17.3519 13.2618 17.7549 13.2062L18.3866 13.1191C18.8814 13.0508 19.25 12.6279 19.25 12.1285V11.8715C19.25 11.3721 18.8814 10.9492 18.3866 10.8809L17.7549 10.7938C17.3519 10.7382 17.031 10.4403 16.8808 10.0622C16.8584 10.006 16.8352 9.95021 16.8109 9.89496C16.6476 9.52216 16.6616 9.08437 16.9058
                                    8.75875L17.1365 8.4512C17.4351 8.05308 17.3955 7.49599 17.0436 7.1441L16.8559 6.95642C16.504 6.60453 15.9469 6.56494 15.5488 6.86353L15.2412 7.09419C14.9156 7.33841 14.4778 7.3524 14.105 7.18905C14.0498 7.16484 13.994 7.14156 13.9378 7.11922C13.5597 6.96897 13.2618 6.64815 13.2062 6.24511L13.1191 5.61336Z" stroke="currentColor" stroke-linecap="round" stroke-linejoin="round"
                      stroke-width="1.5">
                </path>
                <path d="M13.25 12C13.25 12.6904 12.6904 13.25 12 13.25C11.3096 13.25 10.75 12.6904 10.75 12C10.75 11.3096 11.3096 10.75 12 10.75C12.6904 10.75 13.25 11.3096 13.25 12Z" stroke="currentColor" stroke-linecap="round" stroke-linejoin="round"
                      stroke-width="1.5">
                </path>
              </svg>
            </div>
            <a class="inline-block w-full py-2 pl-8 pr-4 text-xs rounded hover:bg-neutral-900 focus:outline-none focus:ring-1 focus:ring-gray-500 focus:bg-gray-800"
               href="#">
              ตั้งค่า</a>
          </li>
        </ul>
      </div>

    </div>
  </div>
</template>

<style></style>