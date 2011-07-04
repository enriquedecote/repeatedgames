package util;

public class Array {
  
  
  public static String arrayToNakedString(int[] array){
    String result = "";
    for(int i=0;i<array.length-1;i++){
      result+=array[i]+" ";
    }
    if (array.length>0){
      result+=array[array.length-1];
    }
    return result;
  }

  public static String arrayToNakedString(double[] array){
    String result = "";
    for(int i=0;i<array.length-1;i++){
      result+=array[i]+" ";
    }
    if (array.length>0){
      result+=array[array.length-1];
    }
    return result;
  }

  public static String arrayToString(int[] array){
    String result = "{";
    for(int i=0;i<array.length-1;i++){
      result+=array[i]+" ";
    }
    if (array.length>0){
      result+=array[array.length-1];
    }
    return result + "}";
  }
  public static String arrayToString(double[] array){
    String result = "{";
    for(int i=0;i<array.length-1;i++){
      result+=array[i]+" ";
    }
    if (array.length>0){
      result+=array[array.length-1];
    }

    return result + "}";
  }

  public static String arrayToNakedString(Object[] array){
    String result = "";
    for(int i=0;i<array.length-1;i++){
      result+=array[i]+" ";
    }
    if (array.length>0){
      result+=array[array.length-1];
    }
    return result;
  }
  
  public static String arrayToString(Object[] array){
    String result = "{";
    for(int i=0;i<array.length-1;i++){
      result+=array[i]+" ";
    }
    if (array.length>0){
      result+=array[array.length-1];
    }
    return result + "}";
  }

  public static void swap(int[] array, int i, int j) {
    int temp = array[i];
    array[i]=array[j];
    array[j] = temp;

  }

}
