<script setup>
import { RouterLink } from 'vue-router';
import { ref } from 'vue';


</script>
 
<template>
    <div class="flex   min-h-screen ">
        <div class="w-64  bg-green-950 ">
    <!-- **** ปุ่มใหญ่จัดการเอกสารเขียวๆ **** -->
            <div class="px-6 pt-8 ">
                <button id="side1" data-dropdown-toggle="dropdown" class=" text-white bg-lime-500 hover:bg-lime-600   rounded-lg text-sm px-8 py-2.5 text-center inline-flex items-center " type="button">
    จัดการเอกสาร <svg class="w-2.5 h-2.5 ms-3" aria-hidden="true" xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 10 6">
<path stroke="currentColor" stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="m1 1 4 4 4-4"/>
</svg>
</button>

<!--sidebar Dropdown menu -->
<div id="dropdown" class="z-10 hidden bg-white divide-y divide-gray-100 rounded-lg shadow w-44 dark:bg-gray-700    ">
    <ul class="rounded-2xl py-2 text-sm text-gray-700 bg-lime-100  dark:text-gray-200" aria-labelledby="dropdownDefaultButton">
      <li>
        <div>
             <!-- **** ปุ่มเข้าหน้าสร้าง **** -->
             <a href="/add" class="flex block px-2 py-2 font-bold hover:bg-lime-300 bg-lime-400 dark:hover:bg-gray-600 dark:hover:text-white">
            <svg class="h-5 w-5 mr-4"  viewBox="0 0 24 24" xmlns="http://www.w3.org/2000/svg" fill="#000000"><g id="SVGRepo_bgCarrier" stroke-width="0"></g><g id="SVGRepo_tracerCarrier" stroke-linecap="round" stroke-linejoin="round"></g><g id="SVGRepo_iconCarrier"> <rect x="0" fill="none" width="24" height="24"></rect> <g> <path d="M21 14v5c0 1.105-.895 2-2 2H5c-1.105 0-2-.895-2-2V5c0-1.105.895-2 2-2h5v2H5v14h14v-5h2z"></path> <path d="M21 7h-4V3h-2v4h-4v2h4v4h2V9h4"></path> </g> </g>
            </svg>สร้างเอกสาร</a>
        </div>
      </li>
      <!-- <li>
        <div>
        <a href="#" class="flex block px-2 py-2 font-bold hover:bg-gray-300 dark:hover:bg-gray-600 dark:hover:text-white">
        <svg class="h-5 w-4 mr-4" viewBox="0 0 24 24" xmlns="http://www.w3.org/2000/svg" id="create-note-alt"  fill="#000000"><g id="SVGRepo_bgCarrier" stroke-width="0"></g><g id="SVGRepo_tracerCarrier" stroke-linecap="round" stroke-linejoin="round"></g><g id="SVGRepo_iconCarrier"><path d="M20,22H4a2,2,0,0,1-2-2V4A2,2,0,0,1,4,2h8a1,1,0,0,1,0,2H4V20H20V12a1,1,0,0,1,2,0v8A2,2,0,0,1,20,22Z" style="fill:#231f20"></path><path d="M22,1.94a1,1,0,0,0-.87-.9,9.45,9.45,0,0,0-2.83.17,1,1,0,0,0-.76.72l-.18.72-.83-.33a1,1,0,0,0-1,.13,4.87,4.87,0,0,0-.7.67C13,5.15,13,8.36,13,9.58l-1.72,1.71a1,1,0,0,0,0,1.42,1,1,0,0,0,1.42,0L14.42,11h.36a7.2,7.2,0,0,0,5.41-2.12h0C22.37,6.39,22,2.12,22,1.94Z"></path></g>
        </svg>ลงนามเอกสาร</a>
        
    </div>
    </li> -->
      <!-- <li>
        <a href="#" class="block px-4 py-2 hover:bg-gray-100 dark:hover:bg-gray-600 dark:hover:text-white">Earnings</a>
      </li>
      <li>
        <a href="#" class="block px-4 py-2 hover:bg-gray-100 dark:hover:bg-gray-600 dark:hover:text-white">Sign out</a>
      </li> -->
    </ul>
