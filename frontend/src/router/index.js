import VueRouter from "vue-router";
import Vue from "vue";
import Login from "@/view/login";
import MyProfile from '@/view/my/profile'
import MyPosts from '@/view/my/posts'
import UpdatePost from '@/view/my/posts/update.vue'
import Register from '@/view/register'
import Discovery from '@/view/discovery'
import Page from '@/view/page'
import Newpost from '@/view/page/new.vue'
import Post from '@/view/page/post.vue'
import Chat from '@/view/chat'
import MyHistory from '@/view/my/history'

Vue.use(VueRouter)


//this file is the router address for all different pages
const router = new VueRouter({
    routes: [
        { path: '/', redirect: '/discovery' },
        { path: '/register', component: Register },
        { path:'/my/profile', component: MyProfile, meta: {
            needLogin: true }},
        { path:'/my/posts', component: MyPosts, meta: {
            needLogin: true }},    
        { path: '/my/update/:postId', component: UpdatePost, meta: {
            needLogin: true }},
        { path:'/my/history', component: MyHistory, meta: {
            needLogin: true }},
        { path: '/login', name: "login", component: Login },
        {
            path: '/discovery', component: Discovery,
            children: [
                {
                    path: "page", component: Page,
                    children: [
                        { path: ":section", component: Page, props: true },
                    ]
                }
            ]
        },
        { path: '/post/:id', component: Post},
        { path: '/newpost', component: Newpost, meta: {
                needLogin: true
            }
        },
        { path: '/chat', component: Chat, meta: {
            needLogin: true
        }
    }
    ]
})

export default router