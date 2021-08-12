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
        <div v-for="o in state.form.comments" :key="o">
          <el-card class="box-card">
            <div class="rate">
              <div style="margin-right: 20px">사용자</div>
              <div>{{ o.commentWriteDate }}</div>
            </div>
            <div>
              {{ o.commentContent }}
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
        <div v-for="o in state.form.comments" :key="o">
          <el-card class="box-card">
            <div class="rate">
              <div style="margin-right: 20px">사용자</div>
              <div>{{ o.commentWriteDate }}</div>
            </div>
            <div>
              {{ o.commentContent }}
            </div>
          </el-card>
        </div>
      </div></el-tab-pane>
  </el-tabs>
</template>

<script>
import { reactive, computed, ref, onMounted } from "vue";
import { useStore } from "vuex";

export default {
  name: "AdminClassContent",
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
        .dispatch("root/getAdminClassDetail", { classNo: props.classNo })
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
.content {
  display: flex;
  justify-content: space-between;
}
</style>
