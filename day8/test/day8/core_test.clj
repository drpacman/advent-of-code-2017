(ns day8.core-test
  (:require [clojure.test :refer :all]
            [day8.core :refer :all]))

(deftest parse-test
  (testing "parses register"
    (is (= ((parse "b inc 5 if a > 1") :register) :b)))
  
  (testing "parses change dec"
    (is (= ((parse "a dec 1 if a == 1") :change) -1)))

  (testing "parses change dec with negative"
    (is (= ((parse "a dec -1 if a == 1") :change) 1)))

  (testing "parse cond =="
    (is ( ((parse "a dec 1 if a == 2") :cond) { :a 2 })))

  (testing "parse cond !="
    (is ( ((parse "a dec 1 if a != 2") :cond) { :a 3 })))

  (testing "parse cond >="
    (is ( ((parse "a dec 1 if a >= 3") :cond) { :a 3 })))

  (testing "parse cond <="
    (is (not (((parse "a dec 1 if a <= 2") :cond) { :a 3 }))))

  (testing "parse cond >"
    (is (not (((parse "a dec 1 if a > 2") :cond) { :a 1 }))))

  (testing "parse cond >"
    (is (((parse "a dec 1 if a > 2") :cond) { :a 3 })))

  (testing "updates registers"
    (let [ registers { :a 2 :b 0 } 
           instr (parse "b inc 5 if a > 1") ]
      (is (instr :cond))
      (let [ r (update-registers registers instr) ]
        (is (= (r :b) 5)))))

  (testing "does not update registers if cond does not match"
    (let [ registers { :a 2 :b 0 } 
           instr (parse "b inc 5 if a > 3") ]
      (is (instr :cond))
      (let [ r (update-registers registers instr) ]
        (is (= (r :b) 0)))))

  (testing "treats missing register as value 0"
    (let [ registers { :a 2 :b 0 } 
           instr (parse "abc inc 5 if a == 2") ]
      (is (instr :cond))
      (let [ r (update-registers registers instr) ]
        (is (= (r :abc) 5)))))
  )

(deftest apply-instruction
  (testing "applies each instruction"
    (let [ entries [ "a inc 5 if a == 0" "b inc 2 if a == 5" ]
          instrs (map parse entries) ]
      (is (= ((apply-instructions instrs) :b) 2)))))
