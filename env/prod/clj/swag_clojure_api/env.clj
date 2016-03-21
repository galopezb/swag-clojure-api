(ns swag-clojure-api.env
  (:require [clojure.tools.logging :as log]))

(def defaults
  {:init
   (fn []
     (log/info "\n-=[swag-clojure-api started successfully]=-"))
   :middleware identity})
