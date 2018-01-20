(ns day2)
(require '[clojure.data.csv :as csv]
         '[clojure.java.io :as io])

(def entries (with-open [reader (io/reader "input.csv")] (doall (csv/read-csv reader))))

(defn validpair [n1 n2]
  (or (= (mod n1 n2) 0)(= (mod n2 n1) 0)))

(defn divisor [row]
  (let [r (sort row)
        h (first r)
        t (rest r)]
    (println r)
    (first (map #(/ % h) (filter #(validpair h %) t)))))

(defn divisible [row]
  ( let [sortedrow (sort row)]
  (loop [r sortedrow]
    (if (empty? r)
      0
      (let [d (divisor r)]
        (if (not (nil? d))
          d
        (recur (rest r))))))))

(defn checksumentry [row]
  (- (last row) (first row)))

(defn sortrow [row]
   (sort (map read-string row)))

(defn checksum [lst]
  (reduce + (for [row lst](divisible (map read-string row)))))

(defn -main [& args]
  (print (checksum entries)))

