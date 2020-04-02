import Vue from 'vue'
import './plugins/axios'
import App from './App.vue'
import router from './router'
import store from './store'

Vue.config.productionTip = false

Vue.directive("rainbow", {
  bind(el, binding, vnode) {
    el.style.color = "#" + Math.random().toString(16).slice(2,8)
  }
})

Vue.directive("mode", {
  bind(el, binding, vnode) {
    if(binding.value == 'wide'){
      el.style.width = "800px";
    } else if(binding.value == "narrow") {
      el.style.width = "480px"
    }
  }
})

Vue.filter("to-uppercase", function (value) {
  return value.toUpperCase()
})

Vue.filter("snippet", function (value) {
  return value.slice(0, 100) + "..."

})
new Vue({
  router,
  store,
  render: h => h(App)
}).$mount('#app')
