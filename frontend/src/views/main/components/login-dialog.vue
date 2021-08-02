<template>
  <el-dialog custom-class="login-dialog" title="로그인" v-model="state.dialogVisible" @close="handleClose">
    <el-form v-loading="loading" :model="state.form" :rules="state.rules" ref="loginForm" :label-position="state.form.align">
      <el-form-item prop="id" label="아이디(이메일)" :label-width="state.formLabelWidth" >
        <el-input v-model="state.form.id" autocomplete="off" @input="onInputForm"></el-input>
      </el-form-item>
      <el-form-item prop="password" label="비밀번호" :label-width="state.formLabelWidth">
        <el-input v-model="state.form.password" autocomplete="off" show-password @input="onInputForm"></el-input>
      </el-form-item>
    </el-form>
    <template #footer>
      <span class="dialog-footer">
        <el-button v-if="!loginValid" type="primary" @click="clickLogin" disabled>로그인</el-button>
        <el-button v-else type="primary" @click="clickLogin">로그인</el-button>
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
</style>

<script>
import { reactive, computed, ref, onMounted } from 'vue'
import { useStore } from 'vuex'

export default {
  name: 'login-dialog',

  props: {
    open: {
      type: Boolean,
      default: false
    }
  },

  setup(props, { emit }) {
    const store = useStore()
    // 마운드 이후 바인딩 될 예정 - 컨텍스트에 노출시켜야함. <return>
    const loginForm = ref(null)
    const loginValid = ref(false)
    const loading = ref(false)

    /*
      // Element UI Validator
      // rules의 객체 키 값과 form의 객체 키 값이 같아야 매칭되어 적용됨
      //
    */
    const validateId = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('필수 입력 항목입니다.'))
      } else if (value.length > 16) {
        callback(new Error('최대 16자까지 입력 가능합니다.'))
      } else {
        callback()
      }
    }

    const validatePassword = (rule, value, callback) => {
      const num = value.search(/[0-9]/g)
      const eng = value.search(/[a-z]/ig)
      const spe = value.search(/[`~!@#$%^&*|₩₩₩'₩";:₩/?]/gi)

      if (value === '') {
        callback(new Error('필수 입력 항목입니다.'))
      } else if (value.length < 9 ) {
        callback(new Error('최소 9 글자를 입력해야 합니다.'))
      } else if (value.length > 16) {
        callback(new Error('최대 16자까지 입력 가능합니다.'))
      } else if (num < 0 || eng < 0 || spe < 0) {
        callback(new Error('비밀번호는 영문, 숫자, 특수문자가 조합되어야 합니다.'))
      } else {
        callback()
      }
    }

    const state = reactive({
      form: {
        id: '',
        password: '',
        align: 'left'
      },
      rules: {
        id: [
          { required: true, validator: validateId, trigger: 'blur' }
        ],
        password: [
          { required: true, validator: validatePassword, trigger: 'blur' }
        ],
      },
      dialogVisible: computed(() => props.open),
      formLabelWidth: '120px',
      isLogined: computed(() =>
        store.getters['root/getIsLogined']
      )
    })

    onMounted(() => {
      console.log(loginForm.value)
    })

    const clickLogin = function () {
      // 로그인 클릭 시 validate 체크 후 그 결과 값에 따라, 로그인 API 호출 또는 경고창 표시
      loginForm.value.validate((valid) => {
        if (valid) {
          console.log('submit')

          loading.value = true

          store.dispatch('root/requestLogin', { userId: state.form.id, userPwd: state.form.password })
          .then(function (result) {

            // localStorage 에 jwt 토큰 저장
            localStorage.setItem('jwt-auth-token', result.data.accessToken)
            store.commit('root/setIsLogined', true)
            loading.value = false
            emit('closeLoginDialog')
          })
          .catch(function (err) {
            alert(err.response.data.message)
            loading.value = false
            emit('closeLoginDialog')
          })

        } else {
          alert('Validate error!')
        }
      })
    }

    const handleClose = function () {
      state.form.id = ''
      state.form.password = ''
      emit('closeLoginDialog')
    }

    const onInputForm = function () {
      loginForm.value.validate((valid) => {
        loginValid.value = valid
      })
    }

    return { loginForm, loginValid, loading, state, clickLogin, handleClose, onInputForm }
  }
}
</script>
