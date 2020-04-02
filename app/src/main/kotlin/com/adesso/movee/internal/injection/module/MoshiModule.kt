package com.adesso.movee.internal.injection.module

import com.adesso.movee.data.remote.model.search.MovieMultiSearchResponseModel
import com.adesso.movee.data.remote.model.search.MovieProductResponseModel
import com.adesso.movee.data.remote.model.search.MultiSearchItemResponseModel
import com.adesso.movee.data.remote.model.search.PersonMultiSearchResponseModel
import com.adesso.movee.data.remote.model.search.ProductResponseModel
import com.adesso.movee.data.remote.model.search.TvShowMultiSearchResponseModel
import com.adesso.movee.data.remote.model.search.TvShowProductResponseModel
import com.adesso.movee.internal.util.DateAdapter
import com.adesso.movee.internal.util.DepartmentResponseJsonAdapter
import com.adesso.movee.internal.util.ImageJsonAdapter
import com.serjltt.moshi.adapters.Wrapped
import com.squareup.moshi.Moshi
import com.squareup.moshi.adapters.PolymorphicJsonAdapterFactory
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
internal class MoshiModule {

    @Provides
    @Singleton
    fun provideMoshi(
        multiSearchItemJsonAdapter: PolymorphicJsonAdapterFactory<MultiSearchItemResponseModel>,
        productJsonAdapter: PolymorphicJsonAdapterFactory<ProductResponseModel>
    ): Moshi {
        return Moshi.Builder()
            .add(multiSearchItemJsonAdapter)
            .add(productJsonAdapter)
            .add(KotlinJsonAdapterFactory())
            .add(ImageJsonAdapter())
            .add(DateAdapter())
            .add(DepartmentResponseJsonAdapter())
            .add(Wrapped.ADAPTER_FACTORY)
            .build()
    }

    @Provides
    fun provideMultiSearchItemJsonAdapter():
        PolymorphicJsonAdapterFactory<MultiSearchItemResponseModel> {
        return PolymorphicJsonAdapterFactory.of(
                MultiSearchItemResponseModel::class.java,
                MultiSearchItemResponseModel.MEDIA_TYPE
            )
            .withSubtype(
                MovieMultiSearchResponseModel::class.java,
                MultiSearchItemResponseModel.MEDIA_TYPE_MOVIE
            )
            .withSubtype(
                TvShowMultiSearchResponseModel::class.java,
                MultiSearchItemResponseModel.MEDIA_TYPE_TV
            )
            .withSubtype(
                PersonMultiSearchResponseModel::class.java,
                MultiSearchItemResponseModel.MEDIA_TYPE_PERSON
            )
    }

    @Provides
    fun provideProductJsonAdapter(): PolymorphicJsonAdapterFactory<ProductResponseModel> {
        return PolymorphicJsonAdapterFactory.of(
                ProductResponseModel::class.java,
                ProductResponseModel.MEDIA_TYPE
            )
            .withSubtype(
                TvShowProductResponseModel::class.java,
                ProductResponseModel.MEDIA_TYPE_TV
            )
            .withSubtype(
                MovieProductResponseModel::class.java,
                ProductResponseModel.MEDIA_TYPE_MOVIE
            )
    }
}
