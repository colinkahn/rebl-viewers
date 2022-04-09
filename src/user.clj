(ns user
  (:require [cognitect.rebl :as rebl]
            [clojure.core.protocols :as p]
            [clojure.test.check.generators :as gen])
  (:import (clojure.test.check.generators Generator)))

(extend-type Generator
  p/Datafiable
  (datafy [gen]
    (doall (gen/sample gen 10))))

(rebl/update-viewers
 {:clojuredocs/var
  {:pred (every-pred map? :rebl.var/name)
   :ctor (fn [x]
           (let [wv (javafx.scene.web.WebView.)
                 sym (:rebl.var/name x)
                 url (str "https://clojuredocs.org/" (namespace sym) "/" (name sym))]
             (.load (.getEngine wv) url)
             wv))}})

(rebl/ui)
