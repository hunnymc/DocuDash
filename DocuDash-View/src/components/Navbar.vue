<script setup>
import { ref, onMounted, onBeforeMount } from "vue";
import Cookies from "js-cookie";

let userid = Cookies.get("documents_DocumentID1");
let showMenu = ref(false);

let isClickUser = false;

const toggleNav = () => (showMenu.value = !showMenu.value);

const user = ref({
  id: 0,
  name: "จ๊อบแจ๊บ - คนถูพื้น",
  role: "คนถูพื้น",
});

const clickUser = (id) => {
  if (id == 1) {
    user.value = {
      id: 1,
      name: "นภา ฟ้าสวย - งานเอกสาร",
      role: "Admin",
    };
  } else if (id == 2) {
    user.value = {
      id: 2,
      name: "ใบฝ้าย นุ่มมิ่ม - ฝ่ายขาย",
      role: "User",
    };
  } else if (id == 3) {
    user.value = {
      id: 3,
      name: "กันยา นาปี - ฝ่ายผลิต",
      role: "Manager",
    };
  }
};

const changeUserButton = (id) => {

  clickUser(id);
  Cookies.set("documents_DocumentID1", id);
  location.reload();
  confirm("คุณต้องการเปลี่ยนตำแหน่งใช่หรือไม่");
  location.reload();
  
};

onBeforeMount(() => {
  const userid = Cookies.get("documents_DocumentID1");
  // changeUserButton(userid);
  clickUser(userid)

});


</script>

