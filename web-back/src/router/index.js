import Vue from 'vue'
import Router from 'vue-router'
const _import = require('./_import_' + process.env.NODE_ENV)
// in development-env not use lazy-loading, because lazy-loading too many pages will cause webpack hot update too slow. so only in production use lazy-loading;
// detail: https://panjiachen.github.io/vue-element-admin-site/#/lazy-loading

Vue.use(Router)

/* Layout */
import Layout from '../views/layout/Layout'

/** note: submenu only apppear when children.length>=1
*   detail see  https://panjiachen.github.io/vue-element-admin-site/#/router-and-nav?id=sidebar
**/

/**
* hidden: true                   if `hidden:true` will not show in the sidebar(default is false)
* alwaysShow: true               if set true, will always show the root menu, whatever its child routes length
*                                if not set alwaysShow, only more than one route under the children
*                                it will becomes nested mode, otherwise not show the root menu
* redirect: noredirect           if `redirect:noredirect` will no redirct in the breadcrumb
* name:'router-name'             the name is used by <keep-alive> (must set!!!)
* meta : {
    roles: ['admin','editor']     will control the page roles (you can set multiple roles)
    title: 'title'               the name show in submenu and breadcrumb (recommend set)
    icon: 'svg-name'             the icon show in the sidebar,
    noCache: true                if true ,the page will no be cached(default is false)
  }
**/
export const constantRouterMap = [
  { path: '/login', component: _import('login/index'), hidden: true },
  { path: '/authredirect', component: _import('login/authredirect'), hidden: true },
  { path: '/404', component: _import('errorPage/404'), hidden: true },
  { path: '/401', component: _import('errorPage/401'), hidden: true },
  {
    path: '',
    component: Layout,
    redirect: 'dashboard',
    icon: 'dashboard',
    noDropdown: true,
    children: [{
      path: 'dashboard',
      component: _import('dashboard/index'),
      name: '首页',
      meta: { title: 'dashboard', noCache: true }
    }]
  }
]

export default new Router({
  // mode: 'history', // require service support
  scrollBehavior: () => ({ y: 0 }),
  routes: constantRouterMap
})

export const asyncRouterMap = [
  {
    spm: '0007.0000.0000.0000',
    path: '/sysadmin',
    component: Layout,
    redirect: '/sysadmin/account/index',
    name: '系统管理',
    icon: 'qq',
    children: [
      // { path: 'account', icon: 'zujian', spm: '0007.0001.0000.0000', component: _import('sysadmin/account/index'), name: '账户管理 ' },
      { path: 'role', icon: 'tab', spm: '0007.0003.0000.0000', component: _import('sysadmin/role/index'), name: '角色管理' },
      // { path: 'permission', icon: 'zujian', component: _import('sysadmin/permission/index'), name: '权限管理' },
      { path: 'resource', icon: 'table', spm: '0007.0004.0000.0000', component: _import('sysadmin/resource/index'), name: '资源管理' }
      // { path: 'dict', icon: 'zujian', spm: '0007.0005.0000.0000', component: _import('sysadmin/dict/index'), name: '数据字典' }
    ]
  },
  { path: '*', redirect: '/404', hidden: true }
]

export const asyncRouterMap2 = [
  {
    path: '/query1',
    component: Layout,
    redirect: '/query1/index',
    name: 'testttt',
    icon: 'form',
    meta: { roles: ['admin'] }, // you can set roles in root nav
    children: [{
      path: 'index',
      component: _import('query/index'),
      name: 'qtreeee',
      meta: {
        title: 'query',
        icon: 'form',
        roles: ['admin'] // or you can only set roles in sub nav
      }
    }]
  },
  { path: '*', redirect: '/404', hidden: true }
]

export const goodsRouter = [
  {
    path: '/goods',
    component: Layout,
    redirect: '/goods/index',
    name: '商品',
    icon: 'form',
    meta: { roles: ['admin'] }, // you can set roles in root nav
    children: [{
      path: 'index',
      component: _import('query/index'),
      name: 'SPU',
      meta: {
        title: 'query',
        icon: 'form',
        roles: ['admin'] // or you can only set roles in sub nav
      }
    }]
  },
  { path: '*', redirect: '/404', hidden: true }
]

export const wmsRouter = [
  {
    path: '/wms',
    component: Layout,
    redirect: '/wms/index',
    name: '库存',
    icon: 'form',
    meta: { roles: ['admin'] }, // you can set roles in root nav
    children: [{
      path: 'index',
      component: _import('query/index'),
      name: '供应商',
      meta: {
        title: 'query',
        icon: 'form',
        roles: ['admin'] // or you can only set roles in sub nav
      }
    }]
  },
  { path: '*', redirect: '/404', hidden: true }
]

export const userRouter = [
  {
    path: '/user',
    component: Layout,
    redirect: '/user/index',
    name: '用户',
    icon: 'form',
    meta: { roles: ['admin'] }, // you can set roles in root nav
    children: [{
      path: 'index',
      component: _import('query/index'),
      name: '列表',
      meta: {
        title: 'query',
        icon: 'form',
        roles: ['admin'] // or you can only set roles in sub nav
      }
    }]
  },
  { path: '*', redirect: '/404', hidden: true }
]

