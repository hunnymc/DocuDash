<script setup>
import { onBeforeMount, onBeforeUnmount } from 'vue';
import { useRouter, useRoute } from 'vue-router';
import Navbar from "../components/Navbar.vue";
import Sidebar from "../components/Sidebar.vue";
import Footer from "../components/footer.vue";
import ViewDoc from "../components/ViewDoc.vue";

const router = useRouter();
const route = useRoute();

// onBeforeMount(() => {
//   if (localStorage.getItem('refreshed') && route.path.startsWith('/view')) {
//     localStorage.removeItem('refreshed');
//     router.push('/list');
//   } else {
//     localStorage.setItem('refreshed', true);
//   }
// });

</script>

<template>
  <Navbar />
  <div class="flex">
    <Sidebar />
    <ViewDoc />
  </div>
  <Footer />
</template>

<style scoped>

</style>