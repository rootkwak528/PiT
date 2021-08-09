<template>
  <el-dialog
    custom-class="search-dialog"
    title="회원가입"
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
      <el-form-item prop="type" label="운동 종목" :label-width="formLabelWidth">
        <el-checkbox-group v-model="state.form.type">
          <el-checkbox label="PT" name="type"></el-checkbox>
          <el-checkbox label="요가" name="type"></el-checkbox>
          <el-checkbox label="필라테스" name="type"></el-checkbox>
          <el-checkbox label="크로스핏" name="type"></el-checkbox>
        </el-checkbox-group>
      </el-form-item>
      <el-form-item prop="level" label="난이도" :label-width="formLabelWidth">
        <el-checkbox-group v-model="state.form.level">
          <el-checkbox label="입문" name="level"></el-checkbox>
          <el-checkbox label="초급" name="level"></el-checkbox>
          <el-checkbox label="중급" name="level"></el-checkbox>
          <el-checkbox label="고급" name="level"></el-checkbox>
        </el-checkbox-group>
      </el-form-item>
      <el-form-item prop="day" label="요일" :label-width="formLabelWidth">
        <el-checkbox-group v-model="state.form.day">
          <el-checkbox label="월" name="day"></el-checkbox>
          <el-checkbox label="화" name="day"></el-checkbox>
          <el-checkbox label="수" name="day"></el-checkbox>
          <el-checkbox label="목" name="day"></el-checkbox>
          <el-checkbox label="금" name="day"></el-checkbox>
          <el-checkbox label="토" name="day"></el-checkbox>
          <el-checkbox label="일" name="day"></el-checkbox>
        </el-checkbox-group>
      </el-form-item>
      <el-form-item prop="time" label="시간" :label-width="formLabelWidth">
        <el-checkbox-group v-model="state.form.time">
          <el-checkbox label="오전(12시 이전)" name="time"></el-checkbox>
          <el-checkbox label="오후(18시 이전)" name="time"></el-checkbox>
          <el-checkbox label="오후(18시 이후)" name="time"></el-checkbox>
        </el-checkbox-group>
      </el-form-item>
    </el-form>
    <template #footer>
      <span class="dialog-footer">
        <el-button @click="dialogFormVisible = false">취소</el-button>
        <el-button type="primary" @click="clickSearch">설정</el-button>
      </span>
    </template>
  </el-dialog>
</template>
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
</style>
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
    // 마운드 이후 바인딩 될 예정 - 컨텍스트에 노출시켜야함. <return>
    const searchForm = ref(null);
    const loading = ref(false);

    /*
      // Element UI Validator
      // rules의 객체 키 값과 form의 객체 키 값이 같아야 매칭되어 적용됨
      //
    */

    const state = reactive({
      form: {
        type: [],
        level: [],
        day: [],
        time: [],
        align: "left"
      },

      dialogVisible: computed(() => props.open),
      formLabelWidth: "120px"
    });

    const clickSearch = function() {
      alert(
        "당신의 선택은? \ntype : " +
          state.form.type +
          "\nlevel : " +
          state.form.level +
          "\nday : " +
          state.form.day +
          "\ntime : " +
          state.form.time
      );
      // console.log("type : "+state.form.type)
      // console.log("level : "+state.form.level)
      // console.log("day : "+state.form.day)
      // console.log("time : "+state.form.time)
      emit("closeSearchDialog");
    };

    const handleClose = function() {
      // state.form.type = []
      // state.form.level = []
      // state.form.day = []
      // state.form.time = []
      emit("closeSearchDialog");
    };

    return { searchForm, loading, state, clickSearch, handleClose };
  }
};
</script>
