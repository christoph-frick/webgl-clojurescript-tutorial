(ns webgl-clojurescript-tutorial.core
  (:require-macros [webgl-clojurescript-tutorial.macros :as macros])
  (:require [thi.ng.geom.gl.core :as gl]
            [thi.ng.geom.gl.webgl.constants :as glc]
            [thi.ng.geom.matrix :as mat]
            [thi.ng.geom.core :as geom]
            [thi.ng.geom.gl.glmesh :as glmesh]
            [thi.ng.geom.gl.shaders :as shaders]
            [thi.ng.geom.gl.camera :as cam]
            [thi.ng.geom.triangle :as tri]
            [thi.ng.geom.sphere :as sph]
            [thi.ng.geom.gl.webgl.animator :as anim]))

(enable-console-print!)

(defonce gl-ctx (gl/gl-context "main"))

(def triangle
  (geom/as-mesh (tri/triangle3 [[1 0 0] [0 0 0] [0 1 0]])
                {:mesh (glmesh/gl-mesh 3)}))

(def shader-spec
  {:vs (macros/resource "shader.vert")
   :fs (macros/resource "shader.frag")
   :uniforms {:view :mat4
              :proj :mat4
              :model :mat4}
   :attribs {:position :vec3}})

(defonce camera (cam/perspective-camera {}))

#_(println camera)

(defn combine-model-shader-camera
  [model shader-spec camera]
  (-> model
      (gl/as-gl-buffer-spec {})
      (assoc :shader (shaders/make-shader-from-spec gl-ctx shader-spec))
      (gl/make-buffers-in-spec gl-ctx glc/static-draw)
      (cam/apply camera)))

#_(println (combine-model-shader-camera triangle shader-spec camera))

(defn spin [t]
  (geom/rotate-z mat/M44 (/ t 5)))

(defn draw-frame [t]
  (doto gl-ctx
    (gl/clear-color-and-depth-buffer 0 0 0 1 1)
    (gl/draw-with-shader (assoc-in (combine-model-shader-camera triangle shader-spec camera)
                                   [:uniforms :model] (spin t)))))

(defonce running
  (anim/animate
    (fn [t]
      (draw-frame t)
      true)))

(defn on-js-reload []
  ;; optionally touch your app-state to force rerendering depending on
  ;; your application
  ;; (swap! app-state update-in [:__figwheel_counter] inc)
)
