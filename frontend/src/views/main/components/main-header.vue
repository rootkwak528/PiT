<template>
  <el-row class="main-header" :gutter="10" :style="{ height: height }">
    <div class="hide-on-small">
      <div class="logo-wrapper">
        <router-link to="/">
          <div class="ic ic-logo" />
        </router-link>
      </div>
      <div class="search-field">
        <el-select
          v-model="selectValue"
          placeholder="Select"
          style="margin-right: 5px"
        >
          <el-option>전체 검색</el-option>
          <el-option>클래스 명 검색</el-option>
          <el-option>강사 명 검색</el-option>
        </el-select>
        <el-input placeholder="검색" v-model="state.searchValue"></el-input>
        <el-button
          icon="el-icon-search"
          @click="search"
          id="searchBtn"
        ></el-button>
        <el-button
          type="text"
          @click="clickSearchDetail"
          class="searchDetailOption"
          >상세 검색 조건</el-button
        >
      </div>
      <div
        class="button-wrapper"
        v-if="!state.isLogined"
        style="margin-top:10px;"
      >
        <div class="btn-notlogined" @click="clickLogin">
          로그인
        </div>
        <div class="btn-notlogined" @click="clickJoin">
          회원가입
        </div>
      </div>
      <div class="button-wrapper" v-else>
        <div type="primary" @click="mvCart" class="button-wrapper-logined">
          장바구니
        </div>
        <div type="primary" class="button-wrapper-logined">
          <router-link
            to="/rgstClass"
            style="text-decoration: none; color: inherit;"
            >마이클래스</router-link
          >
        </div>
        <el-dropdown trigger="click">
          <span class="el-dropdown-link">
            <div class="profile">
              <img
              v-if="state.profileUrl"
              id="profileImg"
              :src="state.profileUrl"
              alt="profile"
              style="width:100%; max-width:200px; height: 40px; cursor: pointer; border-radius: 50%;"
            />
            <img
              v-else
              :src="require('@/assets/images/profile-default.png')"
              alt="profile"
              style="width:100%; max-width:200px; height: 40px; cursor: pointer; border-radius: 50%;"
            />
            </div>
          </span>
          <template #dropdown>
            <el-dropdown-menu>
              <!-- <el-dropdown-item
                icon="el-icon-s-custom"
                style="font:blue; cursor: pointer;"
              >
                <router-link
                  to="/rgstClass"
                  style="text-decoration: none; color: inherit;"
                  >마이페이지</router-link
                >
              </el-dropdown-item> -->
              <!-- 테스트 마이 페이지 -->
              <el-dropdown-item
                icon="el-icon-s-custom"
                style="font:blue; cursor: pointer;"
              >
                <router-link
                  to="/rgstClass"
                  style="text-decoration: none; color: inherit;"
                  >마이페이지</router-link
                >
              </el-dropdown-item>

              <el-dropdown-item icon="el-icon-turn-off" @click="logout"
                >로그아웃</el-dropdown-item
              >
              <el-dropdown-item icon="el-icon-bell" @click="checkAlert"
                >피드백 알림</el-dropdown-item
              >
            </el-dropdown-menu>
          </template>
        </el-dropdown>
      </div>
    </div>
  </el-row>
</template>
<script>
import { reactive, computed, onMounted } from "vue";
import { useStore } from "vuex";
import { useRouter } from "vue-router";
import SearchDialog from "./search-dialog.vue";

export default {
  name: "main-header",
  components: {
    SearchDialog
  },
  data() {
    return {
      fullscreenLoading: false,
      searchDialogVisible: false
    };
  },
  methods: {
    openFullScreenLoading() {
      const loading = this.$loading({
        lock: true,
        text: "Loading",
        spinner: "el-icon-loading",
        background: "rgba(0, 0, 0, 0.7)"
      });
      setTimeout(() => {
        loading.close();
      }, 1000);
    }
  },
  props: {
    height: {
      type: String,
      default: "70px"
    }
  },

  setup(props, { emit }) {
    const store = useStore();
    const router = useRouter();
    const state = reactive({
      searchValue: null,
      isCollapse: true,
      menuItems: computed(() => {
        const MenuItems = store.getters["root/getMenus"];
        let keys = Object.keys(MenuItems);
        let menuArray = [];
        for (let i = 0; i < keys.length; ++i) {
          let menuObject = {};
          menuObject.icon = MenuItems[keys[i]].icon;
          menuObject.title = MenuItems[keys[i]].name;
          menuArray.push(menuObject);
        }
        return menuArray;
      }),
      activeIndex: computed(() => store.getters["root/getActiveMenuIndex"]),
      isLogined: computed(() => store.getters["root/getIsLogined"]),
      profileUrl: computed(() => store.getters["root/getProfileUrl"])
    });

    if (state.activeIndex === -1) {
      state.activeIndex = 0;
      store.commit("root/setMenuActive", 0);
    }

    const search = () => {
      if (state.searchValue === null) {
        alert("검색어를 입력해주세요.");
      } else {
        router.push("/searchresult?word=" + state.searchValue);
      }
    };

    const clickLogin = () => {
      emit("openLoginDialog");
    };

    const clickJoin = () => {
      emit("openJoinDialog");
    };

    const clickSearchDetail = () => {
      emit("openSearchDialog");
      console.log("검색 버튼 열려라참깨..");
      //this.searchDialogVisible = true;
    };

    const changeCollapse = () => {
      state.isCollapse = !state.isCollapse;
    };

    const logout = function() {
      localStorage.removeItem("jwt-auth-token");
      store.commit("root/setIsLogined", false);
      //store.state.isLogined = false;
      console.log("logout clicked!! : " + store.state.isLogined);
      router.push("/");
    };

    const mvCart = function() {
      alert("장바구니 클릭1111!");
    };

    const mvMyclass = function() {
      alert("마이클래스 클릭!");
    };

    const checkAlert = function() {
      alert("피드백 알림 확인");
    };

    const requestUserInfo = function() {};

    return {
      state,
      search,
      clickLogin,
      clickJoin,
      clickSearchDetail,
      changeCollapse,
      logout,
      mvCart,
      mvMyclass,
      checkAlert,
      requestUserInfo
    };
  }
};
</script>
<style>
.main-header {
  padding: 15px 20px;
}
/*Mobile, Tablet*/
.menu-icon-wrapper {
  display: inline-block;
  vertical-align: top;
  position: relative;
  top: 14px;
}

.main-header .hide-on-small .logo-wrapper .ic.ic-logo {
  width: 70px;
  height: 50px;
  background-size: contain;
  background-repeat: no-repeat;
  background-image: url("../../../assets/images/MainLogo.png");
}

#searchBtn {
  padding-top: 0px;
  padding-bottom: 0px;
  max-height: 40px;
  margin-left: 5px;
}

.hide-on-small {
  display: flex;
  justify-content: space-between;
}

.search-field {
  display: flex;
}

.button-wrapper {
  display: flex;
  justify-content: end;
}

.btn-notlogined {
  margin-left: 15px;
  cursor: pointer;
}

.button-wrapper-logined {
  float: left;
  margin-right: 30px;
  cursor: pointer;
  margin-top: 10px;
}
.profile {
  text-align: center;
  width: 40px;
  height: 40px;
}
img {
  max-width: 100%;
  height: auto;
}
</style>
