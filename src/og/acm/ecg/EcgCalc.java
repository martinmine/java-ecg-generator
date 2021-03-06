package og.acm.ecg;
/*
 * See EcgLicense.txt for License terms.
 */

/**
 * @author Mauricio Villarroel (m.villarroel@acm.og)
 */

public class EcgCalc {

    /**
     * Constants
     */
    private static final double PI = 2.0 * Math.asin(1.0);
    private static final int IA = 16807;
    private static final long IM = 2147483647;
    private static final double AM = (1.0 / IM);
    private static final long IQ = 127773;
    private static final int IR = 2836;
    private static final int NTAB = 32;
    private static final double NDIV = (1 + (IM - 1) / NTAB);
    private static final double EPS = 1.2e-7;
    private static final double RNMX = (1.0 - EPS);

    /**
     * DEFINE PARAMETERS AS GLOBAL VARIABLES
     */
    // Order of extrema: [P Q R S T]
    private double[] ti = new double[6];  /* ti converted in radians             */
    private double[] ai = new double[6];  /* new calculated a                    */
    private double[] bi = new double[6];  /* new calculated b                    */
    private int mstate = 3;               /*  System state space dimension       */
    private double xinitial = 1.0;        /*  Initial x co-ordinate value        */
    private double yinitial = 0.0;        /*  Initial y co-ordinate value        */
    private double zinitial = 0.04;       /*  Initial z co-ordinate value        */
    private long rseed;
    private double h;
    private double[] rr, rrpc;

    /*
     * Variables for static function ran1()
     */
    private long iy;
    private long[] iv;

    /*
     * ECG Result Variables
     */
    /* Result Vectors*/
    private double[] ecgResultTime;
    private double[] ecgResultVoltage;
    private int ecgResultNumRows;
    /* Object Variables */
    private EcgParameters paramOb;
    private LogWindow ecgLog;

    /**
     * Creates a new instance of EcgCalc
     * @param parameters
     * @param logOb
     */
    public EcgCalc(EcgParameters parameters, LogWindow logOb) {
        paramOb = parameters;
        ecgLog = logOb;

        // variables for static function ranq()
        iy = 0;
        iv = new long[NTAB];
    }

    public Generator calculateEcg() {
        ecgLog.println("Starting to calculate ECG....");
        return new Generator();
    }

    public int getEcgResultNumRows() {
        return ecgResultNumRows;
    }

    @Deprecated
    public double getEcgResultTime(int index) {
        return ecgResultTime[index];
    }

    @Deprecated
    public double getEcgResultVoltage(int index) {
        return ecgResultVoltage[index];
    }

    @Deprecated
    public int getEcgResultPeak(int index) {
        // TODO remove this entirely
        return 0;
    }

    /*--------------------------------------------------------------------------*/
    /*    UNIFORM DEVIATES                                                      */
    /*--------------------------------------------------------------------------*/
    private double ran1() {

        int j;
        long k;
        double temp;
        boolean flg;

        flg = iy != 0;

        if ((rseed <= 0) || !flg) {
            if (-(rseed) < 1)
                rseed = 1;
            else
                rseed = -rseed;

            for (j = NTAB + 7; j >= 0; j--) {
                k = (rseed) / IQ;
                rseed = IA * (rseed - k * IQ) - IR * k;
                if (rseed < 0)
                    rseed += IM;
                if (j < NTAB)
                    iv[j] = rseed;
            }
            iy = iv[0];
        }

        k = (rseed) / IQ;
        rseed = IA * (rseed - k * IQ) - IR * k;
        if (rseed < 0)
            rseed += IM;

        j = (int) (iy / NDIV);
        iy = iv[j];
        iv[j] = rseed;

        if ((temp = AM * iy) > RNMX)
            return RNMX;
        else
            return temp;
    }

    /*
     * FFT
     */
    private void ifft(double[] data, long nn, int isign) {

        long n, mmax, m, istep, i, j;
        double wtemp, wr, wpr, wpi, wi, theta;
        double tempr, tempi;
        double swap;

        n = nn << 1;
        j = 1;
        for (i = 1; i < n; i += 2) {
            if (j > i) {
                //SWAP(data[j],data[i]);
                swap = data[(int) j];
                data[(int) j] = data[(int) i];
                data[(int) i] = swap;
                //SWAP(data[j+1],data[i+1]);
                swap = data[(int) j + 1];
                data[(int) j + 1] = data[(int) i + 1];
                data[(int) i + 1] = swap;
            }
            m = n >> 1;
            while (m >= 2 && j > m) {
                j -= m;
                m >>= 1;
            }
            j += m;
        }
        mmax = 2;
        while (n > mmax) {
            istep = mmax << 1;
            theta = isign * (6.28318530717959 / mmax);
            wtemp = Math.sin(0.5 * theta);
            wpr = -2.0 * wtemp * wtemp;
            wpi = Math.sin(theta);
            wr = 1.0;
            wi = 0.0;
            for (m = 1; m < mmax; m += 2) {
                for (i = m; i <= n; i += istep) {
                    j = i + mmax;
                    tempr = wr * data[(int) j] - wi * data[(int) j + 1];
                    tempi = wr * data[(int) j + 1] + wi * data[(int) j];
                    data[(int) j] = data[(int) i] - tempr;
                    data[(int) j + 1] = data[(int) i + 1] - tempi;
                    data[(int) i] += tempr;
                    data[(int) i + 1] += tempi;
                }
                wr = (wtemp = wr) * wpr - wi * wpi + wr;
                wi = wi * wpr + wtemp * wpi + wi;
            }
            mmax = istep;
        }
    }

