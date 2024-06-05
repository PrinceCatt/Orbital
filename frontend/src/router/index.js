import VueRouter from "vue-router";
import Vue from "vue";
import Login from "@/view/login";
import Logout from "@/view/logout";
import Profile from '@/view/my/profile'
import Register from '@/view/register'
import Discovery from '@/view/discovery'
import Page from '@/view/page'


Vue.use(VueRouter)

const router = new VueRouter({
    routes:[
        {path:'/', redirect: '/discover'},
        {path:'/register', component: Register},
        {path:'/profile', component: Profile},
        {path:'/login',  component: Login},
        {path:'/logout', component: Logout},
        {path:'/discovery', component: Discovery,
            children: [
                {path: "page", component: Page}
            ]
        },
    ]
})

export default router