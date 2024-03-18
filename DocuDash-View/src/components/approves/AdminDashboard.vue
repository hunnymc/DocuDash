<script setup>
import {ref} from "vue";

// ------------ Area graph ----------------

const series = ref([
  {
    name: 'series1',
    data: [31, 40, 28, 51, 42, 109, 100]
  }
]);

const chartOptions = ref({
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
    type: 'datetime',
    categories: ["2018-09-19T00:00:00.000Z", "2018-09-19T01:30:00.000Z", "2018-09-19T02:30:00.000Z", "2018-09-19T03:30:00.000Z", "2018-09-19T04:30:00.000Z", "2018-09-19T05:30:00.000Z", "2018-09-19T06:30:00.000Z"]
  },
});

// ------------ Pie graph ----------------

const series_pie = ref([44, 55, 13, 43, 22]);
const chartOptions_pie = ref({
  chart: {
    width: 380,
    type: 'pie',
  },
  labels: ['Team A', 'Team B', 'Team C', 'Team D', 'Team E'],
  responsive: [{
    breakpoint: 480,
    options: {
      chart: {
        width: 200
      },
      legend: {
        position: 'bottom'
      }
    }
  }]
});

</script>

<template  >
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
      <svg class="w-3 h-3 ms-1" aria-hidden="true" xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 10 14">
        <path stroke="currentColor" stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M5 13V1m0 0L1 5m4-4 4 4"/>
      </svg>
    </div>
  </div>

  <!-------------- Area Chart ----------------->
  <div id="area-chart">
    <apexchart type="area" height="170" :options="chartOptions" :series="series"></apexchart>
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
          <svg data-popover-target="chart-info" data-popover-placement="bottom" class="w-3.5 h-3.5 text-gray-500 dark:text-gray-400 hover:text-gray-900 dark:hover:text-white cursor-pointer ms-1" aria-hidden="true" xmlns="http://www.w3.org/2000/svg" fill="currentColor" viewBox="0 0 20 20">
            <path d="M10 .5a9.5 9.5 0 1 0 9.5 9.5A9.51 9.51 0 0 0 10 .5Zm0 16a1.5 1.5 0 1 1 0-3 1.5 1.5 0 0 1 0 3Zm1-5.034V12a1 1 0 0 1-2 0v-1.418a1 1 0 0 1 1.038-.999 1.436 1.436 0 0 0 1.488-1.441 1.501 1.501 0 1 0-3-.116.986.986 0 0 1-1.037.961 1 1 0 0 1-.96-1.037A3.5 3.5 0 1 1 11 11.466Z"/>
          </svg>
          <div data-popover id="chart-info" role="tooltip" class="absolute z-10 invisible inline-block text-sm text-gray-500 transition-opacity duration-300 bg-white border border-gray-200 rounded-lg shadow-sm opacity-0 w-72 dark:bg-gray-800 dark:border-gray-600 dark:text-gray-400">
              <div class="p-3 space-y-2">
                  <h3 class="font-semibold text-gray-900 dark:text-white">สถานะคำร้องทั้งหมดของระบบ</h3>
                  <p>สถานะคำร้องทั้งหมดของระบบจะบอกสรุปเปอร์เซ็นสถานะของการขออนุมัติเอกสารของผู้ใช้ว่าอยู่ในขั้นตอนใดบ้างเป็นสามส่วน</p>    
          </div>
          <div data-popper-arrow></div>
      </div>
    </div>

  </div>
</div>

