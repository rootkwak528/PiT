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
          @input="onInputEmailForm"
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
          @input="onInputForm"
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
          @input="onInputForm"
        ></el-input>
      </el-form-item>
      <el-form-item
        prop="gender"
        label="성별"
        :label-width="state.formLabelWidth"
      >
        <el-radio v-model="state.form.gender" label="001">남성</el-radio>
        <el-radio v-model="state.form.gender" label="002">여성</el-radio>
      </el-form-item>
      <el-form-item
        prop="name"
        label="이름"
        :label-width="state.formLabelWidth"
      >
        <el-input
          v-model="state.form.name"
          autocomplete="off"
          @input="onInputForm"
        ></el-input>
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
      <el-form-item prop="job" label="분류" :label-width="state.formLabelWidth">
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
          @input="onInputForm"
          placeholder="-를 제외하고 숫자만 입력해주세요"
          @keyup.enter="clickRegister"
        ></el-input>
      </el-form-item>
      <el-form-item
        prop="desc"
        label="상세 정보"
        :label-width="state.formLabelWidth"
      >
        <el-input
          v-model="state.form.desc"
          autocomplete="off"
          @input="onInputForm"
          placeholder="트레이너는 필수 입력 사항입니다"
          @keyup.enter="clickRegister"
        ></el-input>
      </el-form-item>
    </el-form>
    <template #footer>
      <span class="dialog-footer">
        <el-button
          v-if="!joinValid"
          class="form-btn"
          @click="clickRegister"
          disabled
          >가입하기</el-button
        >
        <el-button v-else class="form-btn" @click="clickRegister"
          >가입하기</el-button
        >
      </span>
    </template>
  </el-dialog>
