import java.util.Scanner;

public class Kuttaka {
  public static void main (String[]args)   {
    // TODO: Auto-generated method stub
    int a, b, r1, r2, k = 0, num = 0;
    int m, n, c, i, val = 0;
    // static int[] q = new int[20];
    
    // System.out.println("Enter the values: ");

    // Scanner obj = new Scanner(System.in);
    // a = obj.nextInt();
    // b = obj.nextInt();
    // r1 = obj.nextInt();
    // r2 = obj.nextInt();

    // System.out.println("You entered: ");
    int[] q = new int[21];
    for(i = 0; i < q.length; i++) {
        q[i] = 0;
    }

    a=45;
    b=26;
    r1=7;
    r2=0;

    if (r1 > r2) c = r1 - r2;
    else c = r2 - r1;

    if (a > b) {
        m = a;
        n = b;
    }

    else {
        m = b;
        n = a;
    }

    int r = 0;
    i = 0;

    while (r != 1) {
        r = m % n;
        q[i++] = m / n;
        m = n;
        k = n;
        n = r;
    }

    for (i = 0; i < 20; i++)
      System.out.println (q[i]);
      
    for (i = 0; i < q.length; i++)  {
        if (q[i] != 0)
        num++;
    }
    q[0] = 0;
    // int t;
//  System.out.println(num+" "+n+" "+c);
    if (num % 2 == 1)  q[num] = k - c;
    else  q[num] = k + c;
    q[num + 1] = 1;
//  for(i=0;i<20;i++)
//   System.out.println(q[i]);
//  System.out.println(num);
//  System.out.println(q[num]);
    for (i = num; i > 1; i--) {
        val = q[i] * q[i - 1] + q[i + 1];
        q[i - 1] = val;
//   System.out.println(val);
    }
//  for(i=0;i<20;i++)
//   System.out.println(q[i]);
    System.out.println (val);

  }
}
