(ns paredit-cm.macros)

(defmacro check
  "checks a paredit operation, assuming it takes one arg: the cm instance.
  initializes the instance's text and cursor position. checks the result
  text and position."
  [f before after]
  `(let [[exp-line# exp-ch# exp-text#] (paredit-cm.test/decode ~after)
         ;;[ b4-line#  b4-ch#  b4-text#] (paredit-cm.test/decode ~before)
         cm# (paredit-cm.test/get-cm ~before)
         ;; _ (.setValue ~cm b4-text#)
         ;; _ (.setCursor ~cm b4-line# b4-ch#)
         _# (~f cm#)
         cur# (paredit-cm.test/get-cursor cm#)
         index# (paredit-cm.test/index-from-pos cm# cur#)
         fname# (-> ~f var meta :name str)
         result-line# (paredit-cm.test/line cur#)
         result-ch#   (paredit-cm.test/ch   cur#)
         result-text# (paredit-cm.test/text cm#)
         result# (paredit-cm.test/encode result-text# index#)
         msg# #(str fname# ": wrong " %1 ". expected [" %2 "] got [" %3 "]\n"
                    "before   : " ~before "\n"
                    "expected : " ~after "\n"
                    "actual   : " result#)]
     (cljs.test/is
      (= exp-line# result-line#)
      (msg# "line" exp-line# result-line#))
     (cljs.test/is
      (= exp-ch# result-ch#)
      (msg# "ch" exp-ch# result-ch#))
     (cljs.test/is
      (= exp-text# result-text#)
      (msg# "text" exp-text# result-text#))))
