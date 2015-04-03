(ns cljs-geom-playground.core
  (:require-macros
   [thi.ng.macromath.core           :as mm])
  (:require
   [thi.ng.common.math.core         :as math
    :refer [PI HALF_PI TWO_PI]]
   [thi.ng.geom.core                :as geom]
   [thi.ng.geom.core.matrix         :as m
    :refer [M44]]
   [thi.ng.geom.core.vector         :as v
    :refer [vec2 vec3]]
   [thi.ng.geom.circle              :as circle]
   [thi.ng.geom.line                :as line]
   [thi.ng.geom.polygon             :as polygon]
   [thi.ng.geom.rect                :as rect]
   [thi.ng.geom.triangle            :as triangle]
   [thi.ng.geom.webgl.animator
    :refer [animate]]
   [thi.ng.geom.webgl.arrays        :as arrays]
   [thi.ng.geom.webgl.buffers       :as buffers]
   [thi.ng.geom.webgl.core          :as gl]
   [thi.ng.geom.webgl.shaders       :as shaders]
   [thi.ng.geom.webgl.shaders.basic :as basic]
   [thi.ng.geom.webgl.utils         :as glu]))

(defn ^:export main []
  (let [gl        (gl/gl-context "main")
        view-rect (gl/get-viewport-rect gl)
        shader1   (->> (basic/make-color-shader-spec {:use-attrib false :3d false})
                       (shaders/make-shader-from-spec gl))
        shader2   (->> (basic/make-color-shader-spec {:use-attrib true  :3d false})
                       (shaders/make-shader-from-spec gl))
        teeth     20
        cog       (-> (polygon/cog 0.5 teeth [0.9 1 1 0.9])
                      (gl/as-webgl-buffer-spec {:normals false :single-color [1 0 0 1]})
                      (buffers/make-attribute-buffers-in-spec gl gl/static-draw)
                      (assoc-in [:uniforms :proj] (gl/ortho view-rect)))]
    (animate
     (fn [[t frame]]
       (gl/set-viewport gl view-rect)
       (gl/clear-color-buffer gl 1 0.93 0.82 1)

       ;; draw blue cog
       (buffers/draw-arrays
        gl (-> cog
               (assoc :shader shader1)
               (update-in [:attribs] dissoc :color)
               (update-in [:uniforms] merge
                          {:model (-> M44
                                      (geom/translate -0.48 0 0)
                                      (geom/rotate t))
                           :color [0 0 1 1]})))

       ;; draw red cog
       (buffers/draw-arrays
        gl (-> cog
               (assoc :shader shader2)
               (assoc-in [:uniforms :model] (-> M44
                                                (geom/translate 0.48 0 0)
                                                (geom/rotate (- (+ t (/ HALF_PI teeth))))))))
       true))))

(main)
