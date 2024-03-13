<script setup>
import { ref, onMounted , onUnmounted } from "vue";
import { useRouter } from "vue-router";
import axios from "axios";
import Cookies from "js-cookie";

const router = useRouter();

// let mainURL = "http://localhost:5002";
let mainURL = "http://cp23kw2.sit.kmutt.ac.th:10003";

let userEmail = ref("");
let password = ref("");

async function login() {

  if (userEmail.value === "" && password.value === "") {
    alert('Please enter your email and password');
    return null;
  }

  if (userEmail.value === "") {
    alert('Please enter your email');
    return null;
  }

  if (password.value === "") {
    alert('Please enter your password');
    return null;
  }

    await axios.post(
      mainURL + '/api/auth/login'
      // 'http://cp23kw2.sit.kmutt.ac.th:35000/api/auth/login'
      , {
      userEmail: userEmail.value,
      password: password.value,
    })
    
    .then((response) => {
      if (response.status === 200) {
        
        // Store the JWT token
        Cookies.set('accessToken', response.data.accessToken);
        Cookies.set('refreshToken', response.data.refreshToken);
        Cookies.set('role', response.data.role);
        Cookies.set('email', userEmail.value);

        alert('Login successful');

        // Redirect to the home page
        router.push('/');

      } else {
        alert ('Login failed');
        return null;
      }
    })
    
    .catch((error) => {
      if (error.response.status === 401) {
        alert('Invalid email address or password. Please try again.');
      }

      if (error.response.status === 500) {
        alert('Server error');
      }

      if (error.response.status === 404) {
        alert('Not found');
      }

      if (error.response.status === 400) {
        alert("Invalid email address or password. Please try again.");
      }
    });
}

</script>

<template>
  <section class="bg-gray-50 dark:bg-gray-900">
    <div
      class="flex flex-col items-center justify-center px-6 py-8 mx-auto md:h-screen lg:py-0"
    >
      <a
        class="flex items-center mb-6 text-2xl font-bold text-gray-900 dark:text-white"
      >
        DocuDash
      </a>
      <div
        class="w-full bg-white rounded-lg shadow dark:border md:mt-0 sm:max-w-md xl:p-0 dark:bg-gray-800 dark:border-gray-700"
      >
        <div class="p-6 space-y-4 md:space-y-6 sm:p-8">
          <h1
            class="text-xl font-bold leading-tight tracking-tight text-gray-900 md:text-2xl dark:text-white"
          >
            เข้าสู่ระบบ E-Document
          </h1>
          <form class="space-y-4 md:space-y-6" action="#">
            <div>
              <label
                for="email"
                class="block mb-2 text-sm font-medium text-gray-900 dark:text-white"
                >Your email</label
              >
              <input
                type="email"
                name="email"
                id="email"
                class="bg-gray-50 border border-gray-300 text-gray-900 sm:text-sm rounded-lg focus:ring-primary-600 focus:border-primary-600 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500"
                placeholder="name@company.com"
                v-model="userEmail"
              />
            </div>
            <div>
              <label
                for="password"
                class="block mb-2 text-sm font-medium text-gray-900 dark:text-white"
                >Password</label
              >
              <input
                type="password"
                name="password"
                id="password"
                placeholder="••••••••"
                class="bg-gray-50 border border-gray-300 text-gray-900 sm:text-sm rounded-lg focus:ring-primary-600 focus:border-primary-600 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500"
                v-model="password"
                />
            </div>
            <!-- <div class="flex items-center justify-between">
              <div class="flex items-start">
                <div class="flex items-center h-5">
                  <input
                    id="remember"
                    aria-describedby="remember"
                    type="checkbox"
                    class="w-4 h-4 border border-gray-300 rounded bg-gray-50 focus:ring-3 focus:ring-primary-300 dark:bg-gray-700 dark:border-gray-600 dark:focus:ring-primary-600 dark:ring-offset-gray-800"
                  />
                </div>
                <div class="ml-3 text-sm">
                  <label for="remember" class="text-gray-500 dark:text-gray-300"
                    >Remember me</label
                  >
                </div>
              </div>
              <a
                href="#"
                class="text-sm font-medium text-primary-600 hover:underline dark:text-white"
                >ลืมรหัสผ่านหรือไม่ ?</a
              >
            </div> -->
            <div
              @click="login()"
              class="w-full text-white bg-green-500 cursor-pointer hover:bg-green-700 shadow-lg hover:shadow-green-900 transition delay-150 duration-300 ease-in-out focus:ring-4 focus:outline-none focus:ring-primary-300 font-medium rounded-lg text-sm px-5 py-2.5 text-center dark:bg-primary-600 dark:hover:bg-primary-700 dark:focus:ring-primary-800"
            >
              เข้าสู่ระบบ
          </div>

            <!-- <a @click="login()">ปุ่มสำรอง</a> -->
            <!-- <p class="text-sm font-light text-gray-500 dark:text-gray-400">
              ลืมรหัสผ่าน?
              <a
                href="#"
                class="font-medium text-primary-600 hover:underline dark:text-primary-500"
                >Sign up</a
              >
            </p> -->
          </form>
        </div>
      </div>
    </div>
  </section>
</template>

<style scoped></style>
