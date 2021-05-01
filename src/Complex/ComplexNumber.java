package Complex;

public class ComplexNumber {
    private double real;
    private double imaginary;

    public ComplexNumber(double real, double imaginary) {
        this.real = real;
        this.imaginary = imaginary;
    }

    public void add(ComplexNumber complex_number) {
        this.real = this.real + complex_number.real;
        this.imaginary = this.imaginary + complex_number.imaginary;
    }

    public double mod() {
        return Math.sqrt(Math.pow(this.real, 2) + Math.pow(this.imaginary, 2));
    }

    public ComplexNumber square() {
        double _real = this.real * this.real - this.imaginary * this.imaginary;
        double _imaginary = 2 * this.real * this.imaginary;
        return new ComplexNumber(_real, _imaginary);
    }

    public double getReal() {
        return real;
    }

    public void setReal(double real) {
        this.real = real;
    }

    public double getImaginary() {
        return imaginary;
    }

    public void setImaginary(double imaginary) {
        this.imaginary = imaginary;
    }
    public static ComplexNumber multiply(ComplexNumber z1, ComplexNumber z2)
    {
        double _real = z1.real*z2.real - z1.imaginary*z2.imaginary;
        double _imaginary = z1.real*z2.imaginary + z1.imaginary*z2.real;
        return new ComplexNumber(_real,_imaginary);
    }public void multiply(ComplexNumber z)
    {
        set(multiply(this,z));
    }
    public void set(ComplexNumber z)
    {
        this.real = z.real;
        this.imaginary = z.imaginary;
    }
    public static ComplexNumber pow(ComplexNumber z, int power)
    {
        ComplexNumber output = new ComplexNumber(z.getReal(),z.getImaginary());
        for(int i = 1; i < power; i++)
        {
            double _real = output.real*z.real - output.imaginary*z.imaginary;
            double _imaginary = output.real*z.imaginary + output.imaginary*z.real;
            output = new ComplexNumber(_real,_imaginary);
        }
        return output;
    }
    public static ComplexNumber parseComplex(String s)
    {
        s = s.replaceAll(" ","");
        ComplexNumber parsed = null;
        if(s.contains(String.valueOf("+")) || (s.contains(String.valueOf("-")) && s.lastIndexOf('-') > 0))
        {
            String re = "";
            String im = "";
            s = s.replaceAll("i","");
            s = s.replaceAll("I","");
            if(s.indexOf('+') > 0)
            {
                re = s.substring(0,s.indexOf('+'));
                im = s.substring(s.indexOf('+')+1,s.length());
                parsed = new ComplexNumber(Double.parseDouble(re),Double.parseDouble(im));
            }
            else if(s.lastIndexOf('-') > 0)
            {
                re = s.substring(0,s.lastIndexOf('-'));
                im = s.substring(s.lastIndexOf('-')+1,s.length());
                parsed = new ComplexNumber(Double.parseDouble(re),-Double.parseDouble(im));
            }
        }
        else
        {
            // Pure imaginary number
            if(s.endsWith("i") || s.endsWith("I"))
            {
                s = s.replaceAll("i","");
                s = s.replaceAll("I","");
                parsed = new ComplexNumber(0, Double.parseDouble(s));
            }
            // Pure real number
            else
            {
                parsed = new ComplexNumber(Double.parseDouble(s),0);
            }
        }
        return parsed;
    }
    public void subtract(ComplexNumber z)
    {
        set(subtract(this,z));
    }
    public static ComplexNumber subtract(ComplexNumber z1, ComplexNumber z2)
    {
        return new ComplexNumber(z1.real - z2.real, z1.imaginary - z2.imaginary);
    }
    public static ComplexNumber divide(ComplexNumber z1, ComplexNumber z2)
    {
        ComplexNumber output = multiply(z1,z2.conjugate());
        double div = Math.pow(z2.mod(),2);
        return new ComplexNumber(output.real/div,output.imaginary/div);
    }
    public ComplexNumber conjugate()
    {
        return new ComplexNumber(this.real,-this.imaginary);
    }
}