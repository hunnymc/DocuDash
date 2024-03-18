<script setup>
import { ref, onMounted, onBeforeMount, watch, watchEffect } from "vue";
import { useRoute, useRouter } from "vue-router";
import { useDocumentListStore } from "../../../stores/listOfDocumentStore.js";
import axios from "axios";
import Cookies from "js-cookie";

const route = useRoute();
const router = useRouter();
let isEverythingLoaded = ref(false);

const store = useDocumentListStore();

// let mainURL = "http://localhost:5002";
let mainURL = "http://cp23kw2.sit.kmutt.ac.th:10003";
let pdfURL = mainURL + "/api/files/" + store.getDocumentUserId + "/" + store.getDocumentFilename;

const page = ref(1);
const scale = ref(1);

let doc = ref({
    branchSource: "NOT_FOUND",
    category: "NOT_FOUND",
    dateAdd: "2023-12-06T06:13:03Z",
    dateUpdate: "2023-12-06T06:13:03Z",
    description: "NOT_FOUND",
    emailSource: "NOT_FOUND@NOT_FOUND.mail.com",
    filePath: "NOT_FOUND.pdf",
    fromSource: "NOT_FOUND",
    id: 5,
    phoneSource: "NOT_FOUND",
    secrecyLevel: "NOT_FOUND",
    status: "NOT_FOUND",
    title: "NOT_FOUND",
    urgency: "NOT_FOUND"
});

let doc_id = ref();

const ClickFile = () => {
    const fileUrl =
        // "http://cp23kw2.sit.kmutt.ac.th:10003/api/files/"
        mainURL + "/api/files/" + doc.value.usersUserid.id + "/" + doc.value.filePath;
    // window.open(fileUrl, '_blank');
    const token = Cookies.get("accessToken"); // replace 'accessToken' with the actual key used to store the token
    const urlWithToken = `${fileUrl}?token=${token}`;
    window.open(urlWithToken, '_blank');
};

const getDocById = async () => {
    doc_id.value = route.params.id;
    const response = await axios.get(
        // "http://cp23kw2.sit.kmutt.ac.th:10003/api/doc/" 
        mainURL + "/api/doc/"
        + doc_id.value, {
        headers: {
            "Authorization": "Bearer " + Cookies.get("accessToken"),
        },
    })
        .catch((error) => {
            switch (error.response.status) {
                case 401:
                    alert("กรุณาเข้าสู่ระบบ");
                    router.push("/login");
                    break;
                case 403:
                    alert("คุณไม่มีสิทธิ์เข้าถึงเอกสารนี้");
                    router.push("/list");
                    break;
                case 404:
                    router.push("/NotFound");
                    alert("ไม่พบเอกสารที่ต้องการ");
                    break;
                default:
                    alert("เกิดข้อผิดพลาดบางอย่าง");
                    break;
            }
        });

    doc.value = response.data;
};

// onBeforeMount(() => {
//     usePDF(mainURL + "/api/files/" + store.getDocumentUserId + "/" + store.getDocumentFilename);
//
// });

onMounted(async () => {
    await getDocById();
    if (store.getDocumentUserId == null || store.getDocumentFilename == null) {
        router.push("/list");
    }
    isEverythingLoaded.value = true;
});

watch(() => route.params.id, async () => {
    await getDocById();
    isEverythingLoaded.value = true;
}
);

// watchEffect(() => {
//     usePDF(mainURL + "/api/files/" + store.getDocumentUserId + "/" + store.getDocumentFilename);
// });

</script>

<template>
    <div class="shadow-md sm:rounded-lg w-full ">
        <section class="bg-gray-200 dark:bg-gray-900 ">
            <div class="py-8 px-auto ml-5 mr-auto  lg:py-12 w-full  max-width: 100%  justify-center ">
                <h2 class="mb-4 text-4xl font-bold text-gray-900 dark:text-white"> รายละเอียดคำขออนุมัติ               
                </h2>
                
                
