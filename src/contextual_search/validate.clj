(ns contextual-search.validate)


(defn all-valid?
  "Gets a sequence of validated conditions, and return true if every condition contains at least one true."
  [validated-conditions]
  (->> validated-conditions
       (map #(some identity %))
       (every? identity)))


(defn- get-word
  [[word _position]]
  word)


(defn pair-validation
  [{:keys [max-distance]} [[_ position-1] [_ position-2]]]
  (let [distance (abs (- position-1 position-2))]
    (<= 1 distance max-distance)))


(defn clause
  "Validate clause of condition on one combination of word-positions."
  [{:keys [word-1 word-2] :as clause} combination]
  (->> combination
       (filter #(#{word-1 word-2} (get-word %)))
       (pair-validation clause)))


(defn single-combination
  [conditions combination]
  (if (> 2 (count combination))
    [[false]]
    (map (fn [condition]
           (map (fn [clause]
                  (clause clause combination))
                condition))
         conditions)))


(defn combinations
  "Gets conditions and combinations of word-position.
                        Validate every combination with all conditions.
                        Returns sequence of booleans."
  [conditions combinations]
  (->> combinations
       (map #(single-combination conditions %))
       (keep all-valid?)
       (filter true?)))