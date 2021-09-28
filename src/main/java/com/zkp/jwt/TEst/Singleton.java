package com.zkp.jwt.TEst;

import org.springframework.http.converter.json.GsonBuilderUtils;

import java.text.SimpleDateFormat;

public class Singleton {
    public    int x=0;
    private static Singleton singleton  ;
    // 构造函数私有化，避免外部创建实例
    private Singleton() {
    }
    public static Singleton getInstace(){
        return singleton=new Singleton();
    }
    //定义一个内部枚举
    public enum SingletonEnum{

        SEED;  // 唯一一个枚举对象，我们称它为“种子选手”！

        private Singleton singleton;

        SingletonEnum(){
            singleton = new Singleton(); //真正的对象创建隐蔽在此！
        }

        public Singleton getInstnce(){
            return singleton;
        }
    }

    // 故意外露的对象获取方法，也是外面获取实例的唯一入口
    public static Singleton getInstance(){
        return SingletonEnum.SEED.getInstnce(); // 通过枚举的种子选手来完成
    }

    public static void main(String[] args) {
//单例模式通过枚举类实现 保证线程安全。
        for (int i = 0 ;i<10;i++){
            new Thread(()->{
                Singleton instance = Singleton.getInstance();
             //   Singleton instance = Singleton.getInstace();
                System.out.println(instance);
                System.out.println(Thread.currentThread().getName());
                //System.out.println(singleton.x);
              //  singleton.x++;
                System.out.println("-------");
            }).start();
        }
    }
}
