/**
 * Created by qi on 2017/9/3.
 */
import { getToken, setToken } from '@/utils/auth'
import { login } from '@/api/login'
const user = {
  state: {
    token: getToken()
  },
  mutations: {
    SET_TOKEN: (state, token) => {
      state.token = token
    }
  },
  actions: {
    login ({commit}, userinfo) {
      const username = userinfo.username.trim()
      return new Promise((resolve, reject) => {
        login(username, userinfo.password)
          .then(response => {
            const data = response.data
            const token = data.result.token
            setToken(token)
            commit('SET_TOKEN', token)
            resolve()
          }).catch(error => {
            reject(error)
          })
      })
    }
  }
}

export default user

