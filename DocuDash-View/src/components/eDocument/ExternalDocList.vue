<script setup>
import {ref, onMounted, onUnmounted, watch} from "vue";
import axios from "axios";
import moment from "moment";
import {useRouter} from "vue-router";
import {useDocumentListStore} from "../../stores/listOfDocumentStore";
import Cookies from "js-cookie";

const router = useRouter();
const store = useDocumentListStore();

let mainURL = import.meta.env.VITE_API_URL;

// let mainURL = "http://localhost:5002";
// let mainURL = "http://cp23kw2.sit.kmutt.ac.th:10003";
// let mainURL = "https://capstone23.sit.kmutt.ac.th/kw2";

let file = ref(null);

watch(() => store.callFunctionInComponentB, (value) => {
  if (value) {
    getAllDoc();
    store.setCallFunctionInComponentB(false);
  }
});

//---------------  button sort by dateAdd in des or asc order ---------------

const sortDateAdd = (order) => {

  // old to new
  if (order === 1) {
    listdata.value.sort((a, b) => {
      let dateA = new Date(a.documentsDocumentid1.dateAdd.split(" ")[0].split("-").reverse().join("-") + "T" + a.documentsDocumentid1.dateAdd.split(" ")[1]);
      let dateB = new Date(b.documentsDocumentid1.dateAdd.split(" ")[0].split("-").reverse().join("-") + "T" + b.documentsDocumentid1.dateAdd.split(" ")[1]);
      return dateA - dateB;
    });

    // new to old
  } else if (order === 2) {
    listdata.value.sort((a, b) => {
      let dateA = new Date(a.documentsDocumentid1.dateAdd.split(" ")[0].split("-").reverse().join("-") + "T" + a.documentsDocumentid1.dateAdd.split(" ")[1]);
      let dateB = new Date(b.documentsDocumentid1.dateAdd.split(" ")[0].split("-").reverse().join("-") + "T" + b.documentsDocumentid1.dateAdd.split(" ")[1]);
      return dateB - dateA;
    });
  }
};


const getAllDoc = async () => {
  const response = await axios
      .post(mainURL + "/api/doc/user/email",
          {email: Cookies.get("email")},
          {
            headers: {Authorization: `Bearer ${Cookies.get("accessToken")}`},
          }
      )

      .then(function (axiosResponse) {
        listdata.value = axiosResponse.data;
        if (listdata.value.length > 0) {

          // filter category is "เอกสารภายนอก"
          listdata.value = listdata.value.filter(
              (item) => item.documentsDocumentid1.category === "เอกสารภายนอก"
          );

          // Sort listdata by dateUpdate in descending order
          listdata.value.sort((a, b) => {
            return (
                new Date(b.documentsDocumentid1.dateUpdate) -
                new Date(a.documentsDocumentid1.dateUpdate)
            );
          });

          listdata.value.forEach((element) => {
            element.documentsDocumentid1.dateAdd = changeTimestampToDate(
                element.documentsDocumentid1.dateAdd
            );
            element.documentsDocumentid1.dateUpdate = changeTimestampToDate(
                element.documentsDocumentid1.dateUpdate
            );
          });
        }
      })

      .catch(function (AxiosError) {
        if (AxiosError.response) {
          switch (AxiosError.response.status) {
            case 400:
              alert("คำขอไม่ถูกต้อง");
              break;
            case 401:
              alert("ไม่ได้รับอนุญาต");
              break;
            case 403:
              alert("ถูกปฏิเสธ");
              break;
            case 404:
              alert("ไม่พบข้อมูล");
              break;
            case 500:
              alert("เซิร์ฟเวอร์เกิดข้อผิดพลาด");
              break;
            default:
              alert("เกิดข้อผิดพลาด");
          }
        } else if (AxiosError.request) {
          alert("ไม่ได้รับการตอบสนองจากเซิร์ฟเวอร์");
        } else {
          alert("เกิดข้อผิดพลาด: " + AxiosError.message);
        }

      });
};

const changeTimestampToDate = (timestamp) => {
  return moment(timestamp * 1000).format("DD-MM-YYYY HH:mm");
};

const clickToViewDoc = async (id, obj) => {
  console.log("clickToViewDoc", id, obj);
  await useDocumentListStore().getdocumentFilenameAndUserIdFromAxios(id);
  await router.push({
    name: "ViewDoc",
    params: {
      id: id,
    },
  });

};

