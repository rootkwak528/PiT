<template>
  <el-row class="main-footer" :gutter="10">
    <div class="contents">
      Copyright © A204 All Rights Reserved.<br />
      HG SY MY YS DH YE Presents.
    </div>
  </el-row>
</template>
<style>
@media (max-width: 700px) {
  .main-footer {
    height: 100px;
  }
  .main-footer .contents {
    line-height: 100px;
    text-align: center;
    margin: 0 auto;
  }
}

@media (min-width: 701px) {
  .main-footer {
    margin-top: 30px;
    height: 100px;
  }
  .main-footer .contents {
    line-height: 40px;
    text-align: center;
    margin: 0 auto;
  }
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
