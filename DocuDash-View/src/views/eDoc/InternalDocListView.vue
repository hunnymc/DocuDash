<script setup>
import { ref , onMounted, inject } from "vue";

import Navbar from "../../components/Navbar.vue";
import Footer from "../../components/footer.vue";
import Sidebar from "../../components/Sidebar.vue";
import InternalDocList from "../../components/eDocument/InternalDocList.vue";

</script>

<template>
  <Navbar />
  <div class="flex">
    <Sidebar />
    <InternalDocList />

  </div>
  <Footer />
</template>

<style scoped>

</style>