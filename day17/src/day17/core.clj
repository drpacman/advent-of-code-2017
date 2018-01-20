(ns day17.core)

(defn spin [ step times ]
  (loop [ start 0 chain [0] n 1 ]
    (if (> n times)
      chain
      (let [ pos (inc (mod (+ start step) (count chain)))
           [ head tail ] (split-at pos chain) ]
        (recur pos (concat head [ n ] tail) (inc n))))
    )
  )

(defn second-item [ step times ]
  (loop [ start 0 n 1 item 1 ]
    (if (> n times)
      item
      (let [ pos (inc (mod (+ start step) n)) ]
        (if (= pos 1)
          (recur pos (inc n) n)
          (recur pos (inc n) item))
        ))))

(defn next-item [ step times target ]
  (nth (drop-while #(not= target %) (spin step times)) 1))

(defn -main [& args]
  (println "Part 1" (next-item 312 2017 2017))
  (println "Part 2" (second-item 312 50000000))
  )
    
