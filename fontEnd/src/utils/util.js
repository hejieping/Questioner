/**
 * Created by qi on 2017/10/12.
 */
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

export { creditScoreMap, creditLabelMap }
