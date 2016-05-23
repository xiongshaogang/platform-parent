package cn.com.tcxy.util;

public class BankUtil {
    private static final String BANK_REGEX = "^[0-9]{13,19}$"; // 银行卡的卡号长度及结构符合ＩＳＯ7812－1有关规定，由13－19位数字表示
    
    /**
     * 银行卡长度验证,Luhn验证
     * 
     * @param str
     * @return 验证通过返回true
     */
    public static Boolean isBank(String number) {
        Boolean b = false;
        b = number.matches(BANK_REGEX);
        if(b){
            int s1 = 0, s2 = 0;
            String reverse = new StringBuffer(number).reverse().toString();
            for(int i = 0 ;i < reverse.length();i++){
                int digit = Character.digit(reverse.charAt(i), 10);
                if(i % 2 == 0){//this is for odd digits, they are 1-indexed in the algorithm
                    s1 += digit;
                }else{//add 2 * digit for 0-4, add 2 * digit - 9 for 5-9
                    s2 += 2 * digit;
                    if(digit >= 5){
                        s2 -= 9;
                    }
                }
            }
            System.out.println("倒序奇数位之和:"+s1);
            System.out.println("倒序偶数位之和s2:"+s2);
            System.out.println("总和:"+(s1+s2));
            if ((s1 + s2) % 10 == 0){
                return true;
            }else{
                return false;
            }  
        }else{
            return b;
        }
    }
 
}
