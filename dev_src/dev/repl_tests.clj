(ns dev.repl-tests
  (:require [clojure.string :as str]
            [clojure.math.combinatorics :as combo]
            [contextual-search.core :as core]
            [contextual-search.validate :as validate]
            [clojure.java.io :as io]
            [malli.core :as malli]
            [malli.error :as error]
            [contextual-search.match-query :as match]
            [contextual-search.word-position :as word-position]))



(comment

  (def query {:words-to-find ["d" "c"]
              :conditions    [[{:word-1 "d" :word-2 "c" :max-distance 1}]]})

  (get query :words-to-find)


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



  (let [text (slurp "resources/contextual-search/test-text.txt")]
    (tap> text)
    (->> (str/split text #"\s+")
         (map str/lower-case)
         (map #(re-find #"[\w\-]+" %))))

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



  (validate/all-valid? [[] [true false]])

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
  (validate/pair-of-word-positions condition comb)
  (tap> (validate/single-combination conditions comb))

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


  (def test-text
    "  The invention discloses a    - fiber solar panel some-word car roof. Carbon fiber is taken as a raw material")



  (tap> (core/seq-of-all-words test-text))

  (def string-to-break "invention discloses a carbon fiber solar panel car roof")

  (core/break-string-on-words string-to-break)

  (core/break-string-on-words "")


  (def words-test-1 ["invention" "discloses" "a" "carbon" "fiber" "solar" "panel" "car" "roof"])

  (def query-1 {:words-to-find ["solar"]
                :conditions    []})

  (def query-2 {:words-to-find ["panel" "solar"]
                :conditions    [[{:word-1 "panel" :word-2 "solar" :max-distance 1}]]})

  (def query-3 {:words-to-find ["panel" "solar" "roof"]
                :conditions    [[{:word-1 "panel" :word-2 "solar" :max-distance 1}]
                                [{:word-1 "panel" :word-2 "roof" :max-distance 2}
                                 {:word-1 "panel" :word-2 "roof" :max-distance 2}]]})

  (def query-4 {:words-to-find ["panel" "solar" "roof"]
                :conditions    []})

  (match/single-word-query? query-1 [])


  (def test-text-all
    "The invention discloses a carbon fiber solar panel car roof. Carbon fiber is taken as a raw material to be
    subjected to die machining; one or more required carbon fiber car roof models are formed; then a silicon raw
    material is subjected to chemical vapor deposition or deposited or sprayed on to a prepared carbon fiber model
    base in a physical spraying mode in a reaction chamber, so as to form a crystalline silicon film; finally the
    carbon fiber model base with the crystalline silicon film is put into a crystal oven, so as to be subjected to
    high-temperature melting crystallization; and a series of battery piece manufacture processes are performed,
    so that a required solar panel is obtained, and the finished product of the carbon fiber solar panel car roof
    is obtained")

  (tap> (core/break-string-on-words test-text-all))

  (match/mani-words-query? query-4 test-text-all)



  (tap> (word-position/find-positions {:words         (core/break-string-on-words test-text-all)
                                       :words-to-find ["panel" "solar" "roof"]}))

  (core/run-text {:text test-text-all})


  (def word-position-combinations-test-1 [[["car" 8] ["material" 17] ["silicon" 37]]
                                          [["car" 30] ["material" 17] ["silicon" 37]]
                                          [["car" 30] ["material" 39] ["silicon" 37]]
                                          [["car" 129] ["material" 39] ["silicon" 84]]])


  (def conditions-test-1 [[{:word-1 "material" :word-2 "silicon" :max-distance 3}]
                          [{:word-1 "car" :word-2 "material" :max-distance 9}
                           {:word-1 "car" :word-2 "silicon" :max-distance 9}]])

  (def conditions-test-2 [[{:word-1 "material" :word-2 "silicon" :max-distance 20}]])


  (tap> (validate/combinations [] word-position-combinations-test-1))

  (tap> (validate/single-combination conditions-test-1 [["car" 30] ["material" 39] ["silicon" 37]]))

  (validate/single-combination conditions-test-2 [["car" 30] ["material" 39] ["silicon" 37]])

  (validate/single-combination conditions-test-1 [["car" 30] ["material" 17] ["silicon" 37]])

  (validate/single-combination conditions-test-2 [["car" 30] ["material" 17] ["silicon" 37]])

  (validate/all-valid? [[true] [true true]])

  (validate/all-valid? [[true] [true false]])
  (validate/all-valid? [[false] [true true]])
  (validate/all-valid? [[true] [false false]])

  (some #{9} [9 5])

  (def words-test-2
    ["the" "invention" "discloses" "a" "fiber" "solar" "panel" "some-word" "car"
     "roof" "carbon" "fiber" "is" "taken" "as" "a" "raw" "material"])

  (def words-to-find ["panel" "solar" "roof"])

  (word-position/find-positions {:words words-test-2 :words-to-find ["c" "b" "d"]})

  (def words-test-answer-1
    [["solar" 6] ["panel" 7] ["roof" 31] ["solar" 115] ["panel" 116] ["solar" 127] ["panel" 128] ["roof" 130]])

  (def words-test-answer-2
    [["solar" 6] ["panel" 7] ["roof" 31] ["solar" 115]])



  (word-position/combinations words-test-answer-2)

  {})