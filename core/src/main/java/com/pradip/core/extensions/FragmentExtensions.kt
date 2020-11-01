package com.pradip.core.extensions

import android.os.Bundle
import androidx.navigation.NavController

fun NavController.navigateSafe(id: Int, currentId:Int, args: Bundle?= null) { if(this.currentDestination?.id == currentId) this.navigate(id, args)}
