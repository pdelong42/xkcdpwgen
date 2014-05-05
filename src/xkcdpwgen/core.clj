(ns xkcdpwgen.core (:gen-class))

; For "bits", I'm emulating the behaviour of the original Python code.  I'm not
; yet sure why he chose to do it that way, but the code the author wrote
; returns one less than the actual number of bits needed to encode the input
; number.  Maybe he had a good reason for this; maybe I'll adopt the other
; convention at a later time.

(defn bits [n]
   (cond
      (< n 2) 0
      :else (+ 1 (bits (/ n 2)))
   )
)

(defn -main
   "I don't do a whole lot ... yet."
   [& args]
   (println "Hello, World!")
)
