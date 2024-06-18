import VueRouter from "vue-router";
import Vue from "vue";
import Login from "@/view/login";
import MyProfile from '@/view/my/profile'
import MyPosts from '@/view/my/posts'
import Register from '@/view/register'
import Discovery from '@/view/discovery'
import Page from '@/view/page'
import NewPost from '@/view/page/new.vue'


Vue.use(VueRouter)

const router = new VueRouter({
    routes:[
        {path:'/', redirect: '/discovery'},
        {path:'/register', component: Register},
        {path:'/my/profile', component: MyProfile, meta: {
            needLogin: true }},
        {path:'/my/posts', component: MyPosts, meta: {
            needLogin: true }},
        {path:'/login', name:"login", component: Login},
        {path:'/discovery', component: Discovery,
            children: [
                {path: "page", component: Page, 
                children: [
                    {path :":id", component: Page, props: true}
                ]
            }
            ]
        },
        {path:'/newpost', component: NewPost, meta: {
            needLogin: true }},
    ]
})

export default router