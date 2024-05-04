package Calculo;
import java.lang.Math;
import java.util.Arrays;
import java.util.List;
import android.util.Log;
public class RLCParalelo {

    public static List<Double> CorrienteSobreAmortiguado( double resistencia, double inductancia, double capacitancia, double corrienteI, double voltajeI ){
        double alpha = 1/(2*resistencia*capacitancia);
        double omega0= 1/Math.sqrt(inductancia*capacitancia);
        double s1 = -alpha + Math.sqrt(Math.pow(alpha,2)-Math.pow(omega0,2));
        double s2 = -alpha - Math.sqrt(Math.pow(alpha,2)-Math.pow(omega0,2));
        /*
        * A1+A2 = voltajeI ==> a1x + b1y = c1
        * s1A1 + s2A2 = (-(corrienteI))/capacitancia ==> a2x + b2y = c2
        * */
        double A1 = 1, A2 = 1;
        double C2 = (-(corrienteI))/capacitancia;
        List<Double> solution = resolverEcuacion(A1,A2, voltajeI, s1, s2, C2);
        if (solution == null) {
            System.out.println("El sistema no tiene solución única.");
            return  null;
        } else {
            System.out.println("Soluciones:");
            System.out.println("x = " + solution.get(0));
            System.out.println("y = " + solution.get(1));
            return  Arrays.asList(solution.get(0),solution.get(1),s1,s2);
        }
    }

    public static List<Double> resolverEcuacion(double a1, double b1, double c1, double a2, double b2, double c2) {
        double determinant = a1 * b2 - a2 * b1;

        if (determinant == 0) {
            return null; // No hay solución única
        } else {
            double x = (c1 * b2 - c2 * b1) / determinant;
            double y = (a1 * c2 - a2 * c1) / determinant;
            return Arrays.asList(x, y);
        }
    }
}
