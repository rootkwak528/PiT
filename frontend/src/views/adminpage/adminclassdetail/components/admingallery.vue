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
  name: "AdminGallery",
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
.content {
  display: flex;
  justify-content: space-between;
}
</style>
