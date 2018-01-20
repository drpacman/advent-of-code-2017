(ns day9.core)
(require '[clojure.string :as str])

(defn strip-garbage [ string ]
  (loop [ contents string count 0 ]
    (let [ char (first contents) ]
      (case char
        \! (recur (rest (rest contents)) count)
        \> [ (apply str (rest contents)) count ]
        (recur (rest contents) (inc count))))))

(defn parse [ string ]
  (loop [ rem (seq string)
          stack '()
          current-group nil
          level 0 ]
    (if (empty? rem)
      current-group
      (let [ char (first rem) ]
        (case char
          \{ (do
               ;;(println "start of group - current group" current-group)
               (if (nil? current-group)
                 (recur (rest rem)
                        stack
                        { :level 1 :content "" :gc 0 :groups () }
                        1)
                 (recur (rest rem)
                        (conj stack current-group)
                        { :level (inc level) :content "" :gc 0 :groups () }
                        (inc level))))
          \, (do
               ;;(println "skipping ,")
               (recur (rest rem)
                     stack
                     current-group
                     level))
          \< (do
               ( let [ [ rem garbage-count ] (strip-garbage (rest rem)) ]
               (recur rem
                      stack
                      (update current-group :gc #( + garbage-count %))
                      level )))
          \} (do
               ;;(println "end of group")
               (if-let [ group ( first stack ) ]
                 (recur (rest rem)
                        (rest stack)
                        (update group :groups #(conj % current-group))
                        (dec level) )
                 current-group
                 ))
          (do
            ;;(println "normal")
            (recur (rest rem)
                   stack
                   (update current-group :contents #( str % char ))
                   level))
          )))))

(defn score [ group ]
  (if (empty? (group :groups))
    (group :level)
    (apply + (group :level) (map #(score %) (group :groups)))))

(defn score-gc [ group ]
  (if (empty? (group :groups))
    (group :gc)
    (apply + (group :gc) (map #(score-gc %) (group :groups)))))

(defn read-input []
  (slurp "resources/input.txt"))

(defn -main [& args]
  (let [ graph (parse (read-input)) ]
    (println "Score is " (score graph))
    (println "GC score is " (score-gc graph))
  ))
