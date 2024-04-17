<script setup>
import { onMounted, ref } from 'vue'
import axios from "axios";
import Cookies from "js-cookie";
import moment from "moment";
import { useRouter } from "vue-router";

let router = useRouter();

let mainURL = import.meta.env.VITE_API_URL;

// let mainURL = "http://localhost:5002";
// let mainURL = "http://cp23kw2.sit.kmutt.ac.th:10003";
// let mainURL = "https://capstone23.sit.kmutt.ac.th/kw2";

const approveList = ref([])
// const approveList = ref(approveListJson)

let user_id = parseInt(Cookies.get('userId'));
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
      if (user_role === 'MANAGER') {
        approveList.value = approveList.value.filter(item => item.managersWhoVerified.some(manager => manager.managerID.id === user_id))
      }
    })
    .catch(function (error) {
      console.log(error);
    })
};

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

const ApproveType = {
  1: "คำร้องแจ้งให้ทราบ",
  2: "คำร้องขออนุมัติเอกสาร"
}

function getUpdateText(dateUpdate) {
  const dateUpdateShowList = {
    0: "วันนี้",
    1: "เมื่อวาน",
    2: "2 วันที่แล้ว",
    3: "3 วันที่แล้ว",
    4: "4 วันที่แล้ว",
    5: "5 วันที่แล้ว",
    6: "6 วันที่แล้ว",
    7: "7 วันที่แล้ว",
    14: "14 วันที่แล้ว",
    30: "30 วันที่แล้ว",
    60: "60 วันที่แล้ว",
    90: "90 วันที่แล้ว",
    180: "180 วันที่แล้ว",
    365: "365 วันที่แล้ว",
  };

  const daysAgo = moment().diff(moment.unix(dateUpdate), 'days');
  let closestDay = Object.keys(dateUpdateShowList).reduce((a, b) => {
    return Math.abs(b - daysAgo) < Math.abs(a - daysAgo) ? b : a;
  });

  return dateUpdateShowList[closestDay] || 'เกิน 1 ปีที่แล้ว';
}

onMounted(() => {
  getApproveList();
});

</script>

