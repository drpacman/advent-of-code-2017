(ns day15.core)

(defn lower-match [ x y ]
  (= (bit-and x 0xffff) (bit-and y 0xffff)))

(defn generator [ seed factor ]
  (iterate #(mod (* factor %) 2147483647) seed))

(defn picky-generator [ seed factor modulus ]
  (filter #(= (mod % modulus) 0) (generator seed factor)))

(defn compare-generators [ genA genB iters ]
  (let [ results (map vector genA genB) ]
    (count (filter #(apply lower-match %)(take iters results)))))

(defn -main [& args]
  (println "part 1 answer is " (compare-generators (generator 634 16807)(generator 301 48271) 40000000))
  (println "part 2 answer is " (compare-generators (picky-generator 634 16807 4)(picky-generator 301 48271 8) 5000000))
  )
