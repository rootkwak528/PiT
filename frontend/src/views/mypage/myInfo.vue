<template>
  <div class="name">
    {{ username }}
  </div>
  <div>
    <el-row class="tac">
      <el-col :span="6">
        <mypage-sidebar />
      </el-col>

      <el-col :span="18">

        <el-row class="tac">
          <el-col :span="8">
            프로필 사진이다 내 얼굴!!!!!!!
          </el-col>
          <el-col :span="16">


            <el-form v-loading="loading" :model="state.form" :rules="state.rules" ref="updateForm" :label-position="state.form.align">

              <el-form-item label="분류" :label-width="state.formLabelWidth">
                <el-label v-model="state.form.type"></el-label>
              </el-form-item>
              <el-form-item prop="email" label="이메일" :label-width="state.formLabelWidth">
                <el-input v-model="state.form.email"></el-input>
              </el-form-item>
              <el-form-item label="이름" :label-width="state.formLabelWidth">
                <el-input v-model="state.form.name"></el-input>
              </el-form-item>
              <el-form-item label="닉네임" :label-width="state.formLabelWidth">
                <el-input style="float:left; width:70%" v-model="state.form.nickname"></el-input>
                <el-button style="float:right; width:28%">중복확인</el-button>
              </el-form-item>
              <el-form-item label="비밀번호" :label-width="state.formLabelWidth">
                <el-input v-model="state.form.pwd"></el-input>
              </el-form-item>
              <el-form-item label="비밀번호 확인" :label-width="state.formLabelWidth">
                <el-input v-model="state.form.pwdChk"></el-input>
              </el-form-item>

            </el-form>



          </el-col>
        </el-row>

      </el-col>
    </el-row>
  </div>
</template>

<script>
import { reactive, computed, ref, onMounted } from "vue";
import { useStore } from 'vuex';
import MypageSidebar from "./component/mypage-sidebar.vue";

export default {
  name: "MyClass",

  components: {
    MypageSidebar
  },

  props: {
    open: {
      type: Boolean,
      default: true
    }
  },

  setup(props, { emit }) {
    const store = useStore();
    const updateForm = ref(null)
    const updateValid = ref(false)
    const isNicknameAvailable = ref(false)
    const loading = ref(false)

    /*
      type
      Email : 이메일은 변경불가 input type 수정 disabled
      name
      nickname : 닉네임 중복확인 필요
      pwd : 비밀번호 validation 필요
      pwdChk : 비밀번호 validation 필요
    */
    const validateEmail = (rule, value, callback) => {
      const email = value
      const emailTest = /^[A-Za-z0-9_\.\-]+@[A-Za-z0-9\-]+\.[A-Za-z0-9\-]+/

      if(emailTest.test(email) == false){
        callback(new Error("이메일 형식이 올바르지 않습니다"));
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

    const validateNickname = (rule, value, callback) => {
      if (value === "") {
        callback(new Error("P;T에서 사용하실 닉네임을 입력해주세요"));
      } else if (value.length > 10) {
        callback(new Error("최대 10자까지 입력 가능합니다"));
      } else {
        callback();
      }
    };

    const validatePwd = (rule, value, callback) => {
      const num = value.search(/[0-9]/g)
      const eng = value.search(/[a-z]/ig)
      const spe = value.search(/[`~!@#$%^&*|₩₩₩'₩";:₩/?]/gi)

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
      const num = value.search(/[0-9]/g)
      const eng = value.search(/[a-z]/ig)
      const spe = value.search(/[`~!@#$%^&*|₩₩₩'₩";:₩/?]/gi)

      if (value === "") {
        callback(new Error("비밀번호를 입력해 주세요"));
      } else if (value.length < 9) {
        callback(new Error("최소 9 글자를 입력해야 합니다"));
      } else if (value.length > 16) {
        callback(new Error("최대 16자까지 입력 가능합니다"));
      } else if (num < 0 || eng < 0 || spe < 0) {
        callback(new Error('비밀번호는 영문, 숫자, 특수문자가 조합되어야 합니다'))
      } else if (value !== state.form.pwd) {
        callback(new Error('입력한 비밀번호와 일치하지 않습니다'))
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
        gender: '',
        desc: '',
        phone: '',
        profile: '',
        type: '',
        email: '',
        name: '',
        nickname: '',
        pwd: '',
        pwdChk: '',
        align: 'left',
      },
      rules: {
        email: [
          { required: true, validator: validateEmail, trigger: 'blur' }
        ],
        name: [
          { required: true, validator: validateName, trigger: 'blur' }
        ],
        nickname: [
          { required: true, validator: validateNickname, trigger: "blur" }
        ],
        pwd: [
          { required: true, validator: validatePwd, trigger: 'blur' }
        ],
        pwdChk: [
          { required: true, validator: validatePwdChk, trigger: 'blur' }
        ],
        desc: [{ validator: validateDesc, trigger: "blur" }],
        phone: [{ required: true, validator: validatePhone, trigger: "blur" }]
      },
      formLabelWidth: "120px",
    });

    onMounted(() => {
      console.log(updateForm.value);
      console.log(localStorage.getItem('jwt-auth-token'));
      requestUserInfo();
    });

    const requestUserInfo = function() {

      store
        .dispatch("root/requestUserInfo", {
          token : localStorage.getItem('jwt-auth-token')
        })
        .then(function(){
          alert("회원 정보 조회되었습니다.");
          loading.value = false;
        })
        .catch(function(err){
          alert(err.response.data.message);
          loading.value = false;
        })
    }

    return { state, updateForm, updateValid, isNicknameAvailable, loading, requestUserInfo }
  },

};
</script>

<style>
.name {
  font-size: 30px;
  font-weight: bold;
  margin-top: 50px;
  margin-bottom: 50px;
}
</style>
