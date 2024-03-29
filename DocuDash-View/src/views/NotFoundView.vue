<script setup>
import {onBeforeMount, ref} from "vue";
import { useRoute, useRouter } from "vue-router";

const route = useRoute();
const router = useRouter();

const goBack = () => {
    window.history.length > 1 ? router.go(-1) : router.push("/");
};

onBeforeMount(() => {
  goBack();
});

</script>

<template>
<!--<section class="bg-white ">-->
<!--    <div class="py-8 px-4 mx-auto max-w-screen-xl lg:py-16 lg:px-6">-->
<!--        <div class="mx-auto max-w-screen-sm text-center">-->
<!--            <h1 class="mb-4 text-7xl  font-extrabold lg:text-9xl text-violet-500 ">404</h1>-->
<!--            <p class="mb-4 text-3xl  font-bold text-gray-900 md:text-4xl dark:text-white">มีบางอย่างผิดพลาด...</p>-->
<!--            <p class="mb-4 text-lg font-light text-gray-500 dark:text-gray-400">ขออภัยค่ะ, ไม่พบหน้าที่คุณกำลังมองหา อาจจะเป็นเพราะ URL ไม่ถูกต้อง หรือหน้านี้อาจถูกย้ายหรือลบไปแล้ว <p class="mb-4 text-lg font-light text-gray-500">คุณสามารถกลับไปที่หน้าหลักเพื่อไปยังส่วนอื่น ๆ ของเว็บไซต์</p></p>-->
<!--            <button href="#" @click="goBack()" class="inline-flex text-white bg-violet-500 hover:bg-violet-600 font-medium rounded-lg text-lg px-5 py-2.5 text-center  my-4">กลับสู่หน้าแรก</button>-->
<!--        </div>   -->
<!--    </div>-->
<!--</section>-->
</template>