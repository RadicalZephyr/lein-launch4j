(ns leiningen.launch4j
  (:require [clojure.data.xml :as xml]
            [clojure.java.io :as io])
  (:import net.sf.launch4j.Log
           net.sf.launch4j.Builder
           net.sf.launch4j.config.ConfigPersister))

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

(defn emit-empty-config []
  (println
   (xml/emit-str
    (xml/sexp-as-element (vec
                          (concat basic-form
                                  top-level-elements))))))

(def repeatable-elements
  [:obj
   :lib
   :var])

(defn read-config [config-file]
  (.. (ConfigPersister/getInstance)
      (load config-file)))

(defn build-project [bin-dir]
  (.. (Builder. (Log/getConsoleLog) bin-dir)
      (build)))

(defn validate-filename [file-name]
  (when file-name
    (let [f (clojure.java.io/file file-name)]
      (if (.exists f)
        (.getCanonicalFile f)
        (do
          (prn (str file-name " does not exist"))
          ((System/exit 0)))))))

(defn launch4j
  "Wrap your leiningen project into a Windows .exe

Add :main to your project.clj to specify the namespace that contains your
-main function."
  [project & args]
  (when (and (:main project)
             (:launch4j project))
    (let [launch4j-opts (:launch4j project)
          target (io/file (:target-path project))
          outfile (io/file target
                           (or (get-in launch4j-opts [])
                               (str (:name project) "-" (:version project) ".exe")))]
      )))
