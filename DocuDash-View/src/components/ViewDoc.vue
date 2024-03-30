<script setup>
import { ref, onMounted, onBeforeMount, watch, watchEffect } from "vue";
import { useRoute, useRouter } from "vue-router";
import { VuePDF, usePDF } from '@tato30/vue-pdf'
import { useDocumentListStore } from "../stores/listOfDocumentStore";
import axios from "axios";
import Cookies from "js-cookie";

const route = useRoute();
const router = useRouter();
const pdfRef = ref(null);
let isEverythingLoaded = ref(false);
let showPDF = ref(false);

const store = useDocumentListStore();

let mainURL = import.meta.env.VITE_API_URL;

// let mainURL = "http://localhost:5002";
// let mainURL = "http://cp23kw2.sit.kmutt.ac.th:10003";
// let mainURL = "https://capstone23.sit.kmutt.ac.th/kw2";

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
    const fileUrl = mainURL + "/api/files/" + doc.value.usersUserid.id + "/" + doc.value.filePath;
    // // window.open(fileUrl, '_blank');
    // const token = Cookies.get("accessToken"); // replace 'accessToken' with the actual key used to store the token
    // const urlWithToken = `${fileUrl}?token=${token}`;
    window.open(fileUrl, '_blank');
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
                    router.push("/kw2/login");
                    break;
                case 403:
                    alert("คุณไม่มีสิทธิ์เข้าถึงเอกสารนี้");
                    router.push("/kw2/list");
                    break;
                case 404:
                    router.push("/kw2/NotFound");
                    alert("ไม่พบเอกสารที่ต้องการ");
                    break;
                default:
                    alert("เกิดข้อผิดพลาดบางอย่าง");
                    break;
            }
        });

    doc.value = response.data;
};

onBeforeMount(() => {

});

onMounted(async () => {
    await getDocById();
    isEverythingLoaded.value = true;
});

watch(() => route.params.id, async () => {
    await getDocById();
    isEverythingLoaded.value = true;
}
);

</script>

