package ry.prioritizer.serialization

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
object SerializationModule {

    @JvmStatic
    @Provides
    @Singleton
    fun provideObjectMapper(): ObjectMapper = jacksonObjectMapper()

}
