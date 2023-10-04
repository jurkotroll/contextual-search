(ns contextual-search.match-query
  (:require [contextual-search.validate :as validate]
            [contextual-search.word-position :as word-position]))


(defn mani-words-query?
  [{:keys [words-to-find conditions] :as _query} words]
  (some?
    (first
      (validate/combinations
        conditions
        (word-position/combinations
          (word-position/find-positions {:words words :words-to-find words-to-find}))))))


(defn single-word-query?
  [{:keys [words-to-find] :as _query} words]
  (some?
    (some #{(first words-to-find)} words)))


(defn match-query?
  [{:keys [words-to-find] :as query} words]
  (let [one-word-query? (= 1 (count words-to-find))]
    (if one-word-query?
      (single-word-query? query words)
      (mani-words-query? query words))))