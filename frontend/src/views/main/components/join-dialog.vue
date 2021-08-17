<template>
  <el-dialog
    custom-class="join-dialog"
    title="회원가입"
    v-model="state.dialogVisible"
    @close="handleClose"
  >
    <el-form
      v-loading="loading"
      :model="state.form"
      :rules="state.rules"
      ref="joinForm"
      :label-position="state.form.align"
    >
      <el-form-item
        prop="email"
        label="아이디(이메일)"
        :label-width="state.formLabelWidth"
      >
        <el-input
          style="float:left; width:70%"
          v-model="state.form.email"
          autocomplete="off"
        ></el-input>
        <el-button
          style="float:right; width:28%"
          class="form-btn"
          @click="checkDuplicatedEmail"
          >중복 확인</el-button
        >
      </el-form-item>
      <el-form-item
        prop="pwd"
        label="비밀번호"
        :label-width="state.formLabelWidth"
      >
        <el-input
          v-model="state.form.pwd"
          autocomplete="off"
          show-password
        ></el-input>
      </el-form-item>
      <el-form-item
        prop="pwdChk"
        label="비밀번호 확인"
        :label-width="state.formLabelWidth"
      >
        <el-input
          v-model="state.form.pwdChk"
          autocomplete="off"
          show-password
        ></el-input>
      </el-form-item>
      <el-form-item
        prop="gender"
        label="성별"
        :label-width="state.formLabelWidth"
        @click="clickGender"
      >
        <el-radio v-model="state.form.gender" label="001">남성</el-radio>
        <el-radio v-model="state.form.gender" label="002">여성</el-radio>
      </el-form-item>
      <el-form-item
        prop="name"
        label="이름"
        :label-width="state.formLabelWidth"
      >
        <el-input v-model="state.form.name" autocomplete="off"></el-input>
      </el-form-item>
      <el-form-item
        prop="nickname"
        label="닉네임"
        :label-width="state.formLabelWidth"
      >
        <el-input
          style="float:left; width:70%"
          v-model="state.form.nickname"
          autocomplete="off"
          @input="onInputNicknameForm"
        ></el-input>
        <el-button
          style="float:right; width:28%"
          class="form-btn"
          @click="checkDuplicatedNickname"
          >중복 확인</el-button
        >
      </el-form-item>
      <el-form-item
        prop="job"
        label="분류"
        :label-width="state.formLabelWidth"
        @click="clickType"
      >
        <el-radio v-model="state.form.type" label="002">트레이너</el-radio>
        <el-radio v-model="state.form.type" label="003">수강생</el-radio>
      </el-form-item>
      <el-form-item
        prop="phone"
        label="휴대전화 번호"
        :label-width="state.formLabelWidth"
      >
        <el-input
          v-model="state.form.phone"
          autocomplete="off"
          placeholder="-를 제외하고 숫자만 입력해주세요"
          @keyup.enter="clickRegister"
        ></el-input>
      </el-form-item>
      <el-form-item
        prop="desc"
        label="트레이너 설명"
        :label-width="state.formLabelWidth"
        v-if="state.form.type == `002`"
      >
        <el-input
          v-model="state.form.desc"
          autocomplete="off"
          placeholder="트레이너는 필수 입력 사항입니다"
          @keyup.enter="clickRegister"
        ></el-input>
      </el-form-item>
    </el-form>

    <template #footer>
      <span class="dialog-footer">
        <el-button
          v-if="state.joinValid"
          class="form-btn"
          @click="clickRegister"
          >가입하기</el-button
        >
        <el-button v-else class="form-btn" disabled>가입하기</el-button>
      </span>
    </template>
  </el-dialog>
</template>

<script>
import { reactive, computed, ref } from "vue";
import { useStore } from "vuex";
import { ElMessage } from "element-plus";

