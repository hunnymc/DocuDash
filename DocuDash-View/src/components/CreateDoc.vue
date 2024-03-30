<script setup>
import {onMounted, onUnmounted, ref, watch} from "vue";
import axios from "axios";
import router from "../router";
import Cookies from "js-cookie";
import {useLoginUserStore} from "../stores/LoginUserStore";

const loginUserStore = useLoginUserStore();

let mainURL = import.meta.env.VITE_API_URL;

// let mainURL = "http://localhost:5002";
// let mainURL = "http://cp23kw2.sit.kmutt.ac.th:10003";
// let mainURL = "https://capstone23.sit.kmutt.ac.th/kw2";

const showAlert = ref(false);

const listOfUsers = ref([]);

const getDocID = async () => {
  const response = await axios.get(
      mainURL + "/api/doc/newdocid",
      {
        headers: {
          Authorization: `Bearer ${Cookies.get("accessToken")}`,
        },
      });

  newdocid.value = response.data;
};

const getAllUsers = async () => {
  const response = await axios.get(
      mainURL + "/api/doc/user"
      , {
        headers: {
          Authorization: `Bearer ${Cookies.get("accessToken")}`,
        },
      });

  listOfUsers.value = response.data;
  console.log(listOfUsers.value);
};

let newdocid = ref(0);

// const selectedSentUser = ref([]);

// // const userDocs = ref([
//   {
//     documentsDocumentid1Id: -1,
//     usersUseridId: -1,
//   },
//   {
//     documentsDocumentid1Id: -1,
//     usersUseridId: -1,
//   }
// // ]);

const selectedSentUser = ref([]);
const userDocs = ref([]);

function addDatatoNewDoc() {
  newDocdata.value.title = "test";
  newDocdata.value.fromSource = "test";
  newDocdata.value.emailSource = "test@test.com";
  newDocdata.value.branchSource = "ฝ่ายการตลาด";
  newDocdata.value.category = "เอกสารภายนอก";
  newDocdata.value.urgency = "ปกติ";
  newDocdata.value.secrecyLevel = "ลับ";
  newDocdata.value.description = "test";
  newDocdata.value.phoneSource = "0812345678";
}

watch(selectedSentUser, (newVal, oldVal) => {

  const addedUsers = newVal.filter((x) => !oldVal.includes(x));
  const removedUsers = oldVal.filter((x) => !newVal.includes(x));

  addedUsers.forEach((userId) => {
    userDocs.value.push({
      documentsDocumentid1Id: newdocid.value, // Replace with your actual document ID
      usersUseridId: userId,
      ownerDocument: localStorage.getItem("fullName"),
    });
  });

  removedUsers.forEach((userId) => {
    const index = userDocs.value.findIndex(
        (doc) => doc.usersUseridId === userId
    );
    if (index !== -1) {
      userDocs.value.splice(index, 1);
    }
  });

});

const newDocdata = ref({
  usersUserid: {
    id: Cookies.get("userId"),
  },
});

console.log(newDocdata.value.usersUserid.id);

let file = ref(null);

const handleFileUpload = (event) => {
  file.value = event.target.files[0];
};

const deleteFile = () => {
  file.value = null;
};

