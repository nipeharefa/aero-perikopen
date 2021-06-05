package dev.nias.perikopen

import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.cache.normalized.lru.EvictionPolicy
import com.apollographql.apollo.cache.normalized.lru.LruNormalizedCacheFactory

object GraphQL {
    fun getClient(): ApolloClient {
        val cacheFactory = LruNormalizedCacheFactory(EvictionPolicy.builder().maxSizeBytes(10 * 1024).build())
        val client = ApolloClient.builder()
            .serverUrl("https://perikopen-gql-v2.nias.dev/query")
            .normalizedCache(cacheFactory)
            .build()

        return client
    }
}