package utils;

public class PayloadUtils {

    public static String getPetPayload(String id, String name, String status){
        String payload = "{\n" +
                "  \"id\": "+id+",\n" +
                "  \"category\": {\n" +
                "    \"id\": 0,\n" +
                "    \"name\": \"hatiko\"\n" +
                "  },\n" +
                "  \"name\": \""+name+"\",\n" +
                "  \"photoUrls\": [\n" +
                "    \"https://s3.amazon.com\"\n" +
                "  ],\n" +
                "  \"tags\": [\n" +
                "    {\n" +
                "      \"id\": 0,\n" +
                "      \"name\": \"string\"\n" +
                "    }\n" +
                "  ],\n" +
                "  \"status\": \""+status+"\"\n" +
                "}";
        return payload;
    }

    public static String getSlackPayload(String message){
        String payload = "{\n" +
                "  \"channel\": \"C04JYTRQTRR\",\n" +
                "  \"text\": \"Majd: "+message+"\"\n" +
                "}";
        return payload;
    }

}
