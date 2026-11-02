package ds;

// Min-heap of upcoming trips. The trip with the earliest departure is at the top.
// Departures are Strings in the format yyyy-MM-dd HH:mm, which compare correctly as text.
public class TripHeap {
    private int[] bookingIds;
    private String[] departures;
    private int size;

    public TripHeap() {
        bookingIds = new int[10];
        departures = new String[10];
        size = 0;
    }

    public void insert(int bookingId, String departure) {
        if (size == bookingIds.length) {
            grow();
        }
        bookingIds[size] = bookingId;
        departures[size] = departure;
        siftUp(size);
        size++;
    }

    // Returns the booking ID of the earliest trip and removes it, or -1 if empty.
    public int extractMin() {
        if (size == 0) {
            return -1;
        }
        int top = bookingIds[0];
        size--;
        bookingIds[0] = bookingIds[size];
        departures[0] = departures[size];
        departures[size] = null;
        siftDown(0);
        return top;
    }

    // Returns the booking ID of the earliest trip without removing it, or -1 if empty.
    public int peekBookingId() {
        if (size == 0) {
            return -1;
        }
        return bookingIds[0];
    }

    public String peekDeparture() {
        if (size == 0) {
            return null;
        }
        return departures[0];
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    public void clear() {
        size = 0;
    }

    private void siftUp(int i) {
        while (i > 0) {
            int parent = (i - 1) / 2;
            if (departures[i].compareTo(departures[parent]) >= 0) {
                break;
            }
            swap(i, parent);
            i = parent;
        }
    }

    private void siftDown(int i) {
        while (true) {
            int left = 2 * i + 1;
            int right = 2 * i + 2;
            int smallest = i;
            if (left < size && departures[left].compareTo(departures[smallest]) < 0) {
                smallest = left;
            }
            if (right < size && departures[right].compareTo(departures[smallest]) < 0) {
                smallest = right;
            }
            if (smallest == i) {
                break;
            }
            swap(i, smallest);
            i = smallest;
        }
    }

    private void swap(int a, int b) {
        int tempId = bookingIds[a];
        bookingIds[a] = bookingIds[b];
        bookingIds[b] = tempId;
        String tempDep = departures[a];
        departures[a] = departures[b];
        departures[b] = tempDep;
    }

    private void grow() {
        int[] newIds = new int[bookingIds.length * 2];
        String[] newDeps = new String[departures.length * 2];
        for (int i = 0; i < size; i++) {
            newIds[i] = bookingIds[i];
            newDeps[i] = departures[i];
        }
        bookingIds = newIds;
        departures = newDeps;
    }
}
