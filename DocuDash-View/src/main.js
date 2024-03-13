import {createApp} from 'vue'
import App from "./App.vue";
import router from "./router";
import { createPinia } from 'pinia'
import piniaPluginPersistedstate from 'pinia-plugin-persistedstate'
import { useDocumentListStore } from './stores/listOfDocumentStore'
import './style.css'
import 'animate.css'

const pinia = createPinia()

pinia.use(piniaPluginPersistedstate)


createApp(App)
    .use(router)
    .use(pinia)
    .mount('#app')

const documentStore = useDocumentListStore()
