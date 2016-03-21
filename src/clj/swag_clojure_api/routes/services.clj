(ns swag-clojure-api.routes.services
  (:require [ring.util.http-response :refer :all]
            [compojure.api.sweet :refer :all]
            [schema.core :as s]))

(defn double-number [x]
  (* x 2))

(defn greet [name]
  (str "Hello, " name "!"))

(defn fibonacci
  [x]
  (cond
    (= x 1)
       [1]
    (= x 2)
       [1 1]
    :else
    (loop [coll [1 1]
           pos (- x 2)]
      (if (zero? pos)
        coll
        (recur
          (conj coll (+ (last coll) (nth coll (- (count coll) 2))))
          (dec pos))))))

(defn is-palindrome?
   [seq]
   (cond
     (string? seq)
       (= seq (apply str (reverse seq)))
     :else
     (= seq (reverse seq))))

(s/defschema Thingie {:id Long
                      :hot Boolean
                      :tag (s/enum :kikka :kukka)
                      :chief [{:name String
                               :type #{{:id String}}}]})
(defapi service-routes
  {:swagger {:ui "/swagger-ui"
             :spec "/swagger.json"
             :data {:info {:version "1.0.0"
                           :title "Sample API"
                           :description "Sample Services"}}}}
  (context "/api" []
    :tags ["thingie"]

    (POST "/double" []
      :return      Double
      :body-params [x :- Long]
      :summary     "Retorna el doble del número"
      (ok (double-number x)))

    (GET "/greet" []
      :return       String
      :query-params [name :- String]
      :summary      "Retorna un saludo personalizado para un nombre dado"
      (ok (greet name)))

    (GET "/fibonacci" []
      :return [Number]
      :query-params [x :- Long]
      :summary      "Retorna los primeros x números de la serie de fibonacci"
      (ok (fibonacci x)))

    (GET "/palindrome" []
      :return Boolean
      :query-params [word]
      :summary      "Retorna true si una cadena es palíndroma"
      (ok (is-palindrome? word)))

    (GET "/plus" []
      :return       Long
      :query-params [x :- Long, {y :- Long 1}]
      :summary      "x+y with query-parameters. y defaults to 1."
      (ok (+ x y)))

    (POST "/minus" []
      :return      Long
      :body-params [x :- Long, y :- Long]
      :summary     "x-y with body-parameters."
      (ok (- x y)))

    (GET "/times/:x/:y" []
      :return      Long
      :path-params [x :- Long, y :- Long]
      :summary     "x*y with path-parameters"
      (ok (* x y)))

    (POST "/divide" []
      :return      Double
      :form-params [x :- Long, y :- Long]
      :summary     "x/y with form-parameters"
      (ok (/ x y)))

    (GET "/power" []
      :return      Long
      :header-params [x :- Long, y :- Long]
      :summary     "x^y with header-parameters"
      (ok (long (Math/pow x y))))

    (PUT "/echo" []
      :return   [{:hot Boolean}]
      :body     [body [{:hot Boolean}]]
      :summary  "echoes a vector of anonymous hotties"
      (ok body))

    (POST "/echo" []
      :return   (s/maybe Thingie)
      :body     [thingie (s/maybe Thingie)]
      :summary  "echoes a Thingie from json-body"
      (ok thingie))))
