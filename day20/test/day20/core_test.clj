(ns day20.core-test
  (:require [clojure.test :refer :all]
            [day20.core :refer :all]))

(def results
  [
      [
       [[3 0 0] [2 0 0] [-1 0 0]]
       [[4 0 0] [0 0 0] [-2 0 0]]
       ]
      [
       [[4 0 0] [1 0 0] [-1 0 0]]
       [[2 0 0] [-2 0 0] [-2 0 0]]
       ]
      [
       [[4 0 0] [0 0 0] [-1 0 0]]
       [[-2 0 0][-4 0 0] [-2 0 0]]
       ]
      [
       [[3 0 0] [-1 0 0] [-1 0 0]]
       [[-8 0 0] [-6 0 0] [-2 0 0]]
       ]
      ])
      

(deftest test-step
  (testing "updates particles"
    (is (= (step (nth results 0)) (nth results 1)))))

(deftest test-is-final
  (testing "particle is final when the sign of velocity and accelaration match"
    (is (is-final [[ 1 2 3 ] [ 4 5 6 ] [7 8 9]] ))))

(deftest test-run-all
  (testing "finds index of nearest when all particles are final"
    (is (= (run-till-all-final (first results)) 0))))

(deftest collisions
  (testing "exercise collisions"
    (is (= 1 (remove-collisions-until-all-final (first results))))))
