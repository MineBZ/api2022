package utils;

import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;


public class JsonUtil {

    private static ObjectMapper mapper=new ObjectMapper();

    static {
        mapper=new ObjectMapper();

    }

    //1. Method: Json Datasını Java Objesine çevirir.(De-Serialization)

    public  static <T> T convertJsonToJavaObject(String json,Class<T> cls) throws IOException {//Generic Method
        T javaResult=null;
        try {
           javaResult= mapper.readValue(json,cls);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return javaResult;
    }


    }






