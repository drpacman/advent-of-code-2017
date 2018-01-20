(ns day10.core)
(require '[ clojure.string :as str ]
         '[ clojure.set :as set ])

(defn reverse-elems [ ring start length ] 
  (let [ ring-size (count ring)
         joined-ring (concat (drop start ring) ring)
         [ section remainder ] (split-at length joined-ring)
         reversed-joined-ring (concat (reverse section) remainder)
         ring-section (take ring-size reversed-joined-ring)
         head-length (- ring-size start)
         [ head tail ] (split-at head-length ring-section)
        ]
    (concat tail head)
    
        ))

(defn runStepsWithRing [ ring start skip-size lengths ]
  (let [ length (first lengths) ]
    (if (empty? lengths)
      [ ring start skip-size ]
      (recur (reverse-elems ring start length)
             (mod (+ start length skip-size) (count ring))
             (inc skip-size)
             (rest lengths))
       )))

(defn runSteps [ numElems lengths ]
  (runStepsWithRing (range 0 numElems) 0 0 lengths))

(defn read-input []
  (str/trim (slurp "resources/input.txt")))

(defn read-input-part-1 [ input ]
  (map #( Integer/parseInt % )
       (-> input
            str/trim
            (str/split #","))))

(defn convert-input-part-2 [ input ]
  (concat (map #(int %) input) [ 17, 31, 73, 47, 23 ]))

(defn do-rounds [ numElems rounds lengths ]
  (loop [ ring (range 0 numElems) rounds rounds start 0 skip-size 0 ]
    (if (> rounds 0)
      (let [ [ ring start skip-size ] (runStepsWithRing ring start skip-size lengths) ]
        (recur ring (dec rounds) start skip-size))
      ring
      )))

(defn sparse-hash [ lengths ]
  (do-rounds 256 64 lengths))

(defn dense-hash [ lengths ]
  (let [ sparse (sparse-hash lengths)
         partitions (partition 16 sparse) ]
    (map #(apply bit-xor %) partitions)))

(defn hex-dense-hash [ lengths ]
  (let [ dense (dense-hash lengths) ]
    (apply str (map #(format "%02x" %) dense))))

(defn knot-hash [ seed ]
  (hex-dense-hash (convert-input-part-2 seed)))

(defn execute-part1 []
  (let [ [ring _ _] (runSteps 256 (read-input-part-1 (read-input)))]
    (println "Day 10 Part 1 answer is " (* (nth ring 0) (nth ring 1)))
    )
  (println "knot hash of empty string is " (hex-dense-hash (convert-input-part-2 "")))
  (println "knot hash of Aoc 2017 is " (hex-dense-hash (convert-input-part-2 "AoC 2017")))
  (println "knot hash of 1,2,3 is" (hex-dense-hash (convert-input-part-2 "1,2,3")))
  (println "knot hash of 1,2,4 is" (hex-dense-hash (convert-input-part-2 "1,2,4")))
  (println "knot hash of flqrgnkx-0" (knot-hash "flqrgnkx-0"))
  )

(defn execute-part2 []
  (let [ input (convert-input-part-2 (read-input)) ]
    (println "day 10 Part 2 answer is " (hex-dense-hash input))))
