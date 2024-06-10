<script setup>
import Navbar from "../../components/Navbar.vue";
import Sidebar from "../../components/Sidebar.vue";
import ApprovePendingRequestList from "../../components/approves/approve-user-list/ApprovePendingRequestList.vue";
</script>

<template>
  <Navbar />
  <div class="flex">
    <Sidebar />
    <ApprovePendingRequestList />

  </div>

  <Footer />
</template>

<style scoped>

</style>