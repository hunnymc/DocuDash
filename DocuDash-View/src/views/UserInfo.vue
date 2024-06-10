<script setup>
import { useLoginUserStore } from "../stores/LoginUserStore";

import Navbar from "../components/Navbar.vue";
import Sidebar from "../components/Sidebar.vue";
import Footer from "../components/footer.vue";
import UserInfo from "../components/UserInfo.vue";

// approval tab
import SidebarAvl from "../components/approves/approve-utility/SidebarAvl.vue";
import NavbarAvl from "../components/approves/approve-utility/NavbarAvl.vue";

</script>

<template>
  <div v-if="useLoginUserStore().previousPath == 1">
    <Navbar />
    <div class="flex">
      <Sidebar />
      <UserInfo />
    </div>
  </div>

  <div v-else-if="useLoginUserStore().previousPath == 2">
    <NavbarAvl />
    <div class="flex">
      <SidebarAvl />
      <UserInfo />
    </div>
  </div>

  <div v-else>
    <Navbar />
    <div class="flex">
      <Sidebar />
      <UserInfo />
    </div>
  </div>

  <Footer />
</template>

<style scoped></style>