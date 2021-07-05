package piotr.celowski.shopperapp.application.dependencyinjection.activitymodule

import android.app.Activity
import android.view.LayoutInflater
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

@Module
@InstallIn(ActivityComponent::class)
abstract class ActivityModule {
    companion object {
        @Provides
        fun layoutInflater(activity: Activity) = LayoutInflater.from(activity)

    }
}