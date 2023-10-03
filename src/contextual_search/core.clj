(ns contextual-search.core
  (:require [contextual-search.match-query :as match]
            [clojure.string :as str])
  (:import (java.io FileNotFoundException)))


(defn seq-of-all-words
  [text]
  (->> (str/split text #"\s+")
       (remove str/blank?)))


(defn clean-string-of-text
  "Gets plain text and cleaning text from non-alphanumeric characters,
  except spaces and hyphen, if it's appears inside a word.
  Return clean text in one string.
  TODO: Should also except some other non-alphanumeric characters which could be part of a word."
  [text]
  (-> text
      (str/replace #"[^A-Za-z0-9 -]| -|- | - " " ")
      str/lower-case
      str/trim))


(defn run-text
  [{:keys [text] :as _opts}]
  (let [clean-string (clean-string-of-text text)
        words (seq-of-all-words clean-string)
        query {}]
    (cond
      (empty? words) {:error "There are no words in given text."}
      (empty? (get query :words-to-find)) {:error "Given query is empty."}
      :else {:result (match/match-query? words query)})))


(defn run-file
  [{:keys [file] :as _opts}]
  (run-text {:text (slurp file)}))


(defn -main
  [& args]
  (let [filename (first args)]
    (try
      (run-file {:file filename})
      (catch FileNotFoundException _e
        (println "File " filename " not found")
        (System/exit 1))
      (catch Exception e
        (println (str "caught exception: " (ex-message e)))
        (System/exit 1)))))
