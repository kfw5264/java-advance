# 设计模式(DesignPatterns)

## 一、单例模式(Singleton)

#### 概念

一个类只有一个实例，且该类能自行创建这种实例。

#### 特点

1. 只有一个实例
2. 能自行创建实例
3. 对外提供一个能访问该实例的全局访问点

#### 应用

1. 饿汉式

   ```java
     public class SingletonDmeo{
           // 创建一个该类私有的对象
           private SingletonDmeo instance = new SingletonDemo();
           // 构造器私有化，禁止其他类创建对象
           private SingletonDmeo(){}
           // 创建一个的静态方法返回对象
           public static SingletonDemo getInstance(){
               return this.instance;
           }
       }
   ```

  


   - 简单明了且在多线程环境下可以不会收到任何影响
   - 在类加载的时候就会产生一个该类的对象

2. 懒汉式

   ```java
   public class SingletonDemo{
       // 创建一个私有实例，但是不初始化
       private SingletonDemo instance;
       // 构造器私有化，禁止其他类创建对象
        private SingletonDmeo(){}
       // 创建一个的静态方法返回对象
       public static SingletonDemo getInstance(){
           if(instance == null){
               instance = new SingletonDemo();
           }
           return instance;
       }
   }		
   ```

   - 这种方法类加载的时候不会创建对象，只是在需要的时候判断实例是否为空，如果为空则创建一个。但是在多线程环境中使用该方法时这种方式可能会创建出多个实例。

    ```java
     public class SingletonDemo{
           private volatile SingletonDemo instance;
           private SingletonDemo(){}
           public static synchronized SingletonDemo getInstance(){
               if(instance == null){
                   instance = new SingletonDemo();
               }
               return instance;
           } 
       }
   ```


   - 通过synchronized锁定方法中的代码，解决多线程中可能或出现的创建多个实例的问题。但是由于其他线程需要等待锁，所以效率比较慢。
   ```java
     public class SingletoDemo{
                   private volatile SingletonDemo instance;
                   private SingletonDemo(){}
                   public static SingletonDemo getInstance(){
                       if(instance == null){
                           synchronized {
                               if(instance == null){
                                   instance = new SingletonDemo();  
                               }  	 
                           }
                       }
                       return instance;
                   }
               }
   ```

   - Double Check提升效率，比较完美的方法。

3. 内部类

   ```java
   public class SingletonDemo{
       private SingletonDemo(){}
       private static class SingletonDemoHolder{
           private final static SingletonDemo INSTANCE = new SingletonDemo()
       }
       public static SingletonDemo getInstance(){
           return SingletonDemoHolder.INSTANCE;
       }
   }
   ```

   

4. 枚举类（Effective Java推荐）

   ```java
   public enum SingletonDemo{
       INSTANCE;
   }
   ```

## 二、原型模式(Prototype)

#### 概念

> 用一个已经创建的实例作为原型，通过复制该原型对象来创建一个与原型相同或者相似的对象
>
> 通过原型模式创建对象非常高效，无需知道创建对象的细节。

#### 结构

- 抽象原型类
- 具体原型类
- 访问类

#### 应用

```java
public class MonkeySun implements Cloneable {

    public MonkeySun(){
       System.out.println("创建了一个孙悟空实例");
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        System.out.print("复制了一个孙悟空实例");
        return super.clone();
    }
}


class Client {

    public static void main(String[] args) throws CloneNotSupportedException {
       MonkeySun  monkeySun = new MonkeySun();
       MonkeySun monkeySunCopy = (MonkeySun) monkeySun.clone();
       System.out.println(monkeySun == monkeySunCopy);   //false
    }
}
```

上述例子中`MonkeySun`为具体原型类，Java自带的`Cloneable`接口抽象原型类

 
## 三、工厂方法模式(Factory Method)

#### 概念

> 定义一个创建产品对象的接口，将产品对象的实际创捷工作推迟到具体的工厂类中。满足创建性模式的“创建与使用相分离”的特点。

#### 优点以及缺点

**优点**

- 客户端只需要知道具体工厂的名称就可以得到所需的产品，无需知道具体产品创建的过程，
-  系统增加新的产品时无需对原有工厂进行修改，只需要添加新的产品类以及与之对应的工厂类，满足开闭原则。

**缺点**

- 每增加一个产品就需要增加一个具体的产品类以及其对应的工厂类，这增加了系统的复杂度。

#### 结构

1. 抽象工厂(Abstract Factory)：提供了创建产品的接口，调用者通过它访问具体工厂的工厂方法createProduct()来创建产品
2. 具体工厂(ConcreteFactory)：主要实现抽象工厂中的抽象方法，完成产品的创建
3. 抽象产品(Product)：定义了产品的规范，描述了产品的主要特征和功能。
4. 具体产品(ConcreteProduct)：实现了抽象产品角色所定义的接口，由具体工厂来创建，它与具体工厂一一对应。 

#### 应用

1. 汽车工厂接口

   ```java
   public interface CarFactory{
       public ICar createCar();
   }
   ```

2. 汽车接口

   ```java
   public interface ICar{
   	public void energy();
   }
   ```

3. 特斯拉工厂

   ```java
   public class TeslaFactory implements ICarFactory {
       @Override
       public ICar createCar() {
           return new TeslaCar();
       }
   }
   ```

4. 奔驰工厂

   ```java
   public class BenzFactory implements ICarFactory {
       @Override
       public ICar createCar() {
           return new BenzCar();
       }
   }
   ```

5. 特斯拉汽车

   ```java
   public class TeslaCar implements ICar {
       @Override
       public void energy() {
           System.out.println("特斯拉使用电......");
       }
   }
   ```

6. 奔驰汽车

   ```java
   public class BenzCar implements ICar {
   
       @Override
       public void energy() {
           System.out.println("奔驰汽车用汽油.....");
       }
   }
   ```

   
## 四、抽象工厂模式

#### 概念

> 一种为访问类提供一个创建一组相关或者相互依赖的接口，且访问类不需要指定所需产品的具体类就可以得到同组不同等级的产品模式结构。

#### 结构

