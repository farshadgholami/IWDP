/**
 * Created by Pouya Payandeh on 3/2/2016.
 */
public class Shell {
    public static void main(String[] args)
    {
        int[] vels = {3, 5, 7, 10, 20, 30};
        String arg = "-initv %d -rei %d -initl %d -ro %.1f -ros %.1f -rosd %.1f -trc %.1f -ct %.1f -trb %d -at %d";
        int n =0;
        for(int i = 0 ; i < vels.length ; i++)
            for(int re = 5 ; re < 50 ; re+=10)
                for(int il = 1000 ; il <=10000 ; il+=2000)
                    for(double ro = 0.1 ; ro <= 0.9 ; ro+=0.4)
                        for(double ros = 0.1 ; ros <= 0.9 ; ros+=0.4)
                            for(double rosd = 0.1 ; rosd <= 0.9 ; rosd+=0.4)
                                for(double trc = 0.1 ; trc <= 0.9 ; trc+=0.4)
                                    for(double ct = 0.1 ; ct <= 0.9 ; ct+=0.4)
                                        for (int trb = 1; trb <= 1000; trb += 200)
                                        {

                                            for (int at = 1; at <= 1000; at += 200) {

                                                System.out.printf("java -jar a.x " + arg+" > %d.log \n",
                                                        vels[i],re,il,ro,ros,rosd,trc,ct,trb,at,++n
                                                        );

                                                if (at == 1)
                                                    at = 0;



                                            }
                                            if (trb == 1)
                                                trb = 0;
                                        }
    }
}
