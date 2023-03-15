package com.sun.base.util

import android.Manifest
import android.os.Build
import androidx.fragment.app.FragmentActivity
import com.permissionx.guolindev.PermissionX
import com.sun.base.expand.toast

object PermissionUtil {

    fun requestAllPermissionTip(activity: FragmentActivity) {
        PermissionX.init(activity)
            .permissions(getAllPermission())
            .onExplainRequestReason{ scope, deniedList ->
                scope.showRequestReasonDialog(deniedList, "应用需要您同意以下权限才能正常使用", "确定")
            }
            .request{ allGranted, _, deniedList ->
                if (!allGranted) {
                    "您拒绝了如下权限$deniedList".toast()
                }
            }

    }

    private fun getAllPermission(): List<String> {
        val list: MutableList<String> = ArrayList()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            list.add(Manifest.permission.ACCESS_BACKGROUND_LOCATION)
        }
        list.add(Manifest.permission.ACCESS_COARSE_LOCATION)
        list.add(Manifest.permission.ACCESS_FINE_LOCATION)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            list.add(Manifest.permission.READ_MEDIA_IMAGES)
            list.add(Manifest.permission.READ_MEDIA_AUDIO)
            list.add(Manifest.permission.READ_MEDIA_VIDEO)
            list.add(Manifest.permission.POST_NOTIFICATIONS)
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                list.add(Manifest.permission.MANAGE_EXTERNAL_STORAGE)
            }
            list.add(Manifest.permission.WRITE_EXTERNAL_STORAGE)
            list.add(Manifest.permission.READ_EXTERNAL_STORAGE)
        }
        list.add(Manifest.permission.CAMERA)
        return list
    }

}