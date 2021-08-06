<template>
  <el-tabs v-model="activeName" @tab-click="handleClick">
    <el-tab-pane label="클래스 설명" name="classDescript">
      <div>
        <h2>클래스 설명</h2>
        <el-divider />
        <h3>{{ state.form.classTitle }}</h3>
        <p>{{ state.form.classDesc }}</p>
        <el-divider />
        <h3>커리큘럼</h3>
        <pre>{{ state.form.classCurri }}</pre>
        <el-divider />
        <h3>커뮤니티</h3>
        <div v-for="o in community" :key="o">
          <el-card class="box-card">
            <div class="rate">
              <div style="margin-right: 20px">{{ o.userid }}</div>
              <div>{{ o.reviewCreateDate }}</div>
            </div>
            <div>
              {{ o.review }}
            </div>
          </el-card>
        </div>
      </div>
    </el-tab-pane>
    <el-tab-pane label="커리큘럼" name="curriculum"
      ><h3>커리큘럼</h3>
      <pre>{{ state.form.classCurri }}</pre></el-tab-pane
    >
    <el-tab-pane label="커뮤니티" name="review"
      ><h3>커뮤니티</h3>
      <div>
        <div v-for="o in community" :key="o">
          <el-card class="box-card">
            <div class="rate">
              <div style="margin-right: 20px">{{ o.userid }}</div>
              <div>{{ o.reviewCreateDate }}</div>
            </div>
            <div>
              {{ o.review }}
            </div>
          </el-card>
        </div>
      </div></el-tab-pane
    >
  </el-tabs>
</template>

<script>
import { reactive, computed, ref, onMounted } from "vue";
import { useStore } from "vuex";

export default {
  name: "ClassContent",
  props: {
    classNo: String
  },
  setup(props) {
    const store = useStore();
    const state = reactive({
      form: {
        classTitle: "",
        classDesc: "",
        classCurri: ""
      }
    });

    onMounted(() => {
      getClassDetail();
    });

    const getClassDetail = function() {
      store
        .dispatch("root/getClassDetail", { classNo: props.classNo })
        .then(function(result) {
          console.log(result.data.classTitle);
          state.form.classTitle = result.data.classTitle;
          state.form.classDesc = result.data.classDesc;
          state.form.classCurri = result.data.classCurri;
          console.log(state.form.classTitle);
        })
        .catch(function() {});
    };

    // store.dispatch("root/requestUserInfo", {
    //   token: "Bearer" + localStorage.getItem("jwt-auth-token")
    // })
    // .then(function(result) {
    //   state.form.profile = result.data.userProfile;
    //   // console.log(
    //   //   "로그인 성공 profile url : " + state.form.profile
    //   // );
    //   store.state.profileUrl = state.form.profile;
    //   store.commit("root/setProfileUrl", store.state.profileUrl);
    //   // console.log("login.vue 에서 url: " + store.state.profileUrl);
    //   loading.value = false;
    // })
    // .catch(function(err) {
    //   alert(err.response.data.message);
    //   loading.value = false;
    // });

    return { getClassDetail, state };
  },
  data() {
    return {
      activeName: "classDescript",
      //       classInfo: {
      //         title: "달심쌤과 함께 퇴근 후 요가퐈이야",
      //         description:
      //           "퇴근 이후에 코어 근육 강화를 위한 요가 수업입니다. 늦은 저녁 시간에 강의가 진행됩니다.",
      //         curriculum: `1주차 : 수강생의 상태 파악
      // 2주차 : ~~
      // 3주차 : ~~
      // 4주차 : ~~

      // 5주차 : ~~
      // 6주차 : ~~
      // 7주차 : ~~
      // 8주차 : ~~

      // 9주차 : ~~
      // 10주차 : ~~
      // 11주차 : ~~
      // 12주차 : ~~`
      //       },
      value: 3.7,
      community: [
        {
          userid: "id123",
          reviewCreateDate: "2021-07-29",
          review: `정말 좋아요! 시간도 정말 적절하고 강사님께서 배려심 넘치세요!!
맨날 앉아있기만 해서 허리가 정말 안좋았는데 달심쌤 수업 듣고 자세도 훨씬 좋아졌어요! 강력추천 드립니다!!`
        },
        {
          userid: "id222",
          reviewCreateDate: "2021-08-01",
          review: `정말 좋아요! 시간도 정말 적절하고 강사님께서 배려심 넘치세요!!
맨날 앉아있기만 해서 허리가 정말 안좋았는데 달심쌤 수업 듣고 자세도 훨씬 좋아졌어요! 강력추천 드립니다!!`
        },
        {
          userid: "id333",
          reviewCreateDate: "2021-08-01",
          review: `정말 좋아요! 시간도 정말 적절하고 강사님께서 배려심 넘치세요!!
맨날 앉아있기만 해서 허리가 정말 안좋았는데 달심쌤 수업 듣고 자세도 훨씬 좋아졌어요! 강력추천 드립니다!!`
        }
      ]
    };
  }
};
</script>

<style>
.text {
  font-size: 14px;
}

.box-card {
  margin-top: 7px;
}
</style>
