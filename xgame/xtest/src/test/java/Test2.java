public class Test2 {
    public static void main(String[] args) {
        outterLoop:
        for (int i = 0; i < 9; i++){
            System.err.println("out--->"+i);
            for (int j = 0; j < 8; j++){
                // ……
                System.err.println("iner--->"+j);
                if (i==3){
                    break outterLoop; // 跳出外层循环
                }


            }
        }
    }
}
