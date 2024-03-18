<script setup>
import ApproveStatus from "../../../components/approves/approve-status-list/ApproveStatus.vue";
import Footer from "../../../components/footer.vue";
import NavbarAvl from "../../../components/approves/approve-utility/NavbarAvl.vue";
import SidebarAvl from "../../../components/approves/approve-utility/SidebarAvl.vue";
</script>

<template>
  <NavbarAvl/>
  <div class="flex">
    <SidebarAvl/>
    <ApproveStatus/>
  </div>
  <Footer/>
</template>

<style scoped></style>