<ol class="pt-4 flex items-center w-1/2 text-sm font-medium text-center text-gray-500  sm:text-base">
    <li class="flex md:w-full items-center text-blue-600 dark:text-blue-500 sm:after:content-[''] after:w-full after:h-1 after:border-b after:border-gray-500 after:border-1 after:hidden sm:after:inline-block after:mx-6 xl:after:mx-10 dark:after:border-gray-700">
        <span class="flex items-center  sm:after:hidden  after:text-gray-200 dark:after:text-gray-500">
            <svg class="w-3.5 h-3.5 sm:w-4 sm:h-4 me-2.5" aria-hidden="true" xmlns="http://www.w3.org/2000/svg" fill="currentColor" viewBox="0 0 20 20">
                <path d="M10 .5a9.5 9.5 0 1 0 9.5 9.5A9.51 9.51 0 0 0 10 .5Zm3.707 8.207-4 4a1 1 0 0 1-1.414 0l-2-2a1 1 0 0 1 1.414-1.414L9 10.586l3.293-3.293a1 1 0 0 1 1.414 1.414Z"/>
            </svg>
            Submit<span class="hidden sm:inline-flex sm:ms-2">request</span>
        </span>  
    </li>
    <li class="flex md:w-full items-center after:content-[''] after:w-full after:h-1 after:border-b after:border-gray-500 after:border-1 after:hidden sm:after:inline-block after:mx-6 xl:after:mx-10 dark:after:border-gray-700">
        <span class="flex items-center after:content-['/'] sm:after:hidden after:mx-2 after:text-gray-200 dark:after:text-gray-500">
            <span class="me-2 ">2</span>
            Admin  <span class="hidden sm:inline-flex sm:ms-2">checks</span>
        </span>
    </li>
    <li class="flex items-center">
        <span class="me-2">3</span>
        Approval
    </li>
