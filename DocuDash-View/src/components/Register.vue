<script setup>
import { ref } from 'vue';
import { useRouter } from 'vue-router';
import axios from 'axios';
import Cookies from 'js-cookie';

const router = useRouter();

let mainUrl = import.meta.env.VITE_API_URL;

// let mainUrl = 'http://localhost:5002';
// let mainUrl = 'http://cp23kw2.sit.kmutt.ac.th:10003';
// let mainUrl = "https://capstone23.sit.kmutt.ac.th/kw2";

const newUser = ref({
    // "email": "johndoe10@example.com",
    // "username": "johndoe10",
    // "password": "password123",
    // "fullName": "johndoe10",
    // "role": "USER",
    // "branch": "ฝ่ายการตลาด"
});

const createUser = async () => {
    newUser.value.username = newUser.value.email.split('@')[0];
    const response = await axios.post(mainUrl + '/api/auth/register', newUser.value, {
        headers: {
            'Authorization': 'Bearer ' + Cookies.get('accessToken')
        }
    })
    .then((response) => {
        alert('บันทึกข้อมูลสำเร็จ');
        newUser.value = {};
        router.push('/kw2/register');
    })
    
    .catch((error) => {
        alert(error.response.data.message);
    });


};

</script>

<template>
    <section class="bg-gray-200 shadow-md sm:rounded-lg w-full">
        <div class="py-4 px-4 mx-auto max-w-2xl lg:py-16">
            <h2 class="mb-4 text-3xl font-bold text-gray-900 ">สร้างบัญชีผู้ใช้ใหม่</h2>
            <form action="#">
                <div class="grid gap-4 sm:grid-cols-2 sm:gap-6">
                    <!-- <div class="sm:col-span-2">
                  <label for="name" class="block mb-2 text-sm font-medium text-gray-900 ">ชื่อผู้ใช้ (Username)</label>
                  <input type="text" name="name" id="name" class="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-primary-600 focus:border-primary-600 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-primary-500 dark:focus:border-primary-500" 
                  placeholder="กรอกชื่อผู้ใช้" required="">
              </div> -->

                    <div class="sm:col-span-2">
                        <label for="fullName" class="block mb-2 text-sm font-medium text-gray-900 ">ชื่อ - นามสกุล </label>
                        <input v-model="newUser.fullName" type="text"
                            class="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-primary-600 focus:border-primary-600 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-primary-500 dark:focus:border-primary-500"
                            placeholder="กรอกชื่อ - นามสกุล" required="">
                    </div>
                    <div class="sm:col-span-2">
                        <label for="name" class="block mb-2 text-sm font-medium text-gray-900 ">รหัสผ่าน (Password)</label>
                        <input v-model="newUser.password" type="password"
                            class="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-primary-600 focus:border-primary-600 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-primary-500 dark:focus:border-primary-500"
                            placeholder="กรอกรหัสผ่าน" required="">
                    </div>



                    <div class="sm:col-span-2">
                        <label for="name" class="block mb-2 text-sm font-medium text-gray-900 ">อีเมล</label>
                        <input v-model="newUser.email" type="email"
                            class="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-primary-600 focus:border-primary-600 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-primary-500 dark:focus:border-primary-500"
                            placeholder="example@mail.com" required="">
                    </div>

                    <!-- <div class="w-full">
                  <label for="brand" class="block mb-2 text-sm font-medium text-gray-900 ">Brand</label>
                  <input type="text" name="brand" id="brand" class="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-primary-600 focus:border-primary-600 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-primary-500 dark:focus:border-primary-500" 
                  placeholder="Product brand" required="">
              </div>
              <div class="w-full">
                  <label for="price" class="block mb-2 text-sm font-medium text-gray-900 ">Price</label>
                  <input type="number" name="price" id="price" class="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-primary-600 focus:border-primary-600 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-primary-500 dark:focus:border-primary-500" placeholder="$2999" required="">
              </div> -->


                    <div class="sm:col-span-2">
                        <label for="category" class="block mb-2 text-sm font-medium text-gray-900 ">แผนก</label>
                        <select v-model="newUser.branch"
                            class="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-primary-500 focus:border-primary-500 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-primary-500 dark:focus:border-primary-500">
                            <option selected="">เลือกแผนกของผู้ใช้ </option>
                            <option value="ฝ่ายเอกสาร">ฝ่ายเอกสาร</option>
                            <option value="ฝ่ายการตลาด">ฝ่ายการตลาด</option>
                            <option value="ฝ่ายขาย">ฝ่ายขาย</option>
                            <option value="ฝ่ายบุคคล">ฝ่ายบุคคล</option>
                            <option value="ฝ่ายลูกค้าสัมพันธ์">ฝ่ายลูกค้าสัมพันธ์</option>
                            <option value="ฝ่ายบัญชี/การเงิน">ฝ่ายบัญชี/การเงิน</option>
                            <option value="ฝ่ายจัดซื้อ">ฝ่ายจัดซื้อ</option>
                            <option value="ฝ่าย IT">ฝ่าย IT</option>
                            <option value="อื่นๆ">อื่นๆ</option>
                        </select>
                    </div>

                    <div class="sm:col-span-2">
                        <label for="category" class="block mb-2 text-sm font-medium text-gray-900 "> บทบาท (Role)</label>
                        <select v-model="newUser.role"
                            class="bg-gray-50 border bo
                            rder-gray-300 text-gray-900 text-sm rounded-lg focus:ring-primary-500 focus:border-primary-500 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-primary-500 dark:focus:border-primary-500">
                            <option selected="">เลือก Role ของผู้ใช้</option>
                            <option value="ADMIN">Admin</option>
                            <option value="USER">User</option>
                            <option value="MANAGER">Manager</option>
                        </select>
                    </div>



                    <!-- <div class="sm:col-span-2">
                  <label for="description" class="block mb-2 text-sm font-medium text-gray-900 ">Description</label>
                  <textarea id="description" rows="8" class="block p-2.5 w-full text-sm text-gray-900 bg-gray-50 rounded-lg border border-gray-300 focus:ring-primary-500 focus:border-primary-500 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-primary-500 dark:focus:border-primary-500" placeholder="Your description here"></textarea>
              </div> -->
                </div>
                <a @click="createUser()" type="submit"
                    class="cursor-pointer flex-col justify-center hover:bg-green-800 items-center px-16 py-4 mt-4 sm:mt-6 text-sm font-bold text-white text-center bg-green-600 rounded-lg focus:ring-4 focus:ring-primary-200">
                    บันทึก
                </a>
            </form>
        </div>
    </section>
</template>

<style scoped></style>