</template>
<style>
.join-dialog {
  width: 500px !important;
  height: 750px !important;
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
}
.form-btn:hover {
  color: #00c0d4;
  background-color: white;
}
</style>
<script>
import { reactive, computed, ref, onMounted } from "vue";
import { useStore } from "vuex";

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
    // 마운드 이후 바인딩 될 예정 - 컨텍스트에 노출시켜야함. <return>
    const joinForm = ref(null);
    const joinValid = ref(false);
    const isEmailAvailable = ref(false);
    const isNicknameAvailable = ref(false);
    const loading = ref(false);

    /*
      // Element UI Validator
      // rules의 객체 키 값과 form의 객체 키 값이 같아야 매칭되어 적용됨
      //
    */

    const validateGender = (rule, value, callback) => {
      if (value === "") {
        callback(new Error("성별을 선택해주세요"));
      } else {
        callback();
      }
    };

    const validateName = (rule, value, callback) => {
      if (value === "") {
        callback(new Error("이름을 입력해주세요"));
      } else if (value.length > 30) {
        callback(new Error("최대 30자까지 입력 가능합니다"));
      } else {
        callback();
      }
    };

    const validateEmail = (rule, value, callback) => {
      const email = value;
      const emailTest = /^[A-Za-z0-9_\.\-]+@[A-Za-z0-9\-]+\.[A-Za-z0-9\-]+/;

      if (emailTest.test(email) == false) {
        callback(new Error("이메일 형식이 올바르지 않습니다"));
      } else {
        callback();
      }
    };

    const validatePwd = (rule, value, callback) => {
      const num = value.search(/[0-9]/g);
      const eng = value.search(/[a-z]/gi);
      const spe = value.search(/[`~!@#$%^&*|₩₩₩'₩";:₩/?]/gi);

      if (value === "") {
        callback(new Error("비밀번호를 입력해 주세요"));
      } else if (value.length < 9) {
        callback(new Error("최소 9 글자를 입력해야 합니다"));
      } else if (value.length > 16) {
        callback(new Error("최대 16자까지 입력 가능합니다"));
      } else if (num < 0 || eng < 0 || spe < 0) {
        callback(
          new Error("비밀번호는 영문, 숫자, 특수문자가 조합되어야 합니다")
        );
      } else {
        callback();
      }
    };

    const validatePwdChk = (rule, value, callback) => {
      const num = value.search(/[0-9]/g);
      const eng = value.search(/[a-z]/gi);
      const spe = value.search(/[`~!@#$%^&*|₩₩₩'₩";:₩/?]/gi);

      if (value === "") {
        callback(new Error("비밀번호를 입력해 주세요."));
      } else if (value !== state.form.pwd) {
        callback(new Error("입력한 비밀번호와 일치하지 않습니다."));
      } else {
        callback();
      }
    };

    const validateNickname = (rule, value, callback) => {
      if (value === "") {
        callback(new Error("P;T에서 사용하실 닉네임을 입력해주세요"));
      } else if (value.length > 10) {
        callback(new Error("최대 10자까지 입력 가능합니다"));
      } else {
        callback();
      }
    };

    const validatePhone = (rule, value, callback) => {
      if (value === "") {
        callback(new Error("휴대전화번호를 입력해주세요"));
      } else if (value.length != 11) {
        callback(new Error("휴대전화번호 11자리를 입력해주세요"));
        // 숫자만 들어오게 조건 추가
      } else {
        callback();
      }
    };

    const validateDesc = (rule, value, callback) => {
      if (value.length > 200) {
        callback(new Error("최대 200자까지 입력 가능합니다"));
      } else {
        callback();
      }
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
        gender: [
          { required: true, validator: validateGender, trigger: "change" }
        ],
        name: [{ required: true, validator: validateName, trigger: "blur" }],
        email: [{ required: true, validator: validateEmail, trigger: "blur" }],
        pwd: [{ required: true, validator: validatePwd, trigger: "blur" }],
        pwdChk: [
          { required: true, validator: validatePwdChk, trigger: "blur" }
        ],
        type: [{ required: true, trigger: "blur" }],
        nickname: [
          { required: true, validator: validateNickname, trigger: "blur" }
        ],
        desc: [{ validator: validateDesc, trigger: "blur" }],
        phone: [{ required: true, validator: validatePhone, trigger: "blur" }]
      },
      dialogVisible: computed(() => props.open),
      formLabelWidth: "120px"
    });

    const clickRegister = function() {
      // 로그인 클릭 시 validate 체크 후 그 결과 값에 따라, 로그인 API 호출 또는 경고창 표시
      joinForm.value.validate(valid => {
        if (valid) {
          console.log("submit");

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
              alert("회원 가입이 완료되었습니다.");
              loading.value = false;
              emit("closeJoinDialog");
            })
            .catch(function(err) {
              alert(err.response.data.message);
              loading.value = false;
              emit("closeJoinDialog");
            });
        } else {
          alert("회원 가입에 실패하였습니다.");
        }
      });
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
              alert("사용 가능한 이메일입니다.");
              isEmailAvailable.value = true;
              onInputForm();
            })
            .catch(err => {
              // 409 error 처리해줘야 함!

              alert(err.response.data.message);
            });
        }
      });
      console.log(isEmailAvailable.value);
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
              alert("사용 가능한 닉네임입니다.");
              isNicknameAvailable.value = true;
              onInputForm();
            })
            .catch(err => {
              alert(err.response.data.message);
            });
        }
      });
      console.log(isNicknameAvailable.value);
    };

    const onInputForm = function() {
      joinForm.value.validate(valid => {
        joinValid.value =
          valid & isEmailAvailable.value & isNicknameAvailable.value;
      });
    };

    const onInputEmailForm = function() {
      isEmailAvailable.value = false;
      onInputForm();
    };

    const onInputNicknameForm = function() {
      isNicknameAvailable.value = false;
      onInputForm();
    };

    return {
      joinForm,
      joinValid,
      isEmailAvailable,
      isNicknameAvailable,
      loading,
      state,
      clickRegister,
      handleClose,
      checkDuplicatedEmail,
      checkDuplicatedNickname,
      onInputForm,
      onInputEmailForm,
      onInputNicknameForm
    };
  }
};
</script>
