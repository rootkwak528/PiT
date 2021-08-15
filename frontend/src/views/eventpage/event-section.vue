<template>
  <div class="content-wrapper">
    <div class="submenu-title" style="margin-top: 15px">이벤트</div>
    <div class="class-card-wrapper">
      <el-card
        class="class-card"
        v-for="eventItem in state.list"
        :key="eventItem"
        :body-style="{
          padding: '0px',
          height: '400px',
          width: '300px'
        }"
        style="margin: 5px"
      >
        <router-link
          :to="`/eventdetail?eventNo=${eventItem.eventNo}`"
          style="text-decoration: none; color: inherit;"
        >
          <el-image
            :src="eventItem.eventImage"
            fit="cover"
            style="width: 300px; height: 250px;"
          />
          <div class="class-card-content">
            <div>
              <div style="height: 80px;">
                <div class="title">{{ eventItem.eventTitle }}</div>
              </div>
              <div class="class-card-tag">
                <el-tag size="mini" color="#BEEDED">
                  {{ eventItem.eventStartTime }} ~
                  {{ eventItem.eventEndTime }}
                </el-tag>
              </div>
            </div>
          </div>
        </router-link>
      </el-card>
    </div>
  </div>
</template>
