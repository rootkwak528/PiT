<template>
  <el-tabs v-model="activeName" @tab-click="handleClick">
    <el-tab-pane label="클래스 설명" name="classDescript">
      <div>
        <h2>{{ state.form.classTitle }}</h2>
        <el-divider />
        <h3>클래스 설명</h3>
        <p>{{ state.form.classDesc }}</p>
        <el-divider />
        <h3>커리큘럼</h3>
        <pre>{{ state.form.classCurri }}</pre>
        <el-divider />
        <h3>커뮤니티</h3>
        <div v-for="comment in state.form.comments" :key="comment">
          <el-card class="box-card" shadow="never">
            <div class="rate">
              <div style="margin-right: 20px">{{ comment.userNickname }}</div>
              <div>{{ comment.commentWriteDate }}</div>
            </div>
            <div>
              {{ comment.commentContent }}
            </div>
          </el-card>
        </div>
      </div>
    </el-tab-pane>
    <el-tab-pane label="커리큘럼" name="curriculum"
      ><h3>커리큘럼</h3>
      <pre>{{ state.form.classCurri }}</pre></el-tab-pane
    >
    <el-tab-pane label="커뮤니티" name="review">
      <h3>커뮤니티</h3>
      <div>
        <div v-for="comment in state.form.comments" :key="comment">
          <el-card class="box-card" shadow="never">
            <div class="rate">
              <div style="margin-right: 20px">{{ comment.userNickname }}</div>
              <div>{{ comment.commentWriteDate }}</div>
            </div>
            <div>
              {{ comment.commentContent }}
            </div>
          </el-card>
        </div>
      </div>
    </el-tab-pane>
  </el-tabs>
</template>

<script>
import { reactive, onMounted } from "vue";
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
      },
      comments: []
    });

    onMounted(() => {
      getClassDetail();
    });

    const getClassDetail = function() {
      store
        .dispatch("root/getClassDetail", { classNo: props.classNo })
        .then(function(result) {
          state.form.classTitle = result.data.classTitle;
          state.form.classDesc = result.data.classDesc;
          state.form.classCurri = result.data.classCurri;
          state.form.comments = result.data.comments;
        })
        .catch(function() {});
    };

    return { getClassDetail, state };
  },
  data() {
    return {
      activeName: "classDescript"
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
