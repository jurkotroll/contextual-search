(ns contextual-search.match-query-test
  (:require [clojure.test :refer [deftest is]]
            [contextual-search.match-query :as match]))


(def words-test-1 ["invention" "discloses" "a" "carbon" "fiber" "solar" "panel" "car" "roof"])

(def words-test-2 ["invention" "discloses" "a" "carbon" "fiber"])

(def words-test-3 ["solar" "invention" "discloses" "a" "carbon" "fiber" "panel" "car" "roof"])

(def query-1 {:words-to-find ["solar"]
              :conditions    []})

(def query-2 {:words-to-find ["panel" "solar"]
              :conditions    [[{:word-1 "panel" :word-2 "solar" :max-distance 1}]]})

(def words-test-4 ["panel" "solar" "car" "roof"])

(def query-4 {:words-to-find ["panel" "solar" "roof"]
              :conditions    [[{:word-1 "panel" :word-2 "solar" :max-distance 1}]
                              [{:word-1 "panel" :word-2 "roof" :max-distance 1}
                               {:word-1 "panel" :word-2 "roof" :max-distance 1}]]})


(deftest single-word-query?-test
  (is (true? (match/single-word-query? query-1 words-test-1)))
  (is (false? (match/single-word-query? query-1 words-test-2)))
  (is (false? (match/single-word-query? query-1 []))))


(deftest mani-words-query?-test
  (is (true? (match/mani-words-query? query-2 words-test-1)))
  (is (false? (match/mani-words-query? query-2 words-test-2)))
  (is (false? (match/mani-words-query? query-2 words-test-3)))
  (is (false? (match/mani-words-query? query-4 words-test-4))))


(deftest match-query?-test
  (is (true? (match/match-query? query-1 words-test-1)))
  (is (false? (match/match-query? query-1 words-test-2)))
  (is (false? (match/match-query? query-1 [])))
  (is (true? (match/match-query? query-2 words-test-1)))
  (is (false? (match/match-query? query-2 words-test-2)))
  (is (false? (match/match-query? query-2 words-test-3))))