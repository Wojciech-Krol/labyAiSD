package lista1;

public class Drawer {
    private static void drawLine(int n, int line) {
        int tmp=0;
        for(int i=line;i<n;i++){
            System.out.printf(".");
        }
        while(tmp<line*2-1){
            System.out.printf("X");
            tmp++;
        }
        for(int i=line;i<n;i++){
            System.out.printf(".");
        }

    }


    public static void drawPyramid(int n) {
        if(n<1){return;}
        for(int i=1;i<=n;i++){
            drawLine(n,i);
            System.out.printf("\n");
        }
    }


    public static void drawChristmassTree(int n) {
        if(n<1){return;}
        for(int i=1;i<=n;i++){
            for(int j=1;j<=i;j++){
                drawLine(n,j);
                System.out.printf("\n");
            }
        }

    }
    public static void drawSqLine(int startpt,int amount, char c){
        if(c=='.'){
            System.out.printf("X");
            for(int i=startpt;i<amount;i++){
                System.out.print(c);
            }
            System.out.printf("X");
        }
        else {
            for (int i = startpt; i < amount; i++) {
                System.out.print(c);
            }
        }
    }
    public static void drawSquare(int n){
            if(n>2){
                drawSqLine(0,n,'X');
                System.out.println();
                for(int i=1;i<n-1;i++) {
                    drawSqLine(1,n-1,'.');
                    System.out.println();
                }
                drawSqLine(0,n,'X');
                System.out.println();
            }
            else{
                drawSqLine(0,n,'X');
                System.out.println();
                drawSqLine(0,n,'X');
                System.out.println();
            }
    }


}