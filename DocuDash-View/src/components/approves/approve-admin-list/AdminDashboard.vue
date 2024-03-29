<script setup>
import {onMounted, ref} from "vue";
import {chartData} from "./chartdata.js";
import axios from "axios";
import moment from "moment";
import Cookies from "js-cookie";
import router from "../../../router/index.js";
import {useDocumentListStore} from "../../../stores/listOfDocumentStore.js";

let mainURL = "http://localhost:5002";
// let mainURL = "http://cp23kw2.sit.kmutt.ac.th:10003";
// let mainURL = "https://capstone23.sit.kmutt.ac.th/kw2";

const series = chartData.series;
const chartOptions = chartData.chartOptions;
const series_pie = chartData.series_pie;
const chartOptions_pie = chartData.chartOptions_pie;

const approveList = ref([])
// const approveList = ref(approveListJson)

let user_id = Cookies.get('userId')
let user_role = Cookies.get('role')
let access_token = Cookies.get('accessToken')

const getApproveList = () => {
  axios.get(mainURL + '/api/approve/info/all', {
    headers: {
      "Authorization": 'Bearer ' + access_token
    }
  })
      .then(function (response) {
        approveList.value = response.data
        approveList.value = filterApproveList();
      })
      .catch(function (error) {
        console.log(error);
      })
};

function filterApproveList() {
  return approveList.value.filter(item => item.status_type_id === 2);
}

async function clickToViewDoc(id) {
  await useDocumentListStore().getdocumentFilenameAndUserIdFromAxios(id);
  await router.push(`/kw2/approval/detail/admin/${id}`);
}

onMounted(() => {
  getApproveList();
});

</script>

