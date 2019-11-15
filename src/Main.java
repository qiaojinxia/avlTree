/**
 * Created by caomaoboy 2019-11-15
 **/
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class Main {
    public static void main(String[] args){


        //测试AVLtree
        PerformanceTest((Map<String, Integer>) new AVLTree<String,Integer>());
        //测试BST
        PerformanceTest((Map<String, Integer>) new BST<String,Integer>());

        }

        public static void PerformanceTest(Map<String, Integer> map){
            long start = System.currentTimeMillis();
            System.out.println("Pride and Prejudice");

            ArrayList<String> words = new ArrayList<>();
            if(FileOperation.readFile("pride-and-prejudice.txt", words)) {
                //模拟最坏的情况
                Collections.sort(words);
                System.out.println("Total words: " + words.size());
                for (String word : words) {
                    if (map.contains(word))
                        map.set(word, map.get(word) + 1);
                    else
                        map.add(word, 1);
                }
                System.out.println("Total different words: " + map.getSize());
                System.out.println("Frequency of PRIDE: " + map.get("pride"));
                //map.remove("prejudice");
                System.out.println("Frequency of PREJUDICE: " + map.get("prejudice"));
                long end = System.currentTimeMillis();

                System.out.println(map.getmAlgorithmName() +"算法"+"耗时"+(end - start)+"ms");
        }

        System.out.println();


    }
}
