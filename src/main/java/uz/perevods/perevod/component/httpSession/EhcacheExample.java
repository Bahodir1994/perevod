package uz.perevods.perevod.component.httpSession;//package uz.customs.customprice.component.httpSession;
//
//import net.sf.ehcache.Cache;
//import net.sf.ehcache.CacheManager;
//import net.sf.ehcache.config.CacheConfiguration;
//import net.sf.ehcache.config.PersistenceConfiguration;
//import net.sf.ehcache.store.MemoryStoreEvictionPolicy;
//
//public class EhcacheExample {
//
//    public static void main(String[] args) {
//        // Create a CacheManager instance
//        CacheManager cacheManager = CacheManager.create();
//
//        // Define the default cache configuration
//        CacheConfiguration defaultCacheConfig = new CacheConfiguration();
//        defaultCacheConfig.maxEntriesLocalHeap(10000000)
//                .eternal(false)
//                .timeToIdleSeconds(300)
//                .timeToLiveSeconds(600)
//                .diskSpoolBufferSizeMB(2048)
//                .maxEntriesLocalDisk(10000000)
//                .diskExpiryThreadIntervalSeconds(120)
//                .memoryStoreEvictionPolicy(MemoryStoreEvictionPolicy.LRU)
//                .statistics(true)
//                .persistence(new PersistenceConfiguration().strategy(PersistenceConfiguration.Strategy.LOCALTEMPSWAP));
//
//        // Create the default cache with the given configuration
//        Cache defaultCache = new Cache(defaultCacheConfig);
//
//        // Define the custom cache configuration
//        CacheConfiguration customCacheConfig = new CacheConfiguration("uz.customs.customprice.entity.costmonitoring.BaseEntity", 1000);
//        customCacheConfig.eternal(false)
//                .timeToIdleSeconds(300)
//                .timeToLiveSeconds(600)
//                .persistence(new PersistenceConfiguration().strategy(PersistenceConfiguration.Strategy.LOCALTEMPSWAP));
//
//        // Create the custom cache with the given configuration
//        Cache customCache = new Cache(customCacheConfig);
//
//        // Add the caches to the cache manager
//        cacheManager.addCache(defaultCache);
//        cacheManager.addCache(customCache);
//
//        // Retrieve the caches from the cache manager
//        Cache retrievedDefaultCache = cacheManager.getCache(Cache.DEFAULT_CACHE_NAME);
//        Cache retrievedCustomCache = cacheManager.getCache("uz.customs.customprice.entity.costmonitoring.BaseEntity");
//
//        // Print out the configuration of each cache
//        System.out.println("Default cache configuration:\n" + retrievedDefaultCache.getCacheConfiguration());
//        System.out.println("Custom cache configuration:\n" + retrievedCustomCache.getCacheConfiguration());
//
//        // Shutdown the cache manager when done
//        cacheManager.shutdown();
//    }
//}
