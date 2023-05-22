import java.util.*;
public class Polynomial {
    PolynomialNode firstNode;
    PolynomialNode lastNode;

    Polynomial(PolynomialNode fN, PolynomialNode lN) {
        firstNode = fN;
        lastNode = lN;
    }

    public boolean isEmpty() {
        return (this.firstNode == null && this.lastNode == null);
    }

    public void deleteZeros() {
        PolynomialNode currentNode = this.firstNode;
        while (currentNode.coefficient < 0.00001 && currentNode.coefficient > -0.0001 && currentNode.nextPart != null) {
            this.firstNode = currentNode.nextPart;
            currentNode = currentNode.nextPart;
        }
        if (currentNode.coefficient < 0.00001 && currentNode.coefficient > -0.0001) {
            this.firstNode = null;
            this.lastNode = null;
            return;
        }
        while (currentNode.nextPart != null) {
            if (currentNode.nextPart.coefficient < 0.00001 && currentNode.nextPart.coefficient > -0.0001) {
                if (currentNode.nextPart.nextPart == null) {
                    this.lastNode = currentNode;
                    return;
                } else {
                    currentNode.nextPart = currentNode.nextPart.nextPart;
                }
            } else {
                currentNode = currentNode.nextPart;
            }
        }
    }

    public void makePoly() {
        PolynomialNode polyNew = new PolynomialNode(0, 0, null);
        polyNew.makePolyNode();
        this.firstNode = polyNew;
        PolynomialNode temp = polyNew;
        while (temp.nextPart != null) {
            temp = temp.nextPart;
        }
        this.lastNode = temp;
    }

    public void copyPoly(Polynomial toCopy) {
        PolynomialNode newNodes = new PolynomialNode(0, 0, null);
        newNodes.copyPolyNodes(toCopy.firstNode);
        this.firstNode = newNodes;
        PolynomialNode temp = newNodes;
        while (temp.nextPart != null) {
            temp = temp.nextPart;
        }
        this.lastNode = temp;
    }

    public void print() {
        PolynomialNode currentNode = this.firstNode;
        System.out.println("");
        while (currentNode != this.lastNode.nextPart) {
            if (currentNode.degree != 0) System.out.print(currentNode.coefficient + " x^" + currentNode.degree);
            else System.out.print(currentNode.coefficient);
            if (currentNode.nextPart != this.lastNode.nextPart) System.out.print(" + ");
            currentNode = currentNode.nextPart;
        }
    }

    public static Polynomial sum(Polynomial a, Polynomial b) {
        PolynomialNode firstPolynomialNodePart = a.firstNode;
        PolynomialNode firstPolyEnd = a.lastNode.nextPart;
        PolynomialNode secondPolynomialNodePart = b.firstNode;
        PolynomialNode secondPolyEnd = b.lastNode.nextPart;
        double resultCoefficient = 0;
        int resultDegree = 0;
        PolynomialNode resultPolynomialNode = new PolynomialNode(
                resultCoefficient,
                resultDegree,
                null
        );
        PolynomialNode returnPolynomialNode = resultPolynomialNode;
        while (firstPolynomialNodePart != firstPolyEnd || secondPolynomialNodePart != secondPolyEnd) {
            if (
                    secondPolynomialNodePart != secondPolyEnd &&
                            (
                                    firstPolynomialNodePart == firstPolyEnd ||
                                            secondPolynomialNodePart.degree > firstPolynomialNodePart.degree
                            )
            ) {
                resultDegree = secondPolynomialNodePart.degree;
                resultCoefficient = secondPolynomialNodePart.coefficient;
                secondPolynomialNodePart = secondPolynomialNodePart.nextPart;
            } else if (
                    firstPolynomialNodePart != firstPolyEnd &&
                            (
                                    secondPolynomialNodePart == secondPolyEnd ||
                                            firstPolynomialNodePart.degree > secondPolynomialNodePart.degree
                            )
            ) {
                resultDegree = firstPolynomialNodePart.degree;
                resultCoefficient = firstPolynomialNodePart.coefficient;
                firstPolynomialNodePart = firstPolynomialNodePart.nextPart;
            } else {
                resultDegree = firstPolynomialNodePart.degree;
                resultCoefficient =
                        firstPolynomialNodePart.coefficient + secondPolynomialNodePart.coefficient;
                firstPolynomialNodePart = firstPolynomialNodePart.nextPart;
                secondPolynomialNodePart = secondPolynomialNodePart.nextPart;
            }
            resultPolynomialNode.nextPart = new PolynomialNode(
                    resultCoefficient,
                    resultDegree,
                    null
            );
            resultPolynomialNode = resultPolynomialNode.nextPart;
        }
        Polynomial resultPoly = new Polynomial(returnPolynomialNode.nextPart, resultPolynomialNode);
        resultPoly.deleteZeros();
        return resultPoly;
    }