</ol>

                
                <div class="grid gap-4 grid-rows-8 sm:grid-cols-2 sm:gap-6">
                   
                    <div class="w-full">
                        <!-- <label class=" block mb-2 text-sm font-bold text-gray-900 dark:text-white">เลขที่ :</label>
                        <div
                            class="block p-1 w-24 bg-gray-50 border border-gray-300 text-sm text-gray-500 dark:text-gray-400 dark:bg-gray-800 rounded-lg ">
                            <h3 class="text-sm font-bold text-gray-900 dark:text-white ">{{ doc_id }}</h3>

                        </div> -->
                    </div>
                    <br>
                    <div class="w-full">
                        <label class="block mb-2 text-sm font-bold text-gray-900 dark:text-white">เรื่อง :</label>
                        <div
                            class="block w-5/6 p-2.5 bg-gray-50 border border-gray-300 text-sm text-gray-500 dark:text-gray-400 dark:bg-gray-800 rounded-lg ">
                            <h3 class="text-sm font-bold text-gray-900 dark:text-white ">{{ doc.title }}</h3>

                        </div>
                    </div>
                    <div class="w-full ">
                        <label class="block mb-2 text-sm font-bold text-gray-900 dark:text-white">ผู้ยื่นขออนุมัติ</label>
                        <div
                            class="block w-5/6 p-2.5 bg-gray-50 border border-gray-300 text-sm text-gray-500 dark:text-gray-400 dark:bg-gray-800 rounded-lg ">
                            <h3 class="text-sm font-bold text-gray-900 dark:text-white ">{{ doc.fromSource }}</h3>
                        </div>
                    </div>
                    <div class="w-full">
                        <label class="block mb-2 text-sm font-bold text-gray-900 dark:text-white">รายละเอียด</label>
                        <div
                            class="block w-5/6 p-2.5 bg-gray-50 border border-gray-300 text-sm text-gray-500 dark:text-gray-400 dark:bg-gray-800 rounded-lg ">
                            <h3 class="text-sm font-bold text-gray-900 dark:text-white ">{{ doc.description }}</h3>

                        </div>
                    </div>
                    <div>
                        <label for="category"
                            class="block mb-2 text-sm font-bold text-gray-900 dark:text-white">แผนก</label>
                        <div
                            class="block w-5/6 p-2.5 bg-gray-50 border border-gray-300 text-sm text-gray-500 dark:text-gray-400 dark:bg-gray-800 rounded-lg ">
                            <h3 class="text-sm font-bold text-gray-900 dark:text-white ">{{ doc.branchSource }}</h3>

                        </div>
                    </div>
                    <div class="w-full">
                        <label class="block mb-2 text-sm font-bold text-gray-900 dark:text-white">โทรศัพท์</label>
                        <div
                            class="block w-5/6 p-2.5 bg-gray-50 border border-gray-300 text-sm text-gray-500 dark:text-gray-400 dark:bg-gray-800 rounded-lg ">
                            <h3 class="text-sm font-bold text-gray-900 dark:text-white ">{{ doc.phoneSource }}</h3>

                        </div>
                    </div>
                    <div class="w-full">
                        <label class="block mb-2 text-sm font-bold text-gray-900 dark:text-white">อีเมล</label>
                        <div
                            class="block w-5/6 p-2.5 bg-gray-50 border border-gray-300 text-sm text-gray-500 dark:text-gray-400 dark:bg-gray-800 rounded-lg ">
                            <h3 class="text-sm font-bold text-gray-900 dark:text-white ">{{ doc.emailSource }}</h3>

                        </div>
                    </div>
                    <!-- หมวดหมู่ -->
                    <div>
                        <label for="category"
                            class="block mb-2 text-sm font-bold text-gray-900 dark:text-white">ประเภทการขออนุมัติ</label>
                        <div
                            class="block w-5/6 p-2.5 bg-gray-50 border border-gray-300 text-sm text-gray-500 dark:text-gray-400 dark:bg-gray-800 rounded-lg ">
                            <h3 class="text-sm font-bold text-gray-900 dark:text-white ">{{ doc.category }}</h3>

                        </div>
                    </div>
                    
                    <div>
                        <label for="category"
                            class="block mb-2 text-sm font-bold text-gray-900 dark:text-white">ความเร่งด่วน</label>
                        <div
                            class="block w-5/6 p-2.5 bg-gray-50 border border-gray-300 text-sm text-gray-500 dark:text-gray-400 dark:bg-gray-800 rounded-lg ">
                            <h3 class="text-sm font-bold text-gray-900 dark:text-white ">{{ doc.urgency }}</h3>

                        </div>
                    </div>
                    
                    <div>
                        <label for="category"
                            class="block mb-2 text-sm font-bold text-gray-900 dark:text-white">ชั้นความลับ</label>
                        <div
                            class="block w-5/6 p-2.5 bg-gray-50 border border-gray-300 text-sm text-gray-500 dark:text-gray-400 dark:bg-gray-800 rounded-lg ">
                            <h3 class="text-sm font-bold text-gray-900 dark:text-white ">{{ doc.secrecyLevel }}</h3>

                        </div>
                    </div>
                    <br>
                    <div>
                        <hr class="flex mx-auto w-full mb-2 border-slate-400" />
                          <a class="mb-3 font-bold text-xs text-teal-700">สถานะการอนุมัติ</a>
                <br><br>
                        <label for="category"
                            class="block mb-2 text-sm font-bold text-gray-900 dark:text-white">ผู้ดูแลระบบ</label>
                        <div
                            class="block w-5/6 p-2.5 bg-gray-50 border border-gray-300 text-sm text-gray-500 dark:text-gray-400 dark:bg-gray-800 rounded-lg ">
                            <h3 class="text-sm font-bold text-gray-900 dark:text-white ">กำลังตรวจสอบคำขอ</h3>

                        </div>
                    </div>
                    <br>
                    <div>
                        <label for="category"
                            class="block mb-2 text-sm font-bold text-gray-900 dark:text-white">ผู้จัดการที่รับเรื่อง</label>
                        <div
                            class="block w-5/6 p-2.5 bg-gray-50 border border-gray-300 text-sm text-gray-500 dark:text-gray-400 dark:bg-gray-800 rounded-lg ">
                            <!-- <h3 class="text-sm font-bold text-gray-900 dark:text-white ">กำลังตรวจสอบคำขออนุมัติ</h3> -->
                            <h3 class="text-sm font-bold text-gray-900 dark:text-white ">รอการตรวจสอบจากผู้ดูแล</h3>

                        </div>
                    </div>
                    <div>
                        
                    </div>

                     <div>
                        <ol class="ml-10 m-6 relative text-gray-500 border-s border-gray-700 dark:border-gray-700 dark:text-gray-400">                  
                        <li class="mb-10 ms-8">            
                                <span class="absolute flex items-center justify-center w-8 h-8 bg-green-200 rounded-full -start-4 ring-4 ring-white ">
            <svg class="w-3.5 h-3.5 text-green-500 " aria-hidden="true" xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 16 12">
                <path stroke="currentColor" stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M1 5.917 5.724 10.5 15 1.5"/>
            </svg>
        </span>
        <h3 class="font-medium leading-tight">ยื่นขออนุมัติ</h3>
        <p class="text-sm">สำเร็จ</p>
    </li>
    <li class="mb-10 ms-8">
        <span class="absolute flex items-center justify-center w-8 h-8 bg-gray-100 rounded-full -start-4 ring-4 ring-white ">
            <svg class="w-3.5 h-3.5 text-gray-500 dark:text-gray-400" aria-hidden="true" xmlns="http://www.w3.org/2000/svg" fill="currentColor" viewBox="0 0 20 16">
                <path d="M18 0H2a2 2 0 0 0-2 2v12a2 2 0 0 0 2 2h16a2 2 0 0 0 2-2V2a2 2 0 0 0-2-2ZM6.5 3a2.5 2.5 0 1 1 0 5 2.5 2.5 0 0 1 0-5ZM3.014 13.021l.157-.625A3.427 3.427 0 0 1 6.5 9.571a3.426 3.426 0 0 1 3.322 2.805l.159.622-6.967.023ZM16 12h-3a1 1 0 0 1 0-2h3a1 1 0 0 1 0 2Zm0-3h-3a1 1 0 1 1 0-2h3a1 1 0 1 1 0 2Zm0-3h-3a1 1 0 1 1 0-2h3a1 1 0 1 1 0 2Z"/>
            </svg>
        </span>
        <h3 class="font-medium leading-tight">ผู้ดูแลตรวจสอบคำขออนุมัติ</h3>
        <p class="text-sm">อยู่ระหว่างดำเนินการ</p>
    </li>
    <li class="mb-10 ms-8">
        <span class="absolute flex items-center justify-center w-8 h-8 bg-gray-100 rounded-full -start-4 ring-4 ring-white dark:ring-gray-900 dark:bg-gray-700">
            <svg class="w-3.5 h-3.5 text-gray-500 dark:text-gray-400" aria-hidden="true" xmlns="http://www.w3.org/2000/svg" fill="currentColor" viewBox="0 0 18 20">
                <path d="M16 1h-3.278A1.992 1.992 0 0 0 11 0H7a1.993 1.993 0 0 0-1.722 1H2a2 2 0 0 0-2 2v15a2 2 0 0 0 2 2h14a2 2 0 0 0 2-2V3a2 2 0 0 0-2-2Zm-3 14H5a1 1 0 0 1 0-2h8a1 1 0 0 1 0 2Zm0-4H5a1 1 0 0 1 0-2h8a1 1 0 1 1 0 2Zm0-5H5a1 1 0 0 1 0-2h2V2h4v2h2a1 1 0 1 1 0 2Z"/>
            </svg>
        </span>
        <h3 class="font-medium leading-tight">ผู้จัดการอนุมัติเอกสารและโครงการ</h3>
        <p class="text-sm">อยู่ระหว่างดำเนินการ</p>
    </li>
    <li class="ms-8">
        <span class="absolute flex items-center justify-center w-8 h-8 bg-gray-100 rounded-full -start-4 ring-4 ring-white dark:ring-gray-900 dark:bg-gray-700">
            <svg class="w-3.5 h-3.5 text-gray-500 dark:text-gray-400" aria-hidden="true" xmlns="http://www.w3.org/2000/svg" fill="currentColor" viewBox="0 0 18 20">
                <path d="M16 1h-3.278A1.992 1.992 0 0 0 11 0H7a1.993 1.993 0 0 0-1.722 1H2a2 2 0 0 0-2 2v15a2 2 0 0 0 2 2h14a2 2 0 0 0 2-2V3a2 2 0 0 0-2-2ZM7 2h4v3H7V2Zm5.7 8.289-3.975 3.857a1 1 0 0 1-1.393 0L5.3 12.182a1.002 1.002 0 1 1 1.4-1.436l1.328 1.289 3.28-3.181a1 1 0 1 1 1.392 1.435Z"/>
            </svg>
        </span>
        <h3 class="font-medium leading-tight">การขออนุมัติสำเร็จ</h3>
        <p class="text-sm">อยู่ระหว่างดำเนินการ</p>
    </li>
