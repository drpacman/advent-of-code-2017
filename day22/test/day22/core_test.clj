(ns day22.core-test
  (:require [clojure.test :refer :all]
            [day22.core :refer :all]))

(def start { [-1 0] \# [1 1] \# })

(deftest steps-test
  (testing "node infected, heading up, turns right, makes current clean"
    (is (= (act { [0 0] \# } [0 0] "UP" 0) '( { [ 0 0 ] \. } [1 0] "RIGHT" 0) )))
  (testing "node uninfected, heading up, turns left, makes current clean"
    (is (= (act start [ 0 0 ] "UP" 0) '( { [0 0] \# [-1 0] \# [1 1] \# } [-1 0] "LEFT" 1) )))
  )

(deftest multiple-steps-test
  (testing "validate using example step 7"
    (let [ [ curr pos dir n ] (run start 7) ]
      (is (= n 5))))
  (testing "validate using example step 70"
    (let [ [ curr pos dir n ] (run start 70) ]
      (println curr pos dir)
     (is (= n 41))))
  (testing "validate using example step 10000"
    (let [ [ curr pos dir n ] (run start 10000) ]
     (is (= n 5587))))
  )

(deftest parse-test
  (testing "test parsing"
    (is (= (count (parse-input [ "..#" "#.." "..." ])) 9)))
  )

(deftest read-input-test
  (testing "test parsing"
    (is (= ((read-input) [ -11 -12 ]) \#)))
  (testing "test parsing"
    (is (= ((read-input) [ 12 11 ]) \#)))
  (testing "test initial"
    (is (= ((read-input) [0 0]) \#)))
  (testing "test parsing"
    (is (= (count (read-input)) 625)))

  )

