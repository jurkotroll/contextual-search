(ns contextual-search.match-query
  (:require [contextual-search.validate :as validate]
            [contextual-search.word-positions :as word-positions]))


(defn mani-words-query?
  [{:keys [words-to-find conditions] :as _query} words]
  (some?
    (first
      (validate/combinations
        conditions
        (word-positions/word-position-combinations
          (word-positions/find-word-positions {:words words :words-to-find words-to-find}))))))


(defn single-word-query?
  [{:keys [words-to-find] :as _query} words]
  (some?
    (some #{(first words-to-find)} words)))


(defn match-query?
  [{:keys [words-to-find] :as query} words]
  (let [one-word-query? (= 1 (count words-to-find))]
    (if one-word-query?
      (single-word-query? words query)
      (mani-words-query? words query))))