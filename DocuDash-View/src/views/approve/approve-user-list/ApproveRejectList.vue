<script setup>
import Footer from "../../../components/footer.vue";
import NavbarAvl from "../../../components/approves/approve-utility/NavbarAvl.vue";
import SidebarAvl from "../../../components/approves/approve-utility/SidebarAvl.vue";
import ApproveRejectRequestList from "../../../components/approves/approve-user-list/ApproveRejectRequestList.vue";
</script>

<template>
  <NavbarAvl/>
  <div class="flex">
    <SidebarAvl/>
    <ApproveRejectRequestList/>
  </div>
  <Footer/>
</template>

<style scoped></style>