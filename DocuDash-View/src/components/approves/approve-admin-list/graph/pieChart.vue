<script setup>
import {onMounted, ref} from "vue";
import axios from "axios";
import { useGraphStore } from "../../../../stores/GraphStore.js";

const graphStore = useGraphStore();

// let mainURL = import.meta.env.VITE_API_URL;
// const pieGraph = ref(
//     {
//       "totalApprove": 29,
//       "totalStatus2": 6,
//       "totalStatus3": 11,
//       "totalStatus4": 7,
//       "totalStatus5": 5
//     }
// );
//
// const getPieInfo = async () => {
//   await axios.get(mainURL + '/api/approve/graph/pie')
//       .then(function (response) {
//         pieGraph.value = response.data;
//       })
//       .catch(function (error) {
//         console.log(error);
//       })
// };
//
// const series_pie = [
//   pieGraph.value.totalStatus2,
//   pieGraph.value.totalStatus3,
//   pieGraph.value.totalStatus4,
//   pieGraph.value.totalStatus5
// ]

const chartOptions_pie = {
  chart: {
    width: 390,
    type: 'pie',
  },
  colors: ['#018FFB', '#FEB018', '#00E197', '#FF4560'],
  labels: ['รอการตรวจสอบจากผู้ดูแล', 'รออนุมัติ', 'อนุมัติแล้ว', 'ไม่ผ่านการอนุมัติ'],
  responsive: [{
    breakpoint: 100,
    options: {
      chart: {
        width: 390
      },
      legend: {
        position: 'bottom'
      }
    }
  }]
}

</script>

<template>
  <div
      class="relative overflow-x-auto border-2 border-solid rounded-lg border-gray-300 dark:border-gray-600 h-44 md:h-72">

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
        <apexchart :options="chartOptions_pie" :series="graphStore.series_pie" type="pie" width="420"></apexchart>
<!--        <apexchart :series="series_pie" type="pie" width="380" :options="chartOptions_pie"></apexchart>-->
      </div>
    </div>

    <!--  ----------------------------------------------    -->

  </div>
</template>

<style scoped>

</style>