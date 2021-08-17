<template>
  <div class="name">
    {{ username }}
  </div>
  <div>
    <div class="tac row">
      <div class="col-sm-4 col-md-3 col-12" id="admin-sidebar">
        <adminpage-sidebar :pathname="pathname"/>
      </div>
      <div class="col-sm-8 col-md-9 col-12">
        <div class="view-section">
          <router-view />
        </div>
      </div>
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
}

@media (min-width: 576px) {
  #admin-sidebar {
    border-right: solid 1.2px #ebeef5;
  }
}
</style>
