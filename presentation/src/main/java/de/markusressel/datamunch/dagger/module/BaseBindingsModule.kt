package de.markusressel.datamunch.dagger.module

import dagger.Module
import dagger.android.ContributesAndroidInjector
import de.markusressel.datamunch.view.fragment.LockscreenFragment
import de.markusressel.datamunch.view.fragment.PluginsFragment
import de.markusressel.datamunch.view.fragment.ServerStatusFragment
import de.markusressel.datamunch.view.fragment.ServicesFragment
import de.markusressel.datamunch.view.fragment.account.group.GroupDetailActivity
import de.markusressel.datamunch.view.fragment.account.group.GroupDetailContentFragment
import de.markusressel.datamunch.view.fragment.account.group.GroupsFragment
import de.markusressel.datamunch.view.fragment.account.user.UserDetailActivity
import de.markusressel.datamunch.view.fragment.account.user.UserDetailContentFragment
import de.markusressel.datamunch.view.fragment.account.user.UsersFragment
import de.markusressel.datamunch.view.fragment.base.DaggerSupportFragmentBase
import de.markusressel.datamunch.view.fragment.jail.MountpointsFragment
import de.markusressel.datamunch.view.fragment.jail.TemplatesFragment
import de.markusressel.datamunch.view.fragment.jail.jail.*
import de.markusressel.datamunch.view.fragment.pages.*
import de.markusressel.datamunch.view.fragment.sharing.AfpSharesFragment
import de.markusressel.datamunch.view.fragment.sharing.CifsSharesFragment
import de.markusressel.datamunch.view.fragment.sharing.NfsSharesFragment
import de.markusressel.datamunch.view.fragment.storage.*
import de.markusressel.datamunch.view.fragment.system.AlertsFragment
import de.markusressel.datamunch.view.fragment.system.MaintenanceFragment
import de.markusressel.datamunch.view.fragment.system.UpdatesFragment
import de.markusressel.datamunch.view.fragment.tasks.SMARTTasksFragment

/**
 * Created by Markus on 07.01.2018.
 */
@Module
abstract class BaseBindingsModule {

    @ContributesAndroidInjector
    internal abstract fun DaggerSupportFragmentBase(): DaggerSupportFragmentBase

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
    internal abstract fun ScrubsFragment(): ScrubsFragment

    @ContributesAndroidInjector
    internal abstract fun SnapshotsFragment(): SnapshotsFragment

    @ContributesAndroidInjector
    internal abstract fun TasksFragment(): TasksFragment

    @ContributesAndroidInjector
    internal abstract fun TasksPage(): TasksPage

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

    @ContributesAndroidInjector
    internal abstract fun MaintenanceFragment(): MaintenanceFragment

    @ContributesAndroidInjector
    internal abstract fun LockscreenFragment(): LockscreenFragment

    @ContributesAndroidInjector
    internal abstract fun AccountPage(): AccountPage

    @ContributesAndroidInjector
    internal abstract fun FileUploaderPage(): FileUploaderPage

    @ContributesAndroidInjector
    internal abstract fun JailPage(): JailPage

    @ContributesAndroidInjector
    internal abstract fun PluginPage(): PluginPage

    @ContributesAndroidInjector
    internal abstract fun StoragePage(): StoragePage

    @ContributesAndroidInjector
    internal abstract fun SharingPage(): SharingPage

    @ContributesAndroidInjector
    internal abstract fun SystemPage(): SystemPage

    @ContributesAndroidInjector
    internal abstract fun UserDetailActivity(): UserDetailActivity

    @ContributesAndroidInjector
    internal abstract fun UserDetailContentFragment(): UserDetailContentFragment

    @ContributesAndroidInjector
    internal abstract fun GroupDetailActivity(): GroupDetailActivity

    @ContributesAndroidInjector
    internal abstract fun GroupDetailContentFragment(): GroupDetailContentFragment

    @ContributesAndroidInjector
    internal abstract fun JailDetailActivity(): JailDetailActivity

    @ContributesAndroidInjector
    internal abstract fun JailDetailContentFragment(): JailDetailContentFragment

    @ContributesAndroidInjector
    internal abstract fun JailServicesContentFragment(): JailServicesContentFragment

    @ContributesAndroidInjector
    internal abstract fun JailShellContentFragment(): JailShellContentFragment

    @ContributesAndroidInjector
    internal abstract fun SMARTTasksFragment(): SMARTTasksFragment

}