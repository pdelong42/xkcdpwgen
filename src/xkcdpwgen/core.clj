(ns xkcdpwgen.core
   (:require
      [clojure.algo.generic.math-functions :refer [ceil]]
      [clojure.pprint                      :refer [pprint]]
      [clojure.string                      :refer [join lower-case split split-lines trim]]
      [clojure.tools.cli                   :refer [parse-opts]] )
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
   [exit-code options-summary & [error-msg]]
   (if error-msg (println error-msg "\n"))
   (println
      (join \newline
         [  "usage: xkcdpwgen [options] arg1 arg2"
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
            "It defaults to 44 bits of entropy as per the cartoon."  ]  )  )
   (System/exit exit-code)  )

(def cli-options
   [  [  "-b"
         "--bitsentropy NUM"
         "generate passwords with at least NUM bits of entropy"
         :parse-fn #(Integer/parseInt %)
         :validate [integer? "not an integer"]
         :default 44  ]
;     [  "-e"
;        "--equivalent LEN"
;        "generate passwords as strong as a string of LEN random printable ascii symbols"
;        :validate [integer? "not an integer"]  ]
      [  "-f"
         "--filename PATH"
         "a full PATH to a file containing a list of words"
         :default "/usr/share/dict/words"  ]
      [  "-h" "--help" "help"  ]
      [  "-n"
         "--numtogen NUM"
         "number of candidate passwords to generate"
         :parse-fn #(Integer/parseInt %)
         :validate [integer? "not an integer"]
         :default 20  ]  ]  )

; ToDo: write tests that verify whether command-line options get recognized
; ToDo: handle exception where filename does not exist or can't be read

(defn passwords
   [  {  {:keys [bitsentropy numtogen filename help]} :options
          :keys [arguments errors summary]  }  ]
   (if help   (usage 0 summary errors))
   (if errors (usage 1 summary errors))
   (let
      [  bylen (makebylen (split-lines (slurp filename))) ; footnote 1
         maxwordlen (last (sort (keys bylen))) ; footnote 2
         words (reduce into (vals bylen))
         wc (count words)
         wordbits (bits wc)
         nwords (int (ceil (/ bitsentropy wordbits)))  ]
      (printf "Final wordlist contains %d words.  Picking %d words provides at least %d bits of entropy.\n" wc nwords (* wordbits nwords))
      (println (join \newline (repeatedly numtogen #(password words nwords maxwordlen))))))

(defn -main
   [& args]
   (passwords (parse-opts args cli-options)))

; Footnote 1:
;
; I'm not sure why things were grouped by word-length in the original
; implementation, since the author never made use of that.  The only
; reason I'm preserving it here is because I think I may take
; advantage of it for more precision.  (Perhaps that's what the
; original author had in mind too).
;
; Footnote 2:
;
; Well, there's *one* use.  However, I think I'd still like it to only
; consider the max length of the words displayed, not out of all
; *possible* words considered.
