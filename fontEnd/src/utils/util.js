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