<template>
  <div class="bg-green-900">
    <nav
      class="container ml-4 sticky-top md:flex md:justify-between md:items-center"
    >
      <!-- **** โลโก้เว็บ **** -->
      <!-- animate__animated animate__flip -->
      <div
        class="flex items-center justify-between "
      >
        <a
          href="/list"
          class="text-xl font-bold text-gray-100 md:text-2xl hover:text-indigo-400 cursor-pointer"
        >
          <img
            class="h-32 max-w-xs object-contain object-left contrast-125 brightness-150"
            src="/public/img/DD1.png"
            alt="image description"
          />
        </a>
        <!-- Mobile menu button -->
        <div @click="toggleNav" class="flex md:hidden">
          <button
            type="button"
            class="text-gray-100 hover:text-gray-400 focus:outline-none focus:text-gray-400"
          >
            <svg viewBox="0 0 24 24" class="w-6 h-6 fill-current">
              <path
                fill-rule="evenodd"
                d="M4 5h16a1 1 0 0 1 0 2H4a1 1 0 1 1 0-2zm0 6h16a1 1 0 0 1 0 2H4a1 1 0 0 1 0-2zm0 6h16a1 1 0 0 1 0 2H4a1 1 0 0 1 0-2z"
              ></path>
            </svg>
          </button>
        </div>
      </div>
      <div
        class="text-gray-100 focus:outline-none focus:text-gray-400 mr-72 pr-72 text-sm font-extrabold hidden-8xl font-sans"
      >
      <!-- animate__animated animate__fadeIn -->
        <h2 class="text-gray-100 ">
          <span
            class="text-transparent bg-clip-text bg-gradient-to-r to-emerald-600 from-sky-400 uppercase text-base"
            >E-Document
          </span>
          <a
            class="text-transparent bg-clip-text bg-gradient-to-r from-indigo-200 via-red-200 to-yellow-100"
            >ระบบงานสารบรรณอิเล็กทรอนิกส์</a
          >
        </h2>
      </div>
      <!-- Mobile Menu open แฮม-->
      <ul
        :class="showMenu ? 'flex' : 'hidden'"
        class="flex-col mt-8 space-y-4 translate-x-64 md:flex md:space-y-0 md:flex-row md:items-center md:space-x-10 md:mt-0"
      >
        <!-- **** หน้าแรก **** -->
        <li>
          <a
            href="/list"
            class="text-gray-100 hover:text-indigo-400 cursor-pointer"
          >
            หน้าแรก
          </a>
        </li>
        <li
          data-tooltip-target="testtool1"
          data-tooltip-trigger="click"
          class="text-gray-100 hover:text-indigo-400 cursor-pointer"
        >
          แจ้งเตือน
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

        <li class="text-gray-100 hover:text-indigo-400 cursor-pointer">
          ข้อมูลผู้ใช้งาน
        </li>
        <li class="text-gray-100 hover:text-indigo-400 cursor-pointer">
          <!-- **** ปุ่มเลือกโรล **** -->
          <button
            id="dropdownInformationButton"
            data-tooltip-target="tooltip-light"
            data-dropdown-toggle="dropdownInformation"
            class="relative text-white bg-green-700 hover:bg-green-500 font-medium rounded-lg text-sm px-5 py-2.5 text-center inline-flex items-center dark:bg-blue-600 dark:hover:bg-blue-700 dark:focus:ring-blue-800"
            type="button"
          >
            เลือกตำแหน่ง
            <svg
              class="w-2.5 h-2.5 ms-3"
              aria-hidden="true"
              xmlns="http://www.w3.org/2000/svg"
              fill="none"
              viewBox="0 0 10 6"
            >
              <path
                stroke="currentColor"
                stroke-linecap="round"
                stroke-linejoin="round"
                stroke-width="2"
                d="m1 1 4 4 4-4"
              />
            </svg>
          </button>
          <div
            id="tooltip-light"
            role="tooltip"
            class="absolute z-10 invisible inline-block px-3 py-2 text-sm font-medium text-gray-900 bg-white border border-gray-200 rounded-lg shadow-sm opacity-0 tooltip"
          >
            ปุ่มนี้ทำมาเพื่อทดสอบการใช้งานเท่านั้น
            <div class="tooltip-arrow" data-popper-arrow></div>
          </div>

          <!-- Dropdown  -->
          <div
            id="dropdownInformation"
            class="z-10 hidden bg-white divide-y divide-gray-100 rounded-lg shadow w-44 dark:bg-gray-700 dark:divide-gray-600"
          >
            <div
              v-if="(userid = 0)"
              class="px-4 py-3 text-sm text-gray-900 dark:text-white"
            >
              <div>จ๊อบแจ๊บ - คนถูพื้น</div>
              <div class="font-medium truncate">JobSuksik@gmail.com</div>
            </div>

            <div v-else class="px-4 py-3 text-sm text-gray-900 dark:text-white">
              <div>{{ user.name }} - {{ user.role }}</div>
            </div>

            <ul
              class="py-2 text-sm text-gray-700 dark:text-gray-200"
              aria-labelledby="dropdownInformationButton"
            >
              <li>
                <router-link
                  @click="changeUserButton(1)"
                  to="/"
                  class="block px-4 py-2 hover:bg-gray-100 dark:hover:bg-gray-600 dark:hover:text-white"
                >
                  นภา ฟ้าสวย - งานเอกสาร</router-link
                >
              </li>
              <li>
                <router-link
                  @click="changeUserButton(2)"
                  to="/"
                  class="block px-4 py-2 hover:bg-gray-100 dark:hover:bg-gray-600 dark:hover:text-white"
                >
                  ใบฝ้าย นุ่มมิ่ม - ฝ่ายขาย</router-link
                >
              </li>
              <li>
                <router-link
                  @click="changeUserButton(3)"
                  to="/"
                  class="block px-4 py-2 hover:bg-gray-100 dark:hover:bg-gray-600 dark:hover:text-white"
                >
                  กันยา นาปี - ฝ่ายผลิต
                </router-link>
              </li>
            </ul>
            <div class="py-2">
              <a
                href="#"
                class="block px-4 py-2 text-sm text-gray-700 hover:bg-gray-100 dark:hover:bg-gray-600 dark:text-gray-200 dark:hover:text-white"
                >Sign out</a
              >
            </div>
          </div>
        </li>
        <!-- <li class="text-gray-100 hover:text-indigo-400 cursor-pointer">ออกจากระบบ</li> -->
      </ul>
    </nav>
  </div>
</template>
