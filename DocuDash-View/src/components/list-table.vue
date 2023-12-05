<script setup>
import { ref } from "vue";

const data = ref([
  {
    "id": 1,
    "Title": "แผนธุรกิจสำหรับสตาร์ทอัพ",
    "FilePath": "/path/to/file1.pdf",
    "DateAdd": "2023-04-12T00:00:00",
    "DateUpdate": "2023-04-12T12:30:00",
    "Category": "เอกสารภายใน",
    "SecrecyLevel": "ปกติ",
    "Urgency": "ด่วนที่สุด",
    "FromSource": "สตาร์ทอัพ",
    "Status": "ผ่าน",
    "UploadBy": 1
  },
  {
    "id": 2,
    "Title": "เอกสารการสมัครสมาชิก",
    "FilePath": "/path/to/file2.pdf",
    "DateAdd": "2023-04-13T00:00:00",
    "DateUpdate": "2023-04-13T12:30:00",
    "Category": "เอกสารภายนอก",
    "SecrecyLevel": "ลับ",
    "Urgency": "ปกติ",
    "FromSource": "สตาร์ทอัพ",
    "Status": "ผ่าน",
    "UploadBy": 1
  },
  {
    "id": 3,
    "Title": "ข้อเสนอโครงการ",
    "FilePath": "/path/to/file3.pdf",
    "DateAdd": "2023-05-14T00:00:00",
    "DateUpdate": "2023-05-14T12:30:00",
    "Category": "เอกสารภายใน",
    "SecrecyLevel": "ลับที่สุด",
    "Urgency": "ด่วน",
    "FromSource": "สตาร์ทอัพ",
    "Status": "ไม่ผ่าน",
    "UploadBy": 1
  },
  {
    "id": 4,
    "Title": "เอกสารที่ 4",
    "FilePath": "/path/to/file4.pdf",
    "DateAdd": "2023-04-02T00:00:00",
    "DateUpdate": "2023-04-02T12:30:00",
    "Category": "เอกสารภายนอก",
    "SecrecyLevel": "ปกติ",
    "Urgency": "ด่วน",
    "FromSource": "สตาร์ทอัพ",
    "Status": "ผ่าน",
    "UploadBy": 1
  },
  {
    "id": 5,
    "Title": "เอกสารที่ 5",
    "FilePath": "/path/to/file5.pdf",
    "DateAdd": "2022-04-03T00:00:00",
    "DateUpdate": "2022-04-03T12:30:00",
    "Category": "เอกสารภายใน",
    "SecrecyLevel": "ปกปิด",
    "Urgency": "ปกติ",
    "FromSource": "สตาร์ทอัพ",
    "Status": "ผ่าน",
    "UploadBy": 1
  }
]);



</script>

<template>
  <div class="shadow-md sm:rounded-lg table-container">
    
    
    <table class="w-full text-sm text-left rtl:text-right text-white">
      <thead class="text-xs text-white uppercase bg-green-800">
        <tr>
          <th scope="col" class="p-4">
            <div class="flex items-center ">
              <!-- <input
                id="checkbox-all"
                type="checkbox"
                class="w-4 h-4 text-blue-600 bg-gray-100 border-gray-300 rounded focus:ring-blue-500 dark:focus:ring-blue-600 
                dark:ring-offset-gray-800 dark:focus:ring-offset-gray-800 focus:ring-2 dark:bg-gray-700 dark:border-gray-600"
              /> -->
              <label class="">รายการ</label>
            </div>
          </th>
          <th scope="col" class="px-6 py-3">ชื่อเรื่อง</th>
          <th scope="col" class="px-6 py-3">รายละเอียด</th>
          <th scope="col" class="px-6 py-3">ความเร่งด่วน</th>
          <th scope="col" class="px-6 py-3">หมวดหมู่</th>
          <th scope="col" class="px-6 py-3">ลงวันที่</th>
          <th scope="col" class="px-6 py-3">จัดการ  
            <button class="m-1 "  data-popover-target="popover-description" data-popover-placement="bottom-end" type="button">
              <svg class="w-4 h-4 ms-2 text-gray-400 hover:text-gray-500" aria-hidden="true" fill="currentColor" viewBox="0 0 20 20" xmlns="http://www.w3.org/2000/svg">
                <path fill-rule="evenodd" d="M18 10a8 8 0 11-16 0 8 8 0 0116 0zm-8-3a1 1 0 00-.867.5 1 1 0 11-1.731-1A3 3 0 0113 8a3.001 3.001 0 01-2 2.83V11a1 1 0 11-2 0v-1a1 1 0 011-1 1 1 0 100-2zm0 8a1 1 0 100-2 1 1 0 000 2z" clip-rule="evenodd"></path></svg><span class="sr-only">Show information</span></button>
                  <div data-popover id="popover-description" role="tooltip" class="absolute z-10 invisible inline-block text-sm text-gray-500 transition-opacity duration-300 bg-white border border-gray-200 rounded-lg shadow-sm opacity-0 w-72 dark:bg-gray-800 dark:border-gray-600 dark:text-gray-400">
                  <div class="p-3 space-y-2">
        <h3 class="font-semibold text-gray-900 dark:text-white">การแก้ไขข้อมูล</h3>
        <p>ท่านสามารถแก้ไขข้อมูลในเอกสารได้ด้วยการคลิ๊กที่รูปดินสอ แล้วทำการแก้ไขข้อมูลตามที่ต้องการจากนั้นกดตกลง</p>
        <h3 class="font-semibold text-gray-900 dark:text-white">การลบข้อมูล</h3>
        <p>ท่านสามารถลบเอกสารได้ที่ปุ่มถังขยะ <span class="important">หากลบแล้วจะไม่สามารถกู้คืนได้ กรุณาตรวจเช็คก่อนดำเนินการ</span></p>
        <!-- <a href="#" class="flex items-center font-medium text-blue-600 dark:text-blue-500 dark:hover:text-blue-600 hover:text-blue-700 hover:underline">Read more <svg class="w-2 h-2 ms-1.5 rtl:rotate-180" aria-hidden="true" xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 6 10">
    <path stroke="currentColor" stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="m1 9 4-4-4-4"/>
  </svg></a> -->
    </div>
    <div data-popper-arrow></div>
