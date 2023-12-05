<script setup>
import Navbar from "./components/Navbar.vue";
import Sidebar from "./components/Sidebar.vue";
import ListTable from "./components/list-table.vue";
import Footer from "./components/footer.vue";
import CreateDoc from "./components/CreateDoc.vue";
import ViewDoc from "./components/ViewDoc.vue";
</script>

<template>
  <Navbar />
  <div class="flex">
    <Sidebar />
    <!-- <ListTable /> -->
    <!-- <CreateDoc /> -->
    <ViewDoc />
  </div>
  <Footer />
</template>

<style scoped>

</style>