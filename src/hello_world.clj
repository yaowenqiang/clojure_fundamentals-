
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