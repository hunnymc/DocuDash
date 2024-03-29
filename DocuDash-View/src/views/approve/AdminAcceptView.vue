<script setup>
import NavbarAvl from "../../components/approves/approve-utility/NavbarAvl.vue";
import SidebarAvl from "../../components/approves/approve-utility/SidebarAvl.vue";
import AdminAcceptPage from "../../components/approves/approve-admin-list/AdminDashboard.vue";
</script>

<template>

  <NavbarAvl/>
  <div class="flex">
    <SidebarAvl/>
    <AdminAcceptPage/>
  </div>
  <Footer/>

</template>

<style scoped>

</style>