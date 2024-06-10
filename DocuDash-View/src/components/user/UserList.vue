<script setup>
import axios from "axios";
import Cookies from "js-cookie";
import {onMounted, ref} from "vue";
import {useRouter} from "vue-router";
import {useLoginUserStore} from "../../stores/LoginUserStore.js";

let listOfUsers = ref([]);
let router = useRouter();

let mainURL = import.meta.env.VITE_API_URL;

const getAllUsers = async () => {
  const response = await axios.get(
      mainURL + "/api/doc/user"
      , {
        headers: {
          Authorization: `Bearer ${Cookies.get("accessToken")}`,
        },
      });

  listOfUsers.value = response.data;
};

const ClickToViewUser = async (user) => {
  console.log(user.id);
  useLoginUserStore().setUser(user);
  await router.push(`/kw2/user-management/detail/${user.id}`)
};

onMounted(() => {
  getAllUsers();
});

</script>


<template>

    <div class="w-full relative overflow-x-auto shadow-md sm:rounded-lg">

        <!-- Header -->
        <section class="flex items-center  bg-gray-50 ">
            <div class="w-full mx-auto ">
                <!-- Start coding here -->
                <div class="relative overflow-hidden bg-white  dark:bg-gray-800 ">
                    <div
                        class="border border-slate-300 flex-row items-center justify-between p-4 space-y-3 sm:flex sm:space-y-0 sm:space-x-4">
                        <div>
                            <h5 class="mr-3 font-semibold dark:text-white text-3xl">รายชื่อผู้ใช้ในระบบทั้งหมด</h5>
                            <p class="text-gray-500 ">รวมรายชื่อของผู้ใช้ในระบบทั้งหมด</p>
                        </div>
                    </div>
                </div>
            </div>
        </section>
        <table class="w-full text-sm text-left rtl:text-right text-gray-500 dark:text-gray-400">
            <thead class="text-xs text-gray-700 uppercase bg-gray-100 dark:bg-gray-700 dark:text-gray-400">
                <tr>
                    <th class="py-3" scope="col">
                        <!-- จุดแดง  -->
                    </th>
                    <th class="px-6 py-2" scope="col">
                        ชื่อ-นามสกุล
                    </th>
                    <th class="px-6 py-2" scope="col">
                        แผนก
                    </th>
                    <th class="px-6 py-2" scope="col">
                        เบอร์โทร
                    </th>
                    <th class="px-6 py-2" scope="col">
                        อีเมลล์
                    </th>
                    <th class="px-6 py-2" scope="col">
                        บทบาทในระบบ
                    </th>
                    <th class="px-6 py-2" scope="col">
                        ดำเนินการ
                    </th>
                </tr>
            </thead>

            <tbody>
                <tr v-for="user in listOfUsers" :key="user.id"
                    class="bg-white border-b dark:bg-gray-800 dark:border-gray-700 hover:bg-gray-50 dark:hover:bg-gray-600">
                    <td class="pl-4 items-center justify-center">

                    </td>
                    <td class="flex items-center py-2 text-gray-900 whitespace-nowrap dark:text-white">
                        <div class="ps-3">
                          <div class="font-normal text-gray-500">ID: {{ user.id }}</div>
                          <div class="text-base font-semibold">{{ user.fullName}}</div>
                        </div>
                    </td>
                    <td class="px-6 py-2">
                      {{user.branch}}
                    </td>
                    <td class="px-6 py-2">
                      {{ user.phone ? user.phone : "ไม่มีข้อมูล"}}
                    </td>
                    <td class="px-6 py-2">
                        {{ user.email }}
                    </td>
                    <td class="px-6 py-2">
                        {{ user.role }}
                    </td> 
                    <td class="px-6 py-2">

                        <button
                            @click="ClickToViewUser(user)"
                            class="font-medium text-blue-600 dark:text-blue-500 hover:underline"
                            type="button">ตรวจสอบ
                        </button>

                    </td>
                </tr>
            </tbody>

            <!-- <tbody>
        <tr>
          <td class="text-center text-black" colspan="7">
            <section class="bg-white dark:bg-gray-900">
              <div class="grid max-w-screen-xl px-4 py-8 mx-auto lg:gap-8 xl:gap-0 lg:py-16 lg:grid-cols-12">
                <div class="mr-auto place-self-center lg:col-span-7">
                  <h1
                      class="max-w-2xl mb-4 text-4xl font-extrabold tracking-tight leading-none md:text-5xl xl:text-6xl dark:text-white">
                    ไม่มีเอกสารในระบบ
                  </h1>
                  <p class="max-w-2xl mb-6 font-light text-gray-500 lg:mb-8 md:text-lg lg:text-xl dark:text-gray-400">
                    ขณะนี้ยังไม่มีเอกสารที่ถูกส่งถึงท่าน
                    ท่านสามารถเช็คเอกสารใหม่ได้ผ่านเมนูแจ้งเตือน
                    <span class="text-blue-600">หากมีเอกสารระบบจะทำการแจ้งเตือนอีกครั้ง
                      </span>หรือติดต่อแอดมินเพี่อสอบถามข้อมูลเพิ่มเติม
                  </p>
                  <a class="inline-flex items-center justify-center px-5 py-3 mr-3 text-base font-medium text-center text-white rounded-lg bg-blue-800 hover:bg-blue-900 dark:focus:ring-primary-900"
                     href="/kw2/approve/list">
                    กลับสู่หน้าแรก
                    <svg class="w-5 h-5 ml-2 -mr-1" fill="currentColor" viewBox="0 0 20 20"
                         xmlns="http://www.w3.org/2000/svg">
                      <path clip-rule="evenodd"
                            d="M10.293 3.293a1 1 0 011.414 0l6 6a1 1 0 010 1.414l-6 6a1 1 0 01-1.414-1.414L14.586 11H3a1 1 0 110-2h11.586l-4.293-4.293a1 1 0 010-1.414z"
                            fill-rule="evenodd"></path>
                    </svg>
                  </a>
                  <a class="inline-flex items-center justify-center px-5 py-3 text-base font-medium text-center text-gray-900 border border-gray-300 rounded-lg hover:bg-gray-100 focus:ring-4 focus:ring-gray-100 dark:text-white dark:border-gray-700 dark:hover:bg-gray-700 dark:focus:ring-gray-800"
                     href="#">
                    ติดต่อแอดมิน
                  </a>
                </div>
                <div class="hidden lg:mt-0 lg:col-span-5 lg:flex">
                  <img alt="mockup" src="../../../assets/im1.png"/>
                </div>
              </div>
            </section>
          </td>
        </tr>
        </tbody> -->


        </table>
    </div>
</template>

<style scoped></style>