<template>
    <div class="shadow-md sm:rounded-lg w-full ">

        <section class="bg-gray-200 dark:bg-gray-900 ">

            <div class="py-8 px-auto ml-5 mr-auto  lg:py-12 w-full  max-width: 100%  justify-center ">
                <h2 class="mb-4 text-4xl font-bold text-gray-900 dark:text-white"> รายละเอียดเอกสาร
                    <button class="m-1 mb-4 pb-4" data-popover-target="popover-description"
                        data-popover-placement="bottom-end" type="button">
                        <svg class="w-4 h-4 ms-2 text-gray-400 hover:text-gray-500" aria-hidden="true"
                            fill="currentColor" viewBox="0 0 20 20" xmlns="http://www.w3.org/2000/svg">
                            <path fill-rule="evenodd"
                                d="M18 10a8 8 0 11-16 0 8 8 0 0116 0zm-8-3a1 1 0 00-.867.5 1 1 0 11-1.731-1A3 3 0 0113 8a3.001 3.001 0 01-2 2.83V11a1 1 0 11-2 0v-1a1 1 0 011-1 1 1 0 100-2zm0 8a1 1 0 100-2 1 1 0 000 2z"
                                clip-rule="evenodd"></path>
                        </svg><span class="sr-only">Show information</span></button>
                    <div data-popover id="popover-description" role="tooltip"
                        class="absolute z-10 invisible inline-block text-sm text-gray-500 transition-opacity duration-300 bg-white border border-gray-200 rounded-lg shadow-sm opacity-0 w-72 dark:bg-gray-800 dark:border-gray-600 dark:text-gray-400">
                        <div class="p-3 space-y-2">
                            <h3 class="font-semibold text-gray-900 dark:text-white">รายละเอียดเอกสาร</h3>
                            <p>ท่านสามารถดูรายละเอียดเอกสารและตัวอย่างไฟล์ได้ที่หน้านี้</p>
                            <h3 class="font-semibold text-gray-900 dark:text-white">การแก้ไข</h3>
                            <p>หากมีจุดผิดพลาดที่ท่านต้องการแก้ไขเอกสารนี้ กรุณาติดต่อผู้ดูแลระบบโดยตรง</p>
                            <!-- <a href="#" class="flex items-center font-medium text-blue-600 dark:text-blue-500 dark:hover:text-blue-600 hover:text-blue-700 hover:underline">อ่านเพิ่มเติม <svg class="w-2 h-2 ms-1.5 rtl:rotate-180" aria-hidden="true" xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 6 10">
    <path stroke="currentColor" stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="m1 9 4-4-4-4"/>
  </svg></a> -->
                        </div>
                        <div data-popper-arrow></div>
                    </div>
                </h2>

                <!-- <form action="#"> -->
                <div class="grid gap-4 grid-rows-8 sm:grid-cols-2 sm:gap-6">
                    <!-- <div class="sm:col-span-2">
                  <label for="name" class="block mb-2 text-sm font-medium text-gray-900 dark:text-white">ชื่อ</label>
                  <input type="text" name="name" id="name" class="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-primary-600 focus:border-primary-600 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-primary-500 dark:focus:border-primary-500" placeholder="Type product name" required="">
              </div> -->
                    <div class="w-full">
                        <label class=" block mb-2 text-sm font-bold text-gray-900 dark:text-white">เลขที่ :</label>
                        <div
                            class="block p-1 w-24 bg-gray-50 border border-gray-300 text-sm text-gray-500 dark:text-gray-400 dark:bg-gray-800 rounded-lg ">
                            <h3 class="text-sm font-bold text-gray-900 dark:text-white ">{{ doc_id }}</h3>

                        </div>
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
                        <label class="block mb-2 text-sm font-bold text-gray-900 dark:text-white">จาก</label>
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
                            class="block mb-2 text-sm font-bold text-gray-900 dark:text-white">หมวดหมู่</label>
                        <div
                            class="block w-5/6 p-2.5 bg-gray-50 border border-gray-300 text-sm text-gray-500 dark:text-gray-400 dark:bg-gray-800 rounded-lg ">
                            <h3 class="text-sm font-bold text-gray-900 dark:text-white ">{{ doc.category }}</h3>

                        </div>
                    </div>
                    <br>
                    <div>
                        <label for="category"
                            class="block mb-2 text-sm font-bold text-gray-900 dark:text-white">ความเร่งด่วน</label>
                        <div
                            class="block w-5/6 p-2.5 bg-gray-50 border border-gray-300 text-sm text-gray-500 dark:text-gray-400 dark:bg-gray-800 rounded-lg ">
                            <h3 class="text-sm font-bold text-gray-900 dark:text-white ">{{ doc.urgency }}</h3>

                        </div>
                    </div>
                    <br>
                    <div>
                        <label for="category"
                            class="block mb-2 text-sm font-bold text-gray-900 dark:text-white">ชั้นความลับ</label>
                        <div
                            class="block w-5/6 p-2.5 bg-gray-50 border border-gray-300 text-sm text-gray-500 dark:text-gray-400 dark:bg-gray-800 rounded-lg ">
                            <h3 class="text-sm font-bold text-gray-900 dark:text-white ">{{ doc.secrecyLevel }}</h3>

                        </div>
                    </div>
                    <br>
                    <!-- <div>
                  <label  class="block mb-2 text-sm font-bold text-gray-900 dark:text-white">สาขา</label>
                  <input  id="item-weight" class="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-primary-600 focus:border-primary-600 block w-5/6 p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-primary-500 dark:focus:border-primary-500" placeholder="12" required="">
              </div>  -->

                    <!-- เลือกผู้ส่ง -->
                    <!-- <div class="w-full">
                        <label for="category"
                            class="block mb-2 text-sm font-bold text-gray-900 dark:text-white">เลือกผู้ส่ง</label>
                        <ul
                            class="w-5/6 text-sm font-medium text-gray-900 bg-white border border-gray-200 rounded-lg dark:bg-gray-700 dark:border-gray-600 dark:text-white">
                            <li class="w-full hover:bg-gray-50 border-b border-gray-200 rounded-t-lg dark:border-gray-600">
                                <div class="flex items-center ps-3">

                                    <label class="w-full py-3 ms-2 text-sm font-bold text-gray-900 dark:text-gray-300">นภา
                                        ฟ้าสวย</label>
                                    <label
                                        class="w-full py-3 ms-2 text-sm font-bold text-orange-400 dark:text-gray-300">งานเอกสาร</label>
                                </div>
                            </li>
                            <li class="w-full hover:bg-gray-50 border-b border-gray-200 rounded-t-lg dark:border-gray-600">
                                <div class="flex items-center ps-3">

                                    <label for="react-checkbox"
                                        class="w-full py-3 ms-2 text-sm font-bold text-gray-900 dark:text-gray-300">ใบฝ้าย
                                        นุ่มมิ่ม</label>
                                    <label for="angular-checkbox"
                                        class="w-full py-3 ms-2 text-sm font-bold text-orange-400 dark:text-gray-300">ฝ่ายขาย</label>
                                </div>
                            </li>
                            <li class="w-full hover:bg-gray-50 border-b border-gray-200 rounded-t-lg dark:border-gray-600">
                                <div class="flex items-center ps-3">

                                    <label for="angular-checkbox"
                                        class="w-full py-3 ms-2 text-sm font-bold text-gray-900 dark:text-gray-300">กันยา
                                        นาปี</label>
                                    <label for="angular-checkbox"
                                        class="w-full py-3 ms-2 text-sm font-bold text-orange-400 dark:text-gray-300">ฝ่ายผลิต</label>
                                </div>
                            </li>
                        </ul>
                    </div> -->
                    <!-- ผู้ลงนาม -->
                    <!-- <div class="w-full">
                        <label for="category"
                            class="block mb-2 text-sm font-bold text-gray-900 dark:text-white">เลือกผู้ลงนาม</label>
                        <ul
                            class="w-5/6 text-sm font-medium text-gray-900 bg-white border border-gray-200 rounded-lg dark:bg-gray-700 dark:border-gray-600 dark:text-white">
                            <li class="w-full hover:bg-gray-50 border-b border-gray-200 rounded-t-lg dark:border-gray-600">
                                <div class="flex items-center ps-3">

                                    <label class="w-full py-3 ms-2 text-sm font-bold text-gray-900 dark:text-gray-300">นภา
                                        ฟ้าสวย</label>
                                    <label
                                        class="w-full py-3 ms-2 text-sm font-bold text-orange-400 dark:text-gray-300">งานเอกสาร</label>
                                </div>
                            </li>
                            <li class="w-full hover:bg-gray-50 border-b border-gray-200 rounded-t-lg dark:border-gray-600">
                                <div class="flex items-center ps-3">

                                    <label
                                        class="w-full py-3 ms-2 text-sm font-bold text-gray-900 dark:text-gray-300">ใบฝ้าย
                                        นุ่มมิ่ม</label>
                                    <label
                                        class="w-full py-3 ms-2 text-sm font-bold text-orange-400 dark:text-gray-300">ฝ่ายขาย</label>
                                </div>
                            </li>
                            <li class="w-full hover:bg-gray-50 border-b border-gray-200 rounded-t-lg dark:border-gray-600">
                                <div class="flex items-center ps-3">

                                    <label class="w-full py-3 ms-2 text-sm font-bold text-gray-900 dark:text-gray-300">กันยา
                                        นาปี</label>
                                    <label
                                        class="w-full py-3 ms-2 text-sm font-bold text-orange-400 dark:text-gray-300">ฝ่ายผลิต</label>
                                </div>
                            </li>
                        </ul>
                    </div> -->

                    <!-- กล่องวางไฟล์ -->
                    <!-- <div class="flex items-center  w-full">
         <label for="dropzone-file" class="flex flex-col items-center justify-center 
         w-5/6 h-64 border-2 border-gray-300 border-dashed rounded-lg cursor-pointer bg-gray-50 
         dark:hover:bg-bray-800 dark:bg-gray-700 hover:bg-gray-100 dark:border-gray-600 dark:hover:border-gray-500 dark:hover:bg-gray-600">
        <div class="flex flex-col items-center justify-center pt-5 pb-6 ">
            <svg class="w-8 h-8 mb-4 text-gray-500 dark:text-gray-400" aria-hidden="true" xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 20 16">
                <path stroke="currentColor" stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M13 13h3a3 3 0 0 0 0-6h-.025A5.56 5.56 0 0 0 16 6.5 5.5 5.5 0 0 0 5.207 5.021C5.137 5.017 5.071 5 5 5a4 4 0 0 0 0 8h2.167M10 15V6m0 0L8 8m2-2 2 2"/>
            </svg>
            <p class="mb-2 text-sm text-gray-500 dark:text-gray-400"><span class="font-semibold">คลิ๊กเพื่ออัพโหลดเอกสาร</span> หรือลากไฟล์และวาง</p>
            <p class="text-xs text-gray-500 dark:text-gray-400">SVG, PNG, JPG, or Docx (MAX 10 MB)</p>
        </div>
        <input id="dropzone-file" type="file" class="hidden" />
        </label>
        </div>  -->

                    <!-- ที่แสดงไฟล์ -->
                    <div class="w-full">
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
                                        <!-- <td @click="ClickFile" class="px-6 py-4 font-semibold text-gray-900 dark:text-white">
                                            {{ doc.filePath  }}
                                        </td> -->
                                        <!-- <td class="px-6 py-4 font-semibold text-gray-900 dark:text-white">
                                            46.6 MB
                                        </td> -->

                                    </tr>

                                </tbody>
                            </table>
                            <!-- --------------------------------------------------- -->

                            <!-- --------------------------------------------------- -->
                        </div>
                    </div>



                    <!-- <div class="w-full">
                        <label for="description"
                            class="block mb-2 text-sm font-bold text-gray-900 dark:text-white">หมายเหตุ</label>
                        <div
                            class="block w-5/6 p-2.5 bg-gray-50 border border-gray-300 text-sm text-gray-500 dark:text-gray-400 dark:bg-gray-800 rounded-lg ">
                            <h3 class="text-sm font-bold text-gray-900 dark:text-white ">อยากเสียวก็เลี้ยวมา</h3>

                        </div>
                    </div> -->
                </div>
                <br>

                <!-- <button type="submit" class=" flex-col  justify-center hover:bg-green-800 items-center px-16 py-4 mt-4 sm:mt-6 text-sm font-bold text-white text-center bg-green-600 rounded-lg focus:ring-4 focus:ring-primary-200 ">
              ยืนยัน
          </button> -->
                <!-- </form> -->
            </div>

            <!-- 
            <div>
                <h3 class="flex items-center justify-center mb-4 text-2xl font-bold text-green-700 dark:text-white ">
                    ตัวอย่างไฟล์แนบ </h3>
                <div class="flex items-center justify-center ">
                    <div class="w-56 h-10 rounded-xl bg-gray-50 border border-gray-300 flex items-center justify-center">
                        <button class="text-white bg-green-700 my-2 ml-1 px-5 rounded-lg"
                            @click="scale = scale > 0.25 ? scale - 0.25 : scale">
                            -
                        </button>
                        <span class="mx-2 font-semibold "> {{ scale * 100 }}%</span>
                        <button class="text-white bg-green-700 my-2  px-5 rounded-lg"
                            @click="scale = scale < 2 ? scale + 0.25 : scale">
                            +
                        </button>
                    </div>
                </div>


                <div class="flex items-center justify-center ">
                    <div class="w-56 h-10 rounded-xl bg-gray-50 border border-gray-300  items-center justify-center">
                        <button class="text-white bg-green-700 my-2 ml-3 px-5 rounded-lg"
                            @click="page = page > 1 ? page - 1 : page">
                            <span>Prev</span>
                        </button>
                        <span class="mx-2 font-semibold ">{{ page }} / {{ pages }}</span>
                        <button class="text-white bg-green-700 my-2  px-5 rounded-lg"
                            @click="page = page < pages ? page + 1 : page">
                            Next
                        </button>
                    </div>
                </div>
            </div> -->

            <!-- Click to watch file -->
            <div>
                <!-- <button @click="showPDF = true">Show PDF</button> -->
                <!-- <div
                    class="border-slate-300 bg-gray-700 w-full rounded-lg shadow-md pdf-container flex items-center justify-center py-10">
                    <VuePDF ref="pdf" :pdf="pdf" :page="page" :scale="scale" />
                </div> -->
                <div>
                    <embed :src="mainURL + '/api/files/' + store.getDocumentUserId + '/' + store.getDocumentFilename"
                        type="application/pdf" width="100%" height="1500px" />
                </div>
            </div>

            <!-- <div
                class="border-slate-300 bg-gray-700 w-full rounded-lg shadow-md pdf-container flex items-center justify-center py-10">
                <VuePDF ref="pdf" :pdf="pdf" :page="page" :scale="scale" />
            </div> -->


        </section>

    </div>
</template>

<style></style>