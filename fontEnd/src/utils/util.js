/**
 * Created by qi on 2017/10/12.
 */
import $ from 'jquery'
export function transformQuestionType2Map (questionTypeArray) {
  let typeMap = {}
  for (var i in questionTypeArray) {
    let type = questionTypeArray[i]
    let subject = type.subject
    if (!typeMap[subject]) {
      typeMap[subject] = []
    }
    typeMap[subject].push(type)
  }
  return typeMap
}

const creditScoreMap = {
  acceptAnswer: 15,
  goodFeedBack: 2,
  answerQuestion: 1,
  badFeedBack: -1
}

const creditLabelMap = {
  acceptAnswer: '回答被采纳',
  goodFeedBack: '回答被赞',
  answerQuestion: '回答了问题',
  badFeedBack: '回答被踩'
}

export function initShare () {
  window.jiathis_config = {
    hideMore: true,
    summary: '',
    siteNum: 10,
    sm: 'renren,tieba,linkedin,tqq,douban,kaixin001,msn,googleplus,fb,twitter',
    shortUrl: false
  }
  $('head script').remove()
  let url = 'http://v3.jiathis.com/code_mini/jia.js'
  let script = document.createElement('script')
  script.setAttribute('src', url)
  document.getElementsByTagName('head')[0].appendChild(script)
}
export { creditScoreMap, creditLabelMap }
