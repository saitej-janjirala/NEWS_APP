package com.saitejajanjirala.news_app.di

import com.saitejajanjirala.news_app.data.network.ApiService
import com.saitejajanjirala.news_app.utils.Keys
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun getApiService() = ApiService.getInstance()

    fun getSettingsList() : List<Pair<String,MutableList<String>>>{
        val ll = mutableListOf<Pair<String,MutableList<String>>>()
        val list1 : Pair<String,MutableList<String>> = Pair(Keys.SETTINGS_CATEGORY,
            mutableListOf<String>("business","entertainment","general","health","science","sports","technology")
        )
        val list2 : Pair<String,MutableList<String>> = Pair(Keys.SETTINGS_COUNTRY,
            mutableListOf<String>("ae","ar","at","au","be","bg","br","ca","ch","cn","co","cu","cz","de","eg","fr","gb","gr","hk","hu","id","ie","il","in","it","jp","kr","lt","lv","ma","mx","my","ng","nl","no","nz","ph","pl","pt","ro","rs","ru","sa","se","sg","si","sk","th","tr","tw","ua","us","ve")
        )
        ll.add(list1)
        ll.add(list2)
        return ll
    }
}