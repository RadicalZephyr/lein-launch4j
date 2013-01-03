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
