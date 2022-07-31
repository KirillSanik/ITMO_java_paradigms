;; :NOTE: чтобы не передавать каждый раз [a, b] и не писать mapv, можно сделать абстрактную функцию для операций
(defn op [f]
  (fn [a, b] (mapv f a b)))

(def v+ (op +))

(def v- (op -))

(def v* (op *))

(def vd (op /))

(defn scalar [a, b]
  (apply + (v* a b)))

(defn vect [a, b]
  (vector
    (- (* (nth a 1) (nth b 2)) (* (nth a 2) (nth b 1)))
    (- (* (nth a 2) (nth b 0)) (* (nth a 0) (nth b 2)))
    (- (* (nth a 0) (nth b 1)) (* (nth a 1) (nth b 0)))
    ))

(defn v*s [a, b]
  (mapv (fn [elem] (* elem b)) a))

;; :NOTE: аналогичное замечание
(def m+ (op v+))

(def m- (op v-))

(def m* (op v*))

(def md (op vd))

(defn m*s [a, b]
  (mapv (fn [elem] (v*s elem b)) a))

(defn m*v [a, b]
  (mapv (fn [elem] (apply + (v* elem b))) a))

(defn transpose [a]
  (apply mapv vector a))

(defn m*m [a, b]
  (transpose (mapv (fn [elem] (m*v a elem)) (transpose b))))

;; :NOTE: и здесь тоже
(def c+ (op m+))

(def c- (op m-))

(def c* (op m*))

(def cd (op md))
