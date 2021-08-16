<template>
  <div style="padding: 30px">
    <div v-if="state.videoSaveTimeList.length != 0">
      <el-collapse v-for="(item, index) in state.videoSaveTimeList" :key="item">
        <el-collapse-item :title="item + `에 저장된 동영상`" name="1">
          <video
            ref="videoPlayer"
            class="video-js"
            controls
            preload="auto"
            width="640"
            height="264"
          >
            <source :src="state.videoUrlList[index]" type="video/webm" />
          </video>
        </el-collapse-item>
      </el-collapse>
    </div>
    <div v-else>저장된 동영상이 없어요!</div>
  </div>
</template>

<script>
import { reactive } from "@vue/reactivity";
import { useStore } from "vuex";
import videojs from "video.js";
import { onBeforeUnmount, ref } from "vue";

export default {
  props: {
    classNo: String,
    options: {
      type: Object,
      default() {
        return {};
      }
    }
  },
  setup(props) {
    const store = useStore();
    const videoPlayer = ref(null);
    var player = null;

    const state = reactive({
      videoUrlList: [],
      videoSaveTimeList: []
    });

    store
      .dispatch("root/getVideoUrls", { classNo: props.classNo })
      .then(function(result) {
        console.log(result.data);
        state.videoUrlList = result.data.videoUrls;

        for (var i = 0; i < result.data.videoUrls.length; i++) {
          var date = result.data.videoSaveTimes[i].toString().substring(0, 10);
          var time = result.data.videoSaveTimes[i].toString().substring(11, 19);
          state.videoSaveTimeList.push(date + " " + time);
        }

        player = videojs(
          videoPlayer,
          props.options,
          function onPlyerReady() {}
        );
      })
      .catch(function(err) {
        console.log(err);
      });

    onBeforeUnmount(() => {
      if (player) {
        player.dispose();
      }
    });
    return { state };
  }
};
</script>

<style>
.video-js {
  width: 100%;
  height: 500px;
}
</style>
