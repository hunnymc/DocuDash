<script setup>
import {onMounted, ref, watch} from "vue";
import axios from "axios";
import moment from "moment";
import Cookies from "js-cookie";
import router from "../../../router/index.js";
import {useDocumentListStore} from "../../../stores/listOfDocumentStore.js";
import waveChart from "./graph/waveChart.vue";
import pieChart from "./graph/pieChart.vue";

let mainURL= import.meta.env.VITE_API_URL;

// let mainURL = "http://localhost:5002";
// let mainURL = "http://cp23kw2.sit.kmutt.ac.th:10003";
// let mainURL = "https://capstone23.sit.kmutt.ac.th/kw2";

const approveList = ref([])
const store = useDocumentListStore();

let user_id = Cookies.get('userId')
let user_role = Cookies.get('role')
let access_token = Cookies.get('accessToken')

const getApproveList = () => {
  axios.get(mainURL + '/api/approve/info/admin', {
    headers: {
      "Authorization": 'Bearer ' + access_token
    }
  })
      .then(function (response) {
        approveList.value = response.data
        // approveList.value = filterApproveList();
      })
      .catch(function (error) {
        console.log(error);
      })
};

function filterApproveList() {
  return approveList.value.filter(item => item.status_type_id === 2);
}

async function clickToViewDoc(id) {

  await axios.post(
      mainURL + '/api/approve/read/admin/' + id
      , {userId: Cookies.get("userId")}
      , {headers: {"Authorization": "Bearer " + Cookies.get("accessToken"),}})
      .catch(function (error) {

        console.log(error);
      });

  await useDocumentListStore().getdocumentFilenameAndUserIdFromAxios(id);
  await router.push(`/kw2/approval/detail/admin/${id}`);
}

// ----------- sort function ----------------------

const buttonSortByDate = (order) => {
  if (order === 1) {
    approveList.value.sort((a, b) => a.documentInfo.dateAdd - b.documentInfo.dateAdd)
  } else if (order === 2) {
    approveList.value.sort((a, b) => b.documentInfo.dateAdd - a.documentInfo.dateAdd)
  }
}

// group by status and sort by date newest
const groupByStatus = () => {
  let group = approveList.value.reduce((r, a) => {
    r[a.status_type_id] = [...r[a.status_type_id] || [], a];
    return r;
  }, {});

  let result = []
  for (let key in group) {
    result.push(group[key])
  }

  result = result.map(item => {
    return item.sort((a, b) => b.documentInfo.dateAdd - a.documentInfo.dateAdd)
  })

  approveList.value = result.flat()
}

// ------------------------------------------------


// ------------------- Wave chart -------------------------
const waveGraph = ref(
    {
      "newRequestsThisWeek": 16,
      "newRequestsLastWeek": 6,
      "percentageIncreaseFromLastWeek": 167,
      "day1": 1711931658028,
      "day2": 1711845258028,
      "day3": 1711758858028,
      "day4": 1711672458028,
      "day5": 1711586058028,
      "day6": 1711499658028,
      "day7": 1711413258028,
      "countOfDay1": 5,
      "countOfDay2": 1,
      "countOfDay3": 3,
      "countOfDay4": 1,
      "countOfDay5": 1,
      "countOfDay6": 3,
      "countOfDay7": 2
    }
);

// get data from api
const getWaveInfo = async () => {
  await axios.get(mainURL + '/api/approve/graph/wave')
      .then(function (response) {
        waveGraph.value = response.data;
      })
      .catch(function (error) {
        console.log(error);
      })
};

const series = [
  {
    name: 'จำนวนเอกสาร',
    data: [waveGraph.value.countOfDay7, waveGraph.value.countOfDay6, waveGraph.value.countOfDay5, waveGraph.value.countOfDay4, waveGraph.value.countOfDay3, waveGraph.value.countOfDay2, waveGraph.value.countOfDay1]
  }
]