</div></th>
        </tr>
      </thead>
      <tbody v-for="(thisdoc, index) in data">
        <tr class="bg-white text-gray-900 border-b border-gray-700 hover:bg-gray-600 hover:text-white">
          <td class="w-4 p-4">
            <!-- <div class="flex items-center">
              <input
                id="checkbox-table-1"
                
                class="w-4 h-4 text-blue-600 bg-gray-100 border-gray-300 rounded focus:ring-blue-500 
                dark:focus:ring-blue-600 dark:ring-offset-gray-800 dark:focus:ring-offset-gray-800 focus:ring-2 
                dark:bg-gray-700 dark:border-gray-600"
              />
              <label for="checkbox-table-1" class="sr-only">checkbox</label>
            </div> -->
            {{ index + 1 }}
          </td>
          <th scope="row" class="px-6 py-4 font-medium ">
            {{ thisdoc.Title }}

          </th>
          <td class="px-6 py-4">{{ thisdoc.Category }}</td>
          <td class="px-6 py-4">{{ thisdoc.Urgency }}</td>
          <td class="px-6 py-4">{{ thisdoc.Category }}</td>
          <td class="px-6 py-4">{{ thisdoc.DateAdd }}</td>
          <td class="px-6 py-4">
            <div>




              <!-- hamberger -->
              <button type="button" data-modal-target="editUserModal"  data-modal-show="editUserModal"  class="m-1" > <a href="#" class=" font-medium text-amber-400 hover:underline"> <svg
                    class="w-6 h-6" id="Icons" xmlns="http://www.w3.org/2000/svg"
                    xmlns:xlink="http://www.w3.org/1999/xlink" viewBox="0 0 32 32" xml:space="preserve" fill="#ffbb00"
                    stroke="#ffbb00">
                    <g id="SVGRepo_bgCarrier" stroke-width="0"></g>
                    <g id="SVGRepo_tracerCarrier" stroke-linecap="round" stroke-linejoin="round"></g>
                    <g id="SVGRepo_iconCarrier">
                      <path class="st0" d="M5,21l-2,8l8-2L30,8l0,0c-0.6-3-3-5.4-6-6l0,0L5,21z"></path>
                      <path class="st0" d="M26,12L26,12c-0.6-3-3-5.4-6-6l0,0"></path>
                      <line class="st0" x1="9" y1="23" x2="21" y2="11"></line>
                    </g>
                  </svg>
                </a></button>

                
                




              <button type="button" class="m-1 px-3"> <a href="#" class="font-medium text-amber-400 hover:underline"> <svg
                    class="w-7 h-7 " viewBox="-2.4 -2.4 28.80 28.80" fill="none" xmlns="http://www.w3.org/2000/svg"
                    stroke="#000000" stroke-width="0.00024000000000000003" transform="rotate(0)">
                    <g id="SVGRepo_bgCarrier" stroke-width="0"></g>
                    <g id="SVGRepo_tracerCarrier" stroke-linecap="round" stroke-linejoin="round" stroke="#CCCCCC"
                      stroke-width="0.048"></g>
                    <g id="SVGRepo_iconCarrier">
                      <path d="M12 2.75C11.0215 2.75 10.1871 3.37503 9.87787 4.24993C9.73983 4.64047 9.31134 4.84517 8.9208 4.70713C8.53026 4.56909 8.32557 4.1406 8.46361 3.75007C8.97804 2.29459 10.3661 1.25 12 1.25C13.634 1.25 15.022 2.29459 15.5365 3.75007C15.6745 4.1406 15.4698 4.56909 15.0793 4.70713C14.6887 4.84517 14.2602 4.64047 14.1222 
  4.24993C13.813 3.37503 12.9785 2.75 12 2.75Z" fill="#ff0000"></path>
                      <path
                        d="M2.75 6C2.75 5.58579 3.08579 5.25 3.5 5.25H20.5001C20.9143 5.25 21.2501 5.58579 21.2501 6C21.2501 6.41421 20.9143 6.75 20.5001 6.75H3.5C3.08579 6.75 2.75 6.41421 2.75 6Z"
                        fill="#ff0000"></path>
                      <path d="M5.91508 8.45011C5.88753 8.03681 5.53015 7.72411 5.11686 7.75166C4.70356 7.77921 4.39085 8.13659 4.41841 8.54989L4.88186 15.5016C4.96735 16.7844 5.03641 17.8205 5.19838 18.6336C5.36678 19.4789 5.6532 20.185 6.2448 20.7384C6.83639 21.2919 7.55994 21.5307 8.41459 21.6425C9.23663 21.75 10.2751 21.75 11.5607 21.75H12.4395C13.7251 21.75 14.7635 21.75 15.5856 21.6425C16.4402 21.5307 17.1638 21.2919 17.7554 20.7384C18.347 20.185 18.6334 19.4789 18.8018 18.6336C18.9637 17.8205 19.0328 16.7844 19.1183 15.5016L19.5818 8.54989C19.6093 8.13659 19.2966 7.77921 18.8833 7.75166C18.47 7.72411 18.1126 8.03681 18.0851 8.45011L17.6251 15.3492C17.5353 16.6971 17.4712 17.6349 17.3307 18.3405C17.1943 19.025 17.004 19.3873 16.7306 19.6431C16.4572 19.8988 16.083 20.0647 15.391 20.1552C14.6776 20.2485 13.7376 20.25 12.3868 20.25H11.6134C10.2626 20.25 9.32255 20.2485 8.60915 20.1552C7.91715 20.0647 7.54299 19.8988 7.26957 19.6431C6.99616 19.3873 6.80583 19.025 6.66948 18.3405C6.52891 
  17.6349 6.46488 16.6971 6.37503 15.3492L5.91508 8.45011Z" fill="#ff0000"></path>
                      <path
                        d="M9.42546 10.2537C9.83762 10.2125 10.2051 10.5132 10.2464 10.9254L10.7464 15.9254C10.7876 16.3375 10.4869 16.7051 10.0747 
  16.7463C9.66256 16.7875 9.29502 16.4868 9.25381 16.0746L8.75381 11.0746C8.71259 10.6625 9.0133 10.2949 9.42546 10.2537Z"
                        fill="#ff0000"></path>
                      <path
                        d="M15.2464 11.0746C15.2876 10.6625 14.9869 10.2949 14.5747 10.2537C14.1626 10.2125 13.795 10.5132 13.7538 10.9254L13.2538 15.9254C13.2126 16.3375 13.5133 16.7051 13.9255 16.7463C14.3376 16.7875 14.7051 16.4868 14.7464 16.0746L15.2464 11.0746Z"
                        fill="#ff0000"></path>
                    </g>
                  </svg></a></button>

                  
                  

            </div>
          </td>
        </tr>
      </tbody>
    </table>

    <!-- test alert -->
    <!-- <div class="flex items-center p-4 mb-4 text-sm text-green-800 border border-green-300 rounded-lg bg-green-50 dark:bg-gray-800 dark:text-green-400 dark:border-green-800" role="alert">
  <svg class="flex-shrink-0 inline w-4 h-4 me-3" aria-hidden="true" xmlns="http://www.w3.org/2000/svg" fill="currentColor" viewBox="0 0 20 20">
    <path d="M10 .5a9.5 9.5 0 1 0 9.5 9.5A9.51 9.51 0 0 0 10 .5ZM9.5 4a1.5 1.5 0 1 1 0 3 1.5 1.5 0 0 1 0-3ZM12 15H8a1 1 0 0 1 0-2h1v-3H8a1 1 0 0 1 0-2h2a1 1 0 0 1 1 1v4h1a1 1 0 0 1 0 2Z"/>
  </svg>
  <span class="sr-only">Info</span>
  <div>
    <span class="font-medium">Success alert!</span> Change a few things up and try submitting again.
  </div>
