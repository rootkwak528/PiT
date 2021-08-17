<template>
  <el-dialog
    custom-class="search-dialog"
    title="상세검색"
    v-model="state.dialogVisible"
    @close="handleClose"
  >
    <el-form
      v-loading="loading"
      :model="state.form"
      :rules="state.rules"
      ref="searchForm"
      :label-position="state.form.align"
    >
      <el-form-item prop="classTypeList" label="운동 종목" :label-width="formLabelWidth">
        <el-checkbox-group v-model="state.form.classTypeList">
          <el-checkbox label="PT" name="classTypeList"></el-checkbox>
          <el-checkbox label="요가" name="classTypeList"></el-checkbox>
          <el-checkbox label="필라테스" name="classTypeList"></el-checkbox>
          <el-checkbox label="기타" name="classTypeList"></el-checkbox>
        </el-checkbox-group>
      </el-form-item>
      <el-form-item prop="classLevelList" label="난이도" :label-width="formLabelWidth">
        <el-checkbox-group v-model="state.form.classLevelList">
          <el-checkbox label="입문" name="classLevelList"></el-checkbox>
          <el-checkbox label="초급" name="classLevelList"></el-checkbox>
          <el-checkbox label="중급" name="classLevelList"></el-checkbox>
          <el-checkbox label="고급" name="classLevelList"></el-checkbox>
        </el-checkbox-group>
      </el-form-item>
      <el-form-item prop="classDayList" label="요일" :label-width="formLabelWidth">
        <el-checkbox-group v-model="state.form.classDayList">
          <el-checkbox label="월" name="classDayList"></el-checkbox>
          <el-checkbox label="화" name="classDayList"></el-checkbox>
          <el-checkbox label="수" name="classDayList"></el-checkbox>
          <el-checkbox label="목" name="classDayList"></el-checkbox>
          <el-checkbox label="금" name="classDayList"></el-checkbox>
          <el-checkbox label="토" name="classDayList"></el-checkbox>
          <el-checkbox label="일" name="classDayList"></el-checkbox>
        </el-checkbox-group>
      </el-form-item>
      <el-form-item prop="time" label="시간" :label-width="formLabelWidth">
        <el-radio-group v-model="state.form.time">
          <el-radio label="오전(12시 이전)" name="time"></el-radio>
          <el-radio label="오후(18시 이전)" name="time"></el-radio>
          <el-radio label="오후(18시 이후)" name="time"></el-radio>
        </el-radio-group>
      </el-form-item>
    </el-form>
    <template #footer>
      <span class="dialog-footer">
        <el-button @click="handleClose">취소</el-button>
        <el-button type="primary" @click="clickSearch">설정</el-button>
      </span>
    </template>
  </el-dialog>
</template>
<script>
import { reactive, computed, ref, onMounted } from "vue";
import { useStore } from "vuex";