<!-- Pie Chart -->
<!--<div class="py-6" id="pie-chart"></div>-->
  <div id="chart">
    <apexchart type="pie" width="320" :options="chartOptions_pie" :series="series_pie"></apexchart>
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

    <div class="flex items-center justify-between flex-column md:flex-row flex-wrap space-y-4 md:space-y-0 py-4 bg-white dark:bg-gray-900">
        <div>
            <button id="dropdownActionButton" data-dropdown-toggle="dropdownAction" class="inline-flex items-center text-gray-500 bg-white border border-gray-300 focus:outline-none hover:bg-gray-100 focus:ring-4 focus:ring-gray-100 font-medium rounded-lg text-sm px-3 py-1.5 dark:bg-gray-800 dark:text-gray-400 dark:border-gray-600 dark:hover:bg-gray-700 dark:hover:border-gray-600 dark:focus:ring-gray-700" type="button">
                <span class="sr-only">Action button</span>
                เรียงลำดับ
                <svg class="w-2.5 h-2.5 ms-2.5" aria-hidden="true" xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 10 6">
                    <path stroke="currentColor" stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="m1 1 4 4 4-4"/>
                </svg>
            </button>
            <!-- Dropdown menu -->
            <div id="dropdownAction" class="z-10 hidden bg-white divide-y divide-gray-100 rounded-lg shadow w-44 dark:bg-gray-700 dark:divide-gray-600">
                <ul class="py-1 text-sm text-gray-700 dark:text-gray-200" aria-labelledby="dropdownActionButton">
                    <li>
                        <a href="#" class="block px-4 py-2 hover:bg-gray-100 dark:hover:bg-gray-600 dark:hover:text-white">เวลาที่ยื่น</a>
                    </li>
                    <li>
                        <a href="#" class="block px-4 py-2 hover:bg-gray-100 dark:hover:bg-gray-600 dark:hover:text-white">ประเภทคำร้อง</a>
                    </li>
                </ul>
            </div>
        </div>
    </div>
    <table class="w-full text-sm text-left rtl:text-right text-gray-500 dark:text-gray-400">
        <thead class="text-xs text-gray-700 uppercase bg-gray-100 dark:bg-gray-700 dark:text-gray-400">
            <tr>
                
                <th scope="col" class="px-6 py-3">
                    ชื่อเรื่อง
                </th>
                <th scope="col" class="px-6 py-3">
                    ผู้ยื่น
                </th>
                <th scope="col" class="px-6 py-3">
                    แผนก
                </th>
                <th scope="col" class="px-6 py-3">
                    วันที่ยื่น
                </th>
                <th scope="col" class="px-6 py-3">
                    ประเภทคำร้อง
                </th>
                
                <th scope="col" class="px-6 py-3">
                    ตรวจสอบรายละเอียด
                </th>
            </tr>
        </thead>
        <tbody>
            <tr class="bg-white border-b dark:bg-gray-800 dark:border-gray-700 hover:bg-gray-50 dark:hover:bg-gray-600">
                
                <th scope="row" class="flex items-center px-6 py-4 text-gray-900 whitespace-nowrap dark:text-white">
                    
                    <div class="ps-3">
                        <div class="text-base font-semibold">ขอทำไข่เจียวปู</div>
                        <div class="font-normal text-gray-500">ไข่เจียวขึ้นราคาจึงขอทำไข่เจียวปู</div>
                    </div>  
                </th>
                <td class="px-6 py-4">
                    อัครเดช สุขสวัสดิ์
                </td>
                <td class="px-6 py-4">
                    <!-- เวลา -->
                </td>
                <td class="px-6 py-4">
                    <!-- เวลา -->
                </td>
                 <td class="px-6 py-4">
                    <div class="flex items-center">
                        <span class="text-gray-900 rounded bg-indigo-200 px-2 py-0.5">คำร้องแจ้งให้ทราบ</span> 
                    </div>
                </td>
                <td class="px-6 py-4">
                    <!-- Modal toggle -->
                    <a href="#" type="button" data-modal-target="editUserModal" data-modal-show="editUserModal" class="font-medium text-blue-600 dark:text-blue-500 hover:underline">ตรวจสอบ</a>
                </td>
            </tr>
            <tr class="bg-white border-b dark:bg-gray-800 dark:border-gray-700 hover:bg-gray-50 dark:hover:bg-gray-600">
               
                <th scope="row" class="flex items-center px-6 py-4 font-medium text-gray-900 whitespace-nowrap dark:text-white">
                    
                    <div class="ps-3">
                        <div class="text-base font-semibold">เอกสารขอรับเงิน</div>
                        <div class="font-normal text-gray-500">เนื่องจากปูไทย</div>
                    </div>
                </th>
                <td class="px-6 py-4">
                    อัครเดช สุขสวัสดิ์
                </td>
                <td class="px-6 py-4">
                    <!-- เวลา -->
                </td>
                <td class="px-6 py-4">
                    <!-- เวลา -->
                </td>
                <td class="px-6 py-4">
                    <div class="flex items-center">
                        <span class="text-gray-900 rounded bg-indigo-200 px-2 py-0.5">คำร้องแจ้งให้ทราบ</span> 
                    </div>
                </td>
                
                <td class="px-6 py-4">
                    <!-- Modal toggle -->
                    <a href="#" type="button" data-modal-show="editUserModal" class="font-medium text-blue-600 dark:text-blue-500 hover:underline">ตรวจสอบ</a>
                </td>
            </tr>
            <tr class="bg-white border-b dark:bg-gray-800 dark:border-gray-700 hover:bg-gray-50 dark:hover:bg-gray-600">
                
                <th scope="row" class="flex items-center px-6 py-4 font-medium text-gray-900 whitespace-nowrap dark:text-white">
                    
                    <div class="ps-3">
                        <div class="text-base font-semibold">หีไก่</div>
                        <div class="font-normal text-gray-500">ฟหกฟหกฟหกฟหกฟก</div>
                    </div>
                </th>
                <td class="px-6 py-4">
                    อัครเดช สุขสวัสดิ์
                </td>
                <td class="px-6 py-4">
                    <!-- เวลา -->
                </td>
                <td class="px-6 py-4">
                    <!-- เวลา -->
                </td>
                <td class="px-6 py-4">
                    <div class="flex items-center">
                        <span class="text-gray-900 rounded bg-orange-200 px-2 py-0.5">คำร้องขออนุมัติเอกสาร</span>
                    </div>
                </td>
                
                <td class="px-6 py-4">
                    <!-- Modal toggle -->
                    <a href="#" type="button" data-modal-show="editUserModal" class="font-medium text-blue-600 dark:text-blue-500 hover:underline">ตรวจสอบ</a>
                </td>
            </tr>
            <tr class="cursor-pointer bg-white border-b dark:bg-gray-800 dark:border-gray-700 hover:bg-gray-50 dark:hover:bg-gray-600">
                
                <th scope="row" class="cursor-pointer flex items-center px-6 py-4 font-medium text-gray-900 whitespace-nowrap dark:text-white">
                    
                    <div class="ps-3">
                        <div class="text-base font-semibold">ลูกอีไข่เป็ด</div>
                        <div class="font-normal text-gray-500">กกกกกกกกกกกกก</div>
                    </div>
                </th>
                <td class="px-6 py-4">
                    อัครเดช สุขสวัสดิ์
                </td>
                <td class="px-6 py-4">
                    <!-- เวลา -->
                </td>
                <td class="px-6 py-4">
                    <!-- เวลา -->
                </td>
                <td class="px-6 py-4">
                    <div class="flex items-center">
                        <span class="text-gray-900 rounded bg-orange-200 px-2 py-0.5">คำร้องขออนุมัติเอกสาร</span>
                    </div>
                </td>
                
                <td class="px-6 py-4">
                    <!-- Modal toggle -->
                    <a href="#" type="button" data-modal-show="editUserModal" class="font-medium text-blue-600 dark:text-blue-500 hover:underline">ตรวจสอบ</a>
                </td>
            </tr>
            <tr class="bg-white dark:bg-gray-800 hover:bg-gray-50 dark:hover:bg-gray-600">
               
                <th scope="row" class="flex items-center px-6 py-4 font-medium text-gray-900 whitespace-nowrap dark:text-white">
                   
                    <div class="ps-3">
                        <div class="text-base font-semibold">ไข่หีหมา</div>
                        <div class="font-normal text-gray-500">ฟฟฟฟฟฟฟฟฟฟฟฟฟฟฟฟฟฟฟ</div>
                    </div>
                </th>
                <td class="px-6 py-4">
                    อัครเดช สุขสวัสดิ์
                </td>
                <td class="px-6 py-4">
                    <!-- เวลา -->
                </td>
                 <td class="px-6 py-4">
                    <!-- เวลา -->
                </td>
                <td class="px-6 py-4">
                    <div class=" flex items-center">
                       <span class="text-gray-900 rounded bg-orange-200 px-2 py-0.5">คำร้องขออนุมัติเอกสาร</span>
                    </div>
                </td>
               
                <td class="px-6 py-4">
                    <!-- Modal toggle -->
                    <a href="#" type="button" data-modal-show="editUserModal" class="font-medium text-blue-600 dark:text-blue-500 hover:underline">ตรวจสอบ</a>
                </td>
            </tr>
        </tbody>
    </table>
    <!-- Edit user modal -->
    <div id="editUserModal" tabindex="-1" aria-hidden="true" class="fixed top-0 left-0 right-0 z-50 items-center justify-center hidden w-full p-4 overflow-x-hidden overflow-y-auto md:inset-0 h-[calc(100%-1rem)] max-h-full">
        <div class="relative w-full max-w-2xl max-h-full">
            <!-- Modal content -->
            <form class="relative bg-white rounded-lg shadow dark:bg-gray-700">
                <!-- Modal header -->
                <div class="flex items-start justify-between p-4 border-b rounded-t dark:border-gray-600">
                    <h3 class="text-xl font-semibold text-gray-900 dark:text-white">
                        Edit user
                    </h3>
                   <button type="button" class="text-gray-400 bg-transparent hover:bg-gray-200 hover:text-gray-900 rounded-lg text-sm w-8 h-8 ms-auto inline-flex justify-center items-center dark:hover:bg-gray-600 dark:hover:text-white" data-modal-hide="editUserModal">
                    <svg class="w-3 h-3" aria-hidden="true" xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 14 14">
                        <path stroke="currentColor" stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="m1 1 6 6m0 0 6 6M7 7l6-6M7 7l-6 6"/>
                    </svg>
                    <span class="sr-only">Close modal</span>
                </button>
                </div>
                <!-- Modal body -->
                <div class="p-6 space-y-6">
                    <div class="grid grid-cols-6 gap-6">
                        <div class="col-span-6 sm:col-span-3">
                            <label for="first-name" class="block mb-2 text-sm font-medium text-gray-900 dark:text-white">First Name</label>
                            <input type="text" name="first-name" id="first-name" class="shadow-sm bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-600 focus:border-blue-600 block w-full p-2.5 dark:bg-gray-600 dark:border-gray-500 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500" placeholder="Bonnie" required="">
                        </div>
                        <div class="col-span-6 sm:col-span-3">
                            <label for="last-name" class="block mb-2 text-sm font-medium text-gray-900 dark:text-white">Last Name</label>
                            <input type="text" name="last-name" id="last-name" class="shadow-sm bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-600 focus:border-blue-600 block w-full p-2.5 dark:bg-gray-600 dark:border-gray-500 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500" placeholder="Green" required="">
                        </div>
                        <div class="col-span-6 sm:col-span-3">
                            <label for="email" class="block mb-2 text-sm font-medium text-gray-900 dark:text-white">Email</label>
                            <input type="email" name="email" id="email" class="shadow-sm bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-600 focus:border-blue-600 block w-full p-2.5 dark:bg-gray-600 dark:border-gray-500 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500" placeholder="example@company.com" required="">
                        </div>
                        <div class="col-span-6 sm:col-span-3">
                            <label for="phone-number" class="block mb-2 text-sm font-medium text-gray-900 dark:text-white">Phone Number</label>
                            <input type="number" name="phone-number" id="phone-number" class="shadow-sm bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-600 focus:border-blue-600 block w-full p-2.5 dark:bg-gray-600 dark:border-gray-500 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500" placeholder="e.g. +(12)3456 789" required="">
                        </div>
                        <div class="col-span-6 sm:col-span-3">
                            <label for="department" class="block mb-2 text-sm font-medium text-gray-900 dark:text-white">Department</label>
                            <input type="text" name="department" id="department" class="shadow-sm bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-600 focus:border-blue-600 block w-full p-2.5 dark:bg-gray-600 dark:border-gray-500 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500" placeholder="Development" required="">
                        </div>
                        <div class="col-span-6 sm:col-span-3">
                            <label for="company" class="block mb-2 text-sm font-medium text-gray-900 dark:text-white">Company</label>
                            <input type="number" name="company" id="company" class="shadow-sm bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-600 focus:border-blue-600 block w-full p-2.5 dark:bg-gray-600 dark:border-gray-500 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500" placeholder="123456" required="">
                        </div>
                        <div class="col-span-6 sm:col-span-3">
                            <label for="current-password" class="block mb-2 text-sm font-medium text-gray-900 dark:text-white">Current Password</label>
                            <input type="password" name="current-password" id="current-password" class="shadow-sm bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-600 focus:border-blue-600 block w-full p-2.5 dark:bg-gray-600 dark:border-gray-500 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500" placeholder="••••••••" required="">
                        </div>
                        <div class="col-span-6 sm:col-span-3">
                            <label for="new-password" class="block mb-2 text-sm font-medium text-gray-900 dark:text-white">New Password</label>
                            <input type="password" name="new-password" id="new-password" class="shadow-sm bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-600 focus:border-blue-600 block w-full p-2.5 dark:bg-gray-600 dark:border-gray-500 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500" placeholder="••••••••" required="">
                        </div>
                    </div>
                </div>
                <!-- Modal footer -->
                <div class="flex items-center p-6 space-x-3 rtl:space-x-reverse border-t border-gray-200 rounded-b dark:border-gray-600">
                    <button type="submit" class="text-white bg-blue-700 hover:bg-blue-800 focus:ring-4 focus:outline-none focus:ring-blue-300 font-medium rounded-lg text-sm px-5 py-2.5 text-center dark:bg-blue-600 dark:hover:bg-blue-700 dark:focus:ring-blue-800">Save all</button>
                </div>
            </form>
        </div>
    </div>
</div>

    </div>
     
    </main>
</template>
