package com.thebrownfoxx.marballs.services.findinfo

import com.thebrownfoxx.marballs.domain.Find
import com.thebrownfoxx.marballs.domain.FindInfo

interface FindInfoProvider {
    fun Find.toFindInfo(): FindInfo
}