(ns xkcdpwgen.core-test
   (:require
      [clojure.test   :refer :all]
      [xkcdpwgen.core :refer :all]
   )
)

(deftest zero  (testing "0" (is (= 0 (bits 0)))))
(deftest one   (testing "1" (is (= 0 (bits 1)))))
(deftest two   (testing "2" (is (= 1 (bits 2)))))
(deftest three (testing "3" (is (= 1 (bits 3)))))
(deftest four  (testing "4" (is (= 2 (bits 4)))))
