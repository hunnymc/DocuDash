<script setup>
import {onBeforeMount, onMounted, ref, watch} from "vue";
import {useRoute, useRouter} from "vue-router";
import {useDocumentListStore} from "../../../stores/listOfDocumentStore.js";
import {FwbButton, FwbModal} from "flowbite-vue";
import axios from "axios";
import Cookies from "js-cookie";

const route = useRoute();
const router = useRouter();
let isEverythingLoaded = ref(false);

const store = useDocumentListStore();
let user_id = Cookies.get('userId');

let mainURL = import.meta.env.VITE_API_URL;

// let mainURL = "http://localhost:5002";
// let mainURL = "http://cp23kw2.sit.kmutt.ac.th:10003";
// let mainURL = "https://capstone23.sit.kmutt.ac.th/kw2";
// let mainURL = "https://capstone23.sit.kmutt.ac.th/kw2";

let fileUrl = ref();
let showPDF = ref(false);

let doc = ref({
  "status_type_id": -1,
  "totalManagers": -1,
  "totalManagersWhoVerified": -1,
  "approve_type_Id": -1,
  "managersWhoVerified": [{
    "id": -1,
    "isPass": -1,
    "comment": null,
    "documentID": null,
    "managerID": {
      "id": -1,
      "username": "",
      "password": null,
      "fullName": "",
      "role": "",
      "email": "",
      "phone": "",
      "branch": ""
    }
  }],
  "documentInfo": {
    "id": -1,
    "title": "",
    "filePath": "",
    "dateAdd": 1711793503,
    "dateUpdate": 1711793503,
    "category": "",
    "secrecyLevel": "",
    "urgency": "",
    "fromSource": "",
    "branchSource": "",
    "emailSource": "",
    "phoneSource": "",
    "status": -1,
    "description": "",
    "usersUserid": {
      "id": -1,
      "username": "",
      "fullName": "",
      "role": "",
      "email": "",
      "phone": "",
      "branch": ""
    }
  }
});


let doc_id = ref();

const ClickFile = () => {
  window.open(fileUrl, '_blank');
  // const token = Cookies.get("accessToken"); // replace 'accessToken' with the actual key used to store the token
  // const urlWithToken = `${fileUrl}?token=${token}`;
  // window.open(urlWithToken, '_blank');
};

const getDocById = async () => {
  doc_id.value = route.params.id;
  const response = await axios.get(mainURL + "/api/approve/doc/" + doc_id.value,
      {
        headers: {
          "Authorization": "Bearer " + Cookies.get("accessToken"),
        },
      })
      .catch((error) => {
        switch (error.response.status) {
          case 401:
            alert("กรุณาเข้าสู่ระบบ");
            router.push("/kw2/login");
            break;
          case 403:
            alert("คุณไม่มีสิทธิ์เข้าถึงเอกสารนี้");
            router.push("/kw2/list");
            break;
          case 404:
            router.push("/kw2/NotFound");
            alert("ไม่พบเอกสารที่ต้องการ");
            break;
          default:
            alert("เกิดข้อผิดพลาดบางอย่าง");
            break;
        }
      });

  doc.value = response.data;
};

const isShowModal = ref(false)
const isShowModal2 = ref(false)

function closeModal() {
  isShowModal.value = false
}

function showModal() {
  isShowModal.value = true
}

function closeModal2() {
  isShowModal2.value = false
}

function showModal2() {
  isShowModal2.value = true
}

let approveResault = ref(-1);

function ClicktoApprove(isPass) {

  let message = "";
  approveResault.value = isPass

  if (isPass === 1) {
    message = "คุณต้องการอนุมัติคำขอนี้ใช่หรือไม่?";
    if (confirm(message)) {
      axios.post(mainURL + "/api/approve/mg/check-approve",
          {
            "document_id": doc_id.value,
            "is_pass": isPass,
            "manager_id": user_id
          },
          {
            headers: {
              "Authorization": "Bearer " + Cookies.get("accessToken"),
            },
          }
      )
          .then((response) => {
            router.push("/kw2/approval/list/manager-accept");
          })
          .catch((error) => {
            alert(error.response.data.message);
          });
    }
  } else if (isPass === 0){
    showModal();
  } else if (isPass === 2) {
    showModal2();
  }
}

let comment = ref("");

