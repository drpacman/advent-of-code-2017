(ns day10.core-test
  (:require [clojure.test :refer :all]
            [day10.core :refer :all]))

(def input "d472")
(def input-as-ints (convert-row-to-integers input))

(deftest day-14-neighbours-test
  (testing "finds neighours in a 4 x 4 grid"
    (is (= (used-neighbours 1 input-as-ints) [ 0 5 ]))))

(deftest loads-grid
  (testing "generates grid"
    (is (= (generate-grid [ "d" "4" "7" "2" ]) [ 13 4 7 2 ]))))

(deftest calculate-regions-in-grid-test
  (testing "calculates correct number of regions"
    (is (= ( (calculate-regions-in-grid (generate-grid [ "d" "4" "7" "2" ])) :region) 2))))
