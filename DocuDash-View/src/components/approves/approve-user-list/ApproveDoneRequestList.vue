<script setup>
import {onBeforeMount, onMounted, ref} from 'vue'
import axios from "axios";
import Cookies from "js-cookie";
import {useDocumentListStore} from "../../../stores/listOfDocumentStore.js";
import {useRouter} from "vue-router";

let mainURL = import.meta.env.VITE_API_URL;

// let mainURL = "http://localhost:5002";
// let mainURL = "http://cp23kw2.sit.kmutt.ac.th:10003";
// let mainURL = "https://capstone23.sit.kmutt.ac.th/kw2";

let router = useRouter();

// const approveList = ref([])
const approveList = ref([
    // {
  // "status_type_id": "status_type_id",
  // "totalManagers": "totalManagers",
  // "totalManagersWhoVerified": "totalManagersWhoVerified",
  // "approve_type_Id": "approve_type_Id",
  // "sentToUser": "sentToUser",
  // "managersWhoVerified": [{
  //   "id": "id",
  //   "isPass": "isPass",
  //   "comment": "comment",
  //   "documentID": "documentID",
  //   "managerID": {
  //     "id": "id",
  //     "username": "username",
  //     "password": "password",
  //     "fullName": "fullName",
  //     "role": "role",
  //     "email": "email",
  //     "phone": "phone",
  //     "branch": "branch"
  //   }
  // }],
  // "documentInfo": {
  //   "id": "id",
  //   "title": "title",
  //   "filePath": "filePath",
  //   "dateAdd": "dateAdd",
  //   "dateUpdate": "dateUpdate",
  //   "category": "category",
  //   "secrecyLevel": "secrecyLevel",
  //   "urgency": "urgency",
  //   "fromSource": "fromSource",
  //   "branchSource": "branchSource",
  //   "emailSource": "emailSource",
  //   "phoneSource": "phoneSource",
  //   "status": "status",
  //   "description": "description",
  //   "usersUserid": {
  //     "id": "id",
  //     "username": "username",
  //     "fullName": "fullName",
  //     "role": "role",
  //     "email": "email",
  //     "phone": "phone",
  //     "branch": "branch"
  //   }
  // }
// }, {
//   "status_type_id": 4,
//   "totalManagers": 1,
//   "totalManagersWhoVerified": 1,
//   "approve_type_Id": 1,
//   "sentToUser": 1,
//   "managersWhoVerified": [{
//     "id": 95,
//     "isPass": 1,
//     "comment": null,
//     "documentID": null,
//     "managerID": {
//       "id": 3,
//       "username": "กันยา",
//       "password": null,
//       "fullName": "กันยา นาปี",
//       "role": "MANAGER",
//       "email": "user3@example.com",
//       "phone": "0299999999",
//       "branch": "ฝ่ายผลิต"
//     }
//   }],
//   "documentInfo": {
//     "id": 476,
//     "title": "test type 1",
//     "filePath": "Wk1_Orientation_Thai.pdf",
//     "dateAdd": 1711735720,
//     "dateUpdate": 1711735720,
//     "category": "เอกสารภายใน",
//     "secrecyLevel": "ลับ",
//     "urgency": "ปกติ",
//     "fromSource": "นภา ฟ้าสวย",
//     "branchSource": "งานเอกสาร",
//     "emailSource": "user1@example.com",
//     "phoneSource": "0812345678",
//     "status": 4,
//     "description": "test",
//     "usersUserid": {
//       "id": 1,
//       "username": "นภา",
//       "fullName": "นภา ฟ้าสวย",
//       "role": "ADMIN",
//       "email": "user1@example.com",
//       "phone": "0981111111",
//       "branch": "งานเอกสาร"
//     }
//   }
// }
])

let user_id = Cookies.get('userId')
let user_role = Cookies.get('role')
let access_token = Cookies.get('accessToken')

