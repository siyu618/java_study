package com.study.hystrix;

import org.testng.annotations.Test;
import rx.Observable;
import rx.Observer;
import rx.functions.Action1;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;


/**
 * Created by tianyuzhi on 17/9/19.
 */
public class CommandHelloWorldTest {
    @Test
    public void testSynchronous() {
        String str = new CommandHelloWorld("World").execute();
        assert "Hello World!".equals(new CommandHelloWorld("World").execute());
        assert "Hello Bob!".equals(new CommandHelloWorld("Bob").execute());

    }

    @Test
    public void testAsynchronous1() throws ExecutionException, InterruptedException {
        Future<String> fs = new CommandHelloWorld("World").queue();
        String str = fs.get();
        assert "Hello World!".equals(new CommandHelloWorld("World").queue().get());
        assert "Hello Bob!".equals(new CommandHelloWorld("Bob").queue().get());

    }

    @Test
    public void testObservable() {
        Observable<String> fWorld = new CommandHelloWorld("World").toObservable();
        Observable<String> fBob = new CommandHelloWorld("Bob").toObservable();

        assert "Hello World!".equals(fWorld.toBlocking().single());
        assert "Hello Bob!".equals(fBob.toBlocking().single());


        fWorld = new CommandHelloWorld("World").toObservable();
        fBob = new CommandHelloWorld("Bob").toObservable();
        fWorld.subscribe(new Observer<String>() {
            @Override
            public void onCompleted() {
                //NONE
            }

            @Override
            public void onError(Throwable throwable) {
                throwable.printStackTrace();
            }

            @Override
            public void onNext(String s) {
                System.out.println("onNext:" + s);
            }
        });

        fBob.subscribe(new Action1<String>() {
            @Override
            public void call(String s) {
                System.out.println("onNext:" + s);
            }
        });
    }

}