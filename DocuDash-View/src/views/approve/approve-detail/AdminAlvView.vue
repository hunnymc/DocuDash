<script setup>
import NavbarAvl from "../../../components/approves/approve-utility/NavbarAvl.vue";
import SidebarAvl from "../../../components/approves/approve-utility/SidebarAvl.vue";
import ApproveAdminDetail from "../../../components/approves/approve-detail/ApproveAdminDetail.vue";
import Footer from "../../../components/footer.vue";

</script>

<template>

  <NavbarAvl/>
  <div class="flex">
    <SidebarAvl/>
    <ApproveAdminDetail/>
  </div>
  <Footer/>

</template>

<style scoped>

</style>