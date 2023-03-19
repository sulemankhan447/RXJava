package com.anushka.rxdemo1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;


public class MainActivity extends AppCompatActivity {
    private final static String TAG="myApp";
    private String greeting="Hello From RxJava";

    private Observable<String> myObservable;
    private Observer<String> myObserver;


    private TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView=findViewById(R.id.tvGreeting);
        myObservable = Observable.just(greeting);
        /***
         * Observable emits data if there is atleast
         * one observer observing it
         *
         */

        /**
         * by writing this,
         * we are telling rx java to do subscription i.e receiving data or some other operator work
         * on background thread
         * so all computation of operators will be done on io thread
         */
        /**
         * Schedulers basically manages thread pool which has one or more threads
         * when we request schedule to work with some thread, it picks that thread from thread pool
         * and does work for us
         */
        myObservable.subscribeOn(Schedulers.io());

        /**
         * once all work is done of operators, our observer will receive data emission
         * so that emission we need on ui thread which is main thread
         * so we are telling rx java to observe on main thread
         */
        myObservable.observeOn(AndroidSchedulers.mainThread());
        myObserver =  new Observer<String>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {
                /**
                 * Call when observer successfully subscribes to observable
                 */
                Log.i("RxJava","onSubscribe Invoked on "+Thread.currentThread().getName());
            }

            @Override
            public void onNext(@NonNull String s) {
                /**
                 * Called when data emission is received
                 */
                Log.i("RxJava","onNext Invoked on "+Thread.currentThread().getName());
                textView.setText(s);
            }

            @Override
            public void onError(@NonNull Throwable e) {
                /**
                 * Call when we receive error during emission of data
                 */
                Log.i("RxJava","onError Invoked on "+Thread.currentThread().getName());
            }

            @Override
            public void onComplete() {
                /**
                 * Called when emission of data is completed
                 */
                Log.i("RxJava","onComplete Invoked on "+Thread.currentThread().getName());
            }
        };

        /**
         * Subscribing observer to observable
         * so observable emits data
         *
         */

        myObservable.subscribe(myObserver);

    }
}