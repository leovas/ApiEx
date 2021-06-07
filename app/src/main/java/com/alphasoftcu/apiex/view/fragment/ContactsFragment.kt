package com.alphasoftcu.apiex.view.fragment

import android.Manifest
import android.app.AlertDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.provider.ContactsContract
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.CursorAdapter
import android.widget.PopupMenu
import android.widget.SimpleCursorAdapter
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.loader.app.LoaderManager
import androidx.loader.content.CursorLoader
import androidx.loader.content.Loader
import com.alphasoftcu.apiex.R
import com.alphasoftcu.apiex.databinding.FragmentContactsBinding


class ContactsFragment :
    Fragment(),
    LoaderManager.LoaderCallbacks<Cursor>,
    AdapterView.OnItemClickListener {

    //Define de View Binding variables
    private var _binding: FragmentContactsBinding? = null
    private val binding get() = _binding!!


    // Define variables for the selected contact
    // The contact's _ID value
    var contactId: Long = 0

    // The contact's LOOKUP_KEY
    var contactKey: String? = null

    // A content URI for the selected contact
    var contactUri: Uri? = null

    // The contact display name
    var contactName: String = ""

    // An adapter that binds the result Cursor to the ListView
    private var cursorAdapter: SimpleCursorAdapter? = null

    // Define global mutable variables
    private val FROM_COLUMNS: Array<String> =
        arrayOf(ContactsContract.Contacts.DISPLAY_NAME_PRIMARY)
    private val TO_IDS: IntArray = intArrayOf(R.id.tv_contactlist_item_name)

    //Projection of columns for query
    private val PROJECTION: Array<out String> = arrayOf(
        ContactsContract.Contacts._ID,
        ContactsContract.Contacts.LOOKUP_KEY,
        ContactsContract.Contacts.DISPLAY_NAME_PRIMARY
    )

    //Sort rows in the query by DISPLAY NAME
    val sortOrder = "${ContactsContract.Contacts.DISPLAY_NAME_PRIMARY} ASC"

    //Permissions request manager
    val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissionResultMap ->
        permissionResultMap[Manifest.permission.READ_CONTACTS]?.also {
            if (it) {
                onPermissionGranted()
            } else {
                binding?.btContactsAllowPermission.visibility = View.VISIBLE
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentContactsBinding.inflate(inflater, container, false)
        val root = binding.root

        activity?.also {
            cursorAdapter = SimpleCursorAdapter(
                it,
                R.layout.contact_item_view,
                null,
                FROM_COLUMNS,
                TO_IDS,
                0
            )
            binding.lvFragmentContacts.adapter = cursorAdapter
        }
        binding.lvFragmentContacts.onItemClickListener = this
        binding.btContactsAllowPermission.setOnClickListener {
            requestPermissionLauncher.launch(
                arrayOf(Manifest.permission.READ_CONTACTS, Manifest.permission.WRITE_CONTACTS)
            )
        }
        checkPermissions()

        return root
    }

    override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        // Get the Cursor
        (parent?.adapter as? CursorAdapter)?.cursor?.apply {
            // Move to the selected contact
            moveToPosition(position)
            // Get the _ID value
            contactId = getLong(CONTACT_ID_INDEX)
            // Get the selected LOOKUP KEY
            contactKey = getString(CONTACT_KEY_INDEX)
            // Create the contact's content Uri
            contactUri = ContactsContract.Contacts.getLookupUri(contactId, contactKey)
            //Get selected contact's display name
            contactName = getString(getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME_PRIMARY))

            showPopupMenu(view)
        }
    }

    override fun onCreateLoader(id: Int, args: Bundle?): Loader<Cursor> {
        return activity?.let {
            return CursorLoader(
                it, ContactsContract.Contacts.CONTENT_URI, PROJECTION,
                null, null, sortOrder
            )
        } ?: throw IllegalStateException()
    }

    override fun onLoadFinished(loader: Loader<Cursor>, data: Cursor?) {
        cursorAdapter?.swapCursor(data)
    }

    override fun onLoaderReset(loader: Loader<Cursor>) {
        cursorAdapter?.swapCursor(null)
    }

    //Check permissions and take actions
    private fun checkPermissions() {
        val requestMessage = getString(R.string.permission_request_message)
        when {
            ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.READ_CONTACTS
            ) == PackageManager.PERMISSION_GRANTED -> {
                onPermissionGranted()
            }
            ActivityCompat.shouldShowRequestPermissionRationale(
                requireActivity(),
                Manifest.permission.READ_CONTACTS
            ) -> {
                requestPermission(requestMessage)
            }
            ActivityCompat.shouldShowRequestPermissionRationale(
                requireActivity(),
                Manifest.permission.WRITE_CONTACTS
            ) -> {
                requestPermission(requestMessage)
            }
            else -> {
                requestPermissionLauncher.launch(
                    arrayOf(Manifest.permission.READ_CONTACTS, Manifest.permission.WRITE_CONTACTS)
                )
            }
        }
    }

    //Actions taked when permissions are allow
    private fun onPermissionGranted() {
        LoaderManager.getInstance(this).initLoader(0, null, this)
        binding.btContactsAllowPermission.visibility = View.GONE
    }

    //Request permissions for access Contacts
    private fun requestPermission(message: String) {
        val builder = AlertDialog.Builder(context)
        builder
            .setTitle("Request Access")
            .setMessage(message)
            .setCancelable(true)
            .setPositiveButton("Yes") { dialog, which ->
                requestPermissionLauncher.launch(
                    arrayOf(Manifest.permission.READ_CONTACTS, Manifest.permission.WRITE_CONTACTS)
                )
            }
            .setNegativeButton("No") { dialog, which ->
                dialog.cancel()
            }
        builder.create().show()
    }

    //Show popup menu for selected contact item on Tap
    private fun showPopupMenu(view: View?) {
        val popup = PopupMenu(context, view)
        val inflater: MenuInflater = popup.menuInflater
        inflater.inflate(R.menu.contact_item_menu, popup.menu)
        popup.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.contact_item_view -> {
                    viewContact()
                    true
                }
                R.id.contact_item_edit -> {
                    editContact()
                    true
                }
                else -> {
                    deleteContact()
                    true
                }
            }
        }
        popup.show()
    }

    //Open Contact Application of device with selected contact in Details Mode
    private fun viewContact() {
        val editIntent = Intent(Intent.ACTION_VIEW).apply {
            setDataAndType(contactUri, ContactsContract.Contacts.CONTENT_ITEM_TYPE)
            putExtra("finishActivityOnSaveCompleted", true)
        }
        startActivity(editIntent)
    }

    //Open Contact Application of device with selected contact in Edit Mode
    private fun editContact() {
        val editIntent = Intent(Intent.ACTION_EDIT).apply {
            setDataAndType(contactUri, ContactsContract.Contacts.CONTENT_ITEM_TYPE)
            putExtra("finishActivityOnSaveCompleted", true)
        }
        startActivity(editIntent)
    }

    //Delete selected contact
    private fun deleteContact() {
        val builder = AlertDialog.Builder(context)
        builder
            .setTitle("Delete Contact")
            .setMessage("${getString(R.string.contact_delete_message)}: ${contactName}?")
            .setCancelable(true)
            .setPositiveButton("Yes") { dialog, which ->
                contactUri?.also {
                    context?.apply {
                        contentResolver.delete(it, null, null)
                    }
                }
            }
            .setNegativeButton("No") { dialog, which ->
                dialog.cancel()
            }
        builder.create().show()
    }

    companion object {
        // The column index for the _ID column
        private const val CONTACT_ID_INDEX: Int = 0

        // The column index for the CONTACT_KEY column
        private const val CONTACT_KEY_INDEX: Int = 1
    }

}


