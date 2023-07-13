package computerdatabase;

import static io.gatling.javaapi.core.CoreDsl.StringBody;
import static io.gatling.javaapi.core.CoreDsl.constantUsersPerSec;
import static io.gatling.javaapi.core.CoreDsl.css;
import static io.gatling.javaapi.core.CoreDsl.csv;
import static io.gatling.javaapi.core.CoreDsl.exec;
import static io.gatling.javaapi.core.CoreDsl.incrementConcurrentUsers;
import static io.gatling.javaapi.core.CoreDsl.rampUsers;
import static io.gatling.javaapi.core.CoreDsl.repeat;
import static io.gatling.javaapi.core.CoreDsl.scenario;
import static io.gatling.javaapi.core.CoreDsl.tryMax;
import static io.gatling.javaapi.http.HttpDsl.http;
import static io.gatling.javaapi.http.HttpDsl.status;
import io.gatling.javaapi.core.ChainBuilder;
import io.gatling.javaapi.core.FeederBuilder;
import io.gatling.javaapi.core.ScenarioBuilder;
import io.gatling.javaapi.core.Simulation;
import io.gatling.javaapi.http.HttpProtocolBuilder;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

/**
 * This sample is based on our official tutorials:
 * <ul>
 *   <li><a href="https://gatling.io/docs/gatling/tutorials/quickstart">Gatling quickstart tutorial</a>
 *   <li><a href="https://gatling.io/docs/gatling/tutorials/advanced">Gatling advanced tutorial</a>
 * </ul>
 */
public class TestCallBaiduSimulation extends Simulation {

    private static int TEST_USER_COUNT = 5000;

    private static int DURATION_SECONDS = 100;

    private HttpProtocolBuilder httpProtocol = http
            //.baseUrl("http://localhost:8001")
            //.baseUrl("https://pre.actqa.com/qa/v1/question-tag/all")
            //.baseUrl("http://172.31.6.108:8003/qa/v1/question-tag/all")
            .baseUrl("http://172.31.24.146:8000/qa/v1/question-tag/all")
            //.baseUrl("http://172.31.1.17")
            .inferHtmlResources()
            .userAgentHeader("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/113.0.0.0 Safari/537.36")
            .shareConnections();

    private ScenarioBuilder scn = scenario(TestCallBaiduSimulation.class.getName())
            .exec(
                    //http("/").get("/query/user?id=1")
                    http("/").get("/")
            );

    {
//        setUp(
//                // generate a closed workload injection profile
//                // with levels of 10, 15, 20, 25 and 30 concurrent users
//                // each level lasting 10 seconds
//                // separated by linear ramps lasting 10 seconds
//                scn.injectClosed(
//                        incrementConcurrentUsers(100)
//                                .times(10)
//                                .eachLevelLasting(10)
//                                .separatedByRampsLasting(10)
//                                .startingFrom(100) // Int
//                ).protocols(httpProtocol)
//        );
        setUp(scn.injectOpen(constantUsersPerSec(TEST_USER_COUNT).during(DURATION_SECONDS))).protocols(httpProtocol);
    }
}
