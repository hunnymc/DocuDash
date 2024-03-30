<script setup>
import {onMounted, ref, watch} from "vue";
import axios from "axios";
import router from "../../../router";
import Cookies from "js-cookie";

let mainURL= import.meta.env.VITE_API_URL;

// let mainURL = "http://localhost:5002";
// let mainURL = "https://cp23kw2.sit.kmutt.ac.th:10003";
// let mainURL = "https://capstone23.sit.kmutt.ac.th/kw2";

const showAlert = ref(false);
let showSentButton = ref(true)

let documentApproveTypeID = 1;

const listOfUsers = ref([]);
const listOfManagerUsers = ref([]);
const filteredManagerUsers = ref([
  {
    "id": 3,
    "fullName": "กันยา นาปี",
    "role": "MANAGER",
    "branch": "ฝ่ายผลิต"
  }, {
    "id": 5,
    "fullName": "user",
    "role": "MANAGER",
    "branch": "ฝ่ายบุคคล"
  },
  {
    "id": 4,
    "fullName": "กันยา นาปี",
    "role": "MANAGER",
    "branch": "ฝ่ายผลิต"
  }, {
    "id": 6,
    "fullName": "user",
    "role": "MANAGER",
    "branch": "ฝ่ายบุคคล"
  },
  {
    "id": 7,
    "fullName": "กันยา นาปี",
    "role": "MANAGER",
    "branch": "ฝ่ายผลิต"
  }, {
    "id": 8,
    "fullName": "user",
    "role": "MANAGER",
    "branch": "ฝ่ายบุคคล"
  },
]);
let searchTerm = ref('');

