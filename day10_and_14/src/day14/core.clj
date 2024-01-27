(ns day14.core)
(require '[ clojure.string :as str ]
         '[ clojure.set :as set ]
         '[ day10.core :as day10 ])

(defn count-bits [ val ]
  (reduce (fn [total n] (if (bit-test val n) (inc total) total)) 0 (range 4)))

(defn convert-row-to-integers [ row ] 
   (map #(Integer/parseInt (.toString %) 16) (seq row)))

(defn sum-bits-in-row [ row ]
  (reduce + 0 (map count-bits (convert-row-to-integers row))))


(defn neighbour-positions [ pos grid-size ]
  (let [ max (- grid-size 1)
         [ x y ] [ (mod pos grid-size) (quot pos grid-size) ]]
    (cond-> []
       (not (= x 0)) (conj (- pos 1))
       (not (= x max)) (conj (+ pos 1))
       (not (= y 0)) (conj (- pos grid-size))
       (not (= y max)) (conj (+ pos grid-size))
       )))

(defn used? [ pos grid ]
   (let [ index (quot pos 4)
          bit-pos (- 3 (mod pos 4)) ]
     (bit-test (nth grid index) bit-pos)
     ))

(defn used-neighbours [ pos grid neighbours ]
  (let [ size (int (Math/sqrt (* 4 (count grid))))
         targets (neighbour-positions pos size)
         neighbours (conj neighbours pos)
         used-targets (set (filter #(used? % grid) targets))
         new-neighbours (set/difference used-targets neighbours) ]
    ;; if new neighbours found, add to group and add all its neighbours
    (if (empty? new-neighbours)
      neighbours
      (reduce (fn [ neighbours neighbour] (used-neighbours neighbour grid neighbours)) neighbours new-neighbours))))

(defn generate-grid [ knots ]
  (flatten (reduce conj [] (map convert-row-to-integers knots))))

(defn find-existing-group-with-target [ groups target ]
  (first (filter (fn [ group ] (contains? group target)) groups)))

(defn calculate-regions-in-grid [ grid ]
  (let [ max (* 4 (count grid)) ]
    (loop [ pos 0 regions #{}  ]
      (if (>= pos max)
        regions
        (if (and (used? pos grid) (empty? (filter #(contains? % pos) regions)))
          (recur (inc pos) (conj regions (used-neighbours pos grid #{})))
          (recur (inc pos) regions))
        ))))

(defn print-bits [ val ]
   [ (if (bit-test val 3) "#" ".")
     (if (bit-test val 2) "#" ".")
     (if (bit-test val 1) "#" ".")
     (if (bit-test val 0) "#" ".") ]
  )

(defn print-row [ row ]
  ;; take first 8 columns 
  (apply str (flatten (map print-bits (take 2 row)))))
  
(defn print-grid [ rows ]
  ;; print first 8 rows
  (let [ rendered-grid (map print-row
                         (take 8
                               (map convert-row-to-integers rows))) ]
    (doall (map println rendered-grid))))

(defn print-row-regions [ used row len ]
  ;; take first 8 columns 
  (apply str
         (map (fn [col]
                (let [ region (used (+ (* row len) col)) ]
                      (if (nil? region)
                        "."
                        (.toString region)))) (range 8))))
  
(defn print-grid-regions [ used len ]
  ;; print first 8 rows
  (let [ rows (range 8)
         row-regions (map #(print-row-regions used % len ) rows) ]
    (doall (map println row-regions))))

(defn calculate-day-14-result [ seed ]
  (map #(day10/knot-hash (str seed "-" %)) (range 128)))

(defn calculate-day-14-part1 [ results ]
  (reduce + 0 (map sum-bits-in-row results)))

(defn calculate-day-14-part2 [ results ]
  (let [ grid (generate-grid results) ]
    (calculate-regions-in-grid grid)))

(defn execute-part1 []
  (let [ validation-results (calculate-day-14-result "flqrgnkx") ]
    (print-grid validation-results)
    (println "day 14 part 1 validate" (calculate-day-14-part1 validation-results)))
  (let [ results (calculate-day-14-result "xlqgujun") ]
    (println "day 14 part 1" (calculate-day-14-part1 results)))
  )

(defn execute-part2 []
  (let [ validation-results (calculate-day-14-result "flqrgnkx") ]
    (print-grid validation-results)
    (println "day 14 part 2 validate" (count (calculate-day-14-part2 validation-results))))
  (let [ results (calculate-day-14-result "xlqgujun") ]
    (println "day 14 part 2 " (count (calculate-day-14-part2 results))))
)
