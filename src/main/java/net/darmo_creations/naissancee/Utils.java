package net.darmo_creations.naissancee;

public final class Utils {
  /**
   * Performs a true modulo operation using the mathematical definition of "a mod b".
   *
   * @param a Value to get the modulo of.
   * @param b The divisor.
   * @return a mod b
   */
  public static int trueModulo(int a, int b) {
    return ((a % b) + b) % b;
  }

  /**
   * Performs a true modulo operation using the mathematical definition of "a mod b".
   *
   * @param a Value to get the modulo of.
   * @param b The divisor.
   * @return a mod b
   */
  public static double trueModulo(double a, double b) {
    return ((a % b) + b) % b;
  }

  private Utils() {
  }
}
