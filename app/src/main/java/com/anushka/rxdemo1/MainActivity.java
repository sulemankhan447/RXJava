package com.anushka.rxdemo1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;


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
        myObserver =  new Observer<String>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {
                /**
                 * Call when observer successfully subscribes to observable
                 */
                Log.i("RxJava","onSubscribe Invoked");
            }

            @Override
            public void onNext(@NonNull String s) {
                /**
                 * Called when data emission is received
                 */
                Log.i("RxJava","onNext Invoked");
                textView.setText(s);
            }

            @Override
            public void onError(@NonNull Throwable e) {
                /**
                 * Call when we receive error during emission of data
                 */
                Log.i("RxJava","onError Invoked");
            }

            @Override
            public void onComplete() {
                /**
                 * Called when emission of data is completed
                 */
                Log.i("RxJava","onComplete Invoked");
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