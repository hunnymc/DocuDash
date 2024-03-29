<script setup>
import Footer from "../../../components/footer.vue";
import NavbarAvl from "../../../components/approves/approve-utility/NavbarAvl.vue";
import SidebarAvl from "../../../components/approves/approve-utility/SidebarAvl.vue";
import ApprovePendingRequestList from "../../../components/approves/approve-user-list/ApprovePendingRequestList.vue";
</script>

<template>
  <NavbarAvl/>
  <div class="flex">
    <SidebarAvl/>
    <ApprovePendingRequestList />
  </div>
  <Footer/>
</template>

<style scoped></style>