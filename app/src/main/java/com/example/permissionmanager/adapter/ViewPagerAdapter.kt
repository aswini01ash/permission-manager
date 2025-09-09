package com.example.permissionmanager.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.permissionmanager.ui.email.EmailFragment
import com.example.permissionmanager.ui.permission.PermissionFragment
import com.example.permissionmanager.ui.profile.ProfileFragment

class ViewPagerAdapter(fragmentActivity: FragmentActivity) :
    FragmentStateAdapter(fragmentActivity) {

    override fun getItemCount(): Int = 3

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> ProfileFragment()
            1 -> PermissionFragment()
            2 -> EmailFragment()
            else -> ProfileFragment()
        }
    }
}