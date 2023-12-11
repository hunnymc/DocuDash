import {createRouter, createWebHistory} from "vue-router";
import Home from "../views/Home.vue";
import AllDocList from "../views/AllDocList.vue";
import CreateDoc from "../views/CreateDoc.vue";
import UserInfo from "../views/UserInfo.vue";
import ViewDoc from "../views/ViewDocView.vue";
import Login from "../views/LoginView.vue";
import EditDoc from "../views/EditDocView.vue";

const routes = [
    {path: '/', redirect: '/list'},
    {path: '/list', name: 'AllDocList', component: AllDocList},
    {path: '/add', name: 'CreateDoc', component: CreateDoc},
    {path: '/user', name: 'UserInfo', component: UserInfo},
    {path: '/view/:id', name: 'ViewDoc', component: ViewDoc},
    {path: '/login', name: 'Login', component: Login},
    {path: '/edit', namr: 'EditDoc', component: EditDoc}
]

const router = createRouter({
    history: createWebHistory(),
    routes
})

export default router