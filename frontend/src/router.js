import { createWebHistory, createRouter } from "vue-router";
import Main from "@/views/main/main.vue";
import Event from "@/views/event.vue";
import SearchResult from "@/views/search-result";
import ClassDetail from "@/views/classdetail/class-detail";

import MypageMain from "@/views/mypage/mypageMain";
import FinishedClass from "@/views/mypage/finishedClass"
import RegisterClass from "@/views/mypage/registerClass"
import RecordedVideo from "@/views/mypage/recordedVideo"
import MyInfo from "@/views/mypage/myInfo"

const routes = [
  {
    path: "/",
    name: "Main",
    component: Main
  },
  {
    path: "/event",
    name: "Event",
    component: Event
  },
  {
    path: "/searchresult",
    name: "SearchResult",
    component: SearchResult
  },
  {
    path: "/classdetail",
    name: "ClassDetail",
    component: ClassDetail
  },
  {
    path: "/mypageMain",
    name: "MypageMain",
    component: MypageMain,
    children: [
      {
        path: "/finishedClass",
        name: "FinishedClass",
        component: FinishedClass
      },
      {
        path: "/rgstClass",
        name: "RegisterClass",
        component: RegisterClass
      },
      {
        path: "/recordedVideo",
        name: "RecordedVideo",
        component: RecordedVideo
      },
      {
        path: "/myinfo",
        name: "MyInfo",
        component: MyInfo
      }
    ]
  }
];

const router = createRouter({
  history: createWebHistory(),
  routes
});

export default router;
