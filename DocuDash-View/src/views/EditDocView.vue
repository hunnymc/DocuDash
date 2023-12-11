<script setup>
import { ref , onMounted , watch } from "vue";
import { onBeforeRouteUpdate, useRoute } from "vue-router";
import Navbar from "../components/Navbar.vue";
import Sidebar from "../components/Sidebar.vue";
import Footer from "../components/footer.vue";
import CreateDoc from "../components/CreateDoc.vue";
import EditDoc from "../components/EditDoc.vue";


</script>

<template>
  <Navbar />
  <div class="flex">
    <Sidebar />
    <EditDoc />
  </div>
  <Footer />
</template>

<style scoped>

</style>