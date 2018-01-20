(ns day21.core)
(require '[ clojure.string :as str ])

(defn parse [ input ]
  (str/split input #"/"))

(defn matches-line [ line rule ]
  (or (= line rule)))

(defn rotate [ input ]
  (map #(apply str %) (apply map vector (reverse input))))

(defn flip-lines [ input ]
  (map str/reverse input))

(defn matches-rule [ input rule ]
  (or
   (every? #(apply matches-line %) (map vector input rule))
   (every? #(apply matches-line %) (map vector (rotate input) rule))
   (every? #(apply matches-line %) (map vector (reverse (rotate input)) rule))
   (every? #(apply matches-line %) (map vector (rotate (rotate input)) rule))
   (every? #(apply matches-line %) (map vector (rotate (rotate (rotate input))) rule))
   (every? #(apply matches-line %) (map vector (reverse input) rule))
   (every? #(apply matches-line %) (map vector (flip-lines input) rule))
  ))

(defn find-rule [ square rules ]
  (filter (fn [ entry ]
            (let [ rule (parse (first entry)) ]
              (matches-rule square rule))) rules))

(defn apply-rules [ square rules ]
  (let [ rule-entry (find-rule square rules)]
    ;;(println square "matches" (count rule-entry))
    (if (empty? rule-entry)
      square
      (parse (last (last rule-entry))))))
  
(defn zip [ rows ]
  (apply map vector rows))

(defn zip-rows [ rows size ]
  (zip (map #(partition (count rows)   %) rows)))

(defn split-input-to-vec [ input size ]
  (map #(zip-rows % size) (partition size input)))

(defn generate-grid [ entries ]
  (map #(apply str %) entries))
  
(defn merge-split-rows [ rows ]
  (map generate-grid rows))

(defn split-input
  ([input]
   (if (= (mod (count input ) 2) 0)
     (split-input input 2)
     (if (= (mod (count input) 3) 0)
       (split-input input 3)
       )
     )
   )
  ([ input n ]
   (map  merge-split-rows (split-input-to-vec input n)))
  )

(defn join-inputs [inputs]
  (if (= (count inputs) 1)
    (flatten inputs)
    (flatten
      (map generate-grid
        (map zip inputs)))))

(defn iterate-row-of-squares [ squares rules ]
  (map #(apply-rules % rules) squares))

(defn iterate-input [input rules]
  (join-inputs
    ( map #(iterate-row-of-squares % rules)
      (split-input input))))

(defn iterate-input-times [ input rules times ]
  (println "Round" times)
  (if (= times 0)
    input
    (recur (iterate-input input rules) rules (dec times))))

(defn count-pixels [ input ]
  (reduce (fn [ res val ]
            (+ res (count
                    (filter #(= % \#) val)))) 0 input))

(defn load-rules [ rules ]
  (reduce (fn [ res val ]
            (let [ [_ k v ] (re-matches #"([.#/]+) => ([.#/]+)" val) ]
              (assoc res k v))) {} rules))

(defn read-input []
  (load-rules (str/split (slurp "resources/input.txt") #"\n")))

(defn -main [& args]
  (println (count-pixels
            (iterate-input-times (parse ".#./..#/###") (read-input) 5)))
  (println (count-pixels
            (iterate-input-times (parse ".#./..#/###") (read-input) 18))))
