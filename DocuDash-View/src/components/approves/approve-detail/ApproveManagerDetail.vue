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
    <li class="flex md:w-full items-center text-blue-600 dark:text-blue-500 sm:after:content-[''] after:w-full after:h-1 after:border-b after:border-blue-600 after:border-1 after:hidden sm:after:inline-block after:mx-6 xl:after:mx-10 dark:after:border-gray-700">
        <span class="flex items-center  sm:after:hidden  after:text-gray-200 dark:after:text-gray-500">
            <svg class="w-3.5 h-3.5 sm:w-4 sm:h-4 me-2.5" aria-hidden="true" xmlns="http://www.w3.org/2000/svg" fill="currentColor" viewBox="0 0 20 20">
                <path d="M10 .5a9.5 9.5 0 1 0 9.5 9.5A9.51 9.51 0 0 0 10 .5Zm3.707 8.207-4 4a1 1 0 0 1-1.414 0l-2-2a1 1 0 0 1 1.414-1.414L9 10.586l3.293-3.293a1 1 0 0 1 1.414 1.414Z"/>
            </svg>
            Submit<span class="hidden sm:inline-flex sm:ms-2">request</span>
        </span>  
    </li>
    <li class="flex md:w-full items-center after:content-[''] after:w-full after:h-1 after:border-b after:border-blue-600 after:border-1 after:hidden sm:after:inline-block after:mx-6 xl:after:mx-10 dark:after:border-gray-700">
        <span class="flex items-center text-blue-600 after:content-['/'] sm:after:hidden after:mx-2 after:text-gray-200 dark:after:text-gray-500">
            <svg class="w-3.5 h-3.5 sm:w-4 sm:h-4 me-2.5" aria-hidden="true" xmlns="http://www.w3.org/2000/svg" fill="currentColor" viewBox="0 0 20 20">
                <path d="M10 .5a9.5 9.5 0 1 0 9.5 9.5A9.51 9.51 0 0 0 10 .5Zm3.707 8.207-4 4a1 1 0 0 1-1.414 0l-2-2a1 1 0 0 1 1.414-1.414L9 10.586l3.293-3.293a1 1 0 0 1 1.414 1.414Z"/>
            </svg>
            Admin  <span class="hidden sm:inline-flex sm:ms-2">checks</span>
        </span>
    </li>
    <li class="text-blue-600 flex items-center">
        <span class="me-2"><svg class="w-3.5 h-3.5 sm:w-4 sm:h-4 me-2.5" aria-hidden="true" xmlns="http://www.w3.org/2000/svg" fill="currentColor" viewBox="0 0 20 20">
                <path d="M10 .5a9.5 9.5 0 1 0 9.5 9.5A9.51 9.51 0 0 0 10 .5Zm3.707 8.207-4 4a1 1 0 0 1-1.414 0l-2-2a1 1 0 0 1 1.414-1.414L9 10.586l3.293-3.293a1 1 0 0 1 1.414 1.414Z"/>
            </svg></span>
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
                  
                    
                    <div>
                        <!-- divเปล่าเอาไว้ใช้ -->
                    </div>

                     <div>
                        
                    </div>
                    <br>
                
                    <!-- ที่แสดงไฟล์ -->
                    <div class="w-full">
                        <hr class="flex mx-auto w-full mb-2 border-slate-400" />
                          <a class="mb-3 font-bold text-lg text-teal-700">ไฟล์แนบคำขออนุมัติ</a><br><br>
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


                    <div class="w-5/6">
                        <hr class="w-full mb-2 border-slate-400" />
                          <a class="mb-3 font-bold text-lg text-teal-700">การอนุมัติ</a><br><br>
                         
<!-- ปุ่มอนุมัติ -->
                         
                        <ul class="grid w-full gap-6 md:grid-cols-2">
                     <li>
                     <div class="inline-flex items-center justify-between w-full p-5 text-white bg-green-600 border border-gray-200 rounded-lg cursor-pointer dark:hover:text-gray-300 dark:border-gray-700 dark:peer-checked:text-blue-500 peer-checked:border-blue-600 peer-checked:text-blue-600 hover:text-gray-100 hover:bg-green-700 dark:text-gray-400 dark:bg-gray-800 dark:hover:bg-gray-700">                           
                     <div class="block">
                        <div class="w-full text-lg font-semibold">อนุมัติคำขอ</div>
                          <div class="w-full">อนุมัติคำร้องของผู้ใช้เพื่อดำเนินการต่อ</div>
                            </div>
                              <svg class="w-5 h-5 ms-3 rtl:rotate-180" aria-hidden="true" xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 14 10">
                             <path stroke="currentColor" stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M1 5h12m0 0L9 1m4 4L9 9"/>
                             </svg>
        </div>
    </li>

            <li>
        <div  class="inline-flex items-center justify-between w-full p-5 text-white bg-red-700 border border-gray-200 rounded-lg cursor-pointer dark:hover:text-gray-300 dark:border-gray-700 dark:peer-checked:text-blue-500 peer-checked:border-blue-600 peer-checked:text-blue-600 hover:text-gray-100 hover:bg-red-800 dark:text-gray-400 dark:bg-gray-800 dark:hover:bg-gray-700">
            <div class="block">
                <div class="w-full text-lg font-semibold">ไม่อนุมัติ</div>
                <div class="w-full">ไม่อนุมัติคำร้องของผู้ใช้ที่ยื่นเข้ามา</div>
            </div>
           <svg class="w-5 h-5 ms-3 rtl:rotate-180"  viewBox="0 0 512 512" version="1.1" xmlns="http://www.w3.org/2000/svg" xmlns:xlink="http://www.w3.org/1999/xlink" fill="#000000"><g id="SVGRepo_bgCarrier" stroke-width="0"></g><g id="SVGRepo_tracerCarrier" stroke-linecap="round" stroke-linejoin="round"></g><g id="SVGRepo_iconCarrier"> <title>cancel</title> <g id="Page-1" stroke="none" stroke-width="1" fill="none" fill-rule="evenodd"> <g id="work-case" fill="#ffffff" transform="translate(91.520000, 91.520000)"> <polygon id="Close" points="328.96 30.2933333 298.666667 1.42108547e-14 164.48 134.4 30.2933333 1.42108547e-14 1.42108547e-14 30.2933333 134.4 164.48 1.42108547e-14 298.666667 30.2933333 328.96 164.48 194.56 298.666667 328.96 328.96 298.666667 194.56 164.48"> </polygon> </g> </g> </g></svg>
        </div>
    </li>
</ul>

<!-- กล่องค้นหา -->


                <!-- ปุ่ม -->
                
                

                    </div>
                    


                </div>
                <br>      
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