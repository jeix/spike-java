//package org.simple.demo;

import lombok.Getter;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class XyzTest {

    public static void main(String[] args) {
        log.info("--------------------");
        P1.let_me_try();
        log.info("--------------------");
        P1R1.let_me_try();
        log.info("--------------------");
        P1R3.let_me_try();
        log.info("--------------------");
        //int path = (int) Math.ceil(Math.random() * 2);
        //log.info("{}", path);
        //if (path == 1)
        P1R2.let_me_try();
        log.info("--------------------");
        //if (path == 2)
        P1R4.let_me_try();
        log.info("--------------------");
    }

    private static class P1 {
        static void let_me_try() {
            new XWorker().process();
            new YWorker().process();
        }
        private static abstract class AbcWorker {
            protected String foo = "Abc_Foo";
            protected String bar = "Abc_Bar";
            public void process() {
                String name = this.toString();
                log.info("{} {} {}", name, this.foo, this.bar);
                this.walk();
            }
            abstract protected void walk();
        }
        private static class XWorker extends AbcWorker {
            protected String foo = "X_Foo";
            protected String bar = "X_Bar";
            @Override
            protected void walk() {
                log.info("{} walk with {}", this.toString(), this.foo);
            }
        }
        private static class YWorker extends AbcWorker {
            protected String foo = "Y_Foo";
            protected String bar = "Y_Bar";
            @Override
            protected void walk() {
                log.info("{} walk with {}", this.toString(), this.foo);
            }
        }
    }

    private static class P1R1 {
        static void let_me_try() {
            new XWorker().process();
            new YWorker().process();
        }
        @Getter
        @Accessors(fluent = true)
        private static abstract class AbcWorker {
            protected String foo = "Abc_Foo";
            protected String bar = "Abc_Bar";
            public void process() {
                String name = this.toString();
                log.info("{} {} {}", name, this.foo(), this.bar());
                this.walk();
            }
            abstract protected void walk();
        }
        @Getter
        @Accessors(fluent = true)
        private static class XWorker extends AbcWorker {
            protected String foo = "X_Foo";
            protected String bar = "X_Bar";
            @Override
            protected void walk() {
                log.info("{} walk with {}", this.toString(), this.foo);
            }
        }
        @Getter
        @Accessors(fluent = true)
        private static class YWorker extends AbcWorker {
            protected String foo = "Y_Foo";
            protected String bar = "Y_Bar";
            @Override
            protected void walk() {
                log.info("{} walk with {}", this.toString(), this.foo);
            }
        }
    }

    private static class P1R2 {
        static void let_me_try() {
            //new Thread(() -> new XWorker().process()).start();
            //new Thread(() -> new YWorker().process()).start();
            Thread t1 = Thread.ofVirtual().start(() -> new XWorker().process());
            Thread t2 = Thread.ofVirtual().start(() -> new YWorker().process());
            try {
                t1.join();
                t2.join();
            } catch (InterruptedException ie) {
                ie.printStackTrace();
            }
        }
        private static abstract class AbcWorker {
            protected String foo = "Abc_Foo";
            protected String bar = "Abc_Bar";
            public void process() {
                String name = this.toString();
                log.info("{} 1 {} {}", name, this.foo, this.bar);
                this.walk();
                log.info("{} 2 {} {}", name, this.foo, this.bar);
            }
            abstract protected void walk();
        }
        private static class XWorker extends AbcWorker {
            protected String foo = "X_Foo";
            protected String bar = "X_Bar";
            @Override
            protected void walk() {
                log.info("{} walk with {}", this.toString(), this.foo);
                super.foo = this.foo;
                super.bar = this.bar;
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        private static class YWorker extends AbcWorker {
            protected String foo = "Y_Foo";
            protected String bar = "Y_Bar";
            @Override
            protected void walk() {
                log.info("{} walk with {}", this.toString(), this.foo);
                super.foo = this.foo;
                super.bar = this.bar;
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    private static class P1R3 {
        static void let_me_try() {
            new XWorker().process();
            new YWorker().process();
        }
        private static abstract class AbcWorker {
            protected String foo = "Abc_Foo";
            protected String bar = "Abc_Bar";
            public void process() {
                String name = this.toString();
                log.info("{} {} {}", name, this.foo, this.bar);
                this.walk();
            }
            abstract protected void walk();
        }
        private static class XWorker extends AbcWorker {
            public XWorker() {
                super.foo = "X_Foo";
                super.bar = "X_Bar";
            }
            @Override
            protected void walk() {
                log.info("{} walk with {}", this.toString(), this.foo);
            }
        }
        private static class YWorker extends AbcWorker {
            public YWorker() {
                super.foo = "Y_Foo";
                super.bar = "Y_Bar";
            }
            @Override
            protected void walk() {
                log.info("{} walk with {}", this.toString(), this.foo);
            }
        }
    }

    private static class P1R4 {
        static void let_me_try() {
            //new Thread(() -> new XWorker().process()).start();
            //new Thread(() -> new YWorker().process()).start();
            Thread t1 = Thread.ofVirtual().start(() -> new XWorker().process());
            Thread t2 = Thread.ofVirtual().start(() -> new YWorker().process());
            try {
                t1.join();
                t2.join();
            } catch (InterruptedException ie) {
                ie.printStackTrace();
            }
        }
        private static abstract class AbcWorker {
            protected String foo = "Abc_Foo";
            protected String bar = "Abc_Bar";
            public void process() {
                String name = this.toString();
                log.info("{} 1 {} {}", name, this.foo, this.bar);
                this.walk();
                log.info("{} 2 {} {}", name, this.foo, this.bar);
            }
            abstract protected void walk();
        }
        private static class XWorker extends AbcWorker {
            protected String foo = "X_Foo";
            protected String bar = "X_Bar";
            @Override
            protected void walk() {
                log.info("{} walk with {}", this.toString(), this.foo);
                super.foo = "%s-%s".formatted(this.toString().split("\\$")[2], this.foo);
                super.bar = "%s-%s".formatted(this.toString().split("\\$")[2], this.bar);
                try {
                    Thread.sleep((long) (Math.random() * 1000));
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        private static class YWorker extends AbcWorker {
            protected String foo = "Y_Foo";
            protected String bar = "Y_Bar";
            @Override
            protected void walk() {
                log.info("{} walk with {}", this.toString(), this.foo);
                super.foo = this.foo;
                super.bar = this.bar;
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
}
