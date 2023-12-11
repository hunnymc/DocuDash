<script setup>
import { ref , onMounted, inject } from "vue";
import Navbar from "../components/Navbar.vue";
import Sidebar from "../components/Sidebar.vue";
import ListTable from "../components/list-table.vue";
import Footer from "../components/footer.vue";

</script>

<template>
  <Navbar />
  <div class="flex">
    <Sidebar />
    <ListTable/>
  </div>
  <Footer />
</template>

<style scoped>

</style>