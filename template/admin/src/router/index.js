import Vue from 'vue'
import Router from 'vue-router'
import Layout from '@/layout'

Vue.use(Router)

/**
 * Note: sub-menu only appear when route children.length >= 1
 * Detail see: https://panjiachen.github.io/vue-element-admin-site/guide/essentials/router-and-nav.html
 *
 * hidden: true                   if set true, item will not show in the sidebar(default is false)
 * alwaysShow: true               if set true, will always show the root menu
 *                                if not set alwaysShow, when item has more than one children route,
 *                                it will becomes nested mode, otherwise not show the root menu
 * redirect: noRedirect           if set noRedirect will no redirect in the breadcrumb
 * name:'router-name'             the name is used by <keep-alive> (must set!!!)
 * meta : {
    roles: ['admin','editor']    control the page roles (you can set multiple roles)
    title: 'title'               the name show in sidebar and breadcrumb (recommend set)
    icon: 'svg-name'/'el-icon-x' the icon show in the sidebar
    breadcrumb: false            if set false, the item will hidden in breadcrumb(default is true)
    activeMenu: '/example/list'  if set path, the sidebar will highlight the path you set
  }
 */

export const constantRoutes = [
  {
    path: '/login',
    component: () => import('@/views/login/index'),
    hidden: true
  },

  {
    path: '/404',
    component: () => import('@/views/404'),
    hidden: true
  },

  {
    path: '/',
    component: Layout,
    redirect: '/dashboard',
    children: [{
      path: 'dashboard',
      name: 'Dashboard',
      component: () => import('@/views/dashboard/index'),
      meta: {title: '首页', icon: 'dashboard'}
    }]
  },

  // template
  {
    path: '/template',
    component: Layout,
    redirect: 'noRedirect',
    meta: {
      title: '模板管理',
      icon: 'el-icon-s-help',
    },
    children: [
      {
        path: 'dashboard',
        component: () => import('@/views/template/dashboard'),
        meta: {title: '仪表盘'}
      },
      {
        path: 'echarts',
        component: () => import('@/views/template/echarts'),
        meta: {title: 'Echarts'},
        children: [
          {
            path: 'bar',
            component: () => import('@/views/template/echarts/bar'),
            meta: {title: '柱状图'}
          },
          {
            path: 'line',
            component: () => import('@/views/template/echarts/line'),
            meta: {title: '折线图'}
          },
          {
            path: 'pie',
            component: () => import('@/views/template/echarts/pie'),
            meta: {title: '饼图'}
          },
          {
            path: 'scatter',
            component: () => import('@/views/template/echarts/scatter'),
            meta: {title: '散点图'}
          }
        ]
      },
      {
        path: 'table',
        component: () => import('@/views/template/table'),
        meta: {title: '表格'}
      },
      {
        path: 'form',
        component: () => import('@/views/template/form'),
        meta: {title: '表单'}
      },
      {
        path: 'upload',
        component: () => import('@/views/template/form/upload'),
        meta: {title: '上传'}
      }
    ]
  },
]

export const asyncRoutes = [

  {
    path: '/personal',
    component: Layout,
    redirect: 'noRedirect',
    meta: {
      title: '个人中心',
      icon: 'nested',
    },
    children: [
      {
        path: 'index',
        component: () => import('@/views/personal/index'),
        meta: {title: '个人信息', icon: 'table'}
      },
      {
        path: 'password',
        component: () => import('@/views/personal/password'),
        meta: {title: '修改密码', icon: 'table'}
      }
    ]
  },

  // system
  {
    path: '/system',
    component: Layout,
    redirect: 'noRedirect',
    meta: {
      roles: ['admin'],
      title: '系统管理',
      icon: 'el-icon-s-help',
    },
    children: [
      {
        path: 'role',
        component: () => import('@/views/system/role'),
        meta: {title: '角色管理'}
      },
      {
        path: 'menu',
        component: () => import('@/views/system/menu'),
        meta: {title: '菜单管理'}
      },
      {
        path: 'user',
        component: () => import('@/views/system/user'),
        meta: {title: '用户管理'}
      }
    ]
  },

  // 404 page must be placed at the end !!!
  {path: '*', redirect: '/404', hidden: true}
]

const createRouter = () => new Router({
  mode: 'history',
  scrollBehavior: () => ({y: 0}),
  routes: constantRoutes
})

const router = createRouter()

export function resetRouter() {
  const newRouter = createRouter()
  router.matcher = newRouter.matcher // reset router
}

export default router
