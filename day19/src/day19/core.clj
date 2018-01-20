(ns day19.core)
(require '[clojure.string :as str])

(defn select [ grid x y]
  (get (nth grid y) x))

(defn is-next [ grid x y r ]
  (not (nil? (re-matches r (str (select grid x y))))))

(defn next-pos
  [grid [x y] dir]
  (let [ c (select grid x y)]
    (case c
      \+
        (case dir
          (:down :up) (if (is-next grid (inc x) y #"[\+\-A-Z]")
                         [[(inc x) y] :right c]
                         [[(dec x) y] :left c])
          (if (is-next grid x (dec y) #"[\+\|A-Z]")
                         [[ x (dec y)] :up c]
                         [[ x (inc y)] :down c])
            )        
      (case dir
        :down [[x (inc y)] :down c]
        :up [[ x (dec y)] :up c]
        :left [[(dec x) y] :left c]
        :right [[(inc x) y] :right c]))
    )
   )

(defn is-nav-char [c]
  (or (= \| c) (= \+ c) (= \- c)))

(defn navigate [ grid ]
  (loop [ pos [ (str/index-of (first grid) \|) 0 ]
          dir :down
          result []
          steps 0 ]
    (let [ [ [x y] d c ] (next-pos grid pos dir) ]
      (if (or (= c \ )
              (>= y (count grid))
              (< y 0)
              (< x 0)
              (>= x (count (first grid))))
        (if (is-nav-char c)
          [ (apply str result) steps ]
          [ (apply str (conj result c)) steps ])
        (if (is-nav-char c)
          (recur [x y] d result (inc steps))
          (recur [x y] d (conj result c) (inc steps)))
        )
      )
    )
  )

(defn read-input []
  (str/split (slurp "resources/input.txt") #"\n"))

(defn -main [& args]
  (println (navigate (read-input))))
        
        
