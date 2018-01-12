maxElementsInMemory：内存中最大缓存对象数。
maxElementsOnDisk：磁盘中最大缓存对象数，若是0表示无穷大。
overflowToDisk：当内存中Element数量达到最大数，是否保存在磁盘
eternal：Element是否永久有效
timeToIdleSeconds：设置Element在失效前的允许闲置时间 (单位：秒)
timeToLiveSeconds：设置Element在失效前允许存活时间 (单位：秒)
memoryStoreEvictionPolicy: 缓存满了之后的淘汰算法 LRU(最近最少使用)(default) FIFO(先进先出) LFU(较少使用)
 