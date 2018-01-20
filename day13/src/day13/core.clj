(ns day13.core)
(require '[ clojure.java.io :as io ])

(defn readLinesFromFile [file]
   (with-open [rdr (io/reader file)]
     (doall (line-seq rdr))))

(defn read-input []
  (readLinesFromFile "resources/input.txt"))

(defn parse-input [ input ]
  (map ( fn [entry]
           (let [ [ _ pos depth ] (re-matches #"(\d+): (\d+)" entry) ]
             [(Integer/parseInt pos) (Integer/parseInt depth)])) input))

(defn at-zero [ round level-depth ]
  (= (mod round (* 2 (- level-depth 1))) 0))

(defn trip-severity [ input ]
   (reduce (fn [ severity entry ]
           (let [ [ pos depth ] entry ]
             (if (at-zero pos depth)
               (+ severity (* pos depth))
               severity))) 0 input))

(defn is-safe? [ round input ]
  (empty?
    (drop-while (fn [ entry ]
                  (let [ [ pos depth ] entry ]
                    (not
                     (at-zero (+ round pos) depth)
                     )))
                input)
    ))

(defn find-delay [ input ]
  (loop [ round 0 ]
    (if (is-safe? round input)
      round
      (recur (inc round)))))

(defn -main [& args]
  (println "part 1 answer, trip severity is " (trip-severity (parse-input (read-input))))
  (println "part 2 answer, delay to safely cross is " (find-delay (parse-input (read-input))))
  )

