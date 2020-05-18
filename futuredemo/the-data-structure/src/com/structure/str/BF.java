package com.structure.str;

/**
 * @Author Tanyu
 * @Date 2020/5/15 17:11
 * @Description 字符串模糊匹配
 * @Version 1.0
 */
public class BF {

  public static void main(String[] args) {

    int bf = bf("abcdefgh", "cdf");
    System.out.println(bf);
  }

  private static int bf(String res, String tar) {
    char[] r = res.toCharArray();
    int m = r.length;
    char[] t = tar.toCharArray();
    int n = t.length;
    int i = 0, j = 0;
    while (i < m && j < n) {
      if (r[i] == t[j]) {
        i++;
        j++;
      } else {
        i = i - j + 1;
        j = 0;
      }
    }
    if (j == n) {
      return i - n;
    } else {
      return 0;
    }
  }
}
