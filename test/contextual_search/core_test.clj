(ns contextual-search.core-test
  (:require [clojure.test :refer [deftest is]]
            [contextual-search.core :as core]))


(def text-test
  "  The invention discloses a    - fiber solar panel some-word car roof. Carbon fiber is taken as a raw material")

(def text-answer
  '("the" "invention" "discloses" "a" "fiber" "solar" "panel" "some-word" "car"
     "roof" "carbon" "fiber" "is" "taken" "as" "a" "raw" "material"))

(deftest seq-of-all-words-test
  (is (= text-answer (core/seq-of-all-words text-test)))
  (is (empty? (core/seq-of-all-words ""))))



(def string-to-break-test "invention discloses a carbon fiber solar panel car roof")

(def string-to-break-answer '("invention" "discloses" "a" "carbon" "fiber" "solar" "panel" "car" "roof"))

(deftest break-string-on-words
  (is (= string-to-break-answer (core/break-string-on-words string-to-break-test)))
  (is (empty? (core/break-string-on-words ""))))