import { createWebHistory, createRouter } from "vue-router";
import Main from "@/views/main/main.vue";
import Event from "@/views/eventpage/event.vue";
import EventDetail from "@/views/eventpage/eventdetail";
import SearchResult from "@/views/searchResult/search-result";
import ClassDetail from "@/views/classdetail/class-detail";

import MypageMain from "@/views/mypage/mypageMain";
import FinishedClass from "@/views/mypage/finishedClass";
import RegisterClass from "@/views/mypage/registerClass";
import RecordedVideo from "@/views/mypage/recordedVideo";
import MyInfo from "@/views/mypage/myInfo";
import FavoriteClass from "@/views/mypage/favoriteClass";
import OpenClass from "@/views/mypage/openClass";
import Videos from "@/views/mypage/components/videos";

import AdminMain from "@/views/adminpage/adminMain";
import AdminClass from "@/views/adminpage/adminClass";
import AdminClassDetail from "@/views/adminpage/adminclassdetail/adminclass-detail";

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
    path: "/eventdetail",
    name: "EventDetail",
    component: EventDetail
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
      },
      {
        path: "/favoriteclass",
        name: "FavoriteClass",
        component: FavoriteClass
      },
      {
        path: "/openClass",
        name: "OpenClass",
        component: OpenClass
      },
      {
        path: "/videos",
        name: "Videos",
        component: Videos
      }
    ]
  },
  {
    path: "/adminMain",
    name: "AdminMain",
    component: AdminMain,
    children: [
      {
        path: "/adminClass",
        name: "AdminClass",
        component: AdminClass
      },
      {
        path: "/adminClassDetail",
        name: "AdminClassDetail",
        component: AdminClassDetail
      }
    ]
  }
];

const router = createRouter({
  history: createWebHistory(),
  routes
});

export default router;
