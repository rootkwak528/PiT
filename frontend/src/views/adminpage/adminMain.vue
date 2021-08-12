<template>
  <div class="name">
    {{ username }}
  </div>
  <div>
    <el-row class="tac">
      <el-col :span="6">
        <adminpage-sidebar :pathname="pathname"/>
      </el-col>
      <el-col :span="18" style="min-height: 655px">
        <div class="view-section">
          <router-view />
        </div>
      </el-col>
    </el-row>
  </div>
</template>
<script>
import AdminpageSidebar from "../adminpage/sidebar/adminpage-sidebar.vue"

export default {
  name: "adminPage",
  components:{
    AdminpageSidebar
  },
  computed: {
    pathname() {
      return(window.location.pathname.slice(1));
    }
  }
}
</script>
<style>
.view-section {
  height: 100%;
}
</style>
