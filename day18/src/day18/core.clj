(ns day18.core)
(require '[clojure.string :as str])

(defn resolve-value [ reg x ]
  (if-let [n (re-matches #"-?\d+" x)]
    (Long/parseLong n)
    (get reg (keyword x) 0)))

(defn update-register [ reg f x y ]
  (assoc reg (keyword x) (f (get reg (keyword x) 0) (resolve-value reg y))))

(defn parse-instruction [ instrs process ]
  (str/split (instrs (process :pc)) #" "))

(defn execute-instruction [ instrs process target ]
  (let [ pc (process :pc)
         reg (process :registers)
         [ instr x y ] (parse-instruction instrs process)
        queue (process :queue) ]
    (case instr
       "set" (merge process { :registers (assoc reg (keyword x) (resolve-value reg y)) :pc (inc pc) })
       "add" (merge process { :registers (update-register reg + x y) :pc (inc pc) })
       "mul" (merge process { :registers (update-register reg * x y) :pc (inc pc) })
       "mod" (merge process { :registers (update-register reg mod x y) :pc (inc pc) })
       "jgz" (if (or (= "1" x) (> (get reg (keyword x) 0) 0))
               (merge process { :registers reg :pc (+ pc (resolve-value reg y)) })
               (merge process { :registers reg :pc (inc pc) }))
       (if (nil? target)
         (case instr
           "snd" (merge process { :queue (atom [ (resolve-value reg x) ])
                                  :pc (inc pc) })
           "rcv" (if (not= (resolve-value reg x) (long 0))
                   process
                   (assoc process :pc (inc pc)))
           )
         (case instr
           "snd" (do 
                   (let [ value (resolve-value reg x) ]
                     (swap! (target :queue) #(conj % value))
                     (merge process { :count (inc (process :count))
                                      :pc (inc pc) })))
           "rcv" (if (empty? (deref (process :queue)))
                   process
                   (let [  received (first @queue)
                           updated-process (merge process { :registers (assoc reg (keyword x) received)
                                                            :pc (inc pc) }) ]
                     (swap! queue #(subvec % 1))
                     updated-process)))
         )
       )
    ))

(defn create-process
  ([id] {
     :id id
     :registers { :p id }
     :pc 0
     :queue (atom [])
     :count 0
    }
  )
  ([] (create-process (long 0))))

(defn is-blocked [ instrs process ]
   (let [[ instr _ _ ] (parse-instruction instrs process)]
     (or
       (and (= instr "rcv") (= 0 (count (deref (process :queue)))))
       (> (process :pc) (count instrs)))
     ))
  
(defn execute-instructions
  ([ instrs A ]
   (loop [ process A ]
     (if (or (>= (process :pc) (count instrs))
             (= "rcv" (first (parse-instruction instrs process))))
       process
       (recur (execute-instruction instrs process nil))))
   )
  ([ instrs A B ]
   (loop [ process-a A process-b B ]
     (if (is-blocked instrs process-a)
       (if (is-blocked instrs process-b)
         (do
           (println "both processes now blocked - terminating")
           [process-a process-b])
         (do
           (recur (execute-instruction instrs process-b process-a) process-a)))
       (recur (execute-instruction instrs process-a process-b) process-b))
   ))
  )

(defn load-input []
  (str/split (str/trim (slurp "resources/input.txt")) #"\n"))

(defn -main [& args]
  (let [ part1 (execute-instructions (load-input) (create-process))
         queue (part1 :queue) ]
    (println "Part 1" (first @queue)))
  (let [ [ process-a process-b ] (execute-instructions (load-input) (create-process 0) (create-process 1)) ]
    (if (= 1 (process-a :id))
      (println "Part 2" (process-a :count))
      (println "Part 2" (process-b :count))))
  )
