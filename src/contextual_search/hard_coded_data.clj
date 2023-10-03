(ns contextual-search.hard-coded-data)


(def query {:words-to-find ["solar"]
            :conditions    []})


#_(def query {:words-to-find ["panel" "solar"]
              :conditions    [[{:word-1 "panel" :word-2 "solar" :max-distance 1}]]})


#_(def query {:words-to-find ["panel" "solar"]
              :conditions    [[{:word-1 "panel" :word-2 "solar" :max-distance 2}]]})


#_(def query {:words-to-find ["panel" "solar" "roof"]
              :conditions    [[{:word-1 "panel" :word-2 "solar" :max-distance 1}]
                              [{:word-1 "panel" :word-2 "roof" :max-distance 2}
                               {:word-1 "panel" :word-2 "roof" :max-distance 2}]]})


#_(def query {:words-to-find ["panel" "solar" "roof"]
              :conditions    [[{:word-1 "panel" :word-2 "solar" :max-distance 1}]
                              [{:word-1 "panel" :word-2 "roof" :max-distance 1}
                               {:word-1 "panel" :word-2 "roof" :max-distance 1}]]})


#_(def query {:words-to-find ["car" "silicon" "material"]
              :conditions    [[{:word-1 "material" :word-2 "silicon" :max-distance 3}]
                              [{:word-1 "car" :word-2 "material" :max-distance 9}
                               {:word-1 "car" :word-2 "silicon" :max-distance 9}]]})


#_(def query {:words-to-find ["film" "silicon" "material"]
              :conditions    [[{:word-1 "material" :word-2 "silicon" :max-distance 2}]
                              [{:word-1 "film" :word-2 "material" :max-distance 2}
                               {:word-1 "film" :word-2 "silicon" :max-distance 2}]]})
