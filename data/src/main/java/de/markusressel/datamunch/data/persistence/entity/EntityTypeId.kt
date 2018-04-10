package de.markusressel.datamunch.data.persistence.entity

enum class EntityTypeId(val id: Long) {
    User(0),
    Group(1),
    Jail(2),
    Mountpoint(3),
    Template(4),
    Plugin(5),
    Service(6),
    AfpShare(7),
    CifsShare(8),
    NfsShare(9),
    Volume(10),
    Dataset(11),
    Disk(12),
    Scrub(13),
    Snapshot(14),
    Task(15),
    Alert(16),
    Update(17),
    SMARTTask(18)
}