/**
 * @Filename:		objson.java
 * @Author:			Ortins, Jeremy
 * @Date:			2014-09-20
 * @Version:		1.0
 * @Description:	A support class to serialize a Java object into JSON format using JSON style Java notation
 * @Revisions:
 */

public class objson {

    /**
     * Generates a JSON array wrapper for a collection of object(s) parameters
     * @param 	arr
     * @param 	data
     * @return	String
     * @throws 	Exception
     */
    public static String toJsonArray(String arr, String data) throws Exception {
        try {
            return "{ \"" + arr + "\": [" + data.replaceAll(" \\}\\{ ", " },{ ") + "] }";
        } catch(Exception e) {
            e.printStackTrace();
            throw e;
        }
    }


    /**
     * Serializes a Java object into a JSON string handling an infinite number of object parameters (with a finite set of supported types)
     * @param 	args
     * @return	String
     * @throws 	Exception
     */
    public static <T> String toJson(T... args) throws Exception {
        try {
            StringBuilder json = new StringBuilder();
            int tryCnt = 0;
            int pIdx = 0;
            int pCnt = getNumPairs(args.length);

            json.append("{ ");
            for(int i = 0; i < args.length; tryCnt++) {

                // Escape
                if(tryCnt >= args.length) {
                    break;
                }

                // Append value
                if(pIdx < pCnt) {
                    json.append(makePair(args[i],args[(i+1)]));
                    i = i + 2;
                    pIdx++;
                } else {
                    break;
                }

                // Comma Delimiter
                if(i < (args.length-1)) {
                    json.append(", ");
                }
            }
            json.append(" }");
            return json.toString();

        } catch(Exception e) {
            e.printStackTrace();
            throw e;
        }
    }
	
	
	/**
     * Generates a key value pair based on a set of template arguments within a range of finite types
     * @param 	key
     * @param 	val
     * @return	String
     * @throws 	Exception
     */
    private static <T> String makePair(T key, T val) throws Exception {
        try {
            StringBuilder result = new StringBuilder();

            // KEY TYPE
            if(key instanceof String) {
                result.append("\"" + (String)key + "\": ");
            }

            // VALUE TYPE
            if(val instanceof Boolean) {
                result.append((Boolean)val);
            } else if(val instanceof Double) {
                result.append((Double)val);
            } else if(val instanceof Float) {
                result.append((Float)val);
            } else if(val instanceof Integer) {
                result.append((Integer)val);
            } else if(val instanceof Long) {
                result.append((Long)val);
            } else if(val instanceof String) {
                if( ((String) val) != null & ((String) val).length() > 0 ) {
                    result.append(
                            (((String)val).charAt(0) == '{') ? (String)val : "\"" + (String)val + "\""
                    );
                } else {
                    result.append("\"\"");
                }
            }
            return result.toString();
        } catch(Exception e) {
            e.printStackTrace();
            throw e;
        }
    }
	
	
	/**
     * Determines how many parameter pairs (name/value) there are to be serialized
     * @param	length
     * @return	int
     * @throws 	Exception
     */
    private static int getNumPairs(int length) throws Exception {
        try {
            return ((length & 1) == 0) ? ((int)length / (int)2) : ((int)(length-1) / (int)2);
        } catch(Exception e) {
            e.printStackTrace();
            throw e;
        }
    }
	
	
	/**
     * Handles any exceptions by returning a JSON formatted exception string
     * @param 	what
     * @param 	type
     * @param 	msg
     * @return	String
     */
    public static String getException(String what, String type, String msg) {
        try {
            return "{ \"exceptions\": [{ "
                    + "\"what\": \"" + what + "\""
                    + ", \"type\": \"" + type + "\""
                    + ", \"msg\": \"" + msg + "\" }] }";
        }
        catch(Exception e) {
            e.printStackTrace();
            return "status: " + e.getMessage();
        }
    }
	
	
	/**
     * Handles string validation; returns a predefined message for invalid strings
     * @param 	val
     * @return	String
     */
    public static String filterString(String val) {
        try {
			return (((String) val) != null & ((String) val).length() > 0) ? val : "UNDEFINED";
        } catch(Exception e) {
            return "UNDEFINED";
        }
    }

}