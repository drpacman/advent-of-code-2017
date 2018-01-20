(ns day21.core-test
  (:require [clojure.test :refer :all]
            [day21.core :refer :all]))

(def rules [ "../.#" "##./#../..." ".#./..#/###" "#..#/..../..../#..#"])

(def init ".#./..#/###")
(def test-rule-1 ".#./..#/###")
(def test-rule-2 ".#./#../###")
(def test-rule-3 "#../#.#/##.")
(def test-rule-4 "###/..#/.#.")

(deftest parse-test
  (testing "parses input"
    (is (= (count (parse init)) 3))))

(deftest match-line-test
  (testing "matches rule if same as item"
    (is (matches-line (first (parse init)) ".#.")))
  (testing "matches rule if reverse of rule is same as item"
    (is (matches-line (first (parse init)) ".#.")))
  )

(deftest match-rule-test
  (testing "matches rule if matches all lines"
    (is (matches-rule (parse init) (parse test-rule-1))))
  (testing "matches rule if matches all lines"
    (is (matches-rule (parse init) (parse test-rule-2))))
  (testing "matches rule if matches all lines"
    (is (matches-rule (parse init) (parse test-rule-3))))
  (testing "matches rule if matches all lines"
    (is (matches-rule (parse init) (parse test-rule-4))))
  )

(deftest split-input-test
  (testing "splits into 2x2 if size divides by 2"
    (is (= (split-input (parse "1234/5678/abcd/efgh")) [[[ "12" "56" ]
                                                         [ "34" "78" ]]
                                                        [[ "ab" "ef" ]
                                                         [ "cd" "gh" ]]]))
    )
  (testing "splits into 3x3 if size divides by 3"
    (is (= (split-input (parse "123/456/789")) [[ ["123" "456" "789"] ]]))
    )
  )

(deftest join-test
  (testing "joins split correctly"
    (is (= (join-inputs [[[ "12" "56" ]
                          [ "34" "78" ]]
                         [[ "ab" "ef" ]
                          [ "cd" "gh" ]]]) [ "1234" "5678" "abcd" "efgh"]))))

(def test-rules { "../.#" "##./#../..."
                  ".#./..#/###" "#..#/..../..../#..#" })

(deftest applying-rules
  (testing "apply first test rule"
    (is (= (apply-rules (parse init) test-rules) ["#..#"
                                                   "...."
                                                   "...."
                                                  "#..#"])))
  (testing "apply first test rule"
    (is (= (iterate-input (parse init) test-rules) ["#..#"
                                                    "...."
                                                    "...."
                                                    "#..#"])))
  (testing "apply multiple iterations"
    (is (= (iterate-input-times (parse init) test-rules 2) [ "##.##."
                                                             "#..#.."
                                                             "......"
                                                             "##.##."
                                                             "#..#.."
                                                             "......" ])))
  (testing "count pixels are 2 iterations"
    (is (= (count-pixels
            (iterate-input-times (parse init) test-rules 2)) 12)))

  )

(deftest debug
  (testing "debug"
    (is (= (count
            (find-rule (parse init) (read-input))) 1))))


;;(deftest test-iteration
;;  (testing "count pixels are 2 iterations"
;;    (is (= (count-pixels
;;            (iterate-input-times (parse init) (read-input) 5)) 100))))


  