const RejectUserRequest = () => {

  axios.post(mainURL + "/api/approve/mg/check-approve",
      {
        "document_id": doc_id.value,
        "is_pass": approveResault.value,
        "manager_id": user_id,
        "comment": comment.value,
      },
      {
        headers: {
          "Authorization": "Bearer " + Cookies.get("accessToken"),
        },
      }
  )
      .then((response) => {
        router.push("/kw2/approval/status");
      })
      .catch((error) => {
        alert(error.response.data.message);
      });
}

const ApproveType = {
  1: "คำร้องขออนุมัติเอกสาร",
  2: "คำร้องแจ้งให้ทราบ"
}

const checkFileIsAvailable = async () => {
  const fileUrl = mainURL + '/api/files/' + store.getDocumentUserId + '/' + store.getDocumentFilename;
  try {
    const response = await axios.get(fileUrl, {
      headers: {
        "Authorization": "Bearer " + Cookies.get("accessToken"),
      },
    });
    if (response.status === 200) {
      console.log("File is available");
      showPDF.value = true;
    } else {
      console.log("File is not available");
      showPDF.value = false;
    }
  } catch (error) {
    console.log("Error occurred while checking file availability: ", error);
  }
};

let isManagerHasNotPassed_data = ref(false);
let checkManagerHasPassed_data = ref(-1);

function isManagerHasNotPassed(managerId) {
  managerId = parseInt(managerId);
  console.log("managerId: ", managerId);
  const manager = doc.value.managersWhoVerified.find(manager => manager.managerID.id === managerId);
  console.log("manager: ", manager);
  isManagerHasNotPassed_data.value = manager.isPass === -1;
  console.log("isManagerHasNotPassed_data.value: ", isManagerHasNotPassed_data.value);
}

function checkManagerHasPassed(managerId) {
  managerId = parseInt(managerId);
  const manager = doc.value.managersWhoVerified.find(manager => manager.managerID.id === managerId);
  if (manager.isPass === 1) {
    checkManagerHasPassed_data.value = 1;
  } else if (manager.isPass === 0) {
    checkManagerHasPassed_data.value = 0;
  } else {
    checkManagerHasPassed_data.value = -1;
  }
  console.log("checkManagerHasPassed_data.value: ", checkManagerHasPassed_data.value);
}

onBeforeMount(() => {
  checkFileIsAvailable();
});

onMounted(async () => {
  await getDocById();
  if (store.getDocumentUserId == null || store.getDocumentFilename == null) {
    router.go(-1)
  }
  isEverythingLoaded.value = true;
  fileUrl = mainURL + '/api/files/' + store.getDocumentUserId + '/' + store.getDocumentFilename;

  isManagerHasNotPassed(user_id);

  if (isManagerHasNotPassed_data.value === true) {
    checkManagerHasPassed(user_id);
  }

});

watch(() => route.params.id, async () => {
  await getDocById();
  isEverythingLoaded.value = true;
});

</script>

