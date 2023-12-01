<script setup>
import Navbar from "./components/Navbar.vue";
import Sidebar from "./components/Sidebar.vue";
import ListTable from "./components/list-table.vue";
</script>

<template>
  <Navbar />
  <div class="flex">
    <Sidebar />
    <ListTable />
  </div>
</template>

<style scoped>

</style>