package com.eniola.addressbookapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentTransaction
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.eniola.addressbookapp.R
import com.eniola.addressbookapp.repository.data.Contact
import com.eniola.addressbookapp.utility.UPDATE_CONTACT
import com.eniola.addressbookapp.utility.hide
import com.eniola.addressbookapp.utility.show
import com.eniola.addressbookapp.utility.toast
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.fragment_contact_list.*
import javax.inject.Inject

class ContactListFragment : DaggerFragment(), ContactListAdapter.OnEditIconClickedListener,
    CreateContactFragment.RefetchAllContact {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val viewModel by viewModels<ContactViewModel> { viewModelFactory }
    private val adapter by lazy { ContactListAdapter(this) }


    private fun observeData() {
        viewModel.state.observe(viewLifecycleOwner) { state ->
            when(state) {
                is ViewState.FETCHCONTACT -> {
                    loader.hide()
                    //pass contact list to recyclerview
                    adapter.setListItem(state.allContact)
                    contact_list_recyclerview.layoutManager = LinearLayoutManager(context,
                        LinearLayoutManager.VERTICAL, false)
                    contact_list_recyclerview.adapter = adapter
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


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_contact_list, container, false)
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            ContactListFragment()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //fetch all contacts from database and show in recyclerview
        viewModel.fetchAllContacts()

        create_contact_fab.setOnClickListener {
            //launch dialog fragment that shows create contact page
            val createContactFragment = CreateContactFragment(this)
            val fragmentTransaction: FragmentTransaction = childFragmentManager.beginTransaction()
            fragmentTransaction.add(createContactFragment, CreateContactFragment::class.java.name)
            fragmentTransaction.commit()

        }

        observeData()
    }

    override fun editContact(contactDetails: Contact) {
        //inflate create contact fragment and perform update
        val bundle = Bundle()
        bundle.putParcelable("contactItem", contactDetails)
        bundle.putString("source", UPDATE_CONTACT)

        val createContactFragment = CreateContactFragment(this)
        createContactFragment.arguments = bundle
        val fragmentTransaction: FragmentTransaction = childFragmentManager.beginTransaction()
        fragmentTransaction.add(createContactFragment, CreateContactFragment::class.java.name)
        fragmentTransaction.commit()
    }

    override fun reFetchContacts() {
        viewModel.fetchAllContacts()
    }


}