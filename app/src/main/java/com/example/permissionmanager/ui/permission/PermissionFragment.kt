package com.example.permissionmanager.ui.permission

import android.Manifest
import android.app.AlertDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.permissionmanager.R
import com.example.permissionmanager.databinding.FragmentPermissionBinding
import com.example.permissionmanager.util.ToastUtil
import com.google.android.material.materialswitch.MaterialSwitch

class PermissionFragment : Fragment() {

    private var _binding: FragmentPermissionBinding? = null
    private val binding get() = _binding!!

    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        updateAllPermissionStatus()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPermissionBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupUI()
        checkAllPermissions()
    }

    override fun onResume() {
        super.onResume()
        updateAllPermissionStatus()
    }

    private fun setupUI() {
        binding.switchCamera.setOnCheckedChangeListener { _, isChecked ->
            handleSwitchToggle(Manifest.permission.CAMERA, isChecked)
        }

        binding.switchMsg.setOnCheckedChangeListener { _, isChecked ->
            handleSwitchToggle(Manifest.permission.SEND_SMS, isChecked)
        }

        binding.switchLocation.setOnCheckedChangeListener { _, isChecked ->
            handleSwitchToggle(Manifest.permission.ACCESS_FINE_LOCATION, isChecked)
        }

        binding.layCameraPermission.setOnClickListener {
            handlePermissionRequest(Manifest.permission.CAMERA)
        }

        binding.layMsgPermission.setOnClickListener {
            handlePermissionRequest(Manifest.permission.SEND_SMS)
        }

        binding.layLocationPermission.setOnClickListener {
            handlePermissionRequest(Manifest.permission.ACCESS_FINE_LOCATION)
        }

        binding.btnContinue.setOnClickListener {
            ToastUtil.shortToast(requireContext(), R.string.btn_clicked)
        }
    }

    private fun checkAllPermissions() {
        updateAllPermissionStatus()
    }

    private fun updateAllPermissionStatus() {
        updatePermissionUI(
            Manifest.permission.CAMERA,
            binding.ivCameraIcon,
            binding.tvCameraStatus,
            binding.switchCamera
        )

        updatePermissionUI(
            Manifest.permission.SEND_SMS,
            binding.ivMessageIcon,
            binding.tvMessageStatus,
            binding.switchMsg
        )

        updatePermissionUI(
            Manifest.permission.ACCESS_FINE_LOCATION,
            binding.ivLocationIcon,
            binding.tvLocationStatus,
            binding.switchLocation
        )

        updateContinueButton()
    }

    private fun updatePermissionUI(
        permission: String,
        iconView: ImageView,
        statusText: TextView,
        switch: MaterialSwitch
    ) {
        val isGranted = ContextCompat.checkSelfPermission(
            requireContext(),
            permission
        ) == PackageManager.PERMISSION_GRANTED

        switch.setOnCheckedChangeListener(null)

        if (isGranted) {
            iconView.setImageResource(R.drawable.ic_check_green)
            statusText.text = getString(R.string.granted)
            statusText.setTextColor(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.green
                )
            )
            switch.isChecked = true
        } else {
            iconView.setImageResource(R.drawable.ic_close_red)
            statusText.text = getString(R.string.denied)
            statusText.setTextColor(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.red
                )
            )
            switch.isChecked = false
        }

        when (permission) {
            Manifest.permission.CAMERA -> {
                switch.setOnCheckedChangeListener { _, isChecked ->
                    handleSwitchToggle(Manifest.permission.CAMERA, isChecked)
                }
            }

            getStoragePermission() -> {
                switch.setOnCheckedChangeListener { _, isChecked ->
                    handleSwitchToggle(getStoragePermission(), isChecked)
                }
            }

            Manifest.permission.SEND_SMS -> {
                switch.setOnCheckedChangeListener { _, isChecked ->
                    handleSwitchToggle(Manifest.permission.SEND_SMS, isChecked)
                }
            }

            Manifest.permission.ACCESS_FINE_LOCATION -> {
                switch.setOnCheckedChangeListener { _, isChecked ->
                    handleSwitchToggle(Manifest.permission.ACCESS_FINE_LOCATION, isChecked)
                }
            }
        }
    }

    private fun handleSwitchToggle(permission: String, isChecked: Boolean) {
        val isCurrentlyGranted = ContextCompat.checkSelfPermission(
            requireContext(),
            permission
        ) == PackageManager.PERMISSION_GRANTED

        if (isChecked && !isCurrentlyGranted) {
            handlePermissionRequest(permission)
        } else if (!isChecked && isCurrentlyGranted) {
            showDisablePermissionDialog(permission)
        } else {
            updateAllPermissionStatus()
        }
    }

    private fun showDisablePermissionDialog(permission: String) {
        val permissionName = when (permission) {
            Manifest.permission.CAMERA -> getString(R.string.camera)
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.READ_MEDIA_IMAGES -> getString(R.string.storage)

            Manifest.permission.SEND_SMS -> getString(R.string.sms)
            Manifest.permission.ACCESS_FINE_LOCATION -> getString(R.string.location)
            else -> getString(R.string.permission)
        }

        AlertDialog.Builder(requireContext())
            .setTitle(getString(R.string.dialog_disable_permission_title, permissionName))
            .setMessage(getString(R.string.dialog_disable_permission_message))
            .setPositiveButton(getString(R.string.dialog_open_settings)) { _, _ ->
                openAppSettings()
            }
            .setNegativeButton(getString(R.string.cancel)) { dialog, _ ->
                dialog.dismiss()
                updateAllPermissionStatus()
            }
            .show()
    }

    private fun openAppSettings() {
        try {
            val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
            val uri = Uri.fromParts("package", requireContext().packageName, null)
            intent.data = uri
            startActivity(intent)
        } catch (e: Exception) {
            ToastUtil.shortToast(requireContext(), R.string.settings_error)
        }
    }

    private fun handlePermissionRequest(permission: String) {
        when {
            ContextCompat.checkSelfPermission(
                requireContext(),
                permission
            ) == PackageManager.PERMISSION_GRANTED -> {
                updateAllPermissionStatus()
            }

            shouldShowRequestPermissionRationale(permission) -> {
                showPermissionRationale(permission)
            }

            else -> {
                requestPermissionLauncher.launch(permission)
            }
        }
    }

    private fun showPermissionRationale(permission: String) {
        val permissionName = when (permission) {
            Manifest.permission.CAMERA -> getString(R.string.camera)
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.READ_MEDIA_IMAGES -> getString(R.string.storage)

            Manifest.permission.SEND_SMS -> getString(R.string.sms)
            Manifest.permission.ACCESS_FINE_LOCATION -> getString(R.string.location)
            else -> getString(R.string.permission)
        }

        AlertDialog.Builder(requireContext())
            .setTitle(getString(R.string.dialog_permission_title))
            .setMessage(getString(R.string.dialog_permission_message, permissionName))
            .setPositiveButton(getString(R.string.dialog_permission_grant)) { _, _ ->
                requestPermissionLauncher.launch(permission)
            }
            .setNegativeButton(getString(R.string.cancel)) { dialog, _ ->
                dialog.dismiss()
                updateAllPermissionStatus()
            }
            .show()

    }

    private fun updateContinueButton() {
        val hasAnyPermission =
            ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.CAMERA
            ) == PackageManager.PERMISSION_GRANTED ||
                    ContextCompat.checkSelfPermission(
                        requireContext(),
                        getStoragePermission()
                    ) == PackageManager.PERMISSION_GRANTED ||
                    ContextCompat.checkSelfPermission(
                        requireContext(),
                        Manifest.permission.SEND_SMS
                    ) == PackageManager.PERMISSION_GRANTED ||
                    ContextCompat.checkSelfPermission(
                        requireContext(),
                        Manifest.permission.ACCESS_FINE_LOCATION
                    ) == PackageManager.PERMISSION_GRANTED

        if (hasAnyPermission) {
            binding.btnContinue.backgroundTintList =
                ContextCompat.getColorStateList(requireContext(), R.color.purpleText)
            binding.btnContinue.setTextColor(
                ContextCompat.getColor(
                    requireContext(),
                    android.R.color.white
                )
            )
            binding.btnContinue.isEnabled = true
        } else {
            binding.btnContinue.backgroundTintList =
                ContextCompat.getColorStateList(requireContext(), R.color.grey01)
            binding.btnContinue.setTextColor(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.grey
                )
            )
            binding.btnContinue.isEnabled = false
        }
    }

    private fun getStoragePermission(): String {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            Manifest.permission.READ_MEDIA_IMAGES
        } else {
            Manifest.permission.READ_EXTERNAL_STORAGE
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}