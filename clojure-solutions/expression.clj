(defn constant [a]
  (fn [args]
    a))

(defn variable [a]
  (fn [args]
    (args a)))

(defn oneOp [f]
  (fn [a]
    (fn [args]
      (f (a args)))))

(def negate (oneOp -))

(def exp (oneOp
           (fn [a]
             (Math/exp a))))

(def ln (oneOp
           (fn [a]
             (Math/log a))))

(defn binOp [f]
  (fn [a, b]
    (fn [args]
      (f (a args) (b args)))))

(def add (binOp +))

(def subtract (binOp -))

(def multiply (binOp *))

(def divide (binOp
              (fn [a, b]
                (/ a (double b)))))

(def getOp {'+ add, '- subtract, '* multiply, '/ divide, 'negate negate, 'ln ln, 'exp exp})

(defn parse [expr]
  (cond
    (seq? expr) (apply (getOp (first expr)) (map parse (rest expr)))
    (number? expr) (constant expr)
    :else (variable (str expr))))

(defn parseFunction [str]
  (parse (read-string str)))

; new hw

(declare ZERO)

(defn Constant[num]
  {
   :evaluate (fn [args] num)
   :toString (str num)
   :diff (fn [arg] ZERO)
   })

(def ZERO (Constant 0))
(def ONE (Constant 1))

(defn Variable[arg]
  {
   :evaluate (fn [args]  (args arg))
   :toString (str arg)
   :diff (fn [argDiff] (if (= argDiff arg) ONE ZERO))
   })

(defn BinOp [f, op, diffCalc]
  (fn [a, b]
    {
     :evaluate (fn [args] (f ((:evaluate a) args) ((:evaluate b) args)))
     :toString (str "(" op " " (:toString a) " " (:toString b) ")")
     :diff (fn [arg] (diffCalc arg a b))
     }
    ))

(def Add (BinOp + "+"
                (fn [arg, a, b] (Add ((:diff a) arg) ((:diff b) arg))
                  )))

(def Subtract (BinOp - "-"
                     (fn [arg, a, b] (Subtract ((:diff a) arg) ((:diff b) arg))
                       )))

(def Multiply (BinOp * "*"
                     (fn [arg, a, b] (Add
                                       (Multiply ((:diff a) arg) b)
                                       (Multiply a ((:diff b) arg))
                                       ))))

(defn divSup [a, b]
  (/ a (double b)))

(def Divide (BinOp divSup "/"
                   (fn [arg, a, b]
                     (Divide
                       (Subtract
                         (Multiply ((:diff a) arg) b)
                         (Multiply a ((:diff b) arg)))
                       (Multiply b b)
                       ))))

(defn UnarOp [f, op, diffCalc]
  (fn [a]
    {
     :evaluate (fn [args] (f ((:evaluate a) args)))
     :toString (str "(" op " " (:toString a) ")")
     :diff (fn [arg] (diffCalc arg a))
     }))

(def Negate (UnarOp - "negate"
                    (fn [arg, a] (Negate ((:diff a) arg)))
                    ))

(defn expSup [value]
  (Math/exp value))

(def Exp (UnarOp expSup "exp"
                 (fn [arg, a]
                   (Multiply (Exp a) ((:diff a) arg))
                   )))

(defn lgSup [value]
  (Math/log value))

(def Ln (UnarOp lgSup "ln"
                (fn [arg, a] (Multiply
                               (Divide ONE a)
                               ((:diff a) arg)
                               ))))

(defn evaluate [arg, args]
  ((:evaluate arg) args))

(defn toString [arg]
  (:toString arg))

(defn diff [arg, value]
  ((:diff arg) value))

(def getOpObj {'+ Add, '- Subtract, '* Multiply, '/ Divide, 'negate Negate, 'ln Ln, 'exp Exp})

(defn parseObj [expr]
  (cond
    (seq? expr) (apply (getOpObj (first expr)) (map parseObj (rest expr)))
    (number? expr) (Constant expr)
    :else (Variable (str expr))))

(defn parseObject [str]
  (parseObj (read-string str)))