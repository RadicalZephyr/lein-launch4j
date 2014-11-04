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

(defn copy-file [^ZipInputStream zip-stream filename]
  (with-open [^FileOutputStream out-file (file-out-stream filename)]
    (let [buff-size 4096
          buffer (byte-array buff-size)]
      (loop [len (.read zip-stream buffer)]
        (when (> len 0)
          (.write out-file buffer 0 len)
          (recur (.read zip-stream buffer)))))))

(defn extract-stream [zip-stream to-folder]
  (let [extract-entry (fn [zip-entry]
                        (when (not (is-dir? zip-entry))
                          (let [to-file (io/file to-folder
                                                 (entry-name zip-entry))
                                parent-file (io/file (.getParent to-file))]
                            (.mkdirs parent-file)
                            (copy-file zip-stream to-file))))]
    (->> zip-stream
         entries
         (map extract-entry)
         dorun)))
