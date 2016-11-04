
/* I mostly just pulled this from the Linked List mergesort assignment since we're using the same data.
 * I made an adjustment so that the key would be defined based on a boolean I gather from user input
 */
public class CityOb {
    public String dma, city, state, key;
    public CityOb(String constructor, boolean selector){
        int locator = constructor.indexOf(',');
        dma = constructor.substring(0, locator);
        locator++;
        int locator2 = constructor.indexOf(',', locator);
        city = constructor.substring(locator, locator2);
        locator2++;
        state = constructor.substring(locator2, constructor.length());
        if(selector)key = dma;
        else key = city;

    }

    public String toString(){
        return dma + "," + city + "," + state;
    }

    public String citySubstring(int a, int b){
        return city.substring(a, b);
    }

    public int cityIndexOf(char a, int b){
        return city.indexOf(a, b);
    }

    public char cityCharAt(int a){
        return city.charAt(a);
    }

    public int cityLength(){
        return city.length();
    }

}
