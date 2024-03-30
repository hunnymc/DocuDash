<script setup>
import Navbar from "./Navbar.vue";
import Cookies from "js-cookie";
import {onBeforeMount, ref} from "vue";

let userRoleNumber = ref(0);
let approvepath = ref("");

function checkUserRole (role) {
  if (role === "USER") {
    userRoleNumber.value = 1;
  } else if (role === "MANAGER") {
    userRoleNumber.value = 2;
  } else if (role === "ADMIN") {
    userRoleNumber.value = 3;
  }
}

function changeApprovePage() {
  if (userRoleNumber.value === 3) {
    approvepath.value = "/kw2/approval/admin/dashboard";
  } else if (userRoleNumber.value === 2) {
    approvepath.value = "/kw2/approval/list/manager-accept";
  } else if (userRoleNumber.value === 1) {
    approvepath.value = "/kw2/approval/list";
  }
}

onBeforeMount(() => {
  checkUserRole(Cookies.get('role'));
  changeApprovePage();
});

</script>

<template>

    <Navbar />

    <section class="bg-white dark:bg-gray-900 h-screen w-screen bg-cover bg-image ">
  <div class="py-8 px-4 mx-auto max-w-screen-xl lg:py-16 lg:px-6 ">
    <div class="max-w-screen-sm mb-8 lg:mb-16 ml-0 border-b pb-4">
        <h2 class="mb-4 text-4xl tracking-tight font-extrabold text-gray-100 dark:text-white drop-shadow-xl">ระบบงานสารบรรณอิเล็กทรอนิกส์</h2>
        <p class="font-light text-gray-100 lg:mb-16 sm:text-xl drop-shadow-md">E-Sarabun </p>
    </div>

      <div class="grid gap-8 mb-6 lg:mb-16 md:grid-cols-2">

        <RouterLink to="/kw2/document">
          <div class="drop-shadow-md cursor-pointer items-center hover:bg-gray-100 bg-gray-50 rounded-lg shadow sm:flex dark:bg-gray-800 dark:border-gray-700">
              <a href="#">
                <svg class="pl-2 h-24" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg"><g id="SVGRepo_bgCarrier" stroke-width="0"></g><g id="SVGRepo_tracerCarrier" stroke-linecap="round" stroke-linejoin="round"></g><g id="SVGRepo_iconCarrier"> <path d="M15.7997 2.21048C15.3897 1.80048 14.6797 2.08048 14.6797 2.65048V6.14048C14.6797 7.60048 15.9197 8.81048 17.4297 8.81048C18.3797 8.82048 19.6997 8.82048 20.8297 8.82048C21.3997 8.82048 21.6997 8.15048 21.2997 7.75048C19.8597 6.30048 17.2797 3.69048 15.7997 2.21048Z" fill="#538bd0"></path> <path d="M20.5 10.19H17.61C15.24 10.19 13.31 8.26 13.31 5.89V3C13.31 2.45 12.86 2 12.31 2H8.07C4.99 2 2.5 4 2.5 7.57V16.43C2.5 20 4.99 22 8.07 22H15.93C19.01 22 21.5 20 21.5 16.43V11.19C21.5 10.64 21.05 10.19 20.5 10.19ZM11.5 17.75H7.5C7.09 17.75 6.75 17.41 6.75 17C6.75 16.59 7.09 16.25 7.5 16.25H11.5C11.91 16.25 12.25 16.59 12.25 17C12.25 17.41 11.91 17.75 11.5 17.75ZM13.5 13.75H7.5C7.09 13.75 6.75 13.41 6.75 13C6.75 12.59 7.09 12.25 7.5 12.25H13.5C13.91 12.25 14.25 12.59 14.25 13C14.25 13.41 13.91 13.75 13.5 13.75Z" fill="#538bd0"></path> </g></svg>
              </a>
              <div class="p-5">
                  <h3 class="text-xl font-bold tracking-tight text-gray-900 dark:text-white">
                      <a href="#">E-Document</a>
                  </h3>
                  <span class="text-gray-500 dark:text-gray-400">ระบบงานจัดเก็บเอกสารอิเล็กทรอนิกส์</span>
                  <p class="mt-3 mb-4 font-light text-gray-500 dark:text-gray-400">ส่วนที่ใช้สำหรับจัดเก็บเอกสาร และส่งเอกสารขององค์กร</p>
              </div>
          </div> 
        </RouterLink>

        <RouterLink :to=approvepath>
          <div class="drop-shadow-md cursor-pointer items-center hover:bg-gray-100 bg-gray-50 rounded-lg shadow sm:flex dark:bg-gray-800 dark:border-gray-700">

                <svg class="pl-2 h-24" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg"><g id="SVGRepo_bgCarrier" stroke-width="0"></g><g id="SVGRepo_tracerCarrier" stroke-linecap="round" stroke-linejoin="round"></g><g id="SVGRepo_iconCarrier"> <path d="M20.5 10.19H17.61C15.24 10.19 13.31 8.26 13.31 5.89V3C13.31 2.45 12.86 2 12.31 2H8.07C4.99 2 2.5 4 2.5 7.57V16.43C2.5 20 4.99 22 8.07 22H15.93C19.01 22 21.5 20 21.5 16.43V11.19C21.5 10.64 21.05 10.19 20.5 10.19ZM13.75 14.94C13.75 14.99 13.75 15.05 13.75 15.1C13.73 15.24 13.67 15.37 13.57 15.49C13.56 15.5 13.54 15.52 13.53 15.53L11.53 17.53C11.38 17.68 11.19 17.75 11 17.75C10.81 17.75 10.62 17.68 10.47 17.53C10.18 17.24 10.18 16.76 10.47 16.47L11.19 15.75H7C6.59 15.75 6.25 15.41 6.25 15C6.25 14.59 6.59 14.25 7 14.25H11.19L10.47 13.53C10.32 13.38 10.25 13.19 10.25 13C10.25 12.81 10.32 12.62 10.47 12.47C10.76 12.18 11.24 12.18 11.53 12.47L13.53 14.47C13.66 14.61 13.73 14.77 13.75 14.94Z" fill="#538bd0"></path> <path d="M17.4297 8.81048C18.3797 8.82048 19.6997 8.82048 20.8297 8.82048C21.3997 8.82048 21.6997 8.15048 21.2997 7.75048C19.8597 6.30048 17.2797 3.69048 15.7997 2.21048C15.3897 1.80048 14.6797 2.08048 14.6797 2.65048V6.14048C14.6797 7.60048 15.9197 8.81048 17.4297 8.81048Z" fill="#538bd0"></path> </g></svg>

              <div class="p-5">
                  <h3 class="text-xl font-bold tracking-tight text-gray-900 dark:text-white">
                      E-Approval
                  </h3>
                  <span class="text-gray-500 dark:text-gray-400">ระบบยืนยันเอกสารและคำร้อง</span>
                  <p class="mt-3 mb-4 font-light text-gray-500 dark:text-gray-400">ส่วนที่ใช้สำหรับการยื่นเอกสารเพื่อขอพิจารณาการยืนยันโครงการ</p>
                  
              </div>
          </div> 
        </RouterLink>

        <RouterLink to="/kw2/guide">
          <div class="drop-shadow-md cursor-pointer items-center hover:bg-gray-100 bg-gray-50 rounded-lg shadow sm:flex dark:bg-gray-800 dark:border-gray-700">
              <a href="#">
                <svg class="pl-2 h-24" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg"><g id="SVGRepo_bgCarrier" stroke-width="0"></g><g id="SVGRepo_tracerCarrier" stroke-linecap="round" stroke-linejoin="round"></g><g id="SVGRepo_iconCarrier"> <path d="M13 3L13.7071 2.29289C13.5196 2.10536 13.2652 2 13 2V3ZM14 22C14.5523 22 15 21.5523 15 21C15 20.4477 14.5523 20 14 20V22ZM19 9H20C20 8.73478 19.8946 8.48043 19.7071 8.29289L19 9ZM18 10C18 10.5523 18.4477 11 19 11C19.5523 11 20 10.5523 20 10H18ZM5.21799 19.908L4.32698 20.362H4.32698L5.21799 19.908ZM6.09202 20.782L6.54601 19.891L6.54601 19.891L6.09202 20.782ZM6.09202 3.21799L5.63803 2.32698L5.63803 2.32698L6.09202 3.21799ZM5.21799 4.09202L4.32698 3.63803L4.32698 3.63803L5.21799 4.09202ZM13.109 8.45399L14 8V8L13.109 8.45399ZM13.546 8.89101L14 8L13.546 8.89101ZM9 16C8.44772 16 8 16.4477 8 17C8 17.5523 8.44772 18 9 18V16ZM12 18C12.5523 18 13 17.5523 13 17C13 16.4477 12.5523 16 12 16V18ZM9 12C8.44772 12 8 12.4477 8 13C8 13.5523 8.44772 14 9 14V12ZM13 14C13.5523 14 14 13.5523 14 13C14 12.4477 13.5523 12 13 12V14ZM9 8C8.44772 8 8 8.44772 8 9C8 9.55228 8.44772 10 9 10V8ZM10 10C10.5523 10 11 9.55228 11 9C11 8.44772 10.5523 8 10 8V10ZM17.2299 17.7929C16.8394 18.1834 16.8394 18.8166 17.2299 19.2071C17.6204 19.5976 18.2536 19.5976 18.6441 19.2071L17.2299 17.7929ZM15.0316 15.2507C14.8939 15.7856 15.2159 16.3308 15.7507 16.4684C16.2856 16.6061 16.8308 16.2841 16.9684 15.7493L15.0316 15.2507ZM17.9375 20C17.3852 20 16.9375 20.4477 16.9375 21C16.9375 21.5523 17.3852 22 17.9375 22V20ZM17.9475 22C18.4998 22 18.9475 21.5523 18.9475 21C18.9475 20.4477 18.4998 20 17.9475 20V22ZM13 2H8.2V4H13V2ZM4 6.2V17.8H6V6.2H4ZM8.2 22H14V20H8.2V22ZM19.7071 8.29289L13.7071 2.29289L12.2929 3.70711L18.2929 9.70711L19.7071 8.29289ZM20 10V9H18V10H20ZM4 17.8C4 18.3436 3.99922 18.8114 4.03057 19.195C4.06287 19.5904 4.13419 19.9836 4.32698 20.362L6.10899 19.454C6.0838 19.4045 6.04612 19.3038 6.02393 19.0322C6.00078 18.7488 6 18.3766 6 17.8H4ZM8.2 20C7.62345 20 7.25117 19.9992 6.96784 19.9761C6.69617 19.9539 6.59545 19.9162 6.54601 19.891L5.63803 21.673C6.01641 21.8658 6.40963 21.9371 6.80497 21.9694C7.18864 22.0008 7.65645 22 8.2 22V20ZM4.32698 20.362C4.6146 20.9265 5.07354 21.3854 5.63803 21.673L6.54601 19.891C6.35785 19.7951 6.20487 19.6422 6.10899 19.454L4.32698 20.362ZM8.2 2C7.65645 2 7.18864 1.99922 6.80497 2.03057C6.40963 2.06287 6.01641 2.13419 5.63803 2.32698L6.54601 4.10899C6.59545 4.0838 6.69617 4.04612 6.96784 4.02393C7.25117 4.00078 7.62345 4 8.2 4V2ZM6 6.2C6 5.62345 6.00078 5.25117 6.02393 4.96784C6.04612 4.69617 6.0838 4.59545 6.10899 4.54601L4.32698 3.63803C4.13419 4.01641 4.06287 4.40963 4.03057 4.80497C3.99922 5.18864 4 5.65645 4 6.2H6ZM5.63803 2.32698C5.07354 2.6146 4.6146 3.07354 4.32698 3.63803L6.10899 4.54601C6.20487 4.35785 6.35785 4.20487 6.54601 4.10899L5.63803 2.32698ZM12 3V7.4H14V3H12ZM14.6 10H19V8H14.6V10ZM12 7.4C12 7.66353 11.9992 7.92131 12.0169 8.13823C12.0356 8.36682 12.0797 8.63656 12.218 8.90798L14 8C14.0293 8.05751 14.0189 8.08028 14.0103 7.97537C14.0008 7.85878 14 7.69653 14 7.4H12ZM14.6 8C14.3035 8 14.1412 7.99922 14.0246 7.9897C13.9197 7.98113 13.9425 7.9707 14 8L13.092 9.78201C13.3634 9.92031 13.6332 9.96438 13.8618 9.98305C14.0787 10.0008 14.3365 10 14.6 10V8ZM12.218 8.90798C12.4097 9.2843 12.7157 9.59027 13.092 9.78201L14 8V8L12.218 8.90798ZM9 18H12V16H9V18ZM9 14H13V12H9V14ZM9 10H10V8H9V10ZM18.937 16C18.937 16.1732 18.8915 16.3053 18.6175 16.5697C18.4638 16.718 18.2828 16.8653 18.0319 17.074C17.7936 17.2723 17.5141 17.5087 17.2299 17.7929L18.6441 19.2071C18.86 18.9913 19.0805 18.8033 19.3109 18.6116C19.5287 18.4305 19.7852 18.2223 20.0065 18.0087C20.4825 17.5493 20.937 16.9314 20.937 16H18.937ZM17.937 15C18.4893 15 18.937 15.4477 18.937 16H20.937C20.937 14.3431 19.5938 13 17.937 13V15ZM16.9684 15.7493C17.0795 15.3177 17.4724 15 17.937 15V13C16.5377 13 15.3645 13.957 15.0316 15.2507L16.9684 15.7493ZM17.9375 22H17.9475V20H17.9375V22Z" fill="#538bd0"></path> </g></svg>
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
 
<style>

/* .bg-image {
  background-image: url('src/assets/CV.png');
  background-repeat: no-repeat;
  background-position: center;
} */

.bg-image {
  background-image: url('../assets/CV.png');
  background-repeat: no-repeat;
  background-position: center;
}

</style>