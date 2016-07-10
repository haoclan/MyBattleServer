package src.Utill;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by lion on 16/7/9.
 */
public class GenClass {

   public static void main(String[] args) throws IOException, InterruptedException {
       //执行shell命令
       //默认的执行地址是另一个地方//根目录工程
    Process ps =Runtime.getRuntime().exec("protoc --java_out ./Utill ./Utill/src/Utill/test.proto");
 //      Process ps =Runtime.getRuntime().exec("pwd");

       ps.waitFor();

        //ps.输入流, 输入流阅读, 缓冲阅读
       // Stringbuffer只是用于把小的String组装起来用
       BufferedReader br = new BufferedReader(new InputStreamReader(ps.getInputStream()));
       StringBuffer sb = new StringBuffer();
       String line;
       while ((line = br.readLine()) != null) {
           sb.append(line).append("\n");
       }
       String result = sb.toString();
       System.out.println(result);



}
}
