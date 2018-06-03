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
    name: '首页',
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
    path: '/query',
    component: Layout,
    redirect: '/query/index',
    name: '查询',
    icon: 'qq',
    meta: { roles: ['admin'] }, // you can set roles in root nav
    children: [{
      path: 'index',
      component: _import('query/index'),
      name: '查询',
      icon: 'bug',
      meta: {
        title: 'query',
        roles: ['admin'] // or you can only set roles in sub nav
      }
    }]
  },
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
