package lt.bta.Demo;

import lt.bta.Demo.helpers.EntityManagerHelper;

import javax.persistence.EntityManager;

public class Main {
    public static void main(String[] args) {
//        EntityManager em = EntityManagerHelper.getEntityManager();

        A a = new B();
        System.out.println(a.x);
        System.out.println(a.getX());
    }
}

class A {
    int x = 10;
    int getX(){return 10;}
}

class B extends A {
    int x = 20;
    int getX() {return x;}
}
