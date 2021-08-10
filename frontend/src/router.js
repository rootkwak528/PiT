import { createWebHistory, createRouter } from "vue-router";
import Main from "@/views/main/main.vue";
import Event from "@/views/event.vue";
import SearchResult from "@/views/search-result";
import ClassDetail from "@/views/classdetail/class-detail";
import MyInfo from "@/views/mypage/myInfo";
import RegisterClass from "@/views/mypage/registerClass";
import FinishedClass from "@/views/mypage/finishedClass";
import RecordedVideo from "@/views/mypage/recordedVideo";

import MypageMain from "@/views/TESTmypage/mypageMain";
import FinishedClassTest from "@/views/TESTmypage/finishedClassTest"
import RegisterClassTest from "@/views/TESTmypage/registerClassTest"
import RecordedVideoTest from "@/views/TESTmypage/recordedVideoTest"
import MyInfoTest from "@/views/TESTmypage/myinfoTest"


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
  // 기존 마이페이지
  {
    path: "/myinfo",
    name: "MyInfo",
    component: MyInfo
  },
  {
    path: "/rgstClass",
    name: "RegisterClass",
    component: RegisterClass
  },
  {
    path: "/finishedClass",
    name: "FinishedClass",
    component: FinishedClass
  },
  {
    path: "/recordedVideo",
    name: "RecordedVideo",
    component: RecordedVideo
  },
  // 기존 마이페이지

  // 수정된 마이 페이지
  {
    path: "/mypageMain",
    name: "MypageMain",
    component: MypageMain,
    children: [
      {
        path: "/finishedClassTest",
        name: "FinishedClassTest",
        component: FinishedClassTest
      },
      {
        path: "/rgstClassTest",
        name: "RegisterClassTest",
        component: RegisterClassTest
      },
      {
        path: "/recordedVideoTest",
        name: "RecordedVideoTest",
        component: RecordedVideoTest
      },
      {
        path: "/myinfoTest",
        name: "MyInfoTest",
        component: MyInfoTest
      }
    ]
  }
];

const router = createRouter({
  history: createWebHistory(),
  routes
});

export default router;
