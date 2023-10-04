(ns contextual-search.word-position
  (:require
    [clojure.math.combinatorics :as combo]))


(defn find-positions
  "Gets vector of words, which needs to be found and vector of all words.
   Finds position of every appearance of word-to-find and combines a tuples
   of word and position - word as string and position as integer '[word position]'."
  [{:keys [words words-to-find]}]
  (let [word-match (set words-to-find)]
    (keep-indexed (fn [position word]
                    (when (word-match word)
                      [word position]))
                  words)))


(defn combinations
  "Gets sequence of tuples [word position] and groups word-positions by word.
  Returns cartesian product of word-positions."
  [words-positions]
  (->> words-positions
       (group-by first)
       (vals)
       (apply combo/cartesian-product)))