    public static Polynomial subtract(Polynomial a, Polynomial b) {
        PolynomialNode toSubtract = new PolynomialNode(0, 0, null);
        PolynomialNode toSubtractFirst = toSubtract;
        PolynomialNode currentB = b.firstNode;
        while (currentB != b.lastNode.nextPart) {
            toSubtract.setNextPart(new PolynomialNode(-currentB.coefficient, currentB.degree, null));
            toSubtract = toSubtract.nextPart;
            currentB = currentB.nextPart;
        }
        Polynomial negativePoly = new Polynomial(toSubtractFirst.nextPart, toSubtract);
        return sum(a, negativePoly);
    }

    public Polynomial negativeCoeffs() {
        PolynomialNode negatedNode = new PolynomialNode(0, 0, null);
        PolynomialNode currentNode = this.firstNode;
        PolynomialNode firstNegated = negatedNode;
        PolynomialNode lastNegated = negatedNode;
        while (currentNode != this.lastNode.nextPart) {
            negatedNode.setNextPart(new PolynomialNode(-currentNode.coefficient, currentNode.degree, null));
            negatedNode = negatedNode.nextPart;
            currentNode = currentNode.nextPart;
        }
        while (lastNegated.nextPart != null) {
            lastNegated = lastNegated.nextPart;
        }
        Polynomial returnPoly = new Polynomial(firstNegated.nextPart, lastNegated);
        return returnPoly;
    }

    public static Polynomial product(Polynomial a, Polynomial b) {
        PolynomialNode firstPoly = a.firstNode;
        PolynomialNode secondPoly = b.firstNode;
        PolynomialNode resultPoly = new PolynomialNode(0, 0, null);
        PolynomialNode returnPoly = resultPoly;
        while (firstPoly != a.lastNode.nextPart) {
            double currentFirstPolyCoefficient = firstPoly.coefficient;
            int currentFirstPolyDegree = firstPoly.degree;
            while (secondPoly != b.lastNode.nextPart) {
                double currentSecondPolyCoefficient = secondPoly.coefficient;
                int currentSecondPolyDegree = secondPoly.degree;
                double resultCoefficient = currentFirstPolyCoefficient * currentSecondPolyCoefficient;
                int resultDegree = currentFirstPolyDegree + currentSecondPolyDegree;
                resultPoly.setNextPart(new PolynomialNode(resultCoefficient, resultDegree, null));
                resultPoly = resultPoly.nextPart;
                secondPoly = secondPoly.nextPart;
            }
            firstPoly = firstPoly.nextPart;
            secondPoly = b.firstNode;
        }
        PolynomialNode lastNode = returnPoly.nextPart;
        resultPoly = returnPoly.nextPart;
        PolynomialNode resultPolySecond = returnPoly.nextPart;
        while (resultPoly != null) {
            resultPolySecond = resultPoly;
            while (resultPolySecond.nextPart != null) {
                if (resultPolySecond.nextPart.degree == resultPoly.degree && resultPoly != resultPolySecond.nextPart) {
                    resultPoly.coefficient += resultPolySecond.nextPart.coefficient;
                    resultPolySecond.nextPart = resultPolySecond.nextPart.nextPart;
                } else resultPolySecond = resultPolySecond.nextPart;
            }
            resultPoly = resultPoly.nextPart;
            if (resultPoly != null && resultPoly.nextPart == null) lastNode = resultPoly;
        }
        Polynomial returnPolynomial = new Polynomial(returnPoly.nextPart, lastNode);
        returnPolynomial.deleteZeros();
        return returnPolynomial;
    }

