(ns day4.core
  (require [clojure.string :as str]
           [clojure.java.io :as io]))

(defn valid [value]
  (let [words (str/split value #" ")]
     (and (= (count (set words)) (count words))
          (= (count (set (map sort words))) (count words)))))

(defn countValid [values]
  (count (filter valid values)))

(defn readLinesFromFile [file]
   (with-open [rdr (io/reader file)]
     (doall (line-seq rdr))))

(defn -main [& args]
  (println "count of valid passphrases is" (countValid (readLinesFromFile "input.txt"))))
