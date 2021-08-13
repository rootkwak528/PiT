<template>
  <div class="open-class-content-wrapper">
    <el-form
      v-loading="loading"
      :model="state.form"
      :rules="state.rules"
      ref="openclassForm"
      :label-position="state.form.align"
    >
      <el-form-item
        prop="classType"
        label="대분류"
        :label-width="state.formLabelWidth"
      >
        <el-select v-model="state.form.classType" placeholder="클래스 분류">
          <el-option
            v-for="item in classTypeOptions"
            :key="item.value"
            :label="item.label"
            :value="item.value"
          >
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item
        prop="classDate"
        label="기간"
        :label-width="state.formLabelWidth"
      >
        <div class="date-picker-wrapper">
          <el-date-picker
            v-model="state.form.classDate"
            type="daterange"
            range-separator="To"
            start-placeholder="시작 날짜"
            end-placeholder="종료 날짜"
            format="YYYY-MM-DD"
          >
          </el-date-picker>
        </div>
      </el-form-item>
      <el-form-item
        prop="classDay"
        label="요일"
        :label-width="state.formLabelWidth"
      >
        <div class="date-picker-wrapper">
          <div style="margin-right: 30px">매주</div>
          <el-checkbox-group v-model="state.form.classDay">
            <el-checkbox label="월"></el-checkbox>
            <el-checkbox label="화"></el-checkbox>
            <el-checkbox label="수"></el-checkbox>
            <el-checkbox label="목"></el-checkbox>
            <el-checkbox label="금"></el-checkbox>
            <el-checkbox label="토"></el-checkbox>
            <el-checkbox label="일"></el-checkbox>
          </el-checkbox-group>
        </div>
      </el-form-item>
      <div class="date-picker-wrapper">
        <el-form-item
          prop="classStartTime"
          label="시간"
          :label-width="state.formLabelWidth"
        >
          <el-time-select
            placeholder="Start time"
            v-model="state.form.classStartTime"
            start="00:00"
            step="01:00"
            end="24:00"
            style="width: 200px"
          >
          </el-time-select>
        </el-form-item>
        <el-form-item prop="classStartTime">
          <el-time-select
            placeholder="End time"
            v-model="state.form.classEndTime"
            start="00:00"
            step="01:00"
            end="24:00"
            :minTime="startTime"
            style="width: 200px"
          >
          </el-time-select>
        </el-form-item>
      </div>
      <el-form-item
        prop="classLevel"
        label="레벨"
        :label-width="state.formLabelWidth"
      >
        <el-select v-model="state.form.classLevel" placeholder="클래스 레벨">
          <el-option
            v-for="item in classLevelOptions"
            :key="item.value"
            :label="item.label"
            :value="item.value"
          >
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item
        prop="classTitle"
        label="클래스 제목"
        :label-width="state.formLabelWidth"
      >
        <el-input v-model="state.form.classTitle"></el-input>
      </el-form-item>
      <el-form-item
        prop="classDesc"
        label="클래스 설명"
        :label-width="state.formLabelWidth"
      >
        <el-input
          v-model="state.form.classDesc"
          type="textarea"
          :rows="2"
        ></el-input>
      </el-form-item>
      <el-form-item
        prop="classCurri"
        label="커리큘럼"
        :label-width="state.formLabelWidth"
      >
        <el-input
          v-model="state.form.classCurri"
          type="textarea"
          :rows="2"
        ></el-input>
      </el-form-item>
      <el-form-item
        prop="classLimit"
        label="인원"
        :label-width="state.formLabelWidth"
      >
        <el-input v-model="state.form.classLimit"></el-input>
      </el-form-item>
      <el-form-item
        prop="classTcnt"
        label="전체수업횟수"
        :label-width="state.formLabelWidth"
      >
        <el-input v-model="state.form.classTcnt"></el-input>
      </el-form-item>
      <el-form-item
        prop="classPrice"
        label="금액"
        :label-width="state.formLabelWidth"
      >
        <el-input v-model="state.form.classPrice"></el-input>
      </el-form-item>
      <el-form-item
        prop="classMaterial"
        label="준비물"
        :label-width="state.formLabelWidth"
      >
        <el-input v-model="state.form.classMaterial"></el-input>
      </el-form-item>
      <el-form-item
        prop="classThumbnail"
        label="썸네일"
        :label-width="state.formLabelWidth"
      >
        <el-upload
          class="upload-demo"
          action="v1/users/image"
          accept="image/jpeg"
          :limit="1"
          :on-exceed="handleExceed"
          :on-success="handleFileSuccess"
        >
          <el-button size="small" type="primary">Click to upload</el-button>
          <!-- <template #tip>
            <div class="el-upload__tip">
              jpg/png files with a size less than 500kb
            </div>
          </template> -->
        </el-upload>
      </el-form-item>
    </el-form>

    <div class="open-class-footer">
      <el-button
        class="form-btn"
        @click="[submitForm('openclassForm'), clickRegister()]"
        >가입하기</el-button
      >
      <!-- <el-button v-else class="form-btn" disabled>가입하기</el-button> -->
    </div>
  </div>
