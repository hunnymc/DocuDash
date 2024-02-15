<script setup>
import { ref, onMounted, onBeforeMount } from "vue";
import { initFlowbite } from 'flowbite'
import { useRouter } from "vue-router";
import Cookies from "js-cookie";
import axios from 'axios';

const router = useRouter();

let showMenu = ref(false);

const toggleNav = () => (showMenu.value = !showMenu.value);

const user = ref({
  username: "",
  fullName: "",
  role: "",
  email: "",
  branch: "",
  phone: "",
});

const getUserInfo = async () => {
    await axios.post(
        'http://localhost:5002/api/auth/user-info'
        // 'http://cp23kw2.sit.kmutt.ac.th:35000/api/auth/user-info'
    , { email: Cookies.get("email") }
    , { headers: { "Authorization": "Bearer " + Cookies.get("accessToken"), }})
    .then((response) => {
      if (response.status === 200) {
        user.value = { ...response.data };
        Cookies.set("userId", response.data.id);
      }
    });
};

function logout() {
  Cookies.remove("accessToken");
  Cookies.remove("refreshToken");
  Cookies.remove("role");
  Cookies.remove("email");
  router.push("/login");
}

onBeforeMount(() => {
  getUserInfo();
});

onMounted(() => {
    initFlowbite();
})



// const changeUserButton = (id) => {
//   clickUser(id);
//   Cookies.set("documents_DocumentID1", id);
//   location.reload();
//   confirm("คุณต้องการเปลี่ยนตำแหน่งใช่หรือไม่");
//   location.reload();
// };

// onBeforeMount(() => {
//   const userid = Cookies.get("documents_DocumentID1");
//   // changeUserButton(userid);
//   clickUser(userid);
// });

// const clickUser = (id) => {
//   if (id == 1) {
//     user.value = {
//       id: 1,
//       name: "นภา ฟ้าสวย - งานเอกสาร",
//       role: "Admin",
//     };
//   } else if (id == 2) {
//     user.value = {
//       id: 2,
//       name: "ใบฝ้าย นุ่มมิ่ม - ฝ่ายขาย",
//       role: "User",
//     };
//   } else if (id == 3) {
//     user.value = {
//       id: 3,
//       name: "กันยา นาปี - ฝ่ายผลิต",
//       role: "Manager",
//     };
//   }
// };

</script>

