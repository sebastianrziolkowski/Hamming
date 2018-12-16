import org.junit.Assert;

import java.util.Arrays;


public class Test {

    int[][]H ={ {1, 1, 1, 0, 1, 0, 0},
            {0, 1, 1, 1, 0, 1, 0},
            {1, 1, 0, 1, 0, 0, 1},
    };


    @org.junit.Test
    public void codingMessage()
    {
        // hamming_code
        //http://www.ecs.umass.edu/ece/koren/FaultTolerantSystems/simulator/Hamming/HammingCodes.html

        int[] input_1 = {1, 1, 1, 0};
        int[] result_1 = {1,1,1,0,1,0,0};
        Assert.assertTrue(Arrays.equals(Main.Code(input_1,H),result_1));


        int[] input_2 = {1,1,1,1};
        int[] result_2 = {1,1,1,1,1,1,1};
        Assert.assertTrue(Arrays.equals(Main.Code(input_2,H),result_2));

        int[] input_3 = {0,0,0,0};
        int[] result_3 = {0,0,0,0,0,0,0};
        Assert.assertTrue(Arrays.equals(Main.Code(input_3,H),result_3));


        int[] input_4 = {1,0,0,1};
        int[] result_4 = {1,0,0,1,1,1,0};
        Assert.assertTrue(Arrays.equals(Main.Code(input_4,H),result_4));
    }

    @org.junit.Test
    public void unCodingMessage()
    {

        int[] input_1 = {1,1,1,0,1,0,0};
        int[] resylt_1 = {1, 1, 1, 0};
        Assert.assertTrue(Arrays.equals(Main.DeCode(H,input_1),resylt_1));

        int[] input_2 = {1,1,1,1,1,1,1};
        int[] resylt_2 = {1, 1, 1, 1};
        Assert.assertTrue(Arrays.equals(Main.DeCode(H,input_2),resylt_2));

        int[] input_3 =  {0,0,0,0,0,0,0};
        int[] resylt_3 = {0, 0, 0, 0};
        Assert.assertTrue(Arrays.equals(Main.DeCode(H,input_3),resylt_3));

        int[] input_4 = {1,0,0,1,1,1,0};
        int[] resylt_4 = {1, 0, 0, 1};
        Assert.assertTrue(Arrays.equals(Main.DeCode(H,input_4),resylt_4));
    }


    @org.junit.Test
    public void syndrome()
    {
        int[] input_1 = {0,0,0};
        Assert.assertEquals(Main.wH(input_1),0);

        int[] input_2 = {1,0,1};
        Assert.assertEquals(Main.wH(input_2),2);

        int[] input_3 = {0,1,0};
        Assert.assertEquals(Main.wH(input_3),1);

        int[] input_4 = {1,1,1};
        Assert.assertEquals(Main.wH(input_4),3);

        int[] input_5 = {1,0,1};
        Assert.assertNotEquals(Main.wH(input_5),0);

        int[] input_6 = {1,0,1};
        Assert.assertNotEquals(Main.wH(input_6),1);
    }

}