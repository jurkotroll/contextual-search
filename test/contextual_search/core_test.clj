(ns contextual-search.core-test
  (:require [clojure.test :refer [deftest is]]
            [contextual-search.core :as core]))


(def text-test-1
  "  The invention discloses a    - fiber solar panel some-word car roof. Carbon fiber is taken as a raw material")

(def text-answer-1
  ["the" "invention" "discloses" "a" "fiber" "solar" "panel" "some-word" "car"
   "roof" "carbon" "fiber" "is" "taken" "as" "a" "raw" "material"])

(deftest seq-of-all-words-test
  (is (= text-answer-1 (core/seq-of-all-words text-test-1)))
  (is (empty? (core/seq-of-all-words ""))))



(def string-to-break-test "invention discloses a carbon fiber solar panel car roof")

(def string-to-break-answer ["invention" "discloses" "a" "carbon" "fiber" "solar" "panel" "car" "roof"])

(deftest break-string-on-words
  (is (= string-to-break-answer (core/break-string-on-words string-to-break-test)))
  (is (empty? (core/break-string-on-words ""))))

(def test-text-all
  "The invention discloses a carbon fiber solar panel car roof. Carbon fiber is taken as a raw material to be
  subjected to die machining; one or more required carbon fiber car roof models are formed; then a silicon raw
  material is subjected to chemical vapor deposition or deposited or sprayed on to a prepared carbon fiber model
  base in a physical spraying mode in a reaction chamber, so as to form a crystalline silicon film; finally the
  carbon fiber model base with the crystalline silicon film is put into a crystal oven, so as to be subjected to
  high-temperature melting crystallization; and a series of battery piece manufacture processes are performed,
  so that a required solar panel is obtained, and the finished product of the carbon fiber solar panel car roof
  is obtained")

(def query-1 {:words-to-find ["solar"]
              :conditions    []})

(def query-2 {:words-to-find ["panel" "solar"]
              :conditions    [[{:word-1 "panel" :word-2 "solar" :max-distance 1}]]})

(def query-3 {:words-to-find ["panel" "solar"]
              :conditions    [[{:word-1 "panel" :word-2 "solar" :max-distance 2}]]})

(def query-4 {:words-to-find ["panel" "solar" "roof"]
              :conditions    [[{:word-1 "panel" :word-2 "solar" :max-distance 1}]
                              [{:word-1 "panel" :word-2 "roof" :max-distance 2}
                               {:word-1 "panel" :word-2 "roof" :max-distance 2}]]})

(def query-5 {:words-to-find ["panel" "solar" "roof"]
              :conditions    [[{:word-1 "panel" :word-2 "solar" :max-distance 1}]
                              [{:word-1 "panel" :word-2 "roof" :max-distance 1}
                               {:word-1 "panel" :word-2 "roof" :max-distance 1}]]})

(def query-6 {:words-to-find ["car" "silicon" "material"]
              :conditions    [[{:word-1 "material" :word-2 "silicon" :max-distance 3}]
                              [{:word-1 "car" :word-2 "material" :max-distance 9}
                               {:word-1 "car" :word-2 "silicon" :max-distance 9}]]})

(def query-7 {:words-to-find ["film" "silicon" "material"]
              :conditions    [[{:word-1 "material" :word-2 "silicon" :max-distance 2}]
                              [{:word-1 "film" :word-2 "material" :max-distance 2}
                               {:word-1 "film" :word-2 "silicon" :max-distance 2}]]})

(deftest run-text-test
  (is (true? (:result (core/run-text {:text test-text-all :query query-1}))))
  (is (true? (:result (core/run-text {:text test-text-all :query query-2}))))
  (is (true? (:result (core/run-text {:text test-text-all :query query-3}))))
  (is (true? (:result (core/run-text {:text test-text-all :query query-4}))))
  (is (false? (:result (core/run-text {:text test-text-all :query query-5}))))
  (is (true? (:result (core/run-text {:text test-text-all :query query-6}))))
  (is (false? (:result (core/run-text {:text test-text-all :query query-7})))))