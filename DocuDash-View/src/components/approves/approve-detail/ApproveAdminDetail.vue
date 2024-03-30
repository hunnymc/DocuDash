<script setup>
import {onMounted, ref, watch} from "vue";
import {useRoute, useRouter} from "vue-router";
import {useDocumentListStore} from "../../../stores/listOfDocumentStore.js";
import axios from "axios";
import Cookies from "js-cookie";

const route = useRoute();
const router = useRouter();
let isEverythingLoaded = ref(false);

const store = useDocumentListStore();

let mainURL= import.meta.env.VITE_API_URL;

// let mainURL = "http://localhost:5002";
// let mainURL = "http://cp23kw2.sit.kmutt.ac.th:10003";
// let mainURL = "https://capstone23.sit.kmutt.ac.th/kw2";
// let mainURL = "https://capstone23.sit.kmutt.ac.th/kw2";

let fileUrl = ref();

let doc = ref({
  "status_type_id": 2,
  "totalManagers": 2,
  "totalManagersWhoVerified": 0,
  "approve_type_Id": 1,
  "managersWhoVerified": [{
    "id": 74,
    "isPass": 0,
    "comment": null,
    "documentID": null,
    "managerID": {
      "id": 17,
      "username": "สันชัย",
      "password": null,
      "fullName": "สันชัย ปราการ",
      "role": "MANAGER",
      "email": "qwe@asd.com",
      "phone": null,
      "branch": "ฝ่ายบุคคล"
    }
  }, {
    "id": 75,
    "isPass": 0,
    "comment": null,
    "documentID": null,
    "managerID": {
      "id": 3,
      "username": "กันยา",
      "password": null,
      "fullName": "กันยา นาปี",
      "role": "MANAGER",
      "email": "user3@example.com",
      "phone": "0299999999",
      "branch": "ฝ่ายผลิต"
    }
  }],
  "documentInfo": {
    "id": 455,
    "title": "test title",
    "filePath": "wk2-4_เกณฑ์การประเมิน+Leader+as+a+Role+Model.pdf",
    "dateAdd": 1711554033,
    "dateUpdate": 1711554033,
    "category": "เอกสารภายใน",
    "secrecyLevel": "ลับ",
    "urgency": "ปกติ",
    "fromSource": "กันยา นาปี",
    "branchSource": "ฝ่ายผลิต",
    "emailSource": "user3@example.com",
    "phoneSource": "0812345678",
    "status": 2,
    "description": "test",
    "usersUserid": {
      "id": 3,
      "username": "กันยา",
      "fullName": "กันยา นาปี",
      "role": "MANAGER",
      "email": "user3@example.com",
      "phone": "0299999999",
      "branch": "ฝ่ายผลิต"
    }
  }
});

let doc_id = ref();