</div>
</div>

             <!-- <button type="button" class="flex items-center w-full p-2 text-base text-gray-900 transition duration-75 rounded-lg group bg-lime-500 hover:bg-lime-600 dark:text-white dark:hover:bg-gray-700" aria-controls="dropdown-example" data-collapse-toggle="dropdown-example">
                  <svg class="flex-shrink-0 w-5 h-5 text-gray-500 transition duration-75 group-hover:text-gray-900 dark:text-gray-400 dark:group-hover:text-white" aria-hidden="true" xmlns="http://www.w3.org/2000/svg" fill="currentColor" viewBox="0 0 18 21">
                     <path d="M15 12a1 1 0 0 0 .962-.726l2-7A1 1 0 0 0 17 3H3.77L3.175.745A1 1 0 0 0 2.208 0H1a1 1 0 0 0 0 2h.438l.6 2.255v.019l2 7 .746 2.986A3 3 0 1 0 9 17a2.966 2.966 0 0 0-.184-1h2.368c-.118.32-.18.659-.184 1a3 3 0 1 0 3-3H6.78l-.5-2H15Z"/>
                  </svg>
                  <span class="flex-1 ms-3 text-left rtl:text-right whitespace-nowrap">จัดการเอกสาร</span>
                  <svg class="w-3 h-3" aria-hidden="true" xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 10 6">
                     <path stroke="currentColor" stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="m1 1 4 4 4-4"/>
                  </svg>
            </button>
            <ul id="dropdown-example" class="hidden py-2 space-y-2">
                  <li>
                     <a href="#" class="flex items-center w-full p-2 text-gray-900 transition duration-75 rounded-lg pl-11 group hover:bg-gray-100 dark:text-white dark:hover:bg-gray-700">AAA</a>
                  </li>
                  <li>
                     <a href="#" class="flex items-center w-full p-2 text-gray-900 transition duration-75 rounded-lg pl-11 group hover:bg-gray-100 dark:text-white dark:hover:bg-gray-700">BBB</a>
                  </li>
                  <li>
                     <a href="#" class="flex items-center w-full p-2 text-gray-900 transition duration-75 rounded-lg pl-11 group hover:bg-gray-100 dark:text-white dark:hover:bg-gray-700">CCC</a>
                  </li>
            </ul>
          -->

            <!-- กล่องเสิช -->
            <!-- <div class="px-6 pt-4">
                <div class="relative">
                    <div class="absolute inset-y-0 left-0 flex items-center pl-2 pointer-events-none">
                        <svg class="w-4 h-4 text-gray-500 stroke-current" fill="none" viewBox="0 0 24 24">
                            <path stroke="currentColor" stroke-linecap="round" stroke-linejoin="round" stroke-width="1.5"
                                d="M19.25 19.25L15.5 15.5M4.75 11C4.75 7.54822 7.54822 4.75 11 4.75C14.4518 4.75 17.25 7.54822 17.25 11C17.25 14.4518 14.4518 17.25 11 17.25C7.54822 17.25 4.75 14.4518 4.75 11Z">
                            </path>
                        </svg>
                    </div>
                    <input type="text"
                        class="w-full rounded pl-8 pr-4 py-2.5 text-xs font-light bg-gray-800 text-gray-400 placeholder-gray-500 focus:outline-none focus:ring-1 focus:ring-gray-500 focus:bg-gray-800"
                        placeholder="search" />
                </div>
            </div> -->
            <div class="px-6 pt-4">
                
            </div>
            <div class="px-6 pt-4">
                <ul class="flex flex-col space-y-2">
                   
                    <li class="">
                        <div class="relative flex justify-between text-white hover:text-white focus-within:text-white ">
                            <div class="flex items-center w-full">
                                <div class="absolute inset-y-0 left-0 flex items-center pl-2 pointer-events-none">
                            <svg class="w-5 h-5 stroke-current" fill="none" viewBox="0 0 24 24">
                                <path stroke="currentColor" stroke-linecap="round" stroke-linejoin="round"
                                    stroke-width="1.5"
                                    d="M4.75 6.75C4.75 5.64543 5.64543 4.75 6.75 4.75H17.25C18.3546 4.75 19.25 5.64543 19.25 6.75V17.25C19.25 18.3546 18.3546 19.25 17.25 19.25H6.75C5.64543 19.25 4.75 18.3546 4.75 17.25V6.75Z">
                                </path>
                                <path stroke="currentColor" stroke-linecap="round" stroke-linejoin="round"
                                    stroke-width="1.5" d="M9.75 8.75V19"></path>
                                <path stroke="currentColor" stroke-linecap="round" stroke-linejoin="round"
                                    stroke-width="1.5" d="M5 8.25H19"></path>
                            </svg>
                        </div>
                                <a href="#" data-tooltip-target="testtool2" data-tooltip-trigger="click"
                                    class="inline-block w-full py-2 pl-8 pr-4 text-xs rounded hover:bg-neutral-900 focus:outline-none focus:ring-1 focus:ring-gray-500 focus:bg-gray-800 ">ทะเบียนเอกสารทั้งหมด</a>
                            </div>

                            <!-- กดทิปส์ -->
          <div id="testtool2" role="tooltip" class="absolute z-10 invisible inline-block px-3 py-2 text-sm font-medium text-white bg-gray-900 rounded-lg shadow-sm opacity-0 tooltip dark:bg-gray-700">
             งาน Release หน้าจ้า
                <div class="tooltip-arrow" data-popper-arrow></div>
                </div>
                            
                            <!-- ลูกศร Drop -->
                            <!-- <button class="absolute right-0 flex items-center p-1" tabindex="-1">
                                <svg class="w-5 h-5 stroke-current" fill="none" viewBox="0 0 24 24">
                                    <path stroke="currentColor" stroke-linecap="round" stroke-linejoin="round"
                                        stroke-width="1.5" d="M15.25 10.75L12 14.25L8.75 10.75"></path>
                                </svg>
                            </button> -->
                        </div>
                        <br>
                        <hr class="border-slate-400" />
                        <a class="px-1 font-bold text-xs text-neutral-400">ทั่วไป</a>
                        <!-- List ใน Drop down -->
                        <!-- <div class="pt-2 pl-4">
                            <ul class="flex flex-col pl-2 text-white border-l border-slate-300">
                                <li>
                                    <a href="#"
                                        class="inline-block w-full px-4 py-2 text-xs rounded hover:bg-neutral-900 hover:text-white focus:outline-none focus:ring-1 focus:ring-gray-500 focus:text-white">Courses</a>
                                </li>
                                <li>
                                    <a href="#"
                                        class="inline-block w-full px-4 py-2 text-xs rounded hover:bg-neutral-900 hover:text-white focus:outline-none focus:ring-1 focus:ring-gray-500 focus:text-white">Categories</a>
                                </li>
                                <li>
                                    <a href="#"
                                        class="inline-block w-full px-4 py-2 text-xs rounded hover:bg-neutral-900 hover:text-white focus:outline-none focus:ring-1 focus:ring-gray-500 focus:text-white">Instructors</a>
                                </li>
                                <li>
                                    <a href="#"
                                        class="inline-block w-full px-4 py-2 text-xs rounded hover:bg-neutral-900 hover:text-white focus:outline-none focus:ring-1 focus:ring-gray-500 focus:text-white">Video
                                        Library</a>
                                </li>
                            </ul>
                        </div> -->
                    </li>
                    <li class="relative text-white hover:text-white focus-within:text-white">
                        <div class="absolute inset-y-0 left-0 flex items-center pl-2 pointer-events-none">
                                    <svg class="w-5 h-5 stroke-current" fill="none" viewBox="0 0 24 24">
                                        <path stroke="currentColor" stroke-linecap="round" stroke-linejoin="round"
                                            stroke-width="1.5"
                                            d="M7.75 19.25H16.25C17.3546 19.25 18.25 18.3546 18.25 17.25V9L14 4.75H7.75C6.64543 4.75 5.75 5.64543 5.75 6.75V17.25C5.75 18.3546 6.64543 19.25 7.75 19.25Z">
                                        </path>
                                        <path stroke="currentColor" stroke-linecap="round" stroke-linejoin="round"
                                            stroke-width="1.5" d="M18 9.25H13.75V5"></path>
                                        <path stroke="currentColor" stroke-linecap="round" stroke-linejoin="round"
                                            stroke-width="1.5" d="M9.75 15.25H14.25"></path>
                                        <path stroke="currentColor" stroke-linecap="round" stroke-linejoin="round"
                                            stroke-width="1.5" d="M9.75 12.25H14.25"></path>
                                    </svg>
                                </div>
                        
                        <a href="/list" 
                            class="inline-block w-full py-2 pl-8 pr-4 text-xs rounded hover:bg-neutral-900 focus:outline-none focus:ring-1 focus:ring-gray-500 focus:bg-gray-800">เอกสารทั้งหมด</a>
                    
                        </li>
                        
                    <li class="relative text-white hover:text-white focus-within:text-white">
                        <div class="absolute inset-y-0 left-0 flex items-center pl-2 pointer-events-none">
                            <svg class="w-5 h-5 stroke-current" fill="none" viewBox="0 0 24 24">
                                <path d="M12 4.75L19.25 9L12 13.25L4.75 9L12 4.75Z" stroke="currentColor" stroke-width="1.5"
                                    stroke-linecap="round" stroke-linejoin="round"></path>
                                <path d="M9.25 12L4.75 15L12 19.25L19.25 15L14.6722 12" stroke="currentColor"
                                    stroke-width="1.5" stroke-linecap="round" stroke-linejoin="round"></path>
                            </svg>
                        </div>
                        <a href="#" data-tooltip-target="testtool3" data-tooltip-trigger="click"
                            class="inline-block w-full py-2 pl-8 pr-4 text-xs rounded hover:bg-neutral-900 focus:outline-none focus:ring-1 focus:ring-gray-500 focus:bg-gray-800">เอกสารภายใน</a>
                    </li>

                     <div id="testtool3" role="tooltip" class="absolute z-10 invisible inline-block px-3 py-2 text-sm font-medium text-white bg-gray-900 rounded-lg shadow-sm opacity-0 tooltip dark:bg-gray-700">
                         งาน Release หน้าจ้า
                        <div class="tooltip-arrow" data-popper-arrow></div></div>


                    <li class="relative text-white hover:text-white focus-within:text-white">
                        <div class="absolute inset-y-0 left-0 flex items-center pl-2 pointer-events-none">
                            <svg class="w-5 h-5 stroke-current" fill="none" viewBox="0 0 24 24">
                                <circle cx="15" cy="9" r="1" fill="currentColor"></circle>
                                <path stroke="currentColor" stroke-linecap="round" stroke-linejoin="round"
                                    stroke-width="1.5"
                                    d="M12 4.75H19.25V12L12.5535 18.6708C11.7544 19.4668 10.4556 19.445 9.68369 18.6226L5.28993 13.941C4.54041 13.1424 4.57265 11.8895 5.36226 11.1305L12 4.75Z">
                                </path>
                            </svg>
                        </div>
                        <a href="#"
                            class="inline-block w-full py-2 pl-8 pr-4 text-xs rounded hover:bg-neutral-900 focus:outline-none focus:ring-1 focus:ring-gray-500 focus:bg-gray-800"> เอกสารภายนอก </a>
                    </li>
                    <br>
                    <hr class="border-slate-400" />
                    <a class="px-1    font-bold text-xs text-neutral-400">ตามการดำเนินงาน</a>
                    <li class="relative text-white hover:text-white focus-within:text-white">
                        <div class="absolute inset-y-0 left-0 flex items-center pl-2 pointer-events-none">
                            <svg class="w-5 h-4" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg"><g id="SVGRepo_bgCarrier" stroke-width="0"></g><g id="SVGRepo_tracerCarrier" stroke-linecap="round" stroke-linejoin="round"></g><g id="SVGRepo_iconCarrier"> <path d="M8.5 21H4C4 17.134 7.13401 14 11 14C11.1681 14 11.3348 14.0059 11.5 14.0176M15 7C15 9.20914 13.2091 11 11 11C8.79086 11 7 9.20914 7 7C7 4.79086 8.79086 3 11 3C13.2091 3 15 4.79086 15 7ZM12.5898 21L14.6148 20.595C14.7914 20.5597 14.8797 20.542 14.962 20.5097C15.0351 20.4811 15.1045 20.4439 15.1689 20.399C15.2414 20.3484 15.3051 20.2848 15.4324 20.1574L19.5898 16C20.1421 15.4477 20.1421 14.5523 19.5898 14C19.0376 13.4477 18.1421 13.4477 17.5898 14L13.4324 18.1574C13.3051 18.2848 13.2414 18.3484 13.1908 18.421C13.1459 18.4853 13.1088 18.5548 13.0801 18.6279C13.0478 18.7102 13.0302 18.7985 12.9948 18.975L12.5898 21Z" stroke="#ffffff" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"></path> </g></svg>
                        </div>
                        <a href="#"
                            class="inline-block w-full py-2 pl-8 pr-4 text-xs rounded hover:bg-neutral-900 focus:outline-none focus:ring-1 focus:ring-gray-500 focus:bg-gray-800">เอกสารรอลงนาม</a>
                    </li>
                    <li class="relative text-white hover:text-white focus-within:text-white">
                        <div class="absolute inset-y-0 left-0 flex items-center pl-2 pointer-events-none">
                            <svg class="w-5 h-4" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg"><g id="SVGRepo_bgCarrier" stroke-width="0"></g><g id="SVGRepo_tracerCarrier" stroke-linecap="round" stroke-linejoin="round"></g><g id="SVGRepo_iconCarrier"> <path d="M15 6H9M20 21H19M19 21H5M19 21C19 18.4898 17.7877 16.1341 15.7451 14.675L12 12M5 21H4M5 21C5 18.4898 6.21228 16.1341 8.25493 14.675L12 12M20 3H19M19 3H5M19 3C19 5.51022 17.7877 7.86592 15.7451 9.32495L12 12M5 3H4M5 3C5 5.51022 6.21228 7.86592 8.25493 9.32495L12 12" stroke="#ffffff" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"></path> </g></svg>
                            
                        </div>
                        <a href="#"
                            class="inline-block w-full py-2 pl-8 pr-4 text-xs rounded hover:bg-neutral-900 focus:outline-none focus:ring-1 focus:ring-gray-500 focus:bg-gray-800">เอกสารรอปฎิบัติ</a>
                    </li>
                </ul>
            </div>

            <div class="px-6 pt-8">
                <hr class="border-slate-400" />
            </div>
            <a class="px-6 pt-4 mt-4   font-bold text-xs text-neutral-400">หมวดหมู่ของผู้ใช้</a>
            
            <div class="px-6 pt-4 pb-8">
               
                <button data-tooltip-target="testtool" data-tooltip-trigger="click" type="button" class="text-white bg-green-700 hover:bg-green-900 focus:ring-4 focus:outline-none focus:ring-blue-300 font-medium rounded-lg text-sm px-5 py-1 text-center inline-flex items-center dark:bg-blue-600 dark:hover:bg-blue-700 dark:focus:ring-blue-800">
                    <svg class="-translate-x-3 rtl:rotate-180 w-7 h-7 ms-2" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg"><g id="SVGRepo_bgCarrier" stroke-width="0"></g><g id="SVGRepo_tracerCarrier" stroke-linecap="round" stroke-linejoin="round"></g><g id="SVGRepo_iconCarrier"> <rect  fill="white"></rect> <path d="M12 6V18" stroke="#ffffff" stroke-linecap="round" stroke-linejoin="round"></path> <path d="M6 12H18" stroke="#ffffff" stroke-linecap="round" stroke-linejoin="round"></path> </g></svg>เพิ่มหมวดหมู่
                
                </button>
                <!-- กดทิปส์ -->
                <div id="testtool" role="tooltip" class="absolute z-10 invisible inline-block px-3 py-2 text-sm font-medium text-white bg-gray-900 rounded-lg shadow-sm opacity-0 tooltip dark:bg-gray-700">
             งาน Release หน้าจ้า
                <div class="tooltip-arrow" data-popper-arrow></div>
                </div>

                <ul>
                    <li class="relative text-white hover:text-white focus-within:text-white">
                        <div class="absolute inset-y-0 left-0 flex items-center pl-2 pointer-events-none">
                            <svg class="w-5 h-5 stroke-current" fill="none" viewBox="0 0 24 24">
                                <path stroke="currentColor" stroke-linecap="round" stroke-linejoin="round"
                                    stroke-width="1.5"
                                    d="M4.75 5.75C4.75 5.19772 5.19772 4.75 5.75 4.75H9.25C9.80228 4.75 10.25 5.19772 10.25 5.75V9.25C10.25 9.80228 9.80228 10.25 9.25 10.25H5.75C5.19772 10.25 4.75 9.80228 4.75 9.25V5.75Z">
                                </path>
                                <path stroke="currentColor" stroke-linecap="round" stroke-linejoin="round"
                                    stroke-width="1.5"
                                    d="M4.75 14.75C4.75 14.1977 5.19772 13.75 5.75 13.75H9.25C9.80228 13.75 10.25 14.1977 10.25 14.75V18.25C10.25 18.8023 9.80228 19.25 9.25 19.25H5.75C5.19772 19.25 4.75 18.8023 4.75 18.25V14.75Z">
                                </path>
                                <path stroke="currentColor" stroke-linecap="round" stroke-linejoin="round"
                                    stroke-width="1.5"
                                    d="M13.75 5.75C13.75 5.19772 14.1977 4.75 14.75 4.75H18.25C18.8023 4.75 19.25 5.19772 19.25 5.75V9.25C19.25 9.80228 18.8023 10.25 18.25 10.25H14.75C14.1977 10.25 13.75 9.80228 13.75 9.25V5.75Z">
                                </path>
                                <path stroke="currentColor" stroke-linecap="round" stroke-linejoin="round"
                                    stroke-width="1.5"
                                    d="M13.75 14.75C13.75 14.1977 14.1977 13.75 14.75 13.75H18.25C18.8023 13.75 19.25 14.1977 19.25 14.75V18.25C19.25 18.8023 18.8023 19.25 18.25 19.25H14.75C14.1977 19.25 13.75 18.8023 13.75 18.25V14.75Z">
                                </path>
                            </svg>
                        </div>
                        <a href="#"
                            class="inline-block w-full py-2 pl-8 pr-4 text-xs rounded hover:bg-neutral-900 focus:outline-none focus:ring-1 focus:ring-gray-500 focus:bg-gray-800">พัสดุและคลัง</a>
                    </li>
                    <li class="relative text-white hover:text-white focus-within:text-white">
                        <div class="absolute inset-y-0 left-0 flex items-center pl-2 pointer-events-none">
                            <svg class="w-5 h-5 stroke-current" fill="none" viewBox="0 0 24 24">
                                <path stroke="currentColor" stroke-linecap="round" stroke-linejoin="round"
                                    stroke-width="1.5"
                                    d="M13.1191 5.61336C13.0508 5.11856 12.6279 4.75 12.1285 4.75H11.8715C11.3721 4.75 10.9492 5.11856 10.8809 5.61336L10.7938 6.24511C10.7382 6.64815 10.4403 6.96897 10.0622 7.11922C10.006 7.14156 9.95021 7.16484 9.89497 7.18905C9.52217 7.3524 9.08438 7.3384 8.75876 7.09419L8.45119 6.86351C8.05307 6.56492 7.49597 6.60451 7.14408 6.9564L6.95641 
                                    7.14408C6.60452 7.49597 6.56492 8.05306 6.86351 8.45118L7.09419 8.75876C7.33841 9.08437 7.3524 9.52216 7.18905 9.89497C7.16484 9.95021 7.14156 10.006 7.11922 10.0622C6.96897 10.4403 6.64815 10.7382 6.24511 10.7938L5.61336 10.8809C5.11856 10.9492 4.75 11.372 4.75 11.8715V12.1285C4.75 12.6279 5.11856 13.0508 5.61336 13.1191L6.24511 13.2062C6.64815 
                                    13.2618 6.96897 13.5597 7.11922 13.9378C7.14156 13.994 7.16484 14.0498 7.18905 14.105C7.3524 14.4778 7.3384 14.9156 7.09419 15.2412L6.86351 15.5488C6.56492 15.9469 6.60451 16.504 6.9564 16.8559L7.14408 17.0436C7.49597 17.3955 8.05306 17.4351 8.45118 17.1365L8.75876 16.9058C9.08437 16.6616 9.52216 16.6476 9.89496 16.811C9.95021 16.8352 10.006 
                                    16.8584 10.0622 16.8808C10.4403 17.031 10.7382 17.3519 10.7938 17.7549L10.8809 18.3866C10.9492 18.8814 11.3721 19.25 11.8715 19.25H12.1285C12.6279 19.25 13.0508 18.8814 13.1191 18.3866L13.2062 17.7549C13.2618 17.3519 13.5597 17.031 13.9378 16.8808C13.994 
                                    16.8584 14.0498 16.8352 14.105 16.8109C14.4778 16.6476 14.9156 16.6616 15.2412 16.9058L15.5488 17.1365C15.9469 17.4351 16.504 17.3955 16.8559 17.0436L17.0436 16.8559C17.3955 16.504 17.4351 15.9469 17.1365 15.5488L16.9058 15.2412C16.6616 14.9156 16.6476 14.4778 16.811 14.105C16.8352 14.0498 16.8584 13.994 16.8808 13.9378C17.031 
                                    13.5597 17.3519 13.2618 17.7549 13.2062L18.3866 13.1191C18.8814 13.0508 19.25 12.6279 19.25 12.1285V11.8715C19.25 11.3721 18.8814 10.9492 18.3866 10.8809L17.7549 10.7938C17.3519 10.7382 17.031 10.4403 16.8808 10.0622C16.8584 10.006 16.8352 9.95021 16.8109 9.89496C16.6476 9.52216 16.6616 9.08437 16.9058 
                                    8.75875L17.1365 8.4512C17.4351 8.05308 17.3955 7.49599 17.0436 7.1441L16.8559 6.95642C16.504 6.60453 15.9469 6.56494 15.5488 6.86353L15.2412 7.09419C14.9156 7.33841 14.4778 7.3524 14.105 7.18905C14.0498 7.16484 13.994 7.14156 13.9378 7.11922C13.5597 6.96897 13.2618 6.64815 13.2062 6.24511L13.1191 5.61336Z">
                                </path>
                                <path stroke="currentColor" stroke-linecap="round" stroke-linejoin="round"
                                    stroke-width="1.5"
                                    d="M13.25 12C13.25 12.6904 12.6904 13.25 12 13.25C11.3096 13.25 10.75 12.6904 10.75 12C10.75 11.3096 11.3096 10.75 12 10.75C12.6904 10.75 13.25 11.3096 13.25 12Z">
                                </path>
                            </svg>
                        </div>
                        <a href="#"
                            class="inline-block w-full py-2 pl-8 pr-4 text-xs rounded hover:bg-neutral-900 focus:outline-none focus:ring-1 focus:ring-gray-500 focus:bg-gray-800">ตั้งค่า</a>
                    </li>

                   
                    
                </ul>
            </div>
           
        </div>
    </div>
</template>
 
<style>

</style>

