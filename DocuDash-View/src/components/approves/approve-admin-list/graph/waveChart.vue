<script setup>
import { onMounted, ref } from "vue";
import axios from "axios";
import moment from "moment";

import { useGraphStore } from "../../../../stores/GraphStore.js";

const graphStore = useGraphStore();

let mainURL = import.meta.env.VITE_API_URL;
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
      alert(error.response.data.message)
    })
};

const series = [
  {
    name: 'จำนวนเอกสาร',
    data: [
      waveGraph.value.countOfDay7, 
      waveGraph.value.countOfDay6, 
      waveGraph.value.countOfDay5, 
      waveGraph.value.countOfDay4, 
      waveGraph.value.countOfDay3, 
      waveGraph.value.countOfDay2, 
      waveGraph.value.countOfDay1
    ]
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

onMounted(async () => {
  await getWaveInfo();
});

</script>

<template>
  <!-- กราฟยึกยักด้านซ้าย -->
  <div
    class="w-full relative overflow-x-auto border-2 border-solid border-gray-300 rounded-lg dark:border-gray-600 h-44 md:h-72">

    <div class="w-full relative overflow-x-auto  bg-white rounded-lg shadow dark:bg-gray-800 p-4 md:p-6">
      <div class="flex justify-between">
        <div>
          <h5 class="leading-none text-3xl font-bold text-gray-900 dark:text-white pb-2">
            {{ waveGraph.newRequestsThisWeek }} คำร้องใหม่</h5>
          <p class="text-base font-normal text-gray-500 dark:text-gray-400">ในสัปดาห์นี้</p>
        </div>
        <div
          class="flex items-center px-2.5 py-0.5 text-base font-semibold text-green-500 dark:text-green-500 text-center">
          {{ waveGraph.percentageIncreaseFromLastWeek }}% จากสัปดาห์ที่แล้ว
          <svg aria-hidden="true" class="w-3 h-3 ms-1" fill="none" viewBox="0 0 10 14"
            xmlns="http://www.w3.org/2000/svg">
            <path d="M5 13V1m0 0L1 5m4-4 4 4" stroke="currentColor" stroke-linecap="round" stroke-linejoin="round"
              stroke-width="2" />
          </svg>
        </div>
      </div>

      <!-------------- Area Chart ----------------->
      <div id="area-chart">
        <apexchart :options="graphStore.chartOptions_wave" :series="graphStore.series_wave" height="155" type="area"></apexchart>
      </div>

      <!--  -------------------------------- -->
      <div class="grid grid-cols-1 items-center border-gray-200 border-t dark:border-gray-700 justify-between">
      </div>
    </div>

  </div>
</template>

<style scoped></style>