(ns day17.core-test
  (:require [clojure.test :refer :all]
            [day17.core :refer :all]))

(deftest test-spin
  (testing "test step 3 item 1"
    (is (= (spin 3 1) [ 0 1 ])))
  (testing "test step 3 item 2"
    (is (= (spin 3 2) [ 0 2 1 ])))
  (testing "test step 3 item 2"
    (is (= (spin 3 3) [ 0  2 3  1 ])))
  )
