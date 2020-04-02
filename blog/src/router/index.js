import Vue from 'vue'
import VueRouter from 'vue-router'
import VueResource from 'vue-resource'
import ElementUI from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css'
import Home from '../views/Home.vue'
import AddBlog from '../views/AddBlog.vue'
import ShowBlog from '../views/ShowBlog.vue'

Vue.use(VueRouter)
Vue.use(ElementUI)
Vue.use(VueResource)

const routes = [
  {
    name: 'home',
    path: '/',
    redirect: '/show-blog',
    component: Home,
    children:[
      {
        name: 'show-blog',
        label: '博客总览',
        path: '/show-blog',
        component: ShowBlog
      },
      {
        name: 'add-blog',
        label: '新增博客',
        path: '/add-blog',
        component: AddBlog
      }
    ]
  }
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

export default router
