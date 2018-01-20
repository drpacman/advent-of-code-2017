(ns day13.core-test

  (:require [clojure.test :refer :all]
            [day13.core :refer :all]))

(def raw-input [ [ 0 3 ] [ 1 2 ] [4 4 ] [6 4] ])

(deftest trip-severity-test
  (testing "test severity"
    (is (= (trip-severity raw-input) 24))))

(deftest find-delay-fast-test
  (testing "test delay"
    (is (= (find-delay raw-input) 10))
    ))

(deftest at-zero-test
  (testing "round 1 depth 3 is not at zero"
    (is (not (at-zero 1 3))))
  (testing "round 4 depth 3 is at-zero"
    (is (at-zero 4 3)))
  (testing "round 12 depth 7 is non-zero"
    (is (not (at-zero 1 3))))
  )
    