1. 抽象工厂(Abstract Factory)：提供创建产品的接口，包含多个创建产品的方法，可以创建多个不同等级的产品。
2.  具体工厂(Concrete Factory)：主要是实现抽象工厂中的多个抽象方法，完成具体产品的创建。
3.  抽象产品(Product)：定义了产品的规范，描述了产品的主要特性和功能，抽象工厂模式有多个抽象产品
4. 具体产品(Concrete Product)：实现了抽象产品角色所定义的接口，由具体工厂创建，它同具体工厂之间是多对一关系。

#### 应用场景

- 需要创建的产品是一系列相互关联，相互依赖的产品组时。
- 系统中有多个产品组，但是每次只使用其中一族产品。
- 系统提供了产品的类库，且所有产品的接口相同。客户端不依赖产品实例的创建细节和内部结构。

## 五、建造者模式

#### 定义

> 将一个复杂对象的构造与它的表示分离，使同样的构建过程可以创建不同的表示。

将一个复杂的对象分为多个简单的对象，然后一步步构建而成。将变与不变分离，即产品的组成部分使不变的，但每一部分使可以灵活选择的。

#### 优点与缺点

- **优点**：
  1. 各个部分的建造者相互独立，有利于系统的扩展。
  2. 客户端不必知道产品内部组成细节，便于控制细节风险。
- **缺点**：
  1. 产品的组成部分必须相同，限制了产品的使用部分。
  2. 如果产品内部变化复杂，该模式会增加很多的建造者类。

#### 结构

1. 产品角色(Product)：它是多个组成部件的复杂对象，由具体建造者来创建其各个部件。
2. 抽象建造者(Builder)：它是一个包含创建产品各个子部件的抽象方法的接口，通常还包含一个返回复杂产品的方法getResult()。
3. 具体建造者(Concrete Builder)：实现Builder接口，完成复杂产品的各个部件的具体创建方法。
4. 指挥者(Director)：它调用建造者对象中的部件构造与装配方法完成复杂对象的创建，在指挥者中不涉及具体产品的信息。

## 六、代理模式(Proxy)
#### 定义  
> 由于某些原因需要给某对象提供一个代理以控制对该对象的访问。

访问对象不能或者不适合直接引用对象，代理对象作为访问对象与目标对象之间的中介。

#### 优点与缺点
 - **优点**
    1. 代理模式在客户端和目标对象中间起到一个中介作用和保护目标对象的作用。
    2. 代理对象可以扩展目标对象的功能。
    3. 代理模式能够将客户端与目标对象分离，在一定程度上降低了系统的耦合度。
 - **缺点**
    1. 在客户端和目标对象中间加了一个代理对象，或造成请求速度减慢。
    2. 增加了系统的复杂度。
    
#### 结构
- 静态代理
 1. 抽象主题类(Subject)：通过接口或者抽象类声明真实主体和代理对象实现的业务方法。 
    ```java
    interface Subject{
        void request();
    }
    ``` 
 2. 真实主题类(Real Subject)：通过实现抽象主题中具体业务，是代理对象所代表的真实对象，是最终要引用的对象。  
    ```java
    class RealSubject implements Subject{
        @Override
        public void request(){
            System.out.println("访问真实主题方法...");
        }
    }
    ```
 3. 代理类(Proxy)：提供与真实主题相同的接口，其内部含有对真实主题的引用，它可以访问、控制和扩展真实主题的功能。  
    ```java
    class Proxy implements Subject{
        private RealSubject realSubject;
        
        @Override
        public void request(){
            if (realSubject == null) {
                realSubject = new RealSubject();
            }
            preRequest();
            realSubject.request();
            afterRequest();
        }  
    
        public void preRequest(){
            System.out.println("访问真实主题之前的预处理......");
        }
        public void afterRequest(){
            System.out.println("访问真实主题之后的后续处理......");
        } 
    }
    ```  
- JDK动态代理
    抽象主题类与真实主题类同上。    
    代理类不需要实现接口   
    ```java
    public class DynamicProxy {
        // 定义一个目标对象
        private Object target;
    
        public DynamicProxy(Object target){
            this.target = target;
        }
    
        // 传统方法
        public Object getProxyInstance(){
            return Proxy.newProxyInstance(
                    target.getClass().getClassLoader(),
                    target.getClass().getInterfaces(),
                    new InvocationHandler() {
                        @Override
                        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                            preRequest();
                            Object returnValue = method.invoke(target, args);
                            afterRequest();
    
                            return  returnValue;
                        }
                    }
            );
        }
    
        // lambda表达是
        public Object getProxyInstanceLambda(){
            return Proxy.newProxyInstance(
                    target.getClass().getClassLoader(),
                    target.getClass().getInterfaces(),
                    (proxy, method, args) -> {
                        preRequest();
                        Object returnValue = method.invoke(target, args);
                        afterRequest();
    
                        return  returnValue;
                    }
            );
        }
    
        private void preRequest(){
            System.out.println("动态代理访问真实主题方法之前的预处理.......");
        }
        private void afterRequest(){
            System.out.println("动态代理访问真实主题之后的后续处理......");
        }
    }
    ```
  
## 七. 适配器模式(Adapter)
#### 定义
> 将一个类的接口转换成客户需要的接口，使得原本不兼容的而不能一起工作的那些类能够一起工作。

#### 优点与缺点
  **优点：**
  1. 客户端通过适配器可以透明的调用目标接口。
  2. 复用了现有的类，程序员不需要修改原有的代码而重用现有的适配器类。
  3. 将目标类与适配器类解耦，解决了目标类和适配者类接口不一致的问题。

  **缺点**    
  - 对于适配器来说，更换适配器的实现过程比较复杂。