export const operateRouter = [
  {
    path: '/operate',
    component: Layout,
    redirect: '/operate/index',
    name: '活动',
    icon: 'form',
    meta: { roles: ['admin'] }, // you can set roles in root nav
    children: [{
      path: 'index',
      component: _import('query/index'),
      name: '列表',
      meta: {
        title: 'query',
        icon: 'form',
        roles: ['admin'] // or you can only set roles in sub nav
      }
    }]
  },
  { path: '*', redirect: '/404', hidden: true }
]

export const marketRouter = [
  {
    path: '/market',
    component: Layout,
    redirect: '/market/index',
    name: '营销',
    icon: 'form',
    meta: { roles: ['admin'] }, // you can set roles in root nav
    children: [{
      path: 'index',
      component: _import('query/index'),
      name: '列表',
      meta: {
        title: 'query',
        icon: 'form',
        roles: ['admin'] // or you can only set roles in sub nav
      }
    }]
  },
  { path: '*', redirect: '/404', hidden: true }
]

export const cmsRouter = [
  {
    path: '/cms',
    component: Layout,
    redirect: '/cms/index',
    name: '内容',
    icon: 'form',
    meta: { roles: ['admin'] }, // you can set roles in root nav
    children: [{
      path: 'index',
      component: _import('query/index'),
      name: '列表',
      meta: {
        title: 'query',
        icon: 'form',
        roles: ['admin'] // or you can only set roles in sub nav
      }
    }]
  },
  { path: '*', redirect: '/404', hidden: true }
]

export const tradeRouter = [
  {
    path: '/trade',
    component: Layout,
    redirect: '/trade/index',
    name: '交易',
    icon: 'form',
    meta: { roles: ['admin'] }, // you can set roles in root nav
    children: [{
      path: 'index',
      component: _import('query/index'),
      name: '列表',
      meta: {
        title: 'query',
        icon: 'form',
        roles: ['admin'] // or you can only set roles in sub nav
      }
    }]
  },
  { path: '*', redirect: '/404', hidden: true }
]

export const financeRouter = [
  {
    path: '/finance',
    component: Layout,
    redirect: '/finance/index',
    name: '财务',
    icon: 'form',
    meta: { roles: ['admin'] }, // you can set roles in root nav
    children: [{
      path: 'index',
      component: _import('query/index'),
      name: '列表',
      meta: {
        title: 'query',
        icon: 'form',
        roles: ['admin'] // or you can only set roles in sub nav
      }
    }]
  },
  { path: '*', redirect: '/404', hidden: true }
]

export const logisticRouter = [
  {
    path: '/logistic',
    component: Layout,
    redirect: '/logistic/index',
    name: '物流',
    icon: 'form',
    meta: { roles: ['admin'] }, // you can set roles in root nav
    children: [{
      path: 'index',
      component: _import('query/index'),
      name: '列表',
      meta: {
        title: 'query',
        icon: 'form',
        roles: ['admin'] // or you can only set roles in sub nav
      }
    }]
  },
  { path: '*', redirect: '/404', hidden: true }
]

export const scheduleRouter = [
  {
    path: '/schedule',
    component: Layout,
    redirect: '/schedule/index',
    name: '调度',
    icon: 'form',
    meta: { roles: ['admin'] }, // you can set roles in root nav
    children: [{
      path: 'index',
      component: _import('query/index'),
      name: '列表',
      meta: {
        title: 'query',
        icon: 'form',
        roles: ['admin'] // or you can only set roles in sub nav
      }
    }]
  },
  { path: '*', redirect: '/404', hidden: true }
]

export const systemRouter = [
  {
    path: '/system',
    component: Layout,
    redirect: '/system/index',
    name: '系统设置',
    icon: 'form',
    meta: { roles: ['admin'] }, // you can set roles in root nav
    children: [
      { path: 'account', icon: 'user', spm: '0007.0001.0000.0000', component: _import('sysadmin/account/index'), name: '账户管理 ' },
      { path: 'role', icon: 'tab', spm: '0007.0003.0000.0000', component: _import('sysadmin/role/index'), name: '角色管理' },
      { path: 'permission', icon: 'lock', component: _import('sysadmin/permission/index'), name: '权限管理' },
      { path: 'resource', icon: 'zip', spm: '0007.0004.0000.0000', component: _import('sysadmin/resource/index'), name: '资源管理' },
      { path: 'org', icon: 'zip', spm: '0007.0004.0000.0000', component: _import('sysadmin/org/index'), name: '组织管理' },
      { path: 'dict', icon: 'form', spm: '0007.0005.0000.0000', component: _import('sysadmin/dict/index'), name: '数据字典' }
    ]
  },
  { path: '*', redirect: '/404', hidden: true }
]

export const routeMap = {
  '0': constantRouterMap,
  '1': goodsRouter,
  '2': wmsRouter,
  '3': userRouter,
  '4': operateRouter,
  '5': marketRouter,
  '6': cmsRouter,
  '7': tradeRouter,
  '8': financeRouter,
  '9': logisticRouter,
  '10': scheduleRouter,
  '11': systemRouter
}
