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

package de.markusressel.datamunch.butterknife;

import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

/**
 * Base class for a RecyclerView.ViewHolder backed by ButterKnife
 * <p>
 * Created by Markus on 30.06.2017.
 */

public abstract class ButterKnifeViewHolder extends RecyclerView.ViewHolder {

    public ButterKnifeViewHolder(View itemView) {
        super(itemView);
//        ButterKnife.bind(this, itemView);
    }

}
