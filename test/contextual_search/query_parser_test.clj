(ns contextual-search.query-parser-test
  (:require [clojure.test :refer :all]
            [contextual-search.query-parser :as query-parser]))

(deftest parse-test
  (is (= {:words-to-find ["car" "silicon" "material"],
          :conditions    [[{:element-1 "car", :element-2 "silicon", :max-distance 3}
                           {:element-1 "car", :element-2 "material", :max-distance 3}]
                          [{:element-1 "silicon", :element-2 "material", :max-distance 9}]]}
         (query-parser/parse "car W3 (silicon W9 material)")))
  (is (= {:words-to-find ["car"],
          :conditions    [[]]}
         (query-parser/parse "car")))
  (is (= {:words-to-find ["car" "silicon" "material" "roof"],
          :conditions    [[{:element-1 "car", :element-2 "material", :max-distance 4}
                           {:element-1 "car", :element-2 "roof", :max-distance 4}
                           {:element-1 "silicon", :element-2 "material", :max-distance 4}
                           {:element-1 "silicon", :element-2 "roof", :max-distance 4}]
                          [{:element-1 "car", :element-2 "silicon", :max-distance 3}]
                          [{:element-1 "material", :element-2 "roof", :max-distance 9}]]}
         (query-parser/parse "(car W3 silicon) W4 (material W9 roof)")))
  (is (= {:words-to-find ["silicon" "material" "car" "roof"],
          :conditions    [[{:element-1 "silicon", :element-2 "material", :max-distance 9}
                           {:element-1 "silicon", :element-2 "car", :max-distance 9}
                           {:element-1 "silicon", :element-2 "roof", :max-distance 9}]
                          [{:element-1 "material", :element-2 "car", :max-distance 3}
                           {:element-1 "material", :element-2 "roof", :max-distance 3}]
                          [{:element-1 "car", :element-2 "roof", :max-distance 5}]]}
         (query-parser/parse "silicon W9 (material W3 (car W5 roof))"))))