<template>
  <div class="shadow-md sm:rounded-lg w-full ">
    <section class="bg-gray-200 dark:bg-gray-900 ">
      <div class="py-8 px-auto ml-5 lg:py-6 justify-center">
        <div class="inline-flex mb-8 px-5 py-3 text-gray-700 border border-gray-200 rounded-lg bg-gray-100 "
             aria-label="Breadcrumb">
          <ol class="inline-flex items-center space-x-1 md:space-x-2 rtl:space-x-reverse">
            <li class="inline-flex items-center">
              <a href="#"
                 class="inline-flex items-center text-sm font-medium text-gray-700 hover:text-blue-600 dark:text-gray-400 dark:hover:text-white">
                <svg class="w-6 h-7 pr-1" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg">
                  <g id="SVGRepo_bgCarrier" stroke-width="0"></g>
                  <g id="SVGRepo_tracerCarrier" stroke-linecap="round" stroke-linejoin="round"></g>
                  <g id="SVGRepo_iconCarrier">
                    <path
                        d="M8 6.00067L21 6.00139M8 12.0007L21 12.0015M8 18.0007L21 18.0015M3.5 6H3.51M3.5 12H3.51M3.5 18H3.51M4 6C4 6.27614 3.77614 6.5 3.5 6.5C3.22386 6.5 3 6.27614 3 6C3 5.72386 3.22386 5.5 3.5 5.5C3.77614 5.5 4 5.72386 4 6ZM4 12C4 12.2761 3.77614 12.5 3.5 12.5C3.22386 12.5 3 12.2761 3 12C3 11.7239 3.22386 11.5 3.5 11.5C3.77614 11.5 4 11.7239 4 12ZM4 18C4 18.2761 3.77614 18.5 3.5 18.5C3.22386 18.5 3 18.2761 3 18C3 17.7239 3.22386 17.5 3.5 17.5C3.77614 17.5 4 17.7239 4 18Z"
                        stroke="#000000" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"></path>
                  </g>
                </svg>
                รายการเอกสารรอการอนุมัติ
              </a>
            </li>

            <li aria-current="page">
              <div class="flex items-center">
                <svg class="rtl:rotate-180  w-3 h-3 mx-1 text-gray-400" aria-hidden="true"
                     xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 6 10">
                  <path stroke="currentColor" stroke-linecap="round" stroke-linejoin="round"
                        stroke-width="2" d="m1 9 4-4-4-4"/>
                </svg>
                <span
                    class="ms-1 text-sm font-medium text-gray-500 md:ms-2 dark:text-gray-400">รายละเอียด</span>
              </div>
            </li>
          </ol>
        </div>
        <h2 class="mb-4 text-4xl font-bold text-gray-900 dark:text-white"> รายละเอียดคำขออนุมัติ
        </h2>
        <hr class="flex  w-11/12 mb-2 border-slate-400"/>
        <div class=" flex justify-center items-center w-full">
          <ol
              class="mt-4 mr-36 bg-gray-50 rounded-lg p-4 pt-4 flex items-center w-1/2 text-sm font-medium text-center text-gray-500  sm:text-base">
            <li
                class="flex md:w-full items-center text-blue-600 dark:text-blue-500 sm:after:content-[''] after:w-full after:h-1 after:border-b after:border-blue-600 after:border-1 after:hidden sm:after:inline-block after:mx-6 xl:after:mx-10 dark:after:border-gray-700">
              <span class="flex items-center  sm:after:hidden  after:text-gray-200 dark:after:text-gray-500">
                <svg aria-hidden="true" class="w-3.5 h-3.5 sm:w-4 sm:h-4 me-2.5" fill="currentColor" viewBox="0 0 20 20"
                     xmlns="http://www.w3.org/2000/svg">
                  <path
                      d="M10 .5a9.5 9.5 0 1 0 9.5 9.5A9.51 9.51 0 0 0 10 .5Zm3.707 8.207-4 4a1 1 0 0 1-1.414 0l-2-2a1 1 0 0 1 1.414-1.414L9 10.586l3.293-3.293a1 1 0 0 1 1.414 1.414Z"/>
                </svg>
                Submit<span class="hidden sm:inline-flex sm:ms-2">request</span>
              </span>
            </li>
            <li
                class="flex md:w-full items-center after:content-[''] after:w-full after:h-1 after:border-b after:border-blue-600 after:border-1 after:hidden sm:after:inline-block after:mx-6 xl:after:mx-10 dark:after:border-gray-700">
              <span
                  class="flex items-center text-blue-600 after:content-['/'] sm:after:hidden after:mx-2 after:text-gray-200 dark:after:text-gray-500">
                <svg aria-hidden="true" class="w-3.5 h-3.5 sm:w-4 sm:h-4 me-2.5" fill="currentColor" viewBox="0 0 20 20"
                     xmlns="http://www.w3.org/2000/svg">
                  <path
                      d="M10 .5a9.5 9.5 0 1 0 9.5 9.5A9.51 9.51 0 0 0 10 .5Zm3.707 8.207-4 4a1 1 0 0 1-1.414 0l-2-2a1 1 0 0 1 1.414-1.414L9 10.586l3.293-3.293a1 1 0 0 1 1.414 1.414Z"/>
                </svg>
                Admin <span class="hidden sm:inline-flex sm:ms-2">checks</span>
              </span>
            </li>
            <li class="text-blue-600 flex items-center">
              <span class="me-2"><svg aria-hidden="true" class="w-3.5 h-3.5 sm:w-4 sm:h-4 me-2.5" fill="currentColor"
                                      viewBox="0 0 20 20" xmlns="http://www.w3.org/2000/svg">
                  <path
                      d="M10 .5a9.5 9.5 0 1 0 9.5 9.5A9.51 9.51 0 0 0 10 .5Zm3.707 8.207-4 4a1 1 0 0 1-1.414 0l-2-2a1 1 0 0 1 1.414-1.414L9 10.586l3.293-3.293a1 1 0 0 1 1.414 1.414Z"/>
                </svg></span>
              Approval
            </li>

          </ol>
        </div>

        <div class="grid gap-4 grid-rows-8 sm:grid-cols-2 sm:gap-6">

          <div class="w-full">
            <!-- <label class=" block mb-2 text-sm font-bold text-gray-900 dark:text-white">เลขที่ :</label>
            <div
                class="block p-1 w-24 bg-gray-50 border border-gray-300 text-sm text-gray-500 dark:text-gray-400 dark:bg-gray-800 rounded-lg ">
                <h3 class="text-sm font-bold text-gray-900 dark:text-white ">{{ doc_id }}</h3>

            </div> -->
          </div>
          <br>
          <div class="w-full">
            <label class="block mb-2 text-sm font-bold text-gray-900 dark:text-white">เรื่อง :</label>
            <div
                class="block w-5/6 p-2.5 bg-gray-50 border border-gray-300 text-sm text-gray-500 dark:text-gray-400 dark:bg-gray-800 rounded-lg ">
              <h3 class="text-sm font-bold text-gray-900 dark:text-white ">{{ doc.documentInfo.title }}</h3>

            </div>
          </div>
          <div class="w-full ">
            <label class="block mb-2 text-sm font-bold text-gray-900 dark:text-white">ผู้ยื่นขออนุมัติ</label>
            <div
                class="block w-5/6 p-2.5 bg-gray-50 border border-gray-300 text-sm text-gray-500 dark:text-gray-400 dark:bg-gray-800 rounded-lg ">
              <h3 class="text-sm font-bold text-gray-900 dark:text-white ">{{ doc.documentInfo.fromSource }}</h3>
            </div>
          </div>
          <div class="w-full">
            <label class="block mb-2 text-sm font-bold text-gray-900 dark:text-white">รายละเอียด</label>
            <div
                class="block w-5/6 p-2.5 bg-gray-50 border border-gray-300 text-sm text-gray-500 dark:text-gray-400 dark:bg-gray-800 rounded-lg ">
              <h3 class="text-sm font-bold text-gray-900 dark:text-white ">{{ doc.documentInfo.description }}</h3>

            </div>
          </div>
          <div>
            <label class="block mb-2 text-sm font-bold text-gray-900 dark:text-white" for="category">แผนก</label>
            <div
                class="block w-5/6 p-2.5 bg-gray-50 border border-gray-300 text-sm text-gray-500 dark:text-gray-400 dark:bg-gray-800 rounded-lg ">
              <h3 class="text-sm font-bold text-gray-900 dark:text-white ">{{ doc.documentInfo.branchSource }}</h3>

            </div>
          </div>
          <div class="w-full">
            <label class="block mb-2 text-sm font-bold text-gray-900 dark:text-white">โทรศัพท์</label>
            <div
                class="block w-5/6 p-2.5 bg-gray-50 border border-gray-300 text-sm text-gray-500 dark:text-gray-400 dark:bg-gray-800 rounded-lg ">
              <h3 class="text-sm font-bold text-gray-900 dark:text-white ">{{ doc.documentInfo.phoneSource }}</h3>

            </div>
          </div>
          <div class="w-full">
            <label class="block mb-2 text-sm font-bold text-gray-900 dark:text-white">อีเมล</label>
            <div
                class="block w-5/6 p-2.5 bg-gray-50 border border-gray-300 text-sm text-gray-500 dark:text-gray-400 dark:bg-gray-800 rounded-lg ">
              <h3 class="text-sm font-bold text-gray-900 dark:text-white ">{{ doc.documentInfo.emailSource }}</h3>

            </div>
          </div>
          <!-- หมวดหมู่ -->
          <div>
            <label class="block mb-2 text-sm font-bold text-gray-900 dark:text-white"
                   for="category">ประเภทการขออนุมัติ</label>
            <div
                class="block w-5/6 p-2.5 bg-gray-50 border border-gray-300 text-sm text-gray-500 dark:text-gray-400 dark:bg-gray-800 rounded-lg ">
              <h3 class="text-sm font-bold text-gray-900 dark:text-white ">{{ ApproveType[doc.approve_type_Id] }}</h3>

            </div>
          </div>

          <div>
            <label class="block mb-2 text-sm font-bold text-gray-900 dark:text-white"
                   for="category">ความเร่งด่วน</label>
            <div
                class="block w-5/6 p-2.5 bg-gray-50 border border-gray-300 text-sm text-gray-500 dark:text-gray-400 dark:bg-gray-800 rounded-lg ">
              <h3 class="text-sm font-bold text-gray-900 dark:text-white ">{{ doc.documentInfo.urgency }}</h3>

            </div>
          </div>

          <div>
            <label class="block mb-2 text-sm font-bold text-gray-900 dark:text-white" for="category">ชั้นความลับ</label>
            <div
                class="block w-5/6 p-2.5 bg-gray-50 border border-gray-300 text-sm text-gray-500 dark:text-gray-400 dark:bg-gray-800 rounded-lg ">
              <h3 class="text-sm font-bold text-gray-900 dark:text-white ">{{ doc.documentInfo.secrecyLevel }}</h3>

            </div>
          </div>


          <div>
            <!-- divเปล่าเอาไว้ใช้ -->
          </div>

          <div>

          </div>
          <br>

          <!-- ที่แสดงไฟล์ -->
          <div class="w-full">
            <hr class="flex mx-auto w-full mb-2 border-slate-400"/>
            <a class="mb-3 font-bold text-lg text-teal-700">ไฟล์แนบคำขออนุมัติ</a><br><br>
            <div class="  rounded-lg shadow-md w-5/6">
              <table class="rounded-lg w-full text-sm text-left rtl:text-right text-gray-500 dark:text-gray-400">
                <thead class="text-xs text-gray-700 uppercase bg-gray-50 dark:bg-gray-700 dark:text-gray-400">
                <tr>

                  <th class="px-6 py-3" scope="col">
                    ชื่อไฟล์
                  </th>

                  <!-- <th scope="col" class="px-6 py-3">
                    ขนาด
                </th> -->

                </tr>
                </thead>
                <tbody>
                <tr v-show="showPDF"
                    class="bg-white border-b dark:bg-gray-800 dark:border-gray-700 hover:bg-gray-50 dark:hover:bg-gray-600">
                  <td class="px-6 py-4 text-blue-600 dark:text-blue-500 underline cursor-pointer" @click="ClickFile">
                    {{ doc.documentInfo.filePath }}
                  </td>
                </tr>

                <tr v-show="!showPDF"
                    class="bg-white border-b dark:bg-gray-800 dark:border-gray-700 hover:bg-gray-50 dark:hover:bg-gray-600">
                  <td class="disabled px-6 py-4 text-black dark:text-blue-500">
                    <p class="text-red-600">⚠️ ไฟล์ไม่พร้อมใช้งาน</p>
                    <br>
                    {{ doc.documentInfo.filePath }}
                  </td>
                </tr>

                </tbody>
              </table>

            </div>
          </div>


          <div class="w-5/6">
            <hr class="w-full mb-2 border-slate-400"/>
            <a class="mb-3 font-bold text-lg text-teal-700">การอนุมัติ</a><br><br>

            <!-- ปุ่มอนุมัติ -->

            <!-- เมื่อ manager ยังไม่ได้ตรวจสอบคำร้องนี้ -->
            <div v-if="isManagerHasNotPassed_data">
              <ul class="grid w-full gap-6 md:grid-cols-2">
                <li>
                  <div
                      class="inline-flex items-center justify-between w-full p-5 text-white bg-green-600 border border-gray-200 rounded-lg cursor-pointer dark:hover:text-gray-300 dark:border-gray-700 dark:peer-checked:text-blue-500 peer-checked:border-blue-600 peer-checked:text-blue-600 hover:text-gray-100 hover:bg-green-700 dark:text-gray-400 dark:bg-gray-800 dark:hover:bg-gray-700"
                      @click="ClicktoApprove(1)">
                    <div class="block">
                      <div class="w-full text-lg font-semibold">อนุมัติคำขอ</div>
                      <div class="w-full">อนุมัติคำร้องของผู้ใช้เพื่อดำเนินการต่อ</div>
                    </div>
                    <svg aria-hidden="true" class="w-5 h-5 ms-3 rtl:rotate-180" fill="none" viewBox="0 0 14 10"
                         xmlns="http://www.w3.org/2000/svg">
                      <path d="M1 5h12m0 0L9 1m4 4L9 9" stroke="currentColor" stroke-linecap="round"
                            stroke-linejoin="round" stroke-width="2"/>
                    </svg>
                  </div>
                </li>

                <li>
                  <div
                      class="inline-flex items-center justify-between w-full p-5 text-white bg-red-700 border border-gray-200 rounded-lg cursor-pointer dark:hover:text-gray-300 dark:border-gray-700 dark:peer-checked:text-blue-500 peer-checked:border-blue-600 peer-checked:text-blue-600 hover:text-gray-100 hover:bg-red-800 dark:text-gray-400 dark:bg-gray-800 dark:hover:bg-gray-700"
                      @click="ClicktoApprove(0)">
                    <div class="block">
                      <div class="w-full text-lg font-semibold">ไม่อนุมัติ</div>
                      <div class="w-full">ไม่อนุมัติคำร้องของผู้ใช้ที่ยื่นเข้ามา</div>
                    </div>
                    <svg class="w-5 h-5 ms-3 rtl:rotate-180" fill="#000000" version="1.1" viewBox="0 0 512 512"
                         xmlns="http://www.w3.org/2000/svg" xmlns:xlink="http://www.w3.org/1999/xlink">
                      <g id="SVGRepo_bgCarrier" stroke-width="0"></g>
                      <g id="SVGRepo_tracerCarrier" stroke-linecap="round" stroke-linejoin="round"></g>
                      <g id="SVGRepo_iconCarrier">
                        <title>cancel</title>
                        <g id="Page-1" fill="none" fill-rule="evenodd" stroke="none" stroke-width="1">
                          <g id="work-case" fill="#ffffff" transform="translate(91.520000, 91.520000)">
                            <polygon id="Close"
                                     points="328.96 30.2933333 298.666667 1.42108547e-14 164.48 134.4 30.2933333 1.42108547e-14 1.42108547e-14 30.2933333 134.4 164.48 1.42108547e-14 298.666667 30.2933333 328.96 164.48 194.56 298.666667 328.96 328.96 298.666667 194.56 164.48">
                            </polygon>
                          </g>
                        </g>
                      </g>
                    </svg>
                  </div>
                </li>

                <li>
                  <div
                      class="inline-flex items-center justify-between w-full p-5 text-white bg-amber-600 border border-gray-200 rounded-lg cursor-pointer dark:hover:text-gray-300 dark:border-gray-700 dark:peer-checked:text-blue-500 peer-checked:border-blue-600 peer-checked:text-blue-600 hover:text-gray-100 hover:bg-amber-700 dark:text-gray-400 dark:bg-gray-800 dark:hover:bg-gray-700"
                      @click="ClicktoApprove(2)">
                    <div class="block">
                      <div class="w-full text-lg font-semibold">ตีกลับ</div>
                      <div class="w-full">ตีกลับคำร้องที่ยื่นเข้ามาเพื่อให้ดำเนินการแก้ไขรายละเอียดคำขออนุมัติใหม่</div>
                    </div>
                    <svg class="w-13 h-12 ms-3 rtl:rotate-180" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg"><g id="SVGRepo_bgCarrier" stroke-width="0"></g><g id="SVGRepo_tracerCarrier" stroke-linecap="round" stroke-linejoin="round"></g><g id="SVGRepo_iconCarrier"> <path d="M9 22H15C20 22 22 20 22 15V9C22 4 20 2 15 2H9C4 2 2 4 2 9V15C2 20 4 22 9 22Z" stroke="#ffffff" stroke-width="1.5" stroke-linecap="round" stroke-linejoin="round"></path> <path d="M9.00002 15.3802H13.92C15.62 15.3802 17 14.0002 17 12.3002C17 10.6002 15.62 9.22021 13.92 9.22021H7.15002" stroke="#ffffff" stroke-width="1.5" stroke-miterlimit="10" stroke-linecap="round" stroke-linejoin="round"></path> <path d="M8.57 10.7701L7 9.19012L8.57 7.62012" stroke="#ffffff" stroke-width="1.5" stroke-linecap="round" stroke-linejoin="round"></path> </g></svg>
                  </div>
                </li>
              </ul>
            </div>


            <div v-else>
              <div class="font-extrabold my-1">
                ผลการอนุมัติ
              </div>
              <!--     ผลการอนุมัติ         -->
              <div v-if="checkManagerHasPassed_data === 1">
                <div
                    class="inline-flex items-center justify-center w-full p-5 text-white bg-green-600 border border-gray-200 rounded-lg peer-checked:border-blue-600 peer-checked:text-blue-600">
                  <div class="block">
                    <div class="w-full text-lg font-semibold">คำขอนี้ได้รับการอนุมัติแล้ว</div>
                  </div>
                </div>
              </div>

              <div v-if="checkManagerHasPassed_data === 0">
                <div
                    class="inline-flex items-center justify-center w-full p-5 text-white bg-red-700 border border-gray-200 rounded-lg peer-checked:border-blue-600 peer-checked:text-blue-600 ">
                  <div class="block">
                    <div class="w-full text-lg font-semibold">คำขอนี้ไม่ได้รับการอนุมัติ</div>
                  </div>

                </div>
              </div>


            </div>

          </div>


        </div>
        <br>
      </div>


      <!-- Click to watch file -->
      <div>

        <div v-show="showPDF"
             class="border-slate-300 bg-gray-700 w-full rounded-lg shadow-md pdf-container flex items-center justify-center py-10">
          <embed :src="mainURL + '/api/files/' + store.getDocumentUserId + '/' + store.getDocumentFilename"
                 height="1000px" type="application/pdf" width="100%"/>
        </div>

        <div v-show="!showPDF"
             class="border-slate-300 bg-gray-700 w-full rounded-lg shadow-md pdf-container flex items-center justify-center py-10">
          <div class="py-8 px-auto ml-5 mr-auto  lg:py-12 w-full  max-width: 100%  justify-center ">
            <h2 class="mb-4 text-4xl font-semibold text-white text-center">⚠️ เอกสารไม่พร้อมใช้งาน</h2>
          </div>
        </div>
      </div>

    </section>

  </div>


  <!-- modal for reject -->
  <fwb-modal v-if="isShowModal" @close="closeModal" persistent not-escapable size="3xl">
    <template #header>
      <div class="flex items-center text-lg font-bold">
        ไม่อนุมัติคำขอ - Not Approved
      </div>
    </template>
    <template #body>
      <p class="text-base leading-relaxed text-gray-500 ">
        โปรดระบุเหตุผลที่ไม่อนุมัติคำขอ เพื่อแจ้งให้เจ้าของคำร้องทราบ
      </p>
      <br>

      <div class="sm:col-span-2">
        <label for="description" class="block mb-2 text-sm font-medium text-gray-900 dark:text-white">คำอธิบาย</label>
        <textarea id="description" rows="5"
                  v-model="comment"
                  class="block p-2.5 w-full text-sm text-gray-900 bg-gray-50 rounded-lg border border-gray-300 focus:ring-primary-500 focus:border-primary-500  "
                  placeholder="กรอกเหตุผลของท่าน..."></textarea>
      </div>


    </template>
    <template #footer>
      <div class="flex justify-between">
        <fwb-button @click="closeModal" color="alternative">
          ยกเลิกการดำเนินการ
        </fwb-button>
        <fwb-button  @click="RejectUserRequest()" color="red">
          ไม่ผ่านการอนุมัติ
        </fwb-button>
      </div>
    </template>
  </fwb-modal>

