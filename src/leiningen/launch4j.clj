(ns leiningen.launch4j
  (:import net.sf.launch4j.Log
           net.sf.launch4j.Builder
           net.sf.launch4j.config.ConfigPersister))
"So the point of this plugin might be just to handle generating the
XML for you, and then feeding it to the program with the correct base directory.

Builder can take an optional basedir"

(defn make-xml-file [project])

(defn read-config [config-file]
  (.. (ConfigPersister/getInstance)
      (load config-file)))

(defn build-project [bin-dir]
  (.. (Builder. (Log/getConsoleLog) bin-dir)
      (build)))

(defn get-launch4j-bin-dir [project])

(defn launch4j
  "I don't do a lot."
  [project & args]
  (read-config (make-xml-file project))
  (build-project (get-launch4j-bin-dir project)))