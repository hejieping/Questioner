/**
 * Created by qi on 2017/9/3.
 */
const getters = {
  token: state => state.user.token,
  hasLogin: state => {
    return state.user.token !== null
  },
  user: state => state.user.userInfo,
  isQuestionListPage: state => state.common.inQuestionListPage,
  questionType: state => state.common.questionType
}

export default getters
