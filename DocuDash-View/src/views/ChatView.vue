<script setup>
import Navbar from "../components/Navbar.vue";
import Sidebar from "../components/Sidebar.vue";
import Footer from "../components/footer.vue";
import Chat from "../components/Chat.vue";

</script>

<template>
  <Navbar />
  <div class="flex">
    <Sidebar />
    <Chat />

  </div>
  <Footer />
</template>

<style scoped>

</style>