export default {
  name: "search-dialog",

  props: {
    open: {
      type: Boolean,
      default: false
    }
  },

  setup(props, { emit }) {
    const store = useStore();
    const searchForm = ref(null);
    const loading = ref(false);

    const state = reactive({
      form: {
        classTypeList: [],
        classType: "",
        classLevelList: [],
        classLevel: "",
        classDayList: [],
        classDay: "",
        classStartTime: "",
        classEndTime: "",
        time: "",
        align: "left"
      },

      dialogVisible: computed(() => props.open),
      formLabelWidth: "120px"
    });

    const clickSearch = function() {

      state.form.classType = "";
      state.form.classLevel = "";
      state.form.classDay = "";
      state.form.classStartTime = "";
      state.form.classEndTime = "";

      // 타입 처리
      if (state.form.classTypeList.length == 0){
        state.form.classType = "00"
      }
      else {
        for(let i = 0; i<state.form.classTypeList.length; i++){
          if (state.form.classTypeList[i] == "PT"){
            state.form.classType += "001 "
          }
          else if (state.form.classTypeList[i] == "요가") {
            state.form.classType += "002 "
          }
          else if (state.form.classTypeList[i] == "필라테스") {
            state.form.classType += "003 "
          }
          else if (state.form.classTypeList[i] == "기타") {
            state.form.classType += "004 "
          }
        }
      }
      // 레벨 처리
      if (state.form.classLevelList.length == 0){
        state.form.classLevel = "00"
      }
      else{
        for(let i = 0; i<state.form.classLevelList.length; i++){
          if (state.form.classLevelList[i] == "입문"){
            state.form.classLevel += "001 "
          }
          else if (state.form.classLevelList[i] == "초급") {
            state.form.classLevel += "002 "
          }
          else if (state.form.classLevelList[i] == "중급") {
            state.form.classLevel += "003 "
          }
          else if (state.form.classLevelList[i] == "고급") {
            state.form.classLevel += "004 "
          }
        }
      }

      // 요일 처리
      if (state.form.classDayList.length == 0){
        state.form.classDay = "월화수목금토일"
      }
      else {
        for(let i = 0; i<state.form.classDayList.length; i++){
          if ( state.form.classDayList[i] == "월"){
            state.form.classDay += "월"
          }
          else if ( state.form.classDayList[i] == "화"){
            state.form.classDay += "화"
          }
          else if ( state.form.classDayList[i] == "수"){
            state.form.classDay += "수"
          }
          else if ( state.form.classDayList[i] == "목"){
            state.form.classDay += "목"
          }
          else if ( state.form.classDayList[i] == "금"){
            state.form.classDay += "금"
          }
          else if ( state.form.classDayList[i] == "토"){
            state.form.classDay += "토"
          }
          else if ( state.form.classDayList[i] == "일"){
            state.form.classDay += "일"
          }
        }
      }

      // 시간 처리
      if (state.form.time == ""){
        state.form.classStartTime = "00";
        state.form.classEndTime = "23";
      }
      else if (state.form.time == "오전(12시 이전)") {
        state.form.classStartTime = "00";
        state.form.classEndTime = "13";
      }
      else if (state.form.time == "오후(18시 이전)"){
        state.form.classStartTime = "12";
        state.form.classEndTime = "19";
      }
      else if (state.form.time == "오후(18시 이후)") {
        state.form.classStartTime = "18";
        state.form.classEndTime = "23";
      }

      store.commit("root/setClassType", state.form.classType)
      store.commit("root/setClassLevel", state.form.classLevel)
      store.commit("root/setClassDay", state.form.classDay)
      store.commit("root/setClassStartTime", state.form.classStartTime)
      store.commit("root/setClassEndTime", state.form.classEndTime)

      // console.log("설정창 classType : " + state.form.classType)
      // console.log("설정창 classLevel : " + state.form.classLevel)
      // console.log("설정창 classDay : " + state.form.classDay)
      // console.log("설정창 classStartTime : " + state.form.classStartTime)
      // console.log("설정창 classEndTime : " + state.form.classEndTime)

      emit("closeSearchDialog");
    };

    const handleClose = function() {
      // state.form.classTypeList = []
      // state.form.classLevelList = []
      // state.form.classDayList = []
      // state.form.time = ""
      emit("closeSearchDialog");
    };

    return { searchForm, loading, state, clickSearch, handleClose };
  }
};
</script>
<style>
.join-dialog {
  width: 500px !important;
  height: 550px;
}
.join-dialog .el-dialog__headerbtn {
  float: right;
}
.join-dialog .el-form-item__content {
  margin-left: 0 !important;
  float: right;
  width: 200px;
  display: inline-block;
}
.join-dialog .el-form-item {
  margin-bottom: 20px;
}
.join-dialog .el-form-item__error {
  font-size: 12px;
  color: red;
}
.join-dialog .el-input__suffix {
  display: none;
}
.join-dialog .el-dialog__footer {
  margin: 0 calc(50% - 80px);
  padding-top: 0;
  display: inline-block;
}
.join-dialog .dialog-footer .el-button {
  width: 120px;
}
.el-dialog {
  padding: 18px;
}

.el-form-item > label{
  width: 85px;
}
</style>