const getApproveList = async () => {
  await axios.get(mainURL + '/api/approve/info/' + user_id, {
    headers: {
      "Authorization": 'Bearer ' + access_token
    }
  })
      .then(function (response) {
        approveList.value = response.data
        // filter only status 4
        approveList.value = approveList.value.filter(item => item.status_type_id === 4)
      })
      .catch(function (error) {
        console.log(error);
      })
}

const StatusName = {
  2: "รอการตรวจสอบจากผู้ดูแล",
  3: "รออนุมัติ",
  4: "อนุมัติแล้ว",
  5: "ไม่ผ่านการอนุมัติ",
  6: "ไม่ผ่านการอนุมัติ",
}

const StatusColor = {
  2: "bg-blue-500",
  3: "bg-yellow-300",
  4: "bg-green-500",
  5: "bg-red-500",
  6: "bg-red-500",
}

const sentDocToUserButton = async (documentID) => {
 if ( confirm("คุณต้องการส่งเอกสารนี้ให้ผู้ใช้งานหรือไม่?")) {
   axios.post(mainURL + '/api/approve/sent-doc?documentID=' + documentID,
       { documentID: documentID  },
       { headers: { "Authorization": 'Bearer ' + access_token }
       })
       .then(function (response) {
         alert('ส่งเอกสารเรียบร้อยแล้ว');
         getApproveList();
       })
       .catch(function (error) {
         alert('เกิดข้อผิดพลาดในการส่งเอกสาร กรุณาลองใหม่อีกครั้ง' +
             'หรือติดต่อแอดมินเพื่อแก้ไขปัญหา' +
             'ข้อความผิดพลาด: ' + error)
       })
 }
};

const clickToViewDoc = async (id) => {

  await axios.post(mainURL + '/api/approve/read/user/' + id, {
    user_id: user_id
  }, {
    headers: {
      "Authorization": 'Bearer ' + access_token
    }
  })

  await useDocumentListStore().getdocumentFilenameAndUserIdFromAxios(id);
  await router.push(`/kw2/approval/detail/user/${id}`)

};

onBeforeMount(async () => {
  await getApproveList();
  approveList.value.sort((a, b) => new Date(b.documentInfo.dateUpdate) - new Date(a.documentInfo.dateUpdate))
})

// onMounted(async () => {
//   await getApproveList();
//   approveList.value.sort((a, b) => new Date(b.documentInfo.dateUpdate) - new Date(a.documentInfo.dateUpdate))
// })

</script>

