<template>
  <div class="gallerySection">
    <el-image
      style="width: 49.8%;"
      :src="srcList[0]"
      :preview-src-list="srcList"
      fit="cover"
    />
    <div class="right">
      <div class="gallerySection-second">
        <el-image :src="srcList[1]" :preview-src-list="srcList" fit="cover" />
      </div>
      <div class="gallerySection-third">
        <el-image
          :src="srcList[2]"
          :preview-src-list="srcList"
          fit="cover"
          style="margin-right: 6px;"
        />
        <el-image :src="srcList[3]" :preview-src-list="srcList" fit="cover" />
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: "AdminClassDetail",
  data() {
    return {
      url:
        "https://images.unsplash.com/photo-1506126613408-eca07ce68773?ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=731&q=80",
      srcList: [
        "https://images.unsplash.com/photo-1506126613408-eca07ce68773?ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=731&q=80",
        "https://images.unsplash.com/photo-1611094601537-cdbb75b979cc?ixid=MnwxMjA3fDB8MHxzZWFyY2h8MjEzfHx5b2dhfGVufDB8fDB8fA%3D%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=500&q=60",
        "https://images.unsplash.com/photo-1604431696980-07e518647bec?ixid=MnwxMjA3fDB8MHxzZWFyY2h8ODB8fHlvZ2F8ZW58MHx8MHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=500&q=60",
        "https://images.unsplash.com/photo-1562088287-bde35a1ea917?ixid=MnwxMjA3fDB8MHxzZWFyY2h8MTB8fHlvZ2F8ZW58MHx8MHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=500&q=60",
        "https://images.unsplash.com/photo-1586035758264-c5c685f55e9c?ixid=MnwxMjA3fDB8MHxzZWFyY2h8MjN8fHlvZ2F8ZW58MHx8MHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=500&q=60"
      ]
    };
  }
};
</script>

<style>
.content {
  display: flex;
  justify-content: space-between;
}
</style>