#### 结构与实现
  1. 目标接口(Target)：当前系统业务所期待的接口。
  ```java
    public interface CommonInterface {
    
        public void ListenMusicByCommonHeadset();
    }
  ```
  2. 适配者类(Adaptee)：被访问和适配的现存组件库中的组件接口。
  ```java
    public class TypeCPhone {
    
        public void listenMusic(){
            System.out.println("用type-c接口的耳机听音乐......");
        }
    }
  ```
  3. 适配器类(Adapter)：转化器，将适配者类转换成目标接口，让客户目标接口的格式访问适配者。
  - 类适配器（不推荐，慎用继承）
  ```java
    public class ClassAdapter extends TypeCPhone implements CommonInterface {
        @Override
        public void ListenMusicByCommonHeadset() {
            System.out.println("借助Type-c转3.5mm接口的适配器听歌");
            this.listenMusic();
        }
    }
  ```  
  - 对象适配器（推荐）
  ```java
    public class ObjectAdapter implements CommonInterface {
    
        private TypeCPhone phone;
    
        public ObjectAdapter(TypeCPhone phone){
            this.phone = phone;
        }
    
        @Override
        public void ListenMusicByCommonHeadset() {
            System.out.println("借助Type-c转3.5mm接口的适配器听歌");
            phone.listenMusic();
        }
    }
  ```

## 八、桥接模式(Bridge)
#### 定义
  > 将抽象与实现分离，使其可以独立变化。用组合关系来代替继承来实现，从而降低了抽象和实现这两个可变维度的耦合度。
  
#### 优点与缺点
  **优点：**
  - 由于抽象与实现相分离，所以扩展性比较强。
  - 其实现细节对客户透明。
  **缺点：**
  - 由于聚合关系建立在抽象层，要求开发者对抽象层进行设计和编程，增加了系统的设计和理解难度。
  
#### 结构与实现
  1. 抽象化角色(Abstraction)：定义抽象类，并包含一个对实现化对象的引用。
  ```java
   public abstract class Abstraction {
   
       // 子类需要继承，所以是protected
       protected Implementor implementor;
   
       public Abstraction(Implementor implementor){
           this.implementor = implementor;
       }
   
       abstract void doThings();
   
   }
  ```
  2. 扩展抽象化角色(Refined Abstraction)：抽象化角色的子类，实现父类中的业务方法，并通过组合关系调用实现化角色中的业务方法。
  ```java
    public class RefinedAbstraction extends Abstraction {
        public RefinedAbstraction(Implementor implementor) {
            super(implementor);
        }
    
        @Override
        void doThings() {
            System.out.println("具体实现化角色");
            implementor.doSomething();
        }
    }
  ```
  3. 实现化角色(Implementor)：定义实现化角色的接口，供扩展实现化角色调用。
  ```java
     public interface Implementor {
        
            public void doSomething();
        }
  ```
  4. 具体实现化角色(Concrete Implementor)：给出实现化角色接口的具体实现。
  ```java
    public class ConcreteImplementor implements Implementor {
            @Override
            public void doSomething() {
                System.out.println("扩展抽象化角色");
            }
        }
  ```

## 九、装饰模式(Decorator)
#### 定义
> 在不改变现有对象结构的情况下，动态的给对象增加一些职责的模式。它属于对象结构型模式。

#### 优点和缺点
**优点：**
- 采用装饰模式扩展对象的功能比采用继承方式更加灵活。
- 可以设计出多个不同的具体装饰类，创造出多个不同行为的组合。
**缺点：**
- 装饰模式增加了很多子类，如果过度使用会使程序变得很复杂。

#### 结构与应用
  1. 抽象构件(Component)：定义一个抽象接口以规范准备接收附加责任的对象。
  ```java
    public interface Component {
        public void doSomething();
    }
  ```
  2. 具体构件(Concrete Component)：实现抽象构建，通过装饰角色为其添加一些职责。
  ```java
    public class ConcreteComponent implements Component {
        @Override
        public void doSomething() {
            System.out.println("具体构件的方法");
        }
    }
  ```
  3. 抽象装饰角色(Decorator)：继承抽象构建，并包含具体构建的实例。可以通过其子类扩展构件的功能。
  ```java
    public abstract class Decorator implements Component {
        private Component component;
    
        public Decorator(Component component){
            this.component = component;
        }
    
        @Override
        public void doSomething() {
            component.doSomething();
        }
    }
  ```
  4. 具体装饰角色(Concrete Decorator)：实现抽象装饰的相关方法，并给具体构件对象添加附加的责任。
  ```java
    public class ConcreteDecorator extends Decorator {
        public ConcreteDecorator(Component component) {
            super(component);
        }
    
        @Override
        public void doSomething() {
            super.doSomething();
            addFunction();
        }
    
        private void addFunction() {
            System.out.println("附加功能!");
        }
    }
  ```

## 十、外观模式(Facade)
#### 定义
> 通过为多个复杂的子系统提供一个一致的接口，而使这些子系统更加容易访问的模式。该模式对外有一个统一的接口，
>外部应用程序不用关心内部子系统的具体细节，这样会大大降低应用程序的复杂度，提高了程序的可维护性。

#### 优点与缺点
**优点：**
1. 降低了子系统与客户端之间的耦合度，是的子系统的变化不会影响调用它的客户类。
2. 对客户屏蔽了子系统组件，减少了客户处理的对象数目，并使得子系统使用起来更加容易。
3. 降低了大型软件系统中的编译依赖性，简化了系统在不同平台之间的移植过程。

**缺点：**
1. 不能很好的限制客户使用子系统类。
2. 增加新的子系统可能需要修改外观类或客户端的源代码，违反了开闭原则。

#### 结构与应用
  1. 外观角色(Facade)：为多个子系统提供一个统一的接口。
  ```java
    public class Facade {
        private SubSystemA systemA;
        private SubSystemB systemB;
        private SubSystemC systemC;
    
        public Facade(){
            systemA = new SubSystemA();
            systemB = new SubSystemB();
            systemC = new SubSystemC();
        }
    
        public void methodA(){
            systemA.doSomethingA();
        }
    
        public void methodB(){
            systemB.doSomethingB();
        }
    
        public void methodC(){
            systemC.doSomethingC();
        }
    }
  ```
  2. 子系统角色(Sub System)：实现系统的部分功能，客户可以通过外观角色访问它。
  ```java
    class SubSystemA {
    
        public void doSomethingA(){
            System.out.println("子系统方法A");
        }
    }

    class SubSystemB {
    
        public void doSomethingB(){
            System.out.println("子系统方法B");
        }
    }

    class SubSystemC {
    
        public void doSomethingC(){
            System.out.println("子系统方法C");
        }
    }
  ```
  3. 客户角色(Client)：通过一个外观角色访问各个子系统的功能。
  ```java
    public class App {
    
        @Test
        public void testMethod(){
            Facade facade = new Facade();
            facade.methodA();
            facade.methodB();
            facade.methodC();
        }
    }
  ```