</ol>
                    </div>


                    <br>
                
                    <!-- ที่แสดงไฟล์ -->
                    <div class="w-full">
                        <hr class="flex mx-auto w-full mb-2 border-slate-400" />
                          <a class="mb-3 font-bold text-xs text-teal-700">ไฟล์แนบคำขออนุมัติ</a><br><br>
                        <div class="  rounded-lg shadow-md w-5/6">
                            <table
                                class="rounded-lg w-full text-sm text-left rtl:text-right text-gray-500 dark:text-gray-400">
                                <thead
                                    class="text-xs text-gray-700 uppercase bg-gray-50 dark:bg-gray-700 dark:text-gray-400">
                                    <tr>

                                        <th scope="col" class="px-6 py-3">
                                            ชื่อไฟล์
                                        </th>

                                        <!-- <th scope="col" class="px-6 py-3">
                                            ขนาด
                                        </th> -->

                                    </tr>
                                </thead>
                                <tbody>
                                    <tr
                                        class="bg-white border-b dark:bg-gray-800 dark:border-gray-700 hover:bg-gray-50 dark:hover:bg-gray-600">
                                        <td @click="ClickFile"
                                            class="px-6 py-4 text-blue-600 dark:text-blue-500 underline cursor-pointer">
                                            {{ doc.filePath }}
                                        </td>
                                    
                                    </tr>

                                </tbody>
                            </table>
                            
                        </div>
                    </div>



                    
                </div>
                <br>

                
            </div>
            
            <div>
                <h3 class="flex items-center justify-center mb-4 text-2xl font-bold text-green-700 dark:text-white ">
                    ตัวอย่างไฟล์แนบ </h3>

            </div>

            <!-- Click to watch file -->
            <div>
                <!-- <button @click="showPDF = true">Show PDF</button> -->
                <div
                    class="border-slate-300 bg-gray-700 w-full rounded-lg shadow-md pdf-container flex items-center justify-center py-10">
                    <!-- <VuePDF ref="pdf" :pdf="pdf" :page="page" :scale="scale" /> -->
<!--                    <embed :src="pdfURL" type="application/pdf" width="100%" height="400px" />-->
                    <embed src="https://www.clickdimensions.com/links/TestPDFfile.pdf" type="application/pdf" width="98%" height="400px" />
                </div>
            </div>

        </section>

    </div>
</template>

<style lang="scss" scoped>

</style>