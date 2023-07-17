package computerdatabase;

import static io.gatling.javaapi.core.CoreDsl.StringBody;
import static io.gatling.javaapi.core.CoreDsl.constantUsersPerSec;
import static io.gatling.javaapi.core.CoreDsl.scenario;
import static io.gatling.javaapi.http.HttpDsl.http;
import io.gatling.javaapi.core.ScenarioBuilder;
import io.gatling.javaapi.core.Simulation;
import io.gatling.javaapi.http.HttpProtocolBuilder;
import java.util.UUID;

/**
 * This sample is based on our official tutorials:
 * <ul>
 *   <li><a href="https://gatling.io/docs/gatling/tutorials/quickstart">Gatling quickstart tutorial</a>
 *   <li><a href="https://gatling.io/docs/gatling/tutorials/advanced">Gatling advanced tutorial</a>
 * </ul>
 */
public class RegistrationAllSimulation extends Simulation {

    private static int TEST_USER_COUNT = 600;

    private static int DURATION_SECONDS = 100;

    private HttpProtocolBuilder httpProtocol = http
            .baseUrl("http://172.31.18.132:8000")
            //.baseUrl("https://pre.actqa.com")
            .inferHtmlResources()
            .userAgentHeader("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/113.0.0.0 Safari/537.36")
            .shareConnections();

    private ScenarioBuilder scn = scenario(RegistrationAllSimulation.class.getName())
            .exec(
                    http("registration")
                            .post("/account/user_action/wallet_login")
                            .header("content-type", "application/json")
                            .body(StringBody(v -> generateBody()))
            );

    private static String generateBody(){
        String address = "{ \"walletAddress\": \"" + randomWalletAddress() + "\",\"walletSign\": \"123456\"}";
        //System.out.println(address);
        return address;
    }

    private static String randomWalletAddress(){
        return "0x11"+ UUID.randomUUID().toString().replace("-","");
    }

    {
        setUp(scn.injectOpen(constantUsersPerSec(TEST_USER_COUNT).during(DURATION_SECONDS))).protocols(httpProtocol);
    }

    public static void main(String[] args) {
        System.out.println(generateBody());
    }
}
