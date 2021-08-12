<template>
  <div class="name">
    {{ username }}
  </div>
  <div>
    <el-row class="tac">
      <el-col :span="6">
        <mypage-sidebar
          :pathname="pathname"
          :userTypeName="state.userTypeName"
        />
      </el-col>
      <el-col :span="18" style="min-height: 655px">
        <router-view :userTypeName="state.userTypeName" />
      </el-col>
    </el-row>
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
          state.userTypeName = result.data.userTypeName;
        })
        .catch(function(err) {
          alert(err.response.data.message);
          console.log(err);
          //store.commit("root/setIsLogined", false);
          //router.push("/");
        });
    };

    return { state, getUserInfo };
  }
};
</script>
