<template>
  <div class="content-wrapper">
    <div class="submenu-title">마이페이지</div>
    <!-- <el-row class="tac"> -->
    <div class="tac row">
      <!-- <el-col :span="8"> -->
      <div class="col-lg-4 col-12" id="profile-content">
        <div class="profileUpload">
          <el-upload
            class="avatar-uploader"
            action="v1/users/image"
            accept="image/jpeg"
            :show-file-list="false"
            :on-success="handleAvatarSuccess"
            :before-upload="beforeAvatarUpload"
          >
            <img
              v-if="state.form.profile"
              :src="state.form.profile"
              class="avatar"
            />
            <i v-else class="el-icon-plus avatar-uploader-icon"></i>
          </el-upload>
          <el-button
            v-if="state.form.profile"
            class="form-btn"
            @click="clickDeleteProfile"
            >기본 이미지로 변경
          </el-button>
          <el-button
            v-else
            class="form-btn"
            @click="clickDeleteProfile"
            style="display:none"
            >기본 이미지로 변경
          </el-button>
          <el-button
          class="form-btn" @click="updateProfile"
          style="margin: 0;"
            >프로필 적용
          </el-button>
        </div>
      <!-- </el-col> -->
      </div>

      <!-- <el-col :span="16"> -->
      <div class="col-lg-8 col-12">
        <el-form
          v-loading="loading"
          :model="state.form"
          :rules="state.rules"
          ref="updateForm"
          :label-position="state.form.align"
        >
          <el-form-item label="분류" :label-width="state.formLabelWidth">
            <el-label v-model="state.form.type">{{ state.form.type }}</el-label>
          </el-form-item>
          <el-form-item
            prop="email"
            label="이메일"
            :label-width="state.formLabelWidth"
          >
            <el-input v-model="state.form.email" disabled>{{
              state.form.email
            }}</el-input>
          </el-form-item>
          <el-form-item
            prop="name"
            label="이름"
            :label-width="state.formLabelWidth"
          >
            <el-input v-model="state.form.name" @input="onInputForm"></el-input>
          </el-form-item>
          <el-form-item
            prop="nickname"
            label="닉네임"
            :label-width="state.formLabelWidth"
          >
            <el-input
              id="nickname-input"
              style="float:left; width:70%"
              v-model="state.form.nickname"
            ></el-input>
            <el-button
              id="nickname-check"
              style="float:right; width:28%"
              class="form-btn"
              @click="checkDuplicatedUpdateNickname"
              >중복확인</el-button
            >
          </el-form-item>
          <el-form-item
            prop="pwd"
            label="비밀번호"
            :label-width="state.formLabelWidth"
          >
            <el-input
              v-model="state.form.pwd"
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
              show-password
              @input="onInputForm"
            ></el-input>
          </el-form-item>
          <el-form-item
            prop="phone"
            label="휴대전화 번호"
            :label-width="state.formLabelWidth"
          >
            <el-input
              v-model="state.form.phone"
              @input="onInputForm"
              placeholder="-를 제외하고 숫자만 입력해주세요"
              >{{ state.form.phone }}</el-input
            >
          </el-form-item>
          <el-form-item label="상세 정보" :label-width="state.formLabelWidth">
            <el-input
              type="textarea"
              :rows="6"
              v-model="state.form.desc"
              maxlength="200"
              show-word-limit
              @input="onInputForm"
              placeholder="트레이너는 필수 입력 사항입니다"
            >
            </el-input>
            <div class="update-footer">
              <el-button
                v-if="!updateValid"
                class="form-btn"
                @click="clickUpdateUser"
                disabled
                >수정하기</el-button
              >
              <el-button v-else class="form-btn" @click="clickUpdateUser"
                >수정하기</el-button
              >
              <el-button class="form-btn-delete" @click="clickDeleteUser"
              style="margin: 0;"
                >탈퇴하기</el-button
              >
            </div>
          </el-form-item>
        </el-form>
      <!-- </el-col> -->
      </div>
    <!-- </el-row> -->
    </div>
  </div>
</template>

<script>
import { reactive, computed, ref, onMounted, message } from "vue";
import { useStore } from "vuex";
import { useRouter } from "vue-router";
import { ElMessage } from "element-plus";

