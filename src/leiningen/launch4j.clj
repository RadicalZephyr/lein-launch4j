(ns leiningen.launch4j
  (:require [clojure.data.xml :as xml])
  (:import net.sf.launch4j.Log
           net.sf.launch4j.Builder
           net.sf.launch4j.config.ConfigPersister))

(def basic-form
  [:launch4jConfig {} []])

(def top-level-elements
  [
   [:headerType {} ""]
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
   [:classpath {} [
                   [:mainClass {} []]
                   [:cp {} []]
                   ]]
   [:singleInstance {} [
                        [:mutexName {} []]
                        [:windowTitle {} []]
                        ]]
   [:jre {} [
             [:path {} []]
             [:minVersion {} []]
             [:maxVersion {} []]
             [:jdkPreference {} []]
             [:initialHeapSize {} []]
             [:initialHeapPercent {} []]
             [:maxHeapSize {} []]
             [:maxHeapPercent {} []]
             [:opt {} []]
             ]]
   [:splash {} [
                [:file {} []]
                [:waitForWindow {} []]
                [:timeout {} []]
                [:timeoutErr {} []]
                ]]
   [:versionInfo {} [
                     [:fileVersion {} []]
                     [:txtFileVersion {} []]
                     [:fileDescription {} []]
                     [:copyright {} []]
                     [:productVersion {} []]
                     [:txtProductVersion {} []]
                     [:productName {} []]
                     [:companyName {} []]
                     [:internalName {} []]
                     [:originalFilename {} []]
                     ]]
   [:messages {} [
                  [:startupErr {} []]
                  [:bundledJreErr {} []]
                  [:jreVersionErr {} []]
                  [:launcherErr {} []]
                  ]]

   ])

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
  "I don't do a lot."
  [project & args]
  (read-config
   (validate-filename
    (:launch4j-config-file project)))
  (build-project
   (validate-filename
    (:launch4j-install-dir project))))
