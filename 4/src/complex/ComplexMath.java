package complex;

public final class ComplexMath {


    private ComplexMath() {}

    // Экспонента e^z
    public static Complex exp(Complex z) {
        double expRe = Math.exp(z.getRe());
        double re = expRe * Math.cos(z.getIm());
        double im = expRe * Math.sin(z.getIm());
        return new Complex(re, im);
    }

    // Синус sin(z)
    public static Complex sin(Complex z) {
        Complex iZ = new Complex(-z.getIm(), z.getRe()); 
        Complex numerator = exp(iZ).subtract(exp(iZ.multiply(-1)));
        return numerator.divide(new Complex(0, 2)); 
    }

    // Косинус cos(z)
    public static Complex cos(Complex z) {
        Complex iZ = new Complex(-z.getIm(), z.getRe());
        Complex numerator = exp(iZ).add(exp(iZ.multiply(-1)));
        return numerator.divide(2);
    }

    // Тангенс tan(z)
    public static Complex tan(Complex z) {
        Complex sinZ = sin(z);
        Complex cosZ = cos(z);
        return sinZ.divide(cosZ);
    }

    // Арктангенс atan(z)
    public static Complex atan(Complex z) {
        Complex i = new Complex(0, 1);
        Complex one = new Complex(1, 0);
        Complex numerator = one.subtract(i.multiply(z));
        Complex denominator = one.add(i.multiply(z));
        Complex quotient = numerator.divide(denominator);
        Complex logPart = ln(quotient);
        return i.multiply(0.5).multiply(logPart).multiply(-1);
    }

    // Гиперболический синус sh(z)
    public static Complex sinh(Complex z) {
        Complex numerator = exp(z).subtract(exp(z.multiply(-1)));
        return numerator.divide(2);
    }

    // Гиперболический косинус ch(z)
    public static Complex cosh(Complex z) {
        Complex numerator = exp(z).add(exp(z.multiply(-1)));
        return numerator.divide(2);
    }

    // Гиперболический тангенс th(z)
    public static Complex tanh(Complex z) {
        Complex sinhZ = sinh(z);
        Complex coshZ = cosh(z);
        return sinhZ.divide(coshZ);
    }

    // Гиперболический котангенс cth(z)
    public static Complex coth(Complex z) {
        Complex sinhZ = sinh(z);
        Complex coshZ = cosh(z);
        return coshZ.divide(sinhZ);
    }

    // Натуральный логарифм комплексного числа ln(z)
    public static Complex ln(Complex z) {
        // ln(z) = ln|z| + i * arg(z)
        double abs = z.abs();
        double arg = z.argument();
        return new Complex(Math.log(abs), arg);
    }
}