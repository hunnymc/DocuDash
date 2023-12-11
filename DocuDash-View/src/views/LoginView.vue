<script setup>
import { ref , onMounted } from "vue";
import { onBeforeRouteUpdate } from "vue-router";

import Navbar from "../components/Navbar.vue";
import Sidebar from "../components/Sidebar.vue";
import Footer from "../components/footer.vue";
import Login from "../components/Login.vue";

</script>

<template>

    <Login />

</template>

<style scoped>

</style>