onMounted(async () => {
  await getAllDoc();
});

const listdata = ref([
  // {
  //   "id": 721,
  //   "accessLevel": null,
  //   "documentsDocumentid1": {
  //     "id": 552,
  //     "title": "test Not Found",
  //     "filePath": "Wk1_Orientation_Thai.pdf",
  //     "dateAdd": "01-04-2024 16:55",
  //     "dateUpdate": "01-04-2024 16:55",
  //     "category": "เอกสารภายใน",
  //     "secrecyLevel": "ลับ",
  //     "urgency": "ปกติ",
  //     "fromSource": "นภา ฟ้าสวย",
  //     "status": null,
  //     "description": "test",
  //     "branchSource": "งานเอกสาร",
  //     "emailSource": "user1@example.com",
  //     "phoneSource": "0812345678",
  //     "usersUserid": {
  //       "id": 1,
  //       "username": "นภา",
  //       "fullName": "นภา ฟ้าสวย",
  //       "role": "ADMIN",
  //       "email": "user1@example.com",
  //       "branch": "งานเอกสาร"
  //     }
  //   },
  //   "usersUserid": {"id": 1}
  // }
]);

</script>

<template>
  <div class="w-full relative overflow-x-auto shadow-md ">

    <section class="flex items-center">
      <div class="w-full   mx-auto ">
        <div class="relative overflow-hidden bg-green-800 ">
          <div
              class=" flex-row items-center justify-between p-4 space-y-3 sm:flex sm:space-y-0 sm:space-x-4">
            <div>
              <h5 class="mr-3 font-semibold text-white text-3xl">รายการเอกสารหมวดหมู่ภายนอก</h5>
              <p class="text-gray-100 ">รวมรายการเอกสารหมวดหมู่ภายนอกที่เกี่ยวข้องกับท่าน</p>
            </div>
          </div>
        </div>
      </div>
    </section>

    <!-- หัวตาราง -->
    <div
        class="flex items-center justify-between flex-column md:flex-row flex-wrap space-y-4 md:space-y-0 py-1 bg-green-800 ">
      <div>
        <button id="dropdownActionButton"
                class="ml-4 inline-flex items-center text-gray-500 bg-white border border-gray-300 focus:outline-none hover:bg-gray-100 focus:ring-4 focus:ring-gray-100 font-medium rounded-lg text-sm px-3 py-1.5 dark:bg-gray-800 dark:text-gray-400 dark:border-gray-600 dark:hover:bg-gray-700 dark:hover:border-gray-600 dark:focus:ring-gray-700"
                data-dropdown-toggle="dropdownAction"
                type="button">
          <span class="sr-only">Action button</span>
          เรียงลำดับ
          <svg aria-hidden="true" class="w-2.5 h-2.5 ms-2.5" fill="none" viewBox="0 0 10 6"
               xmlns="http://www.w3.org/2000/svg">
            <path d="m1 1 4 4 4-4" stroke="currentColor" stroke-linecap="round" stroke-linejoin="round"
                  stroke-width="2"/>
          </svg>
        </button>
        <!-- Dropdown menu -->
        <div id="dropdownAction"
             class="z-10 hidden bg-white divide-y divide-gray-100 rounded-lg shadow w-44 ">
          <ul aria-labelledby="dropdownActionButton" class="py-1 text-sm text-gray-700 ">
            <li>
              <a @click="sortDateAdd(2)" class="block px-4 py-2 hover:bg-gray-100 cursor-pointer">เวลาที่ได้รับ ใหม่ - เก่า </a>
            </li>
            <li>
              <a @click="sortDateAdd(1)" class="block px-4 py-2 hover:bg-gray-100 cursor-pointer">เวลาที่ได้รับ เก่า - ใหม่</a>
            </li>
          </ul>
        </div>
      </div>
    </div>

    <table class="w-full text-sm text-left rtl:text-right text-white">
      <thead class="text-xs text-white uppercase bg-green-800">
      <tr>
        <th scope="col" class="p-4">
          <div class="flex items-center">
            <label class="">รายการ</label>
          </div>
        </th>
        <th scope="col" class="px-6 py-3">ชื่อเรื่อง</th>
        <th scope="col" class="px-6 py-3">รายละเอียด</th>
        <th scope="col" class="px-6 py-3">ความเร่งด่วน</th>
        <th scope="col" class="px-6 py-3">หมวดหมู่</th>
        <th scope="col" class="px-6 py-3">ลงวันที่</th>
      </tr>
      </thead>

      <tbody v-if="listdata.length > 0" v-for="(thisdoc, index) in listdata">
      <tr class="bg-white text-gray-600 border-b  hover:bg-gray-50 ">
        <td class="w-4 p-4">
          {{ index + 1 }}
        </td>
        <th scope="row" class="px-6 py-4 font-medium">
          <a v-on:click="clickToViewDoc(thisdoc.documentsDocumentid1.id, thisdoc)"
             class="cursor-pointer hover:text-blue-700 text-gray-900  font-semibold">{{
              thisdoc.documentsDocumentid1.title
            }}
          </a>
        </th>
        <td class="px-6 py-4">
          {{ thisdoc.documentsDocumentid1.description.length > 50 ? thisdoc.documentsDocumentid1.description.slice(0, 50) + '...' : thisdoc.documentsDocumentid1.description }}        </td>
        <td class="px-6 py-4">{{ thisdoc.documentsDocumentid1.urgency }}</td>
        <td class="px-6 py-4">{{ thisdoc.documentsDocumentid1.category }}</td>
        <td class="px-6 py-4">{{ thisdoc.documentsDocumentid1.dateAdd }}</td>
      </tr>
      </tbody>

      <tbody v-else>
      <tr>
        <td colspan="7" class="text-center text-black">
          <section class="bg-white dark:bg-gray-900">
            <div class="grid max-w-screen-xl px-4 py-8 mx-auto lg:gap-8 xl:gap-0 lg:py-16 lg:grid-cols-12">
              <div class="mr-auto place-self-center lg:col-span-7">
                <h1
                    class="max-w-2xl mb-4 text-4xl font-extrabold tracking-tight leading-none md:text-5xl xl:text-6xl dark:text-white">
                  ไม่มีเอกสารในระบบ
                </h1>
                <p class="max-w-2xl mb-6 font-light text-gray-500 lg:mb-8 md:text-lg lg:text-xl dark:text-gray-400">
                  ขณะนี้ยังไม่มีเอกสารที่ถูกส่งถึงท่าน
                  ท่านสามารถเช็คเอกสารใหม่ได้ผ่านเมนูแจ้งเตือน
                  <span class="text-lime-500">หากมีเอกสารระบบจะทำการแจ้งเตือนอีกครั้ง
                    </span>หรือติดต่อแอดมินเพี่อสอบถามข้อมูลเพิ่มเติม
                </p>
                <a href="#"
                   class="inline-flex items-center justify-center px-5 py-3 mr-3 text-base font-medium text-center text-white rounded-lg bg-green-600 hover:bg-green-800 dark:focus:ring-primary-900">
                  กลับสู่หน้าแรก
                  <svg class="w-5 h-5 ml-2 -mr-1" fill="currentColor" viewBox="0 0 20 20"
                       xmlns="http://www.w3.org/2000/svg">
                    <path fill-rule="evenodd"
                          d="M10.293 3.293a1 1 0 011.414 0l6 6a1 1 0 010 1.414l-6 6a1 1 0 01-1.414-1.414L14.586 11H3a1 1 0 110-2h11.586l-4.293-4.293a1 1 0 010-1.414z"
                          clip-rule="evenodd"></path>
                  </svg>
                </a>
                <a href="#"
                   class="inline-flex items-center justify-center px-5 py-3 text-base font-medium text-center text-gray-900 border border-gray-300 rounded-lg hover:bg-gray-100 focus:ring-4 focus:ring-gray-100 dark:text-white dark:border-gray-700 dark:hover:bg-gray-700 dark:focus:ring-gray-800">
                  ติดต่อแอดมิน
                </a>
              </div>
              <div class="hidden lg:mt-0 lg:col-span-5 lg:flex">
                <img src="../../assets/im1.png" alt="mockup"/>
              </div>
            </div>
          </section>
        </td>
      </tr>
      </tbody>
    </table>
  </div>
</template>

<style>
.table-container {
  width: 100%;
  margin: 0 auto;
}

.st0 {
  fill: none;
  stroke: #ffb700;
  stroke-width: 2;
  stroke-linecap: round;
  stroke-linejoin: round;
  stroke-miterlimit: 10;
}

.important {
  color: red;
  font-weight: bold;
}
</style>
