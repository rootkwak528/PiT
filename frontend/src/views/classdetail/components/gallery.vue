<template>
  <div class="gallerySection">
    <el-image
      style="width: 49.8%;"
      :src="state.form.photoList[0]"
      :preview-src-list="state.form.photoList"
      fit="cover"
    />
    <div class="right">
      <div class="gallerySection-second">
        <el-image
          :src="state.form.photoList[1]"
          :preview-src-list="state.form.photoList"
          fit="cover"
        />
      </div>
      <div class="gallerySection-third">
        <el-image
          :src="state.form.photoList[2]"
          :preview-src-list="state.form.photoList"
          fit="cover"
          style="margin-right: 6px;"
        />
        <el-image
          :src="state.form.photoList[3]"
          :preview-src-list="state.form.photoList"
          fit="cover"
        />
      </div>
    </div>
  </div>
</template>

<script>
import { reactive } from "vue";
import { useStore } from "vuex";

export default {
  name: "Gallery",
  props: {
    classNo: String
  },
  setup(props) {
    const store = useStore();
    const state = reactive({
      form: {
        photoList: []
      }
    });

    store
      .dispatch("root/getClassDetail", { classNo: props.classNo })
      .then(function(result) {
        state.form.photoList = result.data.photoUrls;
      })
      .catch(function(err) {
        alert(err);
      });

    return { state };
  }
};
</script>

<style>
.gallerySection {
  display: flex;
  justify-content: space-between;
  width: 100%;
  height: 500px;
}

.right {
  width: 49.8%;
  margin-left: 6px;
}

.gallerySection-second {
  display: flex;
  height: 49.8%;
  margin-bottom: 6px;
}

.gallerySection-third {
  display: flex;
  width: 100%;
  height: 49%;
}

.gallerySection-second > .el-image {
  width: 100%;
}

.gallerySection-third > .el-image {
  width: 50%;
}
</style>
