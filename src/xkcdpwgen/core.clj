(ns xkcdpwgen.core
   (:require [clojure.string :refer [join lower-case split split-lines trim]])
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

(defn makebylen
   [words]
   (reduce
      #(merge-with into % {(count %2) [%2]})
      {}
      (map normalform words)))

(defn password
   [words nwords maxwordlen]
   (join " "
      (map
         #(format (format "%%-%ds" maxwordlen) %)
         (take nwords (shuffle words)))))

; ToDo: write tests for this function

(defn passwords
   [bylen bitsentropy n]
   (let
      [  maxwordlen 30
         words (reduce into (vals bylen))
         wc (count words)
         wordbits (bits wc)
         nwords (float (/ bitsentropy wordbits))
      ]
      (printf "Final wordlist contains %d words.  Picking %d words provides at least %f bits of entropy." wc nwords (* wordbits nwords))
      (map
         #(password words nwords maxwordlen)
         (range count))))

; Let's just hardcode the dictionary path until I can think of a better way to
; handle it (preferably something idiomatic to Clojure).  Figuring-out a way to
; test this is going to be interesting.  Update: Instead of this being a
; standalone function, I'll just insert its body as the arg to (makebylen ...),
; when I ultimately call that from (-main ...).  Problem solved.  Maybe there I
; can even provide for an optional command-line arg to supply a replacement for
; the dictionary file.

(defn usrdictwords []
   (split-lines (slurp "/usr/share/dict/words")))

(defn -main
   "I don't do a whole lot ... yet."
   [& args]
   (println "Hello, World!"))