<template>

  <div class="w-full relative overflow-x-auto shadow-md sm:rounded-lg">

    <!-- Header -->
    <section class="flex items-center  bg-gray-50 ">
      <div class="w-full   mx-auto ">
        <div class="relative overflow-hidden bg-white  dark:bg-gray-800 ">
          <div
              class="border border-slate-300 flex-row items-center justify-between p-4 space-y-3 sm:flex sm:space-y-0 sm:space-x-4">
            <div>
              <h5 class="mr-3 font-semibold dark:text-white text-3xl">คำร้องที่ผ่านการอนุมัติแล้วทั้งหมด</h5>
              <p class="text-gray-500 ">รวมรายการเอกสารและโครงการที่ขออนุมัติทั้งหมด</p>
            </div>
          </div>
        </div>
      </div>
    </section>
    <table class="w-full text-sm text-left rtl:text-right text-gray-500 dark:text-gray-400">
      <thead class="text-xs text-gray-700 uppercase bg-gray-100 dark:bg-gray-700 dark:text-gray-400">
      <tr>
        <th class="py-3" scope="col">
          <!-- จุดแดง  -->
        </th>
        <th class="px-6 py-3" scope="col">
          ชื่อเรื่อง
        </th>
        <th class="px-6 py-3" scope="col">
          ผู้จัดการที่รับเรื่อง
        </th>
        <th class="px-6 py-3" scope="col">
          สถานะ
        </th>
        <th class="px-6 py-3" scope="col">
          ประเภทคำร้อง
        </th>
        <th class="px-6 py-3" scope="col">
          ตรวจสอบสถานะ
        </th>
        <th class="px-6 py-3" scope="col">
          ส่งต่อคำร้อง
        </th>
      </tr>
      </thead>
      <tbody v-if="approveList.length > 0">
      <tr
          v-for="item in approveList"
          class="bg-white border-b dark:bg-gray-800 dark:border-gray-700 hover:bg-gray-50 dark:hover:bg-gray-600">
        <td class="pl-4 items-center justify-center">
          <div v-if="item.isOwnerRead === 0" class="w-2 h-2 bg-red-500 border border-white rounded-full"></div>
        </td>
        <th class="flex items-center py-4 text-gray-900 whitespace-nowrap dark:text-white" scope="row">
          <div class="ps-3">
            <div class="text-base font-semibold">{{ item.documentInfo.title }}</div>
            <div class="font-normal text-gray-500">{{ item.documentInfo.description }}</div>
          </div>
        </th>
        <td class="px-6 py-4">
          {{ item.managersWhoVerified.map(manager => manager.managerID.fullName).join(', ') }}
        </td>
        <td class="px-6 py-4">
          <div class="flex items-center">
            <div :class="StatusColor[item.status_type_id] + ' h-2.5 w-2.5 rounded-full me-2'"></div>
            {{ StatusName[item.status_type_id] }}
          </div>
        </td>

        <td v-if="item.approve_type_Id === 1" class="px-6 py-4">
          <div class="flex items-center">
            <span class="text-gray-900 rounded bg-orange-200 px-2 py-0.5">คำร้องขออนุมัติเอกสาร</span>
          </div>
        </td>

        <td v-if="item.approve_type_Id === 2" class="px-6 py-4">
          <div class="flex items-center">
            <span class="text-gray-900 rounded bg-indigo-200 px-2 py-0.5">คำร้องแจ้งให้ทราบ</span>
          </div>
        </td>

        <td class="px-6 py-4">
          <button class="font-medium text-blue-600 dark:text-blue-500 hover:underline" type="button"
                  @click="clickToViewDoc(item.documentInfo.id)">ตรวจสอบ
          </button>
        </td>

        <!--   สำหรับคำร้องขออนุมัติเอกสาร แบบที่ 1   -->
        <td v-if="item.approve_type_Id === 1 && item.sentToUser === 0" class="px-6 py-4">
          <button @click="sentDocToUserButton(item.documentInfo.id)" class="cursor-pointer inline-flex hover:bg-blue-800   px-6 py-1   text-sm  text-white text-center items-center bg-blue-600 rounded-lg "
                  type="button">
            ส่ง
            <svg class="pl-2 w-8 h-6" fill="none" viewBox="0 0 24 24" xmlns="http://www.w3.org/2000/svg">
              <g id="SVGRepo_bgCarrier" stroke-width="0"></g>
              <g id="SVGRepo_tracerCarrier" stroke-linecap="round" stroke-linejoin="round"></g>
              <g id="SVGRepo_iconCarrier">
                <path
                    d="M20.7639 12H10.0556M3 8.00003H5.5M4 12H5.5M4.5 16H5.5M9.96153 12.4896L9.07002 15.4486C8.73252 16.5688 8.56376 17.1289 8.70734 17.4633C8.83199 17.7537 9.08656 17.9681 9.39391 18.0415C9.74792 18.1261 10.2711 17.8645 11.3175 17.3413L19.1378 13.4311C20.059 12.9705 20.5197 12.7402 20.6675 12.4285C20.7961 12.1573 20.7961 11.8427 20.6675 11.5715C20.5197 11.2598 20.059 11.0295 19.1378 10.5689L11.3068 6.65342C10.2633 6.13168 9.74156 5.87081 9.38789 5.95502C9.0808 6.02815 8.82627 6.24198 8.70128 6.53184C8.55731 6.86569 8.72427 7.42461 9.05819 8.54246L9.96261 11.5701C10.0137 11.7411 10.0392 11.8266 10.0493 11.9137C10.0583 11.991 10.0582 12.069 10.049 12.1463C10.0387 12.2334 10.013 12.3188 9.96153 12.4896Z"
                    stroke="#ffffff" stroke-linecap="round" stroke-linejoin="round" stroke-width="2"></path>
              </g>
            </svg>
          </button>
        </td>

        <!--   สำหรับคำร้องแข้งให้ทราบ แบบที่ 2   -->
        <td v-else-if="item.approve_type_Id === 2 || item.sentToUser === 1" class="px-6 py-4">
          <a class="font-normal text-gray-500 text-center items-center"> เอกสารถูกส่งแล้ว </a>
        </td>

        <!--    สำหรับคำร้องขออนุมัติเอกสาร แบบที่ 1 แต่ส่งไปหาผู้ใช้งานแล้ว    -->
        <td v-else-if="item.sentToUser === 1" class="px-6 py-4">
          <a class="font-normal text-gray-500 text-center items-center"> เอกสารถูกส่งแล้ว </a>
        </td>

      </tr>
      </tbody>

      <tbody v-else>
      <tr>
        <td class="text-center text-black" colspan="7">
          <section class="bg-white dark:bg-gray-900">
            <div class="grid max-w-screen-xl px-4 py-8 mx-auto lg:gap-8 xl:gap-0 lg:py-16 lg:grid-cols-12">
              <div class="mr-auto place-self-center lg:col-span-7">
                <h1
                    class="max-w-2xl mb-4 text-4xl font-extrabold tracking-tight leading-none md:text-5xl xl:text-6xl dark:text-white">
                  ไม่มีเอกสารที่ผ่านการอนุมัติ
                </h1>
                <p class="max-w-2xl mb-6 font-light text-gray-500 lg:mb-8 md:text-lg lg:text-xl dark:text-gray-400">
                  ขณะนี้ยังไม่มีเอกสารที่ผ่านการอนุมัติในระบบ
                  กรุณาตรวจสอบอีกครั้งในภายหลัง
                  <span class="text-blue-600">หากมีเอกสารระบบจะทำการแจ้งเตือนอีกครั้ง
                    </span>หรือติดต่อแอดมินเพี่อสอบถามข้อมูลเพิ่มเติม
                </p>
                <a class="inline-flex items-center justify-center px-5 py-3 mr-3 text-base font-medium text-center text-white rounded-lg bg-blue-800 hover:bg-blue-900 dark:focus:ring-primary-900"
                   href="/kw2/menu">
                  กลับสู่หน้าแรก
                  <svg class="w-5 h-5 ml-2 -mr-1" fill="currentColor" viewBox="0 0 20 20"
                       xmlns="http://www.w3.org/2000/svg">
                    <path clip-rule="evenodd"
                          d="M10.293 3.293a1 1 0 011.414 0l6 6a1 1 0 010 1.414l-6 6a1 1 0 01-1.414-1.414L14.586 11H3a1 1 0 110-2h11.586l-4.293-4.293a1 1 0 010-1.414z"
                          fill-rule="evenodd"></path>
                  </svg>
                </a>
                <a class="inline-flex items-center justify-center px-5 py-3 text-base font-medium text-center text-gray-900 border border-gray-300 rounded-lg hover:bg-gray-100 focus:ring-4 focus:ring-gray-100 dark:text-white dark:border-gray-700 dark:hover:bg-gray-700 dark:focus:ring-gray-800"
                   href="#">
                  ติดต่อแอดมิน
                </a>
              </div>
              <div class="hidden lg:mt-0 lg:col-span-5 lg:flex">
                <img alt="mockup" src="../../../assets/im1.png"/>
              </div>
            </div>
          </section>
        </td>
      </tr>
      </tbody>
    </table>
  </div>
</template>

<style scoped></style>