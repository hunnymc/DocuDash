<script setup>
import { onMounted, ref } from 'vue'
import axios from "axios";
import Cookies from "js-cookie";
import moment from "moment";
import { useDocumentListStore } from "../../../stores/listOfDocumentStore.js";
import { useRouter } from "vue-router";

let router = useRouter();

let mainURL = "http://localhost:5002";
// let mainURL = "http://cp23kw2.sit.kmutt.ac.th:10003";
// let mainURL = "https://capstone23.sit.kmutt.ac.th/kw2";

const approveList = ref([])
// const approveList = ref(approveListJson)

let user_id = Cookies.get('userId')
let user_role = Cookies.get('role')
let access_token = Cookies.get('accessToken')

const getApproveList = () => {
  axios.get(mainURL + '/api/approve/info/' + user_id, {
    headers: {
      "Authorization": 'Bearer ' + access_token
    }
  })
    .then(function (response) {
      approveList.value = response.data
      // filter only status 2, 3, 5
      approveList.value = approveList.value.filter(item => item.status_type_id === 2 || item.status_type_id === 3 || item.status_type_id === 5)
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
}

const StatusColor = {
  2: "bg-blue-500",
  3: "bg-yellow-300",
  4: "bg-green-500",
  5: "bg-red-500",
}

const clickToViewDoc = async (id) => {

  await useDocumentListStore().getdocumentFilenameAndUserIdFromAxios(id);
  await router.push(`/kw2/approval/detail/user/${id}`)

};

onMounted(() => {
  getApproveList();
})

</script>

<template>

  <div class="w-full relative overflow-x-auto shadow-md sm:rounded-lg">

    <!-- Header -->
    <section class="flex items-center  bg-gray-50 ">
      <div class="w-full   mx-auto ">
        <!-- Start coding here -->
        <div class="relative overflow-hidden bg-white  dark:bg-gray-800 ">
          <div
            class="border border-slate-300 flex-row items-center justify-between p-4 space-y-3 sm:flex sm:space-y-0 sm:space-x-4">
            <div>
              <h5 class="mr-3 font-semibold dark:text-white text-3xl">รายการเอกสารที่ขออนุมัติ</h5>
              <p class="text-gray-500 ">รวมรายการเอกสารและโครงการที่ขออนุมัติทั้งหมด</p>
            </div>
          </div>
        </div>
      </div>
    </section>
    <table class="w-full text-sm text-left rtl:text-right text-gray-500 dark:text-gray-400">
      <thead class="text-xs text-gray-700 uppercase bg-gray-100 dark:bg-gray-700 dark:text-gray-400">
        <tr>
          <th scope="col" class="px-6 py-3">
            ชื่อเรื่อง
          </th>
          <th scope="col" class="px-6 py-3">
            ผู้จัดการที่รับเรื่อง
          </th>
          <th scope="col" class="px-6 py-3">
            สถานะ
          </th>
          <th scope="col" class="px-6 py-3">
            ตรวจสอบสถานะ
          </th>
        </tr>
      </thead>
      <tbody v-if="approveList.length > 0">
        <tr v-for="item in approveList"
          class="bg-white border-b dark:bg-gray-800 dark:border-gray-700 hover:bg-gray-50 dark:hover:bg-gray-600">
          <th scope="row" class="flex items-center px-6 py-4 text-gray-900 whitespace-nowrap dark:text-white">
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
          <td class="px-6 py-4">
            
              <button type="button" @click="clickToViewDoc(item.documentInfo.id)"
                class="font-medium text-blue-600 dark:text-blue-500 hover:underline">ตรวจสอบ</button>
            
          </td>
        </tr>
      </tbody>

      <tbody v-else>
        <tr>
          <td colspan="7" class="text-center text-black">
            <section class="bg-white dark:bg-gray-900">
              <div class="grid max-w-screen-xl px-4 py-8 mx-auto lg:gap-8 xl:gap-0 lg:py-16 lg:grid-cols-12">
                <div class="mr-auto place-self-center lg:col-span-7">
                  <h1
                    class="max-w-2xl mb-4 text-4xl font-extrabold tracking-tight leading-none md:text-5xl xl:text-6xl dark:text-white">
                    ไม่มีเอกสารในระบบ
                  </h1>
                  <p class="max-w-2xl mb-6 font-light text-gray-500 lg:mb-8 md:text-lg lg:text-xl dark:text-gray-400">
                    ขณะนี้ยังไม่มีเอกสารที่ถูกส่งถึงท่าน
                    ท่านสามารถเช็คเอกสารใหม่ได้ผ่านเมนูแจ้งเตือน
                    <span class="text-blue-600">หากมีเอกสารระบบจะทำการแจ้งเตือนอีกครั้ง
                    </span>หรือติดต่อแอดมินเพี่อสอบถามข้อมูลเพิ่มเติม
                  </p>
                  <a href="/kw2/approve/list"
                    class="inline-flex items-center justify-center px-5 py-3 mr-3 text-base font-medium text-center text-white rounded-lg bg-blue-800 hover:bg-blue-900 dark:focus:ring-primary-900">
                    กลับสู่หน้าแรก
                    <svg class="w-5 h-5 ml-2 -mr-1" fill="currentColor" viewBox="0 0 20 20"
                      xmlns="http://www.w3.org/2000/svg">
                      <path fill-rule="evenodd"
                        d="M10.293 3.293a1 1 0 011.414 0l6 6a1 1 0 010 1.414l-6 6a1 1 0 01-1.414-1.414L14.586 11H3a1 1 0 110-2h11.586l-4.293-4.293a1 1 0 010-1.414z"
                        clip-rule="evenodd"></path>
                    </svg>
                  </a>
                  <a href="#"
                    class="inline-flex items-center justify-center px-5 py-3 text-base font-medium text-center text-gray-900 border border-gray-300 rounded-lg hover:bg-gray-100 focus:ring-4 focus:ring-gray-100 dark:text-white dark:border-gray-700 dark:hover:bg-gray-700 dark:focus:ring-gray-800">
                    ติดต่อแอดมิน
                  </a>
                </div>
                <div class="hidden lg:mt-0 lg:col-span-5 lg:flex">
                  <img src="../../../assets/im1.png" alt="mockup" />
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