<template>
  <div class="bg-green-900">
    <nav class="container ml-4 sticky-top md:flex md:justify-between md:items-center">
      <!-- **** โลโก้เว็บ **** -->
      <!-- animate__animated animate__flip -->
      <div class="flex items-center justify-between">
        <a href="/list" class="text-xl font-bold text-gray-100 md:text-2xl hover:text-indigo-400 cursor-pointer">
          <img class="h-32 max-w-xs object-contain object-left contrast-125 brightness-150" src="/public/img/DD1.png"
            alt="image description" />
        </a>
        <!-- Mobile menu button -->
        <div @click="toggleNav" class="flex md:hidden">
          <button type="button" class="text-gray-100 hover:text-gray-400 focus:outline-none focus:text-gray-400">
            <svg viewBox="0 0 24 24" class="w-6 h-6 fill-current">
              <path fill-rule="evenodd"
                d="M4 5h16a1 1 0 0 1 0 2H4a1 1 0 1 1 0-2zm0 6h16a1 1 0 0 1 0 2H4a1 1 0 0 1 0-2zm0 6h16a1 1 0 0 1 0 2H4a1 1 0 0 1 0-2z">
              </path>
            </svg>
          </button>
        </div>
      </div>
      <div
        class="text-gray-100 focus:outline-none focus:text-gray-400 mr-72 pr-72 text-sm font-extrabold hidden-8xl font-sans">
        <!-- animate__animated animate__fadeIn -->
        <h2 class="text-gray-100">
          <span
            class="text-transparent bg-clip-text bg-gradient-to-r to-emerald-600 from-sky-400 uppercase text-base">E-Document
          </span>
          <a
            class="text-transparent bg-clip-text bg-gradient-to-r from-indigo-200 via-red-200 to-yellow-100">ระบบงานสารบรรณอิเล็กทรอนิกส์</a>
        </h2>
      </div>
      <!-- Mobile Menu open แฮม-->
      <ul :class="showMenu ? 'flex' : 'hidden'"
        class="flex-col mt-8 space-y-4 translate-x-64 md:flex md:space-y-0 md:flex-row md:items-center md:space-x-10 md:mt-0">
        <!-- **** หน้าแรก **** -->
        <li>
          <a href="/list" class="text-gray-100 hover:text-indigo-400 cursor-pointer">
            หน้าแรก
          </a>
        </li>
        <li data-tooltip-trigger="click" class="text-gray-100 hover:text-indigo-400 cursor-pointer">
          แจ้งเตือน
        </li>

        <li>
          <button id="dropdownAvatarNameButton" data-dropdown-toggle="dropdownAvatarName"
            class="flex items-center text-sm pe-1 font-medium text-gray-900 rounded-full hover:text-blue-600 dark:hover:text-blue-500 md:me-0 dark:focus:ring-gray-700 dark:text-white"
            type="button">
            <span class="sr-only">Open user menu</span>
            <div class="relative w-10 h-10 overflow-hidden bg-gray-100 rounded-full dark:bg-gray-600">
              <svg class="absolute w-12 h-12 text-gray-400 -left-1" fill="currentColor" viewBox="0 0 20 20"
                xmlns="http://www.w3.org/2000/svg">
                <path fill-rule="evenodd" d="M10 9a3 3 0 100-6 3 3 0 000 6zm-7 9a7 7 0 1114 0H3z" clip-rule="evenodd">
                </path>
              </svg>
            </div>
            <!-- ชื่อเต็ม -->
            <a class="ml-4 font-medium text-sm text-slate-300 hover:text-white"> {{ user.fullName }}</a>
            <svg class="w-2.5 h-2.5 ms-3 text-slate-300" aria-hidden="true" xmlns="http://www.w3.org/2000/svg" fill="none"
              viewBox="0 0 10 6">
              <path stroke="currentColor" stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                d="m1 1 4 4 4-4" />
            </svg>
          </button>

          <!-- <div class="relative w-10 h-10 overflow-hidden bg-gray-100 rounded-full dark:bg-gray-600">
    <svg class="absolute w-12 h-12 text-gray-400 -left-1" fill="currentColor" viewBox="0 0 20 20" xmlns="http://www.w3.org/2000/svg"><path fill-rule="evenodd" d="M10 9a3 3 0 100-6 3 3 0 000 6zm-7 9a7 7 0 1114 0H3z" clip-rule="evenodd"></path></svg>
</div> -->

          <!-- Dropdown menu -->
          <div id="dropdownAvatarName"
            class="z-10 hidden bg-white divide-y divide-gray-100 rounded-lg shadow w-44 dark:bg-gray-700 dark:divide-gray-600">
            <div class="px-4 py-3 text-sm text-gray-900 dark:text-white">
              <!-- ตำแหน่ง -->
              <div class="font-medium">ตำแหน่ง : {{ user.branch }}</div>
              <!-- อีเมล -->
              <div class="truncate"> {{ user.email }} </div>
            </div>
            <ul class="py-2 text-sm text-gray-700 dark:text-gray-200"
              aria-labelledby="dropdownInformdropdownAvatarNameButtonationButton">
              <li>
                <a href="#"
                  @click="router.push('/user')"
                  class="block px-4 py-2 hover:bg-gray-100 dark:hover:bg-gray-600 dark:hover:text-white">
                  ดูโปรไฟล์</a>
              </li>
            </ul>
            <div class="py-2">
              <a href="#"
                @click="logout()"
                class="block px-4 py-2 text-sm text-gray-700 hover:bg-gray-100 dark:hover:bg-gray-600 dark:text-gray-200 dark:hover:text-white">
                ออกจากระบบ</a>
            </div>
          </div>
        </li>

        <!-- กดทิปส์ -->
        <!-- <div
          id="testtool1"
          role="tooltip"
          class="absolute z-10 invisible inline-block px-3 py-2 text-sm font-medium text-white bg-gray-900 rounded-lg shadow-sm opacity-0 tooltip dark:bg-gray-700"
        >
          งาน Release หน้าจ้า
          <div class="tooltip-arrow" data-popper-arrow></div>
        </div> -->

        <!-- <li class="text-gray-100 hover:text-indigo-400 cursor-pointer">
          ข้อมูลผู้ใช้งาน
        </li> -->

        <!-- <li class="text-gray-100 hover:text-indigo-400 cursor-pointer">ออกจากระบบ</li> -->
      </ul>
    </nav>
  </div>
</template>

<style></style>
