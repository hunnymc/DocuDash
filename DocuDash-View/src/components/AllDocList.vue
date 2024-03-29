<script setup>
import { ref, onMounted, onUnmounted, watch } from "vue";
import axios from "axios";
import moment from "moment";
import { useRouter } from "vue-router";
import { useDocumentListStore } from "../stores/listOfDocumentStore";
import Cookies from "js-cookie";

const router = useRouter();
const store = useDocumentListStore();

// let mainURL = "http://localhost:5002";
// let mainURL = "http://cp23kw2.sit.kmutt.ac.th:10003";
let mainURL = "https://capstone23.sit.kmutt.ac.th/kw2";

let file = ref(null);

watch(() => store.callFunctionInComponentB, (value) => {
    if (value) {
        getAllDoc();
        store.setCallFunctionInComponentB(false);
    }
});

const navigateToEdit = (doc) => {
    router.push({ path: "/kw2/edit", query: { document: JSON.stringify(doc) } });
};

const getAllDoc = async () => {
    const response = await axios
        .get( mainURL + "/api/doc/",
            {
                headers: { Authorization: `Bearer ${Cookies.get("accessToken")}` },
            }
        )

        .then(function (axiosResponse) {
            listdata.value = axiosResponse.data;
            if (listdata.value.length > 0) {
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

const deleteDoc = async (id) => {
    if (window.confirm("ต้องการจะลบไฟล์ใช่หรือไม่?")) {
        const response = await axios
            .delete(mainURL + "/api/doc/" + id,
                {
                    headers: {
                        Authorization: "Bearer " + Cookies.get("accessToken"),
                    },
                }
            )

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

        getAllDoc();
    }
};

const fileInput = ref(); // อ้างอิงไฟล์อัพโหลด
let recentEditID = ref(0);

// ปุ่มแก้ไขเอกสาร
const editDoc = async (id) => {
    const template = {
        title: "",
        category: "",
        branchSource: "",
        phoneSource: "",
        emailSource: "",
        fromSource: "",
        description: "",
    };
    for (let key in editList.value) {
        if (!template.hasOwnProperty(key)) {
            delete editList.value[key];
        }
    }
    const formData = new FormData();

    formData.append("file", file.value);
    formData.append("data", JSON.stringify(editList.value));
    await axios
        .patch(
            mainURL + "/api/doc/" + id,
            formData,
            {
                headers: {
                    "Content-Type": "multipart/form-data",
                    Authorization: "Bearer " + Cookies.get("accessToken"),
                },
            }
        )
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
            } else if (listdata.value.length === 0) {
                alert("คุณไม่มีเอกสารในระบบ");
            } else {
                alert("เกิดข้อผิดพลาด: " + AxiosError.message);
            }
        });
    getAllDoc();
    cancelEdit();
    location.reload();
};

const editList = ref({
    id: "",
    accessLevel: "",
    documentsDocumentid1: {
        id: "",
        title: "",
        filePath: "",
        dateAdd: "",
        dateUpdate: "",
        category: "",
        secrecyLevel: "",
        urgency: "",
        fromSource: "",
        status: "",
        description: "",
        usersUserid: {
            id: "",
            username: "",
            fullName: "",
            role: "",
            email: "",
            branch: "",
        },
    },
    usersUserid: {
        id: "",
    },
});

const changeTimestampToDate = (timestamp) => {
    return moment(timestamp * 1000).format("DD-MM-YYYY HH:mm");
};

const handleFileUpload = (event) => {
    file.value = event.target.files[0];
};

const cancelEdit = () => {
    editList.value = [];
    getAllDoc();
};

const clickToViewDoc = async (id, obj) => {

    await useDocumentListStore().getdocumentFilenameAndUserIdFromAxios(id);

    router.push({
        name: "ViewDoc",
        params: {
            id: id,
        },
    });

};

function getNewDoc() {
    getAllDoc();
}

onMounted(() => {
    getAllDoc();
});

const listdata = ref([
    // {
    //   id: 1,
    //   Title: "แผนธุรกิจสำหรับสตาร์ทอัพ",
    //   FilePath: "/path/to/file1.pdf",
    //   DateAdd: "2023-04-12T00:00:00",
    //   DateUpdate: "2023-04-12T12:30:00",
    //   Category: "เอกสารภายใน",
    //   SecrecyLevel: "ปกติ",
    //   Urgency: "ด่วนที่สุด",
    //   FromSource: "สตาร์ทอัพ",
    //   Status: "ผ่าน",
    //   UploadBy: 1,
    // },
    // {
    //   id: 2,
    //   Title: "เอกสารการสมัครสมาชิก",
    //   FilePath: "/path/to/file2.pdf",
    //   DateAdd: "2023-04-13T00:00:00",
    //   DateUpdate: "2023-04-13T12:30:00",
    //   Category: "เอกสารภายนอก",
    //   SecrecyLevel: "ลับ",
    //   Urgency: "ปกติ",
    //   FromSource: "สตาร์ทอัพ",
    //   Status: "ผ่าน",
    //   UploadBy: 1,
    // },
    // {
    //   id: 3,
    //   Title: "ข้อเสนอโครงการ",
    //   FilePath: "/path/to/file3.pdf",
    //   DateAdd: "2023-05-14T00:00:00",
    //   DateUpdate: "2023-05-14T12:30:00",
    //   Category: "เอกสารภายใน",
    //   SecrecyLevel: "ลับที่สุด",
    //   Urgency: "ด่วน",
    //   FromSource: "สตาร์ทอัพ",
    //   Status: "ไม่ผ่าน",
    //   UploadBy: 1,
    // },
    // {
    //   id: 4,
    //   Title: "เอกสารที่ 4",
    //   FilePath: "/path/to/file4.pdf",
    //   DateAdd: "2023-04-02T00:00:00",
    //   DateUpdate: "2023-04-02T12:30:00",
    //   Category: "เอกสารภายนอก",
    //   SecrecyLevel: "ปกติ",
    //   Urgency: "ด่วน",
    //   FromSource: "สตาร์ทอัพ",
    //   Status: "ผ่าน",
    //   UploadBy: 1,
    // },
    // {
    //   id: 5,
    //   title: "เอกสารที่ 5",
    //   filePath: "/path/to/file5.pdf",
    //   dateAdd: "2022-04-03T00:00:00",
    //   dateUpdate: "2022-04-03T12:30:00",
    //   category: "เอกสารภายใน",
    //   secrecyLevel: "ปกปิด",
    //   urgency: "ปกติ",
    //   fromSource: "สตาร์ทอัพ",
    //   status: "ผ่าน",
    //   uploadBy: 1,
    // },
]);
</script>

<template>
    <div class="shadow-md sm:rounded-lg table-container">
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
                    <th scope="col" class="px-6 py-3">
                        จัดการ
                        <button class="m-1" data-popover-target="popover-description" data-popover-placement="bottom-end"
                            type="button">
                            <svg class="w-4 h-4 ms-2 text-gray-400 hover:text-gray-500" aria-hidden="true"
                                fill="currentColor" viewBox="0 0 20 20" xmlns="http://www.w3.org/2000/svg">
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
                                    ท่านสามารถแก้ไขข้อมูลในเอกสารได้ด้วยการคลิ๊กที่รูปดินสอ
                                    แล้วทำการแก้ไขข้อมูลตามที่ต้องการจากนั้นกดตกลง
                                </p>
                                <h3 class="font-semibold text-gray-900 dark:text-white">
                                    การลบข้อมูล
                                </h3>
                                <p>
                                    ท่านสามารถลบเอกสารได้ที่ปุ่มถังขยะ
                                    <span class="important">หากลบแล้วจะไม่สามารถกู้คืนได้
                                        กรุณาตรวจเช็คก่อนดำเนินการ</span>
                                </p>
                            </div>
                            <div data-popper-arrow></div>
                        </div>
                    </th>
                </tr>
            </thead>

            <tbody v-if="listdata.length > 0" v-for="(thisdoc, index) in listdata">
                <tr class="bg-white text-gray-900 border-b border-gray-700 hover:bg-gray-600 hover:text-white">
                    <td class="w-4 p-4">
                        {{ index + 1 }}
                    </td>
                    <th scope="row" class="px-6 py-4 font-medium">
                        <a v-on:click="clickToViewDoc(thisdoc.documentsDocumentid1.id, thisdoc)"
                            class="cursor-pointer hover:text-blue-200">{{
                                thisdoc.documentsDocumentid1.title }}
                        </a>
                    </th>
                    <td class="px-6 py-4">
                        {{ thisdoc.documentsDocumentid1.description }}
                    </td>
                    <td class="px-6 py-4">{{ thisdoc.documentsDocumentid1.urgency }}</td>
                    <td class="px-6 py-4">{{ thisdoc.documentsDocumentid1.category }}</td>
                    <td class="px-6 py-4">{{ thisdoc.documentsDocumentid1.dateAdd }}</td>
                    <td class="px-6 py-4">
                        <div>
                            <!-- hamberger -->
                            <!-- edit buttton -->
                            <button @click="navigateToEdit(thisdoc)">
                                <a class="font-medium text-amber-400 hover:underline">
                                    <svg class="w-6 h-6" id="Icons" xmlns="http://www.w3.org/2000/svg"
                                        xmlns:xlink="http://www.w3.org/1999/xlink" viewBox="0 0 32 32" xml:space="preserve"
                                        fill="#ffbb00" stroke="#ffbb00">
                                        <g id="SVGRepo_bgCarrier" stroke-width="0"></g>
                                        <g id="SVGRepo_tracerCarrier" stroke-linecap="round" stroke-linejoin="round"></g>
                                        <g id="SVGRepo_iconCarrier">
                                            <path class="st0" d="M5,21l-2,8l8-2L30,8l0,0c-0.6-3-3-5.4-6-6l0,0L5,21z"></path>
                                            <path class="st0" d="M26,12L26,12c-0.6-3-3-5.4-6-6l0,0"></path>
                                            <line class="st0" x1="9" y1="23" x2="21" y2="11"></line>
                                        </g>
                                    </svg>
                                </a>
                            </button>

                            <!-- delete buttton -->
                            <button type="button" v-on:click="deleteDoc(thisdoc.documentsDocumentid1.id)" class="px-3">
                                <a href="#" class="font-medium text-amber-400">
                                    <svg class="w-6 h-7" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg">
                                        <g id="SVGRepo_bgCarrier" stroke-width="0"></g>
                                        <g id="SVGRepo_tracerCarrier" stroke-linecap="round" stroke-linejoin="round"></g>
                                        <g id="SVGRepo_iconCarrier">
                                            <path
                                                d="M4 6H20M16 6L15.7294 5.18807C15.4671 4.40125 15.3359 4.00784 15.0927 3.71698C14.8779 3.46013 14.6021 3.26132 14.2905 3.13878C13.9376 3 13.523 3 12.6936 3H11.3064C10.477 3 10.0624 3 9.70951 3.13878C9.39792 3.26132 9.12208 3.46013 8.90729 3.71698C8.66405 4.00784 8.53292 4.40125 8.27064 5.18807L8 6M18 6V16.2C18 17.8802 18 18.7202 17.673 19.362C17.3854 19.9265 16.9265 20.3854 16.362 20.673C15.7202 21 14.8802 21 13.2 21H10.8C9.11984 21 8.27976 21 7.63803 20.673C7.07354 20.3854 6.6146 19.9265 6.32698 19.362C6 18.7202 6 17.8802 6 16.2V6M14 10V17M10 10V17"
                                                stroke="#ff0000" stroke-width="2" stroke-linecap="round"
                                                stroke-linejoin="round"></path>
                                        </g>
                                    </svg>
                                </a>
                            </button>
                        </div>
                    </td>
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
                                    <p
                                        class="max-w-2xl mb-6 font-light text-gray-500 lg:mb-8 md:text-lg lg:text-xl dark:text-gray-400">
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
                                  <img src="../assets/im1.png" alt="mockup" />
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
