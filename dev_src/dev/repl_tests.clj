(ns dev.repl-tests
  (:require [clojure.string :as str]
            [clojure.math.combinatorics :as combo]
            [contextual-search.core :as core]
            [contextual-search.validate :as v]
            [clojure.java.io :as io]
            [clojure.edn :as edn]
            [contextual-search.helpers :as helper]
            [contextual-search.schema :as s]
            [malli.core :as malli]
            [malli.error :as error]))



(comment

  (def query {:words-to-find ["d" "c"]
              :conditions    [[{:word-1 "d" :word-2 "c" :max-distance 1}]]})

  (get query :words-to-find)

  (let [word-positions (core/find-word-positions {:words ["a" "b" "b" "c"] :words-to-find (get query :words-to-find)})
        combinations (core/word-position-combinations word-positions)
        one-comb (first combinations)
        conditions (get query :conditions)
        clause {:word-1 "d" :word-2 "c" :max-distance 1}
        par-val (core/pair-validation clause [["c" 3] ["b" 5]])

        v-comb (map #(core/validate-combination conditions %) combinations)]

    v-comb)


  (- 3 nil)



  (core/find-word-positions {:words ["a" "b" "b" "c"] :words-to-find (get query :words-to-find)})

  (core/match-single-word-query? ["a" "b" "b" "c"] {:words-to-find ["d"]})

  (core/match-mani-words-query? ["a" "b" "b" "c"] query)


  (core/run-text {:text ""})

  (some? [])




  (let [clean-string ""]
    (if (malli/validate s/non-empty-string clean-string)
      (core/seq-of-all-words clean-string)
      (-> s/non-empty-string
          (malli/explain clean-string)
          (error/humanize))))


  (malli/explain
    (malli/from-ast {:type       :string
                     :properties {:min 1}}) "")



  (core/run {:file "s/resources/contextual-search/test-text-empty.txt"})

  (let [text (core/get-text-from-file "resources/contextual-search/test-text.txt")
        string-of-words (core/clean-string-of-text text)
        #_#_queries (helper/load-edn "queries.edn")
        #_#_query (get queries :query-1)
        words-to-find ["solar" "o"]
        word-positions (core/find-word-positions words-to-find string-of-words)
        combinations (core/word-position-combinations words-to-find word-positions)
        conditions [[{:word-1 "solar" :word-2 "panel" :max-distance 1}]]
        v-comb (core/validated-combinations conditions combinations)]

    (tap> v-comb)
    (tap> [:cond conditions])
    (tap> [:comb combinations])


    #_(tap> queries)
    #_(tap> query))

  (.exists (clojure.java.io/file "../../resources/contextual-search/queries.edn"))


  (let [text (slurp "resources/contextual-search/test-text.txt")]
    (tap> text)
    (->> (str/split text #"\s+")
         (map str/lower-case)
         (map #(re-find #"[\w\-]+" %))))





  (core/find-word-positions ["test" "solar"] (core/vector-of-words "test"))

  (io/resource "contextual-search/test-text.txt")

  (core/run {:file "test-text.txt"})

  (core/match-query?
    (slurp (io/resource "contextual-search/test-text.txt"))
    {:words-to-find ["solar"]
     :conditions    [[{:word-1 "solar"}]]})

  (def word-position-combinations-test-1 '((["car" 30] ["material" 39] ["silicon" 37])
                                           (["car" 230] ["material" 239] ["silicon" 237])
                                           (["car" 30] ["material" 39] ["silicon" 84])
                                           (["car" 129] ["material" 17] ["silicon" 37])))


  (def conditions-test-1 [[{:word-1 "material" :word-2 "silicon" :max-distance 3}]
                          [{:word-1 "car" :word-2 "material" :max-distance 9}
                           {:word-1 "car" :word-2 "silicon" :max-distance 9}]])

  (core/validated-combinations conditions-test-1 word-position-combinations-test-1)

  ;(def query1 ["car" "W9" ["material" "W3" "silicon"]])
  ;(def query2 [["material" "W3" "silicon"] "W9" "car"])
  ;(def query3 ["car" "W9" ["material" "W3" "silicon"] "W4" ["material" "W3" "silicon"]])

  (defn parse-query
    [query]
    (let [[element-one distance element-two] query
          element-one (if (string? element-one)
                        element-one
                        (parse-query element-one))
          element-two (if (string? element-two)
                        element-two
                        (parse-query element-two))
          distance (parse-long (re-find #"\d+" distance))]
      [element-one distance element-two]))

  ;(parse-query-1 query1)
  ;(parse-query-1 query2)

  (Integer/parseInt (re-find #"\d+" "W9"))
  (Integer/parseInt (re-find #"\d+" "W99"))
  (Long/parseLong (re-find #"\d+" "W99"))
  (parse-long (re-find #"\d+" "W9"))

  ["car" 9 ["material" 3 "silicon"]]



  (v/all-valid? [[] [true false]])

  (def conditions [[{:word-1 "material" :word-2 "silicon" :max-distance 3}]
                   [{:word-1 "car" :word-2 "material" :max-distance 9}
                    {:word-1 "car" :word-2 "silicon" :max-distance 9}]])

  "clause"

  '(:d 3 (:c 2 (:b 1 :a)))

  [[[:a :b 1]]
   [[:c :q 2] [:c :b 2]]
   [[:d :a 3] [:d :a 3] [:d :c 3]]]

  [{:word-1       "material"
    :word-2       "silicon"
    :max-distance 3}]

  (def condition ["material" "silicon" 3])
  (def comb '(["car" 30] ["material" 39] ["silicon" 37]))
  (v/pair-of-word-positions condition comb)
  (tap> (v/single-combination conditions comb))

  (some? (first [1]))





  "cross product cartesian product"

  (for [x [1 2 3]
        y [4 5 6]
        z [7 8 9]]
    [x y z])

  '([["material" 17] ["silicon" 19]] [["material" 27] ["silicon" 29]])

  [["material" "silicon" 3]
   (or ["car" "material" 9] ["car" "silicon" 9])]

  [
   ["material" "silicon" 3]
   ["car" "silicon" 9]]

  (def test-string "car 1 2 3 4 5 6 7 material silicon")

  "car 1 2 3 4 5 6 7 material 9 silicon"

  (def test-word-positions '(["car" 8] ["material" 17] ["car" 30] ["silicon" 37] ["material" 39] ["silicon" 73] ["silicon" 84] ["car" 129]))

  (let [groups (group-by first test-word-positions)
        variants (for [word-1 (get groups "car")
                       word-2 (get groups "material")
                       word-3 (get groups "silicon")]
                   [word-1 word-2 word-3])]
    (tap> variants))


  (#{"a" "b" "c"} "x")


  (some #(= "x" %) ["x " "b" "c"])

  (def query {:word-1   "silicon"
              :word-2   "material"
              :distance 3})

  (or "silicon" "material")

  (slurp "src/test-text.txt")
  (tap> (-> (slurp "src/test-text.txt")
            (str/replace #"[^A-Za-z0-9 -]| -|- | - " " ")
            str/lower-case
            str/triml
            (str/split #"\s+")))

  (str/replace (slurp "src/test-text.txt") #"[^A-Za-z0-9 ]" " ")
  (str/replace "Test-text.txt -" #"[^A-Za-z0-9 -]|( - )|( -)" " ")

  ["car" "W9" ["silicon" "W3" "material"]]

  [{:element-1    "car"
    :max-distance "W9"
    :element-2    []}]


  (def conditions [[{:word-1 "material" :word-2 "silicon" :max-distance 3}]
                   [{:word-1 "car" :word-2 "material" :max-distance 9}
                    {:word-1 "car" :word-2 "silicon" :max-distance 9}]])




  ;1 -> string and 2 -> vec ==> condition []









  {})