<!-- modal for edit -->
<fwb-modal v-if="isShowModal2" @close="closeModal2" persistent not-escapable size="3xl">
    <template #header>
      <div class="flex items-center text-lg font-bold">
        ไม่อนุมัติคำขอ - Not Approved RETURN FROM MANAGER
      </div>
    </template>
    <template #body>
      <p class="text-base leading-relaxed text-gray-500 ">
        โปรดระบุเหตุผลที่ไม่อนุมัติคำขอ เพื่อแจ้งให้เจ้าของคำร้องทราบ
      </p>
      <br>

      <div class="sm:col-span-2">
        <label for="description" class="block mb-2 text-sm font-medium text-gray-900 dark:text-white">คำอธิบาย</label>
        <textarea id="description" rows="5"
                  v-model="comment"
                  class="block p-2.5 w-full text-sm text-gray-900 bg-gray-50 rounded-lg border border-gray-300 focus:ring-primary-500 focus:border-primary-500  "
                  placeholder="กรอกเหตุผลของท่าน..."></textarea>
      </div>


    </template>
    <template #footer>
      <div class="flex justify-between">
        <fwb-button @click="closeModal2" color="alternative">
          ยกเลิกการดำเนินการ
        </fwb-button>
        <fwb-button  @click="RejectUserRequest()" color="red">
          ไม่ผ่านการอนุมัติ
        </fwb-button>
      </div>
    </template>
  </fwb-modal>


</template>

<style lang="scss" scoped></style>