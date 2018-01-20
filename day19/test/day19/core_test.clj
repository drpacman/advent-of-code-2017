(ns day19.core-test
  (:require [clojure.test :refer :all]
            [day19.core :refer :all]))

(def grid ["    |          " 
           "    |  +--+    " 
           "    A  |  C    " 
           "F---|----E|--+ " 
           "    |  |  |  D "
           "    +B-+  +--+ "])

(deftest test-selecting
  (testing "picks item"
    (is (= (select grid 0 3) \F)))
  (testing "picks item"
    (is (= (select grid 4 0) \|))))

(deftest test-continuing-dir
  (testing "follows | to next vertical |"
    (is (= (next-pos grid [4 0] :down) [[4 1] :down \|])))
  (testing "follows | to next vertical letter"
    (is (= (next-pos grid [4 1] :up) [[4 0] :up \|])))
  (testing "follows - to next vertical position left"
    (is (= (next-pos grid [4 4] :left) [[3 4] :left \|])))
  )

(deftest test-changing-dir
  (testing "follows + and up to next horizonatal position left"
    (is (= (next-pos grid [7 1] :up) [[8 1] :right \+])))
  (testing "follows + and down to next horizonatal position left"
    (is (= (next-pos grid [7 5] :down) [[6 5] :left \+])))
  (testing "follows | and right to next vertical position up"
    (is (= (next-pos grid [7 5] :right) [[7 4] :up \+])))
  )

(deftest test-navigating
  (testing "navigates grid"
    (is (= (navigate grid) "ABCDEF"))))
