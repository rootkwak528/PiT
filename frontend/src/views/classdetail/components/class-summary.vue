<template>
  <el-card class="summary-card">
    <template #header>
      <div class="card-header">
        <div class="title">{{ classInfo.title }}</div>
      </div>
    </template>
    <div style="">
      <div class="subtitle">구매정보</div>
      <el-tag size="mini" color="#BEEDED">8월 8일부터 수강 가능</el-tag>
      <div class="info-content">
        <div class="info-content-child" style="width: 30%">
          <div><i class="el-icon-folder" /> 대분류</div>
          <div><i class="el-icon-goods" /> 준비물</div>
          <div><i class="el-icon-star-off" /> 레벨</div>
          <div><i class="el-icon-goods" /> 기간</div>
          <div><i class="el-icon-star-off" /> 수업 수</div>
        </div>
        <div class="info-content-child" style="margin-left: 15px">
          <div>{{ classInfo.classType }}</div>
          <div>{{ classInfo.classMaterial }}</div>
          <div>{{ classInfo.classLevel }}</div>
          <div>3달</div>
          <div>{{ classInfo.classTcnt }}</div>
        </div>
      </div>
      <el-divider />
      <div class="info-content">
        <div class="info-content-child" style="width: 30%">
          <div><i class="el-icon-coin" /> 금액</div>
        </div>
        <div class="info-content-child" style="margin-left: 15px">
          <div>총 {{ classInfo.classPrice }}원</div>
        </div>
      </div>
      <div class="price-per-month">
        <div></div>
        <div>월 {{ classInfo.classPrice / period }}원</div>
      </div>
      <button class="btn-submit">신청하기</button>
    </div>
  </el-card>
</template>

<script>
export default {
  name: "ClassSummary",
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
  },
  data() {
    return {
      classInfo: {
        title: "달심쌤과 함께 퇴근 후 요가퐈이야",
        classStartDate: "2021-08-08",
        classEndDate: "2021-11-08",
        classType: "요가",
        classMaterial: "요가매트, 요가복",
        classLevel: "초보",
        classTcnt: "24",
        classPrice: "300000"
      },
      period: 3 // 임시변수 -> 나중에 DB에서 받아온 Date로 계산
    };
  }
};
</script>

<style>
.summary-card {
  height: 530px;
}

.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.info-content {
  display: flex;
  font-size: 15px;
  margin-top: 20px;
}

.info-content-child > * {
  margin-bottom: 8px;
}

.title {
  font-size: large;
  font-weight: bold;
}

.subtitle {
  font-weight: bold;
}

.price-per-month {
  color: #f35747;
  font-weight: bold;
  font-size: 20px;
  margin-top: 20px;
  width: 100%;
  display: flex;
  justify-content: space-between;
}

.btn-submit {
  margin-top: 30px;
  background-color: #ef7764;
  width: 100%;
  color: white;
  border: none;
  border-radius: 0.5rem;
  height: 40px;
  font-size: 18px;
  font-weight: bold;
}
</style>
