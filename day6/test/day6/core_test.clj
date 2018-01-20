(ns day6.core-test
  (:require [clojure.test :refer :all]
            [day6.core :refer :all]))

(deftest memory-allocation
  (testing "blocks are reallocated step 1"
    (is (= (allocate [0 2 7 0]) [2 4 1 2])))

  (testing "blocks are reallocated step 2"
    (is (= (allocate [2 4 1 2]) [3 1 2 3])))

  (testing "blocks are reallocated step 3"
    (is (= (allocate [3 1 2 3]) [0 2 3 4])))
)

(deftest count-to-repeat-test
  (testing "example repeats after 5 steps"
    (is (= ((count-to-repetition [0 2 7 0]) :steps) 5))))

(deftest count-loop-to-repeat-test
  (testing "example repeats after 4 steps after first occurance"
    (is (= ((count-to-repetition [0 2 7 0]) :loop-length) 4))))

(deftest preparation
  (testing "max block is identified"
    (let [v (prep [0 2 7 0])]
     (is (= (v :blocks) 7))
     (is (= (v :idx) 2))
     (is (= (v :preped-bank) [0 2 0 0])))))
                            
