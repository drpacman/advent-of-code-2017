(ns day4.core-test
  (:require [clojure.test :refer :all]
            [day4.core :refer :all :as d]))

(deftest validity-test
  (testing "validates non-duplating words"
    (is (d/valid "aa bb cc dd ee")))
  (testing "duplicate words are invalid")
    (is (not (d/valid "aa bb cc dd aa")))
  (testing "words of different lengths are valid"
    (is (d/valid "aa bb cc dd aaa")))
)
(deftest counting-test
  (testing "count valid entries"
    (is (= 1 (d/countValid ["aa bb aa" "aa bb"]))))
)
(deftest anagram-test
  (testing "anagrams are invalid"
    (is (not (d/valid "abcde xyz ecdab")))))
