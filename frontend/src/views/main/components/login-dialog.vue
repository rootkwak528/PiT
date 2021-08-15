<template>
  <el-dialog
    custom-class="login-dialog"
    title="로그인"
    v-model="state.dialogVisible"
    @close="handleClose"
  >
    <el-form
      v-loading="loading"
      :model="state.form"
      :rules="state.rules"
      ref="loginForm"
      :label-position="state.form.align"
    >
      <el-form-item
        prop="email"
        label="아이디(이메일)"
        :label-width="state.formLabelWidth"
      >
        <el-input
          v-model="state.form.email"
          autocomplete="off"
          @input="onInputForm"
        ></el-input>
      </el-form-item>
      <el-form-item
        prop="pwd"
        label="비밀번호"
        :label-width="state.formLabelWidth"
      >
        <el-input
          v-model="state.form.pwd"
          @keyup.enter="clickLogin"
          autocomplete="off"
          show-password
          @input="onInputForm"
        ></el-input>
      </el-form-item>
    </el-form>
    <template #footer>
      <span class="dialog-footer">
        <el-button
          v-if="!loginValid"
          class="form-btn"
          @click="clickLogin"
          disabled
          >로그인</el-button
        >
        <el-button v-else class="form-btn" @click="clickLogin"
          >로그인</el-button
        >
      </span>
    </template>
  </el-dialog>
</template>

<style>
.login-dialog {
  width: 400px !important;
  height: 300px;
}
.login-dialog .el-dialog__headerbtn {
  float: right;
}
.login-dialog .el-form-item__content {
  margin-left: 0 !important;
  float: right;
  width: 200px;
  display: inline-block;
}
.login-dialog .el-form-item {
  margin-bottom: 20px;
}
.login-dialog .el-form-item__error {
  font-size: 12px;
  color: red;
}
.login-dialog .el-input__suffix {
  display: none;
}
.login-dialog .el-dialog__footer {
  margin: 0 calc(50% - 80px);
  padding-top: 0;
  display: inline-block;
}
.login-dialog .dialog-footer .el-button {
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
import { reactive, computed, ref } from "vue";
import { useStore } from "vuex";

export default {
  name: "login-dialog",

  props: {
    open: {
      type: Boolean,
      default: false
    }
  },

  setup(props, { emit }) {
    const store = useStore();
    // 마운드 이후 바인딩 될 예정 - 컨텍스트에 노출시켜야함. <return>
    const loginForm = ref(null);
    const loginValid = ref(false);
    const loading = ref(false);

    /*
      // Element UI Validator
      // rules의 객체 키 값과 form의 객체 키 값이 같아야 매칭되어 적용됨
      //
    */

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

    const state = reactive({
      form: {
        email: "",
        pwd: "",
        align: "left"
      },
      rules: {
        email: [{ required: true, validator: validateEmail, trigger: "blur" }],
        pwd: [{ required: true, validator: validatePwd, trigger: "blur" }]
      },
      dialogVisible: computed(() => props.open),
      formLabelWidth: "120px",
      isLogined: computed(() => store.getters["root/getIsLogined"])
    });

    const clickLogin = function() {
      // 로그인 클릭 시 validate 체크 후 그 결과 값에 따라, 로그인 API 호출 또는 경고창 표시
      console.log("로그인 클릭");
      loginForm.value.validate(valid => {
        if (valid) {
          console.log("submit");

          loading.value = true;

          store
            .dispatch("root/requestLogin", {
              userEmail: state.form.email,
              userPwd: state.form.pwd
            })
            .then(function(result) {
              // localStorage 에 jwt 토큰 저장
              localStorage.setItem("jwt-auth-token", result.data.accessToken);
              store.commit("root/setIsLogined", true);

              // 회원 정보 조회
              store
                .dispatch("root/getUserInfo")
                .then(function(result) {
                  store.state.profileUrl = result.data.userProfile;
                  store.state.userType = result.data.userTypeName;
                  store.state.userNickname = result.data.userNickname;
                  store.commit("root/setUserType", store.state.userType);
                  store.commit("root/setProfileUrl", store.state.profileUrl);
                  store.commit(
                    "root/setUserNickname",
                    store.state.userNickname
                  );
                  loading.value = false;
                })
                .catch(function(err) {
                  alert(err.response.data.message);
                  loading.value = false;
                });
              loading.value = false;
              emit("closeLoginDialog");
            })
            .catch(function(err) {
              alert(err.response.data.message);
              loading.value = false;
              state.form.pwd = "";
            });
        } else {
          alert("Validate error!");
        }
      });
    };

    const handleClose = function() {
      state.form.email = "";
      state.form.pwd = "";
      emit("closeLoginDialog");
    };

    const onInputForm = function() {
      loginForm.value.validate(valid => {
        loginValid.value = valid;
      });
    };

    return {
      loginForm,
      loginValid,
      loading,
      state,
      clickLogin,
      handleClose,
      onInputForm
    };
  }
};
</script>
