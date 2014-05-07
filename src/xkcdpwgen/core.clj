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

; Let's just hardcode the dictionary path until I can think of a better way to
; handle it (preferably something idiomatic to Clojure).  Figuring-out a way to
; test this is going to be interesting.  Update: Instead of this being a
; standalone function, I'll just insert its body as the arg to (makebylen ...),
; when I ultimately call that from (-main ...).  Problem solved.  Maybe there I
; can even provide for an optional command-line arg to supply a replacement for
; the dictionary file.

(defn usrdictwords []
   (split-lines (slurp "/usr/share/dict/words")))

(defn makebylen
   [words]
   (reduce
      #(merge-with into % {(count %2) [%2]})
      {}
      (map normalform words)))

; ToDo: write tests for this function

(defn password
   [words nwords maxwordlen]
   (join " "
      (map
         #(format (format "%%-%ds" maxwordlen) %)
         (take nwords (shuffle words)))))

;def passwords(bylen, bitsentropy, count):
;    result = list()
;    words = list()
;
;    maxwordlen = 11
;    for wordlen in xrange(3, maxwordlen+1):
;        words.extend(bylen[wordlen].keys())
;    wordbits = bits(len(words))
;    nwords = math.ceil(float(bitsentropy) / float(wordbits))
;    print "Final wordlist contains", len(words), "words.  Picking", nwords, "words provides at least", wordbits*nwords, "bits of entropy."
;    for x in xrange(0, count):
;        result.append(password(words, nwords, maxwordlen))
;    return result

(defn -main
   "I don't do a whole lot ... yet."
   [& args]
   (println "Hello, World!"))
