import { createApp } from 'vue'
import App from "./App.vue";
import router from "./router";
import { createPinia } from 'pinia'
import piniaPluginPersistedstate from 'pinia-plugin-persistedstate'
import { useDocumentListStore } from './stores/listOfDocumentStore'
import { useRefreshFunctionStore } from "./stores/RefeshFunctionStore.js";
import { useLoginUserStore } from './stores/LoginUserStore.js';
import VueApexCharts from "vue3-apexcharts";
import PrimeVue from 'primevue/config';
import Lara from '../src/presets/lara';
import Wind from '../src/presets/wind';
import '../node_modules/flowbite-vue/dist/index.css'
import './style.css'
import 'animate.css'
import { useGraphStore } from "./stores/GraphStore.js";

const pinia = createPinia()

pinia.use(piniaPluginPersistedstate)

createApp(App)
    .use(router)
    .use(pinia)
    .use(VueApexCharts)
    .use(PrimeVue,
        {
            unstyled: true,
            pt: Wind
          }
        )
    .mount('#app')

const documentStore = useDocumentListStore()
const refreshFunctionStore = useRefreshFunctionStore()
const graphStore = useGraphStore()
const loginUserStore = useLoginUserStore()