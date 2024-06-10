<script setup>
import { ref , onMounted, inject } from "vue";
import Navbar from "../components/Navbar.vue";
import UserSidebar from "../components/user/UserSidebar.vue";
import ListTable from "../components/list-table.vue";
import Register from "../components/Register.vue";
import Footer from "../components/footer.vue";

</script>

<template>
  <Navbar />
  <div class="flex">
    <UserSidebar />
    <Register />
  </div>
  <Footer />
</template>

<style scoped>

</style>