    /*
     * STANDARD DEVIATION CALCULATOR
     */
    /* n-by-1 vector, calculate standard deviation */
    private double stDev(double[] x, int n) {
        int j;
        double add, mean, diff, total;

        add = 0.0;
        for (j = 1; j <= n; j++)
            add += x[j];

        mean = add / n;

        total = 0.0;
        for (j = 1; j <= n; j++) {
            diff = x[j] - mean;
            total += diff * diff;
        }
        return (Math.sqrt(total / ((double) n - 1)));
    }

    /*
     * THE ANGULAR FREQUENCY (!!!)
     */
    private double angFreq(double t) {
        int i = 1 + (int) Math.floor(t / h);
        return (2.0 * PI / rrpc[i]);
    }

    /*--------------------------------------------------------------------------*/
    /*    THE EXACT NONLINEAR DERIVATIVES                                       */
    /*--------------------------------------------------------------------------*/
    private void derivspqrst(double t0, double[] x, double[] dxdt) {

        int i, k;
        double a0, w0, r0, x0, y0;
        double t, dt, dt2, zBase;
        double[] xi, yi;

        k = 5;
        xi = new double[k + 1];
        yi = new double[k + 1];
        w0 = angFreq(t0);
        r0 = 1.0;
        x0 = 0.0;
        y0 = 0.0;
        a0 = 1.0 - Math.sqrt((x[1] - x0) * (x[1] - x0) + (x[2] - y0) * (x[2] - y0)) / r0;

        for (i = 1; i <= k; i++)
            xi[i] = Math.cos(ti[i]);
        for (i = 1; i <= k; i++)
            yi[i] = Math.sin(ti[i]);


        zBase = 0.005 * Math.sin(2.0 * PI * paramOb.getFHi() * t0);

        t = Math.atan2(x[2], x[1]);
        dxdt[1] = a0 * (x[1] - x0) - w0 * (x[2] - y0);
        dxdt[2] = a0 * (x[2] - y0) + w0 * (x[1] - x0);
        dxdt[3] = 0.0;

        for (i = 1; i <= k; i++) {
            dt = Math.IEEEremainder(t - ti[i], 2.0 * PI);
            dt2 = dt * dt;
            dxdt[3] += -ai[i] * dt * Math.exp(-0.5 * dt2 / (bi[i] * bi[i]));
        }
        dxdt[3] += -1.0 * (x[3] - zBase);
    }

    /*
     * RUNGA-KUTTA FOURTH ORDER INTEGRATION
     */
    private void Rk4(double[] y, int n, double x, double h, double[] yout) {
        int i;
        double xh, hh, h6;
        double[] dydx, dym, dyt, yt;

        dydx = new double[n + 1];
        dym = new double[n + 1];
        dyt = new double[n + 1];
        yt = new double[n + 1];

        hh = h * 0.5;
        h6 = h / 6.0;
        xh = x + hh;

        derivspqrst(x, y, dydx);
        for (i = 1; i <= n; i++)
            yt[i] = y[i] + hh * dydx[i];

        derivspqrst(xh, yt, dyt);
        for (i = 1; i <= n; i++)
            yt[i] = y[i] + hh * dyt[i];

        derivspqrst(xh, yt, dym);
        for (i = 1; i <= n; i++) {
            yt[i] = y[i] + h * dym[i];
            dym[i] += dyt[i];
        }

        derivspqrst(x + h, yt, dyt);
        for (i = 1; i <= n; i++)
            yout[i] = y[i] + h6 * (dydx[i] + dyt[i] + 2.0 * dym[i]);
    }

