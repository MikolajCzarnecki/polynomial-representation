
import java.util.*;

public class PolynomialNode {
    double coefficient;
    int degree;
    PolynomialNode nextPart;
    PolynomialNode(double coeff, int deg, PolynomialNode nex) {
        coefficient = coeff;
        degree = deg;
        nextPart = nex;
    }

    public void setCoefficient(double coeff) {
        this.coefficient = coeff;
    }

    public void setDegree(int deg) {
        this.degree = deg;
    }

    public void setNextPart(PolynomialNode nex) {
        this.nextPart = nex;
    }

    public void makePolyNode() {
        PolynomialNode currentPolyNode = this;
        boolean endMaking = false;
        Scanner input = new Scanner(System.in);
        System.out.println("Podaj stopien:");
        currentPolyNode.setDegree(input.nextInt());
        System.out.println("Podaj wspolczynnik:");
        currentPolyNode.setCoefficient(input.nextDouble());
        double currentCoefficient;
        int currentDegree;
        int lastDegree = currentPolyNode.degree;
        while (!endMaking) {
            System.out.println("Podaj stopien: (-1 konczy wpisywanie)");
            currentDegree = input.nextInt();
            if (currentDegree >= lastDegree) {
                System.out.println("Oczekujemy współczynnka mniejszego niż poprzedni! \n KONIEC");
                return;
            }
            if (currentDegree < 0) {
                System.out.println("KONIEC");
                endMaking = true;
            }
            else {
                System.out.println("Podaj wspolczynnik:");
                currentCoefficient = input.nextDouble();
                currentPolyNode.setNextPart(new PolynomialNode(currentCoefficient, currentDegree,null));
                currentPolyNode = currentPolyNode.nextPart;
            }
        }
    }

    public void copyPolyNodes(PolynomialNode toCopy) {
        PolynomialNode originalPoly = toCopy;
        PolynomialNode newPoly = this;
        if (originalPoly == null) return;
        newPoly.setCoefficient(originalPoly.coefficient);
        newPoly.setDegree(originalPoly.degree);
        originalPoly = originalPoly.nextPart;
        while (originalPoly != null) {
            newPoly.setNextPart(
                    new PolynomialNode(originalPoly.coefficient, originalPoly.degree, null)
            );
            newPoly = newPoly.nextPart;
            originalPoly = originalPoly.nextPart;
        }
    }

    public void printPolyNode() {
        PolynomialNode currentNode = this;
        System.out.println("");
        while (currentNode != null) {
            if (currentNode.degree != 0) System.out.print(currentNode.coefficient + " x^" + currentNode.degree);
            else System.out.print(currentNode.coefficient);
            if(currentNode.nextPart != null) System.out.print(" + ");
            currentNode = currentNode.nextPart;
        }
    }
}
