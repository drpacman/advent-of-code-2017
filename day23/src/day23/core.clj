(ns day23.core)
(require '[ clojure.string :as str ])
(defn get-value [ registers x ]
  (let [ value (re-matches #"-?\d+" x) ]
    (if (nil? value)
      (registers (keyword x))
      (Integer/parseInt value))))

(defn execute-instruction [ instr machine ]
  (let [[ i cmd x y ] (re-matches #"(set|sub|mul|jnz) ([^ ]+) ([^ ]+)" instr)
        registers (machine :registers)
        pc (machine :pc)
        counts (machine :counts)]
    (println instr machine)
    (case cmd
      "set" (assoc machine :registers (assoc registers (keyword x) (get-value registers y)) :pc (inc pc))
      "sub" (assoc machine :registers (assoc registers (keyword x) (- (get-value registers x)
                                                                      (get-value registers y))) :pc (inc pc))
      "mul" (assoc machine :registers (assoc registers (keyword x) (* (get-value registers x)
                                                                       (get-value registers y))) :pc (inc pc) :counts (inc counts))
      "jnz" (if (not= (get-value registers x) 0)
              (update machine :pc (fn [val] (+ val (get-value registers y))))
              (assoc machine :pc (inc pc)))
    )
    ))

(defn execute-instructions
  ([ instrs ]
     (execute-instructions instrs {:registers { :a 0 :b 0 :c 0 :d 0 :e 0 :f 0 :g 0 :h 0 } :pc 0 :counts 0 }))
   ([ instrs machine ]
     (if (>= (machine :pc) (count instrs))
       machine
       (recur instrs (execute-instruction (nth instrs (machine :pc)) machine))))
  )

(defn load-instructions []
  (str/split (slurp "resources/input.txt") #"\n"))

(defn -main [& args]
  (println (execute-instructions (load-instructions))))
