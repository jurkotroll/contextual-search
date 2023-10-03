(ns contextual-search.word-positions
  (:require
    [clojure.math.combinatorics :as combo]))


(defn find-word-positions
  "Gets sequence of all words and return sequence of word-positions
  of every appearance of words-to-find."
  [{:keys [words words-to-find]}]
  (let [word-match (set words-to-find)]
    (keep-indexed (fn [position word]
                    (when (word-match word)
                      [word position]))
                  words)))


(defn word-position-combinations
  "Gets vector of words, which needs to be found, and vector of all words.
  Finds position of every appearance of word-to-find and combines a tuples
  of word and position - word as string and position as integer '[word position]'.
  Groups word-positions by word and returns cartesian product of word-positions."
  [words-positions]
  (->> words-positions
       (group-by first)
       (vals)
       (apply combo/cartesian-product)))