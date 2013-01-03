(ns leiningen.launch4j
  (:import net.sf.launch4j.Log
           net.sf.launch4j.Builder
           net.sf.launch4j.config.ConfigPersister))

(defn read-config [config-file]
  (.. (ConfigPersister/getInstance)
      (load config-file)))

(defn build-project [bin-dir]
  (.. (Builder. (Log/getConsoleLog) bin-dir)
      (build)))

(defn launch4j
  "I don't do a lot."
  [project & args]
  (read-config (:launch4j-config project))
  (build-project (:launch4j-install project)))