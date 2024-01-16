(ns day6.core)

(defn prep [bank]
  ( let [n (apply max bank)
         i (.indexOf bank n)]
  { :blocks n
    :next #(mod (inc %) (count bank))
    :idx i
    :preped-bank (assoc bank i 0)
  }))


(defn allocate [bank]
  ( let [ v (prep bank) 
          next (v :next)
          start (next (v :idx))]
    (loop [idx start blocks (v :blocks) bank (v :preped-bank)]
      (if (= blocks 0)
        bank
        (recur (next idx) (dec blocks) (assoc bank idx (inc (bank idx))))
      )
    )
  )
)

(defn count-to-repetition [bank]
  (loop [ steps 0 state bank states [] ]
    ;(println steps state (count states))
    (if (some #{ state } states )
       { 
         :steps steps 
         :loop-length (- (count states) (.indexOf states state))
       }
       (recur (inc steps) (allocate state) (conj states state) )
    )
  )
)

(defn -main [& args]
   (println ((count-to-repetition [ 0 2 7 2 ]) :steps))
   (let [ result (count-to-repetition [ 11 11 13 7 0 15 5 5 4 4 1 1 7 1 15 11 ]) ]
      (println "Repetition after " (result :steps) "steps and distance between loops is " (result :loop-length))))
   
