(ns xkcdpwgen.core-test
   (:require
      [clojure.test   :refer :all]
      [xkcdpwgen.core :refer :all]  )  )

(deftest bits-0 (testing "0" (is (= 0 (bits 0)))))
(deftest bits-1 (testing "1" (is (= 0 (bits 1)))))
(deftest bits-2 (testing "2" (is (= 1 (bits 2)))))
(deftest bits-3 (testing "3" (is (= 1 (bits 3)))))
(deftest bits-4 (testing "4" (is (= 2 (bits 4)))))
(deftest bits-5 (testing "5" (is (not (= 3 (bits 5))))))

(deftest normalform-1 (testing "normal1" (is (= "this" (normalform "   this   ")))))
(deftest normalform-2 (testing "normal2" (is (= "here" (normalform "Here's another test")))))
(deftest normalform-3 (testing "normal3" (is (= "and another" (normalform "   AND another")))))
(deftest normalform-4 (testing "normal4" (is (= "" (normalform "'I wonder what this will do")))))
(deftest normalform-5 (testing "normal5" (is (not (= "   this   " (normalform "   this   "))))))

(deftest makebylen-1 (testing "makebylen1" (is (= {3, ["foo" "bar"], 5 ["fubar"]} (makebylen '("foo" "bar" "fubar"))))))
(deftest makebylen-2 (testing "makebylen2" (is (= {3, ["foo" "bar"], 5 ["fubar"]} (makebylen '(foo bar fubar))))))

(deftest password-1 (testing "password1" (is (= "foo"   (password ["foo"] 1 3)))))
(deftest password-2 (testing "password2" (is (= "foo"   (password ["foo"] 2 3)))))
(deftest password-3 (testing "password3" (is (= "foo  " (password ["foo"] 1 5)))))
(deftest password-4 (testing "password4" (is (some #(= % (password ["foo" "bar"] 2 3)) '("foo bar" "bar foo")))))

; ToDo: test for failure conditions...
; that is, test things that should fail, to make sure they actually do fail
