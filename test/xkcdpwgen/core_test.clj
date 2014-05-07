(ns xkcdpwgen.core-test
   (:require
      [clojure.test   :refer :all]
      [xkcdpwgen.core :refer :all]
   )
)

(deftest bits-zero  (testing "0" (is (= 0 (bits 0)))))
(deftest bits-one   (testing "1" (is (= 0 (bits 1)))))
(deftest bits-two   (testing "2" (is (= 1 (bits 2)))))
(deftest bits-three (testing "3" (is (= 1 (bits 3)))))
(deftest bits-four  (testing "4" (is (= 2 (bits 4)))))

(deftest normal-1 (testing "normal1" (is (= "this" (normalform "   this   ")))))
(deftest normal-2 (testing "normal2" (is (= "here" (normalform "Here's another test")))))
(deftest normal-3 (testing "normal3" (is (= "and another" (normalform "   AND another")))))
(deftest normal-4 (testing "normal4" (is (= "" (normalform "'I wonder what this will do")))))

(deftest makebylen-1 (testing "makebylen1" (is (= {3, ["foo" "bar"], 5 ["fubar"]} (makebylen '("foo" "bar" "fubar"))))))
(deftest makebylen-2 (testing "makebylen2" (is (= {3, ["foo" "bar"], 5 ["fubar"]} (makebylen '(foo bar fubar))))))

; ToDo: (password ...)