    /*
     * GENERATE RR PROCESS
     */
    private void rrProcess(double[] rr, double flo, double fhi,
                           double flostd, double fhistd, double lfhfratio,
                           double hrmean, double hrstd, double sf, int n) {
        int i, j;
        double c1, c2, w1, w2, sig1, sig2, rrmean, rrstd, xstd, ratio;
        double df;//,dw1,dw2;
        double[] w, Hw, Sw, ph0, ph, SwC;

        w = new double[n + 1];
        Hw = new double[n + 1];
        Sw = new double[n + 1];
        ph0 = new double[n / 2 - 1 + 1];
        ph = new double[n + 1];
        SwC = new double[(2 * n) + 1];

        w1 = 2.0 * PI * flo;
        w2 = 2.0 * PI * fhi;
        c1 = 2.0 * PI * flostd;
        c2 = 2.0 * PI * fhistd;
        sig2 = 1.0;
        sig1 = lfhfratio;
        rrmean = 60.0 / hrmean;
        rrstd = 60.0 * hrstd / (hrmean * hrmean);

        df = sf / (double) n;
        for (i = 1; i <= n; i++)
            w[i] = (i - 1) * 2.0 * PI * df;

        for (i = 1; i <= n; i++) {
            Hw[i] = (sig1 * Math.exp(-0.5 * (Math.pow(w[i] - w1, 2) / Math.pow(c1, 2))) / Math.sqrt(2 * PI * c1 * c1))
                    + (sig2 * Math.exp(-0.5 * (Math.pow(w[i] - w2, 2) / Math.pow(c2, 2))) / Math.sqrt(2 * PI * c2 * c2));
        }

        for (i = 1; i <= n / 2; i++)
            Sw[i] = (sf / 2.0) * Math.sqrt(Hw[i]);

        for (i = n / 2 + 1; i <= n; i++)
            Sw[i] = (sf / 2.0) * Math.sqrt(Hw[n - i + 1]);

        /* randomise the phases */
        for (i = 1; i <= n / 2 - 1; i++)
            ph0[i] = 2.0 * PI * ran1();

        ph[1] = 0.0;
        for (i = 1; i <= n / 2 - 1; i++)
            ph[i + 1] = ph0[i];

        ph[n / 2 + 1] = 0.0;
        for (i = 1; i <= n / 2 - 1; i++)
            ph[n - i + 1] = -ph0[i];

        /* make complex spectrum */
        for (i = 1; i <= n; i++)
            SwC[2 * i - 1] = Sw[i] * Math.cos(ph[i]);

        for (i = 1; i <= n; i++)
            SwC[2 * i] = Sw[i] * Math.sin(ph[i]);

        /* calculate inverse fft */
        ifft(SwC, n, -1);

        /* extract real part */
        for (i = 1; i <= n; i++)
            rr[i] = (1.0 / (double) n) * SwC[2 * i - 1];

        xstd = stDev(rr, n);
        ratio = rrstd / xstd;

        for (i = 1; i <= n; i++)
            rr[i] *= ratio;

        for (i = 1; i <= n; i++)
            rr[i] += rrmean;
    }

    public class EcgMeasurement {
        double voltage;
        double time;
    }

    public class Generator {
        int i, j, k, Nrr, Nt;
        int q;
        double tstep, tecg, rrmean, hrfact, hrfact2;
        double timev;
        double[] x = new double[4];

