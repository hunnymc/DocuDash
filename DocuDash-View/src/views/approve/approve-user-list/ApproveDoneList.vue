<script setup>
import Footer from "../../../components/footer.vue";
import NavbarAvl from "../../../components/approves/approve-utility/NavbarAvl.vue";
import SidebarAvl from "../../../components/approves/approve-utility/SidebarAvl.vue";
import ApproveDoneRequestList from "../../../components/approves/approve-user-list/ApproveDoneRequestList.vue";
</script>

<template>
  <NavbarAvl/>
  <div class="flex">
    <SidebarAvl/>
    <ApproveDoneRequestList/>
  </div>
  <Footer/>
</template>

<style scoped></style>