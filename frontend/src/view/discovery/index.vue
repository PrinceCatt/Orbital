<template>
  <div>
    <h3>
      L'homme n'est qu'un roseau, le plus faible de la nature; mais c'est un
      roseau pensant.
    </h3>
    <el-container style="height: 940px; border: 1px solid #eee">
      <el-aside width="200px" style="background-color: rgb(238, 241, 246)">
        <el-menu :default-openeds="['1', '3']">
          <el-submenu index="1">
            <template slot="title">ACGN</template>
            <el-menu-item-group>
              <el-menu-item index="1-1">
                <el-button @click="openPage('Anime')">Anime</el-button>
              </el-menu-item>
              <el-menu-item index="1-2">
                <el-button @click="openPage('Comics')">Comics</el-button>
              </el-menu-item>
              <el-menu-item index="1-3">
                <el-button @click="openPage('Gaming')">Gaming</el-button>
              </el-menu-item>
              <el-menu-item index="1-4">
                <el-button @click="openPage('LightNovel')">LightNovel</el-button>
              </el-menu-item>
            </el-menu-item-group>
          </el-submenu>

          <el-submenu index="2">
            <template slot="title">Music</template>
            <el-menu-item-group>
              <template slot="title"></template>
              <el-menu-item index="2-1">
                <el-button @click="openPage('Music-Chinese')">Chinese</el-button>
                </el-menu-item>
              <el-menu-item index="2-2">
                <el-button @click="openPage('Music-English')">English</el-button>
                </el-menu-item>
              <el-menu-item index="2-3">
                <el-button @click="openPage('Music-Japanese')">Japanese</el-button>
                </el-menu-item>
              <el-menu-item index="2-4">
                <el-button @click="openPage('Music-Korean')">Korean</el-button>
                </el-menu-item>
              <el-menu-item index="2-5">
                <el-button @click="openPage('Music-Spanish')">Spanish</el-button>
                </el-menu-item>
              <el-menu-item index="2-6">
                <el-button @click="openPage('Music-Others')">Others</el-button>
                </el-menu-item>  
            </el-menu-item-group>
          </el-submenu>

          <el-submenu index="3">
            <template slot="title">Life in NUS</template>
            <el-menu-item-group>
              <template slot="title"></template>
              <el-menu-item index="3-1">
                <el-button @click="openPage('NUS-Study')">Study</el-button>
                </el-menu-item>
              <el-menu-item index="3-2">
                <el-button @click="openPage('NUS-Relationship')">Relationship</el-button>
                </el-menu-item>
              <el-menu-item index="3-3">
                <el-button @click="openPage('NUS-Games')">Physical Games</el-button>
                </el-menu-item>
              <el-menu-item index="3-4">
                <el-button @click="openPage('NUS-Pals')">Pals</el-button>
                </el-menu-item>

            </el-menu-item-group>
          </el-submenu>
        </el-menu>
      </el-aside>

      <el-container>
        <el-header style="text-align: right; font-size: 12px">
          <el-dropdown>
            <i class="el-icon-setting" style="margin-right: 15px"></i>
            <el-dropdown-menu slot="dropdown">
              <el-dropdown-item>New Post</el-dropdown-item>
            </el-dropdown-menu>
          </el-dropdown>
          <span>{{ this.$store.state.user.name }}</span>
        </el-header>

        <el-main>

          <router-view></router-view>
          
        </el-main>
      </el-container>
    </el-container>
  </div>
</template>


  <style>
.el-header {
  background-color: #b3c0d1;
  color: #333;
  line-height: 60px;
}

.el-aside {
  color: #333;
}
</style>
  
  <script>
export default {
  data() {
    return {};
  },

  methods: {
    openPage(section){
      this.$router.push({path: `/discovery/page/${section}`})
    }
  },

};
  </script>

