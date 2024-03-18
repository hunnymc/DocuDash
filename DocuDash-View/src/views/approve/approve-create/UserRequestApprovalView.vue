<script setup>
import NavbarAvl from "../../../components/approves/approve-utility/NavbarAvl.vue";
import SidebarAvl from "../../../components/approves/approve-utility/SidebarAvl.vue";
import RequestAdminApproval from "../../../components/approves/approve-create/CreateApproval.vue";
</script>

<template>
  <NavbarAvl/>
  <div class="flex">
    <SidebarAvl/>
    <RequestAdminApproval/>
  </div>
  <Footer/>
</template>

<style scoped></style>
