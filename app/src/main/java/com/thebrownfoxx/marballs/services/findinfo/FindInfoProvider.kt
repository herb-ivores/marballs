package com.thebrownfoxx.marballs.services.findinfo

import com.thebrownfoxx.marballs.domain.Find
import com.thebrownfoxx.marballs.domain.FindInfo
import com.thebrownfoxx.marballs.domain.Outcome

interface FindInfoProvider {
    suspend fun Find.toFindInfo(): Outcome<FindInfo>
}