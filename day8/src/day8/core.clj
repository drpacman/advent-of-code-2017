(ns day8.core)
(require '[clojure.string :as str]
         '[clojure.java.io :as io])

(def cond-map {
               ">" (partial >)
               ">=" (partial >=)
               "==" (partial =)
               "!=" (partial not=)
               "<" (partial <)
               "<=" (partial <=) } )
(defn parse
  [instr]
   (let [[_ r d v cond-r cond-pred cond-v] (re-matches #"(\w+) (inc|dec) (-?\d+) if (\w+) ([!>=<]+) (-?\d+)" instr)
        value (Integer/parseInt v)
        cond-value (Integer/parseInt cond-v) ]
     { :register ( keyword r )
    :change (if (= d "inc") value (- value))
    :cond (fn [registers] ( (cond-map cond-pred) (registers (keyword cond-r) 0) cond-value))
   }))

(defn update-registers [ registers instr ]
  (if ((instr :cond) registers)
    (if (contains? registers (instr :register))
      (update registers (instr :register) (partial + (instr :change)))
      (assoc registers (instr :register) (instr :change)) )
    registers)
  )

(defn update-registers-track-max [ result instr ]
  (let [ r (update-registers (result :r) instr)
          m (max
              (result :max)
              (or (r (instr :register)) 0))]
    { :r r :max m }))

(defn apply-instructions-track-max [instrs]
  (reduce update-registers-track-max { :r {} :max 0 } instrs)) 

(defn apply-instructions [instrs]
  (reduce update-registers {} instrs)) 

(defn read-input []
  (str/split-lines (slurp "resources/input.txt")))

(defn -main [ & args ]
  (let [ input (read-input)
        instrs (map parse input)
        result (apply-instructions instrs) ]
    (println "result is " (apply max (map val result)))
    (println "max is " ((apply-instructions-track-max instrs) :max)))
  )
