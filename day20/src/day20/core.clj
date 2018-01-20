(ns day20.core)
(require '[clojure.string :as str])

(defn update-particles [ particle ]
  (let [ [p v a] particle ]
    (let [ [px py pz] p
           [vx vy vz] v
           [ax ay az] a ]
   [[ (+ px vx ax) (+ py vy ay) (+ pz vz az) ]
    [ (+ vx ax) (+ vy ay) (+ vz az) ]
    [ ax ay az ]])))
  
(defn step [ particles ]
  (map #( update-particles % ) particles)
  )

(defn is-final [ particle ]
  (let [ [p v a] particle ]
    (println "particle " particle)
    (let [ [px py pz] p
           [vx vy vz] v
           [ax ay az] a ]
      (and (or
            (and (>= ax 0)(>= vx 0)(>= px 0))
            (and (<= ax 0)(<= vx 0)(<= px 0))
            )
           (or
            (and (>= ay 0)(>= vy 0)(>= py 0))
            (and (<= ay 0)(<= vy 0)(<= py 0))
            )
           (or
            (and (>= az 0)(>= vz 0)(>= pz 0))
            (and (<= az 0)(<= vz 0)(<= pz 0))
            )
           )
      )))

(defn index-of-closest-particle [ particles ]
  (first (reduce (fn [ [idx m i] particle ]
                   (let [ [ p v a ] particle
                          [ px py pz ] p
                          dist (+ (Math/abs px) (Math/abs py) (Math/abs pz))]
                     (if (< dist m)
                      [i dist (inc i)]
                      [idx m (inc i)]))) [ 0 Integer/MAX_VALUE 0 ] particles)))
  
(defn run-till-all-final [particles]
  (if (every? is-final particles)
    (index-of-closest-particle particles)
    (recur (step particles))
    ))

(defn remove-collisions-until-all-final [particles]
  (if (every? is-final particles)
    (count particles)
    (recur (reduce (fn [ res val ]
                     (do (println "Val:" (first (last val)))
                         (conj res (first (last val))))) []
                   (filter #(= (count (last %)) 1)
                     (group-by first (step particles)))))
    ))

(defn parse-line [ line ]
  (let [ [ _ px py pz vx vy vz ax ay az ] (re-matches #"p=<(\-?\d+),(\-?\d+),(\-?\d+)>, v=<(\-?\d+),(\-?\d+),(\-?\d+)>, a=<(\-?\d+),(\-?\d+),(\-?\d+)>" line) ]
    [[(Integer/parseInt px) (Integer/parseInt py) (Integer/parseInt pz)]
     [(Integer/parseInt vx) (Integer/parseInt vy) (Integer/parseInt vz)]
     [(Integer/parseInt ax) (Integer/parseInt ay) (Integer/parseInt az)] ]))

(defn read-input []
  (map parse-line (str/split (slurp "resources/input.txt") #"\n")))

(defn -main [& args]
  ;;(println (run-till-all-final (read-input)))
  (println (remove-collisions-until-all-final (read-input))))
           
