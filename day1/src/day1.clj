(ns day1
 (require [clojure.java.io :as io]))

(def input (clojure.string/trim (slurp "input.txt")))

(defn pos [curr max step]
  (mod (+ curr step) max))

(defn twoEqual [ list curr offset ]
 (let [max (count list)
       first (nth list curr)
       second (nth list (pos curr max offset))] 
  (if (= first second) (. Integer parseInt first) 0)))

(defn sumDuplicateNext [list offset]
  (reduce + (for [i (range (count list))]
    (twoEqual list i offset))))
     
(defn -main [& args]
  (println (sumDuplicateNext (map str input) (/ (count input) 2))))
