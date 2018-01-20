(ns day24.core)
(require '[ clojure.string :as str ])

(defn bridge-strength [ bridge ]
  (reduce + 0 (flatten bridge)))

(defn parse [ components-as-str ]
  (map (fn [ item ]
         (let [ [ _ a b ] (re-matches #"(\d+)/(\d+)" item) ]
           [ (Integer/parseInt a) (Integer/parseInt b) ])) components-as-str))

(defn find-component-match [ components target ]
  (filter (fn [ entry ] (some #( = % target ) entry)) components))

(defn remove-component [ components component ]
  (let [[ head tail ] (split-with #(not= component %) components)]
    (concat head (rest tail))))

(defn find-other-connection [ conn n ]
  (if (= (first conn) n)
    (last conn)
    (first conn)))

(defn build-bridges
  ([ components ]
    (let [ initial-components (find-component-match components 0) ]
      (reduce concat [] (map #(build-bridges components % 0 [] ) initial-components))))

  ([ components target-component connection bridge ]
    (let [ bridge (conj bridge target-component)
           next-components (remove-component components target-component)
           next-connection (find-other-connection target-component connection)
           next-target-components (find-component-match next-components next-connection) ]
      (if (empty? next-target-components)
        [ bridge ]
        (reduce concat []
                (map #(build-bridges next-components % next-connection bridge) next-target-components))
        )
      ))
  )

(defn find-longest-bridges [ bridges ]
  (last
   (reduce (fn [ [ m bs ] [ len b ] ]
            (if (> len m)
              [len [b]]
              (if (= len m)
                [ m (conj bs b) ]
                [ m bs ]))) [ 0 [] ]
            (map (fn [bridge] [ (count bridge) bridge ]) bridges))))
  
(defn max-bridge-strength [ bridges ]
  (reduce max 0 (map bridge-strength bridges)))

(defn read-input []
  (str/split (slurp "resources/input.txt") #"\n"))

(defn -main [ & args ]
  (let [ bridges (-> (read-input)
                     (parse)
                     (build-bridges)) ]
  (println "Part 1" (max-bridge-strength bridges))
  (println "Part 2" (max-bridge-strength (find-longest-bridges bridges)))))

