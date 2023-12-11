<script setup>
import { ref , onMounted } from "vue";
import { onBeforeRouteUpdate } from "vue-router";

import Navbar from "../components/Navbar.vue";
import Sidebar from "../components/Sidebar.vue";
import Footer from "../components/footer.vue";
import UserInfo from "../components/UserInfo.vue";

</script>

<template>
  <Navbar />
  <div class="flex">
    <Sidebar />
    <UserInfo />
    
  </div>
  <Footer />
</template>

<style scoped>

</style>