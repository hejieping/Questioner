/**
 * Created by qi on 2017/9/3.
 */
import { getToken, setToken, removeToken } from '@/utils/auth'
import { login } from '@/api/login'
import { Message } from 'element-ui'
const user = {
  state: {
    token: getToken()
  },
  mutations: {
    SET_TOKEN: (state, token) => {
      state.token = token
    },
    REMOVE_TOKEN: (state) => {
      state.token = getToken()
    }
  },
  actions: {
    login ({commit}, userinfo) {
      const username = userinfo.username.trim()
      return new Promise((resolve, reject) => {
        login(username, userinfo.password)
          .then(response => {
            if (response.status === 403) {
              Message({
                message: '登录失败，用户名或密码错误',
                type: 'error',
                duration: 5 * 1000
              })
              reject(response)
              return
            }
            const token = response.result.token
            setToken(token)
            commit('SET_TOKEN', token)
            resolve()
          }).catch(error => {
            reject(error)
          })
      })
    },
    logout ({commit}) {
      removeToken()
      commit('REMOVE_TOKEN')
    }
  }
}

export default user

