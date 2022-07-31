let variable = arg => (x, y, z) => {
    switch (arg) {
        case "x":
            return x;
        case "y":
            return y;
        case "z":
            return z;
    }
}

let cnst = value => () => value;