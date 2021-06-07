package com.alphasoftcu.apiex.view.adapters

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.alphasoftcu.apiex.R
import com.alphasoftcu.apiex.view.fragment.ContactsFragment
import com.alphasoftcu.apiex.view.fragment.PostsFragment

//Array of Tab's title
private val TAB_TITLES = arrayOf(
    R.string.tab_name_posts,      //Tab title for Posts
    R.string.tab_name_contacts    //Tab title for Contacts
)

/**
 * FragmentPagerAdapter that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
class SectionsPagerAdapter(private val context: Context, fm: FragmentManager) :
      FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    override fun getItem(position: Int): Fragment {
        return when(position){
            0 -> PostsFragment()
            else -> ContactsFragment()
        }
    }

    override fun getPageTitle(position: Int): CharSequence {
        return context.resources.getString(TAB_TITLES[position])
    }

    override fun getCount(): Int = 2
}