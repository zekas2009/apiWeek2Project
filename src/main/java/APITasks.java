

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
import POJO.TeamsPojo;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import java.util.Set;


/**
 * Created by nurkulov 12/26/19
 */
public class APITasks {

    /*
     * GET all soccer team names listed in given resource
     * Deserialization type: Pojo
     */

    public static List<String> getAllTeams() throws URISyntaxException, IOException {


        HttpClient httpClient = HttpClientBuilder.create().build();

        URIBuilder uriBuilder = new URIBuilder();
//http://api.football-data.org/v2/teams/
        uriBuilder.setScheme("http");
        uriBuilder.setHost("api.football-data.org");
        uriBuilder.setPath("v2/teams/");


        HttpGet httpGet = new HttpGet(uriBuilder.build());
        httpGet.setHeader("Accept", "application/json");
        httpGet.setHeader("Content-Type", "application/json");
        httpGet.setHeader("X-Auth-Token","23313095d88c47c8a01362bf1adc1e6d");

        HttpResponse httpResponse = httpClient.execute(httpGet);

        ObjectMapper objectMapper = new ObjectMapper();
     //   objectMapper.configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        TeamsPojo teamsPojo = objectMapper.readValue(httpResponse.getEntity().getContent(), TeamsPojo.class);


        List<String> teams = new ArrayList<>();


        for(int i=0; i<teamsPojo.getTeams().size(); i++){
       teams.add(teamsPojo.getTeams().get(i).getName());

        }


        return teams;
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
        HttpClient client= HttpClientBuilder.create().build();
            URIBuilder uri=new URIBuilder();
            uri.setScheme("https").setHost("api.football-data.org").setPath("v2/competitions/2000/scorers");
            HttpGet get=new HttpGet(uri.build());
            get.setHeader("Accept","application/json");
            get.setHeader("X-Auth-Token","371c56e9b4e540ac915d2c7587b9b4d9");
            HttpResponse response=client.execute(get);
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES,false);
            APIPOJO apipojo=objectMapper.readValue(response.getEntity().getContent(),APIPOJO.class);
            List<Integer> goals=new ArrayList<>();
            for(int i=0; i<apipojo.getScorers().size();i++){
                goals.add(apipojo.getScorers().get(i).getNumberOfGoals());
            }
            Collections.sort(goals);
            Integer maxGoals=goals.get(goals.size()-1);
            int second=0;
            for (int i=goals.size()-1; i>=0;i--){
                if(goals.get(i)<maxGoals){
                    second=goals.get(i);
                    break;
                }

            }
            List<String> names=new ArrayList<>();
            for(int i=0; i<apipojo.getScorers().size();i++) {
                if(apipojo.getScorers().get(i).getNumberOfGoals()==second){
                    names.add((String)apipojo.getScorers().get(i).getPlayer().get("name"));
                }
            }
            System.out.println(names);

        return names;
    }
}