export default {
  name: "MyClass",

  components: {},

  props: {
    open: {
      type: Boolean,
      default: true
    }
  },

  setup(props, { emit }) {
    const store = useStore();
    const router = useRouter();
    const updateForm = ref(null);
    const updateValid = ref(false);
    const isNicknameAvailable = ref(false);
    const loading = ref(false);

    /*
      type
      Email : 이메일은 변경불가 input type 수정 disabled
      name
      nickname : 닉네임 중복확인 필요
      pwd : 비밀번호 validation 필요
      pwdChk : 비밀번호 validation 필요
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
        callback(new Error("비밀번호를 입력해 주세요"));
      } else if (value.length < 9) {
        callback(new Error("최소 9 글자를 입력해야 합니다"));
      } else if (value.length > 16) {
        callback(new Error("최대 16자까지 입력 가능합니다"));
      } else if (num < 0 || eng < 0 || spe < 0) {
        callback(
          new Error("비밀번호는 영문, 숫자, 특수문자가 조합되어야 합니다")
        );
      } else if (value !== state.form.pwd) {
        callback(new Error("입력한 비밀번호와 일치하지 않습니다"));
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
        desc: "",
        phone: "",
        profile: "",
        type: "",
        email: "",
        name: "",
        nickname: "",
        pwd: "",
        pwdChk: "",
        align: "left"
      },
      rules: {
        email: [{ required: true, validator: validateEmail, trigger: "blur" }],
        name: [{ required: true, validator: validateName, trigger: "blur" }],
        nickname: [
          { required: true, validator: validateNickname, trigger: "blur" }
        ],
        pwd: [{ required: true, validator: validatePwd, trigger: "blur" }],
        pwdChk: [
          { required: true, validator: validatePwdChk, trigger: "blur" }
        ],
        desc: [{ validator: validateDesc, trigger: "blur" }],
        phone: [{ required: true, validator: validatePhone, trigger: "blur" }]
      },
      formLabelWidth: "120px"
    });

    const handleAvatarSuccess = function(res, file) {
      state.form.profile = URL.createObjectURL(file.raw);
      console.log("업로드 후 profile : " + state.form.profile);
    };

    const beforeAvatarUpload = function(file) {
      const isJPG = file.type === "image/jpeg";
      const isLt2M = file.size / 1024 / 1024 < 10;

      if (!isLt2M) {
        ElMessage({
          message: "10MB 이하의 파일만 업로드 가능합니다.",
          type: "error"
        })
        // this.$message.error("Avatar picture size can not exceed 2MB!");
      }
      return isJPG && isLt2M;
    };

    onMounted(() => {
      // console.log(updateForm.value);
      getUserInfo();
    });

    const getUserInfo = function() {
      store
        .dispatch("root/getUserInfo")
        .then(function(result) {
          state.form.gender = result.data.userGenderName;
          state.form.desc = result.data.userDesc;
          state.form.phone = result.data.userPhone;
          state.form.profile = result.data.userProfile;
          state.form.type = result.data.userTypeName;
          state.form.email = result.data.userEmail;
          state.form.name = result.data.userName;
          state.form.nickname = result.data.userNickname;
          // console.log("수정 전 profile : " + state.form.profile);
          loading.value = false;
        })
        .catch(function(err) {
          //alert(err.response.data.message);
          //error 코드 == 403 -> 접근못하게
          loading.value = false;
        });
    };

    const checkDuplicatedUpdateNickname = function() {
      console.log("닉네임 중복검사 클릭");
      updateForm.value.validateField("nickname", err => {
        if (err === "") {
          store
            .dispatch("root/checkDuplicatedUpdateNickname", {
              userNickname: state.form.nickname
            })
            .then(result => {
              alert("사용 가능한 닉네임입니다.");
              isNicknameAvailable.value = true;
              onInputForm();
            })
            .catch(err => {
              // 409 error 처리해줘야 함!

              alert(err.response.data.message);
            });
        }
      });
      console.log(isNicknameAvailable.value);
    };

    const clickUpdateUser = function() {
      updateForm.value.validate(valid => {
        if (valid) {
          loading.value = true;
          store
            .dispatch("root/updateUserInfo", {
              userProfile: state.form.profile,
              userName: state.form.name,
              userPwd: state.form.pwd,
              userNickname: state.form.nickname,
              userDesc: state.form.desc,
              userPhone: state.form.phone,
              token: "Bearer " + localStorage.getItem("jwt-auth-token")
            })
            .then(function() {
              alert("회원정보가 수정되었습니다.");
              loading.value = false;
              store.state.profileUrl = state.form.profile;
              store.commit("root/setUserNickname", store.state.userNickname);
              store.commit("root/setProfileUrl", store.state.profileUrl);
              console.log(state.form.profile);
              console.log(store.state.profileUrl);
            })
            .catch(function(err) {
              alert(err.response.data.message);
              loading.value = false;
            });
        }
      });
    };

    const clickDeleteProfile = function() {
      alert("프로필 사진을 기본 이미지로 변경하시겠습니까?");
      state.form.profile = "";
    };

    const updateProfile = function() {
      store
        .dispatch("root/updateProfile", {
          profile: state.form.profile,
          token: "Bearer " + localStorage.getItem("jwt-auth-token")
        })
        .then(function() {
          alert("프로필 사진이 변경되었습니다.");
          store.state.profileUrl = state.form.profile;
          store.commit("root/setProfileUrl", store.state.profileUrl);
        })
        .catch(function(err) {
          alert(err.response.data.message);
        });
    };

    const clickDeleteUser = function() {
      store
        .dispatch("root/deleteUser", {
          token: "Bearer" + localStorage.getItem("jwt-auth-token")
        })
        .then(function() {
          alert("회원탈퇴에 성공하였습니다. Pit를 이용해주셔서 감사합니다.");
          localStorage.removeItem("jwt-auth-token");
          store.commit("root/setIsLogined", false);
          router.push("/");
        })
        .catch(function(err) {
          // alert(err.response.data.message);
        });
    };

    const onInputForm = function() {
      updateForm.value.validate(valid => {
        // updateValid.value = valid & isNicknameAvailable.value;
        updateValid.value = valid;
      });
    };

    const onInputNicknameForm = function() {
      isNicknameAvailable.value = false;
      onInputForm();
    };

    return {
      state,
      updateForm,
      updateValid,
      isNicknameAvailable,
      loading,
      getUserInfo,
      onInputForm,
      onInputNicknameForm,
      checkDuplicatedUpdateNickname,
      clickUpdateUser,
      handleAvatarSuccess,
      beforeAvatarUpload,
      clickDeleteProfile,
      updateProfile,
      clickDeleteUser
    };
  }
};
</script>

<style>
.name {
  font-size: 30px;
  font-weight: bold;
  margin-top: 50px;
  margin-bottom: 50px;
}
.form-btn {
  color: white;
  background-color: #00c0d4;
}
.form-btn:hover {
  color: #00c0d4;
  background-color: white;
}
.form-btn-delete {
  color: white;
  background-color: rgb(224, 126, 106);
}
.form-btn-delete:hover {
  color: rgb(224, 126, 106);
  background-color: white;
}

.el-icon-plus.avatar-uploader-icon {
  display: flex;
  justify-content: center;
  align-items: center;
}

.avatar-uploader {
  padding: 70px 0;
}

.avatar-uploader .el-upload {
  border: 1px solid #d9d9d9;
  border-radius: 50%;
  cursor: pointer;
  position: relative;
  overflow: hidden;
}
.avatar-uploader .el-upload:hover {
  border-color: #00c0d4;
}
.avatar-uploader-icon {
  font-size: 24px;
  color: #8c939d;
  width: 178px;
  height: 178px;
  line-height: 178px;
  /* text-align: center; */
}
.avatar {
  width: 178px;
  height: 178px;
  display: block;
}
.profileUpload {
  text-align: center;
}
img {
  max-width: 100%;
  height: auto;
}
.update-footer {
  margin: 15px;
  text-align: center;
}

/* 민영 수정 시작 */
@media (max-width: 992px) {
  #profile-content {
    border-bottom: solid 1.2px #ebeef5;
    margin-bottom: 35px;
    padding-bottom: 35px;
  }
}

/* 민영 수정 끝 */
</style>
