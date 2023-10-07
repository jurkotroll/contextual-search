(ns contextual-search.query-parser
  (:require [clojure.tools.reader.edn :as edn]
            [clojure.walk :as walk]))

(defn- string-to-vec
  "Gets string with words and conditions.
   Changes word on strings, distances on long value, conditions on nested vectors."
  [string]
  (walk/postwalk
    (fn [x]
      (cond
        (coll? x) (vec x)
        (symbol? x) (let [x-string (str x)]
                      (if-some [distance (last (re-find #"W(\d+)" x-string))]
                        (parse-long distance)
                        x-string))

        :else (throw (ex-info "Invalid query." {:query "query"
                                                :form  x}))))
    (edn/read-string (str "[" string "]"))))


(defn- vec-to-query
  "Gets vector of conditions and returns query map with words-to-find and vector of conditions."
  [query-vector]
  (let [words-to-find (atom [])
        conditions (atom [])]
    (walk/prewalk
      (fn [x]
        (cond
          (vector? x) (swap! conditions conj
                             (let [[element-1 max-distance element-2] x
                                   words-2 (filter string? (flatten [element-2]))
                                   words-1 (filter string? (flatten [element-1]))]
                               (vec (mapcat (fn [word-1]
                                              (map (fn [word-2]
                                                     {:element-1    word-1
                                                      :element-2    word-2
                                                      :max-distance max-distance})
                                                   words-2))
                                            words-1))))

          (string? x) (swap! words-to-find conj x)
          :else x)
        x)
      query-vector)
    {:words-to-find @words-to-find
     :conditions    @conditions}))


(defn parse
  "Gets string with words and conditions.
  Returns map of words to find in text and vector of conditions"
  [string]
  (vec-to-query (string-to-vec string)))