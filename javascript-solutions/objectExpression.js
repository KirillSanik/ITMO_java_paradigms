"use strict";

function Op(op, f, diffCalc, ...args) {
    this.op = op
    this.f = f
    this.diffCalc = diffCalc
    this.args = args
}
Op.prototype.evaluate = function (...args) {
    return this.f(...this.args.map(arg => arg.evaluate(...args)))
}
Op.prototype.toString = function () {
    return this.args.reduce((previousValue, currentValue) =>
        previousValue.toString() + ' ' + currentValue.toString()) + ' ' + this.op
}
Op.prototype.prefix = function () {
    return '(' + this.op + ' ' +
        this.args.map(arg => arg.prefix()).reduce((previousValue, currentValue) =>
        previousValue + ' ' + currentValue) + ')'
}
Op.prototype.diff = function (arg) {
    return this.diffCalc(arg, ...this.args)
}

function CreateClass(op, f, diffCalc) {
    function BaseClass(...args) {
        Op.call(this, op, f, diffCalc, ...args)
    }
    BaseClass.prototype = Object.create(Op.prototype)
    return BaseClass
}

// bin ops
const Add = CreateClass('+',
    (a, b) => a + b,
    (arg, ...args) => new Add(args[0].diff(arg), args[1].diff(arg))
)

const  Subtract = CreateClass( '-',
    (a, b) => a - b,
    (arg, ...args) => new Subtract(args[0].diff(arg), args[1].diff(arg))
)

const Multiply = CreateClass( '*',
    (a, b) => a * b,
    (arg, ...args) =>
        new Add(new Multiply(args[0].diff(arg), args[1]),
        new Multiply(args[0], args[1].diff(arg)))
)

const Divide = CreateClass( '/',
    (a, b) => a / b,
    (arg, ...args) =>
        new Divide(new Subtract(new Multiply(args[0].diff(arg), args[1]),
        new Multiply(args[0], args[1].diff(arg))), new Multiply(args[1], args[1]))
)

const Min3 = CreateClass( 'min3',
    (...args) => Math.min(...args),
    (arg, ...args) => new Min3(args.map(argC => argC.diff(arg)))
)

const Max5 = CreateClass( 'max5',
    (...args) => Math.max(...args),
    (arg, ...args) => new Max5(args.map(argC => argC.diff(arg)))
)

// unar ops
const Negate = CreateClass( 'negate',
    (a) => -a,
    (arg, ...args) =>
        new Multiply(new Const(-1), args[0].diff(arg))
)

const Sinh = CreateClass( 'sinh',
    (a) => Math.sinh(a),
    (arg, ...args) =>
        new Multiply(new Cosh(args[0]), args[0].diff(arg))
)

const Cosh = CreateClass( 'cosh',
    (a) => Math.cosh(a),
    (arg, ...args) =>
        new Multiply(new Sinh(args[0]), args[0].diff(arg))
)

// const and variable
const One = new Const(1)
const  Zero = new Const(0)

function Variable(arg) {
    this.arg = arg
}
Variable.prototype.evaluate = function (...args) {
    return args[Variable.ORIGIN[this.arg]]
}
Variable.prototype.toString = function () {
    return this.arg
}
Variable.prototype.diff = function (arg) {
    if (arg === this.arg) {
        return One// :NOTE: unnecessary new objects
    }
    return Zero
}
Variable.prototype.prefix = function () {
    return this.toString()
}
Variable.ORIGIN = {'x' : 0, 'y' : 1, 'z' : 2}

function Const(value) {
    this.value = value
}
Const.prototype.evaluate = function () {
    return this.value
}
Const.prototype.toString = function () {
    return this.value.toString()
}
Const.prototype.diff = function () {
    return Zero
}
Const.prototype.prefix = function () {
    return this.toString()
}

// parser
const opsCnt = {'+' : 2, '-' : 2, '*' : 2, '/' : 2,
    'max5' : 5, 'min3' : 3, 'negate' : 1, 'sinh' : 1, 'cosh' : 1}
const Ops = {'+' : Add, '-' : Subtract, '*' : Multiply, '/' : Divide,
    'max5' : Max5, 'min3' : Min3, 'negate' : Negate, 'sinh' : Sinh, 'cosh' : Cosh}

const parseRealization = function (previousValue, currentValue) {
    if (currentValue in Variable.ORIGIN) {
        previousValue.push(new Variable(currentValue))
    } else if (currentValue in Ops) {
        previousValue.push(new Ops[currentValue](
            ...previousValue.splice(-opsCnt[currentValue])))
    } else {
        previousValue.push(new Const(parseInt(currentValue)))
    }
    return previousValue
}

const parse = function (expr) {
    return expr.split(' ').filter(expr => expr).reduce(parseRealization, [])[0]
}

// :NOTE: human-readable, useful error messages please
function ParseError(message) {
    this.message = message
}
ParseError.prototype = Object.create(Error.prototype)
ParseError.prototype.name = "ParseError"
ParseError.prototype.toString = function () {
    return this.name + this.message
}
ParseError.prototype.constructor = ParseError

let cntBrackets = 0

const strAddSpace = function (str) {
    let resStr = ""
    for (let i = 0; i < str.length; i++) {
        if (str[i] === '(') {
            resStr += ' ' + str[i]
        } else if (str[i] === ')') {
            resStr += str[i] + ' '
        } else {
            resStr += str[i]
        }
    }
    return resStr
}

const parsePrefixRealization = function (previousValue, currentValue) {
    let brackets = 0;
    if (currentValue.length === 1 && currentValue[0] === '(') {
        cntBrackets++
        return previousValue
    }
    if (currentValue.length === 1 && currentValue[0] === ')') {
        cntBrackets--
        return previousValue
    }
    if (currentValue.length > 1 && currentValue[0] === '(') {
        currentValue = currentValue.slice(1)
        cntBrackets++
        brackets++
    }
    if (currentValue.length > 1 && currentValue[currentValue.length - 1] === ')') {
        currentValue = currentValue.slice(0, -1)
        cntBrackets--
        brackets++
    }
    if (currentValue in Variable.ORIGIN) {
        if (brackets === 2) {
            throw new ParseError("this is not function index: " + previousValue.length)
        }
        previousValue.push(new Variable(currentValue))
    } else if (currentValue in Ops) {
        if (previousValue.length < opsCnt[currentValue]) {
            throw new ParseError("wrong count of arguments")
        }
        previousValue.push(new Ops[currentValue](
            ...previousValue.splice(-opsCnt[currentValue]).reverse()))
    } else if (isNaN(parseInt(currentValue)) ||
        parseInt(currentValue).toString() !== currentValue) {
        throw new ParseError("invalid value index: " + previousValue.length)
    }
    else {
        if (brackets === 2) {
            throw new ParseError("this is not function index: " + previousValue.length)
        }
        previousValue.push(new Const(parseInt(currentValue)))
    }
    return previousValue
}

const parsePrefix = function (expr) {
    cntBrackets = 0
    try {
        const result = strAddSpace(expr).split(' ').filter(expr => expr).reverse().reduce(parsePrefixRealization, [])
        if (result.length !== 1) {
            throw new ParseError("wrong count of arguments")
        }
        if (cntBrackets !== 0) {
            throw new ParseError("wrong count of brackets")
        }
        return result[0]
    } catch (e) {
        throw e
    }
}