## 十一、享元模式(Flyweight)
#### 定义
> 运用共享技术来有效的支持大量细粒度对象的复用。通过共享已经存在的对象来大幅度减少需要创建的对象数量、避免
>大量相似类的开销，从而提高系统资源的利用率。

#### 优点与缺点
 **优点：**
 - 相同对象只要保存一份，这降低了系统中对象的数量，从而降低了系统中细粒度对象给内存带来的压力。
 **缺点：**
 1. 为了使对象可以共享，需要将一些不能共享的状态外部化，增加了程序的复杂度。
 2. 读取享元模式的外部状态会使得运行时间稍微变长。
 
 #### 结构与实现
 **状态**
 1. 内部状态：不会随着环境改变而改变的可共享部分。
 2. 外部状态：指随环境改变而不可共享的部分。
 **结构**
 1.  抽象享元角色(Flyweight)：所有具体享元类的基类，为具体享元规范需要实现的公共接口，非享元外部状态以参数形式通过方法传入。
 ```java
    public interface Flyweight {
        void doSomething(UnsharableFlyweight unsharableFlyweight);
    }
 ```
 2. 具体享元角色(Concrete Flyweight)：实现抽象享元角色中所规定的接口。
 ```java
    public class ConcreteFlyweight implements Flyweight {
    
        private String key;
    
        public ConcreteFlyweight(String key) {
            this.key = key;
        }
    
        @Override
        public void doSomething(UnsharableFlyweight unsharableFlyweight) {
            System.out.println("具体享元");
            System.out.println("非享元信息是："+unsharableFlyweight.getInfo());
        }
    }
 ```
 3. 非享元角色(Unsharable Flyweight)：使不可共享的外部状态，以参数的形式注入具体享元角色中。
 ```java
    public class UnsharableFlyweight {
        private String info;
        public UnsharableFlyweight(String info){
            this.info = info;
        }
    
        public String getInfo(){
            return info;
        }
    
        public void setInfo(){
            this.info = info;
        }
    }
 ```
 4. 享元工厂角色(Flyweight Factory)：负责创建和管理享元角色。
 ```java
    public class FlyweightFactory {
    
        private Map<String, Flyweight> flyweightMap = new HashMap<>();
    
        public Flyweight getFlyweight(String key) {
            Flyweight flyweight = flyweightMap.get(key);
            if (flyweight != null) {
                System.out.println("具体享元对象"+key+"已经存在");
            }else{
                flyweight = new ConcreteFlyweight(key);
                flyweightMap.put(key, flyweight);
            }
    
            return flyweight;
        }
    }
 ```

## 十二、组合模式(Composite)
#### 定义
> 将对象组合成树状层次的结构，用来表示部分及整体的关系，使用户对单个对象以及组合对象具有一致的访问性。

#### 优点与缺点
**优点：**
1. 组合模式可以使客户端可以一致的访问单个对象和组合对象，无需关系自己处理的使单个对象还是组合对象。简化了客户端代码。
2. 更容易在组合体内加入新的对象，客户端不会因为加入新的对象而改变代码，满足开闭原则。
**缺点：**
1. 设计较为复杂，客户端需要更多的时间清理类之间的关系。
2. 不容易限制容器内的构件。
3. 不容易用继承的方法来增加构件的新功能。

#### 结构与实现
1. 抽象构件(Component)：为树叶构件和树枝构件声明公共接口，并实现他们的默认行为。在透明式的组合模式中抽象构件
还声明访问和管理子类的接口；在安全式的组合模式中不声明访问和管理子类的接口，管理工作有树枝构件完成。
```java
public interface Component {
    public void add(Component component);
    public void remove(Component component);
    public Component getChild(int i);
    public void operate();
}
```
2. 树叶构件角色(Leaf)：组合中的叶节点对象，没有子节点，用于实现抽象构件角色中生命的公共接口。
```java
public class Leaf implements Component {
    private String name;

    public Leaf(String name) {
        this.name = name;
    }

    @Override
    public void add(Component component) {}

    @Override
    public void remove(Component component) {}

    @Override
    public Component getChild(int i) {
        return null;
    }

    @Override
    public void operate() {
        System.out.println("树叶"+name+"被访问");
    }
}
```
3. 树枝构件对象(Composite)：组合中的分支节点对象，有子节点。实现了抽象构件角色中声明的接口，主要作用是
存储和管理子部件，通常包含add(), remove(), getChild()等方法。
```java
public class Composite implements Component {

    private List<Component> children = new ArrayList<>();

    @Override
    public void add(Component component) {
        children.add(component);
    }

    @Override
    public void remove(Component component) {
        children.remove(component);
    }

    @Override
    public Component getChild(int i) {
        return children.get(i);
    }

    @Override
    public void operate() {
        for (Component component : children) {
            component.operate();
        }
    }
}
```

## 十三、模板方法模式(Template Method)
#### 定义
> 定义一个操作中的算法骨架，而将算法的一些步骤延迟到子类中，使得子类不改变该算法的结构情况下重定义该算法的某些特定步骤。

#### 优点与缺点
**优点：**
1. 封装了不可变部分，扩展了可变部分。
2. 在父类中提取了公共的部分代码，便于代码复用。
3. 部分方法是由子类实现的，因此子类可以通过扩展方式增加相应的功能，符合开闭原则。
**缺点：**
1. 对每个不同的实现都要定义一个子类，这回导致类的个数增加，系统更加庞大，设计也更加抽象。
2. 父类中的抽象方法由子类实现，子类执行结果会影响父类的结果。这导致一种反向的控制结构，提高了代码阅读的难度。

