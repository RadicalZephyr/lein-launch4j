(ns leiningen.launch4j.xml
  (:require [clojure.data.xml :as xml]))

(def basic-form
  [:launch4jConfig {}])

(def top-level-elements
  [[:headerType {} ""]
   [:outfile {} ""]
   [:jar {} ""]
   [:dontWrapJar {} ""]
   [:errTitle {} ""]
   [:downloadUrl {} ""]
   [:supportUrl {} ""]
   [:cmdLine {} ""]
   [:chdir {} ""]
   [:priority {} ""]
   [:customProcName {} ""]
   [:stayAlive {} ""]
   [:icon {} ""]
   [:classpath {}
    [:mainClass {} ""]
    [:cp {} ""]]
   [:singleInstance {}
    [:mutexName {} ""]
    [:windowTitle {} ""]]
   [:jre {}
    [:path {} ""]
    [:minVersion {} ""]
    [:maxVersion {} ""]
    [:jdkPreference {} ""]
    [:initialHeapSize {} ""]
    [:initialHeapPercent {} ""]
    [:maxHeapSize {} ""]
    [:maxHeapPercent {} ""]
    [:opt {} ""]]
   [:splash {}
    [:file {} ""]
    [:waitForWindow {} ""]
    [:timeout {} ""]
    [:timeoutErr {} ""]]
   [:versionInfo {}
    [:fileVersion {} ""]
    [:txtFileVersion {} ""]
    [:fileDescription {} ""]
    [:copyright {} ""]
    [:productVersion {} ""]
    [:txtProductVersion {} ""]
    [:productName {} ""]
    [:companyName {} ""]
    [:internalName {} ""]
    [:originalFilename {} ""]]
   [:messages {}
    [:startupErr {} ""]
    [:bundledJreErr {} ""]
    [:jreVersionErr {} ""]
    [:launcherErr {} ""]]])

(def repeatable-elements
  [:obj
   :lib
   :var])

(def default-options
  {:dontWrapJar false
   :customProcName true
   :stayAlive      false
   :headerType "gui"
   :cmdLine ""
   :chdir   ""
   :priority "normal"
   :downloadUrl "http://java.com/download"
   :supportUrl ""
   :manifest   ""
   :icon       "icon.ico"

   :jre {:path ""
         :minVersion "1.6.0_45"
         :maxVersion ""
         :jdkPreference "preferJre"}
   })

(defn both [pred a b]
  (and (pred a)
       (pred b)))

(defn rec-merge [a b]
  (let [elt-merg (fn [a b]
                   (cond (both map? a b) (rec-merge a b)
                         (both vector? a b) (vec (concat a b))
                         :else b))]
    (merge-with rec-merge a b)))

(defn xml-sexp->map [sexp]
  (if (vector? sexp)
    (let [[key dict & children] sexp]
     {key (reduce merge (map xml-sexp->map children))})
    sexp))

(defn map->xml-sexp [options]
  (map (fn [[key value]] (if (map? value)
                           (vec (concat [key {}]
                                        (map->xml value)))
                           [key {} (str value)]))
       (seq options)))

(defn emit-empty-config []
  (xml/emit-str
   (xml/sexp-as-element (vec
                         (concat basic-form
                                 top-level-elements)))))

(defn emit-config [options]
  (xml/emit-str
   (xml/sexp-as-element (vec
                         (concat basic-form
                                 (map->xml-sexp options))))))
