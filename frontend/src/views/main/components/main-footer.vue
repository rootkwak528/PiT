<template>
  <el-row class="main-footer">
    <div class="footer-container d-block d-sm-flex justify-content-between align-items-end">
      <span class="contents">
        <router-link class="about-btn" to="/about">
          <strong>
            <span class="span-p">P</span>
            <span class="span-i">;</span>
            <span class="span-T">T</span>
            &nbsp&nbsp피땀눈물
          </strong><br><br>
        </router-link>
        피땀눈물은 비대면 상황에서도<br>
        당신의 건강한 생활을 이어가길 응원합니다.
      </span>

      <span class="contents text-end">
        <br><br>
        Copyright©2021 by SSAFY 5th BTS<br>
        요셉 호근 민영 대현 영은
      </span>
    </div>
  </el-row>
</template>
<style>
.main-footer {
  margin-top: 45px;
  font-size: 0.7rem;
  font-weight: 400;
  font-family: ui-sans-serif;
  padding-top: 60px;
  padding-bottom: 80px;
  width: 100vw;
  margin-left: -15vw;
  padding-left: 15vw;
  background-color: #f5f5f5;
  color: #afabab;
}

.main-footer strong {
  color: #948e8e;
}

.footer-container {
  width: 70vw;
}

span.span-p {
  color: #e37461;
}

span.span-i {
  color: #efc273;
}

span.span-T {
  color: #70bfc2;
}

@media (max-width: 700px) {
  .main-footer {
    margin-top: 45px;
    height: auto;
  }
}

@media (max-width: 1360px) {
  .footer-container {
    width: 90vw;
  }
  .main-footer {
    margin-left: -5vw;
    padding-left: 5vw;
  }
}

@media (min-width: 1360px) {
  .main-footer {
    height: auto;
  }
}

.about-btn {
  text-decoration: none;
  font-size: 0.9rem;
}
</style>
<script>
import { reactive } from "vue";
import { useStore } from "vuex";

export default {
  name: "main-footer",

  props: {
    height: {
      type: String,
      default: "100px"
    }
  },

  setup() {
    const state = reactive({});

    return { state };
  }
};
</script>
<style></style>
