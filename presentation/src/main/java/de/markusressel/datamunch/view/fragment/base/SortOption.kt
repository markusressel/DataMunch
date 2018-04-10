package de.markusressel.datamunch.view.fragment.base

import android.support.annotation.StringRes
import de.markusressel.datamunch.R
import de.markusressel.datamunch.data.persistence.entity.*

/**
 * Class specifying a single SortOption for a list view
 */
data class SortOption<in T : Any>(val id: Long, @StringRes val name: Int,
                                  val selector: (T) -> Comparable<*>?,
                                  var reversed: Boolean = false) {

    companion object {

        val ALERT_ENTITY_DISMISSED = createSortOption<AlertEntity>(
                1,
                R.string.dismissed,
                {
                    it
                            .dismissed
                })

        val ALERT_ENTITY_LEVEL = createSortOption<AlertEntity>(
                2,
                R.string.level,
                {
                    it
                            .level
                            .toLowerCase()
                })
        val ALERT_ENTITY_MESSAGE = createSortOption<AlertEntity>(
                3,
                R.string.message,
                {
                    it
                            .message
                            .toLowerCase()
                })

        val DISK_ENTITY_NAME = createSortOption<DiskEntity>(
                4,
                R.string.name,
                {
                    it
                            .disk_name
                            .toLowerCase()
                })

        val SERVICE_ENTITY_NAME = createSortOption<ServiceEntity>(
                5,
                R.string.name,
                {
                    it
                            .srv_service
                            .toLowerCase()
                })

        val SMART_TASK_TYPE = createSortOption<SMARTTaskEntity>(
                6,
                R.string.type,
                {
                    it
                            .smarttest_type
                            .toLowerCase()
                })

        val SMART_TASK_DESCRIPTION = createSortOption<SMARTTaskEntity>(
                7,
                R.string.description,
                {
                    it
                            .smarttest_desc
                            .toLowerCase()
                })

        val PLUGIN_NAME = createSortOption<PluginEntity>(
                8,
                R.string.name,
                {
                    it
                            .plugin_name
                            .toLowerCase()
                })

        val VOLUME_NAME = createSortOption<VolumeEntity>(
                9,
                R.string.name,
                {
                    it
                            .name
                            .toLowerCase()
                })

        val UPDATE_NAME = createSortOption<UpdateEntity>(
                10,
                R.string.name,
                {
                    it
                            .name
                            .toLowerCase()
                })

        val MOUNTPOINT_ID = createSortOption<MountpointEntity>(
                11,
                R.string.id,
                {
                    it
                            .id
                })

        val JAIL_NAME = createSortOption<JailEntity>(
                12,
                R.string.name,
                {
                    it
                            .jail_host
                            .toLowerCase()
                })

        val TEMPLATE_ID = createSortOption<TemplateEntity>(
                13,
                R.string.id,
                {
                    it
                            .id
                })

        val USER_NAME = createSortOption<UserEntity>(
                14,
                R.string.name,
                {
                    it
                            .bsdusr_username
                            .toLowerCase()
                })

        val GROUP_NAME = createSortOption<GroupEntity>(
                15,
                R.string.name,
                {
                    it
                            .bsdgrp_group
                            .toLowerCase()
                })

        val AFP_SHARE_NAME = createSortOption<AfpShareEntity>(
                16,
                R.string.name,
                {
                    it
                            .afp_name
                            .toLowerCase()
                })

        val SNAPSHOT_NAME = createSortOption<SnapshotEntity>(
                17,
                R.string.name,
                {
                    it
                            .name
                            .toLowerCase()
                })

        val TASK_FILESYSTEM = createSortOption<TaskEntity>(
                18,
                R.string.filesystem,
                {
                    it
                            .task_filesystem
                            .toLowerCase()
                })

        val SCRUB_VOLUME = createSortOption<ScrubEntity>(
                19,
                R.string.volume,
                {
                    it
                            .scrub_volume
                            .toLowerCase()
                })

        val CIFS_SHARE_NAME = createSortOption<CifsShareEntity>(
                20,
                R.string.name,
                {
                    it
                            .cifs_name
                            .toLowerCase()
                })

        val NFS_SHARE_ID = createSortOption<NfsShareEntity>(
                21,
                R.string.id,
                {
                    it
                            .id
                })

        val DATASET_NAME = createSortOption<DatasetEntity>(
                22,
                R.string.name,
                {
                    it
                            .name
                            .toLowerCase()
                })

        /**
         * Create SortOption from id
         */
        fun from(id: Long): SortOption<*> {
            return setOf(
                    ALERT_ENTITY_DISMISSED, ALERT_ENTITY_LEVEL, ALERT_ENTITY_MESSAGE,
                    DISK_ENTITY_NAME,
                    SERVICE_ENTITY_NAME,
                    SMART_TASK_TYPE, SMART_TASK_DESCRIPTION, PLUGIN_NAME,
                    VOLUME_NAME,
                    UPDATE_NAME,
                    MOUNTPOINT_ID,
                    JAIL_NAME,
                    TEMPLATE_ID,
                    USER_NAME,
                    GROUP_NAME,
                    AFP_SHARE_NAME,
                    SNAPSHOT_NAME,
                    TASK_FILESYSTEM,
                    SCRUB_VOLUME,
                    CIFS_SHARE_NAME,
                    NFS_SHARE_ID,
                    DATASET_NAME
            )
                    .first { it.id == id }
        }

        /**
         * Helper method to easily create a type safe SortOption
         */
        private fun <T : Any> createSortOption(id: Long, @StringRes name: Int,
                                               selector: (T) -> Comparable<*>?): SortOption<T> {

            return SortOption(id, name, selector)
        }
    }

}

fun <T : Any> SortOption<T>.toEntity(type: Long): SortOptionEntity {
    return SortOptionEntity(0, this.id, type, this.reversed)
}

