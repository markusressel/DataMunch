/*
 *  PowerSwitch by Max Rosin & Markus Ressel
 *  Copyright (C) 2015  Markus Ressel
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

package de.markusressel.datamunch.view.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import de.markusressel.datamunch.R
import de.markusressel.datamunch.butterknife.ButterKnifeViewHolder
import de.markusressel.datamunch.data.entity.Jail
import de.markusressel.datamunch.view.adapter.JailRecyclerViewAdapter.JailViewHolder
import kotterknife.bindView

/**
 * Adapter to visualize Action items in RecyclerView
 *
 *
 * Created by Markus on 04.12.2015.
 */
class JailRecyclerViewAdapter(
        private val context: Context,
        private val jails: List<Jail>) : RecyclerView.Adapter<JailViewHolder>() {

    private var onDeleteClickListener: OnItemClickListener? = null

    fun setOnDeleteClickListener(onItemClickListener: OnItemClickListener) {
        this.onDeleteClickListener = onItemClickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): JailViewHolder {
        val itemView = LayoutInflater.from(context)
                .inflate(R.layout.list_item_jail, parent, false)
        return JailViewHolder(itemView)
    }

    override fun onBindViewHolder(holderJail: JailViewHolder, position: Int) {
        val jail = jails[position]

        holderJail.id.text = jail.id.toString()
        holderJail.name.text = jail.hostname
        holderJail.path.text = jail.path

//        if (position == itemCount - 1) {
//            holderJail.footer.visibility = View.VISIBLE
//        } else {
//            holderJail.footer.visibility = View.GONE
//        }
    }

    // Return the total count of items
    override fun getItemCount(): Int {
        return jails.size
    }

    interface OnItemClickListener {
        fun onItemClick(itemView: View?, position: Int)
    }

    inner class JailViewHolder(itemView: View) : ButterKnifeViewHolder(itemView) {
        internal val id: TextView by bindView(R.id.id)
        internal val name: TextView by bindView(R.id.name)
        internal val path: TextView by bindView(R.id.path)

//        internal var footer: LinearLayout by bindView(R.id.list_footer)

        init {

//            this.delete.setOnClickListener(View.OnClickListener {
//                onDeleteClickListener?.let {
//                    if (adapterPosition === RecyclerView.NO_POSITION) {
//                        return@OnClickListener
//                    }
//                    it.onItemClick(delete, adapterPosition)
//                }
        }
    }
}