<template>
  <main class="w-full relative overflow-x-auto p-4  h-auto ">
    <div class="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-2 gap-4 mb-4">

      <!-- กราฟยึกยักด้านซ้าย -->
      <div
          class="w-full relative overflow-x-auto border-2 border-solid border-gray-300 rounded-lg dark:border-gray-600 h-44 md:h-64"
      >

        <div class="w-full relative overflow-x-auto  bg-white rounded-lg shadow dark:bg-gray-800 p-4 md:p-6">
          <div class="flex justify-between">
            <div>
              <h5 class="leading-none text-3xl font-bold text-gray-900 dark:text-white pb-2">15 คำร้องใหม่</h5>
              <p class="text-base font-normal text-gray-500 dark:text-gray-400">ในสัปดาห์นี้</p>
            </div>
            <div
                class="flex items-center px-2.5 py-0.5 text-base font-semibold text-green-500 dark:text-green-500 text-center">
              12% จากสัปดาห์ที่แล้ว
              <svg aria-hidden="true" class="w-3 h-3 ms-1" fill="none" viewBox="0 0 10 14"
                   xmlns="http://www.w3.org/2000/svg">
                <path d="M5 13V1m0 0L1 5m4-4 4 4" stroke="currentColor" stroke-linecap="round" stroke-linejoin="round"
                      stroke-width="2"/>
              </svg>
            </div>
          </div>

          <!-------------- Area Chart ----------------->
          <div id="area-chart">
            <apexchart :options="chartOptions" :series="series" height="170" type="area"></apexchart>
          </div>

          <!--  -------------------------------- -->
          <div class="grid grid-cols-1 items-center border-gray-200 border-t dark:border-gray-700 justify-between">
          </div>
        </div>

      </div>

      <!-- กราฟกลมด้านขวา -->

      <div
          class=" relative overflow-x-auto border-2 border-solid rounded-lg border-gray-300 dark:border-gray-600 h-32 md:h-64">

        <div class="w-full relative overflow-x-auto  bg-white rounded-lg shadow dark:bg-gray-800 p-4 md:p-6">

          <div class="flex justify-between items-start w-full">
            <div class="flex-col items-center">
              <div class="flex items-center mb-1">
                <h5 class="text-xl font-bold leading-none text-gray-900 dark:text-white me-1">สถานะคำร้อง</h5>
                <svg aria-hidden="true"
                     class="w-3.5 h-3.5 text-gray-500 dark:text-gray-400 hover:text-gray-900 dark:hover:text-white cursor-pointer ms-1"
                     data-popover-placement="bottom"
                     data-popover-target="chart-info" fill="currentColor" viewBox="0 0 20 20"
                     xmlns="http://www.w3.org/2000/svg">
                  <path
                      d="M10 .5a9.5 9.5 0 1 0 9.5 9.5A9.51 9.51 0 0 0 10 .5Zm0 16a1.5 1.5 0 1 1 0-3 1.5 1.5 0 0 1 0 3Zm1-5.034V12a1 1 0 0 1-2 0v-1.418a1 1 0 0 1 1.038-.999 1.436 1.436 0 0 0 1.488-1.441 1.501 1.501 0 1 0-3-.116.986.986 0 0 1-1.037.961 1 1 0 0 1-.96-1.037A3.5 3.5 0 1 1 11 11.466Z"/>
                </svg>
                <div id="chart-info"
                     class="absolute z-10 invisible inline-block text-sm text-gray-500 transition-opacity duration-300 bg-white border border-gray-200 rounded-lg shadow-sm opacity-0 w-72 dark:bg-gray-800 dark:border-gray-600 dark:text-gray-400"
                     data-popover
                     role="tooltip">
                  <div class="p-3 space-y-2">
                    <h3 class="font-semibold text-gray-900 dark:text-white">สถานะคำร้องทั้งหมดของระบบ</h3>
                    <p>
                      สถานะคำร้องทั้งหมดของระบบจะบอกสรุปเปอร์เซ็นสถานะของการขออนุมัติเอกสารของผู้ใช้ว่าอยู่ในขั้นตอนใดบ้างเป็นสามส่วน</p>
                  </div>
                  <div data-popper-arrow></div>
                </div>
              </div>

            </div>
          </div>

          <!-- Pie Chart -->
          <!--<div class="py-6" id="pie-chart"></div>-->
          <div id="chart">
            <apexchart :options="chartOptions_pie" :series="series_pie" type="pie" width="320"></apexchart>
          </div>
        </div>

        <!--  ----------------------------------------------    -->


      </div>

    </div>

    <div
        class="w-full relative overflow-x-auto border-2 border-solid rounded-lg border-gray-300 dark:border-gray-600 h-dvh mb-4 p-2">
      <!-- ตารางกลาง -->
      <div class="w-full relative overflow-x-auto shadow-md sm:rounded-lg">
        <!-- หัวตาราง -->

        <div
            class="flex items-center justify-between flex-column md:flex-row flex-wrap space-y-4 md:space-y-0 py-4 bg-white dark:bg-gray-900">
          <div>
            <button id="dropdownActionButton"
                    class="inline-flex items-center text-gray-500 bg-white border border-gray-300 focus:outline-none hover:bg-gray-100 focus:ring-4 focus:ring-gray-100 font-medium rounded-lg text-sm px-3 py-1.5 dark:bg-gray-800 dark:text-gray-400 dark:border-gray-600 dark:hover:bg-gray-700 dark:hover:border-gray-600 dark:focus:ring-gray-700"
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


          <tbody v-if="approveList.length > 0">
          <tr v-for="item in approveList"
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
              {{ moment(item.documentInfo.createdAt).format('DD/MM/YYYY') }}
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

            <td v-else class="px-6 py-4">
              <div class="flex items-center">
                <span class="text-gray-900 px-2 py-0.5"> ---------------- </span>
              </div>
            </td>

            <td class="px-6 py-4">
              <button class="font-medium text-blue-600 dark:text-blue-500 hover:underline" type="button"
                      @click="clickToViewDoc(item.documentInfo.id)">ตรวจสอบ
              </button>
            </td>
          </tr>
          </tbody>
          <tbody v-else>
          <tr>
            <td class="px-6 py-4" colspan="6">
              <div class="flex items-center justify-center">
                <p class="text-gray-500 dark:text-gray-400">ไม่มีข้อมูล</p>
              </div>
            </td>
          </tr>
          </tbody>





<!--                  <tbody>-->
<!--                      <tr class="bg-white border-b dark:bg-gray-800 dark:border-gray-700 hover:bg-gray-50 dark:hover:bg-gray-600">-->

<!--                          <th scope="row" class="flex items-center px-6 py-4 text-gray-900 whitespace-nowrap dark:text-white">-->

