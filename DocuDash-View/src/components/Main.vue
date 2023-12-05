<template>
    <div class="flex flex-col h-screen">
        <Navbar />
        <div class="flex flex-row flex-1">
        <Sidebar />
        <Content />
        </div>
    </div>
</template>

<script setup>
import Navbar from './Navbar.vue'
import Sidebar from './Sidebar.vue'
import Content from './Content.vue'
</script>

<style scoped>

</style>