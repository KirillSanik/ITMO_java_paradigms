let variable = arg => (x, y, z) => {
    switch (arg) {
        case "x":
            return x
        case "y":
            return y
        case "z":
            return z
    }
}

let cnst = value => () => value

let negate = a => (x, y, z) => -a(x, y, z);

let pi = cnst(Math.PI)
let e = cnst(Math.E)

let binOp = f => (a, b) => (x, y, z) => f(a(x, y, z), b(x, y, z))

let calcAdd = (a, b) => a + b
let add = binOp(calcAdd)

let calcSubtract = (a, b) => a - b
let subtract = binOp(calcSubtract)

let calcMultiply = (a, b) => a * b
let multiply = binOp(calcMultiply)

let calcDivide = (a, b) => a / b
let divide = binOp(calcDivide)

let x = variable("x");
let expr = add(
    subtract(
        multiply(x, x),
        multiply(cnst(2), x)),
    cnst(1));

for (let i = 0; i < 10; i++) {
    console.log(expr(i, 0, 0));
}