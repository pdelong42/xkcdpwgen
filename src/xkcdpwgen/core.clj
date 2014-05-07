(ns xkcdpwgen.core
   (:require [clojure.string :refer [lower-case split split-lines trim]])
   (:gen-class))

; For "bits", I'm emulating the behaviour of the original Python code.  I'm not
; yet sure why he chose to do it that way, but the code the author wrote
; returns one less than the actual number of bits needed to encode the input
; number.  Maybe he had a good reason for this; maybe I'll adopt the other
; convention at a later time.

(defn bits [n]
   (if
      (< n 2)
      0
      (+ 1 (bits (bit-shift-right n 1)))))

(defn normalform [w]
   (lower-case (first (split (trim w) #"'"))))

; Let's just hardcode the dictionary path until I can think of a better way to
; handle it (preferably something idiomatic to Clojure).  Figuring-out a way to
; test this is going to be interesting.

(defn usrdictwords []
   (split-lines (slurp "/usr/share/dict/words")))

(defn makebylen
   [words]
   (reduce
      #(merge-with into % {(count %2) [%2]})
      {}
      (map normalform words)))

(defn password
   [words nwords maxwordlen]
)

;def password(words, nwords, maxwordlen):
;    reslist = list()
;    fmtstr = "%%-%ds" % maxwordlen
;    while len(reslist) < nwords:
;        reslist.append(fmtstr % random.sample(words, 1)[0])
;    return ' '.join(reslist)

(defn -main
   "I don't do a whole lot ... yet."
   [& args]
   (println "Hello, World!"))
