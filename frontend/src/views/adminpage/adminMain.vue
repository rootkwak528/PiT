<template>
  <div class="name">
    {{ username }}
  </div>
  <div>
    <!-- <el-row class="tac"> -->
    <div class="tac row">
      <!-- <el-col :span="6"> -->
      <div class="col-sm-4 col-md-3 col-12" id="admin-sidebar">
        <adminpage-sidebar :pathname="pathname"/>
      <!-- </el-col> -->
      </div>
      <!-- <el-col :span="18" style="min-height: 655px"> -->
      <div class="col-sm-8 col-md-9 col-12">
        <div class="view-section">
          <router-view />
        </div>
      <!-- </el-col> -->
      </div>
    <!-- </el-row> -->
    </div>
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

@media (max-width: 576px) {
  #admin-sidebar {
    border-bottom: solid 1.2px #ebeef5;
    margin-bottom: 35px;
  }

  /* #admin-sidebar-content .admin-content-wrapper {
    margin: 0;
  } */
}

@media (min-width: 576px) {
  #admin-sidebar {
    border-right: solid 1.2px #ebeef5;
  }
}
</style>