</template>

<script>
import { reactive, computed, ref } from "vue";
import { ElMessage } from "element-plus";
import { useStore } from "vuex";

export default {
  name: "OpenClass",
  setup(props, { emit }) {
    const store = useStore();
    const loading = ref(false);
    const openclassForm = ref(null);

    const classTypeOptions = [
      { value: "001", label: "PT" },
      { value: "002", label: "요가" },
      { value: "003", label: "필라테스" },
      { value: "003", label: "기타" }
    ];

    const classLevelOptions = [
      { value: "001", label: "입문" },
      { value: "002", label: "초급" },
      { value: "003", label: "중급" },
      { value: "003", label: "고급" }
    ];

    const validatePrice = ({ rules }, value, callback) => {
      const number = /^[0-9]*$/;
      if (value === "") {
        callback(new Error("금액을 입력해주세요."));
      } else if (!number.test(value)) {
        callback(new Error("숫자만 입력해주세요."));
      } else {
        callback();
      }
    };

    const validateDesc = ({ rule }, value, callback) => {
      if (value === "") {
        callback(new Error("클래스 설명을 입력해주세요."));
      } else if (value.length > 200) {
        callback(new Error("최대 200자까지 입력 가능합니다."));
      } else {
        callback();
      }
    };

    const validateLimit = ({ rules }, value, callback) => {
      const number = /^[0-9]*$/;
      if (value === "") {
        callback(new Error("인원을 입력해주세요."));
      } else if (!number.test(value)) {
        callback(new Error("숫자만 입력해주세요."));
      } else {
        callback();
      }
    };

    const validateTcnt = ({ rules }, value, callback) => {
      const number = /^[0-9]*$/;
      if (value === "") {
        callback(new Error("전체수업횟수를 입력해주세요."));
      } else if (!number.test(value)) {
        callback(new Error("숫자만 입력해주세요."));
      } else {
        callback();
      }
    };

    const state = reactive({
      form: {
        classType: "",
        classDate: "",
        classDay: [],
        classStartDate: "",
        classEndDate: "",
        classTitle: "",
        classDesc: "",
        classCurri: "",
        classPrice: "",
        classMaterial: "",
        classThumbnail: "",
        classTime: "",
        classStartTime: "",
        classEndTime: "",
        classLevel: "",
        classLimit: "",
        classTcnt: "",
        align: "left",
        name: ""
      },
      rules: {
        classType: [
          {
            required: true,
            message: "클래스 분류를 선택해주세요.",
            trigger: "change"
          }
        ],
        classDate: [
          {
            required: true,
            message: "기간을 선택해주세요.",
            trigger: "change"
          }
        ],
        classEndDate: [
          {
            required: true,
            message: "종료일을 선택해주세요.",
            trigger: "change"
          }
        ],
        classDay: [
          { required: true, message: "요일을 선택해주세요.", trigger: "change" }
        ],
        classTitle: [
          {
            required: true,
            message: "클래스 제목을 입력해주세요.",
            trigger: "change"
          }
        ],
        classDesc: [
          { required: true, validator: validateDesc, trigger: "change" }
        ],
        classCurri: [
          {
            required: true,
            message: "클래스 커리큘럼을 입력해주세요.",
            trigger: "change"
          }
        ],
        classPrice: [
          {
            required: true,
            validator: validatePrice,
            trigger: "change"
          }
        ],
        classMaterial: [
          {
            required: true,
            message: "준비물을 입력해주세요.",
            trigger: "change"
          }
        ],
        classStartTime: [
          {
            required: true,
            message: "시간을 입력해주세요.",
            trigger: "change"
          }
        ],
        classEndTime: [
          {
            required: true,
            message: "시간을 입력해주세요.",
            trigger: "change"
          }
        ],
        classLevel: [
          {
            required: true,
            message: "레벨을 선택해주세요.",
            trigger: "change"
          }
        ],
        classLimit: [
          {
            required: true,
            validator: validateLimit,
            trigger: "change"
          }
        ],
        classTcnt: [
          {
            required: true,
            validator: validateTcnt,
            trigger: "change"
          }
        ]
      },
      dialogVisible: computed(() => props.open),
      formLabelWidth: "120px",
      joinValid: false,
      isEmailChecked: false,
      isNicknameChecked: false
    });

    const clickRegister = function() {
      openclassForm.value.validate(valid => {
        if (valid) {
          if (state.form.classThumbnail == "") {
            ElMessage({
              message: "썸네일을 업로드해주세요.",
              type: "error"
            });
          } else {
            state.form.classStartDate = state.form.classDate[0].toDateString();
            state.form.classEndDate = state.form.classDate[1].toDateString();

            console.log(state.form.classStartDate.getMonth());
            console.log(state.form.classEndDate);

            state.form.classStartTime = state.form.classStartTime.split(":")[0];
            state.form.classEndTime = state.form.classEndTime.split(":")[1];

            var day = "";
            for (var i = 0; i < state.form.classDay.length; i++) {
              day += state.form.classDay[i];
            }

            // store
            //   .dispatch("root/createClass", {
            //     classType: state.form.classType,
            //     classTitle: state.form.classTitle,
            //     classDay: day,
            //     classDesc: state.form.classDesc,
            //     classStartDate: state.form.classStartDate,
            //     classEndDate: state.form.classEndDate,
            //     classStartTime: state.form.classStartTime,
            //     classEndTime: state.form.classEndTime,
            //     classCurri: state.form.classCurri,
            //     classPrice: state.form.classPrice,
            //     classMaterial: state.form.classMaterial,
            //     classLevel: state.form.classLevel,
            //     classLimit: state.form.classLimit,
            //     classTcnt: state.form.classTcnt,
            //     classThumbnail: state.form.classThumbnail,
            //     classSubPhotos: "",
            //     ptroomUrl: ""
            //   })
            //   .then(function(result) {
            //     // localStorage 에 jwt 토큰 저장
            //     ElMessage({
            //       message: "회원가입이 완료되었습니다.",
            //       type: "success"
            //     });
            //     loading.value = false;
            //     emit("closeJoinDialog");
            //   })
            //   .catch(function(err) {
            //     ElMessage({
            //       message: err.response.data.message,
            //       type: "error"
            //     });
            //     loading.value = false;
            //     emit("closeJoinDialog");
            //   });
          }
        }
      });
    };

    const handleExceed = function() {
      ElMessage({
        message: "썸네일은 한장만 업로드 가능합니다.",
        type: "error"
      });
    };

    const handleFileSuccess = function(res, file) {
      state.form.classThumbnail = URL.createObjectURL(file.raw);
    };

    const toStringByFormatting = function(source, delimiter = "-") {
      const year = source.getFullYear();
      const month = leftPad(source.getMonth() + 1);
      const day = leftPad(source.getDate());
      return [year, month, day].join(delimiter);
    };

    return {
      loading,
      state,
      clickRegister,
      classTypeOptions,
      classLevelOptions,
      handleExceed,
      handleFileSuccess,
      openclassForm,
      toStringByFormatting
    };
  },
  methods: {
    submitForm(formName) {
      this.$refs[formName].validate(valid => {
        if (valid) {
          //alert("submit!");
        } else {
          return false;
        }
      });
    }
  }
};
</script>

<style>
.date-picker-wrapper {
  display: flex;
  flex-wrap: wrap;
}

.open-class-content-wrapper {
  vertical-align: middle;
  margin-left: 40px;
  margin-right: 50px;
}

.open-class-footer {
  margin-top: 60px;
  display: flex;
  justify-content: center;
}
</style>
