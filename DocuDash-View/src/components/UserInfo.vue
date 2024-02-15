<script setup>
import { ref, onMounted, onUnmounted, onBeforeMount } from "vue";
import { useRoute, useRouter } from "vue-router";
import axios from "axios";
import Cookies from "js-cookie";

let route = useRoute();

let user = ref({
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
        , { email: Cookies.get("email") }
        , { headers: { "Authorization": "Bearer " + Cookies.get("accessToken"), } })
        .then((response) => {
            if (response.status === 200) {
                user.value = { ...response.data };
                Cookies.set("userId", response.data.id);            }
        });
};

onBeforeMount(() => {
    getUserInfo();
});



</script>

<template>
    <div class="w-full bg-white overflow-hidden shadow rounded-lg border">
        <div class="px-4 py-5 sm:px-6">
            <h1 class="font-extrabold text-xl leading-6  text-gray-900">
                ข้อมูลผู้ใช้งาน
            </h1>
            <p class="mt-1 max-w-2xl text-sm text-gray-500">
                รายละเอียดข้อมูลผู้ใช้งาน
            </p>
        </div>
        <div class="border-t border-gray-200 px-4 py-5 sm:p-0">
            <dl class="sm:divide-y sm:divide-gray-200">
                <div class="py-3 sm:py-5 sm:grid sm:grid-cols-3 sm:gap-4 sm:px-6">
                    <dt class="text-sm font-medium text-gray-500">
                        ชื่อ - นามสกุล
                    </dt>
                    <dd class="mt-1 text-sm text-gray-900 sm:mt-0 sm:col-span-2">
                        {{ user.fullName }}
                    </dd>
                </div>
                <div class="py-3 sm:py-5 sm:grid sm:grid-cols-3 sm:gap-4 sm:px-6">
                    <dt class="text-sm font-medium text-gray-500">
                        Email
                    </dt>
                    <dd class="mt-1 text-sm text-gray-900 sm:mt-0 sm:col-span-2">
                        {{ user.email }}
                    </dd>
                </div>
                <div class="py-3 sm:py-5 sm:grid sm:grid-cols-3 sm:gap-4 sm:px-6">
                    <dt class="text-sm font-medium text-gray-500">
                        เบอร์โทรศัพท์
                    </dt>
                    <dd class="mt-1 text-sm text-gray-900 sm:mt-0 sm:col-span-2">
                        {{ user.phone }}
                    </dd>
                </div>
                <div class="py-3 sm:py-5 sm:grid sm:grid-cols-3 sm:gap-4 sm:px-6">
                    <dt class="text-sm font-medium text-gray-500">
                        ตำแหน่ง/แผนก
                    </dt>
                    <dd class="mt-1 text-sm text-gray-900 sm:mt-0 sm:col-span-2">
                        {{ user.branch }}
                    </dd>
                </div>

                <div class="py-3 sm:py-5 sm:grid sm:grid-cols-3 sm:gap-4 sm:px-6">
                    <dt class="text-sm font-medium text-gray-500">

                    </dt>
                    <dd class="mt-1 text-sm text-gray-900 sm:mt-0 sm:col-span-2">

                    </dd>
                </div>

            </dl>
        </div>


        <section class="bg-white dark:bg-gray-900">
            <div class="max-w-screen-xl px-4 py-8 mx-auto text-center lg:py-16 lg:px-6">
                <dl class="grid max-w-screen-md gap-8 mx-auto text-gray-900 sm:grid-cols-3 dark:text-white">
                    <div class="flex flex-col items-center justify-center">
                        <dt class="mb-2 text-3xl md:text-4xl font-extrabold">XXk /แผ่น</dt>
                        <dd class="font-light text-gray-500 dark:text-gray-400">ประหยัดกระดาษไป</dd>
                    </div>
                    <div class="flex flex-col items-center justify-center">
                        <dt class="mb-2 text-3xl md:text-4xl font-extrabold">XX /ต้น</dt>
                        <dd class="font-light text-gray-500 dark:text-gray-400">เทียบเท่ากับต้นไม้</dd>
                    </div>
                    <div class="flex flex-col items-center justify-center">
                        <dt class="mb-2 text-3xl md:text-4xl font-extrabold">XXk /บาท</dt>
                        <dd class="font-light text-gray-500 dark:text-gray-400">ประหยัดค่าไฟไป</dd>
                    </div>
                </dl>
            </div>
        </section>









    </div>
</template>
 
<style></style>