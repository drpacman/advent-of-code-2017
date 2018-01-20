(ns day12.core-test
  (:require [clojure.test :refer :all]
            [day12.core :refer :all]))

(def testinput [ "0 <-> 2" "1 <-> 1" "2 <-> 0, 3, 4" "3 <-> 2, 4" "4 <-> 2, 3, 6" "5 <-> 6" "6 <-> 4, 5" ])

(deftest parse-input-test
  (testing "parses id <-> id2, id3,..."
    (is (= (parse-input-line "1 <-> 2,3,4") [ :1 #{ :2 :3 :4} ])))

  (testing "parses id <-> id2, id3,..."
    (is (= (parse-input-line "12323 <-> 2") [ :12323 #{ :2 } ])))

  )

(deftest group-inputs-test
  (testing "finds groups"
    (let [ input (parse-input-lines testinput) ]
      (is (= (count (groups input)) 2))))

  (testing "count of programs which contain program 0"
    (let [ input (parse-input-lines testinput)
          groups (groups input) ]
      (is (= (count-programs-in-set-for-entry groups :0) 6))
    ))
  )
  
