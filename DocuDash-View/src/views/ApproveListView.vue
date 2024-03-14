<script setup>
import { ref, onMounted, inject } from "vue";
import NavbarAvl from "../components/approves/NavbarAvl.vue";
import SidebarAvl from "../components/approves/SidebarAvl.vue";
import ApproveList from "../components/approves/ApproveList.vue";
import Footer from "../components/footer.vue";
</script>

<template>
  <NavbarAvl />
  <div class="flex">
    <SidebarAvl />
    <ApproveList />
  </div>
  <Footer />

</template>

<style scoped></style>