<!--                              <div class="ps-3">-->
<!--                                  <div class="text-base font-semibold">ขอทำไข่เจียวปู</div>-->
<!--                                  <div class="font-normal text-gray-500">ไข่เจียวขึ้นราคาจึงขอทำไข่เจียวปู</div>-->
<!--                              </div>-->
<!--                          </th>-->
<!--                          <td class="px-6 py-4">-->
<!--                              อัครเดช สุขสวัสดิ์-->
<!--                          </td>-->
<!--                          <td class="px-6 py-4">-->
<!--                              &lt;!&ndash; เวลา &ndash;&gt;-->
<!--                          </td>-->
<!--                          <td class="px-6 py-4">-->
<!--                              &lt;!&ndash; เวลา &ndash;&gt;-->
<!--                          </td>-->
<!--                           <td class="px-6 py-4">-->
<!--                              <div class="flex items-center">-->
<!--                                  <span class="text-gray-900 rounded bg-indigo-200 px-2 py-0.5">คำร้องแจ้งให้ทราบ</span>-->
<!--                              </div>-->
<!--                          </td>-->
<!--                          <td class="px-6 py-4">-->
<!--                              &lt;!&ndash; Modal toggle &ndash;&gt;-->
<!--                              <a href="#" type="button" data-modal-target="editUserModal" data-modal-show="editUserModal" class="font-medium text-blue-600 dark:text-blue-500 hover:underline">ตรวจสอบ</a>-->
<!--                          </td>-->
<!--                      </tr>-->
<!--                      <tr class="bg-white border-b dark:bg-gray-800 dark:border-gray-700 hover:bg-gray-50 dark:hover:bg-gray-600">-->

<!--                          <th scope="row" class="flex items-center px-6 py-4 font-medium text-gray-900 whitespace-nowrap dark:text-white">-->

<!--                              <div class="ps-3">-->
<!--                                  <div class="text-base font-semibold">เอกสารขอรับเงิน</div>-->
<!--                                  <div class="font-normal text-gray-500">เนื่องจากปูไทย</div>-->
<!--                              </div>-->
<!--                          </th>-->
<!--                          <td class="px-6 py-4">-->
<!--                              อัครเดช สุขสวัสดิ์-->
<!--                          </td>-->
<!--                          <td class="px-6 py-4">-->
<!--                              &lt;!&ndash; เวลา &ndash;&gt;-->
<!--                          </td>-->
<!--                          <td class="px-6 py-4">-->
<!--                              &lt;!&ndash; เวลา &ndash;&gt;-->
<!--                          </td>-->
<!--                          <td class="px-6 py-4">-->
<!--                              <div class="flex items-center">-->
<!--                                  <span class="text-gray-900 rounded bg-indigo-200 px-2 py-0.5">คำร้องแจ้งให้ทราบ</span>-->
<!--                              </div>-->
<!--                          </td>-->

<!--                          <td class="px-6 py-4">-->
<!--                              &lt;!&ndash; Modal toggle &ndash;&gt;-->
<!--                              <a href="#" type="button" data-modal-show="editUserModal" class="font-medium text-blue-600 dark:text-blue-500 hover:underline">ตรวจสอบ</a>-->
<!--                          </td>-->
<!--                      </tr>-->
<!--                      <tr class="bg-white border-b dark:bg-gray-800 dark:border-gray-700 hover:bg-gray-50 dark:hover:bg-gray-600">-->

<!--                          <th scope="row" class="flex items-center px-6 py-4 font-medium text-gray-900 whitespace-nowrap dark:text-white">-->

<!--                              <div class="ps-3">-->
<!--                                  <div class="text-base font-semibold">หีไก่</div>-->
<!--                                  <div class="font-normal text-gray-500">ฟหกฟหกฟหกฟหกฟก</div>-->
<!--                              </div>-->
<!--                          </th>-->
<!--                          <td class="px-6 py-4">-->
<!--                              อัครเดช สุขสวัสดิ์-->
<!--                          </td>-->
<!--                          <td class="px-6 py-4">-->
<!--                              &lt;!&ndash; เวลา &ndash;&gt;-->
<!--                          </td>-->
<!--                          <td class="px-6 py-4">-->
<!--                              &lt;!&ndash; เวลา &ndash;&gt;-->
<!--                          </td>-->
<!--                          <td class="px-6 py-4">-->
<!--                              <div class="flex items-center">-->
<!--                                  <span class="text-gray-900 rounded bg-orange-200 px-2 py-0.5">คำร้องขออนุมัติเอกสาร</span>-->
<!--                              </div>-->
<!--                          </td>-->

