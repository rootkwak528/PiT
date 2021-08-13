<template>
  <el-container class="main-wrapper">
    <el-container class="main-container">
      <!-- <div v-if="userType == 관리자" style="width: 70%; margin-left: auto; margin-right: auto;">
        <main-header
          :height="`70px`"
          @openLoginDialog="onOpenLoginDialog"
          @openJoinDialog="onOpenJoinDialog"
          @openSearchDialog="onOpenSearchDialog"
        />
        <navbar />
        <admin-main />
        <main-footer />
      </div> -->

      <div style="width: 70%; margin-left: auto; margin-right: auto;">
        <main-header
          :height="`70px`"
          @openLoginDialog="onOpenLoginDialog"
          @openJoinDialog="onOpenJoinDialog"
          @openSearchDialog="onOpenSearchDialog"
        />
        <navbar />
        <router-view />
        <main-footer />
      </div>
    </el-container>
  </el-container>
  <login-dialog
    :open="loginDialogOpen"
    @closeLoginDialog="onCloseLoginDialog"
  />
  <join-dialog :open="joinDialogOpen" @closeJoinDialog="onCloseJoinDialog" />
  <search-dialog
    :open="searchDialogOpen"
    @closeSearchDialog="onCloseSearchDialog"
  />
</template>

<script>
import LoginDialog from "./views/main/components/login-dialog";
import JoinDialog from "./views/main/components/join-dialog";
import SearchDialog from "./views/main/components/search-dialog";
import Main from "./views/main/main.vue";
import MainHeader from "./views/main/components/main-header";
import MainFooter from "./views/main/components/main-footer";
import Navbar from "./views/main/components/navbar.vue";
import AdminMain from "./views/adminpage/adminMain.vue";
import { useStore } from "vuex";

export default {
  name: "App",

  components: {
    Main,
    MainHeader,
    MainFooter,
    Navbar,
    LoginDialog,
    JoinDialog,
    SearchDialog,
    AdminMain
  },
  methods: {
    onOpenLoginDialog() {
      this.loginDialogOpen = true;
    },
    onCloseLoginDialog() {
      this.loginDialogOpen = false;
    },
    onOpenJoinDialog() {
      this.joinDialogOpen = true;
    },
    onCloseJoinDialog() {
      this.joinDialogOpen = false;
    },
    onOpenSearchDialog() {
      this.searchDialogOpen = true;
    },
    onCloseSearchDialog() {
      this.searchDialogOpen = false;
    }
  },

  data: () => ({
    loginDialogOpen: false,
    joinDialogOpen: false,
    searchDialogOpen: false
  }),

  setup() {
    const store = useStore();

    if (localStorage.getItem("jwt-auth-token")) {
      if (
        localStorage.getItem("jwt-auth-token") != "" &&
        localStorage.getItem("jwt-auth-token") != null
      ) {
        store.commit("root/setIsLogined", true);
        store.dispatch("root/requestUserInfo", {
          token: localStorage.getItem("jwt-auth-token")
        })
        .then(function(result){
          store.commit("root/setProfileUrl", result.data.userProfile);
          store.commit("root/setUserType", result.data.userTypeName);
          store.commit("root/setUserNickname", result.data.userNickname);
        })
      }
    }
    return { store };
  }
  // created() {
  //   this.$store.state.isLogined = true
  //   console.log("ddddddddddddddddd " + this.$store.state.activeMenu);
  //   if (localStorage.getItem("jwt-auth-token")) {
  //     if (
  //       localStorage.getItem("jwt-auth-token") != "" &&
  //       localStorage.getItem("jwt-auth-token") != null
  //     ) {
  //       this.$store.state.isLogined = true;
  //       console.log(
  //         "dddddddddddddddddddddddddddddd" + this.$store.state.isLogined
  //       );
  //       this.$store
  //         .dispatch("root/requestUserInfo", {
  //           token: localStorage.getItem("jwt-auth-token")
  //         })
  //         .then(function(result) {
  //           alert("유저 정보 조회 성공");
  //           console.log(result.data);
  //         })
  //         .catch(function(err) {
  //           console.log(err);
  //           // if(err.response.data.statusCode == 401){
  //           //   // 임시코드
  //           //   if(err.response.data.message == "SignatureVerificationException" || err.response.data.message == "JWTDecodeException"){
  //           //     alert("세션이 유효하지 않습니다.")
  //           //     localStorage.removeItem('jwt-auth-token')
  //           //     store.commit('root/setIsLogined')
  //           //     router.push('/')
  //           //   }else if(err.response.data.message == "TokenExpiredException"){
  //           //     alert("세션이 만료되었습니다.")
  //           //     localStorage.removeItem('jwt-auth-token')
  //           //     store.commit('root/setIsLogined')
  //           //     router.push('/')
  //           //   }
  //           // }else if(err.response.data.statusCode == 403){
  //           //   if(err.response.data.message == "Forbidden"){
  //           //     alert("접근 권한이 없습니다.")
  //           //   }
  //           // }
  //         });
  //     }
  //   } else {
  //     localStorage.setItem("jwt-auth-token", "");
  //   }
  //   //console.log(this.$store.state.isLogined);
  // }
};
</script>
