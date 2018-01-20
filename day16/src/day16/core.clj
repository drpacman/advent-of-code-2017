
(ns day16.core)
(require '[ clojure.string :as str ])

(defn spin [ steps input ]
  (let [ size (count input)
         split-point (- size (mod steps size))
        [head tail] (split-at split-point input) ]
    (apply str (concat tail head))))

(defn xchg [ posX posY input ]
  (let [ in (into [] input) ]
  (apply str (-> in
                 (assoc posY (get input posX))
                 (assoc posX (get input posY))))))

(defn swap [ charA charB input ]
  (xchg (str/index-of input charA) (str/index-of input charB) input ))

(defn match-entry [ entry ]
  (let [ [ _ cmds argA cmdx xargA xargB cmdp pargA pargB ] (re-matches #"(s)(\d+)|(x)(\d+)/(\d+)|(p)(\w)/(\w)" entry) ]
    (cond
      (not (nil? cmds)) (partial spin (Integer/parseInt argA))
      (not (nil? cmdx)) (partial xchg (Integer/parseInt xargA) (Integer/parseInt xargB))
      (not (nil? cmdp)) (partial swap (get pargA 0) (get pargB 0)))))

(defn read-input []
  (str/split (str/trim (slurp "resources/input.txt")) #","))

(defn apply-instructions [ instrs input ]
  (loop [ instrs instrs result input]
    (if (empty? instrs)
      result
      (recur (rest instrs) ((first instrs) result))
      )))

(defn apply-input [input]
  (let [ instrs (map match-entry input) ]
    (apply-instructions instrs "abcdefghijklmnop")))

(defn find-cycle [input]
  (let [ instrs (map match-entry input) ]
    (loop [ result "abcdefghijklmnop" count 0 ]
    (if (and (= result "abcdefghijklmnop") (> count 0))
      count
      (recur (apply-instructions instrs result) (inc count))))))

(defn apply-multiple [input times]
  (let [ instrs (map match-entry input)
         cycle (find-cycle input)
        n (mod times cycle) ]
    (loop [ result "abcdefghijklmnop" count n ]
      (if (= count 0)
        result
        (recur (apply-instructions instrs result) (dec count))))))

(defn -main [& args]
  (println (apply-input (read-input)))
  (println (find-cycle (read-input)))
  (println (apply-multiple (read-input) 1000000000)))

