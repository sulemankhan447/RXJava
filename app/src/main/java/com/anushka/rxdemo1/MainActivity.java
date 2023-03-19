package com.anushka.rxdemo1;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.observers.DisposableObserver;
import io.reactivex.rxjava3.schedulers.Schedulers;


public class MainActivity extends AppCompatActivity {
    private final static String TAG="myApp";
    private String greeting="Hello From RxJava";

    private Observable<String> myObservable;

    /**
     * Disposable observer is abstract class which implements observer and disposable
     * disposable observer is more efficient than observable if we have more than one observer in our activity/fragment
     *
     */
    private DisposableObserver<String> myObserver;
    private DisposableObserver<String> myObserver2;

    private CompositeDisposable compositeDisposable = new CompositeDisposable();


    private TextView textView;

//    private Disposable disposable;
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
//        myObserver =  new Observer<String>() {
//            @Override
//            public void onSubscribe(@NonNull Disposable d) {
//                /**
//                 * Call when observer successfully subscribes to observable
//                 */
//                Log.i("RxJava","onSubscribe Invoked on "+Thread.currentThread().getName());
//
//                /**
//                 * If user has started screen where we do api call and user
//                 * presses back btn, now activity is detsoryed, but api call is still in progress
//                 * and once api call returns response
//                 * it will try to update view
//                 * as observable thinks, observer is still observing data changed
//                 * but thats not the case, observable does not know that observer is destroyed
//                 * so we have to stop observable from publising data changes to avoid
//                 * memory leak or crash
//                 * this can be done using disposable object
//                 * by using disposable object , we will dispose things
//                 */
//                disposable = d;
//            }
//
//            @Override
//            public void onNext(@NonNull String s) {
//                /**
//                 * Called when data emission is received
//                 */
//                Log.i("RxJava","onNext Invoked on "+Thread.currentThread().getName());
//                textView.setText(s);
//            }
//
//            @Override
//            public void onError(@NonNull Throwable e) {
//                /**
//                 * Call when we receive error during emission of data
//                 */
//                Log.i("RxJava","onError Invoked on "+Thread.currentThread().getName());
//            }
//
//            @Override
//            public void onComplete() {
//                /**
//                 * Called when emission of data is completed
//                 */
//                Log.i("RxJava","onComplete Invoked on "+Thread.currentThread().getName());
//            }
//        };

        myObserver = new DisposableObserver<String>(){
            /**
             * in disposable observer, we dont have
             * onsubscribe method which was used to get disposable object
             * here instead we can direcly use disposable object in on destory
             * refer onDestroy for more details
             */



            @Override
            public void onNext(@NonNull String s) {

            }

            @Override
            public void onError(@NonNull Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        };
        myObserver2 = new DisposableObserver<String>(){
            /**
             * in disposable observer, we dont have
             * onsubscribe method which was used to get disposable object
             * here instead we can direcly use disposable object in on destory
             * refer onDestroy for more details
             */



            @Override
            public void onNext(@NonNull String s) {

                Toast.makeText(getApplicationContext(),s,Toast.LENGTH_LONG).show();

            }

            @Override
            public void onError(@NonNull Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        };

        /**
         * Subscribing observer to observable
         * so observable emits data
         *
         */

        compositeDisposable.add(myObserver);
        myObservable.subscribe(myObserver);
        compositeDisposable.add(myObserver2);
        myObservable.subscribe(myObserver2);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        /**
         * using disposable to terminate the subscription
         */
//        disposable.dispose();

        /**
         * Using Disposable observer for terminating the subscription
         */
//        myObserver.dispose();
        /**
         * If we have many observers to dispose,
         * we have to manyally dispose them each
         * and if they are more,
         * than there is possibility of forgetting them to dispose
         * which can create memory leak or crash
         * so preferred method is composite disposable
         *
         */
//        myObserver2.dispose();
        /**
         * Instead of disposing each disposable observer
         * we can use composite diposable with clear method
         * so it will dispose all observers and also clear container which is containing all disposables
         */

        /**
         * if we use dipose in composite disposable,
         * than it will dispose observables
         * but after that we cannot add any disposables in that
         * so we should use clear for composite disposable
         * as it will dispose and also clear all disposales in composite disposable
         */
        compositeDisposable.clear();
    }
}