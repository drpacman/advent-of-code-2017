(ns day11.core)
(require '[ clojure.string :as str ])

(defn direction [ pos target ]
  (let [ [ tx ty ] target
         [ x y ] pos ]
    (if (> tx x)
      (if (> ty y)
        :ne
        :se)
      (if (< tx x)
        (if (> ty y)
          :nw
          :sw)
        ;; target x is pos x
        (if (> ty y)
          :n
          :s)))
    )
  )
              
(defn calculate-position [ pos direction ]
  (let [ [x y] pos ]
    (case direction
      :n [ x (inc y) ]
      :s [ x (dec y) ]
      :ne [ (inc x) (+ y 0.5) ]
      :nw [ (dec x) (+ y 0.5) ]
      :se [ (inc x) (- y 0.5) ]
      :sw [ (dec x) (- y 0.5) ]
      )))

(defn calculate-positions [ init-pos directions ]
  (reduce calculate-position init-pos directions ))

(defn distance
  "calculates shortest distance to point on hex grid"
  [ target ]
  (loop [ pos [ 0 0.0 ] steps [] ]
    (if ( = target pos )
      (count steps)
      (let [ move (direction pos target) ]
        (recur (calculate-position pos move) (conj steps move)))
     )
  ))

(defn distance-after-navigating
  "calculates shortest distance to point on hex grid"
  [ directions ]
  (distance (calculate-positions [ 0 0 ] directions)))

(defn apply-and-keep-positions [ directions ]
  (loop [ pos [ 0 0.0 ] positions [] directions directions ]
    (if (empty? directions)
      positions
      (let [ newpos (calculate-position pos (first directions)) ]
        (recur newpos (conj positions newpos) (rest directions))
        )
      ))
  )

(defn max-distance [ directions ]
  (let [ positions (apply-and-keep-positions directions) ]
    (apply max (map distance positions))
    )
  )


(defn read-input []
  (map #( keyword %)
       (-> (slurp "resources/input.txt")
      str/trim
      (str/split #","))))

(defn -main [& args]
  (println "Part 1 answer is " (distance-after-navigating (read-input)))
  (println "Part 2 answer is " (max-distance (read-input))))