<template>

  <section class="w-full relative overflow-x-auto bg-gray-50  ">
    <section class="flex items-center  bg-gray-50 ">
      <div class="w-full   mx-auto ">
        <!-- Start coding here -->
        <div class="relative overflow-hidden bg-white  dark:bg-gray-800 ">
          <div
            class="border border-slate-300 flex-row items-center justify-between p-4 space-y-3 sm:flex sm:space-y-0 sm:space-x-4">
            <div>
              <h5 class="mr-3 font-semibold dark:text-white text-3xl">สถานะของการอนุมัติทั้งหมด</h5>
              <p class="text-gray-500 ">รวมสถานะเอกสารและโครงการที่ขออนุมัติทั้งหมด</p>
            </div>
          </div>
        </div>
      </div>
    </section>
    <div class=" mx-auto ">
      <div class="relative overflow-hidden bg-white shadow-md dark:bg-gray-800 sm:rounded-lg">
        <div
          class="flex flex-col px-4 py-3 space-y-3 lg:flex-row lg:items-center lg:justify-between lg:space-y-0 lg:space-x-4">
          <div class="flex items-center flex-1 space-x-4">
            <h5>
              <span class="text-gray-500">รวมคำขออนุมัติทั้งหมด: </span>
              <span class="dark:text-white"> {{ approveList.length }} </span>
            </h5>
          </div>
          <div
            class="flex flex-col flex-shrink-0 space-y-3 md:flex-row md:items-center lg:justify-end md:space-y-0 md:space-x-3">
            <button
              class="flex items-center justify-center flex-shrink-0 px-3 py-2 text-sm font-medium text-gray-900 bg-white border border-gray-200 rounded-lg focus:outline-none hover:bg-gray-100 hover:text-primary-700 focus:z-10 focus:ring-4 focus:ring-gray-200 dark:focus:ring-gray-700 dark:bg-gray-800 dark:text-gray-400 dark:border-gray-600 dark:hover:text-white dark:hover:bg-gray-700"
              type="button">
              <svg class="w-4 h-4 mr-2" fill="none" viewBox="0 0 24 24" xmlns="http://www.w3.org/2000/svg">
                <g id="SVGRepo_bgCarrier" stroke-width="0"></g>
                <g id="SVGRepo_tracerCarrier" stroke-linecap="round" stroke-linejoin="round"></g>
                <g id="SVGRepo_iconCarrier">
                  <path
                    d="M4.06189 13C4.02104 12.6724 4 12.3387 4 12C4 7.58172 7.58172 4 12 4C14.5006 4 16.7332 5.14727 18.2002 6.94416M19.9381 11C19.979 11.3276 20 11.6613 20 12C20 16.4183 16.4183 20 12 20C9.61061 20 7.46589 18.9525 6 17.2916M9 17H6V17.2916M18.2002 4V6.94416M18.2002 6.94416V6.99993L15.2002 7M6 20V17.2916"
                    stroke="#000000" stroke-linecap="round" stroke-linejoin="round" stroke-width="2"></path>
                </g>
              </svg>
              รีโหลด
            </button>

          </div>
        </div>
        <div class="overflow-x-auto">
          <table class="w-full text-sm text-left text-gray-500 dark:text-gray-400">
            <thead class="text-xs text-gray-700 uppercase bg-gray-50 dark:bg-gray-700 dark:text-gray-400">
              <tr>
                <th class="p-4" scope="col"></th>
                <th class="px-4 py-3" scope="col">ชื่อเรื่อง</th>
                <th class="px-4 py-3" scope="col">ผู้ยื่นเรื่อง</th>
                <th class="px-4 py-3" scope="col">ประเภทคำร้อง</th>
                <th class="px-4 py-3" scope="col">สถานะการอนุมัติ</th>
                <th class="px-4 py-3" scope="col">วันที่ยื่นคำร้อง</th>
                <th class="px-4 py-3" scope="col">ผจก.ที่ต้องอนุมัติคำร้อง</th>
                <th class="px-4 py-3" scope="col">ขั้นตอน</th>
                <th class="px-4 py-3" scope="col">ผจก.ที่ดำเนินการแล้ว</th>
                <th class="px-4 py-3" scope="col">ความเร่งด่วน</th>
                <th class="px-4 py-3" scope="col">อัพเดทล่าสุด</th>
              </tr>
            </thead>

            <tbody v-if="approveList.length > 0">
              <tr v-for="item in approveList"
                class="border-b dark:border-gray-600 hover:bg-gray-100 dark:hover:bg-gray-700">
                <td class="w-4 px-4 py-3">
                  <div class="flex items-center">
                    <!-- <input id="checkbox-table-search-1"
                      class="w-4 h-4 bg
                  -gray-100 border-gray-300 rounded text-primary-600 focus:ring-primary-500 dark:focus:ring-primary-600 dark:ring-offset-gray-800 focus:ring-2 dark:bg-gray-700 dark:border-gray-600"
                      type="checkbox"> -->
                    <!-- <label class="sr-only" for="checkbox-table-search-1">checkbox</label> -->
                     ID : {{ item.documentInfo.id }}

                  </div>
                </td>

                <th class="flex items-center px-4 py-2 font-medium text-gray-900 whitespace-nowrap dark:text-white"
                  scope="row">
                  <div class="flex items-center">
                    {{ item.documentInfo.title }}
                  </div>
                </th>

                <td class="px-4 py-2">
                  {{ item.documentInfo.usersUserid.fullName }}
                </td>

                <td class="px-4 py-2">
                  {{ ApproveType[item.approve_type_Id] }}
                </td>

                <td class="px-4 py-2 font-medium text-gray-900 whitespace-nowrap dark:text-white">
                  <div class="flex items-center">
                    <div :class="StatusColor[item.status_type_id] + ' inline-block w-4 h-4 mr-2 rounded-full'"></div>
                    {{ StatusName[item.status_type_id] }}
                  </div>
                </td>

                <td class="px-4 py-2 font-medium text-gray-900 whitespace-nowrap dark:text-white">
                  {{ moment.unix(item.documentInfo.dateAdd).format('DD/MM/YYYY') }}
                </td>

                <td class="px-4 py-2 font-medium text-gray-900 whitespace-nowrap dark:text-white">
                  <div class="flex items-center">
                    {{ item.managersWhoVerified.map(manager => manager.managerID.fullName).join(', ') }}
                  </div>
                </td>

                <td class="px-4 py-2 font-medium text-gray-900 whitespace-nowrap dark:text-white">
                  <div class="flex items-center">

                    <!--  Admin กด Approve แล้ว  -->
                    <ol v-if="item.status_type_id === 2" class="flex items-center w-full">
                      <li
                        class="flex w-full items-center text-blue-600 dark:text-blue-500 after:content-[''] after:w-full after:h-1 after:border-b after:border-blue-100 after:border-4 after:inline-block dark:after:border-blue-800">
                        <span
                          class="flex items-center justify-center w-2 h-2 bg-blue-100 rounded-full lg:h-6 lg:w-6 dark:bg-blue-800 shrink-0">
                          <svg aria-hidden="true" class="w-2 h-2 text-blue-600 lg:w-3 lg:h-3 dark:text-blue-300"
                            fill="none" viewBox="0 0 16 12" xmlns="http://www.w3.org/2000/svg">
                            <path d="M1 5.917 5.724 10.5 15 1.5" stroke="currentColor" stroke-linecap="round"
                              stroke-linejoin="round" stroke-width="2" />
                          </svg>
                        </span>
                      </li>

                      <li
                        class="flex w-full items-center after:content-[''] after:w-full after:h-1 after:border-b after:border-gray-100 after:border-4 after:inline-block dark:after:border-gray-700">
                        <span
                          class="flex items-center justify-center w-2 h-2 bg-gray-100 rounded-full lg:h-6 lg:w-6 dark:bg-gray-700 shrink-0">
                          <svg aria-hidden="true" class="w-2 h-2 text-gray-500 lg:w-3 lg:h-3 dark:text-gray-100"
                            fill="currentColor" viewBox="0 0 20 16" xmlns="http://www.w3.org/2000/svg">
                            <path
                              d="M18 0H2a2 2 0 0 0-2 2v12a2 2 0 0 0 2 2h16a2 2 0 0 0 2-2V2a2 2 0 0 0-2-2ZM6.5 3a2.5 2.5 0 1 1 0 5 2.5 2.5 0 0 1 0-5ZM3.014 13.021l.157-.625A3.427 3.427 0 0 1 6.5 9.571a3.426 3.426 0 0 1 3.322 2.805l.159.622-6.967.023ZM16 12h-3a1 1 0 0 1 0-2h3a1 1 0 0 1 0 2Zm0-3h-3a1 1 0 1 1 0-2h3a1 1 0 1 1 0 2Zm0-3h-3a1 1 0 1 1 0-2h3a1 1 0 1 1 0 2Z" />
                          </svg>
                        </span>
                      </li>

                      <li class="flex items-center w-full">
                        <span
                          class="flex items-center justify-center w-10 h-10 bg-gray-100 rounded-full lg:h-6 lg:w-6 dark:bg-gray-700 shrink-0">
                          <svg aria-hidden="true" class="w-4 h-4 text-gray-500 lg:w-3 lg:h-3 dark:text-gray-100"
                            fill="currentColor" viewBox="0 0 18 20" xmlns="http://www.w3.org/2000/svg">
                            <path
                              d="M16 1h-3.278A1.992 1.992 0 0 0 11 0H7a1.993 1.993 0 0 0-1.722 1H2a2 2 0 0 0-2 2v15a2 2 0 0 0 2 2h14a2 2 0 0 0 2-2V3a2 2 0 0 0-2-2ZM7 2h4v3H7V2Zm5.7 8.289-3.975 3.857a1 1 0 0 1-1.393 0L5.3 12.182a1.002 1.002 0 1 1 1.4-1.436l1.328 1.289 3.28-3.181a1 1 0 1 1 1.392 1.435Z" />
                          </svg>
                        </span>
                      </li>

                    </ol>

                    <!--  Manager ยังไม่กด Approve  -->
                    <ol v-if="item.status_type_id === 3" class="flex items-center w-full">
                      <li
                        class="flex w-full items-center text-blue-600 dark:text-blue-500 after:content-[''] after:w-full after:h-1 after:border-b after:border-blue-100 after:border-4 after:inline-block dark:after:border-blue-800">
                        <span
                          class="flex items-center justify-center w-2 h-2 bg-blue-100 rounded-full lg:h-6 lg:w-6 dark:bg-blue-800 shrink-0">
                          <svg aria-hidden="true" class="w-2 h-2 text-blue-600 lg:w-3 lg:h-3 dark:text-blue-300"
                            fill="none" viewBox="0 0 16 12" xmlns="http://www.w3.org/2000/svg">
                            <path d="M1 5.917 5.724 10.5 15 1.5" stroke="currentColor" stroke-linecap="round"
                              stroke-linejoin="round" stroke-width="2" />
                          </svg>
                        </span>
                      </li>

                      <li
                        class="flex w-full items-center text-blue-600 dark:text-blue-500 after:content-[''] after:w-full after:h-1 after:border-b after:border-blue-100 after:border-4 after:inline-block dark:after:border-blue-800">
                        <span
                          class="flex items-center justify-center w-2 h-2 bg-blue-100 rounded-full lg:h-6 lg:w-6 dark:bg-blue-800 shrink-0">
                          <svg aria-hidden="true" class="w-2 h-2 text-blue-600 lg:w-3 lg:h-3 dark:text-blue-300"
                            fill="none" viewBox="0 0 16 12" xmlns="http://www.w3.org/2000/svg">
                            <path d="M1 5.917 5.724 10.5 15 1.5" stroke="currentColor" stroke-linecap="round"
                              stroke-linejoin="round" stroke-width="2" />
                          </svg>
                        </span>
                      </li>

                      <li class="flex items-center w-full">
                        <span
                          class="flex items-center justify-center w-10 h-10 bg-gray-100 rounded-full lg:h-6 lg:w-6 dark:bg-gray-700 shrink-0">
                          <svg aria-hidden="true" class="w-4 h-4 text-gray-500 lg:w-3 lg:h-3 dark:text-gray-100"
                            fill="currentColor" viewBox="0 0 18 20" xmlns="http://www.w3.org/2000/svg">
                            <path
                              d="M16 1h-3.278A1.992 1.992 0 0 0 11 0H7a1.993 1.993 0 0 0-1.722 1H2a2 2 0 0 0-2 2v15a2 2 0 0 0 2 2h14a2 2 0 0 0 2-2V3a2 2 0 0 0-2-2ZM7 2h4v3H7V2Zm5.7 8.289-3.975 3.857a1 1 0 0 1-1.393 0L5.3 12.182a1.002 1.002 0 1 1 1.4-1.436l1.328 1.289 3.28-3.181a1 1 0 1 1 1.392 1.435Z" />
                          </svg>
                        </span>
                      </li>

                    </ol>

                    <!--  เอกสารผ่านหรือไม่ผ่าน  -->
                    <ol v-if="item.status_type_id >= 4" class="flex items-center w-full">
                      <li
                        class="flex w-full items-center text-blue-600 dark:text-blue-500 after:content-[''] after:w-full after:h-1 after:border-b after:border-blue-100 after:border-4 after:inline-block dark:after:border-blue-800">
                        <span
                          class="flex items-center justify-center w-2 h-2 bg-blue-100 rounded-full lg:h-6 lg:w-6 dark:bg-blue-800 shrink-0">
                          <svg aria-hidden="true" class="w-2 h-2 text-blue-600 lg:w-3 lg:h-3 dark:text-blue-300"
                            fill="none" viewBox="0 0 16 12" xmlns="http://www.w3.org/2000/svg">
                            <path d="M1 5.917 5.724 10.5 15 1.5" stroke="currentColor" stroke-linecap="round"
                              stroke-linejoin="round" stroke-width="2" />
                          </svg>
                        </span>
                      </li>

                      <li
                        class="flex w-full items-center text-blue-600 dark:text-blue-500 after:content-[''] after:w-full after:h-1 after:border-b after:border-blue-100 after:border-4 after:inline-block dark:after:border-blue-800">
                        <span
                          class="flex items-center justify-center w-2 h-2 bg-blue-100 rounded-full lg:h-6 lg:w-6 dark:bg-blue-800 shrink-0">
                          <svg aria-hidden="true" class="w-2 h-2 text-blue-600 lg:w-3 lg:h-3 dark:text-blue-300"
                            fill="none" viewBox="0 0 16 12" xmlns="http://www.w3.org/2000/svg">
                            <path d="M1 5.917 5.724 10.5 15 1.5" stroke="currentColor" stroke-linecap="round"
                              stroke-linejoin="round" stroke-width="2" />
                          </svg>
                        </span>
                      </li>

                      <li class="flex items-center w-full">
                        <span
                          class="flex items-center justify-center w-10 h-10 bg-blue-100 rounded-full lg:h-6 lg:w-6 dark:bg-gray-700 shrink-0">
                          <svg aria-hidden="true" class="w-2 h-2 text-blue-600 lg:w-3 lg:h-3 dark:text-blue-300"
                            fill="none" viewBox="0 0 16 12" xmlns="http://www.w3.org/2000/svg">
                            <path d="M1 5.917 5.724 10.5 15 1.5" stroke="currentColor" stroke-linecap="round"
                              stroke-linejoin="round" stroke-width="2" />
                          </svg>
                        </span>
                      </li>

                    </ol>
                  </div>
                </td>

                <td class="px-4 py-2 font-medium text-gray-900 whitespace-nowrap dark:text-white">
                  <div class="flex items-center">
                    <svg class="w-5 h-5 mr-2 text-gray-400" fill="#000000" viewBox="0 0 100 100" xml:space="preserve"
                      xmlns="http://www.w3.org/2000/svg">
                      <g id="SVGRepo_bgCarrier" stroke-width="0"></g>
                      <g id="SVGRepo_tracerCarrier" stroke-linecap="round" stroke-linejoin="round"></g>
                      <g id="SVGRepo_iconCarrier">
                        <g>
                          <g>
                            <path
                              d="M80,71.2V74c0,3.3-2.7,6-6,6H26c-3.3,0-6-2.7-6-6v-2.8c0-7.3,8.5-11.7,16.5-15.2c0.3-0.1,0.5-0.2,0.8-0.4 c0.6-0.3,1.3-0.3,1.9,0.1C42.4,57.8,46.1,59,50,59c3.9,0,7.6-1.2,10.8-3.2c0.6-0.4,1.3-0.4,1.9-0.1c0.3,0.1,0.5,0.2,0.8,0.4 C71.5,59.5,80,63.9,80,71.2z">
                            </path>
                          </g>
                          <g>
                            <ellipse cx="50" cy="36.5" rx="14.9" ry="16.5"></ellipse>
                          </g>
                        </g>
                      </g>
                    </svg>
                    {{ item.totalManagersWhoVerified }} / {{ item.totalManagers }}
                  </div>
                </td>

                <td class="px-4 py-2">{{ item.documentInfo.urgency }}</td>

                <td class="px-4 py-2 font-medium text-gray-900 whitespace-nowrap dark:text-white">
                  {{ getUpdateText(item.documentInfo.dateUpdate) }}
                </td>

              </tr>
            
            </tbody>

            <tbody v-if="approveList.length <= 0">
              <tr>
            <td class="px-6 py-4" colspan="11">
              <div class="flex items-center justify-center">
                <p class="text-gray-500 dark:text-gray-400">ไม่มีข้อมูล</p>
              </div>
            </td>
          </tr>
            
            </tbody>
          </table>
        </div>

      </div>
    </div>
  </section>
</template>

<style scoped></style>