const getDocID = async () => {
  const response = await axios.get(
      mainURL + "/api/doc/newdocid"
      , {
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
  listOfManagerUsers.value = response.data.filter((user) => user.role === "MANAGER");

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
const selectedSentManager = ref([]);
const userDocs = ref([]);
const managerDocs = ref([]);

function addDatatoNewDoc() {
  newDocdata.value.title = "test title";
  // newDocdata.value.fromSource = "test source";
  // newDocdata.value.emailSource = "test@test.com";
  // newDocdata.value.branchSource = "ฝ่ายการตลาด";
  newDocdata.value.category = "เอกสารภายใน";
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

watch(selectedSentManager, (newVal, oldVal) => {

  const addedManagers = newVal.filter((x) => !oldVal.includes(x));
  const removedManagers = oldVal.filter((x) => !newVal.includes(x));

  addedManagers.forEach((managerId) => {
    managerDocs.value.push({
      documentID: newdocid.value, // Replace with your actual document ID
      managerID: managerId,
      documentApproveTypeID: documentApproveTypeID,
    });
  });

  removedManagers.forEach((managerId) => {
    const index = managerDocs.value.findIndex(
        (doc) => doc.usersUseridId === managerId
    );
    if (index !== -1) {
      managerDocs.value.splice(index, 1);
    }
  });

});

watch([searchTerm, listOfManagerUsers], () => {
  filteredManagerUsers.value = listOfManagerUsers.value.filter(user =>
      user.fullName.toLowerCase().includes(searchTerm.value.toLowerCase())
  );
});

const newDocdata = ref({
  usersUserid: {
    id: Cookies.get("userId"),
  },
});

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

  showSentButton.value = false;

  console.log(newDocdata.value);
  try {
    const response = await axios.post(
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
        userDoc.statusTypeID = documentApproveTypeID;
        userDoc.isShow = 0;
      });

      // add new docid from response to every managerDocs
      managerDocs.value.forEach((managerDoc) => {
        managerDoc.documentID = response.data;
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
          .then(async function (response) {
            await axios.post(
                mainURL + "/api/approve/add-manager",
                managerDocs.value,
                {
                  headers: {
                    Authorization: `Bearer ${Cookies.get("accessToken")}`,
                  },
                }
            )
                .then(function (response) {
                  showSentButton.value = true;
                  alert('ส่งคำขอสำเร็จ');
                  router.push("/kw2/approval/list");
                })
                .catch(function (AxiosError) {
                  showSentButton.value = true
                  alert('เกิดข้อผิดพลาด: ' + AxiosError.message);
                });

            // alert('เพิ่มเอกสารสำเร็จ');
            // router.push("/kw2/list");

          })

          .catch(function (AxiosError) {
            showSentButton.value = true
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
              showSentButton.value = true
              alert('ไม่ได้รับการตอบสนองจากเซิร์ฟเวอร์');
            } else {
              showSentButton.value = true
              alert('เกิดข้อผิดพลาด: ' + AxiosError.message);
            }
          });

    } else {
      alert(`Error: ${response.status}`);
    }
  } catch (error) {
    console.error("Error creating document:", error);
  }
};

const getUserInfo = async () => {
  await axios.post(
      mainURL + '/api/auth/user-info'
      , {email: Cookies.get("email")}
      , {headers: {"Authorization": "Bearer " + Cookies.get("accessToken"),}})
      .then((response) => {
        console.log(response.data);
        newDocdata.value.emailSource = response.data.email;
        newDocdata.value.branchSource = response.data.branch;
        newDocdata.value.phoneSource = response.data.phone;
        console.log(newDocdata.value);
        newDocdata.value.fromSource = localStorage.getItem("fullName");
      });
};

onMounted(() => {

  getDocID();
  getUserInfo();
  getAllUsers();
});

</script>

<template>
  <div class="shadow-md sm:rounded-lg w-full">
    <section class="bg-gray-200 dark:bg-gray-900">
      <div class="py-8 px-auto ml-5 mr-auto lg:py-12 w-full max-width: 100% justify-center">
        <h2 class="mb-4 text-4xl font-bold text-gray-900 dark:text-white">
          ขออนุมัติเอกสารและคำร้อง
        </h2>
        <button @click="addDatatoNewDoc"
                class="px-4 py-2 text-sm font-bold text-white bg-blue-600 rounded-lg hover:bg-blue-700 dark:bg-blue-500 dark:hover:bg-blue-600">
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
              <!-- <label class="block mb-2 text-sm font-bold text-gray-900 dark:text-white">เลขที่ :</label>
              <input
                class="bg-gray-100 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-primary-600 focus:border-primary-600 block w- p-1 dark:bg-gray-700"
                disabled :placeholder="newdocid" /> -->
            </div>
            <br/>

            <!-- **** ชื่อเรื่อง **** -->
            <div class="w-full">
              <label class="block mb-2 text-sm font-bold text-gray-900 dark:text-white">เรื่อง :</label>
              <input type="text"
                     class="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-primary-600 focus:border-primary-600 block w-5/6 p-2.5"
                     placeholder="กรุณากรอกชื่อเรื่องหรือโครงการ" v-model="newDocdata.title" required=""/>
            </div>

            <!-- **** กรอกส่งจากใคร **** -->
            <div class="w-full">
              <label class="block mb-2 text-sm font-bold text-gray-900 dark:text-white">ผู้ยื่นขออนุมัติ</label>
              <div
                  class="block w-5/6 p-2.5 bg-gray-50 border border-gray-300 text-sm text-gray-500 dark:text-gray-400 dark:bg-gray-800 rounded-lg ">
                <h3 class="text-sm font-bold text-gray-900 dark:text-white "> {{ newDocdata.fromSource }}</h3>

              </div>
            </div>

            <!-- **** กรอกรายละเอียด **** -->
            <div class="w-full">
              <label class="block mb-2 text-sm font-bold text-gray-900 dark:text-white">รายละเอียด</label>
              <input
                  class="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-primary-600 focus:border-primary-600 block w-5/6 p-2.5"
                  placeholder="กรอกรายละเอียดคำขอเบื้องต้น" v-model="newDocdata.description"/>
            </div>

            <!-- **** กรอกแผนก **** -->
            <div>
              <label for="category" class="block mb-2 text-sm font-bold text-gray-900 dark:text-white">แผนก</label>
              <div
                  class="block w-5/6 p-2.5 bg-gray-50 border border-gray-300 text-sm text-gray-500 dark:text-gray-400 dark:bg-gray-800 rounded-lg ">
                <h3 class="text-sm font-bold text-gray-900 dark:text-white ">{{ newDocdata.branchSource }}</h3>
              </div>
            </div>

            <!-- **** โทรศัพท์ **** -->
            <div class="w-full">
              <label class="block mb-2 text-sm font-bold text-gray-900 dark:text-white">โทรศัพท์</label>
              <input type="number"
                     class="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-primary-600 focus:border-primary-600 block w-5/6 p-2.5"
                     placeholder="08X-XXX-XXXX" v-model="newDocdata.phoneSource"/>
            </div>

            <!-- **** อีเมล **** -->
            <div class="w-full">
              <label class="block mb-2 text-sm font-bold text-gray-900 dark:text-white">อีเมล</label>
              <div
                  class="block w-5/6 p-2.5 bg-gray-50 border border-gray-300 text-sm text-gray-500 dark:text-gray-400 dark:bg-gray-800 rounded-lg ">
                <h3 class="text-sm font-bold text-gray-900 dark:text-white ">{{ newDocdata.emailSource }}</h3>

              </div>
            </div>

            <!-- ปรเภทการขออนุมัติ -->
            <div>
              <label for="category" class="block mb-2 text-sm font-bold text-gray-900 dark:text-white">ประเภทการขออนุมัติ</label>
              <select id="category" v-model="documentApproveTypeID"
                      class="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-primary-500 focus:border-primary-500 block w-5/6 p-2.5 dark:bg-gray-700">
                <option selected="">เลือกประเภทการขออนุมัติ</option>
                <option value="1">คำร้องขออนุมัติเอกสาร</option>
                <option value="2">ขออนุมัติเพื่อแจ้งให้ทราบ</option>
              </select>
            </div>
            <br>
            <!-- **** ผจก **** -->
            <div class="w-full">
              <label for="category" class="block mb-2 text-sm font-bold text-gray-900 dark:text-white">*
                เลือกผู้จัดการที่ต้องอนุมัติเอกสาร</label>
              <div class="w-full block w-5/6 z-10 block bg-white rounded-lg shadow  dark:bg-gray-700">
                <div class="p-3 ">
                  <label class="sr-only">Search</label>
                  <div class="">
                    <div class=" relative items-center ps-3 pointer-events-none">
                      <svg class="transform translate-y-3 top-1/2 absolute w-4 h-4 text-gray-600 " aria-hidden="true"
                           xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 20 20">
                        <path stroke="currentColor" stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                              d="m19 19-4-4m0-7A7 7 0 1 1 1 8a7 7 0 0 1 14 0Z"/>
                      </svg>
                    </div>
                    <input type="text" id="input-group-search"
                           v-model="searchTerm"
                           class="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full ps-10 p-2.5  dark:bg-gray-600 dark:border-gray-500 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500"
                           placeholder="ค้นหารายชื่อผู้ตรวจสอบขั้นตอนต่อไป">
                  </div>
                </div>
                <ul
                    v-for="manager in filteredManagerUsers"
                    class="h-12 px-3 pb-3 overflow-y-auto text-sm text-gray-700 dark:text-gray-200">
                  <li>
                    <div class="flex items-center p-2 rounded hover:bg-gray-100 dark:hover:bg-gray-600">
                      <input id="checkbox-item-14" type="checkbox" :value="manager.id" v-model="selectedSentManager"
                             class="w-4 h-4 text-blue-600 bg-gray-100 border-gray-300 rounded focus:ring-blue-500 dark:focus:ring-blue-600 dark:ring-offset-gray-700 dark:focus:ring-offset-gray-700 focus:ring-2 dark:bg-gray-600 dark:border-gray-500">
                      <label for="checkbox-item-14"
                             class="w-full ms-2 text-sm font-medium text-gray-900 rounded dark:text-gray-300">
                        {{ manager.fullName }} : {{ manager.branch }}
                      </label>
                    </div>
                  </li>


                </ul>

              </div>
            </div>
            <br>
            <!-- **** ชั้นความลับ **** -->
            <div class="w-full">
              <hr class="flex mx-auto w-full mb-2 border-slate-400"/>
              <a class="mb-3 font-bold text-xs text-gray-500">กรุณาเพิ่มข้อมูลให้กับเอกสารที่ขออนุมัติ</a>
              <br><br>
              <label for="category"
                     class="block mb-2 text-sm font-bold text-gray-900 dark:text-white">ชั้นความลับ</label>
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
            <br>
            <div class="w-full"><label for="category"
                                       class="block mb-2 text-sm font-bold text-gray-900 dark:text-white">ความเร่งด่วน</label>
              <select id="category" v-model="newDocdata.urgency"
                      class="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-primary-500 focus:border-primary-500 block w-5/6 p-2.5 dark:bg-gray-700">
                <option selected="">เลือกความเร่งด่วนของเอกสาร</option>
                <option value="ปกติ">ปกติ</option>
                <option value="ด่วน">ด่วน</option>
                <option value="ด่วนที่สุด">ด่วนที่สุด</option>
              </select></div>
            <br/>
            <!-- <div>
                <label for="item-weight" class="block mb-2 text-sm font-bold text-gray-900 dark:text-white">Item Weight (kg)</label>
                <input type="number" name="item-weight" id="item-weight" class="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-primary-600 focus:border-primary-600 block w-5/6 p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-primary-500 dark:focus:border-primary-500" placeholder="12" required="">
            </div>  -->

            <!-- เลือกผู้ส่ง -->
            <div class="w-full">
              <label for="category"
                     class="block mb-2 text-sm font-bold text-gray-900 dark:text-white">เลือกผู้ส่ง</label>
              <ul
                  class="w-5/6 text-sm font-medium text-gray-900 bg-white border border-gray-200 rounded-lg dark:bg-gray-700 dark:border-gray-600 dark:text-white">

                <li v-for="user in listOfUsers"
                    class="w-full hover:bg-gray-50 border-b border-gray-200 rounded-t-lg dark:border-gray-600">
                  <div class="flex items-center ps-3">
                    <input id="vue-checkbox" type="checkbox" :value="user.id" v-model="selectedSentUser"
                           class="w-4 h-4 text-blue-600 bg-gray-100 border-gray-300 rounded focus:ring-blue-500 dark:focus:ring-blue-600 dark:ring-offset-gray-700 dark:focus:ring-offset-gray-700 focus:ring-2 dark:bg-gray-600 dark:border-gray-500"/>
                    <!-- <label for="vue-checkbox"
                      class="w-full py-3 ms-2 text-sm font-bold text-gray-900 dark:text-gray-300">{{ user.fullName }}</label> -->
                    <!--                    <label for="angular-checkbox"-->
                    <!--                           class="w-full py-3 ms-2 text-sm font-bold text-orange-400 dark:text-gray-300">{{ user }}</label>-->
                    <label for="angular-checkbox"
                           class="w-full py-3 ms-2 text-sm font-bold text-orange-400 dark:text-gray-300">
                      {{ user.fullName }}
                    </label>
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
              <label for="dropzone-file"
                     class="flex flex-col items-center justify-center w-5/6 h-64 border-2 border-gray-300 border-dashed rounded-lg cursor-pointer bg-gray-50 dark:hover:bg-bray-800 dark:bg-gray-700 hover:bg-gray-100 dark:border-gray-600 dark:hover:border-gray-500 dark:hover:bg-gray-600">
                <div class="flex flex-col items-center justify-center pt-5 pb-6">
                  <svg class="w-8 h-8 mb-4 text-gray-500 dark:text-gray-400" aria-hidden="true"
                       xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 20 16">
                    <path stroke="currentColor" stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                          d="M13 13h3a3 3 0 0 0 0-6h-.025A5.56 5.56 0 0 0 16 6.5 5.5 5.5 0 0 0 5.207 5.021C5.137 5.017 5.071 5 5 5a4 4 0 0 0 0 8h2.167M10 15V6m0 0L8 8m2-2 2 2"/>
                  </svg>
                  <p class="mb-2 text-sm text-gray-500 dark:text-gray-400">
                    <span class="font-semibold">คลิ๊กเพื่ออัพโหลดเอกสาร</span>
                    หรือลากไฟล์และวาง
                  </p>
                  <p class="text-xs text-gray-500 dark:text-gray-400">
                    SVG, PNG, JPG, or Docx (MAX 10 MB)
                  </p>
                </div>
                <input id="dropzone-file" type="file" class="hidden" @change="handleFileUpload"/>
              </label>
            </div>

            <!-- ที่แสดงไฟล์ -->
            <div class="w-full">
              <div class="rounded-lg shadow-md w-5/6">
                <table class="rounded-lg w-full text-sm text-left rtl:text-right text-gray-500 dark:text-gray-400">
                  <thead class="text-xs text-gray-700 uppercase bg-gray-50 dark:bg-gray-700 dark:text-gray-400">
                  <tr>
                    <th scope="col" class="px-6 py-3">ชื่อไฟล์</th>
                    <th scope="col" class="px-6 py-3">ขนาด</th>
                    <th scope="col" class="py-3"></th>
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
                      <a href="#" class="font-medium text-red-600 dark:text-red-500 hover:underline"
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
          <br/>
          <!-- **** ปุ่มยืนยัน **** -->
          <!-- <button type="submit" @click="CreateDocApi"
            class="flex-col justify-center hover:bg-green-800 items-center px-16 py-4 mt-4 sm:mt-6 text-sm font-bold text-white text-center bg-green-600 rounded-lg focus:ring-4 focus:ring-primary-200">
            ยืนยัน
          </button> -->
          <a v-show="showSentButton" type="submit" @click="CreateDocApi"
             class="cursor-pointer flex-col justify-center hover:bg-green-800 items-center px-16 py-4 mt-4 sm:mt-6 text-sm font-bold text-white text-center bg-green-600 rounded-lg focus:ring-4 focus:ring-primary-200">
            ยื่นขออนุมัติ
          </a>

          <button v-show="!showSentButton"
              disabled type="button" class="flex-col justify-center bg-green-800 items-center px-16 py-4 mt-4 sm:mt-6 text-sm font-bold text-white text-center rounded-lg focus:ring-4 focus:ring-primary-200">
            <svg aria-hidden="true" role="status" class="inline w-4 h-4 me-3 text-white animate-spin" viewBox="0 0 100 101" fill="none" xmlns="http://www.w3.org/2000/svg">
              <path d="M100 50.5908C100 78.2051 77.6142 100.591 50 100.591C22.3858 100.591 0 78.2051 0 50.5908C0 22.9766 22.3858 0.59082 50 0.59082C77.6142 0.59082 100 22.9766 100 50.5908ZM9.08144 50.5908C9.08144 73.1895 27.4013 91.5094 50 91.5094C72.5987 91.5094 90.9186 73.1895 90.9186 50.5908C90.9186 27.9921 72.5987 9.67226 50 9.67226C27.4013 9.67226 9.08144 27.9921 9.08144 50.5908Z" fill="#E5E7EB"/>
              <path d="M93.9676 39.0409C96.393 38.4038 97.8624 35.9116 97.0079 33.5539C95.2932 28.8227 92.871 24.3692 89.8167 20.348C85.8452 15.1192 80.8826 10.7238 75.2124 7.41289C69.5422 4.10194 63.2754 1.94025 56.7698 1.05124C51.7666 0.367541 46.6976 0.446843 41.7345 1.27873C39.2613 1.69328 37.813 4.19778 38.4501 6.62326C39.0873 9.04874 41.5694 10.4717 44.0505 10.1071C47.8511 9.54855 51.7191 9.52689 55.5402 10.0491C60.8642 10.7766 65.9928 12.5457 70.6331 15.2552C75.2735 17.9648 79.3347 21.5619 82.5849 25.841C84.9175 28.9121 86.7997 32.2913 88.1811 35.8758C89.083 38.2158 91.5421 39.6781 93.9676 39.0409Z" fill="currentColor"/>
            </svg>
            Loading...
          </button>
        </form>
      </div>
    </section>
  </div>
</template>

<style lang="scss" scoped>

</style>