package ds;

import model.PackageDeal;

/**
 * Insertion sort for package deals. Arrays only, no built-in sorting.
 * Time complexity: O(n^2) worst case, O(n) when already sorted.
 */
public class PackageSorter {

    // ascending = true: cheapest first. ascending = false: most expensive first.
    public static void sortByPrice(PackageDeal[] deals, boolean ascending) {
        for (int i = 1; i < deals.length; i++) {
            PackageDeal key = deals[i];
            int j = i - 1;
            while (j >= 0 && outOfOrder(deals[j].getTotalPrice(), key.getTotalPrice(), ascending)) {
                deals[j + 1] = deals[j];
                j--;
            }
            deals[j + 1] = key;
        }
    }

    // Highest rating first.
    public static void sortByRating(PackageDeal[] deals) {
        for (int i = 1; i < deals.length; i++) {
            PackageDeal key = deals[i];
            int j = i - 1;
            while (j >= 0 && deals[j].getRating() < key.getRating()) {
                deals[j + 1] = deals[j];
                j--;
            }
            deals[j + 1] = key;
        }
    }

    private static boolean outOfOrder(double left, double right, boolean ascending) {
        return ascending ? left > right : left < right;
    }
}
