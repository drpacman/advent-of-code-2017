(ns day25.core-test
  (:require [clojure.test :refer :all]
            [day25.core :refer :all]))

(def init { :cursor 0
            :values #{}
            :state :a })

(deftest handles-state-a
  (testing "handles a zero"
    (is (= (process { :cursor 0
                      :values #{}
                     :state :a })
                    { :cursor 1
                      :values #{ 0 }
                     :state :b })))
  (testing "handles a one"
    (is (= (process { :cursor 0
                      :values #{ 0 }
                      :state :a })
                    { :cursor -1
                      :values #{}
                     :state :b })))
   )

(deftest handles-state-b
  (testing "handles a zero"
    (is (= (process { :cursor 0
                      :values #{}
                      :state :b })
                    { :cursor -1
                      :values #{ 0 }
                      :state :a })))
  (testing "handles a one"
    (is (= (process { :cursor 0
                      :values #{ 0 }
                      :state :b })
                    { :cursor 1
                      :values #{ 0 }
                      :state :a })))
  )

(deftest runs-steps
  (testing "runs steps"
    (is (= (run-steps init 6)  { :cursor 0
                                :values #{ -2 -1 1 }
                                :state :a }))))
