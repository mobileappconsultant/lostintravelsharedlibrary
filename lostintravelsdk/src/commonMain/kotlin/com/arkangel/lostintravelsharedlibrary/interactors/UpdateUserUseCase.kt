package com.arkangel.lostintravelsharedlibrary.interactors

import com.arkangel.lostintravelsharedlibrary.UpdateUserMutation
import com.arkangel.lostintravelsharedlibrary.type.UpdateUserInput
import com.arkangel.lostintravelsharedlibrary.util.CommonFlow
import com.arkangel.lostintravelsharedlibrary.util.DataState

class UpdateUserUseCase: BaseUseCase<UpdateUserMutation.Response, UpdateUserInput> {
    override fun execute(input: UpdateUserInput): CommonFlow<DataState<UpdateUserMutation.Response>> =
        makeFlow(input, getApiService()::updateUser)
}