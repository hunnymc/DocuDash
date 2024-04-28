<script setup>
import {onBeforeMount, ref, watch} from "vue";
import {useRoute} from "vue-router";
import axios from "axios";
import Cookies from "js-cookie";

let route = useRoute();

let mainURL = import.meta.env.VITE_API_URL;

// let mainURL = "http://localhost:5002";
// let mainURL = "http://cp23kw2.sit.kmutt.ac.th:10003";
// let mainURL = "https://capstone23.sit.kmutt.ac.th/kw2";

let user = ref({
  username: "",
  fullName: "",
  role: "",
  email: "",
  branch: "",
  phone: "",
});

const getUserInfo = async () => {
  await axios.post(
      mainURL + '/api/auth/user-info'
      , {email: Cookies.get("email")}
      , {headers: {"Authorization": "Bearer " + Cookies.get("accessToken"),}})
      .then((response) => {
        if (response.status === 200) {
          user.value = {...response.data};
          Cookies.set("userId", response.data.id);
        }
      });
};

const updateUser = async () => {
  let data = {};
  let oldData = {};
  let newData = {};
  let fieldName = "";

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
          // if success update user info by edit data
          user.value.phone = editPhone.value;
          editPhone.value = "";

          alert("แก้ไขข้อมูลสำเร็จ")
          getUserInfo();

        })
        .catch((error) => {
          alert(error.response.data.message);
        });
  }
};

let editPhone = ref("");
let isEditPhoneButtonDisabled = ref(true);

function validatePhone(phone) {
  const regex = /^\d{10}$/;
  return regex.test(phone);
}

watch(editPhone, (newVal) => {
  isEditPhoneButtonDisabled.value = !newVal;
});
onBeforeMount(() => {
  getUserInfo();
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
          <dd class="mt-1 text-sm text-gray-900 sm:mt-0 sm:col-span-2">
            {{ user.fullName }}
          </dd>
        </div>
        <div class="py-3 sm:py-5 sm:grid sm:grid-cols-3 sm:gap-4 sm:px-6">
          <dt class="text-sm font-medium text-gray-500">
            Email
          </dt>
          <dd class="mt-1 text-sm text-gray-900 sm:mt-0 sm:col-span-2">
            {{ user.email }}
          </dd>
        </div>
        <div class="py-3 sm:py-5 sm:grid sm:grid-cols-3 sm:gap-4 sm:px-6">
          <dt class="text-sm font-medium text-gray-500 ">
            เบอร์โทรศัพท์
          </dt>
          <dd class="mt-1 text-sm text-gray-900 sm:mt-0 sm:col-span-2 flex justify-between items-center">
            {{ user.phone }}
            <!--                      <button class="mr-56 bg-orange-500 hover:bg-orange-700 text-white font-bold py-2 px-4 rounded-lg">-->
            <!--                        แก้ไข-->
            <!--                      </button>-->
          </dd>
          <dd class="border mt-1 p-2 text-sm text-gray-900 sm:mt-0 sm:col-span-2 flex justify-between items-center">
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
          <dd class="mt-1 text-sm text-gray-900 sm:mt-0 sm:col-span-2">
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


  </div>
</template>

<style></style>