package com.eniola.addressbookapp.ui

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.eniola.addressbookapp.R
import com.eniola.addressbookapp.repository.data.Contact
import kotlinx.android.synthetic.main.item_contacts_list.view.*


class ContactListAdapter
    : RecyclerView.Adapter<ContactListAdapter.TransactionViewHolder>() {

    private var userContact = mutableListOf<Contact>()

    fun setListItem(contact: List<Contact>){
        userContact.clear()
        userContact.addAll(contact)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransactionViewHolder {
        return TransactionViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_contacts_list, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return userContact.size
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: TransactionViewHolder, position: Int) {
        val item = userContact[position]
        //holder.initials.text = item.title.toUpperCase()
        holder.contactEmail.text = item.email
        holder.contactPhoneNumber.text = item.phoneNumber
        holder.contactName.text = """${item.firstName} ${item.lastName}"""

    }

    class TransactionViewHolder(view: View)
        : RecyclerView.ViewHolder(view) {
        val initials: TextView = view.contact_initials
        val contactName = view.contact_name
        val contactEmail = view.contact_email
        val contactPhoneNumber = view.contact_phone_number
        val userItem = view.contactItem
    }

}