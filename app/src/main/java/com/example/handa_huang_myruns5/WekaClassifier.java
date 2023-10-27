package com.example.handa_huang_myruns5;



class WekaClassifier {

    public static double classify(Object[] i)
            throws Exception {

        double p = Double.NaN;
        p = WekaClassifier.N220e8700(i);
        return p;
    }
    static double N220e8700(Object []i) {
        double p = Double.NaN;
        if (i[64] == null) {
            p = 1;
        } else if (((Double) i[64]).doubleValue() <= 7.621048) {
            p = WekaClassifier.N4707b7671(i);
        } else if (((Double) i[64]).doubleValue() > 7.621048) {
            p = 2;
        }
        return p;
    }
    static double N4707b7671(Object []i) {
        double p = Double.NaN;
        if (i[4] == null) {
            p = 1;
        } else if (((Double) i[4]).doubleValue() <= 15.82944) {
            p = 1;
        } else if (((Double) i[4]).doubleValue() > 15.82944) {
            p = WekaClassifier.N56dda8512(i);
        }
        return p;
    }
    static double N56dda8512(Object []i) {
        double p = Double.NaN;
        if (i[4] == null) {
            p = 1;
        } else if (((Double) i[4]).doubleValue() <= 18.651777) {
            p = 1;
        } else if (((Double) i[4]).doubleValue() > 18.651777) {
            p = 2;
        }
        return p;
    }
}

