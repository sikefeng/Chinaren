
package com.sikefeng.chinaren;

import com.sikefeng.chinaren.core.ServiceHelper;
import com.sikefeng.chinaren.entity.model.UserBean;
import com.sikefeng.chinaren.entity.model.UserData;
import com.sikefeng.chinaren.presenter.LoginPresenter;
import com.sikefeng.chinaren.presenter.vm.LoginViewModel;
import com.sikefeng.mvpvmlib.base.IRBaseView;
import com.sikefeng.mvpvmlib.utils.LogUtils;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import io.reactivex.android.plugins.RxAndroidPlugins;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;


public class LoginPresenterTest {

    @Mock
    IRBaseView irBaseView;

    @Mock
    LoginPresenter presenter;

    @Mock
    LoginViewModel loginViewModel;

    @BeforeClass
    public static void setUpClass() {

        // Override the default "out of the box" AndroidSchedulers.mainThread() Scheduler
        //
        // This is necessary here because otherwise if the static initialization block in AndroidSchedulers
        // is executed before this, then the Android SDK dependent version will be provided instead.
        //
        // This would cause a java.lang.ExceptionInInitializerError when running the test as a
        // Java JUnit test as any attempt to resolve the default underlying implementation of the
        // AndroidSchedulers.mainThread() will fail as it relies on unavailable Android dependencies.

        // Comment out this line to see the java.lang.ExceptionInInitializerError
//        RxAndroidPlugins.setInitMainThreadSchedulerHandler(__ -> Schedulers.trampoline());
    }

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        presenter = new LoginPresenter(irBaseView, loginViewModel);
    }

    @AfterClass
    public static void tearDownClass() {
        // Not strictly necessary because we can't reset the value set by setInitMainThreadSchedulerHandler,
        // but it doesn't hurt to clean up anyway.
        RxAndroidPlugins.reset();
    }

    @Test
    public void bind_setsFavoriteBookTitle_whenGetFavoriteBookEmits() {
        UserBean userBean = new UserBean();
        userBean.setLoginName("aason");

        ServiceHelper.getUsersAS().login(userBean)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<UserData>() {
                    @Override
                    public void onNext(UserData value) {
                        LogUtils.d("=====>> "+ value.getData().getName());
                        System.out.println("OK");
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        System.out.println(e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                        System.out.println("complte");
                    }
                });
    }

}
