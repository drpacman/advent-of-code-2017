(ns day15.core-test
  (:require [clojure.test :refer :all]
            [day15.core :refer :all]))

(deftest identifies-lower-match
  (testing "matches lower 16 bits"
    (is (lower-match 10 10)))
  (testing "matches lower 16 bits when higher bits don't match"
    (is (lower-match 0xab1111 0x101111))))

(deftest generators-test
  (testing "generator a"
    (is (= (last
            (take 2
                  (generator 65 16807)
                  ))
           1092455)))

  (testing "generator a result 5"
    (is (= (last
            (take 6
                  (generator 65 16807)
                  ))
           1352636452)))

  (testing "generator b result 2"
    (is (= (last
            (take 5
                  (generator 8921 48271)
                  ))
           137874439)))

  (testing "generator b result "
    (is (= (last
            (take 4
                  (generator 8921 48271)
                  ))
           1431495498)))
  )

(deftest counts-test
  (testing "counts matches"
    (is (= (compare-generators (generator 65 16807)(generator 8921 48271) 40000000) 588))))

(deftest picky-counts-test
  (testing "picky counts matches"
    (is (= (compare-generators (picky-generator 65 16807 4)(picky-generator 8921 48271 8) 5000000) 309))))
