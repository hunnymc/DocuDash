<script setup>
import { ref , watch , computed , onMounted , watchEffect , onUnmounted } from "vue";
import { useRoute } from 'vue-router';
import axios from "axios";
import router from "../router";

const route = useRoute();

let editdocdata = ref({});

function reciveData() {
  editdocdata.value = JSON.parse(route.query.document);
  newDocdata.value.id = editdocdata.value.documentsDocumentid1.id;
  newDocdata.value.title = editdocdata.value.documentsDocumentid1.title;
  newDocdata.value.category = editdocdata.value.documentsDocumentid1.category;
  newDocdata.value.secrecyLevel = editdocdata.value.documentsDocumentid1.secrecyLevel;
  newDocdata.value.branchSource = editdocdata.value.documentsDocumentid1.branchSource;
  newDocdata.value.phoneSource = editdocdata.value.documentsDocumentid1.phoneSource;
  newDocdata.value.emailSource = editdocdata.value.documentsDocumentid1.emailSource;
  newDocdata.value.urgency = editdocdata.value.documentsDocumentid1.urgency;
  newDocdata.value.fromSource = editdocdata.value.documentsDocumentid1.fromSource;
  newDocdata.value.status = editdocdata.value.documentsDocumentid1.status;
  newDocdata.value.filePath = editdocdata.value.documentsDocumentid1.filePath;
  newDocdata.value.description = editdocdata.value.documentsDocumentid1.description;
  newDocdata.value.usersUserid = editdocdata.value.documentsDocumentid1.usersUserid;
}

let newdocid = ref(0);

const ClickFile = () => {
    // const fileUrl = "http://localhost:5001/api/files/" + doc.value.filePath;
    const fileUrl = "http://cp23kw2.sit.kmutt.ac.th:10003/api/files/" + newDocdata.value.filePath;
    window.open(fileUrl, '_blank');
};

const selectedSentUser = ref([]);
const userDocs = ref({});

watch(selectedSentUser, (newVal, oldVal) => {
    const addedUsers = newVal.filter(x => !oldVal.includes(x));
    const removedUsers = oldVal.filter(x => !newVal.includes(x));

    addedUsers.forEach(userId => {
        userDocs.value.push({
            documentsDocumentid1Id: newdocid.value, // Replace with your actual document ID
            usersUseridId: userId,
        });
    });

    removedUsers.forEach(userId => {
        const index = userDocs.value.findIndex(doc => doc.usersUseridId === userId);
        if (index !== -1) {
            userDocs.value.splice(index, 1);
        }
    });
});

const newDocdata = ref({
  title: "",
  category: "เลือกหมวดหมู่เอกสาร",
  secrecyLevel: "เลือกชั้นความลับของเอกสาร",
  branchSource: "เลือกแผนกของผู้ส่ง",
  filePath: "",
  phoneSource: "",
  emailSource: "",
  urgency: "เลือกความเร่งด่วนของเอกสาร",
  fromSource: "",
  status: "",
  description: "",
  usersUserid: {
    id: 1,
  },
});

const validateInput = () => {
  if (newDocdata.value.title === "") {
    alert("กรุณากรอกชื่อเรื่อง");
    return false;
  } else if (newDocdata.value.category === "เลือกหมวดหมู่เอกสาร") {
    alert("กรุณาเลือกหมวดหมู่เอกสาร");
    return false;
  } else if (newDocdata.value.secrecyLevel === "เลือกชั้นความลับของเอกสาร") {
    alert("กรุณาเลือกชั้นความลับของเอกสาร");
    return false;
  } else if (newDocdata.value.branchSource === "เลือกแผนกของผู้ส่ง") {
    alert("กรุณาเลือกแผนกของผู้ส่ง");
    return false;
  } else if (newDocdata.value.urgency === "เลือกความเร่งด่วนของเอกสาร") {
    alert("กรุณาเลือกความเร่งด่วนของเอกสาร");
    return false;
  } else if (newDocdata.value.fromSource === "") {
    alert("กรุณากรอกชื่อผู้ส่ง");
    return false;
  } else if (newDocdata.value.description === "") {
    alert("กรุณากรอกรายละเอียด");
    return false;
  } else if (newDocdata.value.usersUserid.id === 0) {
    alert("กรุณาเลือกผู้ส่ง");
    return false;
  } else {
    return true;
  }
};

let file = ref(null);

const handleFileUpload = (event) => {
  file.value = event.target.files[0];
};

const CreateDocApi = async () => {

  validateInput();
  
  const formData = new FormData();
  formData.append("file", file.value);
  formData.append("data", JSON.stringify(newDocdata.value));
  // const response = await axios.post("http://localhost:5001/api/doc/newdoc", formData);
  await axios.patch("http://cp23kw2.sit.kmutt.ac.th:10003/api/doc/" + newDocdata.value.id, formData,
    {
      headers: {
        "Content-Type": "multipart/form-data",
      },
    }
  );
  return router.push("/list");;
};

