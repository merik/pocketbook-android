package com.dmc.pocketbook.dagger;

import com.dmc.pocketbook.data.AppRepository;
import com.dmc.pocketbook.data.remote.AppDataRemoteStore;
import com.dmc.pocketbook.viewmodels.RecentTransactionViewModel;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by chi on 16/1/18.
 */

@Singleton
@Component(modules = {AppModule.class, DataModule.class})
public interface AppComponent {
    void inject(AppDataRemoteStore appDataRemoteStore);
    void inject(AppRepository appRepository);
    void inject(RecentTransactionViewModel recentTransactionViewModel);
}
