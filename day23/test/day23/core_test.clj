(ns day23.core-test
  (:require [clojure.test :refer :all]
            [day23.core :refer :all]))

(def machine
  {
   :registers { :a 0 :b 2 :c 0 :d 0 :e 0 :f 0 :g 0 :h 0 }
   :pc 0
   :counts 0
  })

(deftest test-set-instructions
  (testing "set updates register"
    (is (= (((execute-instruction "set a 1" machine ) :registers) (keyword "a")) 1)))
  (testing "set updates register"
    (is (= (((execute-instruction "set a b" machine ) :registers) (keyword "a")) 2)))
  )

(deftest test-sub-instructions
  (testing "sub decreases register"
    (is (= (((execute-instruction "sub a 1" machine ) :registers) :a) -1)))
  (testing "sub decreases register"
    (is (= (((execute-instruction "sub a b" machine ) :registers) :a) -2)))
  )

(deftest test-mul-instructions
  (testing "mul multiples x by y and sets it to register x"
    (is (= (((execute-instruction "mul b 3" machine ) :registers) :b) 6)))
  (testing "sub decreases register"
    (is (= (((execute-instruction "mul b b" machine ) :registers) :b) 4)))
  )

(deftest test-jnz-instructions
  (testing "jnz x updates pc by value if non zero "
    (is (= ((execute-instruction "jnz b 3" machine ) :pc) 3)))
  (testing "jnz x just increments pc if zero "
    (is (= ((execute-instruction "jnz a 3" machine ) :pc) 1)))

  )

(deftest execution
  (testing "runs multiple instructions"
    (is (= (((execute-instructions ["set a 5" "set b 4" "mul a b"]) :registers) :a) 20))))

(deftest load-input-test
  (testing "load instructions from file"
    (is (= (count (load-instructions)) 32))))
