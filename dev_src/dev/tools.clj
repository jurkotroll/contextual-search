(ns dev.tools
  (:require [portal.api :as p]))


(def p (p/open {:launcher :intellij}))


(add-tap #'p/submit)