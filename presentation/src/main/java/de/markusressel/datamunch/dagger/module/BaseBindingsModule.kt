package de.markusressel.datamunch.dagger.module

import dagger.Module
import dagger.android.ContributesAndroidInjector
import de.markusressel.datamunch.view.fragment.PluginsFragment
import de.markusressel.datamunch.view.fragment.ServerStatusFragment
import de.markusressel.datamunch.view.fragment.ServicesFragment
import de.markusressel.datamunch.view.fragment.account.GroupsFragment
import de.markusressel.datamunch.view.fragment.account.UsersFragment
import de.markusressel.datamunch.view.fragment.base.DaggerSupportFragmentBase
import de.markusressel.datamunch.view.fragment.base.LoadingSupportFragmentBase
import de.markusressel.datamunch.view.fragment.jail.JailsFragment
import de.markusressel.datamunch.view.fragment.jail.MountpointsFragment
import de.markusressel.datamunch.view.fragment.jail.TemplatesFragment
import de.markusressel.datamunch.view.fragment.sharing.AfpSharesFragment
import de.markusressel.datamunch.view.fragment.sharing.CifsSharesFragment
import de.markusressel.datamunch.view.fragment.sharing.NfsSharesFragment
import de.markusressel.datamunch.view.fragment.storage.DatasetsFragment
import de.markusressel.datamunch.view.fragment.storage.DisksFragment
import de.markusressel.datamunch.view.fragment.storage.SnapshotsFragment
import de.markusressel.datamunch.view.fragment.storage.VolumesFragment
import de.markusressel.datamunch.view.fragment.system.AlertsFragment
import de.markusressel.datamunch.view.fragment.system.UpdatesFragment

/**
 * Created by Markus on 07.01.2018.
 */
@Module
abstract class BaseBindingsModule {

    @ContributesAndroidInjector
    internal abstract fun DaggerSupportFragmentBase(): DaggerSupportFragmentBase

    @ContributesAndroidInjector
    internal abstract fun LoadingSupportFragmentBase(): LoadingSupportFragmentBase

    @ContributesAndroidInjector
    internal abstract fun ServerStatusFragment(): ServerStatusFragment

    @ContributesAndroidInjector
    internal abstract fun UsersFragment(): UsersFragment

    @ContributesAndroidInjector
    internal abstract fun GroupsFragment(): GroupsFragment

    @ContributesAndroidInjector
    internal abstract fun ServicesFragment(): ServicesFragment

    @ContributesAndroidInjector
    internal abstract fun VolumesFragment(): VolumesFragment

    @ContributesAndroidInjector
    internal abstract fun DatasetsFragment(): DatasetsFragment

    @ContributesAndroidInjector
    internal abstract fun DisksFragment(): DisksFragment

    @ContributesAndroidInjector
    internal abstract fun SnapshotsFragment(): SnapshotsFragment

    @ContributesAndroidInjector
    internal abstract fun JailsFragment(): JailsFragment

    @ContributesAndroidInjector
    internal abstract fun MountpointsFragment(): MountpointsFragment

    @ContributesAndroidInjector
    internal abstract fun TemplatesFragment(): TemplatesFragment

    @ContributesAndroidInjector
    internal abstract fun PluginsFragment(): PluginsFragment

    @ContributesAndroidInjector
    internal abstract fun AfpSharesFragment(): AfpSharesFragment

    @ContributesAndroidInjector
    internal abstract fun CifsSharesFragment(): CifsSharesFragment

    @ContributesAndroidInjector
    internal abstract fun NfsSharesFragment(): NfsSharesFragment

    @ContributesAndroidInjector
    internal abstract fun UpdatesFragment(): UpdatesFragment

    @ContributesAndroidInjector
    internal abstract fun AlertsFragment(): AlertsFragment

}