#### 结构与实现
1. 抽象类(Abstract Class)：负责给出一个算法的轮廓和骨架，它是由一个模板方法和若干基本方法组成。这些方法的定义如下：
    - 模板方法：定义了算法的骨架，按某种顺序调用其包含的基本方法。
    - 基本方法：整个算法中的一个步骤，包含以下几种类型。
        - 抽象方法：在抽象类中声明，具体子类实现。
        - 具体方法：抽象类中实现，具体子类可以继承或者重写。
        - 钩子方法：在抽象类中已经实现，包括用于判断的逻辑方法和子类重写的方法两种。
  ```java
    public abstract class AbstractClass {
    
        public void templateMethod(){
            specificMethod();
            abstractMethodA();
            abstractMethodB();
        }
        // 具体方法
        public void specificMethod() {
            System.out.println("具体方法被调用");
        };
        // 抽象方法1
        public abstract void abstractMethodA();
        // 抽象方法2
        public abstract void abstractMethodB();
    }
  ```

2. 具体子类(Concrete Class)：实现抽象类中所定义的抽象方法和钩子方法，它们是一个顶级逻辑的一个组成部分。
  ```java
    public class ConcreteClass extends AbstractClass {
        @Override
        public void abstractMethodA() {
            System.out.println("抽象类的抽象方法A");
        }
    
        @Override
        public void abstractMethodB() {
            System.out.println("抽象类的抽象方法B");
        }
    }
  ```

## 十四、策略模式(Strategy)
#### 定义
> 定义了一些列算法，并将每个算法封装起来，使他们可以互相替换，且算法的变化不影响使用算法的客户。
> 策略模式属于对象行为模式，通过对算法进行封装，把使用算法的责任和算法的实现分割开来，并委派给不同的对象对这些算法进行管理。

## 优点与缺点
**优点：**
1. 多重条件语句不宜维护，而使用策略模式可以避免使用多重条件语句。
2. 策略模式提供了一系列可供重用的算法族，恰当使用继承可以把算法的公共代码移交到父类中，从而避免了重复的代码。
3. 策略模式可以提供相同行为的不同实现，客户可以根据不同情况使用不同的策略。
4. 策略模式提供了对开闭原则的完美实现，可以在不修改源代码的情况下灵活增加不同的算法。
5. 策略模式把算法使用放到环境类中，而算法的具体实现移交到策略类中，实现了二者的分离。
**缺点：**
1. 客户端必须理解所有算法的区别，以便恰当时机选择恰当的算法。
2. 策略模式造成很多的策略类。

## 结构与实现
1. 抽象策略类(Strategy)：定义一个公共接口，各种不同算法以不同的方式实现这个接口，环境角色使用这个接口调用不同的算法。
```java
public interface TravelStrategy {
    public void travelMode();
}
```
2. 具体策略类(Concrete Strategy)：实现抽象策略定义的接口，提供具体的算法实现。
```java
public class TravelByCar implements TravelStrategy {
    @Override
    public void travelMode() {
        System.out.println("开车去旅行...");
    }
}

public class TravelByPlain implements TravelStrategy {
    @Override
    public void travelMode() {
        System.out.println("坐着飞机去旅行...");
    }
}

public class TravelByTrain implements TravelStrategy {
    @Override
    public void travelMode() {
        System.out.println("坐火车去旅行。。。");
    }
}
```
3. 环境类(Context)：持有一个策略类的引用，最终给客户端调用。
```java
public class Traveler {

    private TravelStrategy travelStrategy;

    public Traveler(TravelStrategy travelStrategy) {
        this.travelStrategy = travelStrategy;
    }

    public void travel(){
        travelStrategy.travelMode();
    }
}
```

## 十五、命令模式(Command)
#### 定义
> 将一个请求封装成一个对象，使发出请求的责任和执行请求的责任分隔开。这样两者之间通过命令对象进行沟通，方便将命令进行储存、传递、调用、增加和管理。

#### 优点与缺点
**优点：**
1. 降低系统的耦合度。命令模式能够将调用操作的对象和实现该操作的对象解耦。
2. 增加或删除命令非常方便。采用命令模式增加和删除命令不会影星其他类。满足开闭原则，对扩展比较灵活。
3，可以实现宏命令。命令模式和组合模式相结合，将多个命令组装成一个组合命令。
4。 方便实现Undo 和 Redo 模式。命令模式可以备忘录模式相结合，实现命令的撤销和恢复。
**缺点：**
- 产生大量的命令类，增加系统的复杂度。

#### 结构与实现
1. 抽象命令类角色(Command)：声明执行命令的接口，拥有执行命令的抽象方法execute()。
```java
public interface Command {
    void execute();
}
```
2. 具体命令类角色(Concrete Command)：是抽象命令角色的具体实现类，拥有接收者对象，并通过调用接收者的功能来完成命令要执行的操作。
```java
public class ConcreteCommand implements Command {

    private Receiver receiver;

    public ConcreteCommand() {
        this.receiver = new Receiver();
    }

    @Override
    public void execute() {
        receiver.action();
    }
}
```
3. 实现者/接收者角色(Receiver)：执行命令功能的相关操作，是具体命令对象业务的具体实现。
```java
public class Receiver {
    public void action() {
        System.out.println("执行Receiver的action方法");
    }
}
```
4. 调用者/请求者角色(Invoker)：是请求的发送者，通常拥有很多的命令对象，并通过访问命令对象来执行相关请求，不直接访问接收者。
```java
public class Invoker {
    private Command command;

    public Invoker(Command command) {
        this.command = command;
    }

    public void call() {
        command.execute();
    }
}
```

## 十六、责任链模式(Chain Of Responsibility)
#### 定义
> 为了避免请求发送与多个请求处理者耦合在一起，将所有对象通过前一对象记住其下一个对象的引用而连成一条链，当有请求发生时，可以连着这条链，直到对象处理完为止，

