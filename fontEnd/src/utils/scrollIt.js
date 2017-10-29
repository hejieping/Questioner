/* eslint-disable no-unexpected-multiline */
/**
 * Created by qi on 2017/10/29.
 */
export function scrollIt (
  destination = 0,
  duration = 200,
  easing = 'linear',
  callback) {
  let easings = {
    linear (t) {
      return t
    },
    easeInQuad (t) {
      return t * t
    },
    easeOutQuad (t) {
      return t * (2 - t)
    },
    easeInOutQuad (t) {
      return t < 0.5 ? 2 * t * t : -1 + (4 - 2 * t) * t
    },
    easeInCubic (t) {
      return t * t * t
    },
    easeOutCubic (t) {
      return t < 0.5 ? 4 * t * t * t : (t - 1) * (2 * t - 2) + 1
    },
    easeInQuart (t) {
      return t * t * t * t
    },
    easeOutQuart (t) {
      return 1 - --t * t * t * t
    },
    easeInOutQuart (t) {
      return t < 0.5 ? 8 * t * t * t * t : 1 - 8 * --t * t * t * t
    },
    easeInQuint (t) {
      return t * t * t * t * t
    },
    easeOutQuint (t) {
      return 1 + --t * t * t * t * t
    },
    easeInOutQuint (t) {
      return t < 0.5 ? 16 * t * t * t * t * t : 1 + 16 * --t * t * t * t * t
    }
  }
  function resolveCompatibility () {
    let lastTime = 0
    let vendors = ['ms', 'moz', 'webkit', 'o']
    for (let x = 0; x < vendors.length && !window.requestAnimationFrame; ++x) {
      window.requestAnimationFrame =
        window[vendors[x] + 'RequestAnimationFrame']
      window.cancelAnimationFrame =
        window[vendors[x] + 'CancelAnimationFrame'] ||
        window[vendors[x] + 'CancelRequestAnimationFrame']
    }

    if (!window.requestAnimationFrame) {
      window.requestAnimationFrame = function (callback, element) {
        let currTime = new Date().getTime()
        let timeToCall = Math.max(0, 16 - (currTime - lastTime))
        let id = window.setTimeout(function () {
          callback(currTime + timeToCall)
        }, timeToCall)
        lastTime = currTime + timeToCall
        return id
      }
    }
    if (!window.cancelAnimationFrame) {
      window.cancelAnimationFrame = function (id) {
        clearTimeout(id)
      }
    }
  }
  resolveCompatibility()
  function checkElement () {
    document.documentElement.scrollTop += 1
    let elm = document.documentElement.scrollTop !== 0 ? document.documentElement : document.body
    document.documentElement.scrollTop -= 1
    return elm
  }
  let element = checkElement()
  let start = element.scrollTop
  let startTime = Date.now()
  function scroll () {
    let now = Date.now()
    let time = Math.min(1, (now - startTime) / duration)
    let timeFunction = easings[easing](time)
    element.scrollTop = timeFunction * (destination - start) + start
    if (element.scrollTop === destination) {
      callback()
      return
    }
    window.requestAnimationFrame(scroll)
  }
  scroll()
}
