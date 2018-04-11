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