<template>
  <el-tabs v-model="activeName" @tab-click="handleClick">
    <el-tab-pane label="클래스 설명" name="classDescript">
      <div>
        <h2>클래스 설명</h2>
        <el-divider />
        <h3>클래스 요약</h3>
        <el-tag size="mini" color="#BEEDED">8월 8일부터 수강 가능</el-tag>
        <div class="info-content">
          <div class="info-content-child" style="width: 20%">
            <div><i class="el-icon-folder" /> 대분류</div>
            <div><i class="el-icon-goods" /> 준비물</div>
            <div><i class="el-icon-star-off" /> 레벨</div>
            <div><i class="el-icon-goods" /> 기간</div>
            <div><i class="el-icon-star-off" /> 수업 수</div>
            <div><i class="el-icon-coin" /> 금액</div>
          </div>
          <div class="info-content-child" style="margin-left: 15px">
            <div>{{ state.form.classTypeName }}</div>
            <div>{{ state.form.classMaterial }}</div>
            <div>{{ state.form.classLevelName }}</div>
            <div>3달</div>
            <div>{{ state.form.classTcnt }}</div>
            <div>총 {{ state.form.classPrice }}원</div>
          </div>
        </div>
        <el-divider />
        <h3>{{ state.form.classTitle }}</h3>
        <p>{{ state.form.classDesc }}</p>
        <el-divider />
        <h3>커리큘럼</h3>
        <pre>{{ state.form.classCurri }}</pre>
        <el-divider />
        <h3>커뮤니티</h3>
        <div v-for="comment in state.form.comments" :key="comment">
          <el-card class="box-card">
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

    <el-tab-pane label="클래스 요약" name="summary">
      <h3>클래스 요약</h3>
      <el-tag size="mini" color="#BEEDED">8월 8일부터 수강 가능</el-tag>
      <div class="info-content">
        <div class="info-content-child" style="width: 20%">
          <div><i class="el-icon-folder" /> 대분류</div>
          <div><i class="el-icon-goods" /> 준비물</div>
          <div><i class="el-icon-star-off" /> 레벨</div>
          <div><i class="el-icon-goods" /> 기간</div>
          <div><i class="el-icon-star-off" /> 수업 수</div>
          <div><i class="el-icon-coin" /> 금액</div>
        </div>
        <div class="info-content-child" style="margin-left: 15px">
          <div>{{ state.form.classTypeName }}</div>
          <div>{{ state.form.classMaterial }}</div>
          <div>{{ state.form.classLevelName }}</div>
          <div>3달</div>
          <div>{{ state.form.classTcnt }}</div>
          <div>총 {{ state.form.classPrice }}원</div>
        </div>
      </div>
    </el-tab-pane>

    <el-tab-pane label="커리큘럼" name="curriculum"
      ><h3>커리큘럼</h3>
      <pre>{{ state.form.classCurri }}</pre>
    </el-tab-pane>

    <el-tab-pane label="커뮤니티" name="review"
      ><h3>커뮤니티</h3>
      <div>
        <div v-for="comment in state.form.comments" :key="comment">
          <el-card class="box-card">
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

    <div class="permission-btn">
      <el-button
        v-if="state.form.classPermission === '001'"
        style="display: none"
        >클래스 개설승인</el-button
      >
      <el-button v-else class="form-btn" @click="clickGranted"
        >클래스 개설승인</el-button
      >
      <el-button
        v-if="state.form.classPermission === '002'"
        style="display: none"
        >클래스 개설미승인</el-button
      >
      <el-button v-else class="form-btn" @click="clickNotGranted"
        >클래스 개설미승인</el-button
      >
      <el-button
        v-if="state.form.classPermission === '003'"
        style="display: none"
        >클래스 개설거절</el-button
      >
      <el-button v-else class="form-btn-delete" @click="clickDenied"
        >클래스 개설거절</el-button
      >
    </div>
  </el-tabs>
</template>

<script>
import { reactive, computed, ref, onMounted } from "vue";
import { useRouter } from "vue-router";
import { useStore } from "vuex";

export default {
  name: "AdminClassContent",
  props: {
    classNo: String
  },
  setup(props) {
    const store = useStore();
    const router = useRouter();
    const state = reactive({
      form: {
        classTitle: "",
        classDesc: "",
        classCurri: ""
      },
      comments: []
    });

    onMounted(() => {
      getAdminClassDetail();
    });

    const getAdminClassDetail = function() {
      store
        .dispatch("root/getAdminClassDetail", { classNo: props.classNo })
        .then(function(result) {
          // 컨텐트에서 받은 attr
          state.form.classTitle = result.data.classTitle;
          state.form.classDesc = result.data.classDesc;
          state.form.classCurri = result.data.classCurri;
          state.form.comments = result.data.comments;
          // summary에서 받은 attr
          state.form.classStartDate = result.data.classStartDate;
          state.form.classEndDate = result.data.classEndDate;
          state.form.classTypeName = result.data.classTypeName;
          state.form.classMaterial = result.data.classMaterial;
          state.form.classLevelName = result.data.classLevelName;
          state.form.classTcnt = result.data.classTcnt;
          state.form.classPrice = result.data.classPrice;
          state.form.classPermission = result.data.classPermission;

          console.log("adminclass-content : " + state.form.classPermission);

          var startMonth = parseInt(result.data.classStartDate.split("-")[1]);
          var endMonth = parseInt(result.data.classEndDate.split("-")[1]);
          state.form.classPricePerMonth = Math.ceil(
            result.data.classPrice / (endMonth - startMonth + 1)
          );
        })
        .catch(function(err) {
          console.log(err);
        });
    };

    const clickGranted = function() {
      state.form.classPermission = "001";
      updateClassPermission();
      alert(
        "클래스 개설 승인 처리 하셨습니다. 원활한 클래스 관리를 기원합니다."
      );
      router.push("/adminClass");
    };
    const clickNotGranted = function() {
      state.form.classPermission = "002";
      updateClassPermission();
      alert("클래스 개설 미승인 처리 하셨습니다.");
      router.push("/adminClass");
    };
    const clickDenied = function() {
      state.form.classPermission = "003";
      updateClassPermission();
      alert("클래스 개설 거절 처리 하셨습니다.");
      router.push("/adminClass");
    };

    const updateClassPermission = function() {
      store
        .dispatch("root/updateClassPermission", {
          classNo: props.classNo,
          permission: state.form.classPermission
        })
        .then(function(result) {
          console.log(result.data.message);
        })
        .catch(function(err) {
          console.log(err);
        });
    };

    return {
      state,
      router,
      getAdminClassDetail,
      updateClassPermission,
      clickGranted,
      clickNotGranted,
      clickDenied
    };
  },
  data() {
    return {
      activeName: "classDescript"
    };
  }
};
</script>
