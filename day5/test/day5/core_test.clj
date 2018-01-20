(ns day5.core-test
  (:require [clojure.test :refer :all]
            [day5.core :refer :all :as d]))

(deftest escape-maze
  (testing "processing intruction increments current instruction"
    (let [ins [0 1]
          cursor 0]
     (is (= (d/processBasic ins cursor) { :map [1 1] :cursor 0 }))))

  (testing "processing example maze"
    (let [ins [0 3  0  1  -3]
          cursor 0]
     (is (= (d/processBasic ins cursor) { :map [1 3  0  1  -3] :cursor 0}))))  

  (testing "processing example maze"
    (let [ins [0 3  0  1  -3]
          cursor 0
          first (d/processBasic ins cursor)]
     (is (= (d/processBasic (first :map) (first :cursor)) { :map [2 3  0  1  -3] :cursor 1 }))))  

   (testing "count escape steps basic"
     (is (= (d/countStepToEscapeBasic [0 3  0  1  -3]) 5)))

   (testing "count escape steps extended"
     (is (= (d/countStepToEscapeExtended [0 3 0 1 -3]) 10)))
)
