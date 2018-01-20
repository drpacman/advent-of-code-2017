(ns day16.core-test
  (:require [clojure.test :refer :all]
            [day16.core :refer :all]))

(deftest spin-test
  (testing "spin 2 moves 2 round"
    (is (= (spin 2 "abcde") "deabc"))))

(deftest xchg-test
  (testing "exchange pos 2 with pos 3 moves swaps c and d"
    (is (= (xchg 2 3 "abcde") "abdce"))))

(deftest swap-test
  (testing "swap item c with d"
    (is (= (swap \c \d "abcde") "abdce"))))

(deftest test-steps
  (testing "example sequence"
    (is (= (->> "abcde"
                (spin 1)
                (xchg 3 4)
                (swap \e \b)) "baedc"))))

(deftest consume-input-test
  (testing "example sequence as str"
    (is (= (apply-input [ "x15/1" "s15" "x2/3" "s15" "x11/1" "pm/a"]) "abc"))))

(deftest test-match-entry
  ;;(testing "parse entry spin"
  ;;  (is (= ((match-entry "s1") "abcde") "eabcd")))
  (testing "parse entry xchg"
    (is (= ((match-entry "x2/3") "abcde") "abdce")))
  (testing "parse entry swap"
    (is (= ((match-entry "pa/b") "abcde") "bacde")))
  )
