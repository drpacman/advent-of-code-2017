(ns day22.core)
(require '[ clojure.string :as str ])

(defn act-part-2[ curr pos dir n ]
  (case (curr pos)
    \#
      (let [ updated (assoc curr pos \F)
           [ x y ] pos ]
       ;; turning right
       (case dir
         "UP" [ updated [ (inc x) y ] "RIGHT" n ]
         "DOWN" [ updated [ (dec x) y ] "LEFT" n ]
         "LEFT" [ updated [ x (inc y) ] "UP" n ]
         "RIGHT" [ updated [ x (dec y) ] "DOWN" n]
         ))
    \W
      (let [ updated (assoc curr pos \#)
            [ x y ] pos
            n (inc n) ]
       ;; carry on in same direction
       (case dir
         "RIGHT" [ updated [ (inc x) y ] "RIGHT" n ]
         "LEFT" [ updated [ (dec x) y ] "LEFT" n ]
         "UP" [ updated [ x (inc y) ] "UP" n ]
         "DOWN" [ updated [ x (dec y) ] "DOWN" n]
         ))
    \F
      (let [ updated (assoc curr pos \.)
           [ x y ] pos ]
       ;; reverse
       (case dir
         "LEFT" [ updated [ (inc x) y ] "RIGHT" n ]
         "RIGHT" [ updated [ (dec x) y ] "LEFT" n ]
         "DOWN" [ updated [ x (inc y) ] "UP" n ]
         "UP" [ updated [ x (dec y) ] "DOWN" n]
         ))
      (let [ updated (assoc curr pos \W)
           [ x y ] pos ]
       ;; turning left
       (case dir
         "DOWN" [ updated [ (inc x) y ] "RIGHT" n ]
         "UP" [ updated [ (dec x) y ] "LEFT" n ]
         "RIGHT" [ updated [ x (inc y) ] "UP" n ]
         "LEFT" [ updated [ x (dec y) ] "DOWN" n ]
         ))
      )
   )

(defn act-part-1 [ curr pos dir n ]
  (case (curr pos)
    \#
      (let [ updated (assoc curr pos \.)
            [ x y ] pos]
       ;; turning right
       (case dir
         "UP" [ updated [ (inc x) y ] "RIGHT" n ]
         "DOWN" [ updated [ (dec x) y ] "LEFT" n ]
         "LEFT" [ updated [ x (inc y) ] "UP" n ]
         "RIGHT" [ updated [ x (dec y) ] "DOWN" n]
         ))
      (let [ updated (assoc curr pos \#)
            [ x y ] pos
            n (inc n) ]
       ;; turning left
       (case dir
         "DOWN" [ updated [ (inc x) y ] "RIGHT" n ]
         "UP" [ updated [ (dec x) y ] "LEFT" n ]
         "RIGHT" [ updated [ x (inc y) ] "UP" n ]
         "LEFT" [ updated [ x (dec y) ] "DOWN" n ]
         ))
      )
   )

(defn run
  ([ act curr times ]
    (run act curr [0 0] "UP" 0 times))
  ([ act curr pos dir n times ]
    (if (= times 0)
      [ curr pos dir n ]
      (let [ [ c p d n ] (act curr pos dir n) ]
        (recur act c p d n (dec times))
       )))
  )

(defn index-row [ row y ]
  (map-indexed (fn [ idx item ] [ [ idx y ] item ]) row))

(defn index-grid [ grid ]
  (map-indexed (fn [idx row] (index-row row idx)) grid))

(defn flatten-grid [ indexed-grid ]
  (reduce (fn [ res [k v] ] (assoc res k v)) {} (reduce concat [] indexed-grid)))

(defn offset-grid [ indexed-grid offset-x offset-y ]
  (reduce (fn [ dict [ [ x y ] val ] ]
            (assoc dict [ (- x offset-x) (- offset-y y) ] val )) {} indexed-grid))

(defn calculate-offset [ n ]
  (/ (- n 1) 2))

(defn parse-input [ grid ]
  (let [ offset-x (calculate-offset (count (first grid)))
         offset-y (calculate-offset (count grid))]
    (-> grid
        (index-grid)
        (flatten-grid)
        (offset-grid ,,, offset-x offset-y))))

(defn read-input [] 
  (parse-input (str/split (slurp "resources/input.txt") #"\n")))

(defn render-grid [ curr width height pos ]
  (for [y (range height)]
    (println (reduce (fn [ res x ]
                       (str res (get curr [ (- x (calculate-offset width))
                                        (- (calculate-offset height) y) ] "."))) "" (range width)))))

(defn -main [& args]
  (let [ [curr pos dir n ] (run act-part-1 (read-input) 10000) ]
    (println "Part 1 had " n " infections"))
  (let [ [curr pos dir n ] (run act-part-2 (read-input) 10000000) ]
    (println "Part 2 had " n " infections"))
  )
    


