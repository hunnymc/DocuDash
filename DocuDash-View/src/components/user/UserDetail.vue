<script setup>
import {onBeforeMount, ref, watch} from "vue";
import {useLoginUserStore} from "../../stores/LoginUserStore.js";
import axios from "axios";
import Cookies from "js-cookie";

let mainURL = import.meta.env.VITE_API_URL;

let user = ref({
  "username": "",
  "fullName": "",
  "role": "",
  "email": "",
  "phone": "",
  "branch": ""
});

let editFullName = ref("");
let editEmail = ref("");
let editPhone = ref("");

// edit button: name id = 1 , edit email id = 2 , edit phone id = 3
const updateUser = async (buttonID) => {

  let data = {};
  let oldData = {};
  let newData = {};
  let fieldName = "";

  if (buttonID === 1) {
    if (!editFullName.value) {
      alert("กรุณากรอกชื่อ - นามสกุล");
      return;
    } else if (!validateFullName(editFullName.value)) {
      alert("กรุณากรอกชื่อ - นามสกุลให้ถูกต้อง");
      return;
    } else if (editFullName.value === user.value.fullName) {
      alert("กรุณากรอกชื่อ - นามสกุลใหม่");
      return;
    }
    oldData = user.value.fullName;
    newData = editFullName.value;
    data = {fullName: editFullName.value};
    fieldName = "ชื่อ - นามสกุล";
  } else if (buttonID === 2) {
    if (!editEmail.value) {
      alert("กรุณากรอกอีเมลล์");
      return;
    } else if (!validateEmail(editEmail.value)) {
      alert("กรุณากรอกอีเมลล์ให้ถูกต้อง");
      return;
    } else if (editEmail.value === user.value.email) {
      alert("กรุณากรอกอีเมลล์ใหม่");
      return;
    }
    oldData = user.value.email;
    newData = editEmail.value;
    data = {email: editEmail.value};
    fieldName = "อีเมลล์";
  } else if (buttonID === 3) {
    if (!editPhone.value) {
      alert("กรุณากรอกเบอร์โทรศัพท์");
      return;
    } else if (!validatePhone(editPhone.value)) {
      alert("กรุณากรอกเบอร์โทรศัพท์ให้ถูกต้อง");
      return;
    } else if (editPhone.value === user.value.phone) {
      alert("กรุณากรอกเบอร์โทรศัพท์ใหม่");
      return;
    }
    oldData = user.value.phone;
    newData = editPhone.value;
    data = {phone: editPhone.value};
    fieldName = "เบอร์โทรศัพท์";
  }

  // แสดงข้อมูลเก่าและข้อมูลใหม่ให้ผู้ใช้ยืนยันก่อนทำการแก้ไข
  if (window.confirm(`คุณกำลังจะแก้ไข${fieldName}:\n\nข้อมูลปัจจุบัน: ${oldData}\n\nข้อมูลที่ต้องการแก้ไข: ${newData}\n\nกรุณายืนยันว่าคุณต้องการทำการแก้ไขข้อมูลนี้`)) {
    await axios.patch(
        mainURL + "/api/user/" + user.value.id,
        data,
        {
          headers: {
            Authorization: `Bearer ${Cookies.get("accessToken")}`,
          },
        }
    )
        .then((response) => {
          if (response.status === 200) {
            // if success update user info by edit data
            if (buttonID === 1) {
              user.value.fullName = editFullName.value;
              editFullName.value = "";
            } else if (buttonID === 2) {
              user.value.email = editEmail.value;
              editEmail.value = "";
            } else if (buttonID === 3) {
              user.value.phone = editPhone.value;
              editPhone.value = "";
            }
          }

          alert("แก้ไขข้อมูลสำเร็จ")
        })
        .catch((error) => {
          alert(error.response.data.message);
        });
  }
};

function validateEmail(email) {
  const regex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
  return regex.test(email);
}

function validateFullName(fullName) {
  const words = fullName.trim().split(' ');
  return words.length >= 2;
}

function validatePhone(phone) {
  const regex = /^\d{10}$/;
  return regex.test(phone);
}

let isEditFullNameButtonDisabled = ref(true);
let isEditEmailButtonDisabled = ref(true);
let isEditPhoneButtonDisabled = ref(true);

watch(editFullName, (newVal) => {
  isEditFullNameButtonDisabled.value = !newVal;
});

watch(editEmail, (newVal) => {
  isEditEmailButtonDisabled.value = !newVal;
});

watch(editPhone, (newVal) => {
  isEditPhoneButtonDisabled.value = !newVal;
});

onBeforeMount(() => {
  user.value = useLoginUserStore().adminViewUser;
});

</script>

