(ns day9.core-test
  (:require [clojure.test :refer :all]
            [day9.core :refer :all]))

(deftest strips-garbage
  (testing "removes up to >"
    (is (= (strip-garbage "xyz>abc") "abc")))

  (testing "skips !"
    (is (= (strip-garbage "xyz!>abc>d") "d"))))
