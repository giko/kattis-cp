package doctorkattis;

import java.io.*;
import java.util.*;

class DoctorKattis {
    public static void main(String[] args) throws IOException {
        FastIO io = new FastIO();

        Clinic dk = new Clinic();
        int N = io.nextInt();
        for (int i = 0; i < N; i++) {
            switch (io.next()) {
                case "0": {
                    dk.arriveAtClinic(io.next(), io.nextInt());
                    break;
                }
                case "1": {
                    dk.updateInfectionLevel(io.next(), io.nextInt());
                    break;
                }
                case "2": {
                    dk.treated(io.next());
                    break;
                }
                case "3": {
                    io.println(dk.query());
                    break;
                }
            }
        }
        io.close();
    }
}

class Clinic {
    private int patientsCounter = 0;
    final TreeSet<Patient> patients = new TreeSet<>(Collections.reverseOrder());
    final Map<String, Patient> namePatientMap = new HashMap<>(200000, 0.9F);
    final Map<String, Integer> updates = new HashMap<>();

    void arriveAtClinic(String name, int level) {
        Patient p = new Patient(name, level, patientsCounter);
        patients.add(p);
        namePatientMap.put(name, p);
        ++patientsCounter;
    }

    void updateInfectionLevel(String name, int delta) {
        updates.merge(name, delta, Integer::sum);
    }

    private void applyUpdates() {
        Iterator<Map.Entry<String, Integer>> ei = updates.entrySet().iterator();
        while (ei.hasNext()) {
            Map.Entry<String, Integer> e = ei.next();
            Patient patient = namePatientMap.get(e.getKey());
            patients.remove(patient);
            patient.updateLevel(e.getValue());
            patients.add(patient);
            ei.remove();
        }
    }

    void treated(String name) {
        Patient patient = namePatientMap.get(name);
        patients.remove(patient);
        namePatientMap.remove(name);
        updates.remove(name);
    }

    String query() {
        if (patients.isEmpty()) {
            return "The clinic is empty";
        }
        applyUpdates();
        return patients.first().name;
    }
}

class Patient implements Comparable<Patient> {
    final String name;
    int level;
    final int order;

    public Patient(String name, int level, int order) {
        this.name = name;
        this.level = level;
        this.order = order;
    }

    public void updateLevel(int delta) {
        this.level += delta;
    }

    @Override
    public int compareTo(Patient o) {
        int lvlResult = Integer.compare(this.level, o.level);
        if (lvlResult == 0) {
            return Integer.compare(o.order, this.order);
        }
        return lvlResult;
    }
}

class FastIO extends PrintWriter {
    private InputStream stream;
    private byte[] buf = new byte[1 << 16];
    private int curChar;
    private int numChars;

    // standard input
    public FastIO() {
        this(System.in, System.out);
    }

    public FastIO(InputStream i, OutputStream o) {
        super(o);
        stream = i;
    }

    // file input
    public FastIO(String i, String o) throws IOException {
        super(new FileWriter(o));
        stream = new FileInputStream(i);
    }

    // throws InputMismatchException() if previously detected end of file
    private int nextByte() {
        if (numChars == -1) {
            throw new InputMismatchException();
        }
        if (curChar >= numChars) {
            curChar = 0;
            try {
                numChars = stream.read(buf);
            } catch (IOException e) {
                throw new InputMismatchException();
            }
            if (numChars == -1) {
                return -1;  // end of file
            }
        }
        return buf[curChar++];
    }

    // to read in entire lines, replace c <= ' '
    // with a function that checks whether c is a line break
    public String next() {
        int c;
        do {
            c = nextByte();
        } while (c <= ' ');

        StringBuilder res = new StringBuilder();
        do {
            res.appendCodePoint(c);
            c = nextByte();
        } while (c > ' ');
        return res.toString();
    }

    public int nextInt() {  // nextLong() would be implemented similarly
        int c;
        do {
            c = nextByte();
        } while (c <= ' ');

        int sgn = 1;
        if (c == '-') {
            sgn = -1;
            c = nextByte();
        }

        int res = 0;
        do {
            if (c < '0' || c > '9') {
                throw new InputMismatchException();
            }
            res = 10 * res + c - '0';
            c = nextByte();
        } while (c > ' ');
        return res * sgn;
    }

    public double nextDouble() {
        return Double.parseDouble(next());
    }
}