export default {
  name: "join-dialog",

  props: {
    open: {
      type: Boolean,
      default: false
    }
  },

  setup(props, { emit }) {
    const store = useStore();
    const joinForm = ref(null);
    const loading = ref(false);

    const validateEmail = (rules, value, callback) => {
      const email = value;
      const emailTest = /^[A-Za-z0-9_\.\-]+@[A-Za-z0-9\-]+\.[A-Za-z0-9\-]+/;

      if (emailTest.test(email) == false) {
        state.inputAvail.email = false;
        checkAvail();
        callback(new Error("이메일 형식이 올바르지 않습니다."));
      } else {
        state.inputAvail.email = true;
        checkAvail();
        callback();
      }
    };

    const validatePwd = (rule, value, callback) => {
      const num = value.search(/[0-9]/g);
      const eng = value.search(/[a-z]/gi);
      const spe = value.search(/[^`~!@$!%*#^?&\\(\\)\-_=+]/gi);

      if (value === "") {
        state.inputAvail.pwd = false;
        checkAvail();
        callback(new Error("비밀번호를 입력해 주세요."));
      } else if (value.length < 9) {
        state.inputAvail.pwd = false;
        checkAvail();
        callback(new Error("최소 9글자를 입력해야 합니다."));
      } else if (value.length > 16) {
        state.inputAvail.pwd = false;
        checkAvail();
        callback(new Error("최대 16자까지 입력 가능합니다."));
      } else if (num < 0 || eng < 0 || spe < 0) {
        state.inputAvail.pwd = false;
        checkAvail();
        callback(
          new Error("비밀번호는 영문, 숫자, 특수문자가 조합되어야 합니다.")
        );
      } else {
        state.inputAvail.pwd = true;
        checkAvail();
        callback();
      }
    };

    const validatePwdChk = (rule, value, callback) => {
      if (value === "") {
        state.inputAvail.pwdChk = false;
        checkAvail();
        callback(new Error("비밀번호를 입력해 주세요."));
      } else if (value !== state.form.pwd) {
        state.inputAvail.pwdChk = false;
        checkAvail();
        callback(new Error("입력한 비밀번호와 일치하지 않습니다."));
      } else {
        state.inputAvail.pwdChk = true;
        checkAvail();
        callback();
      }
    };

    const validateName = (rule, value, callback) => {
      if (value === "") {
        state.inputAvail.name = false;
        checkAvail();
        callback(new Error("이름을 입력해주세요"));
      } else if (value.length > 30) {
        state.inputAvail.name = false;
        checkAvail();
        callback(new Error("최대 30자까지 입력 가능합니다."));
      } else {
        state.inputAvail.name = true;
        checkAvail();
        callback();
      }
    };

    const validateNickname = (rule, value, callback) => {
      if (value === "") {
        state.inputAvail.nickname = false;
        checkAvail();
        callback(new Error("P;T에서 사용하실 닉네임을 입력해주세요."));
      } else if (value.length > 10) {
        state.inputAvail.nickname = false;
        checkAvail();
        callback(new Error("최대 10자까지 입력 가능합니다."));
      } else {
        state.inputAvail.nickname = true;
        checkAvail();
        callback();
      }
    };

    const validatePhone = (rule, value, callback) => {
      const number = /^[0-9]*$/;
      if (value === "") {
        state.inputAvail.phone = false;
        checkAvail();
        callback(new Error("휴대전화번호를 입력해주세요."));
      } else if (!number.test(value)) {
        state.inputAvail.phone = false;
        checkAvail();
        callback(new Error("숫자만 입력해주세요."));
      } else if (value.length != 11) {
        state.inputAvail.phone = false;
        checkAvail();
        callback(new Error("휴대전화번호 11자리를 입력해주세요."));
        // 숫자만 들어오게 조건 추가
      } else {
        state.inputAvail.phone = true;
        checkAvail();
        callback();
      }
    };

    const validateDesc = (rule, value, callback) => {
      if (value === "") {
        state.inputAvail.desc = false;
        checkAvail();
        callback(new Error("트레이너는 필수 입력입니다."));
      } else if (value.length > 200) {
        state.inputAvail.desc = false;
        checkAvail();
        callback(new Error("최대 200자까지 입력 가능합니다."));
      } else {
        state.inputAvail.desc = true;
        checkAvail();
        callback();
      }
    };

    const clickGender = function() {
      state.inputAvail.gender = true;
      checkAvail();
    };

    const clickType = function() {
      state.inputAvail.type = true;
      checkAvail();
    };

    const state = reactive({
      form: {
        gender: "",
        name: "",
        email: "",
        pwd: "",
        pwdChk: "",
        type: "",
        profile: "",
        nickname: "",
        desc: "",
        phone: "",
        align: "left"
      },
      rules: {
        email: [
          { required: true, validator: validateEmail, trigger: "change" }
        ],
        // gender: [
        //   { required: true, message: "성별을 선택해주세요.", trigger: "change" }
        // ],
        name: [{ required: true, validator: validateName, trigger: "change" }],

        pwd: [{ required: true, validator: validatePwd, trigger: "change" }],
        pwdChk: [
          { required: true, validator: validatePwdChk, trigger: "change" }
        ],
        // type: [
        //   { required: true, message: "분류를 선택해주세요.", trigger: "change" }
        // ],
        nickname: [
          { required: true, validator: validateNickname, trigger: "change" }
        ],
        desc: [{ required: true, validator: validateDesc, trigger: "change" }],
        phone: [{ required: true, validator: validatePhone, trigger: "change" }]
      },
      dialogVisible: computed(() => props.open),
      formLabelWidth: "120px",
      inputAvail: {
        email: false,
        gender: false,
        name: false,
        pwd: false,
        pwdChk: false,
        type: false,
        nickname: false,
        desc: false,
        phone: false
      },
      joinValid: false,
      isEmailChecked: false,
      isNicknameChecked: false
    });

    const checkAvail = function() {
      // console.log(
      //   state.inputAvail.email +
      //     " " +
      //     state.inputAvail.pwd +
      //     " " +
      //     state.inputAvail.pwdChk +
      //     " " +
      //     state.inputAvail.gender +
      //     " " +
      //     state.inputAvail.name +
      //     " " +
      //     state.inputAvail.nickname +
      //     " " +
      //     state.inputAvail.type +
      //     " " +
      //     state.inputAvail.phone
      // );
      if (state.form.type == "002") {
        if (
          state.inputAvail.email &&
          state.inputAvail.pwd &&
          state.inputAvail.pwdChk &&
          //state.inputAvail.gender &&
          state.inputAvail.name &&
          state.inputAvail.nickname &&
          //state.inputAvail.type &&
          state.inputAvail.desc &&
          state.inputAvail.phone
        ) {
          state.joinValid = true;
        } else {
          state.joinValid = false;
        }
      } else {
        if (
          state.inputAvail.email &&
          state.inputAvail.pwd &&
          state.inputAvail.pwdChk &&
          //state.inputAvail.gender &&
          state.inputAvail.name &&
          state.inputAvail.nickname &&
          //state.inputAvail.type &&
          //state.inputAvail.desc &&
          state.inputAvail.phone
        ) {
          state.joinValid = true;
        } else {
          state.joinValid = false;
        }
      }
    };

    const clickRegister = function() {
      if (!state.isEmailChecked) {
        ElMessage({
          message: "이메일 중복체크를 해주세요.",
          type: "error"
        });
      } else if (!state.isNicknameChecked) {
        ElMessage({
          message: "닉네임 중복체크를 해주세요.",
          type: "error"
        });
      } else if (!state.inputAvail.gender) {
        ElMessage({
          message: "성별을 선택해주세요.",
          type: "error"
        });
      } else if (!state.inputAvail.type) {
        ElMessage({
          message: "분류를 선택해주세요.",
          type: "error"
        });
      } else {
        if (state.form.type === "002" && state.form.desc === "") {
          ElMessage({
            message: "트레이너 설명을 입력해주세요.",
            type: "error"
          });
        } else {
          loading.value = true;
          store
            .dispatch("root/register", {
              userGender: state.form.gender,
              userName: state.form.name,
              userEmail: state.form.email,
              userPwd: state.form.pwd,
              userType: state.form.type,
              userProfile: state.form.profile,
              userNickname: state.form.nickname,
              userDesc: state.form.desc,
              userPhone: state.form.phone
            })
            .then(function(result) {
              // localStorage 에 jwt 토큰 저장
              ElMessage({
                message: "회원가입이 완료되었습니다.",
                type: "success"
              });
              loading.value = false;
              emit("closeJoinDialog");
            })
            .catch(function(err) {
              ElMessage({
                message: err.response.data.message,
                type: "error"
              });
              loading.value = false;
              emit("closeJoinDialog");
            });
        }
      }
    };

    const handleClose = function() {
      state.form.gender = "";
      state.form.name = "";
      state.form.email = "";
      state.form.pwd = "";
      state.form.pwdChk = "";
      state.form.type = "";
      state.form.nickname = "";
      state.form.desc = "";
      state.form.phone = "";
      emit("closeJoinDialog");
    };

    const checkDuplicatedEmail = function() {
      console.log("이메일 중복검사 클릭");
      joinForm.value.validateField("email", err => {
        if (err === "") {
          store
            .dispatch("root/checkDuplicatedEmail", {
              userEmail: state.form.email
            })
            .then(result => {
              ElMessage({
                message: "사용 가능한 이메일입니다.",
                type: "success"
              });
              state.isEmailChecked = true;
              state.inputAvail.email = true;
              checkAvail();
            })
            .catch(err => {
              // 409 error 처리해줘야 함!
              ElMessage({
                message: err.response.data.message,
                type: "error"
              });
              state.isEmailChecked = false;
              checkAvail();
            });
        }
      });
    };

    const checkDuplicatedNickname = function() {
      console.log("닉네임 중복검사 클릭");
      joinForm.value.validateField("nickname", err => {
        if (err === "") {
          store
            .dispatch("root/checkDuplicatedNickname", {
              userNickname: state.form.nickname
            })
            .then(result => {
              ElMessage({
                message: "사용 가능한 닉네임입니다.",
                type: "success"
              });
              state.inputAvail.nickname = true;
              state.isNicknameChecked = true;
              checkAvail();
            })
            .catch(err => {
              state.isNicknameChecked = false;
              checkAvail();
              ElMessage({
                message: err.response.data.message,
                type: "error"
              });
            });
        }
      });
    };

    return {
      joinForm,
      loading,
      state,
      clickRegister,
      handleClose,
      checkDuplicatedEmail,
      checkDuplicatedNickname,
      checkAvail,
      clickGender,
      clickType
    };
  }
};
</script>
<style>
.join-dialog {
  width: 500px !important;
  height: 705px !important;
}
.join-dialog .el-dialog__headerbtn {
  float: right;
}
.join-dialog .el-form-item__content {
  margin-left: 0 !important;
  width: 200px;
  display: inline-block;
}
.join-dialog .el-form-item {
  margin-bottom: 20px;
}
.join-dialog .el-form-item__error {
  font-size: 10px;
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
.form-btn {
  color: white;
  background-color: #00c0d4;
  padding: 10px !important;
}
.form-btn:hover {
  color: #00c0d4;
  background-color: white;
}
</style>
