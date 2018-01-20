(ns day25.core)

(defn process [ comp ]
  (let [ n (comp :cursor)
         vs (comp :values) ]
  (case (comp :state)
    :a (if (contains? vs n)
         { :cursor (dec n)
           :values (disj vs n)
           :state :c }
         { :cursor (inc n)
          :values (conj vs n)
           :state :b })
    :b (if (contains? vs n)
         { :cursor (inc n)
           :values vs
           :state :d }
         { :cursor (dec n)
           :values (conj vs n)
           :state :a })
    :c (if (contains? vs n)
         { :cursor (dec n)
           :values (disj vs n)
           :state :e }
         { :cursor (inc n)
           :values (conj vs n)
           :state :a })
    :d (if (contains? vs n)
         { :cursor (inc n)
           :values (disj vs n)
           :state :b }
         { :cursor (inc n)
           :values (conj vs n)
           :state :a })
    :e (if (contains? vs n)
         { :cursor (dec n)
           :values vs
           :state :c }
         { :cursor (dec n)
           :values (conj vs n)
           :state :f })
    :f (if (contains? vs n)
         { :cursor (inc n)
           :values vs
           :state :a }
         { :cursor (inc n)
           :values (conj vs n)
           :state :d })
    )))

(defn run-steps [ comp steps ]
  (if (= steps 0)
    comp
    (recur (process comp) (dec steps))))

(defn -main [& args]
  (println (count ((run-steps { :cursor 0 :values #{} :state :a } 12919244) :values))))
