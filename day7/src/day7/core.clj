(ns day7.core)
(require '[clojure.string :as str])

(defn convert-list-to-map [ input ]
  (apply merge ( map (fn [item] { (item :id) item }) input ) ))

(defn mid-level-nodes [ items ]
  (filter #(not (nil? %)) (flatten (map :children items))))
  
(defn root [ items ] 
  (let [ mids (mid-level-nodes items)]
     (first (filter (fn [item]
               (and (contains? item :children)
                    (not-any? #(= (item :id) %) mids)
               )
             )
      items))
   )
  )

(defn resolve-children [node lookup-map]
  (map #( lookup-map % ) (node :children)))

(defn children [ node-id lookup-map ]
  (let [node (lookup-map node-id)]
    (resolve-children node lookup-map)))

(defn node-weight [node lookup-map]
  ;; weight of node + (sum of weight of all children)
  (reduce +
          (node :weight)
          (if (contains? node :children)
            (map #(node-weight % lookup-map)(resolve-children node lookup-map)))))
  
(defn with-node-weights [ input ]
  (let [ lookup-map (convert-list-to-map input) ]      
   (map #(assoc %1 :node-weight (node-weight %1 lookup-map)) input)))


(defn child-groups [ input node-id ]
  (let [ lookup-map (convert-list-to-map input)
         children (children node-id lookup-map) ]
    (group-by #( % :node-weight ) children)))

(defn find-parent [ input node-id ]
  (first (filter #(some (fn [x] (= node-id x)) (% :children)) input)))

(defn find-unbalanced-child [ input node-id ]
  (println "finding unbalanced child @ " node-id)
  (let [groups (child-groups input node-id)]
    (case (count groups)
      ;; leaf node
      0 node-id
      ;; balanced - they all have the same weight
      1 node-id
      ;; multiple groups - follow the child with a single entry
      (let [ children-to-check (flatten
                                 (map last
                                      (filter #(= 1 (count (last %))) groups)))
             child-to-check (first children-to-check) ]
        (if (nil? child-to-check)
           nil
           (find-unbalanced-child input (child-to-check :id)))
      )
    )
    )
  )

(defn balanced? [ input node-id ]
  (nil? (find-unbalanced-child input node-id)))

;; parse input files
(defn read-input []
  (str/split-lines (slurp "resources/input.txt")))

(defn parse-children [csv]
  (if (nil? csv)
    nil
    (map symbol (map str/trim (str/split csv #",")))))
                     
(defn convert-input [line]
  (if (nil? line)
    nil
    (let [ [ _ id w _ c ] (re-matches #"(\w+) \((\d+)\)( -> ([\w ,]+))?" line) ]  
      { :id (symbol id) :weight (Integer/parseInt w) :children (parse-children c) } )))

(defn load-input []
  (with-node-weights (map convert-input (read-input))))

(defn part2 [ input ]
  (let [ lookup-map (convert-list-to-map input)
         unbalanced-node-id (find-unbalanced-child input ((root input) :id))
         parent-node (find-parent input unbalanced-node-id)
         children (resolve-children parent-node lookup-map)
         target-weight ((first
                        (filter #(not (= unbalanced-node-id ( % :id))) children))
                        :node-weight)
         unbalanced-node (lookup-map unbalanced-node-id)]
    (println "unbalanced node is " unbalanced-node " and target weight is " target-weight)
    (- (unbalanced-node :weight) (- (unbalanced-node :node-weight) target-weight))))
  
(defn -main [& args]
  (let [ input (load-input) ]
    (println "test result is " (root [ { :id :a :weight 1 :children [ :b :c ]} { :id :b :weight 2}    { :id :c :weight 2 :children [ :d ]} { :id :d :weight 4}]))
    (println "part 1 result is " (root input))
    (println "unbalanced child is " (find-unbalanced-child input ((root input) :id)))
    (println "part 2 answer is " (part2 input))
 )
)

