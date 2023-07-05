package raytacer;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class Vector3Test {

    @AfterEach
    void tearDown() {
    }

    @Test
    public void testDotProduct() {
        Vector3 v1 = new Vector3(1, 2, 3);
        Vector3 v2 = new Vector3(4, 5, 6);
        double expectedDotProduct = 32.0;

        double actualDotProduct = v1.dot(v2);

        assertEquals(expectedDotProduct, actualDotProduct, 0.0001);
    }

    @Test
    public void testMultiplyThis() {
        Vector3 v = new Vector3(2, 3, 4);
        double t = 2.5;
        Vector3 expectedVector = new Vector3(5, 7.5, 10);

        Vector3 actualVector = v.multiplyThis(t);

        assertEquals(expectedVector.x, actualVector.x, 0.0001);
        assertEquals(expectedVector.y, actualVector.y, 0.0001);
        assertEquals(expectedVector.z, actualVector.z, 0.0001);
        assertSame(v, actualVector);
    }

    @Test
    public void testMultiply() {
        Vector3 v = new Vector3(2, 3, 4);
        double t = 2.5;
        Vector3 expectedVector = new Vector3(5, 7.5, 10);

        Vector3 actualVector = v.multiply(t);

        assertEquals(expectedVector.x, actualVector.x, 0.0001);
        assertEquals(expectedVector.y, actualVector.y, 0.0001);
        assertEquals(expectedVector.z, actualVector.z, 0.0001);
        assertNotSame(v, actualVector);
    }

    @Test
    public void testNorm() {
        Vector3 v = new Vector3(3, 4, 5);
        double expectedNorm = 7.07106781187;

        double actualNorm = v.norm();

        assertEquals(expectedNorm, actualNorm, 0.0001);
    }

    @Test
    public void testNormSquared() {
        Vector3 v = new Vector3(3, 4, 5);
        double expectedNormSquared = 50.0;

        double actualNormSquared = v.normSquared();

        assertEquals(expectedNormSquared, actualNormSquared, 0.0001);
    }

    @Test
    public void testNormalizeThis() {
        Vector3 v = new Vector3(3, 4, 5);
        Vector3 expectedVector = new Vector3(0.42426406871, 0.56568542494, 0.70710678118);

        Vector3 actualVector = v.normalizeThis();

        assertEquals(expectedVector.x, actualVector.x, 0.0001);
        assertEquals(expectedVector.y, actualVector.y, 0.0001);
        assertEquals(expectedVector.z, actualVector.z, 0.0001);
        assertSame(v, actualVector);
    }

    @Test
    public void testNormalize() {
        Vector3 v = new Vector3(3, 4, 5);
        Vector3 expectedVector = new Vector3(0.42426406871, 0.56568542494, 0.70710678118);

        Vector3 actualVector = v.normalize();

        assertEquals(expectedVector.x, actualVector.x, 0.0001);
        assertEquals(expectedVector.y, actualVector.y, 0.0001);
        assertEquals(expectedVector.z, actualVector.z, 0.0001);
        assertNotSame(v, actualVector);
    }

    @Test
    public void testAddThisVector() {
        Vector3 v1 = new Vector3(1, 2, 3);
        Vector3 v2 = new Vector3(4, 5, 6);
        Vector3 expectedVector = new Vector3(5, 7, 9);

        Vector3 actualVector = v1.addThis(v2);

        assertEquals(expectedVector.x, actualVector.x, 0.0001);
        assertEquals(expectedVector.y, actualVector.y, 0.0001);
        assertEquals(expectedVector.z, actualVector.z, 0.0001);
        assertSame(v1, actualVector);
    }

    @Test
    public void testAddThisScalar() {
        Vector3 v = new Vector3(1, 2, 3);
        double a = 2.5;
        Vector3 expectedVector = new Vector3(3.5, 4.5, 5.5);

        Vector3 actualVector = v.addThis(a);

        assertEquals(expectedVector.x, actualVector.x, 0.0001);
        assertEquals(expectedVector.y, actualVector.y, 0.0001);
        assertEquals(expectedVector.z, actualVector.z, 0.0001);
        assertSame(v, actualVector);
    }

    @Test
    public void testAddVector() {
        Vector3 v1 = new Vector3(1, 2, 3);
        Vector3 v2 = new Vector3(4, 5, 6);
        Vector3 expectedVector = new Vector3(5, 7, 9);

        Vector3 actualVector = v1.add(v2);

        assertEquals(expectedVector.x, actualVector.x, 0.0001);
        assertEquals(expectedVector.y, actualVector.y, 0.0001);
        assertEquals(expectedVector.z, actualVector.z, 0.0001);
        assertNotSame(v1, actualVector);
        assertNotSame(v2, actualVector);
    }

    @Test
    public void testAddScalar() {
        Vector3 v = new Vector3(1, 2, 3);
        double a = 2.5;
        Vector3 expectedVector = new Vector3(3.5, 4.5, 5.5);

        Vector3 actualVector = v.add(a);

        assertEquals(expectedVector.x, actualVector.x, 0.0001);
        assertEquals(expectedVector.y, actualVector.y, 0.0001);
        assertEquals(expectedVector.z, actualVector.z, 0.0001);
        assertNotSame(v, actualVector);
    }

    @Test
    public void testSubtractThis() {
        Vector3 v1 = new Vector3(4, 5, 6);
        Vector3 v2 = new Vector3(1, 2, 3);
        Vector3 expectedVector = new Vector3(3, 3, 3);

        Vector3 actualVector = v1.subtractThis(v2);

        assertEquals(expectedVector.x, actualVector.x, 0.0001);
        assertEquals(expectedVector.y, actualVector.y, 0.0001);
        assertEquals(expectedVector.z, actualVector.z, 0.0001);
        assertSame(v1, actualVector);
    }

    @Test
    void subtract() {
    }

    @Test
    void vectorFromTo() {
    }

    @Test
    void randomPointInUnitSphere() {
    }

    @Test
    void randomUnitVector() {
    }

    @Test
    void crossProduct() {
    }

    @Test
    void sqrtThis() {
    }

    @Test
    void copy() {
    }
}