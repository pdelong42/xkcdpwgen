(ns xkcdpwgen.core
   (:require [clojure.string :refer [lower-case split split-lines trim]])
   (:gen-class))

; For "bits", I'm emulating the behaviour of the original Python code.  I'm not
; yet sure why he chose to do it that way, but the code the author wrote
; returns one less than the actual number of bits needed to encode the input
; number.  Maybe he had a good reason for this; maybe I'll adopt the other
; convention at a later time.

; It drives me nuts that I do division here, when I'd rather just be shifting
; 'n' one bit to the right (much less taxing than division).  However, I don't
; yet know how to do bit twiddling in Clojure.  There's a part of me that just
; wants to do ln(n)/ln(2) and call it a day, but doing a logarithm is even more
; taxing than division.

(defn bits [n]
   (if
      (< n 2)
      0
      (+ 1 (bits (/ n 2)))))

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

(defn -main
   "I don't do a whole lot ... yet."
   [& args]
   (println "Hello, World!"))
