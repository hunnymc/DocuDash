<script setup>
import Navbar from "../../components/Navbar.vue";
import UserSidebar from "../../components/user/UserSidebar.vue";
import UserDetail from "../../components/user/UserDetail.vue";
import Footer from "../../components/footer.vue";

</script>

<template>
  <Navbar />
  <div class="flex">
    <UserSidebar />
    <UserDetail />
    
  </div>
  <Footer />
</template>

<style scoped>

</style>