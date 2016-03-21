(ns swag-clojure-api.env
  (:require [selmer.parser :as parser]
            [clojure.tools.logging :as log]
            [swag-clojure-api.dev-middleware :refer [wrap-dev]]))

(def defaults
  {:init
   (fn []
     (parser/cache-off!)
     (log/info "\n-=[swag-clojure-api started successfully using the development profile]=-"))
   :middleware wrap-dev})
