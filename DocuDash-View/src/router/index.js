import { createRouter, createWebHistory } from "vue-router";
import Home from "../views/Home.vue";
import AllDocList from "../views/AllDocList.vue";
import CreateDoc from "../views/CreateDoc.vue";
import UserInfo from "../views/UserInfo.vue";
import ViewDoc from "../views/ViewDocView.vue";
import Login from "../views/LoginView.vue";
import EditDoc from "../views/EditDocView.vue";
import Register from "../views/RegisterView.vue";
import NotFound from "../views/NotFoundView.vue";
import Chat from "../views/ChatView.vue";
import Cookies from "js-cookie";

const routes = [
    { path: '/', redirect: '/list'},
    { path: '/list', name: 'AllDocList', component: AllDocList },
    { path: '/add', name: 'CreateDoc', component: CreateDoc },
    { path: '/user', name: 'UserInfo', component: UserInfo },
    // { path: '/user', name: 'UserInfo', component: UserInfo ,
    //     beforeEnter: (to, from, next) => {
    //         if (Cookies.get('role') !== 'ADMIN') {
    //             alert('You are not authorized to access this page')
    //             next({ name: 'AllDocList' })
    //         } else {
    //             next()
    //         }
    //     }
    // },
    { path: '/view/:id', name: 'ViewDoc', component: ViewDoc },
    { path: '/login', name: 'Login', component: Login },
    { path: '/edit', namr: 'EditDoc', component: EditDoc },
    { path: '/register', name: 'Register', component: Register},
    { path: '/chat', name: 'Chat', component: Chat},
    { path: '/:pathMatch(.*)*', name: 'NotFound', component: NotFound }
]

const router = createRouter({
    history: createWebHistory(),
    routes
})

export default router


// router.beforeEach((to, from, next) => {
//     if (to.name !== 'Login' && !Cookies.get('accessToken')) 
//     next({ name: 'Login' })

//     else next()

// })