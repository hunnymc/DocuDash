<script setup>
import { ref , onMounted, inject } from "vue";
import Navbar from "../components/Navbar.vue";
import Sidebar from "../components/Sidebar.vue";
import AllDocList from "../components/AllDocList.vue";
import Footer from "../components/footer.vue";

</script>

<template>
  <Navbar />
  <div class="flex">
    <Sidebar />
    <AllDocList />
    
  </div>
  <Footer />
</template>

<style scoped>

</style>