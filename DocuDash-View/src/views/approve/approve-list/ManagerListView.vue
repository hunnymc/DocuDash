<script setup>
import NavbarAvl from "../../../components/approves/approve-utility/NavbarAvl.vue";
import SidebarAvl from "../../../components/approves/approve-utility/SidebarAvl.vue";
import ManagerApprove from "../../../components/approves/approve-manager-list/ManagerApprove.vue";
</script>

<template>

  <NavbarAvl/>
  <div class="flex">
    <SidebarAvl/>
    <ManagerApprove/>
  </div>
  <Footer/>

</template>

<style scoped>

</style>