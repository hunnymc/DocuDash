// ------------------ Import Utility ------------------
import { createRouter, createWebHistory } from "vue-router";
import Cookies from "js-cookie";
import { useDocumentListStore } from "../stores/listOfDocumentStore.js";
import { useRefreshFunctionStore } from "../stores/RefeshFunctionStore.js";
import { useGraphStore } from "../stores/GraphStore.js";

// ------------------ Import Views ------------------
import AllDocList from "../views/AllDocListView.vue";
import AdminDocList from "../views/AdminDocListView.vue";
import CreateDoc from "../views/CreateDoc.vue";
import UserInfo from "../views/UserInfo.vue";
import ViewDoc from "../views/ViewDocView.vue";
import Login from "../views/LoginView.vue";
import EditDoc from "../views/EditDocView.vue";
import Register from "../views/RegisterView.vue";
import NotFound from "../views/NotFoundView.vue";
import Chat from "../views/ChatView.vue";
import Setting from "../views/SettingView.vue";
import MainMenu from "../components/MainMenu.vue";
import ApproveList from "../views/approve/approve-user-list/ApproveListView.vue";
import AdminAcceptView from "../views/approve/AdminAcceptView.vue";
import AdminAlvView from "../views/approve/approve-detail/AdminAlvView.vue";
import UserAlvView from "../views/approve/approve-detail/UserAlvView.vue";
import ManagerAlvView from "../views/approve/approve-detail/ManagerAlvView.vue";
import RequestAdminApprove from "../views/approve/approve-create/UserRequestApprovalView.vue";
import ApproveStatusView from "../views/approve/approve-status-list/ApproveStatusView.vue";
import ManagerListView from "../views/approve/approve-list/ManagerListView.vue";
import ApproveDoneRequestList from "../views/approve/approve-user-list/ApproveDoneList.vue";
import ApprovePendingList from "../views/approve/approve-user-list/ApprovePendingList.vue";
import ApproveRejectList from "../views/approve/approve-user-list/ApproveRejectList.vue";
import ListNonApproveView from "../views/eDoc/ListNonApproveView.vue";
import axios from "axios";
import InternalDocListView from "../views/eDoc/InternalDocListView.vue";
import ExternalDocListView from "../views/eDoc/ExternalDocListView.vue";

const user_role = Cookies.get("role");
const token = Cookies.get("accessToken");

