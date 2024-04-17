<script setup>
import Footer from "../../components/footer.vue";
import Sidebar from "../../components/Sidebar.vue";
import Navbar from "../../components/Navbar.vue";
import ExternalDocList from "../../components/eDocument/ExternalDocList.vue";
</script>

<template>
  <Navbar />
  <div class="flex">
    <Sidebar />
    <ExternalDocList />

  </div>
  <Footer />
</template>

<style scoped>

</style>