
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.HttpClientBuilder;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Assert;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by nurkulov 12/26/19
 */
public class APITasks {

    /*
     * GET all soccer team names listed in given resource
     * Deserialization type: Pojo
     */
    public static List<String> getAllTeams() throws URISyntaxException, IOException {
        return null;
    }

    /*
     * GET names of all goalkeepers from England team
     * note: England team id is 66
     * Deserialization type: TypeReference
     */
    public static List<String> getAllGoalkeepers() throws URISyntaxException, IOException {

        HttpClient httpClient= HttpClientBuilder.create().build();
        URIBuilder uriBuilder=new URIBuilder();
        // http://api.football-data.org/v2/teams/66
        uriBuilder.setScheme("http").setHost("api.football-data.org").setPath("v2/teams/66");

        HttpGet httpGet=new HttpGet(uriBuilder.build());
        httpGet.setHeader("Accept","application/json");
        httpGet.setHeader("X-Auth-Token","42722579a6824fceb9bc68800e4e42c5");

        HttpResponse httpResponse=httpClient.execute(httpGet);
        Assert.assertEquals(HttpStatus.SC_OK,httpResponse.getStatusLine().getStatusCode());

        ObjectMapper objectMapper=new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,false);
        objectMapper.configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES,true);
        Map<String,Object> parsedObject=objectMapper.readValue(httpResponse.getEntity().getContent(),
                new TypeReference<Map<String, Object>>() {});

        List<Map<String,Object>> squad = (List<Map<String, Object>>) parsedObject.get("squad");

      //  System.out.println(squad);
        List<String> goalKeepers=new ArrayList<>();

        try {
            for(Map<String,Object> sq:squad){
                if(sq.get("position").toString().equalsIgnoreCase("Goalkeeper") ){
                    goalKeepers.add(sq.get("name").toString());
                }
            }

        }catch (NullPointerException e){
        //    e.printStackTrace();
        }
        System.out.println(goalKeepers);

        return goalKeepers;
    }

    /*
     * GET names of all defenders from England team
     * note: England team id is 66
     * Deserialization type: TypeReference
     */
    public static List<String> getDefenders() throws URISyntaxException, IOException {
        return null;
    }

    /*
     * GET names of all midfielders from England team
     * note: England team id is 66
     * Deserialization type: Pojo
     */
    public static List<String> getMidfielders() throws IOException, URISyntaxException {
        return null;
    }

    /*
     * GET names of all midfielders from England team whose country is Brazil
     * note: England team id is 66
     * Deserialization type: Pojo
     */
    public static List<String> getMidfielderFromBrazil() throws URISyntaxException, IOException {
        return null;
    }

    /*
     * GET names of all attackers from England team whose origin country is England
     * note: England team id is 66
     * Deserialization type: Pojo
     */
    public static List<String> getAttackerFromEngland() throws URISyntaxException, IOException {
        return null;
    }

    /*
     * GET name of Spain team coach
     * note: Spain team id is 77
     * Deserialization type: Pojo
     */
    public static List<String> getSpainCoach() throws URISyntaxException, IOException {
        return null;
    }

    /*
    GET list of all competitions
    Deserialization type: POJO
     */
    public static List<String> getAllCompetitions() throws URISyntaxException, IOException {
        return null;

    }

    /*
     * GET names of second highest scorrer from competitions of 2000 season
     * note: endpoint for competitions: `competitions/<year>/
     * note: endpoint for scorers: `competitions/<year>/scorers`
     * Deserialization type: Pojo and TypeReference
     */
    public static List<String> getSecondHighestScorer() throws URISyntaxException, IOException {
        return null;
    }
}
