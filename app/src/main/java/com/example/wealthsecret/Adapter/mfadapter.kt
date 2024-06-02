package com.example.wealthsecret.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.wealthsecret.API.model.Large
import com.example.wealthsecret.R

class mfadapter(val list:List<Large>,val context: Context):RecyclerView.Adapter<mfadapter.vh>() {
    class vh(view:View):RecyclerView.ViewHolder(view) {
        val fundname:TextView
        val y3return:TextView
        val schemecat:TextView
        val fundhouse:TextView
        val month:TextView
        val year:TextView
        val year3:TextView
        val cnav:TextView
 init {

     fundname=view.findViewById(R.id.fundname)
     y3return=view.findViewById(R.id.y3return)
     schemecat=view.findViewById(R.id.schemecategory)
     fundhouse=view.findViewById(R.id.fundhouse)
     month=view.findViewById(R.id.month)
     year=view.findViewById(R.id.year)
     year3=view.findViewById(R.id.year3)
     cnav=view.findViewById(R.id.currentnav)

 }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): mfadapter.vh {
        val view = LayoutInflater.from(context).inflate(R.layout.mutualfundview,parent,false)
        return vh(view)
    }

    override fun onBindViewHolder(holder: mfadapter.vh, position: Int) {
         val data=list.get(position)
        holder.fundname.text=data.meta.scheme_name
        holder.y3return.text= data.returns._year_return3.toString()+"%"
        holder.schemecat.text=data.meta.scheme_category
        holder.fundhouse.text=data.meta.fund_house
        holder.month.text= data.returns._month_return.toString()+"%"
        holder.year.text= data.returns._year_return.toString()+"%"
        holder.year3.text= data.returns._year_return3.toString()+"%"
        holder.cnav.text="Rs. "+data.data.get(0).nav

    }

    override fun getItemCount(): Int {
     return list.size
    }
}