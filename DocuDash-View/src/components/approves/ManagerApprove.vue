<script setup>
import { ref } from 'vue'
import { FwbButton, FwbModal } from 'flowbite-vue'

const isShowModal = ref(false)

function closeModal () {
  isShowModal.value = false
}
function showModal () {
  isShowModal.value = true
}
</script>

<template>

  <div class="w-full relative overflow-x-auto shadow-md ">
    <!-- หัวตาราง -->

    <div class="bg-slate-300 flex items-center justify-between  md:flex-row flex-wrap space-y-4 md:space-y-0 py-4 bg-white dark:bg-gray-900">
      <div>
        <div class="m-3 mb-6" >
          <h5 class="mr-3 font-semibold dark:text-white text-3xl">รายการเอกสารที่ขออนุมัติ</h5>
          <p class="text-gray-500 ">รวมรายการเอกสารและโครงการที่ท่านต้องอนุมัติทั้งหมด</p>
        </div>
        <button id="dropdownActionButton" data-dropdown-toggle="dropdownAction" class="ml-2 inline-flex items-center text-gray-700 bg-white border border-gray-300 focus:outline-none hover:bg-gray-100 focus:ring-4 focus:ring-gray-100 font-medium rounded-lg text-sm px-3 py-1.5 dark:bg-gray-800 dark:text-gray-400 dark:border-gray-600 dark:hover:bg-gray-700 dark:hover:border-gray-600 dark:focus:ring-gray-700" type="button">
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
          <a href="#" type="button"  class="font-medium text-blue-600 dark:text-blue-500 hover:underline">ตรวจสอบ</a>
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
          <a href="#" type="button"  class="font-medium text-blue-600 dark:text-blue-500 hover:underline">ตรวจสอบ</a>
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
          <a href="#" type="button"  class="font-medium text-blue-600 dark:text-blue-500 hover:underline">ตรวจสอบ</a>
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
          <a href="#" type="button"  class="font-medium text-blue-600 dark:text-blue-500 hover:underline">ตรวจสอบ</a>
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
          <a href="#" type="button" class="font-medium text-blue-600 dark:text-blue-500 hover:underline">ตรวจสอบ</a>
        </td>
      </tr>
      </tbody>
    </table>

  </div>

</template>

<style scoped>

</style>