package com.eniola.addressbookapp.ui

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.*
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.eniola.addressbookapp.R
import com.eniola.addressbookapp.repository.data.Contact
import com.eniola.addressbookapp.utility.UPDATE_CONTACT
import com.eniola.addressbookapp.utility.hide
import com.eniola.addressbookapp.utility.show
import com.eniola.addressbookapp.utility.toast
import dagger.android.support.DaggerDialogFragment
import kotlinx.android.synthetic.main.fragment_create_contact.*
import javax.inject.Inject

class CreateContactFragment(var listener: RefetchAllContact) : DaggerDialogFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val viewModel by viewModels<ContactViewModel> { viewModelFactory }

    private var contactDetails: Contact? = null
    private var source: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_create_contact, container, false)
    }

    private fun observeData() {
        viewModel.state.observe(viewLifecycleOwner) { state ->
            when(state) {
                is ViewState.SUCCESS -> {
                    loader.hide()
                    activity?.toast(state.message)
                    //re-fetch contact list
                    listener?.reFetchContacts()
                    dialog?.dismiss()
                }

                is ViewState.ERROR -> {
                    loader.hide()
                    activity?.toast(state.errorMessage)
                }

                is ViewState.LOADING -> {
                    if(state.loading) {
                        loader.show()
                    } else {
                        loader.hide()
                    }
                }
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val passedData = arguments
        if(passedData != null){
            contactDetails = passedData.getParcelable("contactItem")
            source = passedData.getString("source")

            if(source == UPDATE_CONTACT) {
                contact_action_text.text = "Update Contact"
                first_name_edit_text.setText(contactDetails?.firstName)
                last_name_edit_text.setText(contactDetails?.lastName)
                email_edit_text.setText(contactDetails?.email)
                phone_number_edit_text.setText(contactDetails?.phoneNumber)
                add_contact_button.text = ("Update Contact")
            }
        }

        cancel_button.setOnClickListener {
            dialog?.dismiss()
        }

        add_contact_button.setOnClickListener {

            //add contact
            val firstName = first_name_edit_text.text.toString()
            val lastName = last_name_edit_text.text.toString()
            val email = email_edit_text.text.toString()
            val phoneNumber = phone_number_edit_text.text.toString()

            //if source is from Update, update details, else, create contact afresh
            if(source == UPDATE_CONTACT){
                contactDetails?.id?.let { it1 ->
                    viewModel.updateContact(it1, firstName, lastName, email, phoneNumber) }
            } else {
                viewModel.createNewContact(firstName, lastName, email, phoneNumber)
            }
        }

        observeData()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val height = resources.getDimensionPixelSize(R.dimen.add_contact_card_view_height)
        dialog?.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, height)
        dialog?.window?.setGravity(Gravity.BOTTOM)
        dialog?.window?.clearFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    }

    interface RefetchAllContact {
        fun reFetchContacts()
    }

}