<script setup>
import { ref, onMounted, onUnmounted, onBeforeMount } from "vue";
import { useRoute, useRouter } from "vue-router";
import Navbar from "./Navbar.vue";
let route = useRoute();

// let mainURL = "http://localhost:5002";
// let mainURL = "http://cp23kw2.sit.kmutt.ac.th:10003";
let mainURL = "http://capstone23.sit.kmutt.ac.th/kw2";

</script>

<template>
    <Navbar />
    <section class="bg-white dark:bg-gray-900">
  <div class="py-8 px-4 mx-auto max-w-screen-xl lg:py-16 lg:px-6 ">
    <div class="max-w-screen-sm mb-8 lg:mb-16 ml-0 border-b pb-4">
        <h2 class="mb-4 text-4xl tracking-tight font-extrabold text-gray-900 dark:text-white">ระบบงานสารบรรณอิเล็กทรอนิกส์</h2>
        <p class="font-light text-gray-500 lg:mb-16 sm:text-xl dark:text-gray-400">E-Sarabun </p>
    </div>

      <div class="grid gap-8 mb-6 lg:mb-16 md:grid-cols-2">

        <RouterLink to="/kw2/document">
          <div class="cursor-pointer items-center hover:bg-gray-100 bg-gray-50 rounded-lg shadow sm:flex dark:bg-gray-800 dark:border-gray-700">
              <a href="#">
                <!-- สำหรับวางไอคอน -->
              </a>
              <div class="p-5">
                  <h3 class="text-xl font-bold tracking-tight text-gray-900 dark:text-white">
                    <!-- <RouterLink to="/kw2/document">E-Document</RouterLink> -->
                      <a href="#">E-Document</a>
                  </h3>
                  <span class="text-gray-500 dark:text-gray-400">ระบบงานจัดเก็บเอกสารอิเล็กทรอนิกส์</span>
                  <p class="mt-3 mb-4 font-light text-gray-500 dark:text-gray-400">ส่วนที่ใช้สำหรับจัดเก็บเอกสาร และส่งเอกสารขององค์กร</p>
                 
              </div>
          </div> 
        </RouterLink>

        <RouterLink to="/kw2/approval">
          <div class="cursor-pointer items-center hover:bg-gray-100 bg-gray-50 rounded-lg shadow sm:flex dark:bg-gray-800 dark:border-gray-700">
              <a href="#">
                  <!-- สำหรับวางไอคอน -->
              </a>
              <div class="p-5">
                  <h3 class="text-xl font-bold tracking-tight text-gray-900 dark:text-white">
                      <a href="#">E-Approval</a>
                  </h3>
                  <span class="text-gray-500 dark:text-gray-400">ระบบยืนยันเอกสารและคำร้อง</span>
                  <p class="mt-3 mb-4 font-light text-gray-500 dark:text-gray-400">ส่วนที่ใช้สำหรับการยื่นเอกสารเพื่อขอพิจารณาการยืนยันโครงการ</p>
                  
              </div>
          </div> 
        </RouterLink>

        <RouterLink to="/kw2/guide">
          <div class="cursor-pointer items-center hover:bg-gray-100 bg-gray-50 rounded-lg shadow sm:flex dark:bg-gray-800 dark:border-gray-700">
              <a href="#">
                  <!-- สำหรับวางไอคอน -->
              </a>
              <div class="p-5">
                  <h3 class="text-xl font-bold tracking-tight text-gray-900 dark:text-white">
                      <a href="#">คู่มือการใช้งาน</a>
                  </h3>
                  <span class="text-gray-500 dark:text-gray-400">คู่มือประกอบการใช้งาน</span>
                  <p class="mt-3 mb-4 font-light text-gray-500 dark:text-gray-400">ส่วนที่ใช้สำหรับดูวิธีใช้งานระบบ E-Document และ ระบบ E-Approval</p>
              </div>
          </div> 
        </RouterLink>
        
      </div>  
  </div>
</section>


        









    
</template>
 
<style></style>