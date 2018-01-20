(ns day5.core
  (require [clojure.java.io :as io]))


(defn process [ins cursor offsetter]
  (let [jump (nth ins cursor)]
    { :map (concat (take cursor ins) [(offsetter jump)] (drop (inc cursor) ins)) :cursor (+ cursor jump)}))

(defn processBasic [ins cursor]
  (process ins cursor (partial inc)))

(defn processExtended [ins cursor]
  (process ins cursor ( fn[x] (if (>= x 3) (dec x) (inc x)))))

(defn countStepToEscape [ins processor]
   (loop [ins ins cursor 0 steps 0]
      (if (= (mod steps 100000) 0) (println steps))
      (if (or (>= cursor (count ins))
              (< cursor 0)) 
            steps
            (let [next (processor ins cursor)]
              (recur (next :map) (next :cursor) (inc steps)))))) 

(defn countStepToEscapeBasic [ins]
   (println "basic")
   (countStepToEscape ins processBasic))

(defn countStepToEscapeExtended [ins]
     (println "extended")
     (countStepToEscape ins processExtended))

(defn readLinesFromFile [file]
   (with-open [rdr (io/reader file)]
     (doall (line-seq rdr))))

(def input (map read-string (readLinesFromFile "input.txt")))

(defn -main [& args]
  (println "Steps to escape basic" (countStepToEscapeBasic [0 3 0 1 -3]))
  (println "Steps to escape basic" (countStepToEscapeBasic input))
  (println "Steps to escape extended" (countStepToEscapeExtended [0 3 0 1 -3]))
  (println "Steps to escape extended" (countStepToEscapeExtended input)))
