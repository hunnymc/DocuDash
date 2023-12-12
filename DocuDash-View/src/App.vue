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

initFlowbite();

onBeforeMount(() => {
  if (Cookies.get("documents_DocumentID1") == null 
  || Cookies.get("documents_DocumentID1") == undefined
  || Cookies.get("documents_DocumentID1") == ""
  ) {
    Cookies.set("documents_DocumentID1", '1');
  }

  initAccordions();
    initCarousels();
    initCollapses();
    initDials();
    initDismisses();
    initDrawers();
    initDropdowns();
    initModals();
    initPopovers();
    initTabs();
    initTooltips();
});

</script>

<template>
  <router-view  ref="routerViewRef" />
</template>

<style scoped></style>