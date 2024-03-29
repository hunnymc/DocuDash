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
let access_token = Cookies.get('accessToken')

const getApproveList = () => {
  axios.get(mainURL + '/api/approve/mg/' + user_id, {
    headers: {
      "Authorization": 'Bearer ' + access_token
    }
  })
      .then(function (response) {
        approveList.value = response.data
        // filter only status 3
        approveList.value = approveList.value.filter((item) => item.status_type_id === 3)

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
  await router.push(`/kw2/approval/detail/manager/${id}`)

};

function changeUNIXtoDate(unix) {
  return moment.unix(unix).format('DD/MM/YYYY');
}

onMounted(async () => {
  getApproveList();
})

</script>

<template>

  <div class="w-full relative overflow-x-auto shadow-md ">
    <!-- หัวตาราง -->

    <div
        class="bg-slate-300 flex items-center justify-between  md:flex-row flex-wrap space-y-4 md:space-y-0 py-4 bg-white dark:bg-gray-900">
      <div>
        <div class="m-3 mb-6">
          <h5 class="mr-3 font-semibold dark:text-white text-3xl">รายการเอกสารที่ขออนุมัติ</h5>
          <p class="text-gray-500 ">รวมรายการเอกสารและโครงการที่ท่านต้องอนุมัติทั้งหมด</p>
        </div>
        <button id="dropdownActionButton" class="ml-2 inline-flex items-center text-gray-700 bg-white border border-gray-300 focus:outline-none hover:bg-gray-100 focus:ring-4 focus:ring-gray-100 font-medium rounded-lg text-sm px-3 py-1.5 dark:bg-gray-800 dark:text-gray-400 dark:border-gray-600 dark:hover:bg-gray-700 dark:hover:border-gray-600 dark:focus:ring-gray-700"
                data-dropdown-toggle="dropdownAction"
                type="button">
          <span class="sr-only">Action button</span>
          เรียงลำดับ
          <svg aria-hidden="true" class="w-2.5 h-2.5 ms-2.5" fill="none" viewBox="0 0 10 6"
               xmlns="http://www.w3.org/2000/svg">
            <path d="m1 1 4 4 4-4" stroke="currentColor" stroke-linecap="round" stroke-linejoin="round"
                  stroke-width="2"/>
          </svg>
        </button>
        <!-- Dropdown menu -->
        <div id="dropdownAction"
             class="z-10 hidden bg-white divide-y divide-gray-100 rounded-lg shadow w-44 dark:bg-gray-700 dark:divide-gray-600">
          <ul aria-labelledby="dropdownActionButton" class="py-1 text-sm text-gray-700 dark:text-gray-200">
            <li>
              <a class="block px-4 py-2 hover:bg-gray-100 dark:hover:bg-gray-600 dark:hover:text-white" href="#">เวลาที่ยื่น</a>
            </li>
            <li>
              <a class="block px-4 py-2 hover:bg-gray-100 dark:hover:bg-gray-600 dark:hover:text-white" href="#">ประเภทคำร้อง</a>
            </li>
          </ul>
        </div>
      </div>
    </div>


    <table class="w-full text-sm text-left rtl:text-right text-gray-500 dark:text-gray-400">

      <thead class="text-xs text-gray-700 uppercase bg-gray-100 dark:bg-gray-700 dark:text-gray-400">
      <tr>
        <th class="px-6 py-3" scope="col">
          ชื่อเรื่อง
        </th>
        <th class="px-6 py-3" scope="col">
          ผู้ยื่น
        </th>
        <th class="px-6 py-3" scope="col">
          แผนก
        </th>
        <th class="px-6 py-3" scope="col">
          วันที่ยื่น
        </th>
        <th class="px-6 py-3" scope="col">
          ประเภทคำร้อง
        </th>
        <th class="px-6 py-3" scope="col">
          ตรวจสอบรายละเอียด
        </th>
      </tr>
      </thead>

      <tbody>

      <tr v-if="approveList.length > 0"
          v-for="item in approveList"
          class="bg-white border-b dark:bg-gray-800 dark:border-gray-700 hover:bg-gray-50 dark:hover:bg-gray-600">

        <th class="flex items-center px-6 py-4 text-gray-900 whitespace-nowrap dark:text-white" scope="row">
          <div class="ps-3">
            <div class="text-base font-semibold">{{ item.documentInfo.title }}</div>
            <div class="font-normal text-gray-500">{{ item.documentInfo.description }}</div>
          </div>
        </th>

        <td class="px-6 py-4">
          {{ item.documentInfo.usersUserid.fullName }}
        </td>

        <td class="px-6 py-4">
          {{ item.documentInfo.usersUserid.branch }}
        </td>

        <td class="px-6 py-4">
          {{ changeUNIXtoDate(item.documentInfo.dateUpdate) }}
        </td>

        <td v-if="item.approve_type_Id === 1" class="px-6 py-4">
          <div class="flex items-center">
            <span class="text-gray-900 rounded bg-indigo-200 px-2 py-0.5">คำร้องแจ้งให้ทราบ</span>
          </div>
        </td>

        <td v-else-if="item.approve_type_Id === 2" class="px-6 py-4">
          <div class="flex items-center">
            <span class="text-gray-900 rounded bg-orange-200 px-2 py-0.5">คำร้องขออนุมัติเอกสาร</span>
          </div>
        </td>

        <td class="px-6 py-4">
          <button @click="clickToViewDoc(item.documentInfo.id)" class="font-medium text-sm  text-white px-6 py-1 bg-sky-600 rounded-lg hover:bg-sky-800  hover:underline" type="button">ตรวจสอบ</button>
        </td>

      </tr>

      <tr v-if="approveList.length <= 0">
        <td colspan="7" class="text-center text-black">
          <section class="bg-white dark:bg-gray-900">
            <div class="flex items-center justify-center m-4">
              <div class="text-center">
                <h5 class="font-semibold dark:text-white text-3xl">ไม่มีรายการเอกสารที่ขออนุมัติ</h5>
                <p class="text-gray-500 ">ไม่มีรายการเอกสารที่ท่านต้องอนุมัติในขณะนี้</p>
              </div>
            </div>
          </section>
        </td>
      </tr>

<!--      <tr class="bg-white border-b dark:bg-gray-800 dark:border-gray-700 hover:bg-gray-50 dark:hover:bg-gray-600">-->

<!--        <th class="flex items-center px-6 py-4 text-gray-900 whitespace-nowrap dark:text-white" scope="row">-->

<!--          <div class="ps-3">-->
<!--            <div class="text-base font-semibold">ขอทำไข่เจียวปู</div>-->
<!--            <div class="font-normal text-gray-500">ไข่เจียวขึ้นราคาจึงขอทำไข่เจียวปู</div>-->
<!--          </div>-->
<!--        </th>-->
<!--        <td class="px-6 py-4">-->
<!--          อัครเดช สุขสวัสดิ์-->
<!--        </td>-->
<!--        <td class="px-6 py-4">-->
<!--          &lt;!&ndash; เวลา &ndash;&gt;-->
<!--        </td>-->
<!--        <td class="px-6 py-4">-->
<!--          &lt;!&ndash; เวลา &ndash;&gt;-->
<!--        </td>-->
<!--        <td class="px-6 py-4">-->
<!--          <div class="flex items-center">-->
<!--            <span class="text-gray-900 rounded bg-indigo-200 px-2 py-0.5">คำร้องแจ้งให้ทราบ</span>-->
<!--          </div>-->
<!--        </td>-->
<!--        <td class="px-6 py-4">-->
<!--          &lt;!&ndash; Modal toggle &ndash;&gt;-->
<!--          <a class="font-medium text-blue-600 dark:text-blue-500 hover:underline" href="#" type="button">ตรวจสอบ</a>-->
<!--        </td>-->
<!--      </tr>-->
<!--      -->
<!--      <tr class="bg-white border-b dark:bg-gray-800 dark:border-gray-700 hover:bg-gray-50 dark:hover:bg-gray-600">-->

<!--        <th class="flex items-center px-6 py-4 font-medium text-gray-900 whitespace-nowrap dark:text-white" scope="row">-->

<!--          <div class="ps-3">-->
<!--            <div class="text-base font-semibold">หีไก่</div>-->
<!--            <div class="font-normal text-gray-500">ฟหกฟหกฟหกฟหกฟก</div>-->
<!--          </div>-->
<!--        </th>-->
<!--        <td class="px-6 py-4">-->
<!--          อัครเดช สุขสวัสดิ์-->
<!--        </td>-->
<!--        <td class="px-6 py-4">-->
<!--          &lt;!&ndash; เวลา &ndash;&gt;-->
<!--        </td>-->
<!--        <td class="px-6 py-4">-->
<!--          &lt;!&ndash; เวลา &ndash;&gt;-->
<!--        </td>-->

<!--        <td class="px-6 py-4">-->
<!--          <div class="flex items-center">-->
<!--            <span class="text-gray-900 rounded bg-orange-200 px-2 py-0.5">คำร้องขออนุมัติเอกสาร</span>-->
<!--          </div>-->
<!--        </td>-->

<!--        <td class="px-6 py-4">-->
<!--          &lt;!&ndash; Modal toggle &ndash;&gt;-->
<!--          <a class="font-medium text-blue-600 dark:text-blue-500 hover:underline" href="#" type="button">ตรวจสอบ</a>-->
<!--        </td>-->
<!--      </tr>-->


      </tbody>
    </table>

  </div>

</template>

<style scoped>

</style>