const CreateDocApi = async () => {
  if (!newDocdata.value.title) {
    alert("กรุณากรอกชื่อเรื่องของเอกสาร");
    return false;
  }

  if (!newDocdata.value.fromSource) {
    alert("กรุณากรอกชื่อผู้ส่ง");
    return false;
  }

  if (!newDocdata.value.emailSource) {
    alert("กรุณากรอกอีเมล");
    return false;
  }

  const emailPattern = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
  if (!emailPattern.test(newDocdata.value.emailSource)) {
    alert("กรุณากรอกอีเมลที่ถูกต้อง");
    return false;
  }

  if (!newDocdata.value.branchSource) {
    alert("กรุณาเลือกแผนกของผู้ส่ง");
    return false;
  }

  if (selectedSentUser.value.length === 0) {
    alert("กรุณาเลือกผู้รับ");
    return false;
  }

  if (!newDocdata.value.category) {
    alert("กรุณาเลือกหมวดหมู่เอกสาร");
    return false;
  }

  if (!newDocdata.value.urgency) {
    alert("กรุณาเลือกความเร่งด่วนของเอกสาร");
    return false;
  }

  if (!newDocdata.value.secrecyLevel) {
    alert("กรุณาเลือกชั้นความลับของเอกสาร");
    return false;
  }

  if (!file.value) {
    alert("กรุณาเลือกไฟล์เอกสาร");
    return false;
  }

  const formData = new FormData();
  formData.append("file", file.value);
  formData.append("data", JSON.stringify(newDocdata.value));
  console.log(newDocdata.value);
  try {
    const response = await axios.post(
        // "http://cp23kw2.sit.kmutt.ac.th:10003/api/doc/",
        mainURL + "/api/doc/",
        formData,
        {
          headers: {
            "Content-Type": "multipart/form-data",
            Authorization: `Bearer ${Cookies.get("accessToken")}`,
          },
        }
    );

    if (
        response.status === 202 ||
        response.status === 201 ||
        response.status === 200 ||
        response.status === 204
    ) {

      showAlert.value = true;
      setTimeout(() => {
        showAlert.value = false;
      }, 3000);

      // add new docid from response to every userDocs
      userDocs.value.forEach((userDoc) => {
        userDoc.documentsDocumentid1Id = response.data;
        userDoc.isShow = 1;
      });

      await axios.post(
          mainURL + "/api/userdoc/",
          userDocs.value,
          {
            headers: {
              Authorization: `Bearer ${Cookies.get("accessToken")}`,
            },
          }
      )
          .then(function (response) {
            alert('เพิ่มเอกสารสำเร็จ');
            router.push("/kw2/document/list");
          })

          .catch(function (AxiosError) {
            if (AxiosError.response) {
              switch (AxiosError.response.status) {
                case 400:
                  alert('คำขอไม่ถูกต้อง');
                  break;
                case 401:
                  alert('ไม่ได้รับอนุญาต');
                  break;
                case 403:
                  alert('ถูกปฏิเสธ');
                  break;
                case 404:
                  alert('ไม่พบข้อมูล');
                  break;
                case 500:
                  alert('เซิร์ฟเวอร์เกิดข้อผิดพลาด');
                  break;
                default:
                  alert('เกิดข้อผิดพลาด');
              }
            } else if (AxiosError.request) {
              alert('ไม่ได้รับการตอบสนองจากเซิร์ฟเวอร์');
            } else {
              alert('เกิดข้อผิดพลาด: ' + AxiosError.message);
            }
          });

      // return router.push("/list");
    } else {
      alert(`Error: ${response.status}`);
    }
  } catch (error) {
    console.error("Error creating document:", error);
  }
};

onMounted(() => {
  getDocID();
  getAllUsers();
  // newDocdata.value.usersUserid.id = Cookies.get("userId");

  // const isRefresh = sessionStorage.getItem("isRefresh");
  // const isRefresh2 = sessionStorage.getItem("isRefresh2");

  // if (isRefresh === "1" && isRefresh2 === "1") {
  //   sessionStorage.setItem("isRefresh", "2");
  //   location.reload();
  // } else if (isRefresh === "1") {
  //   sessionStorage.setItem("isRefresh", "2");
  // }
});

onUnmounted(() => {
  // sessionStorage.setItem("isRefresh", "1");
  // sessionStorage.setItem("isRefresh2", "1");
});
</script>