<template>
  <div class="w-full bg-white overflow-hidden shadow rounded-lg border">
    <div class="px-4 py-5 sm:px-6">
      <h1 class="font-extrabold text-xl leading-6  text-gray-900">
        ข้อมูลผู้ใช้งาน
      </h1>
      <p class="mt-1 max-w-2xl text-sm text-gray-500">
        รายละเอียดข้อมูลผู้ใช้งาน
      </p>
    </div>
    <div class="border-t border-gray-200 px-4 py-5 sm:p-0">
      <dl class="sm:divide-y sm:divide-gray-200">
        <div class="py-3 sm:py-5 sm:grid sm:grid-cols-3 sm:gap-4 sm:px-6">
          <dt class="text-sm font-medium text-gray-500">
            ชื่อ - นามสกุล

          </dt>
          <dd class="font-bold mt-1 text-sm text-gray-900 sm:mt-0 sm:col-span-2 flex justify-between items-center">
            {{ user.fullName }}
          </dd>
          <dd class="border border-orange-200 mt-1 p-2 text-sm text-gray-900 sm:mt-6 sm:col-span-2 flex justify-between items-center">
            <label class="text-sm font-medium text-gray-500">เปลี่ยน / แก้ไขชื่อ - นามสกุล</label>
            <input v-model="editFullName" type="text"
                   class="  block p-2 px-3 text-gray-900 border border-gray-300 rounded-lg bg-gray-50 text-xs"
                   placeholder="กรอกชื่อ-นามสกุลใหม่" required/>
            <button @click="updateUser(1)"
                    :disabled="isEditFullNameButtonDisabled"
                    :class="{'mr-56 text-white font-bold py-2 px-6 rounded-lg': true, 'bg-blue-500 hover:bg-blue-700': !isEditFullNameButtonDisabled, 'bg-gray-500': isEditFullNameButtonDisabled}">
              ยืนยัน
            </button>
          </dd>
        </div>
        <div class="py-3 sm:py-5 sm:grid sm:grid-cols-3 sm:gap-4 sm:px-6">
          <dt class="text-sm font-medium text-gray-500">
            Email
          </dt>
          <dd class="font-bold  mt-1 text-sm text-gray-900 sm:mt-0 sm:col-span-2">
            {{ user.email }}
          </dd>
          <dd class="border border-orange-200 mt-2 p-2 text-sm text-gray-900 sm:mt-6 sm:col-span-2 flex justify-between items-center">
            <label class="text-sm font-medium text-gray-500">เปลี่ยน / แก้ไขอีเมลล์องค์กร</label>
            <input v-model="editEmail" type="email"
                   class="block p-2 px-3 text-gray-900 border border-gray-300 rounded-lg bg-gray-50 text-xs"
                   placeholder="กรอกเบอร์อีเมลล์ใหม่" required/>
            <button @click="updateUser(2)"
                    :disabled="isEditEmailButtonDisabled"
                    :class="{'mr-56 text-white font-bold py-2 px-6 rounded-lg': true, 'bg-blue-500 hover:bg-blue-700': !isEditEmailButtonDisabled, 'bg-gray-500': isEditEmailButtonDisabled}">
              ยืนยัน
            </button>
          </dd>
        </div>
        <div class="py-3 sm:py-5 sm:grid sm:grid-cols-3 sm:gap-4 sm:px-6">
          <dt class="text-sm font-medium text-gray-500 ">
            เบอร์โทรศัพท์
          </dt>
          <dd class=" font-bold  mt-1 text-sm text-gray-900 sm:mt-0 sm:col-span-2 flex justify-between items-center">
            {{ user.phone ? user.phone : "ไม่มีข้อมูล"}}

          </dd>
          <dd class="border border-orange-200 mt-2 p-2 text-sm text-gray-900 sm:mt-6 sm:col-span-2 flex justify-between items-center">
            <label class="text-sm font-medium text-gray-500">เปลี่ยน / แก้ไขเบอร์โทรศัพท์</label>
            <input v-model="editPhone" type="tel"
                   class="block p-2 px-3 text-gray-900 border border-gray-300 rounded-lg bg-gray-50 text-xs"
                   placeholder="กรอกเบอร์โทรใหม่" required/>
            <button @click="updateUser(3)"
                    :disabled="isEditPhoneButtonDisabled"
                    :class="{'mr-56 text-white font-bold py-2 px-6 rounded-lg': true, 'bg-blue-500 hover:bg-blue-700': !isEditPhoneButtonDisabled, 'bg-gray-500': isEditPhoneButtonDisabled}">
              ยืนยัน
            </button>
          </dd>
        </div>
        <div class="py-3 sm:py-5 sm:grid sm:grid-cols-3 sm:gap-4 sm:px-6">
          <dt class="text-sm font-medium text-gray-500">
            ตำแหน่ง/แผนก
          </dt>
          <dd class="font-bold  mt-1 text-sm text-gray-900 sm:mt-0 sm:col-span-2">
            {{ user.branch }}
          </dd>
        </div>

        <div class="py-3 sm:py-5 sm:grid sm:grid-cols-3 sm:gap-4 sm:px-6">
          <dt class="text-sm font-medium text-gray-500">

          </dt>
          <dd class="mt-1 text-sm text-gray-900 sm:mt-0 sm:col-span-2">

          </dd>
        </div>

      </dl>
    </div>
<!--    <button class="ml-4 mt-26 bg-red-600 hover:bg-red-700 text-white font-bold py-2 px-12 rounded-lg">ลบบัญชีผู้ใช้-->
<!--    </button>-->


  </div>
</template>

<style></style>