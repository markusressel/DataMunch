/*
 * DataMunch by Markus Ressel
 * Copyright (c) 2018.
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package de.markusressel.datamunch.dagger.module

import dagger.Module
import dagger.android.ContributesAndroidInjector
import de.markusressel.datamunch.view.fragment.LockscreenFragment
import de.markusressel.datamunch.view.fragment.ServerStatusFragment
import de.markusressel.datamunch.view.fragment.account.group.GroupDetailActivity
import de.markusressel.datamunch.view.fragment.account.group.GroupDetailContentFragment
import de.markusressel.datamunch.view.fragment.account.group.GroupsFragment
import de.markusressel.datamunch.view.fragment.account.user.UserDetailActivity
import de.markusressel.datamunch.view.fragment.account.user.UserDetailContentFragment
import de.markusressel.datamunch.view.fragment.account.user.UsersFragment
import de.markusressel.datamunch.view.fragment.base.DaggerSupportFragmentBase
import de.markusressel.datamunch.view.fragment.base.SortOptionSelectionDialog
import de.markusressel.datamunch.view.fragment.jail.jail.*
import de.markusressel.datamunch.view.fragment.jail.mountpoint.MountpointDetailActivity
import de.markusressel.datamunch.view.fragment.jail.mountpoint.MountpointDetailContentFragment
import de.markusressel.datamunch.view.fragment.jail.mountpoint.MountpointsFragment
import de.markusressel.datamunch.view.fragment.jail.template.TemplateDetailActivity
import de.markusressel.datamunch.view.fragment.jail.template.TemplateDetailContentFragment
import de.markusressel.datamunch.view.fragment.jail.template.TemplatesFragment
import de.markusressel.datamunch.view.fragment.pages.*
import de.markusressel.datamunch.view.fragment.plugins.PluginDetailActivity
import de.markusressel.datamunch.view.fragment.plugins.PluginDetailContentFragment
import de.markusressel.datamunch.view.fragment.plugins.PluginsFragment
import de.markusressel.datamunch.view.fragment.services.ServiceDetailActivity
import de.markusressel.datamunch.view.fragment.services.ServiceDetailContentFragment
import de.markusressel.datamunch.view.fragment.services.ServicesFragment
import de.markusressel.datamunch.view.fragment.sharing.afp.AfpShareDetailActivity
import de.markusressel.datamunch.view.fragment.sharing.afp.AfpShareDetailContentFragment
import de.markusressel.datamunch.view.fragment.sharing.afp.AfpSharesFragment
import de.markusressel.datamunch.view.fragment.sharing.cifs.CifsShareDetailActivity
import de.markusressel.datamunch.view.fragment.sharing.cifs.CifsShareDetailContentFragment
import de.markusressel.datamunch.view.fragment.sharing.cifs.CifsSharesFragment
import de.markusressel.datamunch.view.fragment.sharing.nfs.NfsShareDetailActivity
import de.markusressel.datamunch.view.fragment.sharing.nfs.NfsShareDetailContentFragment
import de.markusressel.datamunch.view.fragment.sharing.nfs.NfsSharesFragment
import de.markusressel.datamunch.view.fragment.storage.dataset.DatasetDetailActivity
import de.markusressel.datamunch.view.fragment.storage.dataset.DatasetDetailContentFragment
import de.markusressel.datamunch.view.fragment.storage.dataset.DatasetsFragment
import de.markusressel.datamunch.view.fragment.storage.disk.DiskDetailActivity
import de.markusressel.datamunch.view.fragment.storage.disk.DiskDetailContentFragment
import de.markusressel.datamunch.view.fragment.storage.disk.DisksFragment
import de.markusressel.datamunch.view.fragment.storage.scrubs.ScrubDetailActivity
import de.markusressel.datamunch.view.fragment.storage.scrubs.ScrubDetailContentFragment
import de.markusressel.datamunch.view.fragment.storage.scrubs.ScrubsFragment
import de.markusressel.datamunch.view.fragment.storage.snapshot.SnapshotDetailActivity
import de.markusressel.datamunch.view.fragment.storage.snapshot.SnapshotDetailContentFragment
import de.markusressel.datamunch.view.fragment.storage.snapshot.SnapshotsFragment
import de.markusressel.datamunch.view.fragment.storage.task.TaskDetailActivity
import de.markusressel.datamunch.view.fragment.storage.task.TaskDetailContentFragment
import de.markusressel.datamunch.view.fragment.storage.task.TasksFragment
import de.markusressel.datamunch.view.fragment.storage.volume.VolumesFragment
import de.markusressel.datamunch.view.fragment.system.MaintenanceFragment
import de.markusressel.datamunch.view.fragment.system.alert.AlertDetailActivity
import de.markusressel.datamunch.view.fragment.system.alert.AlertDetailContentFragment
import de.markusressel.datamunch.view.fragment.system.alert.AlertsFragment
import de.markusressel.datamunch.view.fragment.system.update.UpdatesFragment
import de.markusressel.datamunch.view.fragment.tasks.SMARTTaskDetailActivity
import de.markusressel.datamunch.view.fragment.tasks.SMARTTaskDetailContentFragment
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
    internal abstract fun AboutPage(): AboutPage

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
    internal abstract fun DiskDetailActivity(): DiskDetailActivity

    @ContributesAndroidInjector
    internal abstract fun DiskDetailContentFragment(): DiskDetailContentFragment

    @ContributesAndroidInjector
    internal abstract fun TaskDetailActivity(): TaskDetailActivity

    @ContributesAndroidInjector
    internal abstract fun TaskDetailContentFragment(): TaskDetailContentFragment

    @ContributesAndroidInjector
    internal abstract fun SnapshotDetailActivity(): SnapshotDetailActivity

    @ContributesAndroidInjector
    internal abstract fun SnapshotDetailContentFragment(): SnapshotDetailContentFragment

    @ContributesAndroidInjector
    internal abstract fun ScrubDetailActivity(): ScrubDetailActivity

    @ContributesAndroidInjector
    internal abstract fun AlertDetailActivity(): AlertDetailActivity

    @ContributesAndroidInjector
    internal abstract fun AlertDetailContentFragment(): AlertDetailContentFragment

    @ContributesAndroidInjector
    internal abstract fun ScrubDetailContentFragment(): ScrubDetailContentFragment

    @ContributesAndroidInjector
    internal abstract fun JailDetailContentFragment(): JailDetailContentFragment

    @ContributesAndroidInjector
    internal abstract fun JailServicesContentFragment(): JailServicesContentFragment

    @ContributesAndroidInjector
    internal abstract fun JailShellContentFragment(): JailShellContentFragment

    @ContributesAndroidInjector
    internal abstract fun SMARTTasksFragment(): SMARTTasksFragment

    @ContributesAndroidInjector
    internal abstract fun AfpShareDetailActivity(): AfpShareDetailActivity

    @ContributesAndroidInjector
    internal abstract fun AfpShareDetailContentFragment(): AfpShareDetailContentFragment


    @ContributesAndroidInjector
    internal abstract fun CifsShareDetailActivity(): CifsShareDetailActivity

    @ContributesAndroidInjector
    internal abstract fun CifsShareDetailContentFragment(): CifsShareDetailContentFragment


    @ContributesAndroidInjector
    internal abstract fun NfsShareDetailActivity(): NfsShareDetailActivity

    @ContributesAndroidInjector
    internal abstract fun NfsShareDetailContentFragment(): NfsShareDetailContentFragment


    @ContributesAndroidInjector
    internal abstract fun PluginDetailActivity(): PluginDetailActivity

    @ContributesAndroidInjector
    internal abstract fun PluginDetailContentFragment(): PluginDetailContentFragment

    @ContributesAndroidInjector
    internal abstract fun MountpointDetailActivity(): MountpointDetailActivity

    @ContributesAndroidInjector
    internal abstract fun MountpointDetailContentFragment(): MountpointDetailContentFragment

    @ContributesAndroidInjector
    internal abstract fun TemplateDetailActivity(): TemplateDetailActivity

    @ContributesAndroidInjector
    internal abstract fun TemplateDetailContentFragment(): TemplateDetailContentFragment

    @ContributesAndroidInjector
    internal abstract fun DatasetDetailActivity(): DatasetDetailActivity

    @ContributesAndroidInjector
    internal abstract fun DatasetDetailContentFragment(): DatasetDetailContentFragment

    @ContributesAndroidInjector
    internal abstract fun ServiceDetailActivity(): ServiceDetailActivity

    @ContributesAndroidInjector
    internal abstract fun ServiceDetailContentFragment(): ServiceDetailContentFragment

    @ContributesAndroidInjector
    internal abstract fun SMARTTaskDetailActivity(): SMARTTaskDetailActivity

    @ContributesAndroidInjector
    internal abstract fun SMARTTaskDetailContentFragment(): SMARTTaskDetailContentFragment

    @ContributesAndroidInjector
    internal abstract fun SortOptionSelectionDialog(): SortOptionSelectionDialog

}
