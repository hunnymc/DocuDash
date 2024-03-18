<script setup>
import NavbarAvl from "../../../components/approves/approve-utility/NavbarAvl.vue";
import SidebarAvl from "../../../components/approves/approve-utility/SidebarAvl.vue";
import ApproveManagerDetail from "../../../components/approves/approve-detail/ApproveManagerDetail.vue";
</script>

<template>

  <NavbarAvl/>
  <div class="flex">
    <SidebarAvl/>
    <ApproveManagerDetail/>
  </div>
  <Footer/>

</template>

<style scoped>

</style>