        public Generator()
        {
            // perform some checks on input values
            q = (int) Math.rint(paramOb.getSf() / paramOb.getSfEcg());

            // convert angles from degrees to radians and copy a vector to ai
            for (i = 1; i <= 5; i++) {
                ti[i] = paramOb.getTheta(i - 1) * PI / 180.0;
                ai[i] = paramOb.getA(i - 1);
            }

            // adjust extrema parameters for mean heart rate
            hrfact = Math.sqrt(paramOb.getHrMean() / 60);
            hrfact2 = Math.sqrt(hrfact);

            for (i = 1; i <= 5; i++)
                bi[i] = paramOb.getB(i - 1) * hrfact;

            ti[1] *= hrfact2;
            ti[2] *= hrfact;
            ti[3] *= 1.0;
            ti[4] *= hrfact;
            ti[5] *= 1.0;


            ecgLog.println("Approximate number of heart beats: " + paramOb.getN());
            ecgLog.println("ECG sampling frequency: " + paramOb.getSfEcg() + " Hertz");
            ecgLog.println("Internal sampling frequency: " + paramOb.getSf() + " Hertz");
            ecgLog.println("Amplitude of additive uniformly distributed noise: " + paramOb.getANoise() + " mV");
            ecgLog.println("Heart rate mean: " + paramOb.getHrMean() + " beats per minute");
            ecgLog.println("Heart rate std: " + paramOb.getHrStd() + " beats per minute");
            ecgLog.println("Low frequency: " + paramOb.getFLo() + " Hertz");
            ecgLog.println("High frequency std: " + paramOb.getFHiStd() + " Hertz");
            ecgLog.println("Low frequency std: " + paramOb.getFLoStd() + " Hertz");
            ecgLog.println("High frequency: " + paramOb.getFHi() + " Hertz");
            ecgLog.println("LF/HF ratio: " + paramOb.getLfHfRatio());
            ecgLog.println("time step milliseconds: " + paramOb.getEcgAnimateInterval() + "\n");
            ecgLog.println("Order of Extrema:");
            ecgLog.println("      theta(radians)");
            ecgLog.println("P: [" + ti[1] + "\t]");
            ecgLog.println("Q: [" + ti[2] + "\t]");
            ecgLog.println("R: [" + ti[3] + "\t]");
            ecgLog.println("S: [" + ti[4] + "\t]");
            ecgLog.println("T: [" + ti[5] + "\t]\n");
            ecgLog.println("      a(calculated)");
            ecgLog.println("P: [" + ai[1] + "\t]");
            ecgLog.println("Q: [" + ai[2] + "\t]");
            ecgLog.println("R: [" + ai[3] + "\t]");
            ecgLog.println("S: [" + ai[4] + "\t]");
            ecgLog.println("T: [" + ai[5] + "\t]\n");
            ecgLog.println("      b(calculated)");
            ecgLog.println("P: [" + bi[1] + "\t]");
            ecgLog.println("Q: [" + bi[2] + "\t]");
            ecgLog.println("R: [" + bi[3] + "\t]");
            ecgLog.println("S: [" + bi[4] + "\t]");
            ecgLog.println("T: [" + bi[5] + "\t]\n");


            // Initialise the vector
            x[1] = xinitial;
            x[2] = yinitial;
            x[3] = zinitial;

            // initialise seed
            rseed = -paramOb.getSeed();

           // calculate time scales
            h = 1.0 / (double) paramOb.getSf();
            tstep = 1.0 / (double) paramOb.getSfEcg();

            // calculate length of RR time series
            rrmean = (60.0 / paramOb.getHrMean());
            Nrr = (int) Math.pow(2.0, Math.ceil(Math.log(paramOb.getN() * rrmean * paramOb.getSf()) / Math.log(2.0)));

            ecgLog.println("Using " + Nrr + " = 2^ " + (int) (Math.log(1.0 * Nrr) / Math.log(2.0)) + " samples for calculating RR intervals");

            // create rrProcess with required spectrum
            rr = new double[Nrr + 1];
            rrProcess(rr, paramOb.getFLo(), paramOb.getFHi(), paramOb.getFLoStd(),
                    paramOb.getFHiStd(), paramOb.getLfHfRatio(), paramOb.getHrMean(),
                    paramOb.getHrStd(), paramOb.getSf(), Nrr);

            // create piecewise constant rr
            rrpc = new double[(2 * Nrr) + 1];
            tecg = 0.0;
            i = 1;
            j = 1;
            while (i <= Nrr) {
                tecg += rr[j];
                j = (int) Math.rint(tecg / h);
                for (k = i; k <= j; k++)
                    rrpc[k] = rr[i];
                i = j + 1;
            }
            Nt = j;

            ecgResultNumRows = (int) Math.ceil( (double) Nt / q);
            ecgResultVoltage = new double[ecgResultNumRows];
            ecgResultTime = new double[ecgResultNumRows];

            // integrate dynamical system using fourth order Runge-Kutta
            timev = 0.0;


            // insert into the ECG data table
            ecgLog.println("Generating result matrix...");
            i = 1;
            j = 0;

            /*
            Old code for reference:
            for (i = 1, j = 0; i <= Nt; i++) {
                Rk4(x, mstate, timev, h, x);
                timev += h;

                if (i % q == 0) {
                    final double noise = paramOb.getANoise() * (2.0 * ran1() - 1.0);
                    ecgResultVoltage[j] = ((x[3] - zMin) * (1.6) / zRange - 0.4) + noise;

                    // separate
                    ecgResultTime[j] = (j) * tstep;
                    j++;
                }
            }
            */
        }


        // constants for scaling the signal to lie between -0.4 and 1.2 mV
        final double zMin = -0.01;
        final double zRange = 0.066;

        public EcgMeasurement next() {
            EcgMeasurement measurement = new EcgMeasurement();

            do {
                Rk4(x, mstate, timev, h, x);
                timev += h;
            } while ((i++ % q != 0));

            final double noise = paramOb.getANoise() * (2.0 * ran1() - 1.0);
            measurement.voltage = ((x[3] - zMin) * 1.6 / zRange - 0.4) + noise;

            // separate
            measurement.time = (j) * tstep;
            j++;

            return measurement;
        }
    }
}
