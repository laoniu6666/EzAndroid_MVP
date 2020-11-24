package com.laoniu.ezandroid.utils.other;
 
import java.util.concurrent.TimeUnit;
 
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

public class RxTimer {
 
    /**
     * milliseconds毫秒后执行指定动作
     *
     * @param milliSeconds
     * @param rxAction
     */
    public Disposable timer(long milliSeconds, final RxAction rxAction) {
        return Observable.timer(milliSeconds, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long time){
                        if(null!=rxAction){
                            rxAction.action(time);
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable){
                        if(null!=rxAction){
                            rxAction.action(-1);
                        }
                    }
                });
    }
 
    /**
     * 每隔milliseconds毫秒后执行指定动作
     *
     * @param milliSeconds
     * @param rxAction
     */
    public void interval(long milliSeconds, final RxAction rxAction) {
        Observable.interval(milliSeconds, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long time){
                        if(null!=rxAction){
                            rxAction.action(time);
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable){
                        if(null!=rxAction){
                            rxAction.action(-1);
                        }
                    }
                });
    }
 

    public interface RxAction {
        /**
         * 让调用者指定指定动作
         *
         * @param number
         */
        void action(long number);
    }
}