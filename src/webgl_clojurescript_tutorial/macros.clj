(ns webgl-clojurescript-tutorial.macros
  (:require [clojure.java.io :as io]))

(defmacro resource
  [rel-path]
  (slurp (io/resource rel-path)))
