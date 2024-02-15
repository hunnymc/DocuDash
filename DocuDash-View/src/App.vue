<script setup>
import { onMounted, ref , defineComponent, onBeforeMount} from "vue";
import { useRoute, useRouter } from "vue-router";
import Navbar from "./components/Navbar.vue";
import Cookies from "js-cookie";
import { initFlowbite, initAccordions, 
    initCarousels, 
    initCollapses, 
    initDials, 
    initDismisses, 
    initDrawers, 
    initDropdowns, 
    initModals, 
    initPopovers, 
    initTabs, 
    initTooltips  } from 'flowbite'

// initFlowbite();

// onMounted(() => {
//   initAccordions();
//     initCarousels();
//     initCollapses();
//     initDials();
//     initDismisses();
//     initDrawers();
//     initDropdowns();
//     initModals();
//     initPopovers();
//     initTabs();
//     initTooltips();
// });

onMounted(() => {
    initFlowbite();
})

</script>

<template>
  <router-view  ref="routerViewRef" />
</template>

<style scoped></style>