<template>
  <div class="shadow-md sm:rounded-lg w-full">
    <section class="bg-gray-200 dark:bg-gray-900">
      <div class="py-8 px-auto ml-5 mr-auto lg:py-12 w-full max-width: 100% justify-center">
        <h2 class="mb-4 text-4xl font-bold text-gray-900 dark:text-white">
          สร้างเอกสารใหม่
          <button class="m-1 mb-4 pb-4" data-popover-placement="bottom-end" data-popover-target="popover-description"
                  type="button">
            <svg aria-hidden="true" class="w-4 h-4 ms-2 text-gray-400 hover:text-gray-500" fill="currentColor"
                 viewBox="0 0 20 20" xmlns="http://www.w3.org/2000/svg">
              <path clip-rule="evenodd"
                    d="M18 10a8 8 0 11-16 0 8 8 0 0116 0zm-8-3a1 1 0 00-.867.5 1 1 0 11-1.731-1A3 3 0 0113 8a3.001 3.001 0 01-2 2.83V11a1 1 0 11-2 0v-1a1 1 0 011-1 1 1 0 100-2zm0 8a1 1 0 100-2 1 1 0 000 2z"
                    fill-rule="evenodd"></path>
            </svg>
            <span class="sr-only">Show information</span>
          </button>
          <div id="popover-description" class="absolute z-10 invisible inline-block text-sm text-gray-500 transition-opacity duration-300 bg-white border border-gray-200 rounded-lg shadow-sm opacity-0 w-72 dark:bg-gray-800 dark:border-gray-600 dark:text-gray-400" data-popover
               role="tooltip">
            <div class="p-3 space-y-2">
              <h3 class="font-semibold text-gray-900 dark:text-white">
                การสร้างเอกสาร
              </h3>
              <p>
                ท่านสามารถสร้างเอกสารที่ต้องการโดยกรอกรายละเอียดเอกสารต่าง ๆ
                ลงในแบบฟอร์ม แนบไฟล์เอกสาร และกดยืนยันเพื่อทำการส่ง
              </p>
              <h3 class="font-semibold text-gray-900 dark:text-white">
                การเลือกผู้ส่ง
              </h3>
              <p>
                ท่านสามารถเลือกผู้คนที่ต้องการจะส่งถึงได้
                หากเลือกผิดสามารถทำการลบหรือเพิ่มในภายหลังได้ที่หน้าแรก
              </p>
              <!-- <a href="#" class="flex items-center font-medium text-blue-600 dark:text-blue-500 dark:hover:text-blue-600 hover:text-blue-700 hover:underline">Read more <svg class="w-2 h-2 ms-1.5 rtl:rotate-180" aria-hidden="true" xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 6 10">
          <path stroke="currentColor" stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="m1 9 4-4-4-4"/>
        </svg></a> -->
            </div>
            <div data-popper-arrow></div>
          </div>
        </h2>
        <button class="px-4 py-2 text-sm font-bold text-white bg-blue-600 rounded-lg hover:bg-blue-700 dark:bg-blue-500 dark:hover:bg-blue-600"
                @click="addDatatoNewDoc">
          ทดสอบ
        </button>

        <!-- shadow-sm bg-gray-200 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-600 focus:border-blue-600 block w-full p-2.5 dark:bg-gray-600 dark:border-gray-500 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500 -->

        <form action="#">
          <div class="grid gap-4 grid-rows-8 sm:grid-cols-2 sm:gap-6">
            <!-- <div class="sm:col-span-2">
                <label for="name" class="block mb-2 text-sm font-medium text-gray-900 dark:text-white">Product Name</label>
                <input type="text" name="name" id="name" class="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-primary-600 focus:border-primary-600 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-primary-500 dark:focus:border-primary-500" placeholder="Type product name" required="">
            </div> -->
            <!-- **** เลขเอกสาร **** -->
            <div class="w-full">
              <label class="block mb-2 text-sm font-bold text-gray-900 dark:text-white">เลขที่ :</label>
              <input
                  :placeholder="newdocid"
                  class="bg-gray-100 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-primary-600 focus:border-primary-600 block w- p-1 dark:bg-gray-700" disabled/>
            </div>
            <br/>
            <!-- **** ชื่อเรื่อง **** -->
            <div class="w-full">
              <label class="block mb-2 text-sm font-bold text-gray-900 dark:text-white">เรื่อง :</label>
              <input v-model="newDocdata.title"
                     class="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-primary-600 focus:border-primary-600 block w-5/6 p-2.5"
                     placeholder="กรุณากรอกชื่อเรื่องของเอกสาร" required="" type="text"/>
            </div>
            <!-- **** กรอกส่งจากใคร **** -->
            <div class="w-full">
              <label class="block mb-2 text-sm font-bold text-gray-900 dark:text-white">จาก</label>
              <input id="brand" v-model="newDocdata.fromSource"
                     class="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-primary-600 focus:border-primary-600 block w-5/6 p-2.5"
                     placeholder="กรุณากรอกชื่อผู้ส่ง" required="" type="text"/>
            </div>
            <!-- **** กรอกรายละเอียด **** -->
            <div class="w-full">
              <label class="block mb-2 text-sm font-bold text-gray-900 dark:text-white">รายละเอียด</label>
              <input
                  v-model="newDocdata.description"
                  class="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-primary-600 focus:border-primary-600 block w-5/6 p-2.5" placeholder="กรอกรายละเอียดภายในเอกสารเบื้องต้น"/>
            </div>
            <!-- **** กรอกแผนก **** -->
            <div>
              <label class="block mb-2 text-sm font-bold text-gray-900 dark:text-white" for="category">แผนก</label>
              <select id="category" v-model="newDocdata.branchSource"
                      class="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-primary-500 focus:border-primary-500 block w-5/6 p-2.5 dark:bg-gray-700">
                <option selected="">เลือกแผนกของผู้ส่ง</option>
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
            <!-- **** โทรศัพท์ **** -->
            <div class="w-full">
              <label class="block mb-2 text-sm font-bold text-gray-900 dark:text-white">โทรศัพท์</label>
              <input v-model="newDocdata.phoneSource"
                     class="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-primary-600 focus:border-primary-600 block w-5/6 p-2.5"
                     placeholder="08X-XXX-XXXX" type="number"/>
            </div>
            <!-- **** อีเมล **** -->
            <div class="w-full">
              <label class="block mb-2 text-sm font-bold text-gray-900 dark:text-white">อีเมล</label>
              <input v-model="newDocdata.emailSource"
                     class="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-primary-600 focus:border-primary-600 block w-5/6 p-2.5"
                     placeholder="Example@xmail.com" type="email"/>
            </div>
            <!-- หมวดหมู่ -->
            <div>
              <label class="block mb-2 text-sm font-bold text-gray-900 dark:text-white" for="category">หมวดหมู่</label>
              <select id="category" v-model="newDocdata.category"
                      class="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-primary-500 focus:border-primary-500 block w-5/6 p-2.5 dark:bg-gray-700">
                <option selected="">เลือกหมวดหมู่เอกสาร</option>
                <option value="เอกสารภายนอก">เอกสารภายนอก</option>
                <option value="เอกสารภายใน">เอกสารภายใน</option>
              </select>
            </div>

            <!-- **** ความเร่งด่วน **** -->
            <div>
              <label class="block mb-2 text-sm font-bold text-gray-900 dark:text-white"
                     for="category">ความเร่งด่วน</label>
              <select id="category" v-model="newDocdata.urgency"
                      class="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-primary-500 focus:border-primary-500 block w-5/6 p-2.5 dark:bg-gray-700">
                <option selected="">เลือกความเร่งด่วนของเอกสาร</option>
                <option value="ปกติ">ปกติ</option>
                <option value="ด่วน">ด่วน</option>
                <option value="ด่วนที่สุด">ด่วนที่สุด</option>
              </select>
            </div>

            <!-- **** ชั้นความลับ **** -->
            <div>
              <label class="block mb-2 text-sm font-bold text-gray-900 dark:text-white"
                     for="category">ชั้นความลับ</label>
              <select id="category" v-model="newDocdata.secrecyLevel"
                      class="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-primary-500 focus:border-primary-500 block w-5/6 p-2.5 dark:bg-gray-700">
                <option selected="">เลือกชั้นความลับของเอกสาร</option>
                <option value="ปกติ">ปกติ</option>
                <option value="ปกปิด">ปกปิด</option>
                <option value="ลับ">ลับ</option>
                <option value="ลับมาก">ลับมาก</option>
                <option value="ลับที่สุด">ลับที่สุด</option>
              </select>
            </div>
            <br/>
            <!-- <div>
                <label for="item-weight" class="block mb-2 text-sm font-bold text-gray-900 dark:text-white">Item Weight (kg)</label>
                <input type="number" name="item-weight" id="item-weight" class="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-primary-600 focus:border-primary-600 block w-5/6 p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-primary-500 dark:focus:border-primary-500" placeholder="12" required="">
            </div>  -->

            <!-- เลือกผู้ส่ง -->
            <div class="w-full">
              <label class="block mb-2 text-sm font-bold text-gray-900 dark:text-white"
                     for="category">เลือกผู้ส่ง</label>
              <ul
                  class="w-5/6 text-sm font-medium text-gray-900 bg-white border border-gray-200 rounded-lg dark:bg-gray-700 dark:border-gray-600 dark:text-white">

                <li v-for="user in listOfUsers"
                    class="w-full hover:bg-gray-50 border-b border-gray-200 rounded-t-lg dark:border-gray-600">
                  <div class="flex items-center ps-3">
                    <input id="vue-checkbox" v-model="selectedSentUser" :value="user.id" class="w-4 h-4 text-blue-600 bg-gray-100 border-gray-300 rounded focus:ring-blue-500 dark:focus:ring-blue-600 dark:ring-offset-gray-700 dark:focus:ring-offset-gray-700 focus:ring-2 dark:bg-gray-600 dark:border-gray-500"
                           type="checkbox"/>
                    <label class="w-full py-3 ms-2 text-sm font-bold text-gray-900 dark:text-gray-300"
                           for="vue-checkbox">{{
                        user.fullName
                      }}</label>
                    <!-- <label for="angular-checkbox"
                      class="w-full py-3 ms-2 text-sm font-bold text-orange-400 dark:text-gray-300">งานเอกสาร</label> -->
                  </div>
                </li>

              </ul>
            </div>
            <!-- ผู้ลงนาม -->
            <!-- <div class="w-full">
              <label for="category"
                     class="block mb-2 text-sm font-bold text-gray-900 dark:text-white">เลือกผู้ลงนาม</label>
              <ul class="w-5/6 text-sm font-medium text-gray-900 bg-white border border-gray-200 rounded-lg dark:bg-gray-700 dark:border-gray-600 dark:text-white">
                <li class="w-full hover:bg-gray-50 border-b border-gray-200 rounded-t-lg dark:border-gray-600">
                  <div class="flex items-center ps-3">
                    <input id="vue-checkbox" type="checkbox" value=""
                           class="w-4 h-4 text-blue-600 bg-gray-100 border-gray-300 rounded focus:ring-blue-500 dark:focus:ring-blue-600 dark:ring-offset-gray-700 dark:focus:ring-offset-gray-700 focus:ring-2 dark:bg-gray-600 dark:border-gray-500">
                    <label for="vue-checkbox"
                           class="w-full py-3 ms-2 text-sm font-bold text-gray-900 dark:text-gray-300">นภา
                      ฟ้าสวย</label>
                    <label for="angular-checkbox"
                           class="w-full py-3 ms-2 text-sm font-bold text-orange-400 dark:text-gray-300">งานเอกสาร</label>
                  </div>
                </li>
                <li class="w-full hover:bg-gray-50 border-b border-gray-200 rounded-t-lg dark:border-gray-600">
                  <div class="flex items-center ps-3">
                    <input id="react-checkbox" type="checkbox" value=""
                           class="w-4 h-4 text-blue-600 bg-gray-100 border-gray-300 rounded focus:ring-blue-500 dark:focus:ring-blue-600 dark:ring-offset-gray-700 dark:focus:ring-offset-gray-700 focus:ring-2 dark:bg-gray-600 dark:border-gray-500">
                    <label for="react-checkbox"
                           class="w-full py-3 ms-2 text-sm font-bold text-gray-900 dark:text-gray-300">ใบฝ้าย
                      นุ่มมิ่ม</label>
                    <label for="angular-checkbox"
                           class="w-full py-3 ms-2 text-sm font-bold text-orange-400 dark:text-gray-300">ฝ่ายขาย</label>
                  </div>
                </li>
                <li class="w-full hover:bg-gray-50 border-b border-gray-200 rounded-t-lg dark:border-gray-600">
                  <div class="flex items-center ps-3">
                    <input id="angular-checkbox" type="checkbox" value=""
                           class="w-4 h-4 text-blue-600 bg-gray-100 border-gray-300 rounded focus:ring-blue-500 dark:focus:ring-blue-600 dark:ring-offset-gray-700 dark:focus:ring-offset-gray-700 focus:ring-2 dark:bg-gray-600 dark:border-gray-500">
                    <label for="angular-checkbox"
                           class="w-full py-3 ms-2 text-sm font-bold text-gray-900 dark:text-gray-300">กันยา
                      นาปี</label>
                    <label for="angular-checkbox"
                           class="w-full py-3 ms-2 text-sm font-bold text-orange-400 dark:text-gray-300">ฝ่ายผลิต</label>
                  </div>
                </li>
              </ul>
            </div> -->
            <br/>
            <!-- กล่องวางไฟล์ -->
            <div class="flex items-center w-full">
              <label class="flex flex-col items-center justify-center w-5/6 h-64 border-2 border-gray-300 border-dashed rounded-lg cursor-pointer bg-gray-50 dark:hover:bg-bray-800 dark:bg-gray-700 hover:bg-gray-100 dark:border-gray-600 dark:hover:border-gray-500 dark:hover:bg-gray-600"
                     for="dropzone-file">
                <div class="flex flex-col items-center justify-center pt-5 pb-6">
                  <svg aria-hidden="true" class="w-8 h-8 mb-4 text-gray-500 dark:text-gray-400"
                       fill="none" viewBox="0 0 20 16" xmlns="http://www.w3.org/2000/svg">
                    <path d="M13 13h3a3 3 0 0 0 0-6h-.025A5.56 5.56 0 0 0 16 6.5 5.5 5.5 0 0 0 5.207 5.021C5.137 5.017 5.071 5 5 5a4 4 0 0 0 0 8h2.167M10 15V6m0 0L8 8m2-2 2 2" stroke="currentColor" stroke-linecap="round" stroke-linejoin="round"
                          stroke-width="2"/>
                  </svg>
                  <p class="mb-2 text-sm text-gray-500 dark:text-gray-400">
                    <span class="font-semibold">คลิ๊กเพื่ออัพโหลดเอกสาร</span>
                    หรือลากไฟล์และวาง
                  </p>
                  <p class="text-xs text-gray-500 dark:text-gray-400">
                    SVG, PNG, JPG, or Docx (MAX 10 MB)
                  </p>
                </div>
                <input id="dropzone-file" class="hidden" type="file" @change="handleFileUpload"/>
              </label>
            </div>

            <!-- ที่แสดงไฟล์ -->
            <div class="w-full">
              <div class="rounded-lg shadow-md w-5/6">
                <table class="rounded-lg w-full text-sm text-left rtl:text-right text-gray-500 dark:text-gray-400">
                  <thead class="text-xs text-gray-700 uppercase bg-gray-50 dark:bg-gray-700 dark:text-gray-400">
                  <tr>
                    <th class="px-6 py-3" scope="col">ชื่อไฟล์</th>

                    <th class="px-6 py-3" scope="col">ขนาด</th>
                    <th class="py-3" scope="col"></th>
                  </tr>
                  </thead>
                  <tbody>
                  <tr v-if="file"
                      class="bg-white border-b dark:bg-gray-800 dark:border-gray-700 hover:bg-gray-50 dark:hover:bg-gray-600">
                    <td class="px-6 py-4 font-semibold text-gray-900 dark:text-white">
                      {{ file ? file.name : "" }}
                    </td>
                    <td class="px-6 py-4 font-semibold text-gray-900 dark:text-white">
                      {{ file ? (file.size / 1000000).toFixed(2) : "" }} MB
                    </td>
                    <td class="py-4">
                      <a class="font-medium text-red-600 dark:text-red-500 hover:underline" href="#"
                         @click.prevent="deleteFile">ลบ</a>
                    </td>
                  </tr>

                  <tr v-else
                      class="bg-white border-b dark:bg-gray-800 dark:border-gray-700 hover:bg-gray-50 dark:hover:bg-gray-600">
                    <td class="px-6 py-4 font-semibold text-gray-900 dark:text-white">
                      ไม่มีไฟล์
                    </td>
                    <td class="px-6 py-4 font-semibold text-gray-900 dark:text-white">
                      -
                    </td>
                    <td class="py-4">
                      <a class="font-medium text-red-600 dark:text-red-500 hover:underline" href="#"></a>
                    </td>
                  </tr>
                  </tbody>
                </table>
              </div>
            </div>

            <!-- **** หมายเหตุ **** -->
            <!-- <div class="w-full">
              <label for="desc" class="block mb-2 text-sm font-bold text-gray-900 dark:text-white">หมายเหตุ</label>
              <textarea
                  class="block p-2.5 w-5/6 text-sm text-gray-900 bg-gray-50 rounded-lg border border-gray-300 focus:ring-primary-500 focus:border-primary-500 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-primary-500 dark:focus:border-primary-500"
                  placeholder="กรอกหมายเหตุของคุณ"></textarea>
            </div> -->
          </div>
          <br/>
          <!-- **** ปุ่มยืนยัน **** -->
          <!-- <button type="submit" @click="CreateDocApi"
            class="flex-col justify-center hover:bg-green-800 items-center px-16 py-4 mt-4 sm:mt-6 text-sm font-bold text-white text-center bg-green-600 rounded-lg focus:ring-4 focus:ring-primary-200">
            ยืนยัน
          </button> -->
          <a class="cursor-pointer flex-col justify-center hover:bg-green-800 items-center px-16 py-4 mt-4 sm:mt-6 text-sm font-bold text-white text-center bg-green-600 rounded-lg focus:ring-4 focus:ring-primary-200" type="submit"
             @click="CreateDocApi">
            ยืนยัน
          </a>
        </form>
      </div>
    </section>
  </div>
</template>

<style></style>
