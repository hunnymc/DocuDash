<script setup>
import { RouterLink, useRouter } from 'vue-router';
import { ref, onBeforeMount } from 'vue';
import Cookies from 'js-cookie';
import { FwbButton, FwbModal } from "flowbite-vue";

let isAdmin = ref(false);

const router = useRouter();

const CheckAdmin = () => {
    if (Cookies.get('role') === 'ADMIN') {
        isAdmin.value = true;
    }
};

onBeforeMount(() => {
    CheckAdmin();
});

const isShowModal = ref(false)

function MoveToEApprove() {
    router.push("/kw2/approve/list/pending-request");
}   

function closeModal() {
    isShowModal.value = false
}

function showModal() {
    isShowModal.value = true
}

</script>

<template>
    <div class="flex   min-h-screen ">
        <div class="w-56  bg-green-950 ">
            <!-- สำหรับ Admin เท่านั้น -->
            <div v-if="isAdmin">
                <div class="px-6 pt-8">
                    <hr class="border-slate-400" />
                </div>
                <a class="px-6 pt-4 mt-4   font-bold text-xs text-neutral-400">เมนูผู้ดูแลระบบ</a>

                <div class="px-6 pt-4 pb-8">
                    <a href="/kw2/user-management/register">
                        <button type="button"
                            class="text-white bg-green-500 hover:bg-green-700 focus:ring-4 focus:outline-none focus:ring-blue-300 font-medium rounded-lg text-sm px-5 py-1 text-center inline-flex items-center dark:bg-blue-600 dark:hover:bg-blue-700 dark:focus:ring-blue-800">
                            <svg class="-translate-x-3 rtl:rotate-180 w-7 h-7 ms-2" viewBox="0 0 24 24" fill="none"
                                xmlns="http://www.w3.org/2000/svg">
                                <g id="SVGRepo_bgCarrier" stroke-width="0"></g>
                                <g id="SVGRepo_tracerCarrier" stroke-linecap="round" stroke-linejoin="round"></g>
                                <g id="SVGRepo_iconCarrier">
                                    <rect fill="white"></rect>
                                    <path d="M12 6V18" stroke="#ffffff" stroke-linecap="round" stroke-linejoin="round">
                                    </path>
                                    <path d="M6 12H18" stroke="#ffffff" stroke-linecap="round" stroke-linejoin="round">
                                    </path>
                                </g>
                            </svg>เพิ่มผู้ใช้ใหม่
                        </button>
                    </a>

                  
                </div>
            </div>
            
            <!--    -----------------------------------------------------------------------      -->
            

        </div>
    </div>
</template>

<style></style>
