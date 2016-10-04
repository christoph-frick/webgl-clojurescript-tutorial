(ns webgl-clojurescript-tutorial.core
  (:require [thi.ng.geom.gl.core :as gl]))

(enable-console-print!)

(defonce gl-ctx (gl/gl-context "main"))

(doto gl-ctx
  (gl/clear-color-and-depth-buffer 0 0 0 1 1))

(defn on-js-reload []
  ;; optionally touch your app-state to force rerendering depending on
  ;; your application
  ;; (swap! app-state update-in [:__figwheel_counter] inc)
)
