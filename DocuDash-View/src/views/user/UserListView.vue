<script setup>
import Navbar from "../../components/Navbar.vue";
import UserSidebar from "../../components/user/UserSidebar.vue";
import UserList from "../../components/user/UserList.vue";

</script>

<template>
  <Navbar />
  <div class="flex">
    <UserSidebar />
    <UserList/>

  </div>
  <Footer />
</template>

<style scoped>

</style>