(ns day4.core
  (require [clojure.string :as str]
           [clojure.java.io :as io]))

(defn validPart1 [value]
  (let [words (str/split value #" ")]
     (= (count (set words)) (count words))))

(defn validPart2 [value]
  (let [words (str/split value #" ")]
     (and (= (count (set words)) (count words))
          (= (count (set (map sort words))) (count words)))))

(defn countValid [validator values]
  (count (filter validator values)))


(defn readLinesFromFile [file]
   (with-open [rdr (io/reader file)]
     (doall (line-seq rdr))))

(defn -main [& args]
  (let [values (readLinesFromFile "input.txt")]
	(println "Part1: count of valid passphrases is" (countValid validPart1 values))
  	(println "Part2: count of valid passphrases is" (countValid validPart2 values))))
