(ns contextual-search.validate-test
  (:require [clojure.test :refer [are deftest is testing]]
            [contextual-search.validate :as validate]))


(def word-position-combinations-test-1 [[["car" 8] ["material" 17] ["silicon" 37]]
                                        [["car" 30] ["material" 17] ["silicon" 37]]
                                        [["car" 30] ["material" 39] ["silicon" 37]]
                                        [["car" 129] ["material" 39] ["silicon" 84]]])

(def conditions-test-1 [[{:word-1 "material" :word-2 "silicon" :max-distance 3}]
                        [{:word-1 "car" :word-2 "material" :max-distance 9}
                         {:word-1 "car" :word-2 "silicon" :max-distance 9}]])

(def conditions-test-2 [[{:word-1 "material" :word-2 "silicon" :max-distance 1}]])

(deftest combinations-test
  (is (= [true] (validate/combinations conditions-test-1 word-position-combinations-test-1)))
  (is (empty? (validate/combinations conditions-test-2 word-position-combinations-test-1))))


(deftest single-combination-test
  (is (= [[true] [true true]]
         (validate/single-combination conditions-test-1 [["car" 30] ["material" 39] ["silicon" 37]])))
  (is (= [[false]]
         (validate/single-combination conditions-test-2 [["car" 30] ["material" 39] ["silicon" 37]])))
  (is (= [[false] [false true]]
         (validate/single-combination conditions-test-1 [["car" 30] ["material" 17] ["silicon" 37]])))
  (is (= [[true]]
         (validate/single-combination [[{:word-1 "material" :word-2 "silicon" :max-distance 20}]]
                                      [["car" 30] ["material" 17] ["silicon" 37]]))))

(deftest pair-validation-test
  (is (true? (validate/pair-validation {:max-distance 3}
                                       [["any-data" 39] ["any-data" 37]])))
  (is (true? (validate/pair-validation {:max-distance 3}
                                       [["any-data" 37] ["any-data" 39]])))
  (is (false? (validate/pair-validation {:max-distance 3}
                                        [["any-data" 30] ["any-data" 37]])))
  (is (false? (validate/pair-validation {:max-distance 3}
                                        [["any-data" 39] ["any-data" 30]]))))


(deftest all-valid?-test
  (is (true? (validate/all-valid? [[true] [true true]])))
  (is (true? (validate/all-valid? [[true] [true false]])))
  (is (false? (validate/all-valid? [[false] [true true]])))
  (is (false? (validate/all-valid? [[true] [false false]]))))