    public Polynomial integrate() {
        PolynomialNode resultPolynomialNode = new PolynomialNode(0, 0, null);
        PolynomialNode returnPolynomialNode = resultPolynomialNode;
        PolynomialNode toIntegrate = this.firstNode;
        while (toIntegrate != this.lastNode.nextPart) {
            double currentCoefficient = toIntegrate.coefficient;
            int currentDegree = toIntegrate.degree;
            currentCoefficient /= (currentDegree + 1);
            currentDegree += 1;
            resultPolynomialNode.setNextPart(new PolynomialNode(currentCoefficient, currentDegree, null));
            resultPolynomialNode = resultPolynomialNode.nextPart;
            toIntegrate = toIntegrate.nextPart;
        }
        PolynomialNode temp = returnPolynomialNode.nextPart;
        while (temp.nextPart != null) {
            temp = temp.nextPart;
        }
        Polynomial returnPoly = new Polynomial(returnPolynomialNode.nextPart, temp);
        return returnPoly;
    }

    public Polynomial derivative() {
        PolynomialNode resultPolynomialNode = new PolynomialNode(0, 0, null);
        PolynomialNode returnPolynomialNode = resultPolynomialNode;
        PolynomialNode toDerive = this.firstNode;
        while (toDerive != null && toDerive.degree != 0) {
            double currentCoefficient = toDerive.coefficient;
            int currentDegree = toDerive.degree;
            currentCoefficient *= currentDegree;
            currentDegree -= 1;
            resultPolynomialNode.setNextPart(new PolynomialNode(currentCoefficient, currentDegree, null));
            resultPolynomialNode = resultPolynomialNode.nextPart;
            toDerive = toDerive.nextPart;
        }
        PolynomialNode temp = returnPolynomialNode.nextPart;
        while (temp.nextPart != null) {
            temp = temp.nextPart;
        }
        Polynomial returnPoly = new Polynomial(returnPolynomialNode.nextPart, temp);
        return returnPoly;
    }


    public double leadingCoeff() {
        return this.firstNode.coefficient;
    }

    public double valInX(double x) {
        double valueInX = 0;
        PolynomialNode currentPolyNode = this.firstNode;
        while (currentPolyNode != this.lastNode.nextPart) {
            valueInX += (java.lang.Math.pow(x, currentPolyNode.degree) * currentPolyNode.coefficient);
            currentPolyNode = currentPolyNode.nextPart;
        }
        return valueInX;
    }

    public static boolean diffSignNear(Polynomial poly, double x0, double proximity) {
        double lowerX = x0 - proximity;
        double higherX = x0 + proximity;
        double lowerVal = poly.valInX(lowerX);
        double higherVal = poly.valInX(higherX);
        return (lowerVal * higherVal < 0);
    }

