<script setup>
import { ref , onMounted } from "vue";
import Navbar from "../components/Navbar.vue";
import Sidebar from "../components/Sidebar.vue";
import Footer from "../components/footer.vue";
import Chat from "../components/Chat.vue";

let key = ref(0);

const refresh = () => {
  key.value++;
};


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