#### 优点与缺点
**优点：**
1. 降低了对象之间的耦合度。一个对象无需知道是哪一个对象处理其请求以及链的结构，发送者与接收者也无需拥有对方明确的信息。
2. 增强了系统的可扩展性。可以根据需求增加新的请求处理类，满足开闭原则。
3. 增强了给对象指派职责的灵活性。工作流程发生改变是可以动态改变链内的成员顺序或者调动他们的次序，也可以动态的增加或者删除责任。
4. 简化了对象之间的连接。每个对象只需保持一个后继者的引用，而不用关心其他对象。
5. 责任分担，每个对象秩序处理自己该做的工作，不该处理的留给下一个对象。
**缺点：**
1. 不能保证每个请求一定被处理。由于一个请求没有明确的接收者，所以不一定能保证其被处理，该请求可能传递到末端链都得不到处理。
2. 对比较长的责任链，请求的处理可能涉及到多个处理对象，系统性能将收到一定影响。
3. 责任链的合理性需要客户端来保证，增加了客户端的复杂性，可能会因为职责链设置错误而导致系统出错，也可能会循环调用。

#### 结构与实现
1. 抽象处理者角色(Handle)：定义一个处理请求的接口，包括抽象处理方法和一个后继链接。
```java
public abstract class Handler {
    private Handler next;

    public Handler getNext() {
        return next;
    }

    public void setNext(Handler next) {
        this.next = next;
    }

    public abstract void handleRequest(String request);
}
```
2. 具体处理者角色(Concrete Handle)：实现抽象处理者的处理方法，判断是否本次处理，如果可以请求则处理，否则将请求转给后续处理者。
```java
public class ConcreteHandlerA extends Handler {
    @Override
    public void handleRequest(String request) {
        if (request.equals("first")) {
            System.out.println("具体处理者A负责处理");
        } else if (getNext() != null) {
            getNext().handleRequest(request);
        } else {
            System.out.println("没有人处理");
        }
    }
}

public class ConcreteHandlerB extends Handler {
    @Override
    public void handleRequest(String request) {
        if (request.equals("second")) {
            System.out.println("具体处理者B负责处理");
        } else if (getNext() != null) {
            getNext().handleRequest(request);
        } else {
            System.out.println("没有人处理");
        }
    }
}
```
3. 客户类角色(Client)：创建处理链，并向链头的具体处理者提交请求，他不关心处理细节和请求传递。
```java
public class Client {
    public void invokeChain() {
        Handler handlerA = new ConcreteHandlerA();
        Handler handlerB = new ConcreteHandlerB();

        handlerA.setNext(handlerB);

        handlerA.handleRequest("second");
    }
}
```

## 十七、状态模式
#### 定义
> 对有状态的对象，把复杂的判断逻辑提取到不同的状态对象中，允许状态对象在内部发生改变时改变其行为。

#### 优点与缺点
**优点：**
1. 状态模式将与特定状态相关的行为局部化到一个状态中。并且将不同状态的行为分割开来，满足单一职责原则。
2. 减少对象间的相互依赖。将不同状态引入独立的对象中会使得状态转换变得更加明确，且减少对象间相互转换。
3. 有利于程序的扩展。通过定义新的子类很容易增加新的状态和转换。
**缺点：**
1. 增加系统类以及对象个数。
2. 结构与实现较为复杂，使用不当会引起结构和代码的混乱。
#### 结构与实现
1. 环境角色(Context)：也成为上下文，定义了客户感兴趣的接口，维护一个当前状态，并将于状态相关的操作委托给当前的状态对象来处理。
```java
public class Context {
    private State state;

    public Context(State state) {
        this.state = state;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
        System.out.println("当前状态为"+state.name);
    }

    public void request() {
        state.handle(this); //对请求做处理并指向下一个状态
    }
}
```
2. 抽象状态角色(State)：定义一个接口，用来封装环境对象中特定状态对应的行为。
```java
public abstract class State {
    public String name;

    public abstract void handle(Context context);
}
```
3. 具体状态角色(Concrete State)：实现抽象状态所对应的行为。
```java

public class ConcreteStateA extends State {

    public ConcreteStateA() {
        this.name = "A";
    }

    @Override
    public void handle(Context context) {
        System.out.println(this.name);
        context.setState(new ConcreteStateB());
    }
}

public class ConcreteStateB extends State {

    public ConcreteStateB() {
        this.name = "B";
    }

    @Override
    public void handle(Context context) {
        System.out.println("");
        context.setState(new ConcreteStateA());
    }
}

```

## 十八、 观察者模式(Observer)
#### 定义
> 多个对象间存在一对多依赖关系，当一个对象的状态发生变化时， 所有依赖它的对象都得到通知并自动更新。
> 观察者模式又称为发布订阅模式、模型、视图模式。

#### 优点与缺点
**优点：**
1. 降低了目标与观察者之间的耦合关系。
2. 目标与观察者之间建立了一套触发机制。
**缺点：**
1. 目标与观察者之间的依赖关系并没有完全消除，而且有可能发生循环引用。
2. 当观察者对象很多时，通知的发布会花费很多时间，影响程序的效率。

#### 结构与实现
1. 抽象主题角色(Subject)：也叫抽象目标类，提供了一个用于保存观察者对象的聚集类和增加、删除观察者对象的方法。以及所有通知观察者的抽象方法。
```java
public abstract class Subject {
    protected List<Observer> observers = new ArrayList<>();

    // 添加观察者
    public void add(Observer observer) {
        observers.add(observer);
    }
    // 移除观察者
    public void remove(Observer observer) {
        observers.remove(observer);
    }

    public abstract void notifyObserver();
}
```
2. 具体主题角色(Concrete Subject)：也叫具体目标类，实现抽象目标中的通知方法，当具体主题发生变化时，通知所有注册过的观察者对象。
```java
public class ConcreteSubject extends Subject {
    @Override
    public void notifyObserver() {
        System.out.println("目标发生改变，调用观察者");
        for (Observer observer : observers) {
            observer.response();
        }
    }
}
```
3. 抽象观察者角色(Observer)：它是一个抽象类的接口，包含一个更新自己的方法，且接到具体主题角色的通知时被调用。
```java
public interface Observer {
    void response();
}
```
4. 具体观察者角色(Concrete Observer)：实现抽象观察者定义的抽象方法，以便在目标得到更改时更新自身的状态。
```java
public class ConcreteObserverA implements Observer {
    @Override
    public void response() {
        System.out.println("观察者A作出响应");
    }
}

public class ConcreteObserverB implements Observer {
    @Override
    public void response() {
        System.out.println("观察者B作出响应");
    }
}
```

