<template>
  <el-row
    class="main-sidebar"
    :gutter="10"
    :style="{ 'width': width }">
    <div class="hide-on-small">
      <el-menu
        v-if="state.isLogined"
        :default-active="String(state.activeIndex)"
        active-text-color="#ffd04b"
        class="el-menu-vertical-demo"
        @select="menuSelect">
        <el-menu-item v-for="(item, index) in state.menuItems" :key="index" :index="index.toString()" @click="openFullScreenLoading()" v-loading.fullscreen.lock="fullscreenLoading">
          <i v-if="item.icon" :class="['ic', item.icon]"/>
          <span>{{ item.title }}</span>
        </el-menu-item>
        <el-menu-item @click="logout">
          <i class="ic el-icon-switch-button"></i>
          <span>로그아웃</span>
        </el-menu-item>
      </el-menu>
      <el-menu v-else>
        <el-menu-item route="/">
          <i class="ic el-icon-s-home"></i>
          <span>홈</span>
        </el-menu-item>
      </el-menu>
    </div>
  </el-row>
</template>
<style>
.main-sidebar .el-menu {
  margin-top: 0;
  padding-left: 0;
}
.main-sidebar .hide-on-small {
  height: 100%;
}
.main-sidebar .hide-on-small .el-menu {
  height: 100%;
}
.main-sidebar .el-menu .el-menu-item {
  cursor: pointer;
  border-right: none;
}
.main-sidebar .el-menu .el-menu-item .ic {
  margin-right: 5px;
}
</style>
<script>
import { reactive, computed } from 'vue'
import { useStore } from 'vuex'
import { useRouter } from 'vue-router'

export default {
  name: 'main-header',
// ========= Loading Spinner 생성하는 부분 start
  data() {
    return {
      fullscreenLoading: false
    };
  },
  methods: {
    openFullScreenLoading() {
      const loading = this.$loading({
          lock: true,
          text: 'Loading',
          spinner: 'el-icon-loading',
          background: 'rgba(0, 0, 0, 0.7)'
        });
        setTimeout(() => {
          loading.close();
        }, 1000);
    },
  },
  // ========= Loading Spinner 생성하는 부분 end
  props: {
    width: {
      type: String,
      default: '240px'
    },
  },
  setup(props) {
    const store = useStore()
    const router = useRouter()
    const state = reactive({
      searchValue: null,
      menuItems: computed(() => {
        const MenuItems = store.getters['root/getMenus']
        let keys = Object.keys(MenuItems)
        let menuArray = []
        for (let i = 0; i < keys.length; ++i) {
          let menuObject = {}
          menuObject.icon = MenuItems[keys[i]].icon
          menuObject.title = MenuItems[keys[i]].name
          menuArray.push(menuObject)
        }
        return menuArray
      }),
      activeIndex: computed(() => store.getters['root/getActiveMenuIndex']),
      isLogined: computed(() =>
        store.getters['root/getIsLogined']
      )
    })

    if (state.activeIndex === -1) {
      state.activeIndex = 0
      store.commit('root/setMenuActive', 0)
    }

    const menuSelect = function (param) {
      store.commit('root/setMenuActive', param)
      const MenuItems = store.getters['root/getMenus']
      let keys = Object.keys(MenuItems)
      router.push({
        name: keys[param]
      })
    }

    const logout = function () {
      console.log("logout clicked!!")
      localStorage.removeItem('jwt-auth-token')
      store.commit('root/setIsLogined', false)
      router.push('/')
    }
    console.log("main-sidebar isLogined : " + state.isLogined)

    return { state, menuSelect, logout }
  }
}
</script>
