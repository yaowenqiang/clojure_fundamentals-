
(print "Hello world")
(+ 1 1)
(type 1)
(type true)
(type :a)
(type (keyword "a"))
(type (quote a))
(type 'a')
(type '(1,2,3))
;(type list(1,2,3))
;(type vector(1,2,3))
(nth (vector 1 2 3) 2)
(def x "Hello World")
(let [  x "Hello"]
  (println "hello", x)
  )

(if (empty? x)
  "X is empty"
  "x is not empty"
  )

(if (empty? x)
  nil
  (do
    (println "Ok")
    :ok))

(if-not (empty? x)
  (do
    (println "Ok")
    :ok))


(when-not
  (empty? x)
  (println "Ok")
  :ok)

(case x
  "Goodbye" :goodbye,
  "Hi" :hello,
  :nothing)
(cond
  (= x "Goodby")
  (= (reverse x) "hello")
  :otherwise :nothing
  )

(fn [] "Hello")
(def hello (fn [] "hello"))
#(str "Hello")

(defn hello [] "hello")
(defn hello [name] (str "hello" name))
(hello "myname")

(defn hello "Greets a person named <name> with their <title>" [name title] (str "hello, " title " " name))

(require '[clojure.repl :refer [doc]])
[doc hello]