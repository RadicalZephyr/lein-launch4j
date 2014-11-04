(ns leiningen.launch4j.zip
  (:require [clojure.java.io :as io])
  (:import java.io.FileOutputStream
           java.util.zip.ZipInputStream))

(defn file-out-stream ^FileOutputStream [filename]
  (FileOutputStream. filename))

(defn zip-stream [io-stream]
  (ZipInputStream. io-stream))

(defn entry-name [zip-entry]
  (.getName zip-entry))

(defn is-dir? [zip-entry]
  (.isDirectory zip-entry))

(defn next-entry [zip-stream]
  (.getNextEntry zip-stream))

(defn entries [zip-stream]
  (take-while #(not (nil? %))
              (repeatedly #(next-entry zip-stream))))

;; This must be called before copy-file
;; (.mkdirs (io/file (.getParent (io/file lein-home (entry-name ze)))))
(defn copy-file [^ZipInputStream zip-stream filename]
  (with-open [^FileOutputStream out-file (file-out-stream filename)]
    (let [buff-size 4096
          buffer (byte-array buff-size)]
      (loop [len (.read zip-stream buffer)]
        (when (> len 0)
          (.write out-file buffer 0 len)
          (recur (.read zip-stream buffer)))))))

(defn extract-stream [zip-stream]

  )
