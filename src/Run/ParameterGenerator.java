package Run;

import java.lang.reflect.Field;
import java.util.Random;

/**
 * Created by Pouya Payandeh on 7/16/2016.
 */
public class ParameterGenerator
{
    public static <T> T generate(Class<T> prototype) throws IllegalAccessException, InstantiationException
    {

        Field[] fields = prototype.getFields();
        T obj = prototype.newInstance();
        for (Field f : fields)
        {
            if (f.isAnnotationPresent(RandomParameter.class))
            {
                RandomParameter randomParameter = f.getAnnotation(RandomParameter.class);
                Random rnd = new Random();
                double val = rnd.nextDouble() * (randomParameter.upperBound() - randomParameter.lowerBound())
                        + randomParameter.lowerBound();
                if (f.getType() == double.class)
                    f.set(obj, val);
                else if (f.getType() == int.class)
                    f.set(obj, (int) val);
            }
        }
        return obj;
    }
}
//
//    public static void main(String[] args) throws InstantiationException, IllegalAccessException
//    {
//        Run.ParameterGenerator pg = new Run.ParameterGenerator();
//        Test k = pg.generateParameter(Test.class);
//        System.out.println(k.a);
//        System.out.println(k.b);
//    }
//}
//class Test
//{
//    @Run.RandomParameter(lowerBound = 0.7 ,upperBound = 0.8)
//    public double a;
//    @Run.RandomParameter(lowerBound = 2 ,upperBound = 10)
//    public int b;
//}