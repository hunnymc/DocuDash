import {createApp} from 'vue'
import App from "./App.vue";
import router from "./router";
import { createPinia } from 'pinia'
import './style.css'
import 'animate.css'

const pinia = createPinia()

createApp(App)
    .use(router)
    .use(pinia)
    .mount('#app')
