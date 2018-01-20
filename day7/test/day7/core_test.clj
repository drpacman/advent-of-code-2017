(ns day7.core-test
  (:require [clojure.test :refer :all]
            [day7.core :refer :all]))

(def test-data [ { :id :d :weight 41 :children [ :a :b :f ] }
                { :id :b :weight 70  }
                { :id :a :weight 68 :children [ :c :e ] }
                { :id :e :weight 1 }
                { :id :c :weight 1 }
                { :id :f :weight 71 }])

(deftest find-bottom-item-test
  (testing "find bottom item"
     (is (= ((root test-data) :id) :d))))

(deftest node-weighting
  (testing "inserts node weights into returned structure"
    (let [ result (with-node-weights test-data)
          node-a (first (filter #(= (% :id) :a) result))]
      (is (= (node-a :node-weight) 70))
      )))

(deftest check-balance
  (testing "groups children by weight"
    (let [ result (with-node-weights test-data)
           child-groups (child-groups result :a) ]
      (is (= (count (child-groups 1)) 2))))

   (testing "detects unbalanced node"
     (let [ result (with-node-weights test-data) ]
       (is (balanced? result :a))))
  
   (testing "finds deepest unbalanced node"
       (let [ result (with-node-weights test-data)
              deepest (find-unbalanced-child result :d) ]
         (is (= deepest :f))
         )
       )
 )
 
