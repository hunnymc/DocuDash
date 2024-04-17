<script setup>
import {onBeforeMount, onMounted, ref, watch} from "vue";
import {useRoute, useRouter} from "vue-router";
import {useDocumentListStore} from "../stores/listOfDocumentStore";
import axios from "axios";
import Cookies from "js-cookie";

const route = useRoute();
const router = useRouter();
let isEverythingLoaded = ref(false);
let showPDF = ref(false);

const store = useDocumentListStore();

let mainURL = import.meta.env.VITE_API_URL;

// let mainURL = "http://localhost:5002";
// let mainURL = "http://cp23kw2.sit.kmutt.ac.th:10003";
// let mainURL = "https://capstone23.sit.kmutt.ac.th/kw2";

let doc = ref({
  // branchSource: "NOT_FOUND",
  // category: "NOT_FOUND",
  // dateAdd: "2023-12-06T06:13:03Z",
  // dateUpdate: "2023-12-06T06:13:03Z",
  // description: "NOT_FOUND",
  // emailSource: "NOT_FOUND@NOT_FOUND.mail.com",
  // filePath: "NOT_FOUND.pdf",
  // fromSource: "NOT_FOUND",
  // id: 5,
  // phoneSource: "NOT_FOUND",
  // secrecyLevel: "NOT_FOUND",
  // status: "NOT_FOUND",
  // title: "NOT_FOUND",
  // urgency: "NOT_FOUND"
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

const checkFileIsAvailable = async () => {
  const fileUrl = mainURL + '/api/files/' + store.getDocumentUserId + '/' + store.getDocumentFilename;
  try {
    const response = await axios.get(fileUrl, {
      headers: {
        "Authorization": "Bearer " + Cookies.get("accessToken"),
      },
    });
    if (response.status === 200) {
      console.log("File is available");
      showPDF.value = true;
    } else {
      console.log("File is not available");
      showPDF.value = false;
    }
  } catch (error) {
    console.log("Error occurred while checking file availability: ", error);
  }
};

onBeforeMount(() => {
  checkFileIsAvailable();
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
          <button class="m-1 mb-4 pb-4" data-popover-placement="bottom-end"
                  data-popover-target="popover-description" type="button">
            <svg aria-hidden="true" class="w-4 h-4 ms-2 text-gray-400 hover:text-gray-500"
                 fill="currentColor" viewBox="0 0 20 20" xmlns="http://www.w3.org/2000/svg">
              <path clip-rule="evenodd"
                    d="M18 10a8 8 0 11-16 0 8 8 0 0116 0zm-8-3a1 1 0 00-.867.5 1 1 0 11-1.731-1A3 3 0 0113 8a3.001 3.001 0 01-2 2.83V11a1 1 0 11-2 0v-1a1 1 0 011-1 1 1 0 100-2zm0 8a1 1 0 100-2 1 1 0 000 2z"
                    fill-rule="evenodd"></path>
            </svg>
            <span class="sr-only">Show information</span></button>
          <div id="popover-description"
               class="absolute z-10 invisible inline-block text-sm text-gray-500 transition-opacity duration-300 bg-white border border-gray-200 rounded-lg shadow-sm opacity-0 w-72 dark:bg-gray-800 dark:border-gray-600 dark:text-gray-400"
               data-popover
               role="tooltip">
            <div class="p-3 space-y-2">
              <h3 class="font-semibold text-gray-900 dark:text-white">รายละเอียดเอกสาร</h3>
              <p>ท่านสามารถดูรายละเอียดเอกสารและตัวอย่างไฟล์ได้ที่หน้านี้</p>
              <h3 class="font-semibold text-gray-900 dark:text-white">การแก้ไข</h3>
              <p>หากมีจุดผิดพลาดที่ท่านต้องการแก้ไขเอกสารนี้ กรุณาติดต่อผู้ดูแลระบบโดยตรง</p>
            </div>
            <div data-popper-arrow></div>
          </div>
        </h2>

        <!-- <form action="#"> -->
        <div class="grid gap-4 grid-rows-8 sm:grid-cols-2 sm:gap-6">
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
            <label class="block mb-2 text-sm font-bold text-gray-900 dark:text-white"
                   for="category">แผนก</label>
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
            <label class="block mb-2 text-sm font-bold text-gray-900 dark:text-white"
                   for="category">หมวดหมู่</label>
            <div
                class="block w-5/6 p-2.5 bg-gray-50 border border-gray-300 text-sm text-gray-500 dark:text-gray-400 dark:bg-gray-800 rounded-lg ">
              <h3 class="text-sm font-bold text-gray-900 dark:text-white ">{{ doc.category }}</h3>

            </div>
          </div>
          <br>
          <div>
            <label class="block mb-2 text-sm font-bold text-gray-900 dark:text-white"
                   for="category">ความเร่งด่วน</label>
            <div
                class="block w-5/6 p-2.5 bg-gray-50 border border-gray-300 text-sm text-gray-500 dark:text-gray-400 dark:bg-gray-800 rounded-lg ">
              <h3 class="text-sm font-bold text-gray-900 dark:text-white ">{{ doc.urgency }}</h3>

            </div>
          </div>
          <br>
          <div>
            <label class="block mb-2 text-sm font-bold text-gray-900 dark:text-white"
                   for="category">ชั้นความลับ</label>
            <div
                class="block w-5/6 p-2.5 bg-gray-50 border border-gray-300 text-sm text-gray-500 dark:text-gray-400 dark:bg-gray-800 rounded-lg ">
              <h3 class="text-sm font-bold text-gray-900 dark:text-white ">{{ doc.secrecyLevel }}</h3>

            </div>
          </div>
          <br>

          <!-- ที่แสดงไฟล์ -->
          <div class="w-full">
            <div class="  rounded-lg shadow-md w-5/6">
              <table
                  class="rounded-lg w-full text-sm text-left rtl:text-right text-gray-500 dark:text-gray-400">
                <thead
                    class="text-xs text-gray-700 uppercase bg-gray-50 dark:bg-gray-700 dark:text-gray-400">
                <tr>

                  <th class="px-6 py-3" scope="col">
                    ชื่อไฟล์
                  </th>

                </tr>
                </thead>
                <tbody>
                <tr v-show="showPDF"
                    class="bg-white border-b dark:bg-gray-800 dark:border-gray-700 hover:bg-gray-50 dark:hover:bg-gray-600">
                  <td class="px-6 py-4 text-blue-600 dark:text-blue-500 underline cursor-pointer"
                      @click="ClickFile">
                    {{ doc.filePath }}
                  </td>
                </tr>

                <tr v-show="!showPDF"
                    class="bg-white border-b dark:bg-gray-800 dark:border-gray-700 hover:bg-gray-50 dark:hover:bg-gray-600">
                  <td class="disabled px-6 py-4 text-black dark:text-blue-500">
                    <p class="text-red-600">⚠️ ไฟล์ไม่พร้อมใช้งาน</p>
                    <br>
                    {{ doc.filePath }}
                  </td>
                </tr>

                </tbody>
              </table>
              <!-- --------------------------------------------------- -->

              <!-- --------------------------------------------------- -->
            </div>
          </div>


        </div>
        <br>

      </div>

      <!-- Click to watch file -->
      <div>

        <div v-show="showPDF" class="border-slate-300 bg-gray-700 w-full rounded-lg shadow-md pdf-container flex items-center justify-center py-10">
          <embed :src="mainURL + '/api/files/' + store.getDocumentUserId + '/' + store.getDocumentFilename"
                 height="1000px" type="application/pdf" width="95%"/>
        </div>

        <div v-show="!showPDF" class="border-slate-300 bg-gray-700 w-full rounded-lg shadow-md pdf-container flex items-center justify-center py-10">
          <div class="py-8 px-auto ml-5 mr-auto  lg:py-12 w-full  max-width: 100%  justify-center ">
            <h2 class="mb-4 text-4xl font-semibold text-white text-center">⚠️  เอกสารไม่พร้อมใช้งาน</h2>
          </div>
        </div>
      </div>

    </section>

  </div>
</template>

<style></style>