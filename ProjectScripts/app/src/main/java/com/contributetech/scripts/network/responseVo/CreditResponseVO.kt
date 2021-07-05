package com.contributetech.scripts.network.responseVo

import com.contributetech.scripts.database.Cast

data class CreditResponseVO (
        var id:Int,
        var cast:ArrayList<Cast>
)