const ClickFile = () => {
  console.log("ClickFile");
  console.log("fileUrl: ", fileUrl);
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

function ClicktoApprove(isPass) {
  confirm("คุณต้องการนุมัติคำขอนี้ใช่หรือไม่?");
  axios.post(mainURL + "/api/approve/admin/check-approve",
      {
        "document_id": doc_id.value,
        "is_pass": isPass
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
        alert("เกิดข้อผิดพลาดบางอย่าง");
      });

}

const ApproveType = {
  1: "คำร้องแจ้งให้ทราบ",
  2: "คำร้องขออนุมัติเอกสาร"
}

onMounted(async () => {
  await getDocById();
  if (store.getDocumentUserId == null || store.getDocumentFilename == null) {
    router.go(-1)
  }
  isEverythingLoaded.value = true;

  fileUrl = mainURL + "/api/files/" + doc.value.documentInfo.usersUserid.id + "/" + doc.value.documentInfo.filePath;
  console.log(fileUrl);
});

watch(() => route.params.id, async () => {
      await getDocById();
      isEverythingLoaded.value = true;
    }
);

</script>

<template>
  <div class="shadow-md sm:rounded-lg w-full ">
    <section class="bg-gray-200 dark:bg-gray-900 ">
      <div class="py-8 px-auto ml-5 mr-auto  lg:py-12 w-full  max-width: 100%  justify-center ">
        <h2 class="mb-4 text-4xl font-bold text-gray-900 dark:text-white"> รายละเอียดคำขออนุมัติ
        </h2>

        <div class=" flex justify-center items-center w-full">

          <ol class="mt-4 mr-36 bg-gray-50 rounded-lg  px-4 p-4 flex justify-center items-center w-1/2 text-sm font-medium text-center text-gray-500  sm:text-base">
            <li class="flex md:w-full items-center text-blue-600 dark:text-blue-500 sm:after:content-[''] after:w-full after:h-1 after:border-b after:border-blue-600 after:border-1 after:hidden sm:after:inline-block after:mx-6 xl:after:mx-10 dark:after:border-gray-700">
        <span class="flex items-center  sm:after:hidden  after:text-gray-200 dark:after:text-gray-500">
            <svg aria-hidden="true" class="w-3.5 h-3.5 sm:w-4 sm:h-4 me-2.5" fill="currentColor"
                 viewBox="0 0 20 20" xmlns="http://www.w3.org/2000/svg">
                <path
                    d="M10 .5a9.5 9.5 0 1 0 9.5 9.5A9.51 9.51 0 0 0 10 .5Zm3.707 8.207-4 4a1 1 0 0 1-1.414 0l-2-2a1 1 0 0 1 1.414-1.414L9 10.586l3.293-3.293a1 1 0 0 1 1.414 1.414Z"/>
            </svg>
            Submit<span class="hidden sm:inline-flex sm:ms-2">request</span>
        </span>
            </li>
            <li class="flex md:w-full items-center after:content-[''] after:w-full after:h-1 after:border-b after:border-gray-500 after:border-1 after:hidden sm:after:inline-block after:mx-6 xl:after:mx-10 dark:after:border-gray-700">
        <span
            class="flex items-center text-blue-600 after:content-['/'] sm:after:hidden after:mx-2 after:text-gray-200 dark:after:text-gray-500">
            <svg aria-hidden="true" class="w-3.5 h-3.5 sm:w-4 sm:h-4 me-2.5" fill="currentColor"
                 viewBox="0 0 20 20" xmlns="http://www.w3.org/2000/svg">
                <path
                    d="M10 .5a9.5 9.5 0 1 0 9.5 9.5A9.51 9.51 0 0 0 10 .5Zm3.707 8.207-4 4a1 1 0 0 1-1.414 0l-2-2a1 1 0 0 1 1.414-1.414L9 10.586l3.293-3.293a1 1 0 0 1 1.414 1.414Z"/>
            </svg>
            Admin  <span class="hidden sm:inline-flex sm:ms-2">checks</span>
        </span>
            </li>
            <li class="flex items-center">
              <span class="me-2">3</span>
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
              <h3 class="text-sm font-bold text-gray-900 dark:text-white ">{{ doc.documentInfo.title  }}</h3>

            </div>
          </div>
          <div class="w-full ">
            <label class="block mb-2 text-sm font-bold text-gray-900 dark:text-white">ผู้ยื่นขออนุมัติ</label>
            <div
                class="block w-5/6 p-2.5 bg-gray-50 border border-gray-300 text-sm text-gray-500 dark:text-gray-400 dark:bg-gray-800 rounded-lg ">
              <h3 class="text-sm font-bold text-gray-900 dark:text-white ">{{ doc.documentInfo.usersUserid.fullName }}</h3>
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
            <label class="block mb-2 text-sm font-bold text-gray-900 dark:text-white"
                   for="category">แผนก</label>
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
              <h3 class="text-sm font-bold text-gray-900 dark:text-white ">{{ ApproveType[doc.status_type_id] }}</h3>

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
            <label class="block mb-2 text-sm font-bold text-gray-900 dark:text-white"
                   for="category">ชั้นความลับ</label>
            <div
                class="block w-5/6 p-2.5 bg-gray-50 border border-gray-300 text-sm text-gray-500 dark:text-gray-400 dark:bg-gray-800 rounded-lg ">
              <h3 class="text-sm font-bold text-gray-900 dark:text-white ">{{ doc.documentInfo.secrecyLevel }}</h3>

            </div>
          </div>


          <div>
            <label class="block mb-2 text-sm font-bold text-gray-900 dark:text-white"
                   for="category"> *
              รายชื่อผู้จัดการที่รออนุมัติ</label>
            <div
                class="block w-5/6 p-2.5 bg-gray-50 border border-gray-300 text-sm text-gray-500 dark:text-gray-400 dark:bg-gray-800 rounded-lg ">
              <h3 class="text-sm font-bold text-gray-900 dark:text-white ">{{ doc.managersWhoVerified.map(manager => manager.managerID.fullName).join(', ') }}</h3>

            </div>
          </div>

          <div>

          </div>
          <br>

          <!-- ที่แสดงไฟล์ -->
          <div class="w-full">
            <hr class="flex mx-auto w-full mb-2 border-slate-400"/>
            <a class="mb-3 font-bold text-lg text-teal-700">ไฟล์แนบคำขออนุมัติ</a><br><br>
            <div class="  rounded-lg shadow-md w-5/6">
              <table
                  class="rounded-lg w-full text-sm text-left rtl:text-right text-gray-500 dark:text-gray-400">
                <thead
                    class="text-xs text-gray-700 uppercase bg-gray-50 dark:bg-gray-700 dark:text-gray-400">
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
                <tr
                    class="bg-white border-b dark:bg-gray-800 dark:border-gray-700 hover:bg-gray-50 dark:hover:bg-gray-600">
                  <td class="px-6 py-4 text-blue-600 dark:text-blue-500 underline cursor-pointer"
                      @click="ClickFile">
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


            <!-- กล่องค้นหา -->
            <!-- <div  class="w-full block w-5/6 z-10 block bg-white rounded-lg shadow  dark:bg-gray-700">
                <div class="p-3 ">
                  <label  class="sr-only">Search</label>
                  <div class="">
                    <div class=" relative items-center ps-3 pointer-events-none">
                      <svg class="transform translate-y-3 top-1/2 absolute w-4 h-4 text-gray-600 " aria-hidden="true" xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 20 20">
                        <path stroke="currentColor" stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="m19 19-4-4m0-7A7 7 0 1 1 1 8a7 7 0 0 1 14 0Z"/>
                    </svg>
                    </div>
                    <input type="text" id="input-group-search" class="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full ps-10 p-2.5  dark:bg-gray-600 dark:border-gray-500 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500" placeholder="ค้นหารายชื่อผู้ตรวจสอบขั้นตอนต่อไป">
                  </div>
                </div>
                <ul class="h-48 px-3 pb-3 overflow-y-auto text-sm text-gray-700 dark:text-gray-200" >
                  <li>
                    <div class="flex items-center p-2 rounded hover:bg-gray-100 dark:hover:bg-gray-600">
                      <input id="checkbox-item-11" type="checkbox" value="" class="w-4 h-4 text-blue-600 bg-gray-100 border-gray-300 rounded focus:ring-blue-500 dark:focus:ring-blue-600 dark:ring-offset-gray-700 dark:focus:ring-offset-gray-700 focus:ring-2 dark:bg-gray-600 dark:border-gray-500">
                      <label for="checkbox-item-11" class="w-full ms-2 text-sm font-medium text-gray-900 rounded dark:text-gray-300">Bonnie Green</label>
                    </div>
                  </li>
                  <li>
                    <div class="flex items-center p-2 rounded hover:bg-gray-100 dark:hover:bg-gray-600">
                        <input checked id="checkbox-item-12" type="checkbox" value="" class="w-4 h-4 text-blue-600 bg-gray-100 border-gray-300 rounded focus:ring-blue-500 dark:focus:ring-blue-600 dark:ring-offset-gray-700 dark:focus:ring-offset-gray-700 focus:ring-2 dark:bg-gray-600 dark:border-gray-500">
                        <label for="checkbox-item-12" class="w-full ms-2 text-sm font-medium text-gray-900 rounded dark:text-gray-300">Jese Leos</label>
                      </div>
                  </li>
                  <li>
                    <div class="flex items-center p-2 rounded hover:bg-gray-100 dark:hover:bg-gray-600">
                      <input id="checkbox-item-13" type="checkbox" value="" class="w-4 h-4 text-blue-600 bg-gray-100 border-gray-300 rounded focus:ring-blue-500 dark:focus:ring-blue-600 dark:ring-offset-gray-700 dark:focus:ring-offset-gray-700 focus:ring-2 dark:bg-gray-600 dark:border-gray-500">
                      <label for="checkbox-item-13" class="w-full ms-2 text-sm font-medium text-gray-900 rounded dark:text-gray-300">Michael Gough</label>
                    </div>
                  </li>
                  <li>
                    <div class="flex items-center p-2 rounded hover:bg-gray-100 dark:hover:bg-gray-600">
                      <input id="checkbox-item-14" type="checkbox" value="" class="w-4 h-4 text-blue-600 bg-gray-100 border-gray-300 rounded focus:ring-blue-500 dark:focus:ring-blue-600 dark:ring-offset-gray-700 dark:focus:ring-offset-gray-700 focus:ring-2 dark:bg-gray-600 dark:border-gray-500">
                      <label for="checkbox-item-14" class="w-full ms-2 text-sm font-medium text-gray-900 rounded dark:text-gray-300">Robert Wall</label>
                    </div>
                  </li>
                  <li>
                    <div class="flex items-center p-2 rounded hover:bg-gray-100 dark:hover:bg-gray-600">
                      <input id="checkbox-item-15" type="checkbox" value="" class="w-4 h-4 text-blue-600 bg-gray-100 border-gray-300 rounded focus:ring-blue-500 dark:focus:ring-blue-600 dark:ring-offset-gray-700 dark:focus:ring-offset-gray-700 focus:ring-2 dark:bg-gray-600 dark:border-gray-500">
                      <label for="checkbox-item-15" class="w-full ms-2 text-sm font-medium text-gray-900 rounded dark:text-gray-300">Joseph Mcfall</label>
                    </div>
                  </li>
                  <li>
                    <div class="flex items-center p-2 rounded hover:bg-gray-100 dark:hover:bg-gray-600">
                      <input id="checkbox-item-16" type="checkbox" value="" class="w-4 h-4 text-blue-600 bg-gray-100 border-gray-300 rounded focus:ring-blue-500 dark:focus:ring-blue-600 dark:ring-offset-gray-700 dark:focus:ring-offset-gray-700 focus:ring-2 dark:bg-gray-600 dark:border-gray-500">
                      <label for="checkbox-item-16" class="w-full ms-2 text-sm font-medium text-gray-900 rounded dark:text-gray-300">Leslie Livingston</label>
                    </div>
                  </li>
                        <li>
                    <div class="flex items-center p-2 rounded hover:bg-gray-100 dark:hover:bg-gray-600">
                      <input id="checkbox-item-17" type="checkbox" value="" class="w-4 h-4 text-blue-600 bg-gray-100 border-gray-300 rounded focus:ring-blue-500 dark:focus:ring-blue-600 dark:ring-offset-gray-700 dark:focus:ring-offset-gray-700 focus:ring-2 dark:bg-gray-600 dark:border-gray-500">
                      <label for="checkbox-item-17" class="w-full ms-2 text-sm font-medium text-gray-900 rounded dark:text-gray-300">Roberta Casas</label>
                    </div>
                  </li>
                </ul>

            </div> -->

            <!-- ปุ่ม -->
            <div class="flex gap-4 grid-rows-8 sm:grid-cols-2 sm:gap-6">

              <ul class="grid w-full gap-6 md:grid-cols-2">
                <li>
                  <div
                      class="inline-flex items-center justify-between w-full p-5 text-white bg-green-600 border border-gray-200 rounded-lg cursor-pointer dark:hover:text-gray-300 dark:border-gray-700 dark:peer-checked:text-blue-500 peer-checked:border-blue-600 peer-checked:text-blue-600 hover:text-gray-100 hover:bg-green-700 dark:text-gray-400 dark:bg-gray-800 dark:hover:bg-gray-700"
                      @click="ClicktoApprove(1)">
                    <div class="block">
                      <div class="w-full text-lg font-semibold">อนุมัติคำขอ</div>
                      <div class="w-full">อนุมัติคำร้องของผู้ใช้เพื่อดำเนินการต่อ</div>
                    </div>
                    <svg aria-hidden="true" class="w-5 h-5 ms-3 rtl:rotate-180" fill="none"
                         viewBox="0 0 14 10" xmlns="http://www.w3.org/2000/svg">
                      <path d="M1 5h12m0 0L9 1m4 4L9 9" stroke="currentColor" stroke-linecap="round"
                            stroke-linejoin="round"
                            stroke-width="2"/>
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
                    <svg class="w-5 h-5 ms-3 rtl:rotate-180" fill="#000000" version="1.1"
                         viewBox="0 0 512 512" xmlns="http://www.w3.org/2000/svg"
                         xmlns:xlink="http://www.w3.org/1999/xlink">
                      <g id="SVGRepo_bgCarrier" stroke-width="0"></g>
                      <g id="SVGRepo_tracerCarrier" stroke-linecap="round" stroke-linejoin="round"></g>
                      <g id="SVGRepo_iconCarrier"><title>cancel</title>
                        <g id="Page-1" fill="none" fill-rule="evenodd" stroke="none" stroke-width="1">
                          <g id="work-case" fill="#ffffff" transform="translate(91.520000, 91.520000)">
                            <polygon id="Close"
                                     points="328.96 30.2933333 298.666667 1.42108547e-14 164.48 134.4 30.2933333 1.42108547e-14 1.42108547e-14 30.2933333 134.4 164.48 1.42108547e-14 298.666667 30.2933333 328.96 164.48 194.56 298.666667 328.96 328.96 298.666667 194.56 164.48"></polygon>
                          </g>
                        </g>
                      </g>
                    </svg>
                  </div>
                </li>
              </ul>

            </div>


          </div>


        </div>
        <br>
      </div>
      <!-- Click to watch file -->
      <div>
        <!-- <button @click="showPDF = true">Show PDF</button> -->
        <div
            class="border-slate-300 bg-gray-700 w-full rounded-lg shadow-md pdf-container flex items-center justify-center py-10">
          <!-- <VuePDF ref="pdf" :pdf="pdf" :page="page" :scale="scale" /> -->
          <embed :src="mainURL + '/api/files/' + store.getDocumentUserId + '/' + store.getDocumentFilename"
                 height="400px" type="application/pdf" width="100%"/>
        </div>
      </div>

    </section>

  </div>
</template>

<style lang="scss" scoped>

</style>