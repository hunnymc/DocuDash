import {createRouter, createWebHistory} from "vue-router";
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
import Cookies from "js-cookie";

const routes = [
    {path: '/kw2/', redirect: '/kw2/list'},
    {path: '/kw2/list', name: 'AllDocList', component: AllDocList},
    {
        path: '/kw2/list/all', name: 'AdminDocList', component: AdminDocList,
        beforeEnter: (to, from, next) => {
            if (Cookies.get('role') !== 'ADMIN') {
                // alert('You are not authorized to access this page')
                next({name: 'AllDocList'})
            } else {
                next()
            }
        }
    },
    {path: '/kw2/add', name: 'CreateDoc', component: CreateDoc},
    {path: '/kw2/user', name: 'UserInfo', component: UserInfo},
    {
        path: '/kw2/user', name: 'UserInfo', component: UserInfo,
        beforeEnter: (to, from, next) => {
            if (Cookies.get('role') !== 'ADMIN') {
                // alert('You are not authorized to access this page')
                next({name: 'AllDocList'})
            } else {
                next()
            }
        }
    },
    {
        path: '/kw2/view/:id', name: 'ViewDoc', component: ViewDoc,
        beforeEnter: (to, from, next) => {
            if (from.path !== '/kw2/view/:id') {
                next();
            } else {
                next('/kw2/list'); // redirect to /list if not coming from /list or Navbar
            }
        }
    },
    {path: '/kw2/login', name: 'Login', component: Login},
    {path: '/kw2/edit', namr: 'EditDoc', component: EditDoc},
    {path: '/kw2/register', name: 'Register', component: Register},
    {path: '/kw2/chat', name: 'Chat', component: Chat},
    {path: '/kw2/setting', name: 'Setting', component: Setting},
    {
        path: '/kw2/:pathMatch(.*)*', name: 'NotFound', component: NotFound,
        beforeEnter: (to, from, next) => {
            next('/kw2/list'); // redirect to /list if Not Found page
        }
    }
]

const router = createRouter({
    history: createWebHistory(),
    routes
})

export default router


router.beforeEach((to, from, next) => {
    if (to.name !== 'Login' && !Cookies.get('accessToken'))
        next({name: 'Login'})

    else next()

})