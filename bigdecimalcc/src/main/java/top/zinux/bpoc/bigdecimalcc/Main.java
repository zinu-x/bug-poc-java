package top.zinux.bpoc.bigdecimalcc;

import java.math.BigDecimal;

/**
 * BigDecimal引起的CC攻击
 * @author zinux
 */
public class Main
{
    public static void main( String[] args )
    {
        /* CC攻击：Controller层、API层等对外服务，如果接受前端(调用方)参数的类型为BigDecimal，就会有cc攻击的风险         */
        BigDecimal  none = new BigDecimal("0");//先创建实例，防止类型加载和类型初始化影响性能测试。

        System.out.println("test start ...");
        long start = System.currentTimeMillis();
        BigDecimal  num = new BigDecimal("1e10000000");// 创建一个大数：10的10,000,000次方
        num.setScale(2).doubleValue();//转型为double类型  (模拟运算，其他运算也会有同样糟糕的性能损耗)
        System.out.println("BigDecimal大数运算耗时:"+(System.currentTimeMillis()-start)+"ms");//BigDecimal大数运算耗时:6472ms
        /*
         * 只是简单运算，就耗费5~6秒的时间，多次执行就会造成服务器CPU耗尽拒绝服务。
         * 方案建议1：Controller、API等尽量别用BigDecimal作为入参。
         * 方案建议2：BigDecimal运算前，进行范围校验，不要有大数。
         */
    }
}
