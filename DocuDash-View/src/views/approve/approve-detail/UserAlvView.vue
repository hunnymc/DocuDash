<script setup>
import NavbarAvl from "../../../components/approves/approve-utility/NavbarAvl.vue";
import SidebarAvl from "../../../components/approves/approve-utility/SidebarAvl.vue";
import ApproveUserDetail from "../../../components/approves/approve-detail/ApproveUserDetail.vue";
</script>

<template>

  <NavbarAvl/>
  <div class="flex">
    <SidebarAvl/>
    <ApproveUserDetail/>
  </div>
  <Footer/>

</template>

<style scoped>

</style>