package complex;


public class Main {
    public static void main(String[] args) {
        Complex z1 = new Complex(1, 1); 
        Complex z2 = new Complex(0, 1);
        System.out.println("z1 = " + z1);
        System.out.println("z2 = " + z2);
        System.out.println("z1 + z2 = " + z1.add(z2));
        System.out.println("z1 * z2 = " + z1.multiply(z2));
        System.out.println("Сопряженное к z1: " + z1.conjugate());
        System.out.println("z1 в триг. форме: " + z1.toTrigonometricForm());

        System.out.println("---");

        System.out.println("e^(i*pi) = " + ComplexMath.exp(new Complex(0, Math.PI))); 
        System.out.println("sin(i) = " + ComplexMath.sin(z2));
        System.out.println("cos(1+i) = " + ComplexMath.cos(z1));
        System.out.println("atan(1) = " + ComplexMath.atan(new Complex(1)));

        Complex result = ComplexMath.sinh(new Complex(2, 3));
        System.out.println("sh(2+3i) = " + result);
    }
}
