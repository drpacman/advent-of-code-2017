(ns day3)

(def RIGHT 1)
(def UP 2)
(def LEFT 3)
(def DOWN 4)

(defn coords [stopCond entryCalc]
  (loop [digit 1 direction UP len 1 x 1 y 0 lookup { {:x 0 :y 0} 1 }]
    (let [ coord {:x x :y y}
           lookup (entryCalc lookup coord digit)
           next (lookup coord)]
    (if (stopCond (lookup coord))
      { :map lookup :key coord }
      (case direction
        1 (let [x (+ x 1)]
            (if (= x len)
              (recur next UP len x y lookup) 
              (recur next RIGHT len x y lookup)))
        2 (let [y (+ y 1)]
            (if (= y len)
              (recur next LEFT len x y lookup)
              (recur next UP len x y lookup)))
        3 (let [x (- x 1)]
            (if (= x (-' len))
              (recur next DOWN len x y lookup)
              (recur next LEFT len x y lookup)))
        4 (let [y (- y 1)]
            (if (= y (-' len))
              (recur next RIGHT (+ len 1) x y lookup)
              (recur next DOWN len x y lookup)))
        )))))

(defn summedNeighbours [lookup coord]
  ;; get all possible adjacent positions
  (let [ neighbours [ 
    { :x (- (coord :x) 1) :y (coord :y)}
    { :x (+ (coord :x) 1) :y (coord :y)}
    { :x (- (coord :x) 1) :y (+ (coord :y) 1) }
    { :x (coord :x) :y (+ (coord :y) 1) }
    { :x (+ (coord :x) 1) :y (+ (coord :y) 1) }
    { :x (- (coord :x) 1) :y (- (coord :y) 1) }
    { :x (coord :x) :y (- (coord :y) 1) }
    { :x (+ (coord :x) 1) :y (- (coord :y) 1) }]]
    (reduce (fn [sum xy] 
      (let [val (lookup xy)] 
        (if (nil? val) 
          sum 
          (+ sum val))
        )
      ) 0 neighbours)))

(defn sequentialElem [lookup coord digit]
  (assoc lookup coord (+ digit 1)))

(defn summedElem [lookup coord digit]
  (assoc lookup coord (summedNeighbours lookup coord)))

(defn manhattanDist [coord]
   (+ (Math/abs (coord :x)) (Math/abs (coord :y))))

(defn -main [& args]
  (let [target 265149
        resultA (coords (partial = target) (partial sequentialElem))
        resultB (coords (partial < target) (partial summedElem))]
    (println "Manhattan distance to" target "is" (manhattanDist (resultA :key)))
    (println "Next biggest value after" target "is" (resultB :key) "and has value" (get (resultB :map) (resultB :key)))))
