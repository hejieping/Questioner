/**
 * Created by qi on 2017/9/3.
 */
const getters = {
  token: state => state.user.token,
  hasLogin: state => {
    return true
  }
}

export default getters