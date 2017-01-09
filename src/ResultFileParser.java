import java.io.BufferedReader;
import java.util.Scanner;
import java.util.StringTokenizer;

/**
 * Created by farsh on 12/16/2016.
 */
public class ResultFileParser {
    double ro, ros, rosd, tra, trb, trc, at, bt, ct, initv, initl, min, max, adjustedAvg, meanBest;
    int rei, successfulRuns;
    String best;


    public ResultFileParser(BufferedReader file) {
        Scanner input = new Scanner(file);

        String s = input.nextLine();
        String d = s.replaceAll("[\\[\\](){}]|Result|arg|Arg|rosd|ros|ro|tra|trb|trc|at|bt|ct|initv|initl|rei|=|,", " ");
        StringTokenizer st = new StringTokenizer(d, " ");

        ro = Double.parseDouble(st.nextToken());
        ros = Double.parseDouble(st.nextToken());
        rosd = Double.parseDouble(st.nextToken());
        tra = Double.parseDouble(st.nextToken());
        trb = Double.parseDouble(st.nextToken());
        trc = Double.parseDouble(st.nextToken());
        at = Double.parseDouble(st.nextToken());
        bt = Double.parseDouble(st.nextToken());
        ct = Double.parseDouble(st.nextToken());
        initv = Double.parseDouble(st.nextToken());
        initl = Double.parseDouble(st.nextToken());
        rei = Integer.parseInt(st.nextToken());

        s = input.nextLine();
        d = s.replaceAll("min|=|,", " ");
        st = new StringTokenizer(d, " ");
        min = Double.parseDouble(st.nextToken());
        s = input.nextLine();
        d = s.replaceAll("max|=|,", " ");
        st = new StringTokenizer(d, " ");
        max = Double.parseDouble(st.nextToken());
        input.nextLine();
        s = input.nextLine();
        d = s.replaceAll("adjustedAvg|=|,", " ");
        st = new StringTokenizer(d, " ");
        adjustedAvg = Double.parseDouble(st.nextToken());
        s = input.nextLine();
        d = s.replaceAll("meanBest|=|,", " ");
        st = new StringTokenizer(d, " ");
        meanBest = Double.parseDouble(st.nextToken());
        s = input.nextLine();
        d = s.replaceAll("successfulRuns|=|,", " ");
        st = new StringTokenizer(d, " ");
        successfulRuns = Integer.parseInt(st.nextToken());
        s = input.nextLine();
        d = s.replaceAll("best|=|,|'", " ");
        best = d.trim();
    }

//    public static void main(String[] args) {
//        BufferedReader br = null;
//        try {
//            br = new BufferedReader(new FileReader(String.format("LOG\\%d", 2)));
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        }
//        ResultFileParser r = new ResultFileParser(br);
//
//        System.out.println(r.ro);
//        System.out.println(r.ros);
//        System.out.println(r.rosd);
//        System.out.println(r.best);
//    }
}