const chartOptions = {
  chart: {
    height: 350,
    type: 'area',
    zoom: {
      enabled: false
    }
  },
  dataLabels: {
    enabled: false
  },
  stroke: {
    curve: 'smooth'
  },
  xaxis: {
    type: 'date',
    categories: [
      moment.unix(waveGraph.value.day7 / 1000).format('DD/MM'),
      moment.unix(waveGraph.value.day6 / 1000).format('DD/MM'),
      moment.unix(waveGraph.value.day5 / 1000).format('DD/MM'),
      moment.unix(waveGraph.value.day4 / 1000).format('DD/MM'),
      moment.unix(waveGraph.value.day3 / 1000).format('DD/MM'),
      moment.unix(waveGraph.value.day2 / 1000).format('DD/MM'),
      moment.unix(waveGraph.value.day1 / 1000).format('DD/MM')
    ]
  },
};

// ------------------- Pie chart -------------------------
const pieGraph = ref(
    {
      "totalApprove": 29,
      "totalStatus2": 6,
      "totalStatus3": 11,
      "totalStatus4": 7,
      "totalStatus5": 5
    }
);

const getPieInfo = async () => {
  await axios.get(mainURL + '/api/approve/graph/pie')
      .then(function (response) {
        pieGraph.value = response.data;
      })
      .catch(function (error) {
        console.log(error.response.data.message);
      })
};

const series_pie = [
  pieGraph.value.totalStatus2,
  pieGraph.value.totalStatus3,
  pieGraph.value.totalStatus4,
  pieGraph.value.totalStatus5
]

watch(() => store.callFunctionToFetchDashboard, async (value) => {
  if (value) {
    getApproveList();
    await getWaveInfo();
    await getPieInfo();
    store.setCallFunctionToFetchDashboard(false);
  }
});

onMounted(async () => {
  getApproveList();
  await getWaveInfo();
  await getPieInfo();
});

</script>

<template>
  <main class="w-full relative overflow-x-auto p-4  h-auto ">
    <div class="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-2 gap-4 mb-4">

      <!-- กราฟยึกยักด้านซ้าย -->
      <waveChart :series="series" :chartOptions="chartOptions" />

      <!-- กราฟกลมด้านขวา -->
      <pieChart :series_pie="series_pie"/>

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
                  <a @click="buttonSortByDate(2)" class="cursor-pointer block px-4 py-2 hover:bg-gray-100">เวลาที่ยื่นจาก ใหม่ - เก่า</a>
                </li>
                <li>
                  <a @click="buttonSortByDate(1)" class="cursor-pointer block px-4 py-2 hover:bg-gray-100">เวลาที่ยื่นจาก เก่า - ใหม่</a>
                </li>
              </ul>
            </div>
          </div>
        </div>
        <table class="w-full text-sm text-left rtl:text-right text-gray-500 dark:text-gray-400">
          <thead class="text-xs text-gray-700 uppercase bg-gray-100 dark:bg-gray-700 dark:text-gray-400">
          <tr>
            <th>
              <!--  จุดแดง  -->
            </th>
            <th class="px-4 py-3" scope="col">
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
              class="bg-white border-b  hover:bg-gray-50 ">
            <td>
              <!--     v-if="item.isRead === 0"         -->
              <div v-if="item.isRead === 0"  class="pl-4" >
                <div class="w-2 h-2  bg-red-500 border border-white rounded-full"></div>
              </div>
            </td>
            <th class="flex items-center px-1 py-4 text-gray-900 whitespace-nowrap dark:text-white" scope="row">
              <div class="ps-3">
                <div class="font-normal text-gray-500">ID: {{ item.documentInfo.id }}</div>
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
              {{ moment(item.documentInfo.dateAdd * 1000).format('DD/MM/YYYY') }}
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
            <td class="px-6 py-4" colspan="7">
              <div class="flex items-center justify-center">
                <p class="text-gray-500 dark:text-gray-400">ไม่มีข้อมูล</p>
              </div>
            </td>
          </tr>
          </tbody>

        </table>
      </div>
    </div>
  </main>
</template>