</div>
 -->



 <div class="relative overflow-x-auto shadow-md sm:rounded-lg "> 
    <!-- Edit user modal -->
    <div id="editUserModal" tabindex="-1" aria-hidden="true"  class="fixed top-0 left-0 right-0 z-50 items-center justify-center hidden w-full p-4 overflow-x-hidden overflow-y-auto md:inset-0 h-[calc(100%-1rem)] max-h-full animate__animated animate__fadeInUpBig transition duration-300 delay-150">
        <div class="relative w-full max-w-2xl max-h-full">
            <!-- Modal content -->
            <form class="relative bg-white rounded-lg shadow dark:bg-gray-700 ">
                <!-- Modal header -->
                <div class="flex items-start justify-between p-4 border-b rounded-t dark:border-gray-600 ">
                    <h3 class="text-xl font-semibold text-gray-900 dark:text-white">
                        แก้ไขเอกสาร
                    </h3>
                   <button type="button" class="text-gray-400 bg-transparent hover:bg-gray-200 hover:text-gray-900 rounded-lg text-sm w-8 h-8 ms-auto inline-flex justify-center items-center dark:hover:bg-gray-600 dark:hover:text-white " data-modal-hide="editUserModal">
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
                            <label  class="block mb-2 text-sm font-medium text-slate-950 dark:text-white">เลขที่</label>
                            <input disabled readonly="" type="text" name="first-name" id="first-name" class=" shadow-sm bg-gray-200 border border-gray-300 text-slate-950 text-sm rounded-lg focus:ring-blue-600 focus:border-blue-600 block w-28 p-1 dark:bg-gray-600 dark:border-gray-500 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500" placeholder="4888" >
                        </div>
                        <br>
                        <div class="col-span-6 sm:col-span-3">
                            <label for="first-name" class="block mb-2 text-sm font-medium text-gray-900 dark:text-white">เรื่อง</label>
                            <input  type="text"   class=" shadow-sm bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-600 focus:border-blue-600 block w-full p-2.5 dark:bg-gray-600 dark:border-gray-500 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500" placeholder="ชื่อเรื่อง" required="">
                        </div>
                        <div class="col-span-6 sm:col-span-3">
                            <label class="block mb-2 text-sm font-medium text-gray-900 dark:text-white">ส่งจาก</label>
                            <input type="text"   class="shadow-sm bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-600 focus:border-blue-600 block w-full p-2.5 dark:bg-gray-600 dark:border-gray-500 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500" placeholder="ชื่อผู้ส่ง" required="">
                        </div>
                        <div class="col-span-6 sm:col-span-3">
                            <label  class="block mb-2 text-sm font-medium text-gray-900 dark:text-white">รายละเอียด</label>
                            <input   class="shadow-sm bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-600 focus:border-blue-600 block w-full p-2.5 dark:bg-gray-600 dark:border-gray-500 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500" placeholder="กรอกรายละเอียดของเอกสาร" required="">
                        </div>
                        <div class="col-span-6 sm:col-span-3">
                            <label  class="block mb-2 text-sm font-medium text-gray-900 dark:text-white">แผนก</label>
                            <select  class="shadow-sm bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500">
                            <option class="text-gray-900 text-sm" selected>แผนกของผู้ส่ง</option>
                            <option value="US">AAAA</option>
                            <option value="CA">BBBB</option>
                            <option value="FR">CCCC</option>
                            <option value="DE">DDDD</option>
                            </select>
                            <!-- กล่องเดิม -->
                            <!-- <input type="number" name="phone-number" id="phone-number" class="shadow-sm bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-600 focus:border-blue-600 block w-full p-2.5 dark:bg-gray-600 dark:border-gray-500 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500" placeholder="แผนกของผู้ส่ง" required=""> -->
                        </div>
                        <!-- <div class="col-span-6 sm:col-span-3">
                            <label for="phone-number" class="block mb-2 text-sm font-medium text-gray-900 dark:text-white">Phone Number</label>
                            <input type="number" name="phone-number" id="phone-number" class="shadow-sm bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-600 focus:border-blue-600 block w-full p-2.5 dark:bg-gray-600 dark:border-gray-500 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500" placeholder="แผนกของผู้ส่ง" required="">
                        </div> -->
                        <div class="col-span-6 sm:col-span-3">
                            <label  class="block mb-2 text-sm font-medium text-gray-900 dark:text-white">หมวดหมู่</label>
                            <select  class="shadow-sm bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500">
                            <option class="text-gray-900 text-sm" selected>เลือกหมวดหมู่เอกสาร</option>
                            <option value="US">AAAA</option>
                            <option value="CA">BBBB</option>
                            <option value="FR">CCCC</option>
                            <option value="DE">DDDD</option>
                            </select>                       
                           </div>
                           <div class="col-span-6 sm:col-span-3">
                            <label for="current-password" class="block mb-2 text-sm font-medium text-slate-950 dark:text-white">โทรศัพท์</label>
                            <input  type="number" name="phone-number"  class="shadow-sm bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-600 focus:border-blue-600 block w-full p-2.5 dark:bg-gray-600 dark:border-gray-500 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500" placeholder="08X-XXX-XXXX" required="">
                        </div>

                        <div class="col-span-6 sm:col-span-3">
                            <label for="email" class="block mb-2 text-sm font-medium text-gray-900 dark:text-white">อีเมล</label>
                            <input type="email" name="email" id="email" class="shadow-sm bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-600 focus:border-blue-600 block w-full p-2.5 dark:bg-gray-600 dark:border-gray-500 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500" placeholder="Example@gmail.com" required="">
                        </div>

                          <br>
                           <div class="col-span-6 sm:col-span-3">
                            <label for="first-name" class="block mb-2 text-sm font-medium text-gray-900 dark:text-white">ความเร่งด่วน</label>
                            <input  disabled readonly="" type="text"  id="urgency" class=" shadow-sm bg-gray-200 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-600 focus:border-blue-600 block w-full p-2.5 dark:bg-gray-600 dark:border-gray-500 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500" placeholder="ด่วน" required="">
                        </div>
                        <div class="col-span-6 sm:col-span-3">
                            <label for="first-name" class="block mb-2 text-sm font-medium text-gray-900 dark:text-white">ชั้นความลับ</label>
                            <input  disabled readonly="" type="text"  id="urgency" class=" shadow-sm bg-gray-200 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-600 focus:border-blue-600 block w-full p-2.5 dark:bg-gray-600 dark:border-gray-500 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500" placeholder="ลับ" required="">
                        </div>
                        <!-- <div class="col-span-6 sm:col-span-3">
                            <label for="company" class="block mb-2 text-sm font-medium text-gray-900 dark:text-white">สาขา</label>
                            <input type="number" name="company" id="company" class="shadow-sm bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-600 focus:border-blue-600 block w-full p-2.5 dark:bg-gray-600 dark:border-gray-500 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500" placeholder="กรอก" required="">
                        </div> -->
                        <!-- <div class="col-span-6 sm:col-span-3">
                            <label for="current-password" class="block mb-2 text-sm font-medium text-gray-900 dark:text-white">pass</label>
                            <input  name="current-password" id="current-password" class="shadow-sm bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-600 focus:border-blue-600 block w-full p-2.5 dark:bg-gray-600 dark:border-gray-500 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500" placeholder="••••••••" required="">
                        </div> -->
                        
                        <!-- <div class="col-span-6 sm:col-span-3">
                            <label for="new-password" class="block mb-2 text-sm font-medium text-gray-900 dark:text-white">New Password</label>
                            <input type="password" name="new-password" id="new-password" class="shadow-sm bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-600 focus:border-blue-600 block w-full p-2.5 dark:bg-gray-600 dark:border-gray-500 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500" placeholder="••••••••" required="">
                        </div> -->
                        <div class="col-span-6 sm:col-span-3">
                          <label for="first-name" class="block mb-2 text-sm font-medium text-gray-900 dark:text-white">ชื่อผู้รับ</label>
                          
                          <input  type="text"   class=" shadow-sm bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-600 focus:border-blue-600 block w-full p-2.5 dark:bg-gray-600 dark:border-gray-500 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500" placeholder="กรอกชื่อผู้รับ/ส่งถึงใคร" required="">
                          


                          <div class="block my-2 mb-2 text-sm font-medium text-gray-900 border border-gray-300 rounded-lg dark:text-white">
                          <a  aria-current="true" class="block w-full px-4 py-2 text-white bg-slate-600 border-b border-gray-200 rounded-t-lg  dark:bg-gray-800 dark:border-gray-600">
                           ชื่อผู้รับปัจจุบัน
                           </a>
                          <a  class="  block w-full px-4 py-2 border-b border-gray-200  hover:bg-gray-100 hover:text-blue-700 focus:outline-none focus:ring-2 focus:ring-blue-700 focus:text-blue-700 dark:border-gray-600 dark:hover:bg-gray-600 dark:hover:text-white dark:focus:ring-gray-500 dark:focus:text-white">
                           Nattakit Rattanasiriprom
                           <button type="button" aria-hidden="true" class="m-1  ">
                           <svg class="  w-4 h-4 " viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg"><g id="SVGRepo_bgCarrier" stroke-width="0"></g><g id="SVGRepo_tracerCarrier" stroke-linecap="round" stroke-linejoin="round"></g><g id="SVGRepo_iconCarrier"> <path d="M15 12.75C15.4142 12.75 15.75 12.4142 15.75 12C15.75 11.5858 15.4142 11.25 15 11.25H9C8.58579 11.25 8.25 11.5858 8.25 12C8.25 12.4142 8.58579 12.75 9 12.75H15Z" fill="#ff0000"></path> <path fill-rule="evenodd" clip-rule="evenodd" d="M12.0574 1.25H11.9426C9.63424 1.24999 7.82519 1.24998 6.41371 1.43975C4.96897 1.63399 3.82895 2.03933 2.93414 2.93414C2.03933 3.82895 1.63399 4.96897 1.43975 6.41371C1.24998 7.82519 1.24999 9.63422 1.25 11.9426V12.0574C1.24999 14.3658 1.24998 16.1748 1.43975 17.5863C1.63399 19.031 2.03933 20.1711 2.93414 21.0659C3.82895 21.9607 4.96897 22.366 6.41371 22.5603C7.82519 22.75 9.63423 22.75 11.9426 22.75H12.0574C14.3658 22.75 16.1748 22.75 17.5863 22.5603C19.031 22.366 20.1711 21.9607 21.0659 21.0659C21.9607 20.1711 22.366 19.031 22.5603 17.5863C22.75 16.1748 22.75 14.3658 22.75 12.0574V11.9426C22.75 9.63423 22.75 7.82519 22.5603 6.41371C22.366 4.96897 21.9607 3.82895 21.0659 2.93414C20.1711 2.03933 19.031 1.63399 17.5863 1.43975C16.1748 1.24998 14.3658 1.24999 12.0574 1.25ZM3.9948 3.9948C4.56445 3.42514 5.33517 3.09825 6.61358 2.92637C7.91356 2.75159 9.62177 2.75 12 2.75C14.3782 2.75 16.0864 2.75159 17.3864 2.92637C18.6648 3.09825 19.4355 3.42514 20.0052 3.9948C20.5749 4.56445 20.9018 5.33517 21.0736 6.61358C21.2484 7.91356 21.25 9.62177 21.25 12C21.25 14.3782 21.2484 16.0864 21.0736 17.3864C20.9018 18.6648 20.5749 19.4355 20.0052 20.0052C19.4355 20.5749 18.6648 20.9018 17.3864 21.0736C16.0864 21.2484 14.3782 21.25 12 21.25C9.62177 21.25 7.91356 21.2484 6.61358 21.0736C5.33517 20.9018 4.56445 20.5749 3.9948 20.0052C3.42514 19.4355 3.09825 18.6648 2.92637 17.3864C2.75159 16.0864 2.75 14.3782 2.75 12C2.75 9.62177 2.75159 7.91356 2.92637 6.61358C3.09825 5.33517 3.42514 4.56445 3.9948 3.9948Z" fill="#ff0000"></path> </g></svg>
                           </button>
                          </a>
                           <a  class="block w-full px-4 py-2 border-b border-gray-200  hover:bg-gray-100 hover:text-blue-700 focus:outline-none focus:ring-2 focus:ring-blue-700 focus:text-blue-700 dark:border-gray-600 dark:hover:bg-gray-600 dark:hover:text-white dark:focus:ring-gray-500 dark:focus:text-white">
                          Harry Jamorant
                          <button type="button" aria-hidden="true" class="m-1 ">
                           <svg class=" w-4 h-4 " viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg"><g id="SVGRepo_bgCarrier" stroke-width="0"></g><g id="SVGRepo_tracerCarrier" stroke-linecap="round" stroke-linejoin="round"></g><g id="SVGRepo_iconCarrier"> <path d="M15 12.75C15.4142 12.75 15.75 12.4142 15.75 12C15.75 11.5858 15.4142 11.25 15 11.25H9C8.58579 11.25 8.25 11.5858 8.25 12C8.25 12.4142 8.58579 12.75 9 12.75H15Z" fill="#ff0000"></path> <path fill-rule="evenodd" clip-rule="evenodd" d="M12.0574 1.25H11.9426C9.63424 1.24999 7.82519 1.24998 6.41371 1.43975C4.96897 1.63399 3.82895 2.03933 2.93414 2.93414C2.03933 3.82895 1.63399 4.96897 1.43975 6.41371C1.24998 7.82519 1.24999 9.63422 1.25 11.9426V12.0574C1.24999 14.3658 1.24998 16.1748 1.43975 17.5863C1.63399 19.031 2.03933 20.1711 2.93414 21.0659C3.82895 21.9607 4.96897 22.366 6.41371 22.5603C7.82519 22.75 9.63423 22.75 11.9426 22.75H12.0574C14.3658 22.75 16.1748 22.75 17.5863 22.5603C19.031 22.366 20.1711 21.9607 21.0659 21.0659C21.9607 20.1711 22.366 19.031 22.5603 17.5863C22.75 16.1748 22.75 14.3658 22.75 12.0574V11.9426C22.75 9.63423 22.75 7.82519 22.5603 6.41371C22.366 4.96897 21.9607 3.82895 21.0659 2.93414C20.1711 2.03933 19.031 1.63399 17.5863 1.43975C16.1748 1.24998 14.3658 1.24999 12.0574 1.25ZM3.9948 3.9948C4.56445 3.42514 5.33517 3.09825 6.61358 2.92637C7.91356 2.75159 9.62177 2.75 12 2.75C14.3782 2.75 16.0864 2.75159 17.3864 2.92637C18.6648 3.09825 19.4355 3.42514 20.0052 3.9948C20.5749 4.56445 20.9018 5.33517 21.0736 6.61358C21.2484 7.91356 21.25 9.62177 21.25 12C21.25 14.3782 21.2484 16.0864 21.0736 17.3864C20.9018 18.6648 20.5749 19.4355 20.0052 20.0052C19.4355 20.5749 18.6648 20.9018 17.3864 21.0736C16.0864 21.2484 14.3782 21.25 12 21.25C9.62177 21.25 7.91356 21.2484 6.61358 21.0736C5.33517 20.9018 4.56445 20.5749 3.9948 20.0052C3.42514 19.4355 3.09825 18.6648 2.92637 17.3864C2.75159 16.0864 2.75 14.3782 2.75 12C2.75 9.62177 2.75159 7.91356 2.92637 6.61358C3.09825 5.33517 3.42514 4.56445 3.9948 3.9948Z" fill="#ff0000"></path> </g></svg>
                           </button>
                          </a>
                          <a  class="block w-full px-4 py-2 rounded-b-lg  hover:bg-gray-100 hover:text-blue-700 focus:outline-none focus:ring-2 focus:ring-blue-700 focus:text-blue-700 dark:border-gray-600 dark:hover:bg-gray-600 dark:hover:text-white dark:focus:ring-gray-500 dark:focus:text-white">
                          Downy Hurnany
                          <button type="button" aria-hidden="true" class="m-1 ">
                           <svg class=" w-4 h-4 " viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg"><g id="SVGRepo_bgCarrier" stroke-width="0"></g><g id="SVGRepo_tracerCarrier" stroke-linecap="round" stroke-linejoin="round"></g><g id="SVGRepo_iconCarrier"> <path d="M15 12.75C15.4142 12.75 15.75 12.4142 15.75 12C15.75 11.5858 15.4142 11.25 15 11.25H9C8.58579 11.25 8.25 11.5858 8.25 12C8.25 12.4142 8.58579 12.75 9 12.75H15Z" fill="#ff0000"></path> <path fill-rule="evenodd" clip-rule="evenodd" d="M12.0574 1.25H11.9426C9.63424 1.24999 7.82519 1.24998 6.41371 1.43975C4.96897 1.63399 3.82895 2.03933 2.93414 2.93414C2.03933 3.82895 1.63399 4.96897 1.43975 6.41371C1.24998 7.82519 1.24999 9.63422 1.25 11.9426V12.0574C1.24999 14.3658 1.24998 16.1748 1.43975 17.5863C1.63399 19.031 2.03933 20.1711 2.93414 21.0659C3.82895 21.9607 4.96897 22.366 6.41371 22.5603C7.82519 22.75 9.63423 22.75 11.9426 22.75H12.0574C14.3658 22.75 16.1748 22.75 17.5863 22.5603C19.031 22.366 20.1711 21.9607 21.0659 21.0659C21.9607 20.1711 22.366 19.031 22.5603 17.5863C22.75 16.1748 22.75 14.3658 22.75 12.0574V11.9426C22.75 9.63423 22.75 7.82519 22.5603 6.41371C22.366 4.96897 21.9607 3.82895 21.0659 2.93414C20.1711 2.03933 19.031 1.63399 17.5863 1.43975C16.1748 1.24998 14.3658 1.24999 12.0574 1.25ZM3.9948 3.9948C4.56445 3.42514 5.33517 3.09825 6.61358 2.92637C7.91356 2.75159 9.62177 2.75 12 2.75C14.3782 2.75 16.0864 2.75159 17.3864 2.92637C18.6648 3.09825 19.4355 3.42514 20.0052 3.9948C20.5749 4.56445 20.9018 5.33517 21.0736 6.61358C21.2484 7.91356 21.25 9.62177 21.25 12C21.25 14.3782 21.2484 16.0864 21.0736 17.3864C20.9018 18.6648 20.5749 19.4355 20.0052 20.0052C19.4355 20.5749 18.6648 20.9018 17.3864 21.0736C16.0864 21.2484 14.3782 21.25 12 21.25C9.62177 21.25 7.91356 21.2484 6.61358 21.0736C5.33517 20.9018 4.56445 20.5749 3.9948 20.0052C3.42514 19.4355 3.09825 18.6648 2.92637 17.3864C2.75159 16.0864 2.75 14.3782 2.75 12C2.75 9.62177 2.75159 7.91356 2.92637 6.61358C3.09825 5.33517 3.42514 4.56445 3.9948 3.9948Z" fill="#ff0000"></path> </g></svg>
                           </button>
                          </a>
                        </div>
                        </div>


                        

                          
                        <div class="col-span-6 sm:col-span-3">
                        <label class="block mb-2 text-sm font-medium text-gray-900 dark:text-white " for="small_size">แนบไฟล์เอกสาร</label>
                        <input class="block w-full mb-3 text-xs text-gray-900 border border-gray-300 rounded-lg cursor-pointer bg-gray-50 dark:text-gray-400 focus:outline-none dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400" id="small_size" type="file">
                        
                        <div class="block my-1 mb-2 text-sm font-medium text-gray-900 border border-gray-300 rounded-lg dark:text-white">
                          <a  aria-current="true" class="block w-full px-4 py-2 text-white bg-orange-700 border-b border-gray-200 rounded-t-lg  dark:bg-gray-800 dark:border-gray-600">
                           ไฟล์ที่แนบอยู่ปัจจุบัน
                           </a>
                          <a   class="block w-full  px-4 py-2 border-b border-gray-200  hover:bg-gray-100 hover:text-blue-700 focus:outline-none focus:ring-2 focus:ring-blue-700 focus:text-blue-700 dark:border-gray-600 dark:hover:bg-gray-600 dark:hover:text-white dark:focus:ring-gray-500 dark:focus:text-white">
                            <span class="cursor-pointer" href="#">เอกสารลับ.pdf </span>
                          </a>
                           <a   class="block w-full  px-4 py-2 border-b border-gray-200  hover:bg-gray-100 hover:text-blue-700 focus:outline-none focus:ring-2 focus:ring-blue-700 focus:text-blue-700 dark:border-gray-600 dark:hover:bg-gray-600 dark:hover:text-white dark:focus:ring-gray-500 dark:focus:text-white">
                          <span class="cursor-pointer" href="#">เอกสารงานธุรการ.docx</span>                   
                          </a>                          
                        </div>                
                      </div>                         
                    </div>
                   
                </div>
                <!-- Modal footer -->
                <div class="flex items-center p-6 space-x-3 rtl:space-x-reverse border-t border-gray-200 rounded-b dark:border-gray-600">
                    <button type="submit" class="text-white bg-blue-700 hover:bg-blue-800 focus:ring-4 focus:outline-none focus:ring-blue-300 font-medium rounded-lg text-sm px-5 py-2.5 text-center dark:bg-blue-600 dark:hover:bg-blue-700 dark:focus:ring-blue-800">บันทึกข้อมูล</button>
                </div>


            </form>
        </div>
    </div>
</div>
<!-- End of edit user modal -->
</div>
</template>

<style>.table-container {
  width: 100%;
  margin: 0 auto;

}

.st0 {
  fill: none;
  stroke: #ffb700;
  stroke-width: 2;
  stroke-linecap: round;
  stroke-linejoin: round;
  stroke-miterlimit: 10;
}

.important {
  color: red;
  font-weight: bold;
}



</style>