onMounted(() => {
  reciveData();

  const isRefresh = sessionStorage.getItem('isRefresh');
  const isRefresh2 = sessionStorage.getItem('isRefresh2');

  if (isRefresh === '1' && isRefresh2 === '1') {
    sessionStorage.setItem('isRefresh', '2');
    location.reload();
  } else if (isRefresh === '1') {
    sessionStorage.setItem('isRefresh', '2');
  }
});

onUnmounted(() => {
  sessionStorage.setItem('isRefresh', '1');
  sessionStorage.setItem('isRefresh2', '1');
});

</script>

<template>
<div class="shadow-md sm:rounded-lg w-full">
    <section class="bg-gray-200 dark:bg-gray-900">
      <div class="py-8 px-auto ml-5 mr-auto lg:py-12 w-full max-width: 100% justify-center">
        <h2 class="mb-4 text-4xl font-bold text-gray-900 dark:text-white">
          แก้ไขเอกสาร
          <button class="m-1 mb-4 pb-4" data-popover-target="popover-description" data-popover-placement="bottom-end"
            type="button">
            <svg class="w-4 h-4 ms-2 text-gray-400 hover:text-gray-500" aria-hidden="true" fill="currentColor"
              viewBox="0 0 20 20" xmlns="http://www.w3.org/2000/svg">
              <path fill-rule="evenodd"
                d="M18 10a8 8 0 11-16 0 8 8 0 0116 0zm-8-3a1 1 0 00-.867.5 1 1 0 11-1.731-1A3 3 0 0113 8a3.001 3.001 0 01-2 2.83V11a1 1 0 11-2 0v-1a1 1 0 011-1 1 1 0 100-2zm0 8a1 1 0 100-2 1 1 0 000 2z"
                clip-rule="evenodd"></path>
            </svg>
            <span class="sr-only">Show information</span>
          </button>
          <div data-popover id="popover-description" role="tooltip"
            class="absolute z-10 invisible inline-block text-sm text-gray-500 transition-opacity duration-300 bg-white border border-gray-200 rounded-lg shadow-sm opacity-0 w-72 dark:bg-gray-800 dark:border-gray-600 dark:text-gray-400">
            <div class="p-3 space-y-2">
              <h3 class="font-semibold text-gray-900 dark:text-white">
                การแก้ไขข้อมูล
              </h3>
              <p>
                ท่านสามารถแก้ไขข้อมูลเอกสารได้ ยกเว้นการแก้ไขชั้นความลับ และความเร่งด่วนของเอกสาร
              </p>
              <h3 class="font-semibold text-gray-900 dark:text-white">
                การเพิ่มไฟล์
              </h3>
              <p>
                ท่านสามารถเพิ่มไฟล์เอกสารที่ต้องการได้ แต่ไม่สามารถลบไฟล์ที่อัพโหลดไปตอนแรกได้
              </p>
              <!-- <a href="#" class="flex items-center font-medium text-blue-600 dark:text-blue-500 dark:hover:text-blue-600 hover:text-blue-700 hover:underline">Read more <svg class="w-2 h-2 ms-1.5 rtl:rotate-180" aria-hidden="true" xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 6 10">
          <path stroke="currentColor" stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="m1 9 4-4-4-4"/>
        </svg></a> -->
            </div>
            <div data-popper-arrow></div>
          </div>
        </h2>


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
                class="cursor-not-allowed bg-zinc-100 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-primary-600 focus:border-primary-600 block w- p-1 dark:bg-gray-700"
                disabled
                :placeholder="newDocdata.id" />
            </div>
            <br />
            <!-- **** ชื่อเรื่อง **** -->
            <div class="w-full">
              <label class="block mb-2 text-sm font-bold text-gray-900 dark:text-white">เรื่อง :</label>
              <input type="text"
                class="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-primary-600 focus:border-primary-600 block w-5/6 p-2.5"
                placeholder="newDocdata.title" v-model="newDocdata.title"  required="" />
            </div>
            <!-- **** กรอกส่งจากใคร **** -->
            <div class="w-full">
              <label class="block mb-2 text-sm font-bold text-gray-900 dark:text-white">จาก</label>
              <input type="text" id="brand"
                class="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-primary-600 focus:border-primary-600 block w-5/6 p-2.5"
                placeholder="newDocdata.fromSource" v-model="newDocdata.fromSource" required="" />
            </div>
            <!-- **** กรอกรายละเอียด **** -->
            <div class="w-full">
              <label class="block mb-2 text-sm font-bold text-gray-900 dark:text-white">รายละเอียด</label>
              <input
                class="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-primary-600 focus:border-primary-600 block w-5/6 p-2.5"
                placeholder="กรอกรายละเอียดภายในเอกสารเบื้องต้น" v-model="newDocdata.description" />
            </div>
            <!-- **** กรอกแผนก **** -->
            <div>
              <label for="category" class="block mb-2 text-sm font-bold text-gray-900 dark:text-white">แผนก</label>
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
              <input type="number"
                class="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-primary-600 focus:border-primary-600 block w-5/6 p-2.5"
                placeholder="08X-XXX-XXXX" v-model="newDocdata.phoneSource" />
            </div>
            <!-- **** อีเมล **** -->
            <div class="w-full">
              <label class="block mb-2 text-sm font-bold text-gray-900 dark:text-white">อีเมล</label>
              <input type="email"
                class="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-primary-600 focus:border-primary-600 block w-5/6 p-2.5"
                placeholder="Example@xmail.com" v-model="newDocdata.emailSource" />
            </div>
            <!-- หมวดหมู่ -->
            <div>
              <label for="category" class="block mb-2 text-sm font-bold text-gray-900 dark:text-white">หมวดหมู่</label>
              <select id="category" v-model="newDocdata.category"
                class="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-primary-500 focus:border-primary-500 block w-5/6 p-2.5 dark:bg-gray-700">
                <option selected="">เลือกหมวดหมู่เอกสาร</option>
                <option value="เอกสารภายนอก">เอกสารภายนอก</option>
                <option value="เอกสารภายใน">เอกสารภายใน</option>
              </select>
            </div>
            
            <!-- **** ความเร่งด่วน **** -->
            <div>
              <label for="category"
                class=" block mb-2 text-sm font-bold text-gray-900 dark:text-white">ความเร่งด่วน</label>
              <input type="text" disabled v-model="newDocdata.urgency" class="bg-zinc-100 cursor-not-allowed  border border-gray-300 text-gray-900 text-sm rounded-lg  block w-5/6 p-2.5 dark:bg-gray-700">
            </div>
            
            <!-- **** ชั้นความลับ **** -->
            <div>
              <label for="category" class="block mb-2 text-sm font-bold text-gray-900 dark:text-white">ชั้นความลับ</label>
              <input type="text" disabled v-model="newDocdata.secrecyLevel"
                class=" bg-zinc-100 cursor-not-allowed border border-gray-300 text-gray-900 text-sm rounded-lg  block w-5/6 p-2.5 ">
            </div>
            <br />
            <!-- <div>
                <label for="item-weight" class="block mb-2 text-sm font-bold text-gray-900 dark:text-white">Item Weight (kg)</label>
                <input type="number" name="item-weight" id="item-weight" class="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-primary-600 focus:border-primary-600 block w-5/6 p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-primary-500 dark:focus:border-primary-500" placeholder="12" required="">
            </div>  -->

            <!-- เลือกผู้ส่ง -->
            <!-- <div class="w-full">
              <label for="category" class="block mb-2 text-sm font-bold text-gray-900 dark:text-white">เลือกผู้ส่ง</label>
              <ul
                class="w-5/6 text-sm font-medium text-gray-900 bg-white border border-gray-200 rounded-lg dark:bg-gray-700 dark:border-gray-600 dark:text-white">
                <li class="w-full hover:bg-gray-50 border-b border-gray-200 rounded-t-lg dark:border-gray-600">
                  <div class="flex items-center ps-3">
                    <input id="vue-checkbox" type="checkbox" value="1" v-model="selectedSentUser"
                      class="w-4 h-4 text-blue-600 bg-gray-100 border-gray-300 rounded focus:ring-blue-500 dark:focus:ring-blue-600 dark:ring-offset-gray-700 dark:focus:ring-offset-gray-700 focus:ring-2 dark:bg-gray-600 dark:border-gray-500" />
                    <label for="vue-checkbox"
                      class="w-full py-3 ms-2 text-sm font-bold text-gray-900 dark:text-gray-300">นภา ฟ้าสวย</label>
                    <label for="angular-checkbox"
                      class="w-full py-3 ms-2 text-sm font-bold text-orange-400 dark:text-gray-300">งานเอกสาร</label>
                  </div>
                </li>
                <li class="w-full hover:bg-gray-50 border-b border-gray-200 rounded-t-lg dark:border-gray-600">
                  <div class="flex items-center ps-3">
                    <input id="react-checkbox" type="checkbox" value="2" v-model="selectedSentUser"
                      class="w-4 h-4 text-blue-600 bg-gray-100 border-gray-300 rounded focus:ring-blue-500 dark:focus:ring-blue-600 dark:ring-offset-gray-700 dark:focus:ring-offset-gray-700 focus:ring-2 dark:bg-gray-600 dark:border-gray-500" />
                    <label for="react-checkbox"
                      class="w-full py-3 ms-2 text-sm font-bold text-gray-900 dark:text-gray-300">ใบฝ้าย นุ่มมิ่ม</label>
                    <label for="angular-checkbox"
                      class="w-full py-3 ms-2 text-sm font-bold text-orange-400 dark:text-gray-300">ฝ่ายขาย</label>
                  </div>
                </li>
                <li class="w-full hover:bg-gray-50 border-b border-gray-200 rounded-t-lg dark:border-gray-600">
                  <div class="flex items-center ps-3">
                    <input id="angular-checkbox" type="checkbox" value="3" v-model="selectedSentUser"
                      class="w-4 h-4 text-blue-600 bg-gray-100 border-gray-300 rounded focus:ring-blue-500 dark:focus:ring-blue-600 dark:ring-offset-gray-700 dark:focus:ring-offset-gray-700 focus:ring-2 dark:bg-gray-600 dark:border-gray-500" />
                    <label for="angular-checkbox"
                      class="w-full py-3 ms-2 text-sm font-bold text-gray-900 dark:text-gray-300">กันยา นาปี</label>
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
            <br />
            <!-- กล่องวางไฟล์ -->
            <div class="flex items-center w-full">
              <label for="dropzone-file"
                class="flex flex-col items-center justify-center w-5/6 h-64 border-2 border-gray-300 border-dashed rounded-lg cursor-pointer bg-gray-50 dark:hover:bg-bray-800 dark:bg-gray-700 hover:bg-gray-100 dark:border-gray-600 dark:hover:border-gray-500 dark:hover:bg-gray-600">
                <div class="flex flex-col items-center justify-center pt-5 pb-6">
                  <svg class="w-8 h-8 mb-4 text-gray-500 dark:text-gray-400" aria-hidden="true"
                    xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 20 16">
                    <path stroke="currentColor" stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                      d="M13 13h3a3 3 0 0 0 0-6h-.025A5.56 5.56 0 0 0 16 6.5 5.5 5.5 0 0 0 5.207 5.021C5.137 5.017 5.071 5 5 5a4 4 0 0 0 0 8h2.167M10 15V6m0 0L8 8m2-2 2 2" />
                  </svg>
                  <p class="mb-2 text-sm text-gray-500 dark:text-gray-400">
                    <span class="font-semibold">คลิ๊กเพื่ออัพโหลดเอกสาร</span>
                    หรือลากไฟล์และวาง
                  </p>
                  <p class="text-xs text-gray-500 dark:text-gray-400">
                    SVG, PNG, JPG, or Docx (MAX 10 MB)
                  </p>
                </div>
                <input id="dropzone-file" type="file" class="hidden" 
                  @change="handleFileUpload" />
              </label>
            </div>

            <!-- ที่แสดงไฟล์ -->
            <div class="w-full">
              <div class="  rounded-lg shadow-md w-5/6">
                <table class="rounded-lg w-full text-sm text-left rtl:text-right text-gray-500 dark:text-gray-400">
                  <thead class="text-xs text-gray-700 uppercase bg-gray-50 dark:bg-gray-700 dark:text-gray-400">
                  <tr>

                    <th scope="col" class="px-6 py-3">
                      ชื่อไฟล์
                    </th>
                  </tr>
                  </thead>
                  <tbody>
                  <tr v-if="newDocdata.filePath" @click="ClickFile" class="bg-white border-b dark:bg-gray-800 dark:border-gray-700 hover:bg-gray-50 dark:hover:bg-gray-600">
                    <td class="px-6 py-4 text-blue-600  underline cursor-pointer font-semibold ">
                      {{ newDocdata.filePath }}
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
                    <td class=" py-4">
                      <a href="#" class="font-medium text-red-600 dark:text-red-500 hover:underline"></a>
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
          <br />

          <!-- **** ปุ่มยืนยัน **** -->
          <!-- <button type="submit" @click="CreateDocApi"
            class="flex-col justify-center hover:bg-green-800 items-center px-16 py-4 mt-4 sm:mt-6 text-sm font-bold text-white text-center bg-green-600 rounded-lg focus:ring-4 focus:ring-primary-200">
            ยืนยัน
          </button> -->
          <a type="submit" @click="CreateDocApi"
            class="cursor-pointer flex-col justify-center hover:bg-green-800 items-center px-16 py-4 mt-4 sm:mt-6 text-sm font-bold text-white text-center bg-green-600 rounded-lg focus:ring-4 focus:ring-primary-200">
            ยืนยัน
          </a>
        </form>
      </div>
    </section>
  </div>









</template>
<style>
</style>