    /*Euclidian division of PolynomialNodes
     * https://en.wikipedia.org/wiki/PolynomialNode_greatest_common_divisor#Euclidean_division
     */
    public static Polynomial[] eucDivision(Polynomial poly1, Polynomial poly2) {
        Polynomial a = new Polynomial(null, null);
        a.copyPoly(poly1);
        Polynomial b = new Polynomial(null, null);
        b.copyPoly(poly2);
        Polynomial r = new Polynomial(null, null);
        r.copyPoly(poly1);
        PolynomialNode qNode = new PolynomialNode(0, 0, null);
        Polynomial q = new Polynomial(qNode, qNode);
        double c = b.leadingCoeff();
        int d = b.firstNode.degree;
        int degR = a.firstNode.degree;
        PolynomialNode sNode = new PolynomialNode(0, 0, null);
        Polynomial s = new Polynomial(sNode, sNode);

        Polynomial sb;
        double lcR;
        while (degR >= d) {
            lcR = r.leadingCoeff();
            s.firstNode.setCoefficient(lcR / c);
            s.firstNode.setDegree(degR - d);
            q = sum(q, s);
            sb = product(s, b);
            r = subtract(r, sb);
            degR = r.firstNode.degree;
        }
        Polynomial[] returnArray = new Polynomial[2];
        q.deleteZeros();
        r.deleteZeros();
        returnArray[0] = q;
        returnArray[1] = r;
        return returnArray;
    }

    /*Finding roots of a polynomial using Sturm's Theorem
     * https://en.wikipedia.org/wiki/Sturm%27s_theorem
     */
    public LinkedList<Polynomial> sturmSequence() {
        LinkedList<Polynomial> returnList = new LinkedList<Polynomial>();
        returnList.add(this);
        Polynomial p2 = this.derivative();
        returnList.add(p2);
        Polynomial secondLast = p2;
        Polynomial px = eucDivision(this, this.derivative())[1].negativeCoeffs();
        returnList.add(px);
        int pxDegree = px.firstNode.degree;
        Polynomial temp;
        while (pxDegree > 1) {
            temp = eucDivision(secondLast, px)[1].negativeCoeffs();
            secondLast = px;
            px = temp;
            returnList.add(px);
            pxDegree = px.firstNode.degree;
        }
        if (pxDegree == 1) {
            temp = eucDivision(secondLast, px)[1].negativeCoeffs();
            secondLast = px;
            px = temp;
            returnList.add(px);
            pxDegree = px.firstNode.degree;
        }
        return returnList;
    }

    public static int sturmSeqSignChanges(LinkedList<Polynomial> sturmSeq, double x) {
        int[] vals = new int[sturmSeq.size()];
        for (int i = 0; i < sturmSeq.size(); i++) {
            double temp = sturmSeq.get(i).valInX(x);
            if (temp > 0) {
                vals[i] = 1;
            } else if (temp < 0) {
                vals[i] = -1;
            } else {
                vals[i] = 0;
            }
        }
        int numOfChanges = 0;
        int lastSign = vals[0];
        for (int i = 1; i < sturmSeq.size(); i++) {
            if (lastSign * vals[i] < 0) {
                lastSign = vals[i];
                numOfChanges++;
            }
        }
        return numOfChanges;
    }

    public static int sturmSeqBetween(LinkedList<Polynomial> sturmSeq, double low, double high) {
        return (sturmSeqSignChanges(sturmSeq, low) - sturmSeqSignChanges(sturmSeq, high));
    }
    public static int sturmSeqAtInfinities(LinkedList<Polynomial> sturmSeq, boolean positiveInf) {
        int[] vals = new int[sturmSeq.size()];
        for (int i = 0; i < sturmSeq.size(); i++) {
            Polynomial investigatedPoly = sturmSeq.get(i);
            int polyLeadDeg = investigatedPoly.firstNode.degree;
            double polyLeadCoeff = investigatedPoly.firstNode.coefficient;
            boolean evenDegAndPositive = (polyLeadDeg % 2 == 0 && polyLeadCoeff > 0);
            boolean oddDegAndPositive = (polyLeadDeg % 2 == 1 && ((polyLeadCoeff > 0) == positiveInf));
            if (evenDegAndPositive || oddDegAndPositive) {
                vals[i] = 1;
            } else {
                vals[i] = -1;
            }
        }
        int numOfChanges = 0;
        int lastSign = vals[0];
        for (int i = 1; i < sturmSeq.size(); i++) {
            if (lastSign * vals[i] < 0) {
                lastSign = vals[i];
                numOfChanges++;
            }
        }
        return numOfChanges;
    }

