(ns day24.core-test
  (:require [clojure.test :refer :all]
            [day24.core :refer :all]))

(def test-components [ "0/2" "2/2" "2/3" "3/4" "3/5" "0/1" "10/1" "9/10" ])

(deftest parses-component
  (testing "finds candidate components"
    (is (= (parse test-components) [ [ 0 2 ] [ 2 2 ] [ 2 3 ] [ 3 4 ] [ 3 5 ] [ 0 1 ] [ 10 1 ]  [ 9 10 ] ]))))

(deftest removes-component
  (testing "finds and removes component"
    (is (= (remove-component (parse test-components) [ 2 2 ])
           [ [ 0 2 ] [ 2 3 ] [ 3 4 ] [ 3 5 ] [ 0 1 ] [ 10 1 ]  [ 9 10 ] ]))))

(deftest find-other-connection-test
  (testing "finds other index"
    (is (= (find-other-connection [ 1 2 ] 1) 2))))

(deftest find-component
  (testing "finds candidate components"
    (is (= (count (find-component-match (parse test-components) 0)) 2))))

(deftest calculates-bridge-strength
  (testing "calcs strength from sum of each entry"
    (is (= (bridge-strength [[0 1][ 10 1] [9 10]]) 31))))

(deftest builds-bridges-with-matching-component
  (testing "builds bride from next matching component"
    (is (some #( = [[ 0 1 ] [ 10 1 ] [ 9 10 ]] %)
              (build-bridges (parse test-components)))
        )))

(deftest finds-max-bridge
  (testing "finds bridge with max strenght"
    (is (= (max-bridge-strength (build-bridges (parse test-components))) 31))))

(deftest finds-longest-bridge
  (testing "finds longest bridge"
    (is (= (find-longest-bridges
            (build-bridges
             (parse test-components))) [[[0 2] [2 2] [2 3] [3 4]]
                                        [[0 2] [2 2] [2 3] [3 5]]]))))