const routes = [


    // Main Menu
    { path: "/", redirect: "/kw2/menu" },

    { path: "/kw2/", redirect: "/kw2/menu" },

    { path: "/kw2/menu", name: "MainMenu", component: MainMenu },


    // Document
    { path: "/kw2/document", redirect: "/kw2/document/list" },

    { path: "/kw2/document/list", name: "AllDocList", component: AllDocList },
    {
        path: "/kw2/document/list/all",
        name: "AdminDocList",
        component: AdminDocList,
        beforeEnter: (to, from, next) => {
            if (user_role === "ADMIN") {
                next();
            } else {
                next({ name: "NotFound" });
            }
        },
    },

    { path: "/kw2/document/list/internal", name: "InternalDocListView", component: InternalDocListView },

    { path: "/kw2/document/list/external", name: "ExternalDocListView", component: ExternalDocListView },

    { path: "/kw2/document/add", name: "CreateDoc", component: CreateDoc },

    {
        path: "/kw2/user",
        name: "UserInfo",
        component: UserInfo,
    },
    {
        path: "/kw2/document/view/:id",
        name: "ViewDoc",
        component: ViewDoc,
        beforeEnter: (to, from, next) => {
            const documentStore = useDocumentListStore();
            documentStore.getdocumentFilenameAndUserIdFromAxios(to.params.id).then(r => {
                next();
            });
        },
    },

    { path: "/kw2/login", name: "Login", component: Login },

    {
        path: "/kw2/document/edit/:id",
        namr: "EditDoc",
        component: EditDoc,
        beforeEnter: (to, from, next) => {
            if (Cookies.get("role") !== "ADMIN") {
                next({ name: "NotFound" });
            } else {
                next();
            }
        },
    },

    {
        path: "/kw2/register",
        name: "Register",
        component: Register,
        beforeEnter: (to, from, next) => {
            if (Cookies.get("role") === "ADMIN") {
                next();
            } else {
                next({ name: "NotFound" });
            }
        },
    },

    {
        path: "/kw2/document/list/pending-request",
        name: "ListNonApproveView",
        component: ListNonApproveView,
        beforeEnter: (to, from, next) => {
            if (Cookies.get("role") === "ADMIN" || Cookies.get("role") === "USER") {
                next();
            } else {
                next({ name: "NotFound" });
            }
        },
    },

    { path: "/kw2/document/chat", name: "Chat", component: Chat },

    { path: "/kw2/document/setting", name: "Setting", component: Setting },


    // Approval
    {
        path: "/kw2/approval",
        beforeEnter: (to, from, next) => {
            if (user_role === "ADMIN") {
                next("/kw2/approval/admin/dashboard");
            } else if (user_role === "MANAGER") {
                next("/kw2/approval/list/manager-accept");
            } else {
                next("/kw2/approval/list");
            }
        },
    },

    {
        path: "/kw2/approval/list",
        name: "ApproveList",
        component: ApproveList,
        beforeEnter: (to, from, next) => {
            if (Cookies.get("role") === "ADMIN" || Cookies.get("role") === "USER") {
                next();
            } else {
                next({ name: "NotFound" });
            }
        },
    },

    {
        path: "/kw2/approval/list/done-request",
        name: "ApproveDoneRequestList",
        component: ApproveDoneRequestList,
        beforeEnter: (to, from, next) => {
            if (Cookies.get("role") === "ADMIN" || Cookies.get("role") === "USER") {
                next();
            } else {
                next({ name: "NotFound" });
            }
        },
    },

    {
        path: "/kw2/approve/list/pending-request",
        name: "ApprovePendingRequestList",
        component: ApprovePendingList,
        beforeEnter: (to, from, next) => {
            if (Cookies.get("role") === "ADMIN" || Cookies.get("role") === "USER") {
                next();
            } else {
                next({ name: "NotFound" });
            }
        },
    },

    {
        path: "/kw2/approve/list/reject-request",
        name: "ApproveRejectRequestList",
        component: ApproveRejectList,
        beforeEnter: (to, from, next) => {
            if (Cookies.get("role") === "ADMIN" || Cookies.get("role") === "USER") {
                next();
            } else {
                next({ name: "NotFound" });
            }
        },
    },

    {
        path: "/kw2/approval/create",
        name: "RequestAdminApprove",
        component: RequestAdminApprove,
        beforeEnter: (to, from, next) => {
            if (Cookies.get("role") === "USER" || Cookies.get("role") === "ADMIN") {
                next();
            } else {
                next({ name: "NotFound" });
            }
        },
    },

    {
        path: "/kw2/approval/admin/dashboard",
        name: "AdminAcceptView",
        component: AdminAcceptView,
        beforeEnter: (to, from, next) => {
            if (Cookies.get("role") === "ADMIN") {
                const graphStore = useGraphStore();
                graphStore.getPieInfo().then(r => {
                    graphStore.getWaveInfo().then(r => {
                        next();
                    });
                });
            } else {
                next({ name: "NotFound" });
            }
        },
    },

    {
        path: "/kw2/approval/list/manager-accept",
        name: "ManagerListView",
        component: ManagerListView,
        beforeEnter: (to, from, next) => {
            if (Cookies.get("role") === "MANAGER") {
                next();
            } else {
                next({ name: "NotFound" });
            }
        },
    },

    {
        path: "/kw2/approval/detail/user/:id",
        name: "UserAlvView",
        component: UserAlvView,
        beforeEnter: (to, from, next) => {
            if (Cookies.get("role") === "USER" || Cookies.get("role") === "ADMIN") {
                next();
            } else {
                next({ name: "NotFound" });
            }
        },
    },

    {
        path: "/kw2/approval/detail/admin/:id",
        name: "AdminAlvView",
        component: AdminAlvView,
        beforeEnter: (to, from, next) => {
            if (Cookies.get("role") === "ADMIN") {
                next();
            } else {
                next({ name: "NotFound" });
            }
        },
    },

    {
        path: "/kw2/approval/detail/manager/:id",
        name: "ManagerAlvView",
        component: ManagerAlvView,
        beforeEnter: (to, from, next) => {
            if (Cookies.get("role") === "MANAGER") {
                next();
            } else {
                next({ name: "NotFound" });
            }
        },
    },

    {
        path: "/kw2/approval/status",
        name: "ApproveStatusView",
        component: ApproveStatusView,
        beforeEnter: (to, from, next) => {
            if (
                Cookies.get("role") === "ADMIN" || Cookies.get("role") === "MANAGER"
            ) {
                next();
            } else {
                next({ name: "NotFound" });
            }
        },
    },


    // Not Found
    {
        path: "/:pathMatch(.*)*",
        name: "NotFound",
        component: NotFound,
        beforeEnter: (to, from, next) => {
            if (window.history.length > 1) {
                next(-1); // redirect to previous page
            } else {
                next("/"); // redirect to home page
            }
        },
    },
];

const router = createRouter({
    history: createWebHistory(),
    routes,
});

export default router;

// axios.interceptors.response.use((response) => response, (error) => {
//     if (typeof error.response === 'undefined') {
//         alert('⚠️ ขณะนี้เกิดปัญหาในการเชื่อมต่อเครือข่าย '
//             + 'ปัญหานี้อาจเกิดจาก CORS หรือการขาดการเชื่อมต่ออินเทอร์เน็ต '
//             + 'กรุณาลองเข้าใช้งานใหม่อีกครั้งในภายหลัง ขออภัยในความไม่สะดวก 🙏🏼')
//     }
//     return Promise.reject(error)
// })

router.beforeEach((to, from, next) => {
    const refreshFunctionStore = useRefreshFunctionStore();
    const documentStore = useDocumentListStore();

    if (to.name !== 'Login' && !Cookies.get('accessToken')) {
        next({ name: 'Login' });
    } else {
        refreshFunctionStore.checkAccessToken(token).then(isTokenValid => {
            if (!isTokenValid && to.name !== "Login") {
                alert("เกิดข้อผิดพลาด กรุณาเข้าสู่ระบบใหม่อีกครั้ง")
                next("/kw2/login");
            } else {
                if (to.name !== "Login" && !Cookies.get("accessToken"))
                    next({ name: "Login" });
                else {
                    if (to.name === "ViewDoc") {
                        documentStore.getdocumentFilenameAndUserIdFromAxios(to.params.id).then(r => {
                            next();
                        });
                    } else {
                        next();
                    }
                }
            }
        });
    }
});