    public int numOfRoots() {
        LinkedList<Polynomial> thisSturmSeq = this.sturmSequence();
        int signChangesAtNegativeInf = sturmSeqAtInfinities(thisSturmSeq, false);
        int signChangesAtPositiveInf = sturmSeqAtInfinities(thisSturmSeq, true);
        return (signChangesAtNegativeInf - signChangesAtPositiveInf);
    }

    public static double rootFindSingular(LinkedList<Polynomial> sturmSeq, double l, double r) {
        int maxRepetitions = 10000;
        int currentIteration = 0;
        double currentLeft = l;
        double currentRight = r;
        double currentMiddle = (l + r) / 2;
        boolean signChangeNear = diffSignNear(sturmSeq.get(0), currentMiddle, 0.001);

        while(currentIteration < maxRepetitions && !signChangeNear) {
            int numOfRootsLeft = sturmSeqBetween(sturmSeq ,currentLeft, currentMiddle);
            int numOfRootsRight = sturmSeqBetween(sturmSeq ,currentMiddle, currentRight);
//            System.out.println("nalewo: " + numOfRootsLeft + "  na prawo: " + numOfRootsRight);
            if (diffSignNear(sturmSeq.get(0), currentLeft, 0.001)) {
                return currentLeft;
            }
            if (diffSignNear(sturmSeq.get(0), currentRight, 0.001)) {
                return currentRight;
            }


            if (numOfRootsLeft == numOfRootsRight) {
//                System.out.println("Bug w znajdowaniu");
                return currentMiddle;
            } else {
                if (numOfRootsLeft == 1) {
//                    System.out.println(" nalewo");
                    currentRight = currentMiddle;
                } else {
//                    System.out.println(" naprawo");
                    currentLeft = currentMiddle;
                }
            }
            currentMiddle = (currentLeft + currentRight) / 2;
            signChangeNear = diffSignNear(sturmSeq.get(0), currentMiddle, 0.001);
            currentIteration++;
        }
        return currentMiddle;
    }
    public static void rootFindMultiple(
            LinkedList<Polynomial> sturmSeq, double l, double r, LinkedList<Double> resultList
    ) {
        double currentMiddle = (l + r) / 2;
        int numOfRootsLeft = sturmSeqBetween(sturmSeq ,l, currentMiddle);
        int numOfRootsRight = sturmSeqBetween(sturmSeq ,currentMiddle, r);
        if (numOfRootsLeft == 1) {
            resultList.addLast(rootFindSingular(sturmSeq, l, currentMiddle));
        }
        if (numOfRootsLeft > 1) {
            rootFindMultiple(sturmSeq, l, currentMiddle, resultList);
        }
        if (numOfRootsRight == 1) {
            resultList.addLast(rootFindSingular(sturmSeq, currentMiddle, r));
        }
        if (numOfRootsRight > 1) {
            rootFindMultiple(sturmSeq, currentMiddle, r, resultList);
        }
    }
    public LinkedList<Double> findRoots(double start, double range) {
        LinkedList<Polynomial> sturmSeq = this.sturmSequence();
        int numOfRoots = this.numOfRoots();
        if (numOfRoots == 0) {
            System.out.println("Nie ma pierwiastków");
            return null;
        }
        double minX = start - range;
        double maxX = start + range;
        LinkedList<Double> returnList = new LinkedList<Double>();
        rootFindMultiple(sturmSeq, minX, maxX, returnList);
        return returnList;
    }


    public static void main(String[] args) {
        Polynomial testPoly1 = new Polynomial(null, null);
        testPoly1.makePoly();
        //testPoly1.print();

        //System.out.println("wszystkie pierwiastki: " + testPoly1.numOfRoots());
        LinkedList<Double> rootList = testPoly1.findRoots(0, 100);
        if (rootList != null) {
            for (int i = 0; i < rootList.size(); i++) {
                System.out.println(rootList.get(i));
            }
        }
    }
}
