package ry.prioritizer.serialization

import dagger.Module
import dagger.Provides
import model.Category
import model.Item
import model.Tree
import serialization.CategorySerializer
import serialization.ComposedJsonArraySerializer
import serialization.ItemSerializer
import serialization.JsonSerializer
import serialization.TreeSerializer
import javax.inject.Singleton

@Module
object SerializationModule {

    @Provides
    @Singleton
    fun provideCategorySerializer(): JsonSerializer<Category> = CategorySerializer

    @Provides
    @Singleton
    fun provideItemSerializer(): JsonSerializer<Item> = ItemSerializer

    @Provides
    @Singleton
    fun provideQueueSerializer(
        itemSerializer: JsonSerializer<Item>
    ): JsonSerializer<List<Item>> =
        ComposedJsonArraySerializer(itemSerializer)

    @Provides
    @Singleton
    fun provideTreeSerializer(
        categorySerializer: JsonSerializer<Category>,
        queueSerializer: JsonSerializer<List<Item>>
    ): JsonSerializer<Tree> =
        TreeSerializer(
            categorySerializer,
            queueSerializer
        )

}
