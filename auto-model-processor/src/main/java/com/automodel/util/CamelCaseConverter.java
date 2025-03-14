package com.automodel.util;

public class CamelCaseConverter {

    /**
     * 将字符串转换为小驼峰命名（Lower Camel Case）
     *
     * @param input 输入字符串，可以包含下划线或空格
     * @return 小驼峰命名的字符串
     */
    public static String toLowerCamelCase(String input) {
        return toCamelCase(input, false);
    }

    /**
     * 将字符串转换为大驼峰命名（Upper Camel Case）
     *
     * @param input 输入字符串，可以包含下划线或空格
     * @return 大驼峰命名的字符串
     */
    public static String toUpperCamelCase(String input) {
        return toCamelCase(input, true);
    }

    /**
     * 通用方法：将字符串转换为驼峰命名
     *
     * @param input          输入字符串
     * @param upperCaseFirst 是否将第一个单词的首字母大写
     * @return 驼峰命名的字符串
     */
    private static String toCamelCase(String input, boolean upperCaseFirst) {
        if (input == null || input.isEmpty()) {
            return input;
        }

        // 使用正则表达式分割字符串（支持下划线、空格等分隔符）
        String[] words = input.split("[\\s_]+");

        StringBuilder result = new StringBuilder();

        for (int i = 0; i < words.length; i++) {
            String word = words[i];
            if (word.isEmpty()) {
                continue; // 跳过空单词
            }

            if (i == 0 && !upperCaseFirst) {
                // 第一个单词，首字母小写
                result.append(word.toLowerCase());
            } else {
                // 后续单词，首字母大写
                result.append(Character.toUpperCase(word.charAt(0)));
                if (word.length() > 1) {
                    result.append(word.substring(1).toLowerCase());
                }
            }
        }

        return result.toString();
    }

    public static void main(String[] args) {
        String input1 = "user_name";
        String input2 = "order amount";
        String input3 = "product_description";

        System.out.println("Lower Camel Case: " + toLowerCamelCase(input1)); // userName
        System.out.println("Upper Camel Case: " + toUpperCamelCase(input1)); // UserName

        System.out.println("Lower Camel Case: " + toLowerCamelCase(input2)); // orderAmount
        System.out.println("Upper Camel Case: " + toUpperCamelCase(input2)); // OrderAmount

        System.out.println("Lower Camel Case: " + toLowerCamelCase(input3)); // productDescription
        System.out.println("Upper Camel Case: " + toUpperCamelCase(input3)); // ProductDescription
    }
}
