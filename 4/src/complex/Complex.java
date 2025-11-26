package complex;

public class Complex {
    private final double re; 
    private final double im; 


    public Complex(double re, double im) {
        this.re = re;
        this.im = im;
    }


    public Complex(double re) {
        this(re, 0.0);
    }


    public Complex() {
        this(0.0, 0.0);
    }


    public double getRe() {
        return re;
    }

    public double getIm() {
        return im;
    }


    public Complex add(Complex other) {
        return new Complex(this.re + other.re, this.im + other.im);
    }

    public Complex add(double real) {
        return new Complex(this.re + real, this.im);
    }


    public Complex subtract(Complex other) {
        return new Complex(this.re - other.re, this.im - other.im);
    }

    public Complex subtract(double real) {
        return new Complex(this.re - real, this.im);
    }


    public Complex multiply(Complex other) {
        double resultRe = this.re * other.re - this.im * other.im;
        double resultIm = this.re * other.im + this.im * other.re;
        return new Complex(resultRe, resultIm);
    }

    public Complex multiply(double real) {
        return new Complex(this.re * real, this.im * real);
    }


    public Complex divide(Complex other) {
        double denominator = other.re * other.re + other.im * other.im;
        if (denominator == 0) {
            throw new ArithmeticException("Division by zero (complex number)");
        }
        double resultRe = (this.re * other.re + this.im * other.im) / denominator;
        double resultIm = (this.im * other.re - this.re * other.im) / denominator;
        return new Complex(resultRe, resultIm);
    }

    public Complex divide(double real) {
        if (real == 0) {
            throw new ArithmeticException("Division by zero");
        }
        return new Complex(this.re / real, this.im / real);
    }


    public Complex conjugate() {
        return new Complex(this.re, -this.im);
    }


    public double abs() {
        return Math.sqrt(re * re + im * im);
    }


    public double argument() {
        return Math.atan2(im, re);
    }


    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Complex complex = (Complex) obj;

        return Double.compare(complex.re, re) == 0 &&Double.compare(complex.im, im) == 0;
    }

    @Override
    public int hashCode() {
        return java.util.Objects.hash(re, im);
    }



    // Алгебраическая форма
    @Override
    public String toString() {
        if (im == 0) return String.format("%.2f", re);
        if (re == 0) return String.format("%.2fi", im);
        return String.format("%.2f %s %.2fi", re, (im < 0 ? "-" : "+"), Math.abs(im));
    }

    // Тригонометрическая форма
    public String toTrigonometricForm() {
        double r = this.abs();
        double phi = this.argument();
        return String.format("%.2f * (cos(%.2f) + i*sin(%.2f))", r, phi, phi);
    }
}