(ns xkcdpwgen.core
   (:require
      [clojure.pprint    :refer [pprint]]
      [clojure.string    :refer [join lower-case split split-lines trim]]
      [clojure.tools.cli :refer [parse-opts]] )
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

(defn usage
   [options-summary]
   (join \newline
      "usage: %prog [options] arg1 arg2"
      ""
      "Options:"
      options-summary
      ""
      "See http://xkcd.com/936/"
      ""
      "Basic argument is that picking 4 random common words for a password "
      "gives you about 44 bits of entropy, vs, say 28 bits of entropy for "
      "a \"strong\" (but patterned to be kinda memorable) random password."
      ""
      "xkcdpwgen uses a dictionary to generate a list of words in \"normal\" form, "
      "and generate candidate passwords as a few random dictionary words in a row."
      "Given the length of the word list that we're drawing from, we can then"
      "pick enough words to satisfy given strenght criteria."
      ""
      "It defaults to 44 bits of entropy as per the cartoon."))

(def cli-options
   [  [  "-b"
         "--bitsentropy NUM"
         "generate passwords with at least NUM bits of entropy"
         :validate [integer? "not an integer"]
         :default 44 ]
;     [  "-e"
;        "--equivalent LEN"
;        "generate passwords as strong as a string of LEN random printable ascii symbols"
;        :validate [integer? "not an integer"] ]
      [  "-f"
         "--filename PATH"
         "a full PATH to a file containing a list of words"
         :default "/usr/share/dict/words"]
      [  "-n"
         "--numtogen NUM"
         "number of candidate passwords to generate"
         :validate [integer? "not an integer"]
         :default 20] ] )

; ToDo: test that command-line options get recognized

(defn passwords
   [  {:keys [options arguments errors summary]}]
   (let
      [  maxwordlen 30 ; ToDo: determine this automatically from width of largest word used
         words (reduce into (vals (makebylen (split-lines (slurp (:filename options)))))) ; footnote 1
         wc (count words)
         wordbits (bits wc)
         nwords (int (/ (:bitsentropy options) wordbits)) ; ToDo: this needs to be fixed to round up instead of down
      ]
      (printf "Final wordlist contains %d words.  Picking %d words provides at least %d bits of entropy.\n" wc nwords (* wordbits nwords))
      (dorun ; this feels hacky :-/
         (map println
            (repeatedly
               (:numtogen options)
              #(password words nwords maxwordlen))))))

(defn -main
   [& args]
   (passwords (parse-opts args cli-options))) ; ToDo: fix this so that a non-existent flag generates an error or a help message

; Footnote 1
;
; I'm not sure why things were grouped by word-length in the original
; implementation, since the author never made use of that.  The only
; reason I'm preserving it here is because I think I may take
; advantage of it for more precision.  (Perhaps that's what the
; original author had in mind too).
