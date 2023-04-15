package com.arkangel.lostintravelsharedlibrary.interactors

import com.arkangel.lostintravelsharedlibrary.CreateNewBudgetMutation
import com.arkangel.lostintravelsharedlibrary.type.CreateBudget
import com.arkangel.lostintravelsharedlibrary.util.CommonFlow
import com.arkangel.lostintravelsharedlibrary.util.DataState

class CreateNewBudgetUseCase: BaseUseCase<CreateNewBudgetMutation.Response, CreateBudget> {
    override fun execute(input: CreateBudget) = makeFlow(input, getApiService()::createNewBudget)
}