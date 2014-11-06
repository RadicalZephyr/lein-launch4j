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
  {
   ;; These are possible fields but they MUST be filled in, and they
   ;; are inherently project specific.  Thus no default could make
   ;; sense.  It's better to have launch4j fail because they are
   ;; absent then proceed with nonsenical values.
   ;; -----
   ;; :jar      ""
   ;; :outfile  ""


   ;; These are possible fields that don't need to be present.  So
   ;; don't add them so they won't appear if not specified by the
   ;; user.
   ;; -----
   ;; :errTitle    ""
   ;; :manifest    ""
   ;; :cmdLine     ""
   ;; :supportUrl  ""
   ;; :chdir       ""
   ;; :classpath {
   ;;             :cp         ""
   ;;             :mainClass  ""
   ;;             }
   ;; :singleInstance {
   ;;                  :windowTitle  ""
   ;;                  :mutexName    ""
   ;;                  }
   ;; :splash {
   ;;          :timeoutErr     ""
   ;;          :timeout        ""
   ;;          :waitForWindow  ""
   ;;          :file           ""
   ;;          }
   ;; :messages {
   ;;            :launcherErr    ""
   ;;            :jreVersionErr  ""
   ;;            :bundledJreErr  ""
   ;;            :startupErr     ""
   ;;            }
   ;; :versionInfo {
   ;;               :internalName       ""
   ;;               :copyright          ""
   ;;               :productName        ""
   ;;               :fileVersion        ""
   ;;               :txtFileVersion     ""
   ;;               :productVersion     ""
   ;;               :companyName        ""
   ;;               :txtProductVersion  ""
   ;;               :fileDescription    ""
   ;;               :originalFilename   ""
   ;;               }


   ;; These are the values that have sensible default values that many
   ;; users won't want to specify.

   :dontWrapJar     false
   :customProcName  true
   :stayAlive       false

   :headerType  "gui"
   :icon        ""
   :priority    "normal"
   :downloadUrl "http://java.com/download"

   :jre {
         ;; :path           ""
         ;; :maxVersion     ""

         :minVersion     "1.6.0_45"
         :jdkPreference  "preferJre"
         }
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
                                        (map->xml-sexp value)))
                           [key {} (str value)]))
       (seq options)))

(defn sexp-with-basic-form [sexp]
  (xml/sexp-as-element
   (vec
    (concat basic-form
            sexp))))

(defn emit-sexp-with-basic-form-str [sexp]
  (xml/emit-str (sexp-with-basic-form sexp)))

(defn emit-sexp-with-basic-form [sexp writer]
  (xml/emit (sexp-with-basic-form sexp) writer))


;; Actual user level functions

(defn emit-config-str [options]
  (emit-sexp-with-basic-form-str (map->xml-sexp
                                  (rec-merge default-options
                                             options))))

(defn emit-config [options writer]
  (emit-sexp-with-basic-form (map->xml-sexp
                              (rec-merge default-options
                                         options))
                             writer))
