**Chapter1**

*1.1 Context Switch(上下文切换)*
   * 无锁并发
      * 多线程竞争锁时，会引起上下文切换，可以用一些办法来避免使用锁，入江数据的ID按照Hash算法取模分段，不同的线程处理不同的段。 
   * CAS算法
      * compare and swap
   * 使用最少线程
      * 避免创建不需要的线程，比如任务很少，但是创建了很多线程来处理，这样会照成大量线程都处于等待状态。
   * 协程
      * 在单线程里实现多任务的调度，并且的单线程里维护多个任务之间的切换

*1.2 避免死锁的常见方法*
   * 避免一个线程同时获取多个锁
   * 避免一个线程在锁内同时占用多个资源
   * 尝试使用定时锁，lock.tryLock
   * 对于数据库锁，枷锁和解锁必须在一个数据库连接里面，否则会出现解锁失败的情况
   
*1.3* 资源限制的挑战
   * 资源限制：比如服务器带宽只有2M/s,某个资源下载速度是1M/s，则多线程的上线也是1M/s
   * 资源限制引发的问题
      * 并行为真的并行
   * 如何解决资源限制的问题
      * 硬件限制：集群并行
      * 软件限制：资源池
   * 在资源限制情况下进行并发编程
      * 找到瓶颈
      

**Chapter 2**

*2.1* volatile 的应用
   * cpu术语
      * 内存屏障（memory barrier）：一组处理器指令，用户实现对内存操作的顺序限制
      * 缓冲行（cache line）：cup高速缓存中可以分配的最小存储单位。cpu填写缓存行时会加载整个缓存行，现在CP需要执行几百次CPU指令
      * 原子操作（atomic operations）：不可终端的一个或一系列操作
      * 缓存行填充（cache line fill）：当处理器识别到从内存中读取操作数是可缓存的，处理器读取整个缓存行到适当的缓存（L1,L2,L3或所有）
      * 缓存命中（cache hit）：如果进行高速缓存行填充操作的内存为止仍是下次处理器访问的地址是，处理器从缓存中读取操作数，而不是从内存中读取
      * 写命中（write hit）：当处理器将操作数写回到一个内存缓存的区域时，它会首先检查这个缓存的内存地址是否在缓存行中，如果存在一个有效的缓存行，则处理器将这个操作数写回到到缓存，而不是写回到内存，这个操作被称为写命中
      * 写缺失（write misses the cache）：一个有效的缓存行被写入到不存在的内存区域
   * Lock前缀指令
      * 将当前处理缓存行的数据写回到系统内存
      * 这个协会内存的操作会使在其他CPU里缓存了该内存地址的数据无效
   * 缓存一致性协议
      * MESI（modified，Exclusive，Share，Invalid）http://blog.csdn.net/muxiqingyang/article/details/6615199
   * volatile的两条实现原则
      * 1）Lock 前缀指令会引起处理器缓存协会到内存
      * 2）一个处理器的缓存回写到内存会导致其他处理器的缓存无效
   * volatile的使用优化
      * LinkedTransferQueue
         * 追加字节优化性能
            * L1,L2,L3 高速缓存是64字节宽，不支持部分填充缓存行
            * 避免head和tail放在一个缓存行里面
         * 不适用的场景
            * 缓存行非64字节的
            * 共享变量不会被频繁地写
            
*2.2* synchronized 的实现原理与应用
   * 重量级锁
      * jdk1.6为了优化性能引入了偏向锁和轻量级锁，以及所的存储结构和升级过程
   * 实现同步的基础：java中每个对象都可以作为锁
      * 对于普通的同步方法，锁是当前实例对象
      * 对于静态同步方法，锁是当前类的class对象
      * 对于同步方法快，所示synchronized括号里面的对象
   * jvm基于进入和退出Monitor对象来实现方法的同步和代码块的同步
      * 方法块同步是通过monitorenter和monitorexit指令实现的
      * synchronized用的锁是存在java对象头里的
   * java对象头
      * 如果对象是数组类型，VM使用3个字宽（word）来存储对象头，否则用两个
         * mark word：32/64 bit，存储对象的hashCode或锁信息
            * 其中存有gc年龄
         * class metadata address：32/64 bit，存储对象类型数据的指针
         * array length：32/32 bit，数组的长度，如果对象是数组
   * 锁的升级与对比
      * 锁类别
         * 无锁状态，偏向锁状态，轻量级锁状态，重量级锁状态
            * 锁可以升级却不能降级，是处于提高获获取锁和释放锁的效率
      * 偏向锁
         * 当线程访问同步块并获取锁，会在队形头和战阵中的所记录里存储锁偏向的线程ID，以后该线程在进入和退出同步块是不需要进行CAS操作来加锁和解锁，只需要简单测试一下对象头的Mark Word中是否存储着会想当前线程的偏向锁。
         * 偏向锁，使用一种等到竞争出现才释放锁的机制，所以当其他线程尝试竞争偏向锁时，持有偏向锁的线程才会释放锁。
         * 关闭偏向锁
            * jdk1.6 1.7中默认开启
               * -XX:BiasedLockingStartupDelay=0:关闭延时
               * -XX:UseBiasedLocking=false，关闭偏向锁，默认进入轻量级锁状态
         * 优点
            * 加解锁不需要额外的消耗，与非同步方法比，仅存在ns的差距
         * 缺点
            * 如果存在竞争，会带来额外的撤销的消耗
         * 适用场景
            * 只有一个线程访问的同步块场景
      * 轻量级锁
         * 加锁：执行同步块之前，VM在当前线程的战阵中创建用于存储所记录的空间，并将对象头中的Mark Word复制到所记录中（Displaced Mark Word）
             * 没抢到，就自旋
         * 解锁：CAS将Displaced Mark Word替换回到对象头
            * 成功则表示没有竞争的发生
            * 失败则表示存在竞争，则锁会膨胀成重量级锁，并自己阻塞。
         * 优点
            * 竞争的线程不会阻塞，提高了响应速度
         * 缺点
            * 如果始终得不到所竞争的线程，自旋会消耗CPU
         * 适用场景
            * 最求相应时间，同步块执行的速度非常快
      * 重量级锁
         * 优点
            * 线程竞争不用自旋，不会消耗CPU
         * 缺点
            * 线程阻塞，响应时间缓慢
         * 适用场景
            * 最求吞吐量，同步块执行时间较长            

*2.3* 原子操作的实现原理
   * 术语
      * CAS
      * cache line
      * CPU pipeline：X86指令分解为5~6个步骤，使用CPU中5~6个不同功能的电路单元主城的指令处理流水线来完成。
      * Memory order violation：假共享（多个CPU指令通过时修改同一个缓存行的不同部分）引起的CPU清空流水线
   * 处理器如何实现原子操作
      * 1）通过总线加锁保证原子性
         * lock#信号
         * 导致其他CPU不能访问内存
      * 2）通过缓存锁来保证原子性
         * 缓存一致性
         * 处理器不适用缓存锁定的场景
            * 当操作的数据不能缓存在处理器内部、或操作的数据跨多个缓存行，这个是会使用总线锁
            * 有些处理器不支持缓存锁定
   * java如何实现原子操作
      * java中通过锁和循环CAS来实现原子操作
      * CAS
         * 利用处理器的CMPXCHG指令
         * 问题
            * ABA：添加版本号
            * 循环时间长开销大：pause指令
            * 只能保证一个共享变量的原子操作
      * 使用锁机制实现原子操作
         * 除了偏向锁，很多锁都用了循环CAS
             
               
               
              
      
      
















