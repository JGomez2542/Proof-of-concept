package com.example.jasongomez.rxjavapoc;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private TextView tvEmissions;
    private Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvEmissions = findViewById(R.id.tvEmissions);
        handler = new Handler(Looper.getMainLooper());

    }

    /**
     * Combines the emissions of two observables. The concat operator waits for one observable to complete before emitting values from the second.
     * http://reactivex.io/documentation/operators/concat.html
     *
     * @param view
     */

    @SuppressLint("CheckResult")
    public void onStartConcat(View view) {
        Observable<Integer> observable1 = Observable.just(1, 2, 3, 4);
        Observable<Integer> observable2 = Observable.just(5, 6, 7, 8);

        Observable.concat(observable1, observable2)
                .subscribeOn(Schedulers.io())
                .toList()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(integerList -> new Thread(() -> {
                    for (Integer integer : integerList) {
                        try {
                            Thread.sleep(1000);
                            handler.post(() -> tvEmissions.setText(String.valueOf(integer)));
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }).start(), Throwable::printStackTrace);
    }

    /**
     * Combines the emission of two observables. The merge operator may interleave the emissions of both observables. Order is not guaranteed.
     * http://reactivex.io/documentation/operators/merge.html
     *
     * @param view
     */

    @SuppressLint("CheckResult")
    public void onStartMerge(View view) {
        Observable<Integer> observable1 = Observable.just(1, 2, 3, 4);
        Observable<Integer> observable2 = Observable.just(5, 6, 7, 8);

        Observable.merge(observable1, observable2)
                .subscribeOn(Schedulers.io())
                .toList()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(integerList -> new Thread(() -> {
                    for (Integer integer : integerList) {
                        try {
                            Thread.sleep(1000);
                            handler.post(() -> tvEmissions.setText(String.valueOf(integer)));
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }).start(), Throwable::printStackTrace);
    }

    /**
     * Combines the emissions of two observables, then emits them one by one after a 1 second delay. ConcatMap maintains the order of the emitted values.
     * http://reactivex.io/documentation/operators/concat.html
     *
     * @param view
     */

    @SuppressLint("CheckResult")
    public void onStartSwitchMap(View view) {
        Observable<Integer> observable1 = Observable.just(1, 2, 3, 4);
        Observable<Integer> observable2 = Observable.just(5, 6, 7, 8);

        Observable.concat(observable1, observable2)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .concatMap(integer -> Observable.just(integer).delay(1, TimeUnit.SECONDS))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(integer -> tvEmissions.setText(String.valueOf(integer)),
                        Throwable::printStackTrace,
                        () -> Toast.makeText(this, "Completed", Toast.LENGTH_SHORT).show(),
                        disposable -> {
                        });
    }

    /**
     * Combines the emissions of two observables, then emits them one by one after a 1 second delay. This is very similar to concatMap above, but may result
     * in interleaved values. Order is not guaranteed.
     * http://reactivex.io/documentation/operators/flatmap.html
     *
     * @param view
     */

    @SuppressLint("CheckResult")
    public void onStartFlatMap(View view) {
        Observable<Integer> observable1 = Observable.just(1, 2, 3, 4);
        Observable<Integer> observable2 = Observable.just(5, 6, 7, 8);

        Observable.concat(observable1, observable2)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .flatMap(Observable::just)
                .zipWith(Observable.interval(1, TimeUnit.SECONDS), (n, i) -> n)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(integer -> tvEmissions.setText(String.valueOf(integer)),
                        Throwable::printStackTrace,
                        () -> Toast.makeText(this, "Completed", Toast.LENGTH_SHORT).show(),
                        disposable -> {
                        });
    }

    /**
     * Instantiates two Person objects each with a list of friends, emitting each friend downstream to the observer.
     * http://reactivex.io/documentation/operators/flatmap.html
     *
     * @param view
     */

    @SuppressLint("CheckResult")
    public void onStartFlatMapList(View view) {
        List<String> person1Friends = new ArrayList<>();
        List<String> person2Friends = new ArrayList<>();
        List<Person> personList = new ArrayList<>();

        person1Friends.add("jason");
        person1Friends.add("robby");
        person1Friends.add("jones");
        person1Friends.add("david");

        person2Friends.add("larry");
        person2Friends.add("bobby");
        person2Friends.add("jerry");
        person2Friends.add("yoMomma");

        personList.add(new Person(person1Friends, "Janice"));
        personList.add(new Person(person2Friends, "Felicia"));

        Observable.fromIterable(personList)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .flatMap(person -> Observable.fromIterable(person.getFriends()))
                .observeOn(AndroidSchedulers.mainThread())
                .toList()
                .subscribe(friendList -> {
                    StringBuilder sb = new StringBuilder();
                    for (String friend : friendList) {
                        sb.append(friend);
                        sb.append(", ");
                    }
                    tvEmissions.setText(sb.toString());
                }, Throwable::printStackTrace);
    }

    /**
     * Filters the emissions from the source observable, and then maps each value that passes the predicate test to a string appended with "mapped".
     * http://reactivex.io/documentation/operators/filter.html
     * http://reactivex.io/documentation/operators/map.html
     *
     * @param view
     */

    @SuppressLint("CheckResult")
    public void onStartFilterAndMap(View view) {
        String[] alphabet = {"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z"};
        Observable.fromArray(alphabet)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .filter(letter -> letter.equals("a") || letter.equals("b") || letter.equals("c"))
                .map(letter -> letter + " mapped")
                .toList()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(list -> {
                    StringBuilder sb = new StringBuilder();
                    for (String emission : list) {
                        sb.append(emission);
                        sb.append(" ");
                    }
                    tvEmissions.setText(sb.toString());
                }, Throwable::printStackTrace);
    }


    /**
     * Emits a random integer. If the integer is not divisible by 3, the retry operator resubscribes to the source observable.
     * http://reactivex.io/documentation/operators/retry.html
     *
     * @param view
     */

    @SuppressLint("CheckResult")
    public void onStartRetry(View view) {

        Observable.create(emitter -> {
            Random random = new Random();
            int randomInt = random.nextInt(50) + 1;

            if (randomInt % 3 != 0 && !emitter.isDisposed()) {
                emitter.onError(new Throwable("Number " + randomInt + " is not divisible by 3"));
            } else {
                emitter.onNext(randomInt);
                emitter.onComplete();
            }

        }).subscribeOn(Schedulers.io())
                .doOnError(error -> tvEmissions.setText(error.getMessage()))
                .observeOn(Schedulers.io())
                .delay(1, TimeUnit.SECONDS)
                .retry()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(integer -> tvEmissions.setText(String.valueOf(integer)),
                        Throwable::printStackTrace,
                        () -> Toast.makeText(this, "Completed!", Toast.LENGTH_SHORT).show(),
                        disposable -> {
                        });

    }

    /**
     * Emits a random integer. If the integer is not divisible by 3, then we resubscribe three times before giving up. This is just like the retry operator, the only
     * difference being that we can specify our own retry logic.
     * https://blog.danlew.net/2016/01/25/rxjavas-repeatwhen-and-retrywhen-explained/
     *
     * @param view
     */

    @SuppressLint("CheckResult")
    public void onStartRetryWhen(View view) {

        Observable.fromCallable(() -> {
            Random random = new Random();
            int randomInt = random.nextInt(50) + 1;
            if (randomInt % 3 != 0) {
                throw new Exception("Number " + randomInt + " is not divisible by 3");
            } else {
                return randomInt;
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .retryWhen(errors -> errors.zipWith(Observable.range(1, 3), (Throwable n, Integer i) -> {
                    tvEmissions.setText(n.getMessage());
                    if (i == 3) {
                        Thread.sleep(1000);
                        throw Exceptions.propagate(n);
                    } else {
                        return i;
                    }
                }).delay(1, TimeUnit.SECONDS))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(integer -> tvEmissions.setText(String.valueOf(integer)),
                        error -> tvEmissions.setText("Failed"),
                        () -> Toast.makeText(this, "Completed!", Toast.LENGTH_SHORT).show(),
                        disposable -> {
                        });

    }
}
