(ns day12.core)
(require '[ clojure.string :as str ]
         '[ clojure.java.io :as io ]
         '[ clojure.set :as set ] )

(defn handle-entry [ key group lm ]
  (let [ children (lm key)
         updated-lm (dissoc lm key)
         group (set/union group children) ]
    (reduce (fn [ [lm group] child ]
               (if (contains? lm child)
                  (handle-entry child group lm)
                  [ lm group ])) [updated-lm group] children)
    ))

(defn groups [ input ]
  (let [ lm (into (sorted-map) input) ]
    (loop [ lookup-map lm groups [] ]
      (let [ entry (first lookup-map)
             key (first entry)
             [ remaining-entries group ] (handle-entry key #{ key } lookup-map)
             groups (conj groups group)]
        (if (empty? remaining-entries)
          groups
          (recur remaining-entries groups)))
      )
    ))
        
(defn count-programs-in-set-for-entry [ groups target ]
  (count (first (filter #( contains? % target ) groups))))

(defn parse-input-line
  [line]
  (let [[_ id conns-str] (re-matches #"(\w+) <\-> ([\w,\s]+)" line) ]
    [ (keyword id)
      (set
        (map #(keyword %) 
             (map #(str/trim %)
                  (str/split conns-str #",")
                  ))
        ) ]
    )
  )

(defn parse-input-lines [ lines ]
  (map #(parse-input-line %) lines))

(defn readLinesFromFile [file]
   (with-open [rdr (io/reader file)]
     (doall (line-seq rdr))))

(defn read-input []
  (readLinesFromFile "resources/input.txt"))

(defn parse-input []
  (parse-input-lines (read-input)))

(defn part1 []
  (count-programs-in-set-for-entry (groups (parse-input)) :0))

(defn part2 []
  (count (groups (parse-input))))

(defn -main [& args]
  (println "Part 1" (part1))
  (println "Part 2" (part2))
  )