## 十九、中介者模式(Mediator)
#### 定义
> 定义一个中介来封装一些列对象之间的交互，使原有对象之间的耦合松散，且可以独立改变他们之间的交互。

#### 优点与缺点
**优点：**
1. 降低了对象之间的耦合，使得对象易于独立的复用。
2. 将对象中的一对多关联变为一对多关联，提高系统的灵活性，使得系统更加容易维护。
**缺点：**
- 当同事类过多的情况下，中介者的职责会变得负责而庞大，以致于系统难以维护。

#### 结构与实现
1. 抽象中介者(Mediator)：中介者的接口，提供了同事对象的注册与转发同事对象的抽象方法。
```java
public interface Mediator {
    void register(Colleague colleague);
    void relay(Colleague colleague);
}
```
2. 具体中介者(Concrete Mediator)：实现抽象中介者的接口。定义一个List来管理同事对象，协调各个同事之间的相互交互，因此依赖于同事角色。
```java
public class ConcreteMedia implements Mediator {

    private Set<Colleague> colleagues = new HashSet<>();

    @Override
    public void register(Colleague colleague) {
        colleagues.add(colleague);
        colleague.setMediator(this);
    }

    @Override
    public void relay(Colleague col) {
        for (Colleague colleague : colleagues) {
            if(!col.equals(colleague)) {
                colleague.receive();
            }
        }
    }
}
```
3. 抽象同事类(Colleague)：定义同事类接口，保存中介者对象，提供同事对象相互交互的抽象方法，实现所有相互影响的同事类的所有功能。
```java
public abstract class Colleague {
    protected Mediator mediator;

    public void setMediator(Mediator mediator) {
        this.mediator = mediator;
    }

    public abstract void receive();

    public abstract void send();
}
```
4. 具体同事类(Concrete Colleague)：抽象同事类的实现者，当需要与其他同事对象交互时，由中介者对象负责后续的交互。
```java
public class ConcreteColleagueA extends Colleague {
    @Override
    public void receive() {
        System.out.println("同事类A接收到请求");
    }

    @Override
    public void send() {
        System.out.println("同事类A发送请求");
        mediator.relay(this);
    }
} 

public class ConcreteColleagueB extends Colleague {
    @Override
    public void receive() {
        System.out.println("同事类B接收请求");
    }

    @Override
    public void send() {
        System.out.println("同事类B发送请求");
        mediator.relay(this);
    }
}
```

## 二十、迭代器模式(Iterator)
#### 定义
> 提供一个对象来顺序访问聚合对象中的一系列数据，而不暴漏聚合对象的内部表示。

#### 优点与缺点
**优点：**
1. 访问一个聚合对象无需暴漏内部表示。
2. 遍历任务交给迭代器完成，这简化了聚合类。
3. 它支持以不同方式遍历一个集合，甚至可以自定义迭代器的子类以支持新的遍历。
4. 增加新的聚合类和迭代器类都很方便，无需修改原有代码。
5. 封装性良好，为遍历不同的集合提供一个统一的接口。
**缺点：**
- 增加类的个数，这在一定程度上增加了系统的复杂性。

#### 结构与实现
1. 抽象聚合角色(Aggregate)：定义存储、添加、删除聚合对象以及创建迭代器对象的接口。
```java
public interface Aggregate {
    void add(Object obj);
    void remove(Object obj);
    public Iterator getIterator();
}
```
2. 具体聚合角色(Concrete Aggregate)：实现抽象聚合类，返回一个具体迭代器的实例。
```java
public class ConcreteAggregate implements Aggregate {

    List<Object> list = new ArrayList<>();

    @Override
    public void add(Object obj) {
        list.add(obj);
    }

    @Override
    public void remove(Object obj) {
        list.remove(obj);
    }

    @Override
    public Iterator getIterator() {
        return new ConcreteIterator(list);
    }
}
```
3. 抽象迭代器角色(Iterator)：定义访问和遍历聚合元素的接口，通常包含hasNext()、first()、next()等方法。
```java
public interface Iterator {
    Object first();
    Object next();
    boolean hasNext();
}
```
4. 具体迭代器角色(Concrete Iterator)：实际抽象迭代器中所定义的方法，完成对聚合对象的遍历，记录遍历的当前位置。
```java
public class ConcreteIterator implements Iterator {

    private List<Object> list = null;
    private int index = -1;

    public ConcreteIterator(List<Object> list) {
        this.list = list;
    }

    @Override
    public Object first() {
        index=0;
        Object obj=list.get(index);;
        return obj;
    }

    @Override
    public Object next() {
        Object obj=null;
        if(this.hasNext())
        {
            obj=list.get(++index);
        }
        return obj;
    }

    @Override
    public boolean hasNext() {
        if(index<list.size()-1) {
            return true;
        } else {
            return false;
        }
    }
}
```

## 二十一、访问者模式(Visitor)
#### 定义
> 将作用于某种数据结构中的各元素的操作分离出来封装成独立的类，使其在不改变数据结构的前提下可以添加作用于这些元素的新的操作，为数据结构中每个元素提供更多的访问方式。

#### 优点与缺点
**优点：**
1. 扩展性好。能够在不修改对象结构的元素的情况下，为对象结构中的元素添加新的功能。
2. 复用性好。可以通过访问者来定义整个对象结构通用功能，从而提高系统的复用程度。
3. 灵活性好。访问者模式将数据结构与作用于结构上的操作解耦，使得操作集合可相对自由的演化而不影响系统的数据结构。
4. 符合单一职责原则。访问者模式把相关行为封装在一起，构成一个访问者，使得每个访问者功能都比较单一。
**缺点：**
1. 增加新的元素类很困难。在访问者模式中，每增加一个新的元素类，都要在每一个具体的访问类中增加相应的具体操作，这违背了“开闭原则”。
2. 破坏封装。访问者模式中具体元素对访问者公布细节，这破坏了对象的封装性。
3. 违背了依赖倒置原则。访问者模式以来了具体类，而没有依赖抽象类。

