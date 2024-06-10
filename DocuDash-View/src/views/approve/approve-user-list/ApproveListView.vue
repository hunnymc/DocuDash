<script setup>
import NavbarAvl from "../../../components/approves/approve-utility/NavbarAvl.vue";
import SidebarAvl from "../../../components/approves/approve-utility/SidebarAvl.vue";
import ApproveList from "../../../components/approves/approve-user-list/ApproveList.vue";
import Footer from "../../../components/footer.vue";
</script>

<template>
  <NavbarAvl/>
  <div class="flex">
    <SidebarAvl/>
    <ApproveList/>
  </div>
  <Footer/>
</template>

<style scoped></style>
