(ns day11.core-test
  (:require [clojure.test :refer :all]
            [day11.core :refer :all]))

(deftest test-distances
  (testing "Run ne ne ne"
    (is (= (distance-after-navigating [ :ne :ne :ne ])) 3 ))

  (testing "Run ne ne sw sw"
    (is (= (distance-after-navigating [ :ne :ne :sw :sw ])) 0 ))

  (testing "Run ne ne s s"
    (is (= (distance-after-navigating [ :ne :ne :s :s ])) 2 ))

  (testing "Run se sw se sw sw"
    (is (= (distance-after-navigating [ :se :sw :se :sw :sw ])) 3 ))

  )
(deftest test-position-change
  (testing "moving north from [0 0] changes to [0 1]"
    (is (= (calculate-position [ 0 0 ] :n) [ 0 1 ])))
  (testing "moving south from [0 0] changes to [0 -1]"
    (is (= (calculate-position [ 0 0 ] :s) [ 0 -1 ])))
  (testing "moving north-east from [0 0] changes to [1 0.5]"
    (is (= (calculate-position [ 0 0 ] :ne) [ 1 0.5 ])))
  (testing "moving north-west from [0 0] changes to [-1 0.5]"
    (is (= (calculate-position [ 0 0 ] :nw) [ -1 0.5 ])))
  (testing "moving south-east from [0 0] changes to [1 -0.5]"
    (is (= (calculate-position [ 0 0 ] :se) [ 1 -0.5 ])))
  (testing "moving south-west from [0 0] changes to [-1 -0.5]"
    (is (= (calculate-position [ 0 0 ] :sw) [ -1 -0.5 ])))
  )

(deftest test-multi-move
  (testing "ne ne ne position is "
    (is (= (calculate-positions [ 0 0 ] [ :ne :ne :ne ]) [ 3 1.5 ])))
  (testing "ne ne sw sw position is "
    (is (= (calculate-positions [ 0 0 ] [ :ne :ne :sw :sw ]) [ 0 0.0 ])))
  (testing "ne ne s s position is "
    (is (= (calculate-positions [ 0 0 ] [ :ne :ne :s :s ]) [ 2 -1.0 ])))
  (testing "se sw se sw sw position is "
    (is (= (calculate-positions [ 0 0 ] [ :se :sw :se :sw :sw ]) [ -1 -2.5 ])))
  )

(deftest test-direction
  (testing "at [ 0 0 ], target is [ 1 1 ], move ne"
    (is (=  (direction [0 0] [1 1]) :ne)))
  (testing "at [ 0 0 ], target is [ 1 0 ], move ne"
    (is (=  (direction [0 0] [1 0]) :se)))
  (testing "at [ 0 0 ], target is [ 0 1 ], move n"
    (is (=  (direction [0 0] [0 1]) :n)))
  (testing "at [ 0 0 ], target is [ 1 -0.5 ], move se"
    (is (=  (direction [0 0] [1 -0.5]) :se)))
  )
  
