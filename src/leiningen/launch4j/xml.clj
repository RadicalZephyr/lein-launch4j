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

(defn map->xml [options]
  (map (fn [[key value]] (if (map? value)
                           (vec (concat [key {}]
                                        (map->xml value)))
                           [key {} (str value)]))
       (seq options)))

(defn emit-empty-config []
  (println
   (xml/emit-str
    (xml/sexp-as-element (vec
                          (concat basic-form
                                  top-level-elements))))))
