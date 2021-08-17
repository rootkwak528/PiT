<template>
  <div class="name">
    {{ username }}
  </div>
  <div class="container">
    <div class="tac row">
      <div class="col-sm-4 col-md-3 col-12" id="sidebar" style="padding: 0;">
        <!-- :span="6"  -->
        <mypage-sidebar
          :pathname="pathname"
          :userTypeName="state.userTypeName"
        />
      </div>
      <div class="col-sm-8 col-md-9 col-12" id="sidebar-content" style="min-height: 655px; padding: 0;">
        <!-- :span="18"  -->
        <router-view :userTypeName="state.userTypeName" />
      </div>
    </div>
  </div>
</template>

<script>
import MypageSidebar from "../mypage/sidebar/mypage-sidebar.vue";
import { useRouter } from "vue-router";
import { reactive, computed, onMounted } from "vue";
import { useStore } from "vuex";

export default {
  name: "MypageMain",
  components: {
    MypageSidebar
  },
  methods: {
    format(percentage) {
      return percentage === 100 ? "Full" : `${percentage}%`;
    }
  },
  computed: {
    pathname() {
      return window.location.pathname.slice(1);
    }
  },
  setup() {
    const router = useRouter();
    const store = useStore();

    onMounted(() => {
      getUserInfo();
    });

    const state = reactive({
      userTypeName: ""
    });

    const getUserInfo = function() {
      store
        .dispatch("root/getUserInfo")
        .then(function(result) {
          //console.log(result);
          state.userTypeName = result.data.userTypeName;
        })
        .catch(function(err) {
          //alert(err.response.data.message);
          console.log(err);
          //store.commit("root/setIsLogined", false);
          //router.push("/");
        });
    };

    return { state, getUserInfo };
  }
};
</script>

<style>
@media (max-width: 576px) {
  #sidebar {
    border-bottom: solid 1.2px #ebeef5;
    margin-bottom: 35px;
  }

  #sidebar-content .content-wrapper {
    margin: 0;
  }
}

@media (min-width: 576px) {
  #sidebar {
    border-right: solid 1.2px #ebeef5;
  }
}
</style>
