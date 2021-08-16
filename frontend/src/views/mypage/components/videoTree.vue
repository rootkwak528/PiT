<template>
  <el-tree
    :data="data"
    :props="defaultProps"
    @node-click="handleNodeClick"
  ></el-tree>
  <video-player :options="videoOptions" />
</template>

<script>
import VideoPlayer from "./videoplayer.vue";
export default {
  components: {
    VideoPlayer
  },
  setup() {},
  data() {
    return {
      videoOptions: {
        autoplay: true,
        controls: true,
        sources: [
          {
            src:
              "https://i5a204.p.ssafy.io/openvidu/recordings/ses_O9H9QaNtfm/str_CAM_SG84_con_ATmh8cnZdE.webm",
            type: "video/webm"
          }
        ]
      },
      data: [
        {
          label: "Level one 1",
          children: [
            {
              label: "Level two 1-1",
              children: [
                {
                  label: "Level three 1-1-1"
                }
              ]
            }
          ]
        },
        {
          label: "Level one 2",
          children: [
            {
              label: "Level two 2-1",
              children: [
                {
                  label: "Level three 2-1-1"
                }
              ]
            },
            {
              label: "Level two 2-2",
              children: [
                {
                  label: "Level three 2-2-1"
                }
              ]
            }
          ]
        },
        {
          label: "Level one 3",
          children: [
            {
              label: "Level two 3-1",
              children: [
                {
                  label: "Level three 3-1-1"
                }
              ]
            },
            {
              label: "Level two 3-2",
              children: [
                {
                  label: "Level three 3-2-1"
                }
              ]
            }
          ]
        }
      ],
      defaultProps: {
        children: "children",
        label: "label"
      }
    };
  }
};
</script>

<style></style>