#### 结构与实现
1. 抽象访问者角色(Visitor)：定义一个访问具体元素的接口，为每一个具体元素类对应一个访问操作visit()，该操作中的参数类型表示了被访问的具体元素。
```java
public interface Visitor {
    void visit(ConcreteElementA elementA);
    void visit(ConcreteElementB elementB);
}
```
2. 具体访问者角色(Concrete Visitor)：实现抽象访问者角色中声明的各个访问操作，确定访问者访问一个元素时该做什么。
```java
public class ConcreteVisitorA implements Visitor {
    @Override
    public void visit(ConcreteElementA elementA) {
        System.out.println("具体访问者A-->"+elementA.operationA());
    }

    @Override
    public void visit(ConcreteElementB elementB) {
        System.out.println("具体访问者A-->"+elementB.operationA());
    }
}

public class ConcreteVisitorB implements Visitor {
    @Override
    public void visit(ConcreteElementA elementA) {
        System.out.println("具体访问者B-->"+elementA.operationA());
    }

    @Override
    public void visit(ConcreteElementB elementB) {
        System.out.println("具体访问者B-->"+elementB.operationA());
    }
}
```
3. 抽象元素角色(Element)：声明一个包含接收accept()的接口，被接收的访问者对象作为accept()方法的参数。
```java
public interface Element {
    void accept(Visitor visitor);
}
```
4. 具体元素角色(Concrete Element)：实现抽象元素角色提供的accept()操作，其方法通常是visitor.visit(this)，具体操作元素中可能还包含本身业务逻辑的相关操作。
```java
public class ConcreteElementA implements Element {
    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public String operationA() {
        return "具体元素A的操作";
    }
}

public class ConcreteElementB implements Element {
    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
    public String operationA() {
        return "具体元素B的操作";
    }
}
```
5. 对象结构角色(Object Structure)：是一个包含元素角色的容器，提供让访问者对象遍历容器中所有元素的方法，通常由List、Map、Set等聚合类实现。
```java
public class ObjectStructure {
    private List<Element> list = new ArrayList<Element>();

    public void accept(Visitor visitor) {
        Iterator<Element> i = list.iterator();
        while (i.hasNext()) {
            ((Element) i.next()).accept(visitor);
        }
    }

    public void add(Element element) {
        list.add(element);
    }

    public void remove(Element element) {
        list.remove(element);
    }
}
```

## 二十二、备忘录模式(Memento)
#### 定义
> 在不破换封装的前提下，捕获一个对象的内部状态，并在该对象之外保存这个对象，以便在之后使用的时候恢复到原先使用的状态。

#### 优点与缺点
**优点：**
1. 提供了一种可恢复状态的机制。当用户需要的时候能够方便的将状态恢复到某个历史值。
2。 实现了内部状态的封装，除了创建它的发起人之外，其他对象都不能访问这些状态信息。
3. 简化了发起人类。发起人不需要管理和保存其内部状态，所有状态均保存在备忘录中，并由管理者进行管理，这符合单一职责原理。
**缺点：**
- 资源消耗过大。如果需要保存的内部状态比较多或者比较繁琐，将会占用大量的资源。

#### 结构与实现
1. 发起人角色(Originator)：记录当前时刻的内部状态信息，提供创建备忘录和恢复备忘录数据的功能，实现其他业务功能，可以访问备忘录里的所有信息。
```java
public class Originator {
    private String state;

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Memento createMemento() {
        return new Memento(state);
    }

    public void restoreMemento(Memento memento) {
        this.setState(memento.getState());
    }
}
```
2。 备忘录角色(Memento)：负责存储发起人的内部信息，在需要的时候提供给状态的发起人。
```java
public class Memento {

    private String state;

    public Memento(String state) {
        this.state = state;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
```
3. 管理者角色(Caretaker)：对备忘录进行管理，提供保存与获取备忘录的功能，但其不能对备忘录的内容和功能进行访问与修改。
```java
public class Cretaker {
    private Memento memento;

    public Memento getMemento() {
        return memento;
    }

    public void setMemento(Memento memento) {
        this.memento = memento;
    }
}
```

## 二十三、解释器模式(Interpreter)
#### 定义
> 给分析对象定义一个语言，并定义该语言的文法表示，再设计一个解析器来解析语言中的句子。也就是说，用编译器语言的方式来分析应用中的实例。
> 这种模式实现了文法表达式处理的接口，该接口解释一个特定的上下文。

#### 优点与缺点
**优点：**
1. 扩展性好。由于解释器是使用类来表示语言的文法规则，因此可以同科继承等机制来改变或扩展文法。
2. 容易实现。在语法数中的每个表达式节点都是相似的，所以实现其文法比较容易。
**缺点：**
1. 执行效率低。解释器中通常使用大量的循环或递归调用，当要解释的句子比较复杂时，其运行速度很慢，且代码的调试也比较麻烦。
2. 会引起类膨胀。解释器模式中每条规则至少定义一个类，当包含文法规则很多时，类的个数将急剧增加，导致系统难以管理和维护。
3. 可应用场景比较少。在软件开发中，需要定义语言文法的应用实例非常少，所以这种模式很少被使用到。

#### 结构与实现
1. 抽象表达式（Abstract Expression）角色：定义解释器的接口，约定解释器的解释操作，主要包含解释方法 interpret()。
2. 终结符表达式（Terminal    Expression）角色：是抽象表达式的子类，用来实现文法中与终结符相关的操作，文法中的每一个终结符都有一个具体终结表达式与之相对应。
3. 非终结符表达式（Nonterminal Expression）角色：也是抽象表达式的子类，用来实现文法中与非终结符相关的操作，文法中的每条规则都对应于一个非终结符表达式。
4. 环境（Context）角色：通常包含各个解释器需要的数据或是公共的功能，一般用来传递被所有解释器共享的数据，后面的解释器可以从这里获取这些值。
5. 客户端（Client）：主要任务是将需要分析的句子或表达式转换成使用解释器对象描述的抽象语法树，然后调用解释器的解释方法，当然也可以通过环境角色间接访问解释器的解释方法。

