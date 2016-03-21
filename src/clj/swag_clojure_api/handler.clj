(ns swag-clojure-api.handler
  (:require [compojure.core :refer [routes wrap-routes]]
            [swag-clojure-api.layout :refer [error-page]]
            [swag-clojure-api.routes.home :refer [home-routes]]
            [swag-clojure-api.routes.services :refer [service-routes]]
            [compojure.route :as route]
            [swag-clojure-api.middleware :as middleware]))

(def app-routes
  (routes
    #'service-routes
    (wrap-routes #'home-routes middleware/wrap-csrf)
    (route/not-found
      (:body
        (error-page {:status 404
                     :title "page not found"})))))

(def app (middleware/wrap-base #'app-routes))
