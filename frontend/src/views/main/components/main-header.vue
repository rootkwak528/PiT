<template>
  <el-row class="main-header" :gutter="10" :style="{ height: height }">
    <div class="main-navbar">
      <div class="logo-wrapper">
        <router-link to="/">
          <div class="ic ic-logo" />
        </router-link>
      </div>
      <div class="search-field">
        <el-select
          v-model="state.searchSelect"
          placeholder="전체 검색"
          style="margin-right: 5px; margin-left: 15px; width: 250px"
        >
          <el-option value="전체 검색">전체 검색</el-option>
          <el-option value="클래스 명 검색">클래스 명 검색</el-option>
          <el-option value="강사 명 검색">강사 명 검색</el-option>
        </el-select>
        <el-input placeholder="검색" v-model="state.searchKeyword"></el-input>
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
        <div type="primary" @click="mvFavoriteClass" class="button-wrapper-logined">
          찜목록
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
              <el-dropdown-item
                icon="el-icon-s-custom"
                style="font:blue; cursor: pointer;"
              >
                <router-link
                  to="/myinfo"
                  style="text-decoration: none; color: inherit;"
                  >마이페이지</router-link
                >
              </el-dropdown-item>

              <!-- 민영 수정 시작 -->
              <el-dropdown-item
                icon="el-icon-notebook-1"
                class="small-button-wrapper-logined"
                style="font:blue; cursor: pointer;"
              >
                <router-link
                to="/rgstClass"
                style="text-decoration: none; color: inherit;"
                >마이클래스</router-link
              >
              </el-dropdown-item>

              <el-dropdown-item
                icon="el-icon-star-on"
                class="small-button-wrapper-logined"
                style="font:blue; cursor: pointer;"
              >
                <span type="primary"
                @click="mvFavoriteClass"
                style="text-decoration: none; color: inherit;"
                >찜목록
                </span
              >
              </el-dropdown-item>
              <!-- 민영 수정 끝 -->

              <el-dropdown-item
                v-if="state.userType === '관리자'"
                icon="el-icon-s-custom"
                style="font:blue; cursor: pointer;"
              >
                <router-link
                  to="/adminClass"
                  style="text-decoration: none; color: inherit;"
                  >관리자 페이지</router-link
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
      searchSelect: "",
      searchType: computed(() => store.getters["root/getSearchType"]),
      searchKeyword: "",
      classType: computed(() => store.getters["root/getClassType"]),
      classLevel: computed(() => store.getters["root/getClassLevel"]),
      classDay: computed(() => store.getters["root/getClassDay"]),
      classStartTime: computed(() => store.getters["root/getClassStartTime"]),
      classEndTime: computed(() => store.getters["root/getClassEndTime"]),

      isLogined: computed(() => store.getters["root/getIsLogined"]),
      profileUrl: computed(() => store.getters["root/getProfileUrl"]),
      userType: computed(() => store.getters["root/getUserType"]),
      userNickname: computed(() => store.getters["root/getUserNickname"]),
    });

    const search = () => {
      // console.log("변환 전 state.searchType : " + state.searchSelect);
      // searchType 변환 처리 전체 : 0 , 클래스명 : 1, 강사명 : 2
      if ( state.searchSelect == "전체 검색" ) {
        store.commit("root/setSearchType", "0");
      }
      else if ( state.searchSelect == "클래스 명 검색" ) {
        store.commit("root/setSearchType", "1");
      }
      else if ( state.searchSelect == "강사 명 검색" ) {
        store.commit("root/setSearchType", "2");
      }

      store.commit("root/setSearchKeyword", state.searchKeyword);

      console.log("searchType : " + state.searchType);
      console.log("searchKeyword : " + state.searchKeyword);
      console.log("classType : " + state.classType)
      console.log("classLevel : " + state.classLevel)
      console.log("classDay : " + state.classDay)
      console.log("classStartTime : " + state.classStartTime)
      console.log("classEndTime : " + state.classEndTime)

      getSearchClassList()
      // 이미 스토어에 필요한 값들은 모두 커밋되어 있는 상태.
      // main-header의 state에 있는 값들로 조회
      // 조회에 성공하면 searchresult로 이동하고 searchSelect, Keyword = ""
      router.push("/searchresult?word=" + state.searchKeyword);
      state.searchSelect = "";
      // store.commit("root/setSearchType", "0");
      state.searchKeyword = "";
      // store.commit("root/setClassType", "00");
      // store.commit("root/setClassLevel", "00");
      // store.commit("root/setClassDay", "월화수목금토일");
      // store.commit("root/setClassStartTime", "00");
      // store.commit("root/setClassEndTime", "23");
    };

    const getSearchClassList = function() {
      store
        .dispatch("root/getSearchClassList",{
          searchType: store.getters["root/getSearchType"],
          searchKeyword: store.getters["root/getSearchKeyword"],
          classType: store.getters["root/getClassType"],
          classLevel: store.getters["root/getClassLevel"],
          classDay: store.getters["root/getClassDay"],
          classStartTime: store.getters["root/getClassStartTime"],
          classEndTime: store.getters["root/getClassEndTime"],
        })
        .then(function(result) {
          store.commit("root/setClassList", result.data);
          store.commit("root/setSearchType", "0");
          store.commit("root/setSearchKeyword", "");
          store.commit("root/setClassType", "00");
          store.commit("root/setClassLevel", "00");
          store.commit("root/setClassDay", "월화수목금토일");
          store.commit("root/setClassStartTime", "00");
          store.commit("root/setClassEndTime", "23");

          for (var i = 0; i < state.list.length; i++) {
            var startMonth = parseInt(
              result.data[i].classStartDate.split("-")[1]
            );
            var endMonth = parseInt(result.data[i].classEndDate.split("-")[1]);
            result.data[i].classPrice = Math.ceil(
              result.data[i].classPrice / (endMonth - startMonth + 1)
            );
          }
        })
        .catch(function(err) {
          // alert(err.response);
        });
    };

    const clickLogin = () => {
      emit("openLoginDialog");
    };

    const clickJoin = () => {
      emit("openJoinDialog");
    };

    const clickSearchDetail = () => {
      emit("openSearchDialog");
      //this.searchDialogVisible = true;
    };

    const changeCollapse = () => {
      state.isCollapse = !state.isCollapse;
    };

    const logout = function() {
      localStorage.removeItem("jwt-auth-token");
      store.commit("root/setIsLogined", false);
      //store.state.isLogined = false;
      router.push("/");
    };

    const mvFavoriteClass = function() {
      console.log("state.userType : " + state.userType);
      if (state.userType == '수강생') {
        router.push("/favoriteclass");
      }
      else {
        alert("찜목록은 수강생만 사용할 수 있어요!");
      }
    };

    const checkAlert = function() {
      alert("피드백 알림 확인");
    };

    return {
      state,
      search,
      clickLogin,
      clickJoin,
      clickSearchDetail,
      changeCollapse,
      logout,
      mvFavoriteClass,
      checkAlert,
      getSearchClassList
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


/* .main-header .hide-on-small .main-navbar .logo-wrapper .ic.ic-logo{
  width: 70px;
  height: 50px;
  background-size: contain;
  background-repeat: no-repeat;
  background-image: url("../../../assets/images/MainLogo.png");
} */

.ic.ic-logo {
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

.main-navbar {
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

/* 민영 수정 시작 */

/* 상세 검색 버튼 정렬 및 마진 */
.searchDetailOption {
  padding: 0;
  /* max-height: 40px; */
  height: 40px;
  margin-left: 10px;
  margin-right: 10px;
}

/* 반응형 처리 - 마이클래스, 찜목록 */
@media (max-width: 1080px) {
  .button-wrapper-logined {
    display: none;
  }

  .small-button-wrapper-logined {
    display: block;
  }
}

@media (min-width: 1080px) {
  .button-wrapper-logined {
    display: block;
  }

  .small-button-wrapper-logined {
    display: none;
  }
}
/* 민영 수정 끝 */
</style>