<!--                          <td class="px-6 py-4">-->
<!--                              &lt;!&ndash; Modal toggle &ndash;&gt;-->
<!--                              <a href="#" type="button" data-modal-show="editUserModal" class="font-medium text-blue-600 dark:text-blue-500 hover:underline">ตรวจสอบ</a>-->
<!--                          </td>-->
<!--                      </tr>-->
<!--                      <tr class="cursor-pointer bg-white border-b dark:bg-gray-800 dark:border-gray-700 hover:bg-gray-50 dark:hover:bg-gray-600">-->

<!--                          <th scope="row" class="cursor-pointer flex items-center px-6 py-4 font-medium text-gray-900 whitespace-nowrap dark:text-white">-->

<!--                              <div class="ps-3">-->
<!--                                  <div class="text-base font-semibold">ลูกอีไข่เป็ด</div>-->
<!--                                  <div class="font-normal text-gray-500">กกกกกกกกกกกกก</div>-->
<!--                              </div>-->
<!--                          </th>-->
<!--                          <td class="px-6 py-4">-->
<!--                              อัครเดช สุขสวัสดิ์-->
<!--                          </td>-->
<!--                          <td class="px-6 py-4">-->
<!--                              &lt;!&ndash; เวลา &ndash;&gt;-->
<!--                          </td>-->
<!--                          <td class="px-6 py-4">-->
<!--                              &lt;!&ndash; เวลา &ndash;&gt;-->
<!--                          </td>-->
<!--                          <td class="px-6 py-4">-->
<!--                              <div class="flex items-center">-->
<!--                                  <span class="text-gray-900 rounded bg-orange-200 px-2 py-0.5">คำร้องขออนุมัติเอกสาร</span>-->
<!--                              </div>-->
<!--                          </td>-->

<!--                          <td class="px-6 py-4">-->
<!--                              &lt;!&ndash; Modal toggle &ndash;&gt;-->
<!--                              <a href="#" type="button" data-modal-show="editUserModal" class="font-medium text-blue-600 dark:text-blue-500 hover:underline">ตรวจสอบ</a>-->
<!--                          </td>-->
<!--                      </tr>-->
<!--                      <tr class="bg-white dark:bg-gray-800 hover:bg-gray-50 dark:hover:bg-gray-600">-->

<!--                          <th scope="row" class="flex items-center px-6 py-4 font-medium text-gray-900 whitespace-nowrap dark:text-white">-->

<!--                              <div class="ps-3">-->
<!--                                  <div class="text-base font-semibold">ไข่หีหมา</div>-->
<!--                                  <div class="font-normal text-gray-500">ฟฟฟฟฟฟฟฟฟฟฟฟฟฟฟฟฟฟฟ</div>-->
<!--                              </div>-->
<!--                          </th>-->
<!--                          <td class="px-6 py-4">-->
<!--                              อัครเดช สุขสวัสดิ์-->
<!--                          </td>-->
<!--                          <td class="px-6 py-4">-->
<!--                              &lt;!&ndash; เวลา &ndash;&gt;-->
<!--                          </td>-->
<!--                           <td class="px-6 py-4">-->
<!--                              &lt;!&ndash; เวลา &ndash;&gt;-->
<!--                          </td>-->
<!--                          <td class="px-6 py-4">-->
<!--                              <div class=" flex items-center">-->
<!--                                 <span class="text-gray-900 rounded bg-orange-200 px-2 py-0.5">คำร้องขออนุมัติเอกสาร</span>-->
<!--                              </div>-->
<!--                          </td>-->

<!--                          <td class="px-6 py-4">-->
<!--                              &lt;!&ndash; Modal toggle &ndash;&gt;-->
<!--                              <a href="#" type="button" data-modal-show="editUserModal" class="font-medium text-blue-600 dark:text-blue-500 hover:underline">ตรวจสอบ</a>-->
<!--                          </td>-->
<!--                      </tr>-->
<!--                  </tbody>-->



        </table>
      </div>
    </div>
  </main>
</template>
