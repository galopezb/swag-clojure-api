(ns user
  (:require [mount.core :as mount]
            swag-clojure-api.core))

(defn start []
  (mount/start-without #'swag-clojure-api.core/repl-server))

(defn stop []
  (mount/stop-except #'swag-clojure-api.core/repl-server))

(defn restart []
  (stop)
  (start))


