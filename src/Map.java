public interface Map<K,V> {
    void add(K key,V value);
    V remove(K key);
    boolean contains(K k);
    V get(K k);//传入某个键 返回值
    void set(K k,V v);//修改操作 给定一个键 修改对应值
    int getSize();
    boolean isEmpty();
    String getmAlgorithmName();


}
