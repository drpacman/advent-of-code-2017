(ns day18.core-test
  (:require [clojure.test :refer :all]
            [day18.core :refer :all]))

(def instructions ["set a 1" "add a 2" "mul a a" "mod a 5"])
(def instructions-all  ["set a 1" "add a 2" "mul a a" "mod a 5" "snd a" "set a 0" "rcv a" "jgz a -1" "set a 1" "jgz a -2"])

(deftest test-example
  (testing "testing full example"
    (let [ result (execute-instructions instructions-all (create-process)) ]
    (is (= 4 (first
              (deref
               (result :queue)))
           ))))
  )

(deftest test-set-up
  (testing "set executed"
    (is (= 1 (get-in (execute-instructions ["set a 1"] (create-process)) [:registers :a]))))
  (testing "register set then add num"
    (is (= 3 (get-in (execute-instructions ["set a 1" "add a 2"] (create-process)) [:registers :a]))))
  (testing "register set then add other reg"
    (is (= 2 (get-in (execute-instructions ["set a 1" "add a a"] (create-process)) [:registers :a]))))
  (testing "register set then mul other reg"
    (is (= 6 (get-in (execute-instructions ["set a 2" "mul a 3"] (create-process)) [:registers :a]))))
  (testing "register set then mul other reg"
    (is (= 4 (get-in (execute-instructions ["set a 2" "mul a a"] (create-process)) [:registers :a]))))
  (testing "register updated"
    (is (= 4 (get-in (execute-instructions instructions (create-process)) [:registers :a]))))
  )

(deftest test-snd-rcv
  (testing "snd for part 1 sets queue value"
    (is (= 4 (first
              (deref
               ((execute-instructions ["snd 4"] (create-process)) :queue))))))
  )

(deftest test-snd-rcv-2-processes
  (testing "snd for part 2 sets queue value"
    (let [ [ process-a process-b ] (execute-instructions ["snd 1" "snd 2" "snd p" "rcv a" "rcv b" "rcv c" "rcv d"] (create-process 0) (create-process 1)) ]
      (is (and (= 1 (get-in process-a [:registers :c]))
               (= 0 (get-in process-b [:registers :c]))))
      ))
  )
