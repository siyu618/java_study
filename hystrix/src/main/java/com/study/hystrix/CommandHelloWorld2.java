package com.study.hystrix;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixObservable;
import com.netflix.hystrix.HystrixObservableCommand;
import rx.Observable;
import rx.Subscriber;

/**
 * Created by tianyuzhi on 17/9/18.
 */
public class CommandHelloWorld2 extends HystrixObservableCommand<String> {
    private String name;
    protected CommandHelloWorld2(String name) {
        super(HystrixCommandGroupKey.Factory.asKey("ExampleGroup"));
        this.name = name;
    }

    @Override
    protected Observable<String> construct() {
        return Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                try {
                    if (!subscriber.isUnsubscribed()) {
                        subscriber.onNext("Hello ");
                        subscriber.onNext(name + "!");
                        subscriber.onCompleted();
                    }
                } catch (Exception e) {
                    subscriber.onError(e);
                }
            }
        });
    }
}
