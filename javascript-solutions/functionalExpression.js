// :NOTE: non-strict mode
// :NOTE: 'let' instead of 'const'
"use strict";

const variable = arg => (x, y, z) => {
    switch (arg) {
        case "x":
            return x
        case "y":
            return y
        case "z":
            return z
    }
}

const cnst = value => () => value

const negate = a => (x, y, z) => -a(x, y, z)

const pi = cnst(Math.PI)
const e = cnst(Math.E)

const binOp = f => (a, b) => (x, y, z) => f(a(x, y, z), b(x, y, z))

const add = binOp((a, b) => a + b)

const subtract = binOp((a, b) => a - b)

const multiply = binOp((a, b) => a * b)

const divide = binOp((a, b) => a / b)