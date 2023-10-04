(ns contextual-search.word-position-test
  (:require [clojure.test :refer :all]
            [contextual-search.word-position :as word-position]))


(def words-test-1
  ["the" "invention" "discloses" "a" "fiber" "solar" "panel" "some-word" "car"
   "roof" "carbon" "fiber" "is" "taken" "as" "a" "raw" "material"])

(def words-test-answer-1
  [["solar" 5] ["panel" 6] ["roof" 9]])

(def text-answer-1
  ["the" "invention" "discloses" "a" "fiber" "solar" "panel" "some-word" "car"
   "roof" "carbon" "fiber" "is" "taken" "as" "a" "raw" "material"])

(deftest find-positions-test
  (is (= words-test-answer-1 (word-position/find-positions
                               {:words         words-test-1
                                :words-to-find ["panel" "solar" "roof"]})))
  (is (empty? (word-position/find-positions {:words words-test-1 :words-to-find ["c" "b" "d"]}))))


(def word-positions-test-1
  [["solar" 6] ["panel" 7] ["roof" 31] ["solar" 115] ["panel" 116] ["solar" 127] ["panel" 128] ["roof" 130]])

(def word-positions-test-2
  [["solar" 6] ["panel" 7] ["roof" 31]])

(def word-position-combinations-answer-1
  [[["solar" 6] ["panel" 7] ["roof" 31]]
   [["solar" 6] ["panel" 7] ["roof" 130]]
   [["solar" 6] ["panel" 116] ["roof" 31]]
   [["solar" 6] ["panel" 116] ["roof" 130]]
   [["solar" 6] ["panel" 128] ["roof" 31]]
   [["solar" 6] ["panel" 128] ["roof" 130]]
   [["solar" 115] ["panel" 7] ["roof" 31]]
   [["solar" 115] ["panel" 7] ["roof" 130]]
   [["solar" 115] ["panel" 116] ["roof" 31]]
   [["solar" 115] ["panel" 116] ["roof" 130]]
   [["solar" 115] ["panel" 128] ["roof" 31]]
   [["solar" 115] ["panel" 128] ["roof" 130]]
   [["solar" 127] ["panel" 7] ["roof" 31]]
   [["solar" 127] ["panel" 7] ["roof" 130]]
   [["solar" 127] ["panel" 116] ["roof" 31]]
   [["solar" 127] ["panel" 116] ["roof" 130]]
   [["solar" 127] ["panel" 128] ["roof" 31]]
   [["solar" 127] ["panel" 128] ["roof" 130]]])

(deftest combinations-test
  (is (= word-position-combinations-answer-1 (word-position/combinations word-positions-test-1)))
  (is (= [[["solar" 6] ["panel" 7] ["roof" 31]]] (word-position/combinations word-positions-test-2)))
  (is (= [[]] (word-position/combinations []))))