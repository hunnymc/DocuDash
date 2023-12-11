<script setup>
import { ref , onMounted } from "vue";
import { onBeforeRouteUpdate } from "vue-router";

import Navbar from "../components/Navbar.vue";
import Sidebar from "../components/Sidebar.vue";
import Footer from "../components/footer.vue";
import ViewDoc from "../components/ViewDoc.vue";

let key = ref(0);

const refresh = () => {
  key.value++;
};

</script>

<template>
  <Navbar />
  <div class="flex">
    <Sidebar />
    <ViewDoc />
  </div>
  